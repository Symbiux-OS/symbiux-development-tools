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

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.nokia.s60tools.util.debug.DbgUtility;
import com.nokia.s60tools.util.internal.Messages;

/**
 * Miscellaneous utility methods related to files.
 */
public class FileUtils {
	
	/**
	 * Constant for UTF-8 encoding type.
	 */
	public static final String ENCODING_TYPE_UTF_8 = "UTF-8"; //$NON-NLS-1$
	/**
	 * Constant for UTF-8 encoding type.
	 */
	public static final String ENCODING_TYPE_UTF_16 = "UTF-16"; //$NON-NLS-1$
	
	
	/**
	 * Queries user for an external file to be opened, and opens it.
	 * @param projectName Name of the project to open file into.
	 * @throws CoreException
	 * @throws PartInitException
	 * @throws FileNotFoundException 
	 */
	public static void openExternalFileDialog(String projectName) throws CoreException, PartInitException, FileNotFoundException {
	   IWorkbench workbench = PlatformUI.getWorkbench();			   
	   IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
	   
	   Shell shell = window.getShell();
	   String fileAbsolutePathName = new FileDialog(shell, SWT.OPEN).open();
	   if (fileAbsolutePathName == null)
	      return;
	
	   openExternalFileImpl(fileAbsolutePathName, workbench, projectName);
	}

	/**
	 * Opens the external file given as parameter.
	 * @param fileAbsolutePathName Absolute path name to the file to be opened.
	 * @param projectName Name of the project to open file into.
	 * @throws CoreException
	 * @throws PartInitException
	 * @throws FileNotFoundException 
	 */
	public static void openExternalFile(String fileAbsolutePathName, String projectName) throws CoreException, PartInitException, FileNotFoundException {		
	   IWorkbench workbench = PlatformUI.getWorkbench();			   
	   openExternalFileImpl(fileAbsolutePathName, workbench, projectName);
	}

	/**
	 * Opens the external file given as parameter.
	 * @param fileAbsolutePathName Absolute path name to the file to be opened.
	 * @param workbench Workbench to be used.
	 * @param projectName Name of the project to open file into.
	 * @throws CoreException
	 * @throws PartInitException
	 * @throws FileNotFoundException 
	 */
	private static void openExternalFileImpl(String fileAbsolutePathName, IWorkbench workbench, String projectName) throws CoreException, PartInitException, FileNotFoundException {
		
		File f = new File(fileAbsolutePathName);
		if(!f.exists()){
			throw new FileNotFoundException(Messages.getString("FileUtils.NonExistingExternalFile_ErrMsg") + fileAbsolutePathName); //$NON-NLS-1$
		}
		
	   IPath location = new Path(fileAbsolutePathName);		   		   
	   IWorkspace ws = ResourcesPlugin.getWorkspace();
	   IProject project = ws.getRoot().getProject(projectName); //$NON-NLS-1$
		   
	   if (!project.exists())
	      project.create(null);
	   if (!project.isOpen())
	      project.open(null);
	   IFile file = project.getFile(location.lastSegment());
	   file.createLink(location, IResource.REPLACE, null);
	   final FileEditorInput editorInput = new FileEditorInput(file);
	   
	   IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();	
	   IWorkbenchPage page = window.getActivePage();
	   if (page != null){
		   IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(file.getName());
		   page.openEditor(editorInput, desc.getId());
	   }
	}

	/**
	 * Loads the contents of the given file into memory. 
	 * @param filePathName Absolute path name for the file.
	 * @return StringBuffer instance having the contents of the file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static StringBuffer loadDataFromFile(String filePathName) throws FileNotFoundException, IOException {
		StringBuffer dataBuffer = new StringBuffer();
		File f = new File(filePathName);
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream br = new BufferedInputStream(fis);
		int available = fis.available();
		for(int readLoopCount=1; available > 0;readLoopCount++){
			byte[] tmpByteArr = new byte[available];
			int readBytes = br.read(tmpByteArr, 0, available);
			if(readBytes == -1){
				break;
			}
			else if(readBytes != available){
				br.close();
				fis.close();
				throw new IOException(Messages.getString("FileUtils.FAILED_TO_LOAD_DATA")  //$NON-NLS-1$
						              + filePathName 
						              + Messages.getString("FileUtils.LESS_BYTES_THAN_EXPECTED")); //$NON-NLS-1$
			}
			dataBuffer.append(new String(tmpByteArr, 0, readBytes));
			available = fis.available();
		}
		br.close();
		fis.close();
		return dataBuffer;
	}

	/**
	 * Save text to file. If the folder where file is located does not exist, creating one.
	 * @param filename file name with full path
	 * @param file file contents
	 * @throws IOException
	 */
	public static void writeToFile( String fileName, String fileContents ) throws IOException {		
		writeToFile(fileName, fileContents, null);
	}		
	
