/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description:
*
*/
 
 
package com.nokia.s60tools.util.cmdline;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;

import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.internal.Messages;


/**
 * Command line tool executor class for 
 * Win32 platform.
 */
class Win32CmdLineCommandExecutor implements ICmdLineCommandExecutor {
	
	private final ICmdLineCommandExecutorObserver observer;
	private final IConsolePrintUtility consolePrintUtility;
	private ICustomLineReader stdOutReader = null;
	private ICustomLineReader stdErrReader = null;
	
	private boolean isCurrentExecutionCancelled = false;
	
	/**
	 * Constructor.
	 */
	Win32CmdLineCommandExecutor(ICmdLineCommandExecutorObserver observer, 
			                    IConsolePrintUtility consolePrintUtility){
		this.observer = observer;
		this.consolePrintUtility = consolePrintUtility;
	}
	
	/**
	 * Helper class that can be used to consume 
	 * given input stream in its own thread and
	 * print the results to the console.
	 */
	private class StreamConsumerWorkerThread extends Thread	{
		
		public static final int STDOUT_STREAM = 0;
		public static final int STDERR_STREAM = 1;
		
	    InputStream is = null;	    
	    int streamType;
	    ICustomLineReader customLineReader = null;
	    
	    StreamConsumerWorkerThread(InputStream is, 
	    						   int streamType, 
	    						   ICustomLineReader customLineReader){
	        this.is = is;
	        this.streamType = streamType;
	        if(customLineReader != null){
		        this.customLineReader = customLineReader;	        	
	        }
	        else{
	        	this.customLineReader = new DefaultLineReader();
	        }
	    }
	    	    
	    /* (non-Javadoc)
	     * @see java.lang.Thread#run()
	     */
	    public void run(){
	        try{
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            
	            String line=null;
	            while ( (line = customLineReader.readLine(br, observer)) != null){
	                	            	
	            	//      All spaces are seen by default as ÿ characters, and there is currently 
	            	//      no better way to correct it than by using replaceAll	            	
	            	String reformattedLine = line.replaceAll("ÿ", " "); //$NON-NLS-1$ //$NON-NLS-2$

	            	if(streamType == STDERR_STREAM){
	            		consolePrintUtility.println(
	            									reformattedLine,
	            									IConsolePrintUtility.MSG_ERROR
	            										);	
	            	}
	            	else{
	            		consolePrintUtility.println(	
													reformattedLine);
	            	}	            	
	            }
	        } catch (IOException ioe){
                ioe.printStackTrace();  
              }
	    }
	} // End of private class StreamConsumerWorkerThread
	
	/**
	 * Worker thread that allows the running of command line
	 * tool as a background process, instead of UI blocking one.
	 */
	private class CmdLineCommandExecutorWorkerThread extends Thread {
		
		/**
		 * Command-line string array list.
		 */
		private List<String[]> cmdLineArrayList = null;
		
		/**
		 * Path to directory where to run the commands.
		 */
		private String path = null;

		/**
		 * Constructor.
		 * @param cmdLineArrayList List of command lines in a string array form.
		 */
		public CmdLineCommandExecutorWorkerThread(List<String[]> cmdLineArrayList){
			this.cmdLineArrayList = cmdLineArrayList;				
		}
		
