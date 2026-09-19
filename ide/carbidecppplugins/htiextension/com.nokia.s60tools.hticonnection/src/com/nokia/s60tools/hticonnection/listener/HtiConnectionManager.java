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

package com.nokia.s60tools.hticonnection.listener;

import org.eclipse.core.runtime.ListenerList;

import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;

/**
* Singleton class for managing HTI Connection listeners
*/
public class HtiConnectionManager {
	
	/**
	 * Singleton instance.
	 */
	static private HtiConnectionManager instance = null;
	
	/**
	 * Listeners interested in job completions operations.
	 */
	private ListenerList listeners = null;
	
	/**
	 * Private default constructor.
	 */
	private HtiConnectionManager() {
		listeners = new ListenerList();
	}
	
	/**
	 * Only one instance can exist at one time
	 * @return Current instance
	 */
	public static HtiConnectionManager getInstance(){
		if( instance == null ){
			instance = new HtiConnectionManager();
		}
		return instance;		
	}
	
	/**
	 * Add listener.
	 * Listener will be informed that connection has been started, if
	 * connection is has been started before.
	 * @param obj Listener
	 */
	public void addListener(IHtiConnectionListener obj){
		listeners.add(obj);
		if(HtiConnection.getInstance().getConnectionStatus() == ConnectionStatus.CONNECTED) {
			// Listener is informed that connection has already been started.
			obj.connectionStarted();			
		}

	}
	
	/**
	 * Remove listener
	 * @param obj Listener
	 */
	public void removeListener(IHtiConnectionListener obj){
		listeners.remove(obj);
	}
	
	/**
	 * Inform all listeners of connection termination
	 */
	public void informConnectionTerminated() {
		Object[] listenerArray = listeners.getListeners();
		for (int i = 0; i < listenerArray.length; i++) {
			IHtiConnectionListener listenerObj = (IHtiConnectionListener) listenerArray[i];
			listenerObj.connectionTerminated();
		}
	}

	/**
	 * Inform all listeners of connection termination
	 */
	public void informConnectionStarted() {
		Object[] listenerArray = listeners.getListeners();
		for (int i = 0; i < listenerArray.length; i++) {
			IHtiConnectionListener listenerObj = (IHtiConnectionListener) listenerArray[i];
			listenerObj.connectionStarted();
		}
	}
}
