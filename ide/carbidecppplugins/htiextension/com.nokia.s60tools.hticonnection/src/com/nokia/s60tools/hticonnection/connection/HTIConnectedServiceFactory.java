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

import java.util.Collection;
import java.util.Collections;

import com.nokia.carbide.remoteconnections.interfaces.AbstractSynchronizedConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;

/**
 * Factory for Connected HTI Services.
 */
public class HTIConnectedServiceFactory implements IConnectedServiceFactory {
	

	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory#createConnectedService(com.nokia.carbide.remoteconnections.interfaces.IService, com.nokia.carbide.remoteconnections.interfaces.IConnection)
	 */
	public IConnectedService createConnectedService(IService service,
			IConnection connection) {
		
		if(getCompatibleConnectionTypeIds(service).contains(connection.getConnectionType().getIdentifier())) {
			return new HTIConnectedService(service, (AbstractSynchronizedConnection)connection);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory#getCompatibleConnectionTypeIds(com.nokia.carbide.remoteconnections.interfaces.IService)
	 */
	public Collection<String> getCompatibleConnectionTypeIds(IService service) {
		if (service instanceof HTIService) {
			return HTIService.getCompatibleConnectionTypeIds();
		}
		return Collections.emptyList();
	}

}
