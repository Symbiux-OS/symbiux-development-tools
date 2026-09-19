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

import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;
import com.nokia.s60tools.symbianfoundationtemplates.engine.OriginalTemplate;
import com.nokia.s60tools.symbianfoundationtemplates.engine.TransformedTemplate;
import com.nokia.s60tools.symbianfoundationtemplates.engine.s60.S60Engine;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.BaseTemplateWizard;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.BaseTemplateWizardPage;

/**
 * S60 template wizard. Handles template transforming with S60Engine.
 *
 */
public abstract class S60TemplateWizard extends BaseTemplateWizard {
	public S60TemplateWizard(String image) {
		super();
		
		Image wizardBanner = new Image(PlatformUI.getWorkbench().getDisplay(),
				SymbianFoundationTemplates.getDefault().getImagesPath() + File.separator + image);
		
		setDefaultPageImageDescriptor(ImageDescriptor.createFromImage(wizardBanner));
	}
	
	@Override
	public AbstractList<TransformedTemplate> getTransformedTemplates() {
		AbstractList<TransformedTemplate> transformedTemplates = new ArrayList<TransformedTemplate>();
		
		for(BaseTemplateWizardPage page : getTemplatePages()) {
			for(OriginalTemplate template : page.getTemplates()) {
				try {
					S60Engine engine = new S60Engine(template.getTemplateFile());
					engine.setTransformRules(template.getTransformRules());
				
					transformedTemplates.add(new TransformedTemplate(template.getToBeTransformedFile(),
							engine.getTransformedString()));
				} catch(IOException ioe) {
					SymbianFoundationTemplates.getDefault().showErrorDialog("Error transforming template!", ioe.getMessage());
				}
			}
		}
		
		return transformedTemplates;
	}
}