	/**
	 * Save text to file. If the folder where file is located does not exist, creating one.
	 * Sets file encoding type.
	 * @param filename file name with full path
	 * @param file file contents
	 * @param encoding encoding type e.g. {@link FileUtils.ENCODING_TYPE_UTF_8}  
	 * @throws IOException
	 */
	public static void writeToFile( String fileName, String fileContents, String encoding ) throws IOException {		
		BufferedWriter out = null;
		try {
						
			File file = new File(fileName);
			File parent = file.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
			
			//If encoding is set
			if(encoding != null){
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding));
			}
			else{
				out = new BufferedWriter(new FileWriter(fileName));
			}
			
			out.write(fileContents);			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(out != null){
				out.close();				
			}
		}
	}	
	
	/**
	 * Gets logical drive for the given file object pointing to a file or path name.
	 * @param absolutePathNameFileObject File object containing a directory or a file path name..
	 * @return logical drive for the file or path name including colon and file separator (e.g. C:\)
	 */
	public static String getLogicalDrive(File absolutePathNameFileObject) {
		File parentFile = absolutePathNameFileObject;
		while(parentFile.getParentFile() != null){
			parentFile = parentFile.getParentFile();
		}
		return parentFile.getAbsolutePath();
	}

	/**
	 * Gets logical drive for the given file or path name string. The given path should be absolute.
	 * If given path is relative (i.e. does not contain logical drive in path name), a new file
	 * object is created which adds current directory with current logical drive before the relative path.
	 * @param absolutePathName Absolute path name string to a directory a file containing logical drive
	 * @return logical drive for the file or path name including colon and file separator (e.g. C:\)
	 */
	public static String getLogicalDrive(String absolutePathName) {
		return getLogicalDrive(new File(absolutePathName));
	}

	/**
	 * Checks if the given path starts with drive letter sequence e.g. with C:\ and contains
	 * at least a single character after the drive letter start sequence.
	 * Check is done with the following regular expression <code>[a-zA-Z]:[\\/].+</code> that 
	 * e.g. matches the following paths
	 * - A:\my\example\path1, c:\my\example\path1, Z:/my\example\path3, and r:/my/example/path1
	 * but does not match the following ones:
	 * - :\my\example\path2, \my\example\path, z:\, R:/ 
	 * If regular expression matches the first three letters are removed from the path.
	 * @param pathName Path name to check for drive letter removal. Path can be absolute
	 *                 or relative, and does not have point to valid file or directory. 
	 * @return Source path without drive letter, if it existed previously.
	 */
	public static String removeDriveLetterPortionFromPathIfExists(String pathName) {
		String pathWithDriveLetterRegExp = "[a-zA-Z]:[\\\\/].+"; //$NON-NLS-1$
		if(pathName.matches(pathWithDriveLetterRegExp)){
			// In case of match skipping first three letters
			return pathName.substring(3); 		
		}
		return pathName;
	}
	
  	/**
	 * Replaces all characters that cannot be used in Win32 directory and file names.
	 * @param stringToBeChecked string to be checked for illegal characters.
	 * @param replaceChar replacement character for illegal filename characters found
	 * @return string from which all illegal filename characters are replaced
	 */
	public static String removeIllegalWin32FilenameCharactersFromString(String stringToBeChecked, char replaceChar) {
		// Set of characters that cannot be part of a file or directory name (list was got from win32 error message) 
		final char illegalFilenameCharsArr[] = { '\\', '/', ':', '*', '?', '"', '<', '>', '|' };
		// Checking that replacement character itself is not illegal character
		if(new String(illegalFilenameCharsArr).indexOf(replaceChar) != -1){
			throw new IllegalArgumentException("Tried to replace illegal Win32 filename character with also illegal character" + ": " + replaceChar);
		}
		String resultString = new String(stringToBeChecked);
		if(resultString != null){
			for (int i = 0; i < illegalFilenameCharsArr.length; i++) {
				char ch = illegalFilenameCharsArr[i];				
				resultString = resultString.replace(ch, replaceChar);
			}		
		}
		return resultString;
	}
	
}
