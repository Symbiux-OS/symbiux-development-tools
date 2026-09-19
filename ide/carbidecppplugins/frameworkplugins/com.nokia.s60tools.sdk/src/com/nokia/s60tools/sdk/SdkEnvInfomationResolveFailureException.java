/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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
 
 
package com.nokia.s60tools.sdk;

/**
 * Exception thrown in case something fails when trying
 * find out facts about current SDK/Platform environment.
 */
public class SdkEnvInfomationResolveFailureException extends Exception {
		
	static final long serialVersionUID = -6103977959428981590L;

	/**
	 * Default constructor.
	 */
	public SdkEnvInfomationResolveFailureException(){
		super();
	}

	/**
	 * Constructor with attached message.
	 * @param message Informative message about situation causing the exception.
	 */
	public SdkEnvInfomationResolveFailureException(String message){
		super(message);
	}

}
