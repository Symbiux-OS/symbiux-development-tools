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

package com.nokia.s60tools.ui.wizards;

/**
 * Common method interface for wizard pages that are 
 * designed to work together with wizard implementations
 * inherited from <code>S60ToolsWizard</code>, which
 * expects all contained pages to adhere to 
 * this interface.
 *
 *@see com.nokia.s60tools.ui.wizards.S60ToolsWizard
 */
public interface IS60ToolsWizardPage {

	/**
	 * This method is called when buttons states needs to 
	 * be recalculated.   
	 * Called by <code>S60ToolsWizard</code> class
	 * when wizard page has been changed.
	 * This method can be also called by listeners
	 * attached to wizard page whenever button
	 * stated needs to be recalculated.
	 *@see com.nokia.s60tools.ui.wizards.S60ToolsWizard#pageChanged
	 */
	public abstract void recalculateButtonStates();

	/**
	 * Sets the initial focus for the wizard page.
	 * Called by <code>S60ToolsWizard</code> class
	 * when wizard page has been changed.
	 *@see com.nokia.s60tools.ui.wizards.S60ToolsWizard#pageChanged
	 */
	public abstract void setInitialFocus();

}