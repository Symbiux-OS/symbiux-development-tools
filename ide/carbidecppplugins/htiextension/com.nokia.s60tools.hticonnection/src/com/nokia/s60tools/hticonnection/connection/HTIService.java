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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nokia.carbide.remoteconnections.interfaces.IRemoteAgentInstallerProvider;
import com.nokia.carbide.remoteconnections.interfaces.IService2;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * Service class for HTI Connections.
 */
public class HTIService implements IService2 {

	/**
	 * Identification for HTI Service.
	 */
	public static final String ID = HTIService.class.getName();
	
	// Compatible connection types.
	public static final String SERIAL_TYPE = "com.nokia.carbide.trk.support.connection.SerialConnectionType"; //$NON-NLS-1$
	public static final String SERIAL_BT_TYPE = "com.nokia.carbide.trk.support.connection.SerialBTConnectionType"; //$NON-NLS-1$
	public static final String USB_TYPE = "com.nokia.carbide.trk.support.connection.USBConnectionType"; //$NON-NLS-1$
	public static final String TCPIP_TYPE = "com.nokia.carbide.connection.TCPIPConnectionType"; //$NON-NLS-1$
	
	/**
	 * Default TCP/IP port
	 */
	public static final String DEFAULT_TCPIP_PORT = "4000"; //$NON-NLS-1$
	/**
	 * Resource string for TCP/IP port. 
	 */
	public static final String IP_PORT = "port"; //$NON-NLS-1$
	
	/**
	 * Gets compatible connection type ids.
	 * @return Compatible connection type ids.
	 */
	public static Collection<String> getCompatibleConnectionTypeIds() {
		List<String> typeIDs = Arrays.asList(new String[] {
				SERIAL_TYPE,
				SERIAL_BT_TYPE,
				USB_TYPE,
				TCPIP_TYPE
				});
		return typeIDs;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IService#getAdditionalServiceInfo()
	 */
	public String getAdditionalServiceInfo() {
		if(HtiConnection.getInstance().getConnectionStatus() != ConnectionStatus.SHUTDOWN) {
			return Messages.getString("HTIService.TestingInfo_Msg"); //$NON-NLS-1$
		}
		return Messages.getString("HTIService.EnsureConnectionInfo_Msg"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IService#getDisplayName()
	 */
	public String getDisplayName() {
		return "HTI"; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IService#getIdentifier()
	 */
	public String getIdentifier() {
		return ID;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IService#getInstallerProvider()
	 */
	public IRemoteAgentInstallerProvider getInstallerProvider() {
		// No installers.
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IService#isTestable()
	 */
	public boolean isTestable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IService2#getDefaults()
	 */
	public Map<String, String> getDefaults() {
		// Returning default port.
		Map<String, String> settings = new HashMap<String, String>();
		settings.put(IP_PORT, DEFAULT_TCPIP_PORT);
		return settings;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IService2#wantsDeviceOS()
	 */
	public boolean wantsDeviceOS() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		// Not needed to implement adapter.
		return null;
	}
}