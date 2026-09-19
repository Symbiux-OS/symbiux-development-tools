/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.imaker.internal.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;
import com.nokia.s60tools.imaker.internal.model.iContent.ImageContent;

public class ProjectManager {
	public static final String NEW_ITEM            = "<New Configuration>";
	public static final String IBY_FILENAME_PREFIX = "imaker_additional_";
	public static final String IBY_FOLDER_NAME     = "rombuild";
	public static final String MAKEFILE_NAME       = "imaker_additions.mk";

	private String activeFile = "";
	private IProject project;
	private ArrayList<IResource> imakerFiles;

	public ProjectManager(IProject project) {
		this.imakerFiles = new ArrayList<IResource>();
		this.project = project;
	}

	public String getActiveFile() {
		return activeFile;
	}

	public void setActiveFile(String newActive) {
		activeFile = newActive;
		
	}
	
	public IProject getProject() {
		return project;
	}
	
	/**
	 * @return The root location of the project
	 */
	public String getRoot() {
		return IMakerUtils.getProjectRootLocation(project);
	}
	
	
	/**
	 * Returns the imaker file in the specified location
	 * @param location
	 * @return
	 */
	public IResource getImakerFile(IPath location) {
		IResource ret = null;
		for (IResource resource : getImakerFiles()) {
			if(resource.getLocation().equals(location)) {
				ret = resource;
				break;
			}
		}
		return ret;
	}

//	public IFile getImakerMakeFile() {
//		IFolder pfolder = project.getFolder(IBY_FOLDER_NAME);
//		IFile mk = pfolder.getFile(MAKEFILE_NAME);
//		if(mk.exists()) {
//			return mk;
//		} else {
//			return null;
//		}
//	}


	/**
	 * Returns all imaker files in the selected project
	 * @return
	 */
	public List<IResource> getImakerFiles() {
		imakerFiles.clear();
		try {
			project.accept(new ImakerResourceVisitor(imakerFiles));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return imakerFiles;
	}
	
	private class ImakerResourceVisitor implements IResourceVisitor {

		private ArrayList<IResource> list;

		/**
		 * @param imps
		 */
		public ImakerResourceVisitor(ArrayList<IResource> imps) {
			this.list = imps;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
		 */
//		@Override
		public boolean visit(IResource res) throws CoreException {
			String name = res.getName();
			if(name.endsWith(".imp")) {
				list.add(res);
			}
			if(res instanceof IFolder) {
				if(name.equals("src") || name.equals("inc")) {
					return false;
				}
			}
			return true;
		}
		
	}

	/**
	 * Creates new iby file to the specified location, with the specified content
	 * @param basename The name of the iby file, without the extension
	 * @param content The contents fo iby file
	 * @param path parent folder of the iby file
	 * @return The location or path of the newly created file
	 */
	public IPath createIbyFile(String basename, String content, IPath path) {
		File parent = path.toFile();
		createFolder(parent);
		path = path.append(basename+".iby");
		File file = path.toFile();
		
		FileOutputStream fo;
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			fo = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fo);
			
			Writer out = new BufferedWriter(osw);
			out.write(content);
			out.flush();
			out.close();
			return new Path(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void createFolder(File folder) {
		if(folder!=null&&!folder.exists()) {
			String parent = folder.getParent();
			File f = new File(parent);
			createFolder(f);
			folder.mkdir();
		}
	}

	public IPath createAdditionsMakefile(List<IPath> files, IPath path) {
		createFolder(path.toFile());
		IPath mk = path.append(MAKEFILE_NAME);
		File mkFile = mk.toFile();
		
		FileOutputStream fo;
		try {
			StringBuffer sb = new StringBuffer();
			for (IPath p : files) {
				File f = p.toFile();
				String name = f.getName();
				int index = name.lastIndexOf('_');
				String loc = name.substring(index+1, name.length()-4);
				loc = loc.toUpperCase();
				sb.append(loc+"_OBY += " + f.getAbsolutePath()+"\n");
			}

			fo = new FileOutputStream(mkFile);
			OutputStreamWriter osw = new OutputStreamWriter(fo);
			
			Writer out = new BufferedWriter(osw);
			out.write(sb.toString());
			out.flush();
			out.close();
			return new Path(mkFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Populate the model of the dialog with content from the selected project
	 * @param ic
	 */
	public void populate(ImageContent ic) {
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
			String filename = null;
			
//			boolean debug = "udeb".equalsIgnoreCase(buildConfig.getTargetString());
			// add the binaries and any resource-type files generated by all mmp files in the project
			for (IPath mmp : EpocEngineHelper.getMMPFilesForBuildConfiguration(buildConfig)) {
				IPath hp = EpocEngineHelper.getHostPathForExecutable(buildConfig, mmp);
				IPath tp = EpocEngineHelper.getTargetPathForExecutable(buildConfig, mmp);
				
				filename = hp.segment(hp.segmentCount()-1);
				
				// add component executable
				if (hp != null) {
					addEntry(ic, hp.toOSString(), tp.toOSString(), filename);
				}

				HashMap<String, String> hostTargetRSRCMap = EpocEngineHelper.getHostAndTargetResources(buildConfig, mmp);
				// Add resource files...
				for (String key : hostTargetRSRCMap.keySet()) {
					filename = new File(key).getName();
					addEntry(ic, key, hostTargetRSRCMap.get(key),filename);
				}
			}

			// check the project for image makefiles
			HashMap<String, String> hostTargetImagesMap = EpocEngineHelper.getHostAndTargetImages(buildConfig);
			for (String key : hostTargetImagesMap.keySet()) {
				filename = new File(key).getName();				
				addEntry(ic, key, hostTargetImagesMap.get(key), filename);
			}
		}	
	}

	private void addEntry(ImageContent ic,
			String hp, String tp, String filename) {
		IbyEntry entry = IContentFactory.eINSTANCE.createIbyEntry();
		if(hp.length()>2) {
			hp = hp.substring(2);
		}
		entry.setFile(hp);

		if(tp!=null&&!tp.endsWith(filename)) {
			if(tp.endsWith(File.separator)) {
				tp = tp + filename;
			} else {
				tp = tp + File.separator + filename;
			}
		}
		if(tp!=null) {
			tp = tp.substring(2);			
		}
		entry.setTarget(tp);
		boolean contains = false;
		for (IbyEntry e : ic.getEntries()) {
			if(e.equals(entry)) {
				contains = true;
				break;
			}
		}
		if(!contains) {
			ic.getEntries().add(entry);
		}
	}
}
