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

import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;

/**
 * Contains status for connected service.
 */
public class HTIConnectionStatus implements IStatus {

	/**
	 * Status for connected service.
	 */
	private final EStatus status;
	/**
	 * Short status description.
	 */
	private final String shortDesc;
	/**
	 * Long status description.
	 */
	private final String longDesc;
	/**
	 * Connected service.
	 */
	private final IConnectedService connectedService;

	/**
	 * Constructor.
	 * @param connectedService Connected service.
	 * @param status Status for connected service.
	 * @param shortDesc Short status description.
	 * @param longDesc Long status description.
	 */
	public HTIConnectionStatus(IConnectedService connectedService, EStatus status, String shortDesc, String longDesc) {
		this.connectedService = connectedService;
		this.status = status;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus#getConnectedService()
	 */
	public IConnectedService getConnectedService() {
		return connectedService;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus#getEStatus()
	 */
	public EStatus getEStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus#getLongDescription()
	 */
	public String getLongDescription() {
		return longDesc;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus#getShortDescription()
	 */
	public String getShortDescription() {
		return shortDesc;
	}
}
