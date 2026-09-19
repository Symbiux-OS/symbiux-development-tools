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

package com.nokia.s60tools.imaker.internal.iqrf.util;

public class IQRFUtil {
	public static boolean equals(String string1, String string2) {
		boolean ret = false;
		if((string1==null)&&(string2==null)) {
			ret=true;
		} else if((string1==null)||(string2==null)) {
			ret=false;
		} else {
			ret = string1.equals(string2);
		}
		return ret;
	}
	public static void main(String[] args) {
		if(equals(null,null)) {
			System.out.println("null==null");
		}
		if(equals(null,"")) {
			System.out.println("null==");
		} else {
			System.out.println("null!=");
		}
		if(equals("","")) {
			System.out.println("\"\"==\"\"");
		}else {
			System.out.println("\"\"==\"\"");
		}
	}
}
