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
package com.nokia.s60tools.util.sourcecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.exceptions.JobCancelledByUserException;
import com.nokia.s60tools.util.internal.Messages;

/**
 * Utility methods for handling Carbide/Eclipse projects.
 */
public class ProjectUtils {


	/**
	 * Print utility used to report error, warning, and info messages.
	 */
	private IConsolePrintUtility printUtility;

	/**
	 * Constructor.
	 * @param printUtility Print utility used to report error, warning, and info messages.
	 */
	public ProjectUtils(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;		
	}
	
	/**
	 * Create a Carbide C++ project by given <code>source file</code> file.
	 * Seeks <code>mmp</code> -file where given source code belongs to, and
	 * by <code>mmp</code> -file, seeks <code>bld.inf</code> -file where
	 * <code>mmp</code> -file belongs to. By <code>bld.inf</code> -file
	 * creating Carbide C++ project.
	 * 
	 * @param monitor Job progress monitor.
	 * @param srcLocation Source location
	 * @param sdkID SDK Id identifying the used SDK.
	 * @return a Carbide C++ project.
	 * @throws JobCancelledByUserException
	 * @throws CannotFoundFileException
	 * @throws IOException
	 * @throws CacheFileDoesNotExistException
	 * @throws PartInitException
	 * @throws CoreException
	 */
	public ICProject createProject(IProgressMonitor monitor, SourceFileLocation srcLocation, String sdkID) 
		throws JobCancelledByUserException, CannotFoundFileException, 
				IOException, PartInitException, CoreException
	{

		monitor.subTask(Messages.getString("ProjectUtils.SearchingMmpFile_Msg_ToUser") + srcLocation.getSourceFileLocation()); //$NON-NLS-1$
		String mmpFilePath = getMMPFilePath(srcLocation, monitor);
		// Throwing JobCancelledByUserException if job was canceled when search was ongoing. 
        if (monitor.isCanceled()){
        	String msg = Messages.getString(Messages.getString("ProjectUtils.Canceled_By_User_ConsoleMsg")); //$NON-NLS-1$
        	printUtility.println(msg);    				
		    throw new JobCancelledByUserException(msg);
        }
        
        monitor.subTask(Messages.getString("ProjectUtils.SearchingBldInf_Msg_ToUser") + mmpFilePath); //$NON-NLS-1$
		String bldInfFilePath = getBldInfFilePath(srcLocation, mmpFilePath, monitor);
		// Throwing JobCancelledByUserException if job was canceled when search was ongoing. 
        if (monitor.isCanceled()){
        	String msg = Messages.getString(Messages.getString("ProjectUtils.Canceled_By_User_ConsoleMsg")); //$NON-NLS-1$
        	printUtility.println(msg);    				
		    throw new JobCancelledByUserException(msg);
        }
		IProjectFinder finder = ProjectFinderFactory.createProjectFinder(printUtility, monitor);
		int rootLevel = finder.getRootLevelFromBldInf(bldInfFilePath);
		String projectName = getProjectName(bldInfFilePath, rootLevel);
		projectName = checkProjectName(projectName);
		return createProject(bldInfFilePath, projectName, rootLevel, sdkID, monitor);
	}
	
	/**
	 * Create a Carbide C++ project by given <code>bld.inf</code> file.
	 * 
	 * @param monitor Progress monitor.
	 * @param <code>bld.inf</code> file path
	 * @param sdkID SDK Id identifying the used SDK.
	 * @return a Carbide C++ project.
	 * @throws JobCancelledByUserException
	 * @throws CannotFoundFileException
	 * @throws IOException
	 * @throws CacheFileDoesNotExistException
	 * @throws PartInitException
	 * @throws CoreException
	 */
	public ICProject createProject(IProgressMonitor monitor, String bldInfFilePath, String sdkID) 
		throws JobCancelledByUserException, CannotFoundFileException, 
				IOException, PartInitException, CoreException
	{
		
		IProjectFinder finder = ProjectFinderFactory.createProjectFinder(printUtility, monitor);
		int rootLevel = finder.getRootLevelFromBldInf(bldInfFilePath);
		String projectName = getProjectName(bldInfFilePath, rootLevel);
		projectName = checkProjectName(projectName);
		return createProject(bldInfFilePath, projectName, rootLevel, sdkID, monitor);
		
	}	
	
	/**
	 * Create a Carbide C++ project by given <code>bld.inf</code> file.
	 * @param bldInfFilePath Path name to bld.inf file to create project from.
	 * @param projectName Name of the project to be created.
	 * @param rootLevel How many directories upwards is the root level 
	 *                  for the workspace compared to bld.inf file absolute path. 
	 * @param sdkID SDK Id identifying the used SDK.
	 * @param monitor Job progress monitor.
	 * @return a C++ project imported by given bld.inf file.
	 * @throws CoreException
	 * @throws PartInitException
	 * @throws FileNotFoundException
	 */
	public ICProject createProject( String bldInfFilePath,
								String projectName, int rootLevel, String sdkID, IProgressMonitor monitor)
										throws CoreException, PartInitException, FileNotFoundException {
	   IProject project = ProjectCorePlugin.createProject(projectName, getProjectPath(bldInfFilePath, rootLevel));
	   String projectRelativeBldInfPath = getProjectRelativeBldInfPath(bldInfFilePath, rootLevel);
	   
	   ISDKManager manager = SDKCorePlugin.getSDKManager();
	   ISymbianSDK sdk = manager.getSDK(sdkID, true);//get currently used SDK	   
	   	   
	   //Get all build configurations of used SDK
       boolean sbsv2Project = project.hasNature(CarbideBuilderPlugin.CARBIDE_SBSV2_PROJECT_NATURE_ID);
	   String builderId = sbsv2Project ? 
			   ISymbianBuilderID.SBSV2_BUILDER : ISymbianBuilderID.SBSV1_BUILDER;
	   
	   // If there is no build info for the builderId, null is returned.
	   ISDKBuildInfo info = sdk.getBuildInfo(builderId);
	   
	   // null buildConfigs list is not allowed. 
	   List<ISymbianBuildContext> buildConfigs = (null != info) ? 
			   info.getFilteredBuildConfigurations() : new ArrayList<ISymbianBuildContext>();
	   
	   List<String> infComponentsList = new ArrayList<String>();
	   
	   ICProject cProject = ProjectCorePlugin.postProjectCreatedActions(
			   project, projectRelativeBldInfPath, buildConfigs, infComponentsList, null, null, monitor);

	   return cProject;

	}		
	
