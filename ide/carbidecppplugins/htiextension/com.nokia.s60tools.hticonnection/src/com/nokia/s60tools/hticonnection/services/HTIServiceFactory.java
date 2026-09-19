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

package com.nokia.s60tools.hticonnection.services;

import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.services.applicationcontrolservice.ApplicationControlService;
import com.nokia.s60tools.hticonnection.services.connectiontestservice.ConnectionTestService;
import com.nokia.s60tools.hticonnection.services.ftpservice.FTPService;
import com.nokia.s60tools.hticonnection.services.keyeventservice.KeyEventService;
import com.nokia.s60tools.hticonnection.services.screencaptureservice.ScreenCaptureService;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Factory class for creating HTI services.
 */
public class HTIServiceFactory {
	
	/**
	 * Gets HTI version to which HTI Connection is currently connected. Or null if connection
	 * is down. Value for this query is got when connection is established and it is
	 * up to date when connection started message comes by {@link com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener}. 
	 * See {@link com.nokia.s60tools.hticonnection.listener.HtiConnectionManager} for more information.
	 * @return HTI Version queried from HTI agent or null if connection is down.
	 */
	public static HTIVersion getCurrentHTIVersion() {
		return HtiConnection.getInstance().getHTIVersion();
	}
	
	/**
	 * Creates an default implementation for {@link IScreenCaptureService}.
	 * ConnectionTestService is used for capturing screens from device.
	 * @param printUtility Used for printing messages.
	 * @return {@link IScreenCaptureService}
	 */
	public static IScreenCaptureService createScreenCaptureService(IConsolePrintUtility printUtility){
		return new ScreenCaptureService(printUtility);
	}
	
	/**
	 * Creates an default implementation for {@link IConnectionTestService}.
	 * ConnectionTestService can be used for testing if connection is ready for requests.
	 * @param printUtility Used for printing messages.
	 * @return {@link IConnectionTestService}
	 */
	public static IConnectionTestService createConnectionTestService(IConsolePrintUtility printUtility){
		return new ConnectionTestService(printUtility);
	}
	
	/**
	 * Creates an default implementation for {@link IFTPService}.
	 * FTPService is used for performing file operations on device
	 * @param printUtility Used for printing messages.
	 * @return {@link IFTPService}
	 */
	public static IFTPService createFTPService(IConsolePrintUtility printUtility){
		return new FTPService(printUtility);
	}
	
	/**
	 * Creates an default implementation for {@link IApplicationControlService}.
	 * ApplicationControlService is used for controlling applications on device
	 * @param printUtility Used for printing messages.
	 * @return {@link IApplicationControlService}
	 */
	public static IApplicationControlService createApplicationControlService(IConsolePrintUtility printUtility){
		return new ApplicationControlService(printUtility);
	}
	
	/**
	 * Creates an default implementation for {@link IKeyEventService}.
	 * Key event service is used for sending key and touch screen events to device.
	 * @param printUtility Used for printing messages.
	 * @return {@link IKeyEventService}
	 */
	public static IKeyEventService createKeyEventService(IConsolePrintUtility printUtility){
		return new KeyEventService(printUtility);
	}
}
