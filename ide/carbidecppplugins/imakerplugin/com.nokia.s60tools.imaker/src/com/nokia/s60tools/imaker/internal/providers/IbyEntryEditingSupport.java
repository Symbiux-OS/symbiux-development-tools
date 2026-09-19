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

package com.nokia.s60tools.imaker.internal.providers;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;

import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;

public abstract class IbyEntryEditingSupport extends EditingSupport {
	protected int column;

	public IbyEntryEditingSupport(ColumnViewer viewer, int column) {
		super(viewer);
		this.column = column;
	}

	protected void updateWarning(Object element) {
		IbyEntry entry = getEntry(element);
		entry.setStatusMessage("Todo!");
	}
	
	protected IbyEntry getEntry(Object element) {
		return (IbyEntry) element;
	}	
}
