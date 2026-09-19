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

import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;

/**
 * Service that contains interface for controlling applications on device.
 */
public interface IApplicationControlService {
	
	/**
	 * Start an application defined by its name. Optional DocumentName parameter 
	 * can be specified to pass it to the application started.
     * @param programName name of the executable (with full path if necessary)
     * @param docName name of the document passed to started application
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void startApplicationByName(String programName, String docName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException;

	/**
	 * Starting a process with parameters.
	 * @param programName name of the executable (with full path if necessary)
	 * @param parameters must not be <code>null</code>
	 * @param timeout  Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ConnectionException Connection failed
	 */
	public void startProcess(String programName, String parameters, long timeout) throws ServiceShutdownException, HTIException, ConnectionException;
	
	
	/**
	 * Start an application defined by its Uid. Optional DocumentName parameter 
	 * can be specified to pass it to the application started.
     * @param uid Uid of the executable
     * @param docName name of the document passed to started application
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void startApplicationByUid(int uid, String docName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Get a status of an application defined by name.
	 * If status is AppStatus.RUNNING, then application is running.
	 * If status is AppStatus.NOT_FOUND, then application is not running.
	 * @param programName name of the executable (with full path if necessary)
     * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
     * @return Information of application status
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public AppStatus getApplicationStatusByName(String programName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException;

	/**
	 * Get a status of an application defined by UID.
	 * If status is AppStatus.RUNNING, then application is running.
	 * If status is AppStatus.NOT_FOUND, then application is not running.
	 * @param uid UID of the executable
     * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return Information of application status
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public AppStatus getApplicationStatusByUid(int uid, long timeout) throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Stop an application defined by its name.
	 * @param programName name of the executable (with full path if necessary)
     * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void stopApplicationByName(String programName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Stop an application defined by its UID.
	 * @param uid UID of the executable
     * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void stopApplicationByUid(int uid, long timeout) throws ServiceShutdownException, HTIException, ConnectionException;
}

