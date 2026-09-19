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

package com.nokia.s60tools.imaker;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Control;

public class PixelConverter {
	
	private FontMetrics fFontMetrics;

	public int convertHorizontalDLUsToPixels(Control button, int dlus) {
		GC gc = new GC(button);
		gc.setFont(button.getFont());
		fFontMetrics= gc.getFontMetrics();
		gc.dispose();
		return Dialog.convertHorizontalDLUsToPixels(fFontMetrics, dlus);
	}
}
