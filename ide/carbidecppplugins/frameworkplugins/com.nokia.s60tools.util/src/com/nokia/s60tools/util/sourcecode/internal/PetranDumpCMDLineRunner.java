/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.util.sourcecode.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.nokia.s60tools.util.cmdline.CmdLineCommandExecutorFactory;
import com.nokia.s60tools.util.cmdline.CmdLineExeption;
import com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor;
import com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver;
import com.nokia.s60tools.util.cmdline.UnsupportedOSException;
import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.internal.Messages;
import com.nokia.s60tools.util.sourcecode.SourceFileLocation;

/**
 * Helper Class for running <code>\epoc32\release\[variant]\[build]\petran.exe -dump</code> for founding 
 * method address by ordinal
 */
public class PetranDumpCMDLineRunner implements ICmdLineCommandExecutorObserver {

	/**
	 * Constants for executing command-line commands
	 */	
	private static final String C_PARAM = "/C"; //$NON-NLS-1$
	private static final String CMD_PARAM = "cmd"; //$NON-NLS-1$
	private static final String HEXA_ADDRESS_PREFIX = "0x"; //$NON-NLS-1$
	private static final String TOOLS = "tools"; //$NON-NLS-1$
	private static final String ORDINAL_AND_WHITE_SPACE = "ordinal "; //$NON-NLS-1$
	private static final String PETRAN_EXE_DUMP_PARAM = "-dump"; //$NON-NLS-1$
	private static final String PETRAN_EXE_E_PARAM = "e"; //$NON-NLS-1$
	/**
	 * Executable name for petran.exe
	 */
	public static final String PETRAN_EXE_NAME = "petran.exe"; //$NON-NLS-1$
	/**
	 * Executable name for elftran.exe
	 */
	public static final String ELFTRAN_EXE_NAME = "elftran.exe"; //$NON-NLS-1$

    private ICmdLineCommandExecutor cmdLineExecutor = null;
	private PetranDumpLineReader stdOutReader;
	private String ordinalIndex;
	private PetranDumpLineReader stdErrReader;
	private IConsolePrintUtility printUtility = null;

	/**
	 * Creates CMD line runner for running <code>petran.exe</code> to seek method address by ordinal
	 * @param printUtility Print utility used to report error, warnings, and info messages.
	 * @throws UnsupportedOSException if currently used OS is not supported
	 */
    public PetranDumpCMDLineRunner( IConsolePrintUtility printUtility ) throws UnsupportedOSException{
		this.printUtility = printUtility;
		cmdLineExecutor = CmdLineCommandExecutorFactory.
			CreateOsDependentCommandLineExecutor(this, new PetranCMDLinePrintUtility( printUtility ));    	
    }
    
	/**
	 * Get address for function by ordinal
	 * @param ordinal Function ordinal.
	 * @param dllName DLL name.
	 * @param variant Build variant.
	 * @param build Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @return Address for method
	 * @throws InterruptedException if the current thread is interrupted by another thread while it is waiting.
	 * @throws CmdLineExeption if there was errors in command line run.
	 * @throws NumberFormatException if found address is not valid address.
	 */
	public String getAddress(String ordinal, String dllName, String variant, String build, String epocRootPath)  
	throws InterruptedException, CmdLineExeption, NumberFormatException{
		
		if(ordinal == null || ordinal.trim().length() == 0){
			return null;
		}
		
		this.ordinalIndex = ordinal.trim() + ":";		 //$NON-NLS-1$
		
		String[] cmdLineArray = buildCommandLine(ordinal, dllName, variant, build, epocRootPath);
		stdOutReader = new PetranDumpLineReader( new ArrayList<String>());
		stdErrReader = new PetranDumpLineReader( new ArrayList<String>());
		//Running synchronous command
		int res = cmdLineExecutor.runSyncCommand(cmdLineArray, stdOutReader, stdErrReader);

		//get address from result, throws exeption if there was some errors
		String addr = seekAddressFromResult();
		
		if(addr == null){
			//Throw exception if there is errors or prints warning if there is no errors in stdErrReader
			handleError(res);		
		}		
				
		return addr;
	}

