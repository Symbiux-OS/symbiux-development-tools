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

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;

/**
 * This class defines common structure for all S60 tool wizards.
 * It can be subclasses in order to provide wizards that can
 * utilize these services provided.
 * 
 * This class is planned to be used together with
 * <code>com.nokia.s60tools.ui.wizards.S60ToolsWizardPage</code> 
 * class.
 * 
 * @see com.nokia.s60tools.ui.wizards.S60ToolsWizardPage
 */
public abstract class S60ToolsWizard extends Wizard  implements IPageChangedListener{
	
	/**
	 * Default constructor.
	 */
	protected S60ToolsWizard(){		
		init();
	}

	/**
	 * Constructor allowing to use product-specific banner image.
	 * @param bannerImgDescriptor Banner image descriptor.
	 */
	protected S60ToolsWizard(ImageDescriptor bannerImgDescriptor){
		setDefaultPageImageDescriptor(bannerImgDescriptor);
		init();
	}
	
	/**
	 * Initialized default settings for the wizard.
	 */
	private void init(){		
		//By default there is no wizard help available. Can be overridedn by sub classes.
		setHelpAvailable(false);
		//By default is no progress monitor is needed.  Can be overridedn by sub classes.
		setNeedsProgressMonitor(false);
	}

	/**
	 * Overrides the base class implementation from  
	 * <code>org.eclipse.jface.dialogs.IPageChangedListener</code>.
	 * 
	 * This overridden implementation 
	 * gets current <code>S60ToolsWizardPage</code> instance and calls it
	 * <code>recalculateButtonStates</code> and <code>setInitialFocus</code> methods
	 * thus initializing page into correct initial state.
	 * 
	 * @see com.nokia.s60tools.ui.wizards.S60ToolsWizardPage#recalculateButtonStates
	 * @see com.nokia.s60tools.ui.wizards.S60ToolsWizardPage#setInitialFocus
	 */
	public void pageChanged(PageChangedEvent event) {
		//
		// Updating buttons states when the page is changed
		//
    	IWizardContainer container = getContainer();
    	if(container != null){
    		S60ToolsWizardPage currPage = (S60ToolsWizardPage)container.getCurrentPage();
    		if(currPage != null){
    			currPage.recalculateButtonStates();
    			currPage.setInitialFocus();
    		}
    	}
	}
		
	/**
	 * Overrides the base class implementation from  
	 * <code>org.eclipse.jface.wizard.Wizard</code>
	 * and defines this as abstract method, therefore enforcing
	 * derived class to provide an implementation, which
	 * would be otherwise optional.
	 */
    public abstract void addPages();
		
}
