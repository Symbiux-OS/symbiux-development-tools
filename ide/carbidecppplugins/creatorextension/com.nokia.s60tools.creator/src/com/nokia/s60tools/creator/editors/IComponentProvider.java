/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies). 
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
 
package com.nokia.s60tools.creator.editors;

import java.util.Vector;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.IComponentServices;
import com.nokia.s60tools.creator.components.contact.ContactSet;

/**
 * Interface to provide component related ({@link AbstractComponent}) services.
 */
public interface IComponentProvider {
	
	/**
	 * Gets weather edit or add is launched
	 * @return true if edit is launched, false otherwise.
	 */
	boolean isInEditMode();
	
	/**
	 * Get editable component
	 * @return a Component
	 */
	public AbstractComponent getEditable();
	
	/**
	 * Get components by type
	 * @param type
	 * @return components with selected type
	 */
	public Vector<AbstractComponent> getComponents(String type);
	
	/**
	 * Get listener which can be used to open dialog for creating {@link ContactSet} components. 
	 * @param shell
	 * @param provider
	 * @return listener
	 */
	public SelectionListener getAddNewContactSetComponentListener(final Shell shell,
			final IComponentProvider provider) ;
	

	/**
	 * Get all components
	 * @return components
	 */
	public IComponentServices getComponents();
	
	
}
