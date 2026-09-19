/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

 
 
package com.nokia.s60tools.metadataeditor.xml;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class APIIDGenerator {

	/**
	 * Calculates unique id (MD5 checksum) from APIName and returns it.
	 * @param APIName
	 * @return MD5 checksum from APIName
	 * @throws NoSuchAlgorithmException
	 */
	public String getID(String APIName) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		StringBuffer chekcsum = new StringBuffer();
		byte[] bytes = md.digest(APIName.getBytes());

		for (int i = 0; i < bytes.length; i++) {
			String hexStr = Integer.toHexString(0xff & bytes[i]);
			if (hexStr.length() < 2) {
				chekcsum.append("0");
			}
			chekcsum.append(hexStr);
		}
		return chekcsum.toString();
	}
	
	
}
