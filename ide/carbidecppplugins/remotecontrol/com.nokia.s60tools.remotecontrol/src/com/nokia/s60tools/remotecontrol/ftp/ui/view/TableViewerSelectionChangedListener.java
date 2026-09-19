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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * Handles selection events in ftp table viewer.
 */
public class TableViewerSelectionChangedListener implements
		ISelectionChangedListener {
	
	/**
	 * FtpView instance
	 */
	private FtpView view;
	
	/**
	 * Constructor
	 * @param view FtpView
	 */
	public TableViewerSelectionChangedListener(FtpView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent arg0) {
		// Update toolbar and menu
		view.selectionChanged();
	}

}
