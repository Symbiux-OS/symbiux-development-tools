/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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
 
package com.nokia.s60tools.ui;

import java.util.List;


/**
 * Generic clipboard copy handler, supports String arrays and returns one string.
 * Each array items is separated with new line.
 */
public class StringArrayClipboardCopyHandler extends AbstractTextClipboardCopyHandler{
	
	String[] propDataArr = null;

	/** 
	 * Returns a String if only one line of text was selected.
	 * If multiple lines of text was selected, String contains lines separeted with new line <code>"\n"</code>.
	 * @see com.nokia.s60tools.ui.AbstractTextClipboardCopyHandler#buildTextString()
	 */
	protected String buildTextString() {
		StringBuffer strBuf = new StringBuffer();
		
		for (int i = 0; i < propDataArr.length; i++) {			
			strBuf.append(propDataArr[i]); //$NON-NLS-1$
			//If we have more than one line of text, adding new lines between them.
			if(propDataArr.length > 1 && i != (propDataArr.length -1 )){
				strBuf.append("\r\n"); //$NON-NLS-1$
			}
		}
		return strBuf.toString();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.ui.ICopyActionHandler#acceptAndCopy(java.util.List)
	 */
	public boolean acceptAndCopy(List<Object> objectsToCopy) {
		
		try {
			// Trying avoid unnecessary exceptions
			
			if(objectsToCopy.size() > 0){
				if(!(objectsToCopy.get(0) instanceof String)){
					return false;
				}
			}
			else{
				// No objects to copy
				return false;
			}
			propDataArr = (String[]) objectsToCopy.toArray(new String[0]);
			// Class cast succeeded, and we can perform copy
			performCopy();
			return true;
		} catch (ClassCastException e) {
			// We can ignore this, this means just that we d
			// do not suppor the given object type
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
