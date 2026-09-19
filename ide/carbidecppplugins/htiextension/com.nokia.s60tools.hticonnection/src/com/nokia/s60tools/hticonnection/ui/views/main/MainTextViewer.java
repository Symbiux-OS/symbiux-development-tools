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

package com.nokia.s60tools.hticonnection.ui.views.main;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * Viewer for showing text output.
 */
public class MainTextViewer extends TextViewer implements IDocumentListener {
	
	/**
	 * View font
	 */
	private String FONT = "Arial"; //$NON-NLS-1$
	/**
	 * View font size
	 */
	private final int FONT_SIZE = 10;
	/**
	 * Scroll lock state.
	 */
	private boolean scrollLock = false;
	
	/**
	 * Constructor.
	 * @param parent Parent composite.
	 * @param styles Style options.
	 */
	public MainTextViewer(Composite parent, int styles){
		super(parent, styles);		
		setEditable(false);

		IDocument document = LogDocument.getInstance();
		document.addDocumentListener(this);
		
		// Creating input for viewer.
		setInput(document);
	}
	
	/**
	 * Initializing settings for viewer.
	 */
	public void initializeSettings(){
		// Setting correct font.
		Font font = new Font(getControl().getDisplay(),
				new FontData(FONT, FONT_SIZE, SWT.NORMAL));
		getTextWidget().setFont(font);
		
		// Setting view to the end of document.
		setTopIndex(getBottomIndex());
	}
	
	/**
	 * Sets scroll lock on or off.
	 * @param scrollLockState True if setting scroll lock on. False if setting it off.
	 */
	public void setScrollLock(boolean scrollLockState){
		scrollLock = scrollLockState;
	}
	
	/**
	 * Clears text from console.
	 */
	public void clearScreen(){
		// Clearing screen and.
		getDocument().set(""); //$NON-NLS-1$
	}

	/**
	 * Selects all text from console.
	 */
	public void selectAllText() {
		IDocument document = getDocument();
		setSelectedRange(0, document.getLength());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	public void documentAboutToBeChanged(DocumentEvent event) {
		// Not needed.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	public void documentChanged(DocumentEvent event) {
		if(!scrollLock){
			// Scrolling text down to correct location.
			setTopIndex(getDocument().getNumberOfLines());
		}
	}

	/**
	 * Returns currently selected text.
	 * @return Currently selected text or null if getting selection failed.
	 */
	public String getSelectedText() {
		IDocument document = getDocument();
		Point range = getSelectedRange();
		try {
			// Getting text.
			return document.get(range.x, range.y);
		} catch (BadLocationException e) {
			// Returning null to mean empty string if get fails.
			e.printStackTrace();
		}
		return null;
	}
}
