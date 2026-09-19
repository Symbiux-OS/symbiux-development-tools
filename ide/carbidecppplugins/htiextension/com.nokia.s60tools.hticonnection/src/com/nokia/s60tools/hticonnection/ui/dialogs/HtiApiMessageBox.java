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

package com.nokia.s60tools.hticonnection.ui.dialogs;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.common.ProductInfoRegistry;

/**
 * This class wraps the MessageBox in order to 
 * enhance it with product name, and run it in
 * HTI api plugin's active shell context.
 * 
 * @see org.eclipse.swt.widgets.MessageBox
 */
public class HtiApiMessageBox {

	MessageBox msgBox;

	/**
	 * Constructor.
	 * @param message User visible message.
	 * @param style Style bits.
	 */
	public HtiApiMessageBox(String message, int style){		
		Shell sh = HtiApiActivator.getCurrentlyActiveWbWindowShell();
		msgBox = new MessageBox(sh, style);
		msgBox.setMessage(message);	
		msgBox.setText(ProductInfoRegistry.getProductName());
	}

	/**
	 * Constructor.
	 * @param sh Parent shell for the new instance.
	 * @param message User visible message.
	 * @param style Style bits.
	 */
	public HtiApiMessageBox(Shell sh, String message, int style){		
		msgBox = new MessageBox(sh, style);
		msgBox.setMessage(message);	
		msgBox.setText(ProductInfoRegistry.getProductName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0) {
		return msgBox.equals(arg0);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.MessageBox#getMessage()
	 */
	public String getMessage() {
		return msgBox.getMessage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Dialog#getParent()
	 */
	public Shell getParent() {
		return msgBox.getParent();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Dialog#getStyle()
	 */
	public int getStyle() {
		return msgBox.getStyle();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Dialog#getText()
	 */
	public String getText() {
		return msgBox.getText();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return msgBox.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.MessageBox#setMessage(java.lang.String)
	 */
	public void setMessage(String string) {
		msgBox.setMessage(string);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Dialog#setText(java.lang.String)
	 */
	public void setText(String string) {
		msgBox.setText(string);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return msgBox.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.MessageBox#open()
	 */
	public int open() {
		return msgBox.open();
	}
	
}
