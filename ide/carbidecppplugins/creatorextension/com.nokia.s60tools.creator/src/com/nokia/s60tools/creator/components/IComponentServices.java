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

package com.nokia.s60tools.creator.components;

import java.util.Map;
import java.util.Vector;

/**
 * Interface to implement some component related functions
 */
public interface IComponentServices {

	/**
	 * If that component is referenced by any other component
	 * @param component
	 * @return true if any other component references to this component
	 */
	public abstract boolean isReferencedByAnotherComponent(
			AbstractComponent component);

	/**
	 * @return all components
	 */
	public Map<String, Vector<AbstractComponent>> getComponents();

	/**
	 * Get Component by type and component String
	 * @param componentToString
	 * @return a Component or null if not found
	 */
	public AbstractComponent getComponentByComponentString(
			String componentToString);

	/**
	 * Get Components of selected type
	 * @param compoentType, component type as it is in {@link AbstractComponent} subclasses. Use
	 * <code>AbstractComponent.getType()</code> to get component id.
	 * @return a component, null if not exist
	 */
	public Vector<AbstractComponent> getComponents(String type);

}