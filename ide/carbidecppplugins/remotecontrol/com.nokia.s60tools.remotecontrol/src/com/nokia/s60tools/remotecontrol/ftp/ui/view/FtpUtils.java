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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.IFTPService;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.ViewContentProvider.OPERATION;
import com.nokia.s60tools.remotecontrol.job.DeleteDirJob;
import com.nokia.s60tools.remotecontrol.job.DeleteFileJob;
import com.nokia.s60tools.remotecontrol.job.FileDownloadJob;
import com.nokia.s60tools.remotecontrol.job.FileUploadJob;
import com.nokia.s60tools.remotecontrol.job.MakeDirJob;
import com.nokia.s60tools.remotecontrol.job.PasteJob;
import com.nokia.s60tools.remotecontrol.job.RenameJob;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.ui.dialogs.ConfirmDeleteDialog;
import com.nokia.s60tools.remotecontrol.ui.dialogs.ConfirmReplaceDialog;
import com.nokia.s60tools.remotecontrol.ui.dialogs.FolderNameDialog;
import com.nokia.s60tools.remotecontrol.ui.dialogs.RemoteControlMessageBox;
import com.nokia.s60tools.remotecontrol.ui.dialogs.RenameDialog;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Utility class for file transfer operations
 */
public class FtpUtils {
	
	/**
	 * Name for rename file dialog.
	 */
	private static final String RENAME_FILE_DLG_NAME = Messages.getString("FtpUtils.RenameFile_DlgName"); //$NON-NLS-1$
	/**
	 * Name for rename folder dialog.
	 */
	private static final String RENAME_FOLDER_DLG_NAME = Messages.getString("FtpUtils.RenameFolder_DlgName"); //$NON-NLS-1$
	
	/**
	 * Constant timeout used for listing files/folders.
	 */
	private static final int LIST_CONTENTS_TIMEOUT = 3000;
	
	/**
	 * Download file and change it's name
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 */
	static public void downloadFileAs(TableViewer viewer, ViewContentProvider contentProvider) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
			
		if(selectedObject == null || !FtpFileObject.class.isInstance(selectedObject)){
			// Nothing selected or selection is not file
			return;
		}
		
		FtpFileObject ftpObject = (FtpFileObject)selectedObject;
		
		// Show Save as dialog to user
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		FileDialog fileDialog = new FileDialog(sh, SWT.SAVE);
		fileDialog.setFileName(ftpObject.getName());
		fileDialog.setText(Messages.getString("FtpUtils.DownloadAs_DialogText")); //$NON-NLS-1$
		// Get user selected folder
		String destFile = fileDialog.open();
		
		if (destFile == null) {
			// User is canceled dialog
			return;
		}
		
		// Full path for remote file
		String currentDirectory = getCurrentPath(contentProvider);
		String remoteFile = currentDirectory + ftpObject.getName();
		String destFileName = destFile.substring(destFile.lastIndexOf(File.separator) + File.separator.length());
		
		// Remember last used folder
		String destFolder = destFile.substring(0, destFile.lastIndexOf(File.separator));
		RCPreferences.setDownloadLocation(destFolder);
		destFolder = addFileSepatorToEnd(destFolder);
		
		FileDownloadJob job = null;
		boolean canWrite = true;
		