		/**
		 * Constructor.
		 * @param cmdLineArrayList List of command lines in a string array form.
		 * @param path Path to directory where to run the commands.
		 */
		public CmdLineCommandExecutorWorkerThread(List<String[]> cmdLineArrayList, String path){
			this.cmdLineArrayList = cmdLineArrayList;		
			this.path = path;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run()
		{
			String cmdLine = null;
	        Process proc = null;

	        String msgUnexpectedException = Messages.getString("Win32CmdLineCommandExecutor.UnexpectedException_ConsoleMsg"); //$NON-NLS-1$
	        String msgCmdLine = Messages.getString("Win32CmdLineCommandExecutor.Unexcepted_Exception_When_Running_ConsoleMsg"); //$NON-NLS-1$
	        String msgException = Messages.getString("Win32CmdLineCommandExecutor.Exception_Desc_ConsoleMsg"); //$NON-NLS-1$
	        
	        for (String[] cmdLineArray : cmdLineArrayList) {		        
		        try {
		        	
		        	if(isCurrentExecutionCancelled){
		        		return;
		        	}
		        	
					cmdLine = new String(""); //$NON-NLS-1$
					for (int i = 0; i < cmdLineArray.length; i++) {
						cmdLine = cmdLine +  " " + cmdLineArray[i];				 //$NON-NLS-1$
					}							
			        proc = executeCommand(cmdLine, msgCmdLine, msgException, path);		                                
			        checkExitValue(cmdLine, proc, msgCmdLine, msgException);									
			        
				} catch (Exception e) {
					e.printStackTrace();			
		    		consolePrintUtility.println(msgUnexpectedException,
							 IConsolePrintUtility.MSG_ERROR);	
		    		consolePrintUtility.println(msgException + e.getMessage(),
							 IConsolePrintUtility.MSG_ERROR);
		    		observer.interrupted(e.getMessage());
				}
				
			} // for
	        
	        // Informing that all commands has been executed.
			if(observer instanceof ICmdLineCommandExecutorObserver2){
				((ICmdLineCommandExecutorObserver2)observer).allCommandsExecuted();
			}
		} 
		
	}//End of private class CmdLineCommandExecutorWorkerThread
	
