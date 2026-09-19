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
 
 
package com.nokia.s60tools.ui;

import java.util.List;

/**
 * Defines common interface used by copy handler
 * clients. Clients can have a set of possible 
 * copy handler implementations, and this
 * interface helps to select correct implementation
 * for the object type at hand.
 * 
 * Copy handlers are implemented by subclassing
 * <code>AbstractTextClipboardCopyHandler</code> class .
 * 
 * There should be only one copy handler defined per object 
 * type.
 * 
 * @see com.nokia.s60tools.ui.AbstractTextClipboardCopyHandler
 */
public interface ICopyActionHandler {
	
	/**
	 * This methods tests if the given object type is supported, 
	 * and copies object data to the clipboard if the object 
	 * type was supported.
	 * @param objectsToCopyList List of objects to perform copy for.
	 * @return Returns <code>true</code> if the object type was
	 *         recognized and copy was performed, otherwise returns
	 *         <code>false</code>.
	 */
	public boolean acceptAndCopy(List<Object> objectsToCopyList);
}
