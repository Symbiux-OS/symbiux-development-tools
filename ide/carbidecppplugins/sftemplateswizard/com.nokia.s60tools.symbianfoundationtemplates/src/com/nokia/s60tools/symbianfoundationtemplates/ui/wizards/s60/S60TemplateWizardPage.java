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

import java.util.AbstractList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;
import com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.BaseTemplateWizardPage;

/**
 * S60 template wizard base page.
 *
 */
public abstract class S60TemplateWizardPage extends BaseTemplateWizardPage implements Listener {
	protected static final IPreferenceStore preferenceStore = SymbianFoundationTemplates.getDefault().getPreferenceStore();
	
	protected final S60TemplatePageType pageType;

	/**
	 * Default constructor.
	 * 
	 * @param pageType the page type
	 */
	public S60TemplateWizardPage(S60TemplatePageType pageType) {
		super(pageType.getTitle());
		
		this.pageType = pageType;
	}
	
	public void handleEvent(Event event) {
		updateStatus();
			
		getWizard().getContainer().updateButtons();
	}

	public IStatus getStatus() {
		for(IStatus status : getStatuses())
			if(status.matches(IStatus.ERROR))
				return status;
		
		return new Status(IStatus.OK, SymbianFoundationTemplates.PLUGIN_ID, 0, "", null);
	}
	
	public String getPageMessage() {
		return pageType.getMessage();
	}
	
	/**
	 * Update the status of the page.
	 *
	 */
	protected void updateStatus() {
		IStatus mostSevere = new Status(IStatus.OK, SymbianFoundationTemplates.PLUGIN_ID, 0, "", null);
		
		for(IStatus status : getStatuses())
			if(status.getSeverity() > mostSevere.getSeverity())
				mostSevere = status;
		
		applyStatus(mostSevere);
		
		updateFields();
	}

	/**
	 * Get the statuses of this page.
	 * 
	 * @return the statuses
	 */
	protected abstract AbstractList<IStatus> getStatuses();
	
	/**
	 * Update the fields of this page.
	 *
	 */
	protected abstract void updateFields();
}
