/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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
 
 
package com.nokia.s60tools.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;

/**
 * Common base class for wizard pages that are 
 * designed to work together with wizard implementations
 * inherited from <code>S60ToolsWizard</code>, which
 * expects all contained pages to adhere to the
 * interface defined by this base class.
 *
 *@see com.nokia.s60tools.ui.wizards.S60ToolsWizard
 */
public abstract class S60ToolsWizardPage extends WizardPage implements IS60ToolsWizardPage {

	/**
	 * Constructor
	 * @param pageName wizard page name
	 */
	protected S60ToolsWizardPage(String pageName){
		super(pageName);
	}
		
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.ui.wizards.IS60ToolsWizardPage#recalculateButtonStates()
	 */
	public abstract void recalculateButtonStates();
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.ui.wizards.IS60ToolsWizardPage#setInitialFocus()
	 */
	public abstract void setInitialFocus();
	
}
