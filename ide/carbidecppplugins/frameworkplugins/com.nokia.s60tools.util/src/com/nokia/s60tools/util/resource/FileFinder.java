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
 
package com.nokia.s60tools.util.resource;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;

import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.internal.Messages;


/**
 * File finder utility.
 */
public class FileFinder {
	
	/**
	 * Default deepness for creating own {@link Thread}s for new folders.
	 */
	public static final int DEFAULT_DEPTH_FOR_NEW_THREADS = 1;

	/**
	 * How deep to folder hierarchy we will notice to observer about progress.  
	 */
	private static final int FOLDERS_DEPTH_FOR_MONITORING = 2;
	
	
	/**
	 * How deep will be new {@link Thread} created for folders
	 * 
	 * Next diagram explains deepness for more detailed level.
	 * 
	 * Deepness 1, marked: "(1)" in diagram (also default value {@link FileFinder#DEFAULT_DEPTH_FOR_NEW_THREADS} )
	 * means that all given folders will have new Thread and seeking under that folder occurs
	 * under that {@link Thread}. E.g. folder structure 
	 * under it means that there will be 2 {@link Thread}s. 
	 * 
	 * If value "2" is given, all given folders and folders
	 * under that given folder will have new {@link Thread}, in diagram value "2" means that
	 * folders with deepness (1) and (2) will have new {@link Thread}. E.g. folder structure 
	 * under it means that there will be 5 {@link Thread}s.
	 * 
	 * [Given folder 1 (1)]
	 * 		->[sub folder 1-1 (2)]
	 * 			->[sub folder 1-1-1 (3)]
	 * 		->[sub folder 1-2]
	 * 			->[sub folder 1-2-1 (3)]
	 * [Given folder 2 (1)]
	 * 		->[sub folder 2-1 (2)]
	 */
	private final int maxDepthForNewThreads;
	
	/**
	 * Keeping track of number of Threads in run
	 */
	Hashtable <Thread, String> runs;
	
	/**
	 * Files to be returned to caller
	 */
	Vector<File> filesWanted;
	
	/**
	 * Observer for passing messages to user and vice versa
	 */
	private IFileFinderObserver observer;
	
	/**
	 * Application (Plug-in) specific console print utility
	 */
	private final IConsolePrintUtility printUtility;	

	/**
	 * Using Integer instead of int for synchronized update
	 */
	private Integer stepsCompleted = new Integer (0);
	
	/**
	 * Create new FileFinde with additional max depth for new Threads parameter. Parameter
	 * tells how deep new threads will be created to each folders when seeking files. 
	 * Default value is {@link FileFinder#DEFAULT_DEPTH_FOR_NEW_THREADS}.
	 * 
	 * Next diagram explains deepness for more detailed level.
	 * 
	 * Deepness 1, marked: "(1)" in diagram (also default value {@link FileFinder#DEFAULT_DEPTH_FOR_NEW_THREADS} )
	 * means that all given folders will have new Thread and seeking under that folder occurs
	 * under that {@link Thread}. E.g. folder structure 
	 * under it means that there will be 2 {@link Thread}s. 
	 * 
	 * If value "2" is given, all given folders and folders
	 * under that given folder will have new {@link Thread}, in diagram value "2" means that
	 * folders with deepness (1) and (2) will have new {@link Thread}. E.g. folder structure 
	 * under it means that there will be 5 {@link Thread}s.
	 * 
	 * [Given folder 1 (1)]
	 * 		->[sub folder 1-1 (2)]
	 * 			->[sub folder 1-1-1 (3)]
	 * 		->[sub folder 1-2]
	 * 			->[sub folder 1-2-1 (3)]
	 * [Given folder 2 (1)]
	 * 		->[sub folder 2-1 (2)]
	 *
	 * Most effective amount of {@link Thread}s depends on folder structure, default (1) or 2 are recommended values. 
	 * 
	 * @param printUtility Utility into which print error, warning, and info messages.
	 * @param maxDepthForNewThreads how deep will be new {@link Thread} made for each folder. 
	 * Default value is {@link FileFinder#DEFAULT_DEPTH_FOR_NEW_THREADS}, 
	 * minimum value is {@link FileFinder#DEFAULT_DEPTH_FOR_NEW_THREADS}.
	 * 
	 * @param isRunFromUIThread set <code>true</code> if run from UI Thread and <code>false</code> if not. 
	 * Default values is <code>true</code>.
	 */
	public FileFinder(IConsolePrintUtility printUtility, int maxDepthForNewThreads){
		this.printUtility = printUtility;
		if(maxDepthForNewThreads > 0){
			this.maxDepthForNewThreads = maxDepthForNewThreads;
		}else{
			this.maxDepthForNewThreads = DEFAULT_DEPTH_FOR_NEW_THREADS;
		}
		
	}
	
