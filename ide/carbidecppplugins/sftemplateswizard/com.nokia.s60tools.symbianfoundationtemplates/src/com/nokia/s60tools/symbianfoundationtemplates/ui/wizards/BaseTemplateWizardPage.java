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
package com.nokia.s60tools.symbianfoundationtemplates.ui.wizards;

import java.util.AbstractList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;

import com.nokia.s60tools.symbianfoundationtemplates.engine.OriginalTemplate;

/**
 * Base template wizard page.
 *
 */
public abstract class BaseTemplateWizardPage extends WizardPage {
	/**
	 * The default constructor.
	 * 
	 * @param title the page title
	 */
	protected BaseTemplateWizardPage(String title) {
		super(title);
	}
	
	/**
	 * Apply the given status to the wizard.
	 * 
	 * @param status the status of the page
	 */
	public void applyStatus(IStatus status) {
		String message = status.getMessage();
	
		switch(status.getSeverity()) {
			case IStatus.OK:
				setErrorMessage(null);
				if(message.length() == 0)
					setMessage(getPageMessage());
				break;
			case IStatus.WARNING:
				setErrorMessage(null);
				setMessage(message, WizardPage.WARNING);
				break;				
			case IStatus.INFO:
				setErrorMessage(null);
				setMessage(message, WizardPage.INFORMATION);
				break;			
			default:
				setErrorMessage(message);
				setMessage(null);
				break;		
		}
	}
	
	/**
	 * Get templates from the page.
	 * 
	 * @return the templates
	 */
	public abstract AbstractList<OriginalTemplate> getTemplates();
	
	/**
	 * Get this pages message for the wizard.
	 * 
	 * @return the message
	 */
	public abstract String getPageMessage();
	
	/**
	 * Get the status of this page.
	 * 
	 * @return the status
	 */
	public abstract IStatus getStatus();

	/**
	 * Save the preferences to the preference store.
	 *
	 */
	protected abstract void savePreferencesToStore();
}
