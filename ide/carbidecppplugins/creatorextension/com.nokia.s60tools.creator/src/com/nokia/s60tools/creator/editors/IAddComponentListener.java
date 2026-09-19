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
 
package com.nokia.s60tools.creator.editors;

import org.eclipse.swt.widgets.Listener;

import com.nokia.s60tools.creator.components.AbstractComponent;

public interface IAddComponentListener extends Listener{
	
	/**
	 * Tell listeners that a Component was added
	 * @param a Component, can be <code>null</code>
	 */
	public void componentAdded(AbstractComponent comp);

}
