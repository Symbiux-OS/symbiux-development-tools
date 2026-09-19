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

package com.nokia.s60tools.imaker;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.Bundle;

public class StatusHandler {
	
	private static IStatus createStatus(int severity, String message, Throwable exception) {
		Bundle bundle = IMakerPlugin.getDefault().getBundle();
		String pluginId = (String)bundle.getHeaders().get(Messages.getString("StatusHandler.0")); //$NON-NLS-1$
		IStatus status = new Status(severity, pluginId, message, exception);
		return status;
	}
	
	public static void handle(int severity, String message, Throwable exception) {
		IStatus status = createStatus(severity, message, exception);
		StatusManager.getManager().handle(status,StatusManager.SHOW);
	}
}
