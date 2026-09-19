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

 
 
package com.nokia.s60tools.metadataeditor.util;

public class MetadataFilenameGenerator {

	/**
	 * Characters that are not allowed in file name
	 */
	private static final String[] forbiddenChars = 
	{ "-", "/", "&", "<", ">" };
	

	
	public static String createMetadataFilename(String APINAme){
		
		String file = APINAme.toLowerCase().trim();
		
		//" +" means more than one white spaces, e.g. "a    b c" -> "a b c"
		file = file.replaceAll(" +", " ");
		file = file.replace('\\', '-');//Changing, so it will be removed
		for (int i = 0; i < forbiddenChars.length; i++) {
			file = file.replaceAll(forbiddenChars[i],  "");
		}
		file = file.replaceAll(" ", "_");
		
		return file + ".metaxml";

	}

}