	/**
	 * Create new FileFinder with default max seeking depth.
	 * @param printUtility Utility into which print error, warning, and info messages.
	 */
	public FileFinder(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;
		this.maxDepthForNewThreads = DEFAULT_DEPTH_FOR_NEW_THREADS;		
	}	
	

	/**
	 * Seeks all folders under given folders and finds the wanted file types.
	 * Files found are returned through {@link IFileFinderObserver#completed(int, Collection)}.
	 * 
	 * You can parameterize  How deep will be new {@link Thread} created for folders
	 * 
	 * @param observer Observer object to get results.
	 * @param fileType File types wanted to seek.
	 * @param folders Folders where to seek from.
	 */
	public void seekFilesFromFolders(IFileFinderObserver observer,
			String fileType, final String[] folders){
		
		this.observer = observer;
		
		runs = new Hashtable <Thread, String>();
		filesWanted = new Vector<File>();
		
		try {
			final FileTypeFilter typeFilter = new FileTypeFilter(fileType);			
			runSeeksFromFolders(folders, typeFilter);
		} catch (Exception e) {
			e.printStackTrace();
			String message = Messages.getString("FileFinder.ErrorOnStart_ErrMsg_Part1") //$NON-NLS-1$
				+fileType
				+Messages.getString("FileFinder.ErrorOnStart_ErrMsg_Part2") +e; //$NON-NLS-1$
			cancelAllJobs(message);
		}

	}
	
	/**
	 * Seeks all folders under given folders and finds the wanted filea.
	 * Files found are returned through {@link IFileFinderObserver#completed(int, Collection)}.
	 * 
	 * You can parameterize  How deep will be new {@link Thread} created for folders
	 * 
	 * @param observer Observer object to get results.
	 * @param fileType File types wanted to seek.
	 * @param folders Folders where to seek from.
	 */
	public void seekFilesFromFolders(IFileFinderObserver observer,
			final String fileNames[], final String[] folders){
		this.observer = observer;
		
		runs = new Hashtable <Thread, String>();
		filesWanted = new Vector<File>();
		final FileNameFilter nameFilter = new FileNameFilter(fileNames);
		
		try {
			runSeeksFromFolders(folders, nameFilter);
		} catch (Exception e) {
			e.printStackTrace();
			String message = Messages.getString("FileFinder.ErrorOnStartByName_ErrMsg_Part1") //$NON-NLS-1$
				+nameFilter.getSearchItem()
				+Messages.getString("FileFinder.ErrorOnStartByName_ErrMsg_Part2") +e; //$NON-NLS-1$
			cancelAllJobs(message);
		}		
	}	
	
	/**
	 * Seeks given folders with given file filter.
	 * @param folders Folder name array.
	 * @param typeFilter File type filter.
	 */
	private void runSeeksFromFolders(
			final String[] folders,
			final IFileFinderFileFilter typeFilter)  {

		final FileFilter dirFilter = new DirFilter();
			
		int currentDepth = 1;

		// Check number of folders and set steps to monitor by them
		setMonitorSteps(folders, dirFilter, typeFilter);

		boolean isJobStarted = false;
		
		//Looping through folders, staring new Thread for each main level folder
		for (int i = 0; i < folders.length; i++) {
			
			final File dir = new File(folders[i]);
			if (dir.exists() && dir.isDirectory()) {
				isJobStarted = true;
				seekFilesFromFolderWithThread(typeFilter, dirFilter, dir, currentDepth);
			} else {
				printUtility.println(Messages.getString("FileFinder.UnableToSeek_ErrMsg_Part1") +typeFilter.getSearchItem() +Messages.getString("FileFinder.UnableToSeek_ErrMsg_Part2") + folders[i] //$NON-NLS-1$ //$NON-NLS-2$
						+ Messages.getString("FileFinder.UnableToSeek_ErrMsg_Part3")); //$NON-NLS-1$
			}

		}

		// If there is no jobs to start, just telling observers that everything
		// is done and returning empty list
		if (!isJobStarted) {
			observer.completed(IStatus.OK, filesWanted);
		}

	}
		
