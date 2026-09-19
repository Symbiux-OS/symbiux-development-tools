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
import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.hticonnection.resources.ImageKeys;
import com.nokia.s60tools.hticonnection.resources.ImageResourceManager;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * Action for handling start and stopping datagateway.exe.
 */
public class StartStopGatewayAction extends S60ToolsBaseAction {

	private static final String startGatewayMsg = Messages.getString("StartStopGatewayAction.Start_Datagateway_Msg"); //$NON-NLS-1$
	private static final String stopGatewayMsg = Messages.getString("StartStopGatewayAction.Stop_Datagateway_Msg"); //$NON-NLS-1$
	
	/**
	 * Is datagateway running
	 */
	boolean running = false;
	
	/**
	 * Constructor
	 */
	public StartStopGatewayAction() {
		super(startGatewayMsg,
				startGatewayMsg,
				IAction.AS_PUSH_BUTTON,
				ImageKeys.IMG_START_GATEWAY);
		
		setRunning(HtiConnection.getInstance().getConnectionStatus()
					 != ConnectionStatus.SHUTDOWN);
	}

	/**
	 * Set datagateway running status
	 * @param running True if datagateway is running
	 */
	public void setRunning(boolean running) {
		this.running = running;
		updateImage();
	}

	/**
	 * Updates correct image to action depending on status.
	 */
	private void updateImage(){
		if(running){
			// Stopping the gateway.
			setImageDescriptor(ImageResourceManager.
					getImageDescriptor(ImageKeys.IMG_STOP_GATEWAY));
			setText(stopGatewayMsg);
			setToolTipText(stopGatewayMsg);
		} else {
			// Starting the gateway.
			setImageDescriptor(ImageResourceManager.
					getImageDescriptor(ImageKeys.IMG_START_GATEWAY));
			setText(startGatewayMsg);
			setToolTipText(startGatewayMsg);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		
		if(!running){
			updateImage();

			// Starting the connection in own thread to prevent button staying down for a while.
			Thread startConnection = new Thread() {
				/* (non-Javadoc)
				 * @see java.lang.Thread#run()
				 */
				public void run() {
					checkConnection();
					HtiConnection.getInstance().startConnection(false);
				}
			};
			startConnection.start();

		} else {
			updateImage();
			// Stopping the gateway.
			HtiConnection.getInstance().stopConnection();
		}
	}
	
	/**
	 * Checks if there is valid connection set. If there isn't, then tries to find valid
	 * connection that can be used instead.
	 */
	private void checkConnection() {
		IConnection conn = HtiConnection.getInstance().getCurrentConnection();
		if(conn == null) {
			// Checking if there is valid connection, that hasn't been set yet.
			HtiConnection.getInstance().setCurrentConnection(HtiApiActivator.getPreferences().getCurrentConnection(), false);
		}
	}
}