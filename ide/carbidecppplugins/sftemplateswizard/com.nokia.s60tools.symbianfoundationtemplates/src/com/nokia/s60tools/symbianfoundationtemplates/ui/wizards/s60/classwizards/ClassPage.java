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
package com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.classwizards;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;
import com.nokia.s60tools.symbianfoundationtemplates.engine.OriginalTemplate;
import com.nokia.s60tools.symbianfoundationtemplates.engine.s60.S60TransformKeys;
import com.nokia.s60tools.symbianfoundationtemplates.resources.HelpContextIDs;
import com.nokia.s60tools.symbianfoundationtemplates.resources.LastUsedData;
import com.nokia.s60tools.symbianfoundationtemplates.resources.Messages;
import com.nokia.s60tools.symbianfoundationtemplates.resources.PreferenceConstants;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.FileSelectionControl;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.FolderSelectionControl;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.S60TemplatePageType;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60.S60TemplateWizardPage;
import com.nokia.s60tools.symbianfoundationtemplates.util.Util;

/**
 * Class page.
 *
 */
public class ClassPage extends S60TemplateWizardPage {
	private FolderSelectionControl sourceFolderSelection;
	private FolderSelectionControl headerFolderSelection;
	
	private FileSelectionControl classSelection;
	public FileSelectionControl fileNameSelection;
	private Text subSystemText;
	private Text moduleText;
	private Text descriptionText;
	private Text copyrightText;
	private Composite topLevel;
	private Button cleanBtn;
	private Combo licenseText;
	private Combo companyNameText;
	private Combo copyrightString;
	
	public ClassPage(S60TemplatePageType pageType) {
		super(pageType);
		setTitle(pageType.getTitle());
	}

	public void createControl(Composite parent) {
		topLevel = new Composite(parent, SWT.NONE);
		// Layout manager
		topLevel.setLayout(new GridLayout(1, false));
		topLevel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		Composite selection = new Composite(topLevel, SWT.NONE);
		selection.setLayout(new GridLayout(3, false));
		selection.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		if(pageType != S60TemplatePageType.M_CLASS_PAGE)
			sourceFolderSelection = new FolderSelectionControl(selection, this, Messages.getString("WizardPageSourceFolderLabel")); //$NON-NLS-1$
		
		// Folder selection control
		headerFolderSelection = new FolderSelectionControl(selection, this, Messages.getString("WizardPageHeaderFolderLabel")); //$NON-NLS-1$
		
		// File selection control
		classSelection = new FileSelectionControl(selection, this, Messages.getString("WizardPageClassNameLabel")); //$NON-NLS-1$
		
		//
		fileNameSelection = new FileSelectionControl(selection, this, "File Name:");

		// Separator
		Label separator = new Label(topLevel, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		// Info control
		Composite info = new Composite(topLevel, SWT.NONE);
		info.setLayout(new GridLayout(2, false));
		info.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
	
		new Label(info, SWT.NONE).setText(Messages.getString("WizardPageSubSystemLabel")); //$NON-NLS-1$
		subSystemText = new Text(info, SWT.BORDER);
		subSystemText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		new Label(info, SWT.NONE).setText(Messages.getString("WizardPageModuleLabel")); //$NON-NLS-1$
		moduleText = new Text(info, SWT.BORDER);
		moduleText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		new Label(info, SWT.NONE).setText(Messages.getString("WizardPageDescriptionLabel")); //$NON-NLS-1$
		descriptionText = new Text(info, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
			
		GC gc = new GC(descriptionText);
		FontMetrics fm = gc.getFontMetrics();
		int cols = 20;
		int rows = 6;
		int width = cols * fm.getAverageCharWidth();
		int height = rows * fm.getHeight();
			
		GridData gridData = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
		gridData.widthHint = width;
		gridData.heightHint = height;
			
		descriptionText.setLayoutData(gridData);
		
		new Label(info, SWT.NONE).setText(Messages.getString("WizardPageCopyrightLabel")); //$NON-NLS-1$
		copyrightText = new Text(info, SWT.BORDER);
		copyrightText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		new Label(info, SWT.NONE).setText(Messages.getString("WizardPageLicenseLabel")); //$NON-NLS-1$
		licenseText = new Combo(info, SWT.BORDER|SWT.READ_ONLY);
		licenseText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		new Label(info, SWT.NONE).setText(Messages.getString("WizardPageCompanyLabel")); //$NON-NLS-1$
		companyNameText = new Combo(info, SWT.BORDER);
		companyNameText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		new Label(info, SWT.NONE).setText(Messages.getString("WizardPageCopyrightStringLabel"));
		copyrightString = new Combo(info, SWT.BORDER);// | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		copyrightString.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		GridData data=new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint=30;
		cleanBtn=new Button(topLevel,SWT.CHECK);
		cleanBtn.setText(Messages.getString("WizardPageIncludeInstructionsLabel"));
		cleanBtn.setSelection(true);
		cleanBtn.setLayoutData(data);
		
		setControl(topLevel);
		setPageComplete(true);
		
		copyrightText.setText(new Integer(Util.getCopyrightYear()).toString());
		licenseText.setItems(Util.getDefaultLicence());
		licenseText.select(0);
	
		LastUsedData previousData = new LastUsedData();
		String[] values = previousData.getPreviousValues(LastUsedData.ValueTypes.NAME);
		if(values!=null)
		{
			companyNameText.setItems(values);
			companyNameText.select(0);
		}
		else
		{
			companyNameText.setText(Util.getDefaultCompanyName());
			previousData.saveValues(LastUsedData.ValueTypes.NAME, companyNameText.getText());
		}
		
		values = previousData.getPreviousValues(LastUsedData.ValueTypes.COPYRIGHT);
		if(values !=null)
		{
			copyrightString.setItems(values);
			copyrightString.select(0);
		}
		else
		{
			copyrightString.setText(Util.getDefaultCompanyCopyright());
			previousData.saveValues(LastUsedData.ValueTypes.COPYRIGHT, copyrightString.getText());
		}
	
		if(!preferenceStore.getBoolean(PreferenceConstants.OPENED_FROM_VIEW))
			setPreferencesFromStore();

		performHelp();
		//updateStatus();
		topLevel.setFocus();
	}
	
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(topLevel,
				HelpContextIDs.SF_TEMPLATES_OVERVIEW);
	}
	
	public AbstractList<OriginalTemplate> getTemplates() {
		AbstractList<OriginalTemplate> templates = new ArrayList<OriginalTemplate>();
		
		String[] templateFiles = getTemplateFile().split(";"); //$NON-NLS-1$
		
		IFile headerFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
				headerFolderSelection.getSelectedFolder() + File.separator
				+ fileNameSelection.getSelectedFileWithoutExtension() + ".h")); //$NON-NLS-1$
		