	/**
	 * Get address for function by given ordinal
	 * @param ordinal Function ordinal
	 * @param dllPath DLL path
	 * @param epocRootPath EPOCROOT path
	 * @return Address for the method
	 * @throws InterruptedException
	 * @throws CmdLineExeption
	 * @throws NumberFormatException
	 */
	public String getAddress(String ordinal, String dllPath, String epocRootPath)  
	throws InterruptedException, CmdLineExeption, NumberFormatException{
		
		if(ordinal == null || ordinal.trim().length() == 0){
			return null;
		}
		
		this.ordinalIndex = ordinal.trim() + ":";		 //$NON-NLS-1$
		
		String[] cmdLineArray = buildCommandLine(ordinal, dllPath, epocRootPath);
		stdOutReader = new PetranDumpLineReader( new ArrayList<String>());
		stdErrReader = new PetranDumpLineReader( new ArrayList<String>());
		//Running synchronous command
		int res = cmdLineExecutor.runSyncCommand(cmdLineArray, stdOutReader, stdErrReader);

		//get address from result, throws exeption if there was some errors
		String addr = seekAddressFromResult();
		
		if(addr == null){
			//Throw exception if there is errors or prints warning if there is no errors in stdErrReader
			handleError(res);		
		}		
				
		return addr;
	}
	/**
	 * Handles error situations, when results was not found.
	 * @param retCode Return code from command-line execution.
	 * @throws CmdLineExeption
	 */
	private void handleError(int retCode) throws CmdLineExeption {
		//if there was no errors with running command, and we did not found result, just returning null
		//error situation is, if there is errors in reader or return code was not expected
		
		//Check if there was some errors
		ArrayList<String> errs = stdErrReader.getResultLinesArrayList();
		if(!errs.isEmpty()){
			StringBuffer b = new StringBuffer();
			for (Iterator<String> iterator = errs.iterator(); iterator.hasNext();) {
				String err = (String) iterator.next();
				b.append(err);
			}
			throw new CmdLineExeption(b.toString());
		}
		//error handler for cases that there was no errors on running, but return code was not 0, and we did not found result
		else if(retCode != 0){			
			//if there was some errors with running command, and seekAddressFromResult was not sending Exception, writing message to console
			printUtility.println(Messages.getString("PetranDumpCMDLineRunner.Unexpected_Return_Value_Warning") +retCode, IConsolePrintUtility.MSG_WARNING);//$NON-NLS-1$				
		}		
		
	}

