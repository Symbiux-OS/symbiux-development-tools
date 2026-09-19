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

/**
 * Contains version number for HTI.
 */
public class HTIVersion {
	
	/**
	 * Major version number
	 */
	private int major;
	
	/**
	 * Minor version number
	 */
	private int minor;
	
	/**
	 * Constructor
	 * @param major Major version number
	 * @param minor Minor version number
	 */
	public HTIVersion(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	/**
	 * Get major version number
	 * @return the major version number
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * Get minor version number
	 * @return the minor version number
	 */
	public int getMinor() {
		return minor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return major + "." + minor; //$NON-NLS-1$
	}
}
