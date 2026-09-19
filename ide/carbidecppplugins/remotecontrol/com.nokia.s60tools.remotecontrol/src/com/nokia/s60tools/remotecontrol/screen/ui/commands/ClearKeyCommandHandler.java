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

package com.nokia.s60tools.remotecontrol.screen.ui.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.nokia.s60tools.hticonnection.services.IKeyEventService;

/**
 * Command handler for Clear soft key
 */
public class ClearKeyCommandHandler extends BaseCommandHandler {

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.screen.ui.commands.BaseCommandHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		scanCodeKeyPressed(IKeyEventService.SCANCODE_CLEAR);
		return null;
	}
}
