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
import org.eclipse.jface.window.Window;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;
import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.connection.HTIService;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * Action for creating new connection or selecting currently used connection.
 */
@SuppressWarnings("restriction")
public class NewConnectionAction extends S60ToolsBaseAction {

	/**
	 * Constructor.
	 */
	public NewConnectionAction() {
		super(Messages.getString("NewConnectionAction.NewConnection_Action_Text"), //$NON-NLS-1$
				Messages.getString("NewConnectionAction.NewConnection_Action_Tooltip"), //$NON-NLS-1$
				IAction.AS_PUSH_BUTTON,
				null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.actions.S60ToolsBaseAction#run()
	 */
	public void run() {
		// Creating new settings wizard for HTI Connection.
		SettingsWizard wizard = new SettingsWizard(null, new HTIService());
		int result = wizard.open(HtiApiActivator.getCurrentlyActiveWbWindowShell());
		if(result == Window.OK) {
			// Wizard was completed successfully. Starting connection with new connection.
			IConnection connection = wizard.getConnectionToEdit();
			HtiConnection htiConnection = HtiConnection.getInstance();
			if(htiConnection.getConnectionStatus() != ConnectionStatus.SHUTDOWN) {
				htiConnection.stopConnection();
			}
			// Starting new connection.
			htiConnection.setCurrentConnection(connection, false);
			htiConnection.startConnection(false);
		}
	}
}
