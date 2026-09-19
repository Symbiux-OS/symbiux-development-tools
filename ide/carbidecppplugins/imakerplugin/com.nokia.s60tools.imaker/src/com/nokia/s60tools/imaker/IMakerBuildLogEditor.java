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

import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * An editor for iMaker build log files.
 * 
 */
public class IMakerBuildLogEditor extends TextEditor {

	public static final String DEFAULT_BUILD_LOG  = Messages.getString("IMakerBuildLogEditor.0"); //$NON-NLS-1$
	public static final String EDITOR_ID          = Messages.getString("IMakerBuildLogEditor.1"); //$NON-NLS-1$
	public static final String EDITOR_CONTEXT     = EDITOR_ID + Messages.getString("IMakerBuildLogEditor.2"); //$NON-NLS-1$
	public static final String NEWLINE            = System.getProperty("line.separator");
	/**
	 * Default constructor
	 */
	public IMakerBuildLogEditor() {
		super();
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setEditorContextMenuId(EDITOR_CONTEXT);
	}

	public void dispose() {
		super.dispose();
	}

	public boolean isEditable() {
		return false;
	}

	public boolean isEditorInputModifiable() {
		return false;
	}

	public boolean isEditorInputReadOnly() {
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		ISourceViewer isv = getSourceViewer();
		isv.addTextListener(new ITextListener() {
			public void textChanged(TextEvent te) {
				ISourceViewer sv = getSourceViewer();
				sv.revealRange(te.getOffset(),te.getLength());
			}
		});
	}
}
