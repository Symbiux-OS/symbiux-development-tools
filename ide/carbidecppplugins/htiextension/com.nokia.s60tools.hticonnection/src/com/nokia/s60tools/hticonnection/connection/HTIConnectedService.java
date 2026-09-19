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

package com.nokia.s60tools.hticonnection.connection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.Version;

import com.nokia.carbide.remoteconnections.interfaces.AbstractConnectedService2;
import com.nokia.carbide.remoteconnections.interfaces.AbstractSynchronizedConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.services.HTIVersion;

/**
 * Connected service for HTI to contain connection information.
 */
public class HTIConnectedService extends AbstractConnectedService2 {
		
	/**
	 * Constructor.
	 * @param service Service that is connected.
	 * @param connection Connection that is connected.
	 */
	public HTIConnectedService(IService service,
			AbstractSynchronizedConnection connection) {
		super(service, connection);
		currentStatus = new Status();
		
		currentStatus.setEStatus(EStatus.DOWN, Messages.getString("HTIConnectedService.NotConnected_ShortMsg"), ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.AbstractConnectedService2#runTestStatus(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected TestResult runTestStatus(IProgressMonitor monitor) {
		if(!externalTesting) {
			// Not using automatic testing as it would take too much resources. Returning currently known status.
			IStatus status = getStatusFromHtiConnection();
			return new TestResult(status.getEStatus(), status.getShortDescription(), status.getLongDescription());
		}
			
		// Testing connection when user has ordered testing command from the settings wizard.
		monitor.beginTask("Testing HTI Service", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		IStatus status = HtiConnection.getInstance().testConnection(connection);
		
		monitor.done();
		
		return new TestResult(status.getEStatus(), status.getShortDescription(), status.getLongDescription());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedService#getStatus()
	 */
	public IStatus getStatus() {
		return currentStatus;
	}
	
	/**
	 * Returns status of this connection.
	 * @return Status of this connection.
	 */
	public IStatus getStatusFromHtiConnection() {
		HtiConnection htiConnection = HtiConnection.getInstance();
		
		HTIConnectionStatus status = null;
		if(connection.equals(htiConnection.getCurrentConnection())) {
			// This connection is active, getting status.
			ConnectionStatus connStatus = htiConnection.getConnectionStatus();
			switch (connStatus) {
			case CONNECTED:
				HTIVersion version = HtiConnection.getInstance().getHTIVersion();
				status = new HTIConnectionStatus(this,
						EStatus.UP,
						Messages.getString("HTIConnectedService.Connected_ShortMsg"), //$NON-NLS-1$
						Messages.getString("HTIConnectedService.ConnectedToHti_LongMsg") + version.toString()); //$NON-NLS-1$
				break;
			case CONNECTING:
				status = new HTIConnectionStatus(this,
						EStatus.UP,
						Messages.getString("HTIConnectedService.TryingToConnect_ShortMsg"), //$NON-NLS-1$
						""); //$NON-NLS-1$
				break;
			case SHUTDOWN:
				status = new HTIConnectionStatus(this,
						EStatus.DOWN,
						Messages.getString("HTIConnectedService.NotConnected_ShortMsg"), //$NON-NLS-1$
						""); //$NON-NLS-1$
				break;
			case TESTING:
				status = new HTIConnectionStatus(this, EStatus.UP, Messages.getString("HTIConnectedService.Testing_ShortMsg"), ""); //$NON-NLS-1$ //$NON-NLS-2$
				break;
			}
		} 
		if(status == null) {
			// This connection is not active.
			status = new HTIConnectionStatus(this, EStatus.DOWN, Messages.getString("HTIConnectedService.NotConnected_ShortMsg"), "");	 //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return status;
	}
	
	/**
	 * Refreshes current status.
	 */
	public void refreshStatus() {
		IStatus status = getStatusFromHtiConnection();
		
		currentStatus.setEStatus(status.getEStatus(), status.getShortDescription(), status.getLongDescription());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedService#setDeviceOS(java.lang.String, org.osgi.framework.Version)
	 */
	public void setDeviceOS(String familyName, Version version) {
		// Not implemented.
	}
}