	/**
	 * Check if project exist or not
	 * @param projectName Name of the project
	 * @return <code>true</code> if project exists
	 */
	public boolean isProjectExisting(String projectName){
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return project.exists();
	}
	
	/**
	 * Checks if project already exists in workspace. Returns project name that doesn't exist.
	 * Modifies name if needed by adding number number to the end of project name.
	 * @param projectName Existence of project with this name is checked.
	 * @return Project name that doesn't exist in workspace.
	 */
	private String checkProjectName(String projectName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		String tmpProjectName = projectName;
		int projectPostfix = 1;
		
		// Checking if project exists and as a result tmpProjectName doesn't exist in workspace.
		while(project.exists()){
			projectPostfix++;
			tmpProjectName = projectName + projectPostfix;
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(tmpProjectName);
		}

		return tmpProjectName;
	}
	
	/**
	 * Returns .mmp file path for source file.
	 * @param location Location of source file.
	 * @param monitor  Job progress monitor.
	 * @return Path to mmp file that uses source file.
	 * @throws CannotFoundFileException
	 */
	private String getMMPFilePath(SourceFileLocation location, IProgressMonitor monitor) throws CannotFoundFileException {
		IProjectFinder finder = ProjectFinderFactory.createProjectFinder(printUtility, monitor);
		String path = finder.findMMPFile(location.getSourceFileLocation());
		return path;
	}

	/**
	 * Returns bld.inf file path for mmp file.
	 * @param location Locatation of source file.
	 * @param mmpFilePath Path of mmp file which bld.inf file is searched.
	 * @param monitor Job progress monitor.
	 * @return Path of bld.inf file.
	 * @throws CannotFoundFileException
	 */
	private String getBldInfFilePath(SourceFileLocation location, String mmpFilePath, IProgressMonitor monitor)throws CannotFoundFileException {
		IProjectFinder finder = ProjectFinderFactory.createProjectFinder(printUtility, monitor);
		String path = finder.findBLDINFFile(mmpFilePath);
		return path;
	}
	
	/**
	 * Returns project name from the given absolute path to bld.inf file.
	 * @param bldInfFilePath Absolute path to bld.inf file
	 * @param rootLevel How many directories upwards is the root level 
	 *                  that defined name for the project. 
	 * @return project name for the bld.inf file
	 */
	private String getProjectName(String bldInfFilePath, int rootLevel){
		File file = new File(bldInfFilePath);
		// Removing file name.
		file = file.getParentFile();

		// Getting correct directory by removing folder correct amount of folders names.
		for(int i = 0;i < rootLevel;i++){
			file = file.getParentFile();
		}

		return file.getName();
	}
	/**
	 * Returns project name.
	 * @param bldInfFilePath Absolute path to bld.inf file
	 * @param monitor Job progress monitor.
	 * @return name for the project
	 * @throws IOException 
	 */
	public String getProjectName(String bldInfFilePath, IProgressMonitor monitor) throws IOException{
		
		IProjectFinder finder = ProjectFinderFactory.createProjectFinder(printUtility, monitor);
		int rootLevel = finder.getRootLevelFromBldInf(bldInfFilePath);		

		return getProjectName(bldInfFilePath, rootLevel);
	}	
	
	/**
	 * Gets project path for the new project based in the bld.inf file.
	 * @param bldInfFilePath Absolute path to bld.inf file
	 * @param rootLevel How many directories upwards is the root level 
	 *                  that defined name for the project. 
	 * @return project path 
	 */
	private String getProjectPath(String bldInfFilePath, int rootLevel){
		File file = new File(bldInfFilePath);
		// Removing file name.
		file = file.getParentFile();
		
		// Getting correct directory by removing folder correct amount of folders names.
		for(int i = 0;i < rootLevel;i++){
			file = file.getParentFile();
		}
		
		return file.getAbsolutePath();
	}	
	
	/**
	 * Returns relative path to bld.inf file.
	 * @param bldInfFilePath Absolute path to bld.inf file.
	 * @param rootLevel How many directories upwards is the root level 
	 *                  for the workspace compared to bld.inf file absolute path. 
	 * @return relative path to bld.inf file
	 */
	private String getProjectRelativeBldInfPath(String bldInfFilePath, int rootLevel) {
		File file = new File(bldInfFilePath);
		String relativePath = File.separator + file.getName();
		// Removing file name.
		file = file.getParentFile();
		
		// Getting correct directory by removing folder correct amount of folders names.
		for(int i = 0;i < rootLevel;i++){
			relativePath = File.separator + file.getName() + relativePath;
			file = file.getParentFile();
		}
		
		return relativePath;
	}		
	
	
}
