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

package com.nokia.s60tools.hticonnection.preferences;

import java.util.Collection;
import java.util.List;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.core.HtiConnection;

/**
 * Helper class to use HTI API preferences. Use this class for accessing
 * HTI API preferences instead of accessing directly through
 * {@link org.eclipse.jface.util.IPropertyChangeListener.IPreferenceStore}.
 * 
 * However this class should be used via <code>IHtiApiPreferences</code>
 * class, that could be queried from <code>HtiApiActivator</code> with
 * the following method <code>HtiApiActivator.getPreferences()</code>.
 * @see
 */
public class HtiApiPreferences implements IHtiApiPreferences {

	/**
	 * The type of "current" type of connection.
	 */
	public static String SELECTION_ID_CURRENT = "com.nokia.carbide.remoteConnections.currentConnection";
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.preferences.IHtiApiPreferences#initDefaultValues()
	 */
	public void initDefaultValues() {
		// COM port
		if(!HtiApiActivator.getPrefsStore().getDefaultString(HtiApiPreferenceConstants.CONNECTION_ID).
				equals(HtiApiPreferenceConstants.DEFAULT_CONNECTION_ID)){
			
			HtiApiActivator.getPrefsStore().setDefault(HtiApiPreferenceConstants.CONNECTION_ID, 
					HtiApiPreferenceConstants.DEFAULT_CONNECTION_ID);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.preferences.IHtiApiPreferences#getConnectionID()
	 */
	public String getConnectionID() {
		return HtiApiActivator.getPrefsStore().getString(
				HtiApiPreferenceConstants.CONNECTION_ID);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.preferences.IHtiApiPreferences#setConnectionID(java.lang.String)
	 */
	public void setConnectionID(String connectionID) {
		HtiApiActivator.getPrefsStore().setValue(
				HtiApiPreferenceConstants.CONNECTION_ID, connectionID);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.preferences.IHtiApiPreferences#getCurrentConnection()
	 */
	public IConnection getCurrentConnection() {
		// Initializing needed variables.
		String connID = getConnectionID();
		Collection<IConnection> connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		
		// Checking for correct connection.
		for(IConnection conn : connections) {	
			if(conn.getIdentifier().equals(connID)) {
				return conn;
			}
		}
		
		// Connection set in preferences was not found. Checking if there is only one connection.

		List<IConnection> htiConnections = HtiConnection.getInstance().getHTIConnections();
		
		// Do not change default if it is "current connection".
		if (!HtiApiActivator.getPreferences().getConnectionID().equals(HtiApiPreferences.SELECTION_ID_CURRENT) &&
			htiConnections.size() == 1) {
			// Only one compatible connection was found. Using it as default.
			IConnection conn = htiConnections.get(0);
			HtiApiActivator.getPreferences().setConnectionID(conn.getIdentifier());
			return conn;
		}
		
		// Valid connection was not found.
		return null;
	}
}
