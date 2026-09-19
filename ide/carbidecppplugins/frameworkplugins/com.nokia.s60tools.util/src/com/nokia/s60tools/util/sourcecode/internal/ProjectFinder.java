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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;

import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.internal.Messages;
import com.nokia.s60tools.util.sourcecode.CannotFoundFileException;
import com.nokia.s60tools.util.sourcecode.IProjectFinder;

/**
 * Utility for finding mmp file for given source path, and
 * bld.inf file for given mmp file.
 */
public class ProjectFinder implements IProjectFinder {

	//
	// Constants for file searching
	//
	private final String MMP_FILE_PATTERN = ".mmp"; //$NON-NLS-1$
	private final String MMH_FILE_PATTERN = ".mmh"; //$NON-NLS-1$
	private final String INF_FILE_PATTERN = "bld.inf"; //$NON-NLS-1$
	private final String SOURCE_SEARCH_STRING = "source"; //$NON-NLS-1$
	private final String INCLUDE_SEARCH_STRING = "#include"; //$NON-NLS-1$
	private final String GROUP_FOLDER_NAME = "group"; //$NON-NLS-1$
	private final String PRJ_MMPFILES_STR = "PRJ_MMPFILES"; //$NON-NLS-1$
	private final String COMMENT_STR = "//"; //$NON-NLS-1$
	private final String SEARCH_LEVEL1_STR = "../"; //$NON-NLS-1$
	private final String SEARCH_LEVEL2_STR = "..\\"; //$NON-NLS-1$
	//
	// Constants for search level dephts
	//	
	private final int BLD_FILE_SEARCH_LEVEL = 2;
	private final int MMP_FILE_SEARCH_LEVEL = 4;

	/**
	 * Console utility used to report error, warnings, and info message. 
	 */
	private IConsolePrintUtility printUtility;
	/**
	 * Progress monitor used to check cancel status of the calling job.
	 */
	private IProgressMonitor monitor = null;
	