		IFile sourceFile = null;
		if(pageType != S60TemplatePageType.M_CLASS_PAGE)
			sourceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
					sourceFolderSelection.getSelectedFolder() + File.separator
					+ fileNameSelection.getSelectedFileWithoutExtension() + ".cpp")); //$NON-NLS-1$
			
		templates.add(new OriginalTemplate(templateFiles[0], headerFile, getTransformRules()));
		if(pageType != S60TemplatePageType.M_CLASS_PAGE)
			templates.add(new OriginalTemplate(templateFiles[1], sourceFile, getTransformRules()));
		
		return templates;
	}
	
	protected AbstractList<IStatus> getStatuses() {
		AbstractList<IStatus> statuses = new ArrayList<IStatus>();
		
		statuses.add(Util.getFolderNameStatus(headerFolderSelection.getSelectedFolder(), Messages.getString("WizardPageHeaderFolderMsg"))); //$NON-NLS-1$
		if(pageType != S60TemplatePageType.M_CLASS_PAGE)
			statuses.add(Util.getFolderNameStatus(sourceFolderSelection.getSelectedFolder(), Messages.getString("WizardPageSourceFolderMsg"))); //$NON-NLS-1$
		
		statuses.add(getFileNameStatus(classSelection.getSelectedFile(),fileNameSelection.getSelectedFile()));
		
		return statuses;
	}
	
	protected void updateFields() {
		if(pageType != S60TemplatePageType.M_CLASS_PAGE)
			subSystemText.setText(Util.getSubSystemName(sourceFolderSelection.getSelectedFolder()));
		moduleText.setText(Util.getModuleName(headerFolderSelection.getSelectedFolder()));
	}
	
	protected void savePreferencesToStore() {
		if(preferenceStore.getBoolean(PreferenceConstants.OPENED_FROM_VIEW)==false)
		{
			if(pageType != S60TemplatePageType.M_CLASS_PAGE)	
				preferenceStore.setValue(PreferenceConstants.SF_CLASS_PAGE_SRCDIR, sourceFolderSelection.getSelectedFolder());
			preferenceStore.setValue(PreferenceConstants.SF_CLASS_PAGE_HDRDIR, headerFolderSelection.getSelectedFolder());
			preferenceStore.setValue(PreferenceConstants.SF_CLASS_PAGE_MODULE, moduleText.getText());
			preferenceStore.setValue(PreferenceConstants.SF_CLASS_PAGE_SUBSYSTEM, subSystemText.getText());
		}
		preferenceStore.setValue(PreferenceConstants.SF_LICENSE_INDEX, licenseText.getSelectionIndex());
		LastUsedData saveData = new LastUsedData();
		saveData.saveValues(LastUsedData.ValueTypes.NAME, companyNameText.getText());
		saveData.saveValues(LastUsedData.ValueTypes.COPYRIGHT, copyrightString.getText());
	}
	
	private void setPreferencesFromStore() {
		if(preferenceStore.getBoolean(PreferenceConstants.OPENED_FROM_VIEW)==false)
		{
			if(pageType != S60TemplatePageType.M_CLASS_PAGE && preferenceStore.getString(PreferenceConstants.SF_CLASS_PAGE_SRCDIR)!="")
				sourceFolderSelection.setSelectedFolder(preferenceStore.getString(PreferenceConstants.SF_CLASS_PAGE_SRCDIR));
			if(preferenceStore.getString(PreferenceConstants.SF_CLASS_PAGE_SRCDIR)!="")
				headerFolderSelection.setSelectedFolder(preferenceStore.getString(PreferenceConstants.SF_CLASS_PAGE_HDRDIR));
			moduleText.setText(preferenceStore.getString(PreferenceConstants.SF_CLASS_PAGE_MODULE));
			subSystemText.setText(preferenceStore.getString(PreferenceConstants.SF_CLASS_PAGE_SUBSYSTEM));
		}
		licenseText.select(preferenceStore.getInt(PreferenceConstants.SF_LICENSE_INDEX));
	}
		
	private Map<String, String> getTransformRules() {
		Map<String, String> transformRules = new HashMap<String, String>();
		
		transformRules.put(S60TransformKeys.getString("key_filename"), fileNameSelection.getSelectedFileWithoutExtension()); //$NON-NLS-1$
		transformRules.put(S60TransformKeys.getString("key_module"), moduleText.getText()); //$NON-NLS-1$
		transformRules.put(S60TransformKeys.getString("key_subsystem"), subSystemText.getText()); //$NON-NLS-1$
		transformRules.put(S60TransformKeys.getString("key_description"), descriptionText.getText()); //$NON-NLS-1$
		transformRules.put(S60TransformKeys.getString("key_copyright"), copyrightText.getText()); //$NON-NLS-1$
		transformRules.put(S60TransformKeys.getString("key_classname"), classSelection.getSelectedFileWithoutExtension()); //$NON-NLS-1$
		transformRules.put(S60TransformKeys.getString("key_license"), licenseText.getText()); //$NON-NLS-1$		
		transformRules.put(S60TransformKeys.getString("key_companyname"), companyNameText.getText()); //$NON-NLS-1$
		transformRules.put(S60TransformKeys.getString("key_companycopyright"), copyrightString.getText());
		
		if(licenseText.getText().equalsIgnoreCase("Symbian Foundation License v1.0"))
			transformRules.put(S60TransformKeys.getString("key_licenseurl"), "http://www.symbianfoundation.org/legal/sfl-v10.html");
		else if(licenseText.getText().equalsIgnoreCase("Eclipse Public License v1.0"))
			transformRules.put(S60TransformKeys.getString("key_licenseurl"), "http://www.eclipse.org/legal/epl-v10.html");
		
		String upperCaseClassName = classSelection.getSelectedFileWithoutExtension().toUpperCase();
		
		switch(pageType) {
		case C_CLASS_PAGE:
			transformRules.put(S60TransformKeys.getString("key_cclassname"), "C_" + upperCaseClassName + "_H"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			break;
		case M_CLASS_PAGE:
			transformRules.put(S60TransformKeys.getString("key_mclassname"), "M_" + upperCaseClassName + "_H"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			break;
		case R_CLASS_PAGE:
			transformRules.put(S60TransformKeys.getString("key_rclassname"), "R_" + upperCaseClassName + "_H"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			break;
		case T_CLASS_PAGE:
			transformRules.put(S60TransformKeys.getString("key_tclassname"), "T_" + upperCaseClassName + "_H"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			break;
		}
		
		return transformRules;
	}
	
	private IStatus getFileNameStatus(String className,String fileName) {
		IStatus fileStatus = new Status(IStatus.OK, SymbianFoundationTemplates.PLUGIN_ID, 0, "", null); //$NON-NLS-1$
		
		if(className.length() == 0)
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, Messages.getString("WizardPageErrorClassNameEmpty"), null); //$NON-NLS-1$
		else if(fileName.length() == 0)
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, Messages.getString("WizardPageErrorFileNameEmpty"), null);
		else if(fileName.indexOf(" ")!=-1)
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, Messages.getString("WizardPageErrorFileNameHasSpaces"), null);
		else if(!Util.checkFileName(className))
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, className+Messages.getString("WizardPageErrorInvalidClassName"), null);
		else if(!Util.checkFileName(fileName))
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, fileName+Messages.getString("WizardPageErrorInvalidFileName"), null);
		else if(headerFolderSelection.getSelectedFolder()!="" && Util.getSelectedFile(fileName + ".h", headerFolderSelection.getSelectedFolder()).exists()) //$NON-NLS-1$
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, Messages.getString("WizardPageErrorClassHeader") + fileName + ".h" + Messages.getString("WizardPageErrorClassAlreadyExists"), null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		else if(pageType != S60TemplatePageType.M_CLASS_PAGE && sourceFolderSelection.getSelectedFolder()!="" && Util.getSelectedFile(fileName + ".cpp", sourceFolderSelection.getSelectedFolder()).exists()) //$NON-NLS-1$
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, Messages.getString("WizardPageErrorClassSource") + fileName + ".cpp" + Messages.getString("WizardPageErrorClassAlreadyExists"), null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		else {
			String message = Messages.getString("WizardPageWarningClassNameDiscouraged"); //$NON-NLS-1$
			
			switch(pageType) {
			case C_CLASS_PAGE:
				if(!className.startsWith("C")) //$NON-NLS-1$
					fileStatus = new Status(IStatus.WARNING, SymbianFoundationTemplates.PLUGIN_ID, 1, message + "C.", null); //$NON-NLS-1$
				break;
			case M_CLASS_PAGE:
				if(!className.startsWith("M")) //$NON-NLS-1$
					fileStatus = new Status(IStatus.WARNING, SymbianFoundationTemplates.PLUGIN_ID, 1, message + "M.", null); //$NON-NLS-1$
				break;
			case R_CLASS_PAGE:
				if(!className.startsWith("R")) //$NON-NLS-1$
					fileStatus = new Status(IStatus.WARNING, SymbianFoundationTemplates.PLUGIN_ID, 1, message + "R.", null); //$NON-NLS-1$
				break;
			case T_CLASS_PAGE:
				if(!className.startsWith("T")) //$NON-NLS-1$
					fileStatus = new Status(IStatus.WARNING, SymbianFoundationTemplates.PLUGIN_ID, 1, message + "T.", null); //$NON-NLS-1$
				break;
			}
		}
		
		return fileStatus;
	}
	
	private String getTemplateFile() {
		String location = SymbianFoundationTemplates.getDefault().getTemplatesPath() + File.separator;
		String[] templateFiles = pageType.getTemplateFiles().split(";"); //$NON-NLS-1$
		if(!cleanBtn.getSelection())
			return location + templateFiles[2] + ";" + location + templateFiles[3]; //$NON-NLS-1$
		
		return location + templateFiles[0] + ";" + location + templateFiles[1]; //$NON-NLS-1$
	} 
	@Override
	public void handleEvent(Event event) {
		if(event.widget == classSelection.getTextControl())
		{
			fileNameSelection.setFileName(classSelection.getSelectedFile());
		}
		super.handleEvent(event);
	}
}