	/**
	 * Counts and sets steps to monitor
	 * @param folders Folder name array.
	 * @param dirFilter Directory filter.
	 * @param typeFilter File type filter.
	 */
	private void setMonitorSteps(String[] folders, FileFilter dirFilter,
			IFileFinderFileFilter typeFilter) {
		
		if(isCanceled()){
			return;
		}		

		//Count how many threads will be created, number of threads depends on folders given and
		//depth for new threads under those folders
		int dirCount = 0;
		for (int j = 0; j < folders.length; j++) {
			File dir = new File(folders[j]);
			if(dir.exists() && dir.isDirectory()){
				dirCount++;//One added for folder, and rest added for folders under that folder
				dirCount += getDirCount(dirFilter, dir);
			}
		}
		
		observer.beginTask(Messages.getString("FileFinder.BegingTask_Msg_Part1") +typeFilter.getSearchItem() +Messages.getString("FileFinder.BegingTask_Msg_Part2"), dirCount /*IFileFinderObserver.STEPS*/); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets sub directory count from the given directory.
	 * @param dirFilter Directory filter to be applied for query.
	 * @param dir Directory to check for sub directories. 
	 * @return count of sub directories
	 */
	private int getDirCount(FileFilter dirFilter, File dir) {
		
		if(isCanceled()){
			return 0;
		}		
		if(dir.isDirectory()){
			File [] dirs = dir.listFiles(dirFilter);
			return dirs.length;
		}
		return 0;
	}
	
	/**
	 * Creates threads for finding files according directory and file filter.
	 * @param fileTypeFilter File type filter.
	 * @param dirFilter Directory name filter.
	 * @param dir Directory to start search from.
	 * @param currentDepth Current depth of search.
	 */
	private void seekFilesFromFolderWithThread(final IFileFinderFileFilter fileTypeFilter, final FileFilter dirFilter,
			final File dir, final int currentDepth) {
		
		if(isCanceled()){
			return;
		}
		if(dir.exists() && dir.isDirectory()){
			// Creating anonymous thread object
			Thread seekFilesRunnable = new Thread(){
				public void run(){
					try {
						Collection<File> paths = seekFilesFromFolder(fileTypeFilter, dirFilter, dir, currentDepth);
						if(paths != null){
							filesWanted.addAll(paths);
						}
					} catch (Exception e) {
						e.printStackTrace();
						String message = Messages.getString("FileFinder.ErrorOnSeek_ErrMsg_Part1") //$NON-NLS-1$
							+fileTypeFilter.getSearchItem()
							+Messages.getString("FileFinder.ErrorOnSeek_ErrMsg_Part2")  //$NON-NLS-1$
							+dir.getAbsolutePath() 
							+Messages.getString("FileFinder.ErrorOnSeek_ErrMsg_Part3") +e; //$NON-NLS-1$
						cancelAllJobs(message);
					}finally{
						removeJob(this);						
					}
				}

			};
			//
			// Setting parameters and starting thread
			//
			seekFilesRunnable.setPriority(Thread.MIN_PRIORITY);
			
			// Showing a visible message has to be done in its own thread
			// in order not to cause invalid thread access
			addJob(seekFilesRunnable, dir.getAbsolutePath());

			//If job is started from UI Thread, Thread is started with Display
			seekFilesRunnable.start();
		}
	}

	/**
	 * Checks if finder job canceled
	 * @return <code>true</code> if canceled, <code>false</code> otherwise.
	 */
	private boolean isCanceled() {
		boolean canceled = observer.isCanceled();
		return canceled;
	}
	
	/**
	 * Finds files according directory and file filter.
	 * @param fileTypeFilter File type filter.
	 * @param dirFilter Directory name filter.
	 * @param dir Directory to start search from.
	 * @param currentDepth Current depth of search.
	 * @return Collection of files found.
	 * @throws Exception
	 */
	private Collection<File> seekFilesFromFolder(final IFileFinderFileFilter fileTypeFilter, FileFilter dirFilter ,
			final File dir, int currentDepth) throws Exception{		
		
		//If canceled or not a existing directory, just returning
		if(isCanceled() || ! dir.isDirectory() || !dir.exists()){
			return null;
		}		
		
		Thread.yield(); //Trying to give other Threads chance to do they things so seek don't get all resources 
	
		//Updating monitor status with progress percentages
		String msg = Messages.getString("FileFinder.ProgressMsg_Part1") +fileTypeFilter.getSearchItem() + Messages.getString("FileFinder.ProgressMsg_Part2") +dir.getAbsolutePath(); //$NON-NLS-1$ //$NON-NLS-2$
		if(currentDepth <= FOLDERS_DEPTH_FOR_MONITORING){//monitor steps is count by number of folders in given folders
			synchronized (stepsCompleted) {			
				observer.progress(stepsCompleted.intValue(), msg);
				stepsCompleted++;	
			}
		}
		
		//Add files with wanted type to list from this folder
		Collection<File> foundFiles = new Vector<File>();				
		File [] files = dir.listFiles(fileTypeFilter);
		for (int i = 0; i < files.length; i++) {
			foundFiles.add(files[i]);
		}
		
		//When all wanted files listed, checking all folders under given folder
		File [] dirs = dir.listFiles(dirFilter);
		int nextDepth = currentDepth + 1;

		for (int i = 0; i < dirs.length; i++) {
			//If parameters says that we create a new Thread to seek folders with this deepness
			if(currentDepth < maxDepthForNewThreads){
				seekFilesFromFolderWithThread(fileTypeFilter, dirFilter, dirs[i], nextDepth);
			}
			//Otherwise we already have as many Threads we want, just keep looking folders recursively
			else{
				Collection<File> paths = seekFilesFromFolder(fileTypeFilter, dirFilter, dirs[i], nextDepth);
				if(paths != null){
					foundFiles.addAll(paths);
				}
			}			
		}		
		
		return foundFiles;
	}

	/**
	 * Filter that accepts only files with given file type.
	 */
	private class FileTypeFilter implements IFileFinderFileFilter{
		
		private String fileType = null;

		/**
		 * Constructor.
		 * @param fileType File types to be accepted.
		 */
		public FileTypeFilter(String fileType){
			this.fileType = fileType.toLowerCase();			
		}
		
		/* (non-Javadoc)
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(fileType) ? true : false;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.s60tools.util.resource.IFileFinderFileFilter#getSearchItem()
		 */
		public String getSearchItem() {			
			return fileType;
		}	
	}		
	
	/**
	 * Filter accepts only files with given file name.
	 */
	private class FileNameFilter implements IFileFinderFileFilter{
				
		private String[] fileNames = null;

		/**
		 * Constructor
		 * @param fileNames Filenames to be accepted.
		 */
		public FileNameFilter(String fileNames[]){
			this.fileNames = fileNames;	
		}
		
		/* (non-Javadoc)
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		public boolean accept(File file) {
			for (int i = 0; i < fileNames.length; i++) {
				if(file.getName().equalsIgnoreCase(fileNames[i])){
					return true;
				}
			}
			return false;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.s60tools.util.resource.IFileFinderFileFilter#getSearchItem()
		 */
		public String getSearchItem() {		
			StringBuffer files = new StringBuffer();
			String separator = ", "; //$NON-NLS-1$
			
			for (int i = 0; i < fileNames.length; i++) {
				files.append(fileNames[i]);
				files.append(separator);
			}
			if(files.length() > 0){
				return files.substring(0, files.length()-separator.length());
			}
			
			return files.toString();
		}	
	}		
	
	/**
	 * Filter that accepts only directories
	 */
	private class DirFilter implements FileFilter{
		
		public boolean accept(File file) {
			return file.isDirectory();
		}	
	}		
	
	/**
	 * Cancel all ongoing jobs. 
	 */
	private void cancelAllJobs(String message){
		printUtility.println(message, IConsolePrintUtility.MSG_ERROR);
		observer.interrupted(message);		
	}
	
	/**
	 * Removes a job from run list and if there is no runs left, 
	 * returning completed to observer if no jobs left to be done
	 * @param runnable Thread to be removed from run list.
	 */
	private void removeJob(Thread runnable) {
		synchronized (runs) {
			if (runnable != null) {
				runs.remove(runnable);
			}
			if (runs.size() == 0) {
				if (isCanceled()) {
					observer.completed(IStatus.CANCEL, filesWanted);
				} else {
					// When there are no jobs left to be done, notifying observer
					// that everything is done with files found.
					observer.completed(IStatus.OK, filesWanted);
				}
			}
		}// synchronized
	}

	/**
	 * Adds a job to run list
	 * @param seekFilesRunnable Thread to be added.
	 * @param dirNameToSeek Directory name from which the thread seeks files.
	 */
	private void addJob(Thread seekFilesRunnable, String dirNameToSeek) {
		synchronized (runs) {
			runs.put(seekFilesRunnable, dirNameToSeek);
		}// synchronized
	}
}