		if (RCPreferences.getDownloadConfirm()) {
			// Confirm replace from user if file already exists
			File file = new File(destFile);
			if (file.exists()) {				
				// Show confirmation dialog
				ConfirmReplaceDialog dlg = new ConfirmReplaceDialog(
						sh, destFileName, false,
						ConfirmReplaceDialog.Operation.DOWNLOAD);
				dlg.open();

				int sel = dlg.getSelection();
				switch (sel) {
				case ConfirmReplaceDialog.RENAME_ID:
					// Rename this file
					RenameDialog renameDlg = new RenameDialog(sh, destFileName, true, RENAME_FILE_DLG_NAME);
					if (renameDlg.open() == Dialog.CANCEL){
						// User canceled dialog
						return;
					}
					// Get new file name from dialog. If user canceled dialog the original
					// filename is returned
					destFile = destFolder + renameDlg.getFileName();
					canWrite = true;
					break;
				case IDialogConstants.YES_ID:
					// Replace this file
					canWrite = true;
					break;
				case IDialogConstants.NO_ID:
					// Do not replace this file
					canWrite = false;
					break;
				case IDialogConstants.CANCEL_ID:
					// Cancel operation
					return;
				default:
					break;
				}
			}
		}
		if (canWrite){
			job = new FileDownloadJob(Messages.getString("FtpUtils.Download_File_Job_Name") +ftpObject.getName(), remoteFile, destFile); //$NON-NLS-1$
			job.setPriority(Job.DECORATE);
			job.schedule();
		}
		
	}
	
	/**
	 * Download all selected files
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 */
	@SuppressWarnings("unchecked")
	static public void downloadFiles(TableViewer viewer, ViewContentProvider contentProvider) {
		
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
			
		if(selectedObject == null || !FtpFileObject.class.isInstance(selectedObject)){
			// Nothing selected or selection is not file
			return;
		}
		
		// Show select folder dialog to user
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		
		DirectoryDialog dirDialog = new DirectoryDialog(sh, SWT.OPEN);
		dirDialog.setText(Messages.getString("FtpUtils.Select_Folder_DlgText"));  //$NON-NLS-1$
		dirDialog.setMessage(Messages.getString("FtpUtils.Select_Folder_DlgMsg")); 	 //$NON-NLS-1$
		
		String downloadFilterPath = RCPreferences.getDownloadLocation();
		if (downloadFilterPath == "") { //$NON-NLS-1$
			// Workspace root is used as default folder
			IPath location = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation();
			dirDialog.setFilterPath(location.toString());
		} else {
			// use last used folder
			dirDialog.setFilterPath(downloadFilterPath);
		}
		
		// Get user selected folder
		String destPath = dirDialog.open();
		
		if (destPath == null) {
			// User is canceled dialog
			return;
		}	
		// Remember last used folder
		RCPreferences.setDownloadLocation(destPath);
		destPath = addFileSepatorToEnd(destPath);
				
		// Full path for remote file
		String currentDirectory = getCurrentPath(contentProvider);
		
		IFtpObject ftpObject = null;
		String remoteFile = null;
		String destFile = null; 
		FileDownloadJob job = null;
		
		// Download files
		Iterator iter = selection.iterator();
		boolean canWrite;
		boolean replaceAll = false;
		while (iter.hasNext()) {
			canWrite = true;
			ftpObject = (IFtpObject) iter.next();
			if (FtpFileObject.class.isInstance(ftpObject)) {
				// Folders are not downloaded
				// Full path for destination file
				destFile = destPath + ftpObject.getName();
				remoteFile = currentDirectory + ftpObject.getName();
				
				if (RCPreferences.getDownloadConfirm()) {
					// Confirm replace from user if file already exists
					File file = new File(destFile);
					if (file.exists() && !replaceAll) {				
						// Show confirmation dialog
						ConfirmReplaceDialog dlg = new ConfirmReplaceDialog(
								sh, ftpObject.getName(), iter.hasNext(),
								ConfirmReplaceDialog.Operation.DOWNLOAD);
						dlg.open();

						int sel = dlg.getSelection();
						switch (sel) {
						case ConfirmReplaceDialog.RENAME_ID:
							// Rename this file
							RenameDialog renameDlg = new RenameDialog(sh, ftpObject.getName(), true, RENAME_FILE_DLG_NAME);
							if (renameDlg.open() == Dialog.CANCEL){
								// User canceled dialog
								return;
							}
							// Get new file name from dialog. If user canceled dialog the original
							// filename is returned
							destFile = destPath + renameDlg.getFileName();
							canWrite = true;
							break;
						case IDialogConstants.YES_ID:
							// Replace this file
							canWrite = true;
							break;
						case IDialogConstants.YES_TO_ALL_ID:
							// Replace all files
							replaceAll = true;
							break;
						case IDialogConstants.NO_ID:
							// Do not replace this file
							canWrite = false;
							break;
						case IDialogConstants.CANCEL_ID:
							// Cancel operation
							return;
						default:
							break;
						}
					}
				}
				if (canWrite){
					job = new FileDownloadJob(Messages.getString("FtpUtils.Download_File_Job_Name") +ftpObject.getName(), //$NON-NLS-1$
							remoteFile, destFile, false); 
					job.setPriority(Job.DECORATE);
					job.schedule();
				}
			}
		}
	}
	
	/**
	 * Downloads file and then opens it.
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 */
	public static void downloadFileAndOpen(TableViewer viewer, ViewContentProvider contentProvider) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
			
		if(selectedObject == null || !FtpFileObject.class.isInstance(selectedObject)){
			// Nothing selected or selection is not file
			return;
		}
		
		FtpFileObject ftpObject = (FtpFileObject)selectedObject;
		
		String tempFolder = RemoteControlActivator.getDefault().getDownloadTempDir();
		File destFolder = new File(tempFolder);
		if(!destFolder.exists()) {
			if(!destFolder.mkdirs()) {
				// Failed to get temp directory.
				RemoteControlConsole.getInstance().println(Messages.getString("FtpUtils.FailedToMakeDir_ConsoleMsg") + //$NON-NLS-1$
						ftpObject.getName() + "'.", //$NON-NLS-1$
							IConsolePrintUtility.MSG_ERROR);
				return;
			}
		}
		
		// Getting paths for source and destination files.
		String currentDirectory = getCurrentPath(contentProvider);
		String remoteFile = currentDirectory + ftpObject.getName();
		String destFile = tempFolder + ftpObject.getName();
		
		// Scheduling download job.
		FileDownloadJob job = new FileDownloadJob(
				Messages.getString("FtpUtils.Download_File_Job_Name") + ftpObject.getName(), //$NON-NLS-1$
				remoteFile,
				destFile,
				true);
		job.setPriority(Job.DECORATE);
		job.schedule();
	}
	
	/**
	 * Upload given source files.
	 * @param uploadDir Directory where files are to be uploaded.
	 * @param sourceFiles Files with path which are to be uploaded.
	 */
	public static void uploadFiles(String uploadDir, String[] sourceFiles) {
		
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		
		// Get file list from remote directory for checking
		// if file already exists
		String[] files = null;
		if (RCPreferences.getUploadConfirm()) {
			IFTPService ftpService = HTIServiceFactory
					.createFTPService(RemoteControlConsole.getInstance());
			try {
				// Get list of files on remote folder
				files = ftpService.listFiles(uploadDir, LIST_CONTENTS_TIMEOUT);
			} catch (Exception e) {
				// Failed to list files. Show message to user and return
				RemoteControlMessageBox message = new RemoteControlMessageBox(
						Messages.getString("FtpUtils.Upload_Fail_ConsoleErrorMsg") //$NON-NLS-1$
						, SWT.ICON_ERROR); 
				message.open();
				e.printStackTrace();
				return;
			}
		}
		
		boolean replaceAll = false;
		String destFileName = null;
			
		for (int j = 0; j < sourceFiles.length; j++) {
			boolean canWrite = true;
			File sourceFile = new File(sourceFiles[j]);
			destFileName = sourceFile.getName();
			
			if (RCPreferences.getUploadConfirm()) {
				// Ask confirmation for replacing files if already exists on
				// target directory
				for (int i = 0; i < files.length; i++) {
					if (!replaceAll && files[i].equals(destFileName)) {
						// No need to check other files
						i = files.length;
						// File exists on remote folder
						// Show confirmation dialog
						ConfirmReplaceDialog dlg = new ConfirmReplaceDialog(
								sh, destFileName,
								(j < sourceFiles.length - 1),
								ConfirmReplaceDialog.Operation.UPLOAD);
						dlg.open();

						int sel = dlg.getSelection();
						switch (sel) {
						case ConfirmReplaceDialog.RENAME_ID:
							// Rename this file
							RenameDialog renameDlg = new RenameDialog(sh, destFileName, true, RENAME_FILE_DLG_NAME);
							if (renameDlg.open() == Dialog.CANCEL){
								// User canceled dialog
								return;
							}
							// Get new file name from dialog. If user canceled dialog the original
							// filename is returned
							destFileName = renameDlg.getFileName();
							canWrite = true;
							break;
						case IDialogConstants.YES_ID:
							// Replace this file
							canWrite = true;
							break;
						case IDialogConstants.YES_TO_ALL_ID:
							// Replace all files
							replaceAll = true;
							break;
						case IDialogConstants.NO_ID:
							// Do not replace this file
							canWrite = false;
							break;
						case IDialogConstants.CANCEL_ID:
							// Cancel operation
							return;
						default:
							break;
						}
					}
				}
			}
			// Upload file
			if (canWrite) {
				String srcFile = null;
				String destFile = null;

				// Full path for destination file
				destFile = uploadDir + destFileName;
				srcFile = sourceFiles[j];

				FileUploadJob job = new FileUploadJob(Messages
						.getString("FtpUtils.Upload_File_Job_Name") //$NON-NLS-1$
						+ " " //$NON-NLS-1$
						+ sourceFile.getName(), srcFile, destFile);
				job.setPriority(Job.DECORATE);
				job.schedule();
			}
		}
	}
	
	/**
	 * Makes directory
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 */
	static public void makeDir(TableViewer viewer, ViewContentProvider contentProvider) {
		
		// Get current folder		
		String parentDir = getCurrentPath(contentProvider);
		
		// Show dialog where user can enter name for the new folder
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		FolderNameDialog folderNameDialog = new FolderNameDialog(sh);
		if (folderNameDialog.open() == IDialogConstants.CANCEL_ID) {
			// User is canceled dialog
			return;
		}	
		// Parse new folder path				
		parentDir = addFileSepatorToEnd(parentDir);
		String folderPath = parentDir + folderNameDialog.getFolderName();
		
		// Start job for making dir
		MakeDirJob job = new MakeDirJob(Messages.getString("FtpUtils.Make_Dir_Job_Name") +" " +folderPath, folderPath);  //$NON-NLS-1$ //$NON-NLS-2$
		job.schedule();
	}
	
	/**
	 * Deletes selected directories and files
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 */
	@SuppressWarnings("unchecked")
	static public void delete(TableViewer viewer, ViewContentProvider contentProvider) {
		
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		
		if(RCPreferences.getDeleteConfirm()) {
			// Show confirmation dialog to user
			Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
			ConfirmDeleteDialog dlg = new ConfirmDeleteDialog(sh);
			dlg.open();
			if (dlg.getSelection() != IDialogConstants.YES_ID) {
				// Do not delete
				return;
			}
		}
		
		// Get current folder		
		String path = getCurrentPath(contentProvider);
		
		// Delete all selected files and folders
		IFtpObject ftpObject = null;
		String dir = null;
		Iterator iter = selection.iterator();
		while (iter.hasNext()) {
			ftpObject = (IFtpObject) iter.next();
			// Add selected file/folder to path
			dir = path + ftpObject.getName();

			if (FtpFolderObject.class.isInstance(ftpObject)) {
				// Start job for deleting folder
				DeleteDirJob job = new DeleteDirJob(
						Messages.getString("FtpUtils.Delete_Folder_Job_Name") + " " + dir, dir); //$NON-NLS-1$ //$NON-NLS-2$
				job.schedule();
			} else if (FtpFileObject.class.isInstance(ftpObject)) {
				// Start job for deleting file
				DeleteFileJob job = new DeleteFileJob(
						Messages.getString("FtpUtils.Delete_File_Job_Name") + " " + dir, dir); //$NON-NLS-1$ //$NON-NLS-2$
				job.schedule();
			}
		}
	}
	
	/**
	 * Add file separator to end of path if missing
	 * @param path Path 
	 * @return Path with file separator
	 */
	public static String addFileSepatorToEnd(String path) {
		if (path != null && !path.endsWith(File.separator)){
			path += File.separator;
		}
		return path;
	}
	
	
	/**
	 * Returns currently selected directory, or null if current directory is root.
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 * @return Currently selected directory, or null if current directory is root.
	 */
	public static String getUploadDirectory(TableViewer viewer, ViewContentProvider contentProvider) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
			
		// Get current folder		
		// Full path for remote file
		String uploadDir = getCurrentPath(contentProvider);
		if(uploadDir == null) {
			return null;
		}
		
		// If selection is folder is it added to upload path
		if (FtpFolderObject.class.isInstance(selectedObject)) {
			FtpFolderObject folderObject = (FtpFolderObject)selectedObject;
			uploadDir += folderObject.getName();
		}	
		uploadDir = addFileSepatorToEnd(uploadDir);
		
		return uploadDir;
	}
	
	/**
	 * Get current path
	 * @param contentProvider Content provider
	 * @return Current path ending with file separator
	 */
	public static String getCurrentPath(ViewContentProvider contentProvider) {
		return addFileSepatorToEnd(contentProvider.getDirectoryPath());
	}
	
	/**
	 * Returns files and folders that are currently selected in viewer.
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 * @return All currently selected file and folder names.
	 */
	@SuppressWarnings("unchecked")
	public static List<IFtpObject> getSelectedObjectNames(TableViewer viewer, ViewContentProvider contentProvider) {
		List<IFtpObject> selectedObjects = new ArrayList<IFtpObject>();
		
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		
		// Get all selected files and folders
		IFtpObject ftpObject;
		Iterator iter = selection.iterator();
		while (iter.hasNext()) {
			ftpObject = (IFtpObject) iter.next();
			
			// Adding files and folders to list.
			if (FtpFileObject.class.isInstance(ftpObject) ||
					FtpFolderObject.class.isInstance(ftpObject)) {
				selectedObjects.add(ftpObject);
			}
		}
		
		return selectedObjects;
	}
	
	/**
	 * Opens file outside of workspace with Default editor, system editor,
	 * or default text editor depending on which is available.
	 * @param filePathName File name and path that is opened.
	 */
	public static void openFile(String filePathName) {
		// Checking that file exists.
		final File file = new File(filePathName);
		if(file == null || !file.exists()){
			RemoteControlConsole.getInstance().println(Messages.getString("FtpUtils.FailedToOpenFile_ErrMsg"), //$NON-NLS-1$
					IConsolePrintUtility.MSG_ERROR);
			return;
		}
		
		//Runnable to open file
		final Runnable runOpen = new Runnable() {
			public void run() {
				try {
					// Getting needed resources and opening file. 
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
					
					IEditorPart part = IDE.openEditorOnFileStore(page, fileStore);

					// Checking if opening failed and error part was opened.
					if(part != null && part.getClass().getName().equals("org.eclipse.ui.internal.ErrorEditorPart")) { //$NON-NLS-1$
						handleErrorOnOpenFile(file, part, page);
					}
				} catch (PartInitException e) {
					RemoteControlConsole.getInstance().println(Messages.getString("FtpUtils.FailedToOpenEditor_ErrMsg") + e.getMessage(), //$NON-NLS-1$
							IConsolePrintUtility.MSG_ERROR);
				}
			}
		};
		// File needs to be opened from UI thread.
		Display.getDefault().asyncExec(runOpen);
	}
	
	/**
	 * Handles errors when opening a file.
	 * @param file File to be opened.
	 * @param editorPart Editor that was opened instead of correct editor.
	 * @param page Workbenchpage used to open editorPart.
	 * @throws PartInitException Thrown when editor couldn't be initialized correctly.
	 */
	private static void handleErrorOnOpenFile(File file, IEditorPart editorPart, IWorkbenchPage page)
			throws PartInitException {
		String errorText = Messages.getString("FtpUtils.FailedToOpenDefaultEditor_ErrMsg1") + file.getName() +  //$NON-NLS-1$
							Messages.getString("FtpUtils.FailedToOpenDefaultEditor_ErrMsg2"); //$NON-NLS-1$
		RemoteControlMessageBox errorDlg =
			new RemoteControlMessageBox(errorText, SWT.YES | SWT.NO | SWT.ERROR);
		
		int result = errorDlg.open();
		if(result == SWT.YES) {
			// Existing error page needs to be closed or page wont open correctly.
			page.closeEditor(editorPart, false);
			
			// Opening file in text editor.
			IDE.openEditor(page, file.toURI(), "org.eclipse.ui.DefaultTextEditor", true); //$NON-NLS-1$
		}
	}

	/**
	 * Renames currently selected file.
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 */
	public static void rename(TableViewer viewer, ViewContentProvider contentProvider) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
			
		if(selectedObject == null || !FtpNamedObject.class.isInstance(selectedObject)){
			// Nothing selected or selection is not file
			return;
		}
		
		FtpNamedObject ftpObject = (FtpNamedObject)selectedObject;
		
		// Getting paths for source and destination files.
		String currentDirectory = getCurrentPath(contentProvider);
		String remoteFile = currentDirectory + ftpObject.getName();
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		String dialogName;
		// Get dialog name.
		if(ftpObject instanceof FtpFileObject) {
			dialogName = RENAME_FILE_DLG_NAME;
		} else if(ftpObject instanceof FtpFolderObject) {
			dialogName =RENAME_FOLDER_DLG_NAME; 
		} else {
			// Not supported type.
			return;
		}
		
		RenameDialog renameDlg = new RenameDialog(sh, ftpObject.getName(), false, dialogName);
		if (renameDlg.open() == Dialog.CANCEL){
			// User canceled dialog
			return;
		}
		// Get new file name from dialog.
		String targetFile = currentDirectory + renameDlg.getFileName();
		
		RenameJob job = new RenameJob(Messages.getString("FtpUtils.RenameJob_Name") + //$NON-NLS-1$
						ftpObject.getName(),
				remoteFile,
				targetFile);
		job.schedule();
	}
	
	/**
	 * Gets currently selected path. This adds single folder to the path, if one folder is selected.
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 * @return Currently selected path, or <code>null</code> if root is shown.  
	 */
	private static String getSelectedPath(TableViewer viewer,
			ViewContentProvider contentProvider) {
		// Getting required information.
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		String targetPath = getCurrentPath(contentProvider);
		
		if(targetPath == null){
			// Can't get selected path from root.
			return null;
		}
		
		if(selection.size() == 1) {
			Object selectedObject = selection.getFirstElement();
			if(FtpFolderObject.class.isInstance(selectedObject)) {
				// Selection is a folder. Adding currently selected single folder to path.
				targetPath = targetPath + ((FtpFolderObject)selectedObject).getName();
			}
		}
		
		return targetPath;
	}
	
	/**
	 * Returns all files and folders in target directory.
	 * @param targetDir Listing is got for this directory.
	 * @return Contents of target directory.
	 */
	private static String[] getFilesAndFolders(String targetDir) {
		// Get file and folder list from remote directory for checking
		// if file already exists
		String[] files = null;
		String[] folders = null;
		if (RCPreferences.getUploadConfirm()) {
			IFTPService ftpService = HTIServiceFactory
					.createFTPService(RemoteControlConsole.getInstance());
			try {
				// Get list of files on remote folder
				files = ftpService.listFiles(targetDir, LIST_CONTENTS_TIMEOUT);
				folders = ftpService.listDirs(targetDir, LIST_CONTENTS_TIMEOUT);
			} catch (Exception e) {
				// Failed to list contents of directory.
				e.printStackTrace();
				return null;
			}
		}
		
		// Creating single array that contains all files and folders.
		String[] filesAndFolders = new String[files.length + folders.length];
		int place = 0;
		for(int i = 0;i < files.length;i++) {
			filesAndFolders[place] = files[i];
			place++;
		}
		for(int i = 0;i < folders.length;i++) {
			filesAndFolders[place] = folders[i];
			place++;
		}
		
		return filesAndFolders;
	}
	
	/**
	 * Pastes previously selected files to target folder.
	 * @param viewer Viewer
	 * @param contentProvider Content provider
	 */
	public static void pasteFiles(TableViewer viewer, ViewContentProvider contentProvider) {
		// Getting required information.
		List<IFtpObject> selectedFiles = contentProvider.getSelectedFiles();
		IFtpObject[] sourceFiles = selectedFiles.toArray(new IFtpObject[0]);
		String sourcePath = contentProvider.getSelectedPath();
		String destFilePath = getSelectedPath(viewer, contentProvider);
		if(destFilePath == null) {
			// Can't paste to root.
			return;
		}
		
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		
		// Getting files and folders.
		String filesAndFolders[] = getFilesAndFolders(destFilePath);
		if(filesAndFolders == null) {
			// Gettings list failed.
			// Failed to list files. Show message to user and return
			RemoteControlMessageBox message = new RemoteControlMessageBox(
					Messages.getString("FtpUtils.FailedToPaste_ErrMsg") //$NON-NLS-1$
					, SWT.ICON_ERROR); 
			message.open();
			return;
		}
		
		boolean replaceAll = false;
			
		for (int j = 0; j < sourceFiles.length; j++) {
			
			if(isSubFolder(sourceFiles[j], sourcePath, destFilePath)) {
				// Can't copy folder to its subfolder.
				break;
			}
			
			boolean canWrite = true;
			String sourceFile = sourceFiles[j].getName();
			String destFileName = sourceFiles[j].getName();
			if (RCPreferences.getPasteConfirm()) {
				// Ask confirmation for replacing files if already exists on
				// target directory
				for (int i = 0; i < filesAndFolders.length; i++) {
					if (!replaceAll && filesAndFolders[i].equals(destFileName)) {
						// No need to check other files
						i = filesAndFolders.length;
						// File exists on remote folder
						// Show confirmation dialog
						ConfirmReplaceDialog dlg = new ConfirmReplaceDialog(
								sh, destFileName,
								(j < sourceFiles.length - 1),
								ConfirmReplaceDialog.Operation.PASTE);
						dlg.open();

						int sel = dlg.getSelection();
						switch (sel) {
						case ConfirmReplaceDialog.RENAME_ID:
							// Rename this file
							RenameDialog renameDlg = new RenameDialog(sh, destFileName, true, RENAME_FILE_DLG_NAME);
							if (renameDlg.open() == Dialog.CANCEL){
								// User canceled dialog
								return;
							}
							// Get new file name from dialog. If user canceled dialog the original
							// filename is returned
							destFileName = renameDlg.getFileName();
							canWrite = true;
							break;
						case IDialogConstants.YES_ID:
							// Replace this file
							canWrite = true;
							break;
						case IDialogConstants.YES_TO_ALL_ID:
							// Replace all files
							replaceAll = true;
							break;
						case IDialogConstants.NO_ID:
							// Do not replace this file
							canWrite = false;
							break;
						case IDialogConstants.CANCEL_ID:
							// Cancel operation
							return;
						default:
							break;
						}
					}
				}
			}

			if(canWrite) {
				if(sourceFiles[j] instanceof FtpFolderObject) {
					// Cut & Copy operation for empty folders needs to be handled specially because
					// ftp copy copies contents of folder to target folder. If there is nothing in
					// source folder, then cut/paste and copy/paste fails. This is handled user friendly by
					// creating new empty folder if necessary.
					pasteFolder(contentProvider, sourcePath, sourceFile, destFilePath, destFileName);
				} else {
					// Paste file by using default handling.
					doDefaultPasteOperation(contentProvider, sourcePath, destFilePath, sourceFile, destFileName);
				}
			}
		}
		
		// If files are moved, then clipboard needs to be cleared.
		if(contentProvider.getFileOperation() == OPERATION.CUT) {	
			contentProvider.setClipboardFiles(new ArrayList<IFtpObject>(), "", OPERATION.NONE); //$NON-NLS-1$
		}
	}

	/**
	 * Does default file/directory paste that does not require any special considerations.
	 * @param contentProvider Content provider
	 * @param sourcePath Path for source file/directory.
	 * @param destFilePath Path for target file/directory.
	 * @param sourceFile Source file name.
	 * @param destFileName Target file name.
	 */
	private static void doDefaultPasteOperation(ViewContentProvider contentProvider,
			String sourcePath, String destFilePath, String sourceFile,
			String destFileName) {
		PasteJob job = new PasteJob(Messages.getString("FtpUtils.PasteFileJob_Name") + sourceFile, //$NON-NLS-1$
				sourceFile,
				sourcePath,
				destFileName,
				destFilePath,
				contentProvider.getFileOperation());
		job.schedule();
	}

	/**
	 * Checks if destFilePath is subfolder to sourcePath or they are same..
	 * @param sourceFtpObject Source object.
	 * @param sourcePath Path for source folder.
	 * @param destFilePath Path for destination folder.
	 * @return True if destFilePath is subfolder or same. False otherwise.
	 */
	private static boolean isSubFolder(IFtpObject sourceFtpObject, String sourcePath, String destFilePath) {
		if(!(sourceFtpObject instanceof FtpFolderObject)) {
			return false;
		}
		String source = addFileSepatorToEnd(sourcePath) + sourceFtpObject.getName() + File.separator;
		String dest = addFileSepatorToEnd(destFilePath);
		
		if(dest.startsWith(source)) {
			if(source.length() == dest.length()) {
				// Source is same as destination. Show message to user.
				RemoteControlMessageBox message = new RemoteControlMessageBox(
						Messages.getString("FtpUtils.SourceSameAsDestination_ErrMsg1") + //$NON-NLS-1$
						sourceFtpObject.getName() + Messages.getString("FtpUtils.SourceSameAsDestination_ErrMsg2"), //$NON-NLS-1$
						SWT.ICON_ERROR); 
				message.open();
				return true;
			}
			else if (source.length() < dest.length()) {
				// Destination folder is subfolder.. Show message to user.
				RemoteControlMessageBox message = new RemoteControlMessageBox(
						Messages.getString("FtpUtils.DestinationIsSubfolder_ErrMsg1") + //$NON-NLS-1$
						sourceFtpObject.getName() + Messages.getString("FtpUtils.DestinationIsSubfolder_ErrMsg2"), //$NON-NLS-1$
						SWT.ICON_ERROR); 
				message.open();
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Handles copy/pasting folders correctly. Empty source folders are created
	 * instead of copying.
	 * @param contentProvider Content provider
	 * @param sourcePath Path for source file.
	 * @param sourceFile Source file name.
	 * @param destFilePath Path for target file.
	 * @param destFileName Target file name.
	 * @param destFilesAndFolders File and folder names in target path.
	 */
	private static void pasteFolder(ViewContentProvider contentProvider, String sourcePath,
			String sourceFile, String destFilePath, String destFileName) {
		String sourceFilePath = addFileSepatorToEnd(sourcePath) + sourceFile;
		String[] sourceFilesAndFolders = getFilesAndFolders(sourceFilePath);
		String[] destFilesAndFolders = getFilesAndFolders(destFilePath);
	
		String targetFilePath = addFileSepatorToEnd(destFilePath) + destFileName;
		
		// Does destination folder already exist?
		boolean destExists = false;
		for (String fileFolder : destFilesAndFolders) {
			if (fileFolder.equals(destFileName)) destExists = true; // ...yes it does.
		}
		
		// If destination folder exists, delete it.
		if (destExists) {
			DeleteDirJob deleteDirJob = new DeleteDirJob(Messages.getString("FtpUtils.Delete_Folder_Job_Name") + " " + //$NON-NLS-1$ //$NON-NLS-2$
					targetFilePath, targetFilePath);
			deleteDirJob.schedule();
		}
		
		// Create destination folder.
		MakeDirJob makeDirJob = new MakeDirJob(Messages.getString("FtpUtils.Make_Dir_Job_Name") + " " + //$NON-NLS-1$ //$NON-NLS-2$
				targetFilePath, targetFilePath);
		makeDirJob.schedule();
		destExists = true;
		
		if(sourceFilesAndFolders == null) {
			// Failed to list files. Show message to user.
			RemoteControlMessageBox message = new RemoteControlMessageBox(
					Messages.getString("FtpUtils.FailedToPaste_ErrMsg") //$NON-NLS-1$
					, SWT.ICON_ERROR); 
			message.open();
			
		} else if(sourceFilesAndFolders.length == 0) {
			// Empty source folder.
			if(contentProvider.getFileOperation() == OPERATION.CUT){
				// Trying to cut empty folder over existing folder, needs to remove cut folder.
				DeleteDirJob deleteDirJob = new DeleteDirJob(Messages.getString("FtpUtils.Delete_Folder_Job_Name") + " " + //$NON-NLS-1$ //$NON-NLS-2$
						sourceFilePath, sourceFilePath);
				deleteDirJob.schedule();
			}			
			// Else trying to copy empty folder over existing folder, nothing to do.
			return;
			
		} else {			
			// Copy and Cut of non-empty directories works well also with the default paste operation.
			doDefaultPasteOperation(contentProvider, sourcePath, destFilePath, sourceFile,
					destFileName);			
		}
	}
}