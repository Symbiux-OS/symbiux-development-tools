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
package com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60;

import com.nokia.s60tools.symbianfoundationtemplates.resources.Images;
import com.nokia.s60tools.symbianfoundationtemplates.resources.Messages;
import com.nokia.s60tools.symbianfoundationtemplates.resources.TemplateFiles;

/**
 * Template page type.
 *
 */
public enum S60TemplatePageType {
	C_CLASS_PAGE	(	Messages.getString("CClassWizardTitle"), //$NON-NLS-1$
						Messages.getString("CClassWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.C_Class"), //$NON-NLS-1$
						Images.getString("Banner.Class")
					),
	
	M_CLASS_PAGE	(	Messages.getString("MClassWizardTitle"), //$NON-NLS-1$
						Messages.getString("MClassWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.M_Class"), //$NON-NLS-1$
						Images.getString("Banner.Class")
					),
	
	R_CLASS_PAGE	(	Messages.getString("RClassWizardTitle"), //$NON-NLS-1$
						Messages.getString("RClassWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.R_Class"), //$NON-NLS-1$
						Images.getString("Banner.Class")
					),
						
	T_CLASS_PAGE	(	Messages.getString("TClassWizardTitle"), //$NON-NLS-1$
						Messages.getString("TClassWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.T_Class"), //$NON-NLS-1$
						Images.getString("Banner.Class")
					),
						
	C_HEADER_PAGE	(	Messages.getString("CHeaderWizardTitle"), //$NON-NLS-1$
						Messages.getString("CHeaderWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.C_Header"), //$NON-NLS-1$
						Images.getString("Banner.Header")
					),
						
	M_HEADER_PAGE	(	Messages.getString("MHeaderWizardTitle"), //$NON-NLS-1$
						Messages.getString("MHeaderWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.M_Header"), //$NON-NLS-1$
						Images.getString("Banner.Header")
					),
						
	R_HEADER_PAGE	(	Messages.getString("RHeaderWizardTitle"), //$NON-NLS-1$
						Messages.getString("RHeaderWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.R_Header"), //$NON-NLS-1$
						Images.getString("Banner.Header")
					),
						
	T_HEADER_PAGE	(	Messages.getString("THeaderWizardTitle"), //$NON-NLS-1$
						Messages.getString("THeaderWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.T_Header"), //$NON-NLS-1$
						Images.getString("Banner.Header")
					),
						
	SOURCE_PAGE		(	Messages.getString("SourceWizardTitle"), //$NON-NLS-1$
						Messages.getString("SourceWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.Source"), //$NON-NLS-1$
						Images.getString("Banner.Source")
					),
						
	RESOURCE_PAGE	(	Messages.getString("ResourceWizardTitle"), //$NON-NLS-1$
						Messages.getString("ResourceWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.Resource"), //$NON-NLS-1$
						Images.getString("Banner.File")
					),
						
	ICON_PAGE		(	Messages.getString("IconWizardTitle"), //$NON-NLS-1$
						Messages.getString("IconWizardMessage"), //$NON-NLS-1$
						TemplateFiles.getString("TemplateFile.Icon"), //$NON-NLS-1$
						Images.getString("Banner.File")
					),
	
	MMP_PAGE		(	Messages.getString("MMPWizardTitle"),
						Messages.getString("MMPWizardMessage"),
						TemplateFiles.getString("TemplateFile.MMP"),
						Images.getString("Banner.MMPFile")
					),
	
	BUILD_INFO_PAGE	(	Messages.getString("BuildInfoWizardTitle"),
						Messages.getString("BuildInfoWizardMessage"),
						TemplateFiles.getString("TemplateFile.BuildInfo"),
						Images.getString("Banner.File")
					);
	
	private String title;
	private String message;
	private String templateFiles;
	private String image;
	
	private S60TemplatePageType(String title, String message, String templateFiles, String image) {
		this.title = title;
		this.message = message;
		this.templateFiles = templateFiles;
		this.image = image;
	}
	
	/**
	 * @return the pages title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the pages message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return the template files for this page
	 */
	public String getTemplateFiles() {
		return templateFiles;
	}
	
	public String getImage() {
		return image;
	}
}
