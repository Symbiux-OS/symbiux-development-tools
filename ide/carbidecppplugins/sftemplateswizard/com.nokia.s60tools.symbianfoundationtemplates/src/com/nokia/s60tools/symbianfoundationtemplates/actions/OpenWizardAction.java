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
package com.nokia.s60tools.symbianfoundationtemplates.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cdt.builder.ICarbideBuildManager;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;
import com.nokia.s60tools.symbianfoundationtemplates.resources.Messages;
import com.nokia.s60tools.symbianfoundationtemplates.resources.PreferenceConstants;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.S60TemplateWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.classwizards.CClassWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.classwizards.MClassWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.classwizards.RClassWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.classwizards.TClassWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.headerwizards.CHeaderWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.headerwizards.MHeaderWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.headerwizards.RHeaderWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.headerwizards.THeaderWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.sourcewizards.SourceWizard;

/**
 * This is an action class to open the wizard from the context menu of an MMP file. 
 *
 */
public class OpenWizardAction implements IObjectActionDelegate {

	S60TemplateWizard wiz;
	private ICarbideProjectInfo prjInfo;
	public OpenWizardAction() {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(final IAction action) {
		IWorkbenchWindow window=SymbianFoundationTemplates.getDefault().getWorkbench().getActiveWorkbenchWindow();
		ISelection selection= (ISelection)window.getSelectionService().getSelection();
		IProject prj= CarbideBuilderPlugin.getProjectInContext();
		ICarbideBuildManager mngr=CarbideBuilderPlugin.getBuildManager();
		prjInfo = mngr.getProjectInfo(prj);
		List<java.io.File> userinc = new ArrayList<java.io.File>(); 
	    List<java.io.File> sysinc=new ArrayList<java.io.File>();
	    
		if(selection instanceof TreeSelection)
		{
			IStructuredSelection treeSelection= (IStructuredSelection)selection;
			IPath mmp=new Path(treeSelection.getFirstElement().toString());
			EpocEnginePathHelper pathHelper=new EpocEnginePathHelper(prjInfo.getProject());
			//EpocEngineHelper.getMMPIncludePaths(prjInfo.getProject(),fl.getProjectRelativePath(),prjInfo.getDefaultConfiguration(), userinc, sysinc);
			
			//gets USERINCLUDE paths from the MMP file
			//Treeselection object is different for different views
			//(i.e. for Sysmbian Project Navigator  and Project Explorer)
			IPath mmpPath=null;
			if(treeSelection.getFirstElement() instanceof IResource)
			{
				mmpPath=((IResource)treeSelection.getFirstElement()).getLocation();
				EpocEngineHelper.getMMPIncludePaths(prjInfo.getProject(),((IFile)treeSelection.getFirstElement()).getProjectRelativePath(),prjInfo.getDefaultConfiguration(), userinc, sysinc);
			}
			else
			{
				IFile fl=ResourcesPlugin.getWorkspace().getRoot().getFile(mmp);
				mmpPath=fl.getLocation();
				EpocEngineHelper.getMMPIncludePaths(prjInfo.getProject(),fl.getProjectRelativePath(),prjInfo.getDefaultConfiguration(), userinc, sysinc);
			}
			//gets SOURCEPATH paths from the MMP file  
			List<IPath> sourceFiles=EpocEngineHelper.getSourceFilesForConfiguration(prjInfo.getDefaultConfiguration(),mmpPath);//mmppath.makeAbsolute());
			Iterator<IPath> srsFilesItr=sourceFiles.iterator();
			List<String> srcDirs=new ArrayList<String>();
			while(srsFilesItr.hasNext())
			{
				//IFile inc=ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(test.next().toFile().getParent()));
				IPath mmppath=srsFilesItr.next();
				String str=(pathHelper.convertFilesystemToWorkspace(mmppath)).toFile().getParent().replace('\\', '/');
				if(!srcDirs.contains(str))
				{
					srcDirs.add(str);
				}
			}
			
			MMPSourceUserIncPaths prevPaths=new MMPSourceUserIncPaths();
			
			//Convert all user include paths to project-relative
			List<String> userIncPaths=new ArrayList<String>();
			Iterator<File> incItr=userinc.iterator();
			while(incItr.hasNext())
			{
				//IFile inc=ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(inci.next().getAbsolutePath()));
				IPath incpath=new Path(incItr.next().getAbsolutePath());
				String str=(pathHelper.convertFilesystemToWorkspace(incpath)).toString().replace('\\', '/');
				userIncPaths.add(str);
			}
			
			prevPaths.saveValues( MMPSourceUserIncPaths.ValueTypes.SRC_DIRS,srcDirs.toArray(new String[0]));
			prevPaths.saveValues( MMPSourceUserIncPaths.ValueTypes.USERINC_DIRS,userIncPaths.toArray(new String[0]));
			
		}
		//set boolean true when open from the project view 
		IPreferenceStore preferenceStore = SymbianFoundationTemplates.getDefault().getPreferenceStore();
		preferenceStore.setValue(PreferenceConstants.OPENED_FROM_VIEW, true);
		Runnable showWizardRunnable = new Runnable(){
			public void run(){
	  		  WizardDialog wizDialog;
	  		  if(action.getText().equalsIgnoreCase("C Class"))
	  		  {
	  			  wiz=new CClassWizard();
	  			  wiz.setWindowTitle(Messages.getString("CClassWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("M Class"))
	  		  {
	  			  wiz=new MClassWizard();
	  			  wiz.setWindowTitle(Messages.getString("MClassWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("R Class"))
	  		  {
	  			  wiz= new RClassWizard();
	  			  wiz.setWindowTitle(Messages.getString("RClassWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("T Class"))
	  		  {
	  			  wiz=new TClassWizard();
	  			  wiz.setWindowTitle(Messages.getString("TClassWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("C Class Header"))
	  		  {
	  			  wiz=new CHeaderWizard();
	  			  wiz.setWindowTitle(Messages.getString("CHeaderWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("M Class Header"))
	  		  {
	  			  wiz=new MHeaderWizard();
	  			  wiz.setWindowTitle(Messages.getString("MHeaderWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("R Class Header"))
	  		  {
	  			  wiz=new RHeaderWizard();
	  			  wiz.setWindowTitle(Messages.getString("RHeaderWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("T Class Header"))
	  		  {
	  			  wiz=new THeaderWizard();
	  			  wiz.setWindowTitle(Messages.getString("THeaderWizardTitle"));
	  		  }
	  		  else if(action.getText().equalsIgnoreCase("Source File"))
	  		  {
	  			  wiz=new SourceWizard();
	  			  wiz.setWindowTitle(Messages.getString("SourceWizardTitle"));
	  		  }
	  		  wizDialog = new WizardDialog(Display.getCurrent().getActiveShell(), wiz);
			  wizDialog.create();		
	       	  wizDialog.getShell().setSize(550, 620);
			  try{
			  wizDialog.setBlockOnOpen(true);
			  wizDialog.open();
			  }catch(Exception e)
			  {				  
				  e.printStackTrace();
			  }
			 }
		   };
		   
		  Display.getDefault().syncExec(showWizardRunnable); 
		  
		  clearAllPreviousValues();
		  
	}
	/**
	 * Clear all the values before the wizard is opened next time.
	 *
	 */
	private void clearAllPreviousValues()
	{
		IPreferenceStore preferenceStore = SymbianFoundationTemplates.getDefault().getPreferenceStore();
		MMPSourceUserIncPaths toClearPaths=new MMPSourceUserIncPaths();
		toClearPaths.saveValues(MMPSourceUserIncPaths.ValueTypes.SRC_DIRS, null);
		toClearPaths.saveValues(MMPSourceUserIncPaths.ValueTypes.USERINC_DIRS, null);
		preferenceStore.setValue(PreferenceConstants.OPENED_FROM_VIEW, false);
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
