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

package com.nokia.s60tools.hticonnection.gateway;

import java.io.File;
import java.io.IOException;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.common.ProductInfoRegistry;
import com.nokia.s60tools.hticonnection.connection.HTIService;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.util.HtiApiConsole;
import com.nokia.s60tools.util.cmdline.UnsupportedOSException;

/**
 * This class controls gateway.
 */
public class DataGatewayManager {

	// Datagateway parameters.
	private static final String COM_PORT_PARAMETER_NAME = "-COMPORT="; //$NON-NLS-1$
	private static final String REMOTE_HOST_PARAMETER_NAME = "-REMOTE_HOST="; //$NON-NLS-1$
	private static final String REMOTE_PORT_PARAMETER_NAME = "-REMOTE_PORT="; //$NON-NLS-1$
	private static final String PORT_PARAMETER_NAME = "-port="; //$NON-NLS-1$
	private static final String COMM_CHANNEL_PARAMETER = "-commchannel="; //$NON-NLS-1$
	private static final String COMM_CHANNEL_SERIAL = "SERIAL"; //$NON-NLS-1$
	private static final String COMM_CHANNEL_IP = "IPCOMM"; //$NON-NLS-1$
	
	// This constant is introduced to remove the dependency to class com.freescale.cdt.debug.cw.core.SerialConnectionSettings,
	// which existed only because of the string value of constant SerialConnectionSettings.Port.
	// The string value is the same as SerialConnectionSettings.Port. 
	// It is essentially API and will not be changed.
	private static final String PORT = "port"; //$NON-NLS-1$
	
	
	/**
	 * Class that holdes Datagateway.exe process that is running.
	 */
	private ProcessHolder holder = null;
	/**
	 * This object is used to synchronize process holder.
	 * Separate synchronizer object is needed because holder object changes and can be null.
	 */
	private Object holderSynchronizer = new Object();
	
	/**
	 * Constructor.
	 */
	public DataGatewayManager() {
	}

	/**
	 * Starts gateway with specified connection.
	 * @param connection Connection that is used to start gateway.
	 * @param isTesting True if datagateway is started only to test connection settings.
	 * @return True if succeeded to start datagateway. False otherwise.
	 * @throws UnsupportedOSException 
	 */
	public boolean startGateway(IConnection connection, boolean isTesting) throws UnsupportedOSException {
		try {
			// Getting general parameters.
			String path = HtiApiActivator.getDefault().getPluginInstallPath() +
					File.separator + ProductInfoRegistry.getWin32BinariesRelativePath();
			
			String[] cmd = getDatagatewayCommand(connection, path);
			
			// Making sure that old gateway is stopped.
			stopGateway();
			
			// Starting datagateway. Each gateway has own class that keeps track of the process.
			
			boolean started = false;
			synchronized (holderSynchronizer) {
				holder = new ProcessHolder();
				started = holder.runAsyncCommand(cmd, path);
			}
			
			if(!started){
				// Running command failed. Possible errors are reported in processCreated callback.
				HtiConnection.getInstance().setConnectionStatus(ConnectionStatus.SHUTDOWN);
				return false;
			}
			
			// Started successfully.
			return true;
		} catch (IOException e) {
			HtiApiConsole.getInstance().println(
					Messages.getString("DataGatewayManager.Start_Datagateway_Failed_ConsoleMsg"), //$NON-NLS-1$
					HtiApiConsole.MSG_ERROR);
		} catch (Exception e) {
			HtiApiConsole.getInstance().println(Messages.getString("DataGatewayManager.Start_Datagateway_Failed_Reason_ConsoleMsg") //$NON-NLS-1$
					+ e.getMessage(), HtiApiConsole.MSG_ERROR);
		}
		
		// Exception was thrown. Failed to start gateway.
		return false;
	}
	
	/**
	 * Returns command that is used to run datagateway.
	 * @param connection Connection that is used to start gateway.
	 * @param path Path where datagateway is located.
	 * @return Command that is used to run datagateway.
	 */
	private String[] getDatagatewayCommand(IConnection connection, String path) {
		
		// Getting general parameters.
		String type = connection.getConnectionType().getIdentifier();
		
		if(type.equals(HTIService.SERIAL_TYPE) ||
				type.equals(HTIService.SERIAL_BT_TYPE) ||
				type.equals(HTIService.USB_TYPE)) {
			// Handling serial connections
			
			String comPort = "COM" + connection.getSettings().get(PORT); //$NON-NLS-1$

			String[] cmd = new String[] {
					path + File.separator + ProductInfoRegistry.getDatagatewayExeName(), 
					PORT_PARAMETER_NAME + DataGatewayConstants.PORT,
					COMM_CHANNEL_PARAMETER + COMM_CHANNEL_SERIAL,
					COM_PORT_PARAMETER_NAME+comPort};
			
			return cmd;
			
		}
		else if(type.equals(HTIService.TCPIP_TYPE)) {
			// Handling IP connections.
			String remoteHost = connection.getSettings().get("ipAddress"); //$NON-NLS-1$
			String remotePort = connection.getSettings().get(HTIService.ID);
			
			String[] cmd = new String[] {
					path + File.separator + ProductInfoRegistry.getDatagatewayExeName(), 
					PORT_PARAMETER_NAME + DataGatewayConstants.PORT,
					COMM_CHANNEL_PARAMETER + COMM_CHANNEL_IP,
					REMOTE_HOST_PARAMETER_NAME + remoteHost,
					REMOTE_PORT_PARAMETER_NAME + remotePort };
			
			return cmd;
			
		}
		else {
			throw new RuntimeException(Messages.getString("DataGatewayManager.IncomptibleConnection_ExceptionMsg")); //$NON-NLS-1$
		}
	}
	
	/**
	 * Stops currently running gateway, or does nothing if gateway is not running.
	 */
	public void stopGateway(){
		synchronized (holderSynchronizer) {
			if(holder != null) {
				holder.stopProcess();
				// This process holder is not needed anymore.
				holder = null;
			}
		}
	}
	
	/**
	 * Checks if gateway is ready for use.
	 * @return Returns true if gateway is ready for use.
	 */
	public boolean isReady() {
		synchronized (holderSynchronizer) {
			if(holder == null) {
				return false;
			}
			return holder.isReady();
		}
	}
}