	/**
	 * Constructor.
	 * @param printUtility Print utility used to report error, warnings, and info messages.
	 *                     If set to <code>null</code> no reporting is done.
	 * @param monitor Progress monitor used to check cancel status of the calling job.
	 *                Cannot be <code>null</code>.
	 */
	public ProjectFinder(IConsolePrintUtility printUtility, IProgressMonitor monitor){
		this.printUtility = printUtility;
		this.monitor = monitor;
	}	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.IProjectFinder#findBLDINFFile(java.lang.String)
	 */
	public String findBLDINFFile(String MMPFilePath) throws CannotFoundFileException {
		File file = new File(MMPFilePath);
		File parentDir = file.getParentFile();
		String searchString = fileNameWithoutExtension(file.getName());
		
		String result = searchStringFromDirectory(parentDir, searchString,
						null, false, true, BLD_FILE_SEARCH_LEVEL, null);
		if(result == null) {
			if(isCanceled()){
				return null;
			}
			else{
				String errMsg = Messages.getString("ProjectFinder.BldInfNotFound_Exception_Msg"); //$NON-NLS-1$
				if(printUtility != null){
					printUtility.println(errMsg, IConsolePrintUtility.MSG_ERROR);					
				}
				throw new CannotFoundFileException(errMsg + MMPFilePath); 
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.IProjectFinder#findMMPFile(java.lang.String)
	 */
	public String findMMPFile(String sourceFilePath) throws CannotFoundFileException {
		String checkedSourceFilePath = checkSourceFilePath(sourceFilePath);
		if(checkedSourceFilePath == null) {
			String errMsg = Messages.getString("ProjectFinder.SourceFileNotFound_Exception_Msg"); //$NON-NLS-1$
			if(printUtility != null){
				printUtility.println(errMsg, IConsolePrintUtility.MSG_ERROR);				
			}
			throw new CannotFoundFileException(errMsg + sourceFilePath); 
		}
		
		File file = new File(checkedSourceFilePath);
		File parentDir = file.getParentFile();
		String searchString = file.getName();
		
		String result = searchStringFromDirectory(parentDir, searchString,
						SOURCE_SEARCH_STRING, true, true, MMP_FILE_SEARCH_LEVEL, null);
		if(result == null) {
			if(isCanceled()){
				return null;
			}
			else{
				String errMsg = Messages.getString("ProjectFinder.MmpFileNotFound_Exception_Msg"); //$NON-NLS-1$
				if(printUtility != null){
					printUtility.println(errMsg, IConsolePrintUtility.MSG_ERROR);					
				}
				throw new CannotFoundFileException(errMsg + checkedSourceFilePath); 
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.IProjectFinder#getRootLevelFromBldInf(java.lang.String)
	 */
	public int getRootLevelFromBldInf(String bldInfFilePath) throws IOException {
		// Getting variables.
		FileInputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		int rootLevelCount = 0;

		try {
			in = new FileInputStream(bldInfFilePath);
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);

			// Getting root level count.
			rootLevelCount = getRootLevelFromBufferedReader(br, bldInfFilePath);
			
			// Adding one to root level count if root is group folder.
			if(isRootLevelGroupFolder(bldInfFilePath, rootLevelCount)){
				rootLevelCount++;
			}
			// Closing resources.
		} finally {
			if (in != null) {
				in.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return rootLevelCount;
	}

	/**
	 * Reads lines from reader, parses lines for references to upper directories
	 * and returns how many levels bld.inf file is from root.
	 * @param reader BufferedReader from which data is used to get root level.
	 * @param bldInfFilePath Path to bld.inf file.
	 * @return How far project root is from bld.inf.
	 * @throws IOException if exception occurs when handling the bld.inf file.
	 */
	private int getRootLevelFromBufferedReader(BufferedReader reader, String bldInfFilePath) throws IOException{
		int rootLevelCount = 0;
		String searchString1 = SEARCH_LEVEL1_STR;
		String searchString2 = SEARCH_LEVEL2_STR;
		String line = null;

		// Going through file line by line.
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			// Excluding comment lines.
			if(line.startsWith(COMMENT_STR)){
				continue;
			}

			// Checking if line contains reference to upper directory level.
			while (line.startsWith(searchString1)) {
				// "../" or its multiple was found from beginning of line. Creating value for next comparison.
				rootLevelCount++;
				searchString1 += SEARCH_LEVEL1_STR;
				searchString2 += SEARCH_LEVEL2_STR;
			}
			while (line.startsWith(searchString2)) {
				// "..\" or its multiple was found from beginning of line. Creating value for next comparison.
				rootLevelCount++;
				searchString1 += SEARCH_LEVEL1_STR;
				searchString2 += SEARCH_LEVEL2_STR;
			}
			
			// Checking if include line contains reference to upper directory level.
			if(line.startsWith(INCLUDE_SEARCH_STRING)){
				while (line.contains(searchString1)) {
					// "../" or its multiple was found. Creating value for next comparison.
					rootLevelCount++;
					searchString1 += SEARCH_LEVEL1_STR;
					searchString2 += SEARCH_LEVEL2_STR;
				}
				while (line.contains(searchString2)) {
					// "..\" or its multiple was found. Creating value for next comparison.
					rootLevelCount++;
					searchString1 += SEARCH_LEVEL1_STR;
					searchString2 += SEARCH_LEVEL2_STR;
				}
			}
		}
		
		return rootLevelCount;
	}
	
	/**
	 * Checks if current root level folder is named as "group".
	 * @param bldInfFilePath Bld.inf file path that is checked.
	 * @param rootLevelCount How far project root is from bld.inf.
	 * @return True if root folder name is group. False otherwise. 
	 */
	private boolean isRootLevelGroupFolder(String bldInfFilePath, int rootLevelCount) {
		File file = new File(bldInfFilePath);
		
		// Removing file name.
		file = file.getParentFile();

		// Getting to correct directory by removing correct amount of folders names.
		for(int i = 0;i < rootLevelCount;i++){
			file = file.getParentFile();
		}
		
		String dirName = file.getName();
		
		// Adding one to rootLevelCount if directory name is group.
		if(dirName.equalsIgnoreCase(GROUP_FOLDER_NAME)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks that file really exists and that file name is correct.
	 * It returns correct file when case of file name doesn't match.
	 * @param sourceFilePath Source file to check.
	 * @return Checked source file or null if file was not found.
	 */
	private String checkSourceFilePath(String sourceFilePath) {
		File file = new File(sourceFilePath);
		
		String fileName = file.getName();
		
		// Checking that directory exists.
		File directory = file.getParentFile();
		if(!directory.exists()){
			return null;
		}
		
		// Go though files in directory. Checks and returns file with correct case.
		File[] files = directory.listFiles();
		for(File tmpFile : files){
			if(tmpFile.getName().equalsIgnoreCase(fileName)){
				return tmpFile.getAbsolutePath();
			}
		}
		
		return null;
	}
	/**
	 * Returns file name without file extension.
	 * @param fileName File name which is modified.
	 * @return File name without extension or same string.
	 */
	private String fileNameWithoutExtension(String fileName) {
		if(fileName.indexOf('.') > 0) {
			return fileName.substring(0, fileName.lastIndexOf('.'));
		}
		return fileName;
	}

	/**
	 * Searches specific directory when searching searchString.
	 * First step is to search string from files.
	 * Then subdirectories are search starting from group folder.
	 * Finally upper directories are searched.
	 * @param directoryName Directory to be searched.
	 * @param searchString String that is searched for.
	 * @param searchBySource True if source string is searched from mmp file.
	 * @param filesToSearch From which files string is searched.
	 * @param searchSubFolders <code>True</code> if subfolders are to be searched.
	 * @param searchLevel Defines how many levels upwards search will be extended.
	 * @param excludedFolder Excludes folder from search. Used to prevent searching same folder.
	 * @return First file that contains searchString and has fileExtension or <code>null</code>.
	 */
	private String searchStringFromDirectory(File directoryName, String searchString, String secondarySearchString,
				boolean searchMmpFile, boolean searchSubFolders, int searchLevel, File excludedFolder) {
		// Returning null if search is canceled.
		if(isCanceled()){
			return null;
		}
		
		File[] files = directoryName.listFiles();
		if(files == null){
			// Returning null if file isn't correct directory.
			return null;
		}
		ArrayList<File> directories = new ArrayList<File>();
		File groupDirectory = null;
		
		// First going through files.
		for(File file : files) {
			// Adding directory to separate list for later use.
			if(file.isDirectory()){
				// Saving group directory name so that in can be checked first.
				if(file.getName().equalsIgnoreCase(GROUP_FOLDER_NAME)){
					groupDirectory = file;
				}
				else {
					directories.add(file);
				}
			}
			else {
				String fileName = checkFile(searchString, file, secondarySearchString, searchMmpFile);
				if(fileName != null) {
					// File was found. Returning it.
					return fileName;
				}
			}
		}
		
		// Checking for group directory.
		if(groupDirectory != null && groupDirectory != excludedFolder){
			// Searching recursively sub directories.
			String fileName = searchStringFromDirectory(groupDirectory, searchString,
					secondarySearchString, searchMmpFile, searchSubFolders, 0, null);
			if(fileName != null) {
				// File was found. Returning it.
				return fileName;
			}			
		}
		
		// Then going through other sub directories.
		for(File file : directories){
			// Handling directories
			if(file.isDirectory() && searchSubFolders) {
				// Checking if folder is excluded from search. 
				if(excludedFolder == null || !excludedFolder.equals(file)) {
					// Searching recursively sub directories.
					String fileName = searchStringFromDirectory(file, searchString,
							secondarySearchString, searchMmpFile, searchSubFolders, 0, null);
					if(fileName != null) {
						// File was found. Returning it.
						return fileName;
					}
				}
			}
		}
		
		// Searching upper directories.
		if(searchLevel > 0) {
			return searchStringFromDirectory(directoryName.getParentFile(), searchString,
					secondarySearchString, searchMmpFile, searchSubFolders, searchLevel - 1, directoryName);
		}
		// File was not found. Returning null.
		return null;
	}

	/**
	 * Checks file based on file name and returns path to file if search 
	 * string was found from the file.
	 * @param searchString Search string.
	 * @param file         File object to check for.
	 * @param searchBySource Searching based on mmp file if set to <code>true</code>.
	 * @param searchMmpFile Search based on source  file if set to <code>true</code>.
	 * @return Path if correct file was found. Otherwise null.
	 */
	private String checkFile(String searchString, File file,
			String secondarySearchString, boolean searchMmpFile) {
		// Getting variables.
		String fileName = file.getName();
		
		try {
			if(searchMmpFile){
				// Checking .mmp file.
				if(fileNameMatches(fileName, MMP_FILE_PATTERN)) {
					// File name matches with pattern.
					if(searchFile(file, searchString, secondarySearchString, null)) {
						// String was found from file. Returning file name.
						return file.getPath();
					}
				}
				// Checking .mmh file.
				if(fileNameMatches(fileName, MMH_FILE_PATTERN)) {
					// File name matches with pattern.
					if(searchFile(file, searchString, secondarySearchString, null)) {
						// String was found from mmh file. Searching mmp file for this file.
						return searchMmpForMmhFile(file);
					}
				}		
			}
			else{
				// Checking bld.inf file.
				if(fileNameMatches(fileName, INF_FILE_PATTERN)) {
					// File name matches with pattern.
					if(searchFile(file, searchString, secondarySearchString, PRJ_MMPFILES_STR)) {
						// String was found from file. Returning file name.
						return file.getPath();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(printUtility != null){
				String errMsg = Messages.getString("ProjectFinder.UnexpectedException_ErrMsg"); //$NON-NLS-1$
				printUtility.println(errMsg + ": " + e.getMessage(), IConsolePrintUtility.MSG_ERROR);					 //$NON-NLS-1$
			}
		}
		return null;
	}

	
	/**
	 * Searching .mmp file that has included .mmh file.
	 * @param file Searching mmp file for this file.
	 * @return Full file path or null if file was not found.
	 */
	private String searchMmpForMmhFile(File file) {
		File parentDir = file.getParentFile();
		String searchString = file.getName();
		// Using include search string as mmh files are included in mmp files.
		return searchStringFromDirectory(parentDir, searchString, INCLUDE_SEARCH_STRING,
				true, true, MMP_FILE_SEARCH_LEVEL, null);
	}

	/**
	 * Checks if filePattern matches to the end of fileName.
	 * @param fileName File name to be checked.
	 * @param filePattern Pattern to which file name is checked.
	 * @return True if end of the file name matches to pattern.
	 */
	private boolean fileNameMatches(String fileName, String filePattern) {
		if(fileName.length() < filePattern.length()) {
			return false;
		}
		String reducedFileName = fileName.substring(fileName.length() - filePattern.length());
		return reducedFileName.equalsIgnoreCase(filePattern);
	}
	
	/**
	 * Searches specific file when searching searchString
	 * @param file File to be searched.
	 * @param searchString String that is searched for.
	 * @param secondarySearchString This String must be in same line as searchString.
	 * @param searchStartString Comparison is started after this string.
	 * @return <code>True</code> if stringString is found.
	 * @throws FileNotFoundException File was not found.
	 * @throws IOException 
	 */
	private boolean searchFile(File file, String searchString, String secondarySearchString,
				String searchStartString) throws FileNotFoundException, IOException{
		FileInputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		boolean returnValue = false;

		try {
			in = new FileInputStream(file);
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);

			String line = null;

			// Starting search when searchStartString is found from stream.
			if(searchStartString != null){
				// Reading lines until searchStartString is found. 
				while ((line = br.readLine()) != null) {
					if(checkLine(line, searchStartString, false)){
						break;
					}
				}				
			}
			// seek wanted text from file line by line
			while ((line = br.readLine()) != null) {
				// Checking if searchString is found from line.
				if (secondarySearchString != null){
					// Checking if secondary search string is found.
					if(checkLine(line, secondarySearchString, false)){
						returnValue = checkLine(line, searchString, false);
					}
				}
				else {
					returnValue = checkLine(line, searchString, false);
				}
				// Stopping search if value was found.
				if(returnValue){
					break;
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return returnValue;
	}
	
	/**
	 * Checking if string is found from line.
	 * @param line String from which searchString is searched.
	 * @param searchString This is searched for.
	 * @param isCaseSensitive True is search is case sensitive.
	 * @return True if searchString was found from line.
	 */
	private boolean checkLine(String line, String searchString, boolean isCaseSensitive){
		// Removing possible white space.
		line = line.trim();
		
		// Excluding comment lines.
		if(line.startsWith(COMMENT_STR)){
			return false;
		}
		
		// Getting pattern that is searched.
		Pattern pattern;
		if(isCaseSensitive){
			pattern = Pattern.compile(searchString);
		}
		else{
			pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
		}
		Matcher matcher = pattern.matcher(line);
		
		return matcher.find();
	}

	/**
	 * Checks if search is canceled.
	 * @return True if monitor is not null and search is canceled. Otherwise false.
	 */
	private boolean isCanceled() {
		if(monitor != null){
			return monitor.isCanceled();
		}
		return false;
	}
}