	/**
	 * Waits for the end of the given process, checks the exit value,
	 * and prints the necessary information to the console output. 
	 * @param cmdLine Command line as a string.
	 * @param proc Process to be waited for ending.
	 * @param msgCmdLine Error message given if execution fails.
	 * @param msgException Error message prefixed before exception's message.
	 */
	private void checkExitValue(String cmdLine, Process proc, String msgCmdLine, String msgException) {
		// Waiting for the process to end and checking the exit status
        int exitVal = 0;

		String exitValMsg = Messages.getString("Win32CmdLineCommandExecutor.Cmd_ExitVal_ConsoleMsg"); //$NON-NLS-1$
		String interruptedCmdMsg = Messages.getString("Win32CmdLineCommandExecutor.CmdExec_Interrupted_ConsoleMsg"); //$NON-NLS-1$

		try {
			observer.processCreated(proc);
			exitVal = proc.waitFor();			

			// Printing exitValue to console only in case of an error.
			// In other cases the printout messes up the result printout.
			if(exitVal != 0){
        		consolePrintUtility.println(exitValMsg + exitVal,
											 IConsolePrintUtility.MSG_ERROR);	
        	}
			
			// Notifying the observer
			if(observer instanceof ICmdLineCommandExecutorObserver2){
				((ICmdLineCommandExecutorObserver2)observer).completed(proc);
			}
			else{
				observer.completed(exitVal);					
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			consolePrintUtility.println(interruptedCmdMsg + cmdLine,
					IConsolePrintUtility.MSG_WARNING);	
			observer.interrupted(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			consolePrintUtility.println(msgCmdLine + cmdLine,
					 IConsolePrintUtility.MSG_ERROR);	
			consolePrintUtility.println(msgException + e.getMessage(),
					 IConsolePrintUtility.MSG_ERROR);	
			observer.interrupted(e.getMessage());
		}
	}
	
	/**
	 * Executed the command given in command line array and returns
	 * the invoked process. Implements the necessary logging to the console.
	 * @param cmdLine Command line as a string.
	 * @param msgCmdLine Error message given if execution fails.
	 * @param msgException Error message prefixed before exception message.
	 * @param path Path to directory where to run this command
	 * @return Newly created process for the invoked command.
	 */
	private Process executeCommand(String cmdLine, String msgCmdLine, String msgException, String path) {
		File dir = null;
		if(path != null) {
			dir = new File(path);
		}
		return executeCommand(cmdLine, msgCmdLine, msgException, false, dir);
	}
	
	/**
	 * Executed the command given in command line array and returns
	 * the invoked process. Implements the necessary logging to the console.
	 * @param cmdLine Command line as a string.
	 * @param msgCmdLine Error message given if execution fails.
	 * @param msgException Error message prefixed before exception message.
	 * @param isSyncCall set to <code>true</code> if there is need to make synchronous call.
	 * @return Newly created process for the invoked command.
	 */
	private Process executeCommand(String cmdLine, String msgCmdLine, String msgException, boolean isSyncCall) {
		return executeCommand(cmdLine, msgCmdLine, msgException, isSyncCall, null);
	}
	
	/**
	 * Executed the command given in command line array and returns
	 * the invoked process. Implements the necessary logging to the console.
	 * @param cmdLine Command line as a string.
	 * @param msgCmdLine Error message given if execution fails.
	 * @param msgException Error message prefixed before exception message.
	 * @param isSyncCall set to <code>true</code> if there is need to make synchronous call.
	 * @param dir Directory where to run this command
	 * @return Newly created process for the invoked command.
	 */
	private Process executeCommand(String cmdLine, String msgCmdLine, String msgException, boolean isSyncCall, File dir) {
		
		Runtime rt = null;
		Process proc = null;
		
        String msgExecuting = Messages.getString("Win32CmdLineCommandExecutor.Executing_Cmd_ConsoleMsg"); //$NON-NLS-1$

        try {			
			// Executing the command
	        rt = Runtime.getRuntime();
    		consolePrintUtility.println(msgExecuting + cmdLine);	
			proc = rt.exec(cmdLine, null, dir);
			
	        // Setting up and starting stream consumers
	        StreamConsumerWorkerThread errorStreamConsumer= null;            
	        StreamConsumerWorkerThread outputStreamConsumer = null;		
			
	        errorStreamConsumer= new StreamConsumerWorkerThread(
											proc.getErrorStream(), 
											StreamConsumerWorkerThread.STDERR_STREAM,
											stdErrReader
															  );            
	        outputStreamConsumer = new StreamConsumerWorkerThread(
	        								proc.getInputStream(),
	        								StreamConsumerWorkerThread.STDOUT_STREAM,
	        								stdOutReader
	        													);
	        if(isSyncCall){
	        	// If user wants to make synchronous call we do no invoke background processing threads
		        outputStreamConsumer.run();
		        errorStreamConsumer.run();	        	
	        }
	        else{
	        	// In normal asynchronous case output is handled at background
		        errorStreamConsumer.start();
		        outputStreamConsumer.start();	        	
	        }	        
	        
		} catch (Exception e) {
			e.printStackTrace();
    		consolePrintUtility.println(msgCmdLine + cmdLine,
					 IConsolePrintUtility.MSG_ERROR);	
    		consolePrintUtility.println(msgException + e.getMessage(),
					 IConsolePrintUtility.MSG_ERROR);	
			observer.interrupted(e.getMessage());
		}
		return proc;
	}	
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runCommand(java.lang.String[])
	 */
	public void runCommand(String[] cmdLineArray) {
		// Delegating this call further by after parameter modification
		ArrayList<String[]> cmdLineArrayList = new ArrayList<String[]>();
		cmdLineArrayList.add(cmdLineArray);
		runCommand(cmdLineArrayList);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runCommand(java.lang.String[], java.lang.String)
	 */
	public void runCommand(String[] cmdLineArray, String path) {
		// Delegating this call further by after parameter modification
		ArrayList<String[]> cmdLineArrayList = new ArrayList<String[]>();
		cmdLineArrayList.add(cmdLineArray);
		runCommand(cmdLineArrayList, path);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runCommand(java.lang.String[], com.nokia.s60tools.util.cmdline.ICustomLineReader, com.nokia.s60tools.util.cmdline.ICustomLineReader, org.eclipse.core.runtime.jobs.Job)
	 */
	public void runCommand(String[] cmdLineArray, 
						   ICustomLineReader stdOutReader, 
						   ICustomLineReader stdErrReader,
						   Job jobContext) {
		// Delegating this call further by after parameter modification
		ArrayList<String[]> cmdLineArrayList = new ArrayList<String[]>();
		cmdLineArrayList.add(cmdLineArray);
		runCommand(cmdLineArrayList, stdOutReader, stdErrReader, jobContext);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runCommand(java.util.List)
	 */
	public void runCommand(List<String[]> cmdLineArrayList) {
		// Making sure that we are using the default line reading
		stdOutReader = null;
		stdErrReader = null;	
		isCurrentExecutionCancelled = false;
		(new CmdLineCommandExecutorWorkerThread(cmdLineArrayList)).start();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runCommand(java.util.List)
	 */
	public void runCommand(List<String[]> cmdLineArrayList, String path) {
		// Making sure that we are using the default line reading
		stdOutReader = null;
		stdErrReader = null;	
		isCurrentExecutionCancelled = false;
		(new CmdLineCommandExecutorWorkerThread(cmdLineArrayList, path)).start();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runCommand(java.util.List, com.nokia.s60tools.util.cmdline.ICustomLineReader, com.nokia.s60tools.util.cmdline.ICustomLineReader, org.eclipse.core.runtime.jobs.Job)
	 */
	public void runCommand(List<String[]> cmdLineArrayList, ICustomLineReader stdOutReader, ICustomLineReader stdErrReader, Job jobContext) {
		// Setting up custom line readers
		this.stdOutReader = stdOutReader;
		this.stdErrReader = stdErrReader;	
		Thread th = new CmdLineCommandExecutorWorkerThread(cmdLineArrayList);
		if(jobContext != null){
			// Setting thread for job context to observe
			jobContext.setThread(th);
		}
		isCurrentExecutionCancelled = false;
		th.start();		
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#cancelCommandExecutions()
	 */
	public void cancelCommandExecutions() {
		isCurrentExecutionCancelled = true;		
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runSyncCommand(java.lang.String[], com.nokia.s60tools.util.cmdline.ICustomLineReader, com.nokia.s60tools.util.cmdline.ICustomLineReader)
	 */
	public int runSyncCommand(String[] cmdLineArray,
			ICustomLineReader stdOutReader, ICustomLineReader stdErrReader) throws InterruptedException {

		// Setting up custom line readers
		this.stdOutReader = stdOutReader;
		this.stdErrReader = stdErrReader;	
		
        Process proc = null;

        String msgCmdLine = Messages.getString("Win32CmdLineCommandExecutor.Unexcepted_Exception_When_Running_ConsoleMsg"); //$NON-NLS-1$
        String msgException = Messages.getString("Win32CmdLineCommandExecutor.Exception_Desc_ConsoleMsg"); //$NON-NLS-1$
        
		String cmdLine = new String(""); //$NON-NLS-1$
		for (int i = 0; i < cmdLineArray.length; i++) {
			cmdLine = cmdLine +  " " + cmdLineArray[i];				 //$NON-NLS-1$
		}	
        proc = executeCommand(cmdLine, msgCmdLine, msgException, true);
        
        //return value from Process, 0 indicates normal termination. 
    	return proc.waitFor();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor#runAsyncCommand(java.lang.String[], java.lang.String)
	 */
	public Process runAsyncCommand(String[] cmdLineArray, String path) {
		// Making sure that we are using the default line reading
		stdOutReader = null;
		stdErrReader = null;	
		isCurrentExecutionCancelled = false;
		
		Process proc = null;

        String msgCmdLine = Messages.getString("Win32CmdLineCommandExecutor.Unexcepted_Exception_When_Running_ConsoleMsg"); //$NON-NLS-1$
        String msgException = Messages.getString("Win32CmdLineCommandExecutor.Exception_Desc_ConsoleMsg"); //$NON-NLS-1$
        
		String cmdLine = new String(""); //$NON-NLS-1$
		for (int i = 0; i < cmdLineArray.length; i++) {
			cmdLine = cmdLine +  " " + cmdLineArray[i];	//$NON-NLS-1$
		}
		
		// Getting the process.
        proc = executeCommand(cmdLine, msgCmdLine, msgException, path);
        
        // Starting thread that will wait until process ends and informs later through callback.
        CheckExitValueThread newThread = new CheckExitValueThread(cmdLine, proc, msgCmdLine, msgException);
        newThread.start();
        
		return proc;
	}
	
	/**
	 * Helper class that can be used to check exit value
	 * in different thread.
	 */
	private class CheckExitValueThread extends Thread{

		private final String cmdLine;
		private final String msgCmdLine;
		private final String msgException;
		private final Process proc;

		/**
		 * Constructor.
		 * @param cmdLine Command line as a string.
		 * @param proc Process to be waited for ending.
		 * @param msgCmdLine Error message given if execution fails.
		 * @param msgException Error message prefixed before exception message.
		 */
		public CheckExitValueThread(String cmdLine, Process proc,
				String msgCmdLine, String msgException) {
			this.cmdLine = cmdLine;
			this.proc = proc;
			this.msgCmdLine = msgCmdLine;
			this.msgException = msgException;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run(){
			checkExitValue(cmdLine, proc, msgCmdLine, msgException);									
		}
	}
	
}