	/**
	 * Create a command line to execute command.
	 * @param ordinal Function ordinal.
	 * @param dllName DLL name.
	 * @param variant Build variant.
	 * @param build Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @return Command-line string array.
	 * @throws IOException
	 */
	private String[] buildCommandLine(String ordinal, String dllName, String variant, String build, String epocRootPath){
		Vector<String> cmdLineVector = new Vector<String>();
		String epocRootPathWithSlash = epocRootPath.endsWith(File.separator) ? epocRootPath : epocRootPath + File.separatorChar; 
		String dllPath = 
			epocRootPathWithSlash
			+SourceFileLocation.EPOC32_FOLDER_NAME + File.separatorChar
			+SourceFileLocation.RELEASE_FOLDER_NAME + File.separatorChar
			+variant + File.separatorChar
			+build + File.separatorChar
			+dllName;

		// By default using elftran.exe for dumping function data
		String exePath = 
			epocRootPath + File.separatorChar 
			+SourceFileLocation.EPOC32_FOLDER_NAME + File.separatorChar
			+TOOLS + File.separatorChar
			+ELFTRAN_EXE_NAME;
		
		if(!new File(exePath).exists()){
			// If elftran.exe is not found using petran.exe instead as fallback mechanism
			exePath = 
				epocRootPath + File.separatorChar 
				+SourceFileLocation.EPOC32_FOLDER_NAME + File.separatorChar
				+TOOLS + File.separatorChar
				+PETRAN_EXE_NAME;
		}
		
		//Adding quotes (") to paths <command> --> "<command>"
		//prevent problems with folder/file -names with white spaces.
		
		//running external command, cmd /C is needed
		cmdLineVector.add("\"" + CMD_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		cmdLineVector.add("\"" + C_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		// Executable
		cmdLineVector.add("\"" + exePath +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		// Parameters, excel if sheet to convert
		cmdLineVector.add("\"" + PETRAN_EXE_DUMP_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		cmdLineVector.add("\"" + PETRAN_EXE_E_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		// Options, location to store converted xml file. 
		cmdLineVector.add("\"" + dllPath +"\"");   //$NON-NLS-1$   //$NON-NLS-2$
		
		return cmdLineVector.toArray(new String[0]);
	}

	/**
	 * Create a command line to execute command, based on given parameters.
	 * @param ordinal Function ordinal.
	 * @param dllPath DLL path
	 * @param epocRootPath EPOCROOT path
	 * @return Command-line string array.
	 */
	private String[] buildCommandLine(String ordinal, String dllPath, String epocRootPath){
		Vector<String> cmdLineVector = new Vector<String>();
		
		// By default using elftran.exe for dumping function data
		String exePath = 
			epocRootPath + File.separatorChar 
			+SourceFileLocation.EPOC32_FOLDER_NAME + File.separatorChar
			+TOOLS + File.separatorChar
			+ELFTRAN_EXE_NAME;
		
		if(!new File(exePath).exists()){
			// If elftran.exe is not found using petran.exe instead as fallback mechanism
			exePath = 
				epocRootPath + File.separatorChar 
				+SourceFileLocation.EPOC32_FOLDER_NAME + File.separatorChar
				+TOOLS + File.separatorChar
				+PETRAN_EXE_NAME;
		}
		
		//Adding quotes (") to paths <command> --> "<command>"
		//prevent problems with folder/file -names with white spaces.
		
		//running external command, cmd /C is needed
		cmdLineVector.add("\"" + CMD_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		cmdLineVector.add("\"" + C_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		// Executable
		cmdLineVector.add("\"" + exePath +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		// Parameters, excel if sheet to convert
		cmdLineVector.add("\"" + PETRAN_EXE_DUMP_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		cmdLineVector.add("\"" + PETRAN_EXE_E_PARAM +"\"");//$NON-NLS-1$   //$NON-NLS-2$
		// Options, location to store converted xml file. 
		cmdLineVector.add("\"" + dllPath +"\"");   //$NON-NLS-1$   //$NON-NLS-2$
		
		return cmdLineVector.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#interrupted(java.lang.String)
	 */
	public void interrupted(String reasonMsg) {
		//needed only because of CmdLineCommandExecutorFactory need ICmdLineCommandExecutorObserver implementation, 
		//because this class using only synchronous method from CMD line running, observer not needed		
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#processCreated(java.lang.Process)
	 */
	public void processCreated(Process proc) {
		//needed only because of CmdLineCommandExecutorFactory need ICmdLineCommandExecutorObserver implementation, 
		//because this class using only synchronous method from CMD line running, observer not needed		
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#progress(int)
	 */
	public void progress(int percentage) {
		//needed only because of CmdLineCommandExecutorFactory need ICmdLineCommandExecutorObserver implementation, 
		//because this class using only synchronous method from CMD line running, observer not needed
	}		
	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#completed(int)
	 */
	public void completed(int exitValue) {
		//needed only because of CmdLineCommandExecutorFactory need ICmdLineCommandExecutorObserver implementation, 
		//because this class using only synchronous method from CMD line running, observer not needed
	}

	/**
	 * Get address from result by ordinal.
	 * @return address as hexa, e.g. 0x00001234 or null if can't found
	 * @throws Exception if can't found result and there is some error messages in reader
	 */
	private String seekAddressFromResult() {
		
		ArrayList<String> results = stdOutReader.getResultLinesArrayList();		
		String addr  = null;//address to return
		
		//seek lines and get wanted address
		for (Iterator<String> iterator = results.iterator(); iterator.hasNext();) {
			
			String line = (String) iterator.next();

			//Making sure that there is no tabs, only spaces
			line = line.replaceAll("\t", " "); //$NON-NLS-1$ //$NON-NLS-2$
			//removing multiple spaces: " +" means more than one white spaces, e.g. "a    b c" -> "a b c"
			line = line.replaceAll(" +", " ");			 //$NON-NLS-1$ //$NON-NLS-2$
			//remove extra spaces in the start and end of line
			line = line.trim();

			//Lines should be first like: "Ordinal    75:  00008129"
			//So now it should be like: "Ordinal 75: 00008129"

			//if current line is one of the ordinal lines
			if(line.trim().toLowerCase().startsWith(ORDINAL_AND_WHITE_SPACE)){

				//removing "ordinal " from line, so it will start with the number
				line = line.substring(ORDINAL_AND_WHITE_SPACE.length());

				//if line starts number what we are intressed of, e.g. "75:"
				if(line.startsWith(ordinalIndex)){
					//rest of the string is address
					addr = line.substring(ordinalIndex.length()).trim();
					checkAddressNumberFormat(addr);
					if(!addr.toLowerCase().startsWith(HEXA_ADDRESS_PREFIX)){
						addr = HEXA_ADDRESS_PREFIX + addr;						
					}					
					break;
				}
			}
		}		
		
		//If not found, just returning null
		return addr;
	}

	/**
	 * Check that address is in number format.
	 * @throws NumberFormatException if address is not in number format
	 * @param addr Address string.
	 */
	private void checkAddressNumberFormat(String addr) throws NumberFormatException {
		String number = addr;
		if(number.toLowerCase().startsWith(HEXA_ADDRESS_PREFIX)){
			number = number.substring(number.indexOf(HEXA_ADDRESS_PREFIX) +HEXA_ADDRESS_PREFIX.length() ) ;						
		}
		Integer.parseInt(number, 16);		
	}
	
}
