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

package com.nokia.s60tools.hticonnection.actions;

import org.eclipse.jface.action.IAction;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * Action used to select connection from menu.
 */
public class SelectConnectionAction extends S60ToolsBaseAction {

	/**
	 * Connection for this action.
	 */
	private final IConnection connection;

	/**
	 * Constructor.
	 * @param connection Connection to be selected.
	 */
	public SelectConnectionAction(IConnection connection) {
		super(connection.getDisplayName(), Messages.getString("SelectConnectionAction.Tooltip_Text"), IAction.AS_RADIO_BUTTON, null); //$NON-NLS-1$
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.actions.S60ToolsBaseAction#run()
	 */
	public void run() {
		// Starting the connection in own thread to prevent button staying down for a while.
		if(this.isChecked()) {
			Thread startConnection = new StartConnectionThread();
			startConnection.start();
		}
	}
	
	/**
	 * Thread that can be used to start new connection.
	 */
	private class StartConnectionThread extends Thread {

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			HtiConnection.getInstance().stopConnection();
			HtiConnection.getInstance().setCurrentConnection(connection, false);
			HtiConnection.getInstance().startConnection(false);
		}
	};
}
