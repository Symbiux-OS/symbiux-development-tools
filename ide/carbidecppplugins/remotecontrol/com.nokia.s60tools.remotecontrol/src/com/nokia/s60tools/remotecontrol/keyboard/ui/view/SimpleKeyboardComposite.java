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

package com.nokia.s60tools.remotecontrol.keyboard.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import com.nokia.s60tools.hticonnection.services.IKeyEventService;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Simple keyboard 
 */
public class SimpleKeyboardComposite extends BaseKeyboardComposite {

	/**
	 * Number of colums
	 */
	private static final int COLUMN_NUM = 9;
	
	/**
	 * Layout for keys.
	 */
	private static final KbKey  SIMPLE_LAYOUT[] = { 
		new KbKey(Messages.getString("SimpleKeyboardComposite.Key_1"), null, IKeyEventService.SCANCODE_NUMPAD_1, 3), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_2"), null, IKeyEventService.SCANCODE_NUMPAD_2, 3), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_3"), null, IKeyEventService.SCANCODE_NUMPAD_3, 3),  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new KbKey(Messages.getString("SimpleKeyboardComposite.Key_4"), null, IKeyEventService.SCANCODE_NUMPAD_4, 3), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_5"), null, IKeyEventService.SCANCODE_NUMPAD_5, 3), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_6"), null, IKeyEventService.SCANCODE_NUMPAD_6, 3),  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new KbKey(Messages.getString("SimpleKeyboardComposite.Key_7"), null, IKeyEventService.SCANCODE_NUMPAD_7, 3), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_8"), null, IKeyEventService.SCANCODE_NUMPAD_8, 3), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_9"), null, IKeyEventService.SCANCODE_NUMPAD_9, 3),  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new KbKey(Messages.getString("SimpleKeyboardComposite.Key_Asterix"), null, IKeyEventService.SCANCODE_NUMPAD_ASTERISK, 3/*, 1, KbKeyFunc.CHR*/), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_0"), null, IKeyEventService.SCANCODE_NUMPAD_0, 3), new KbKey(Messages.getString("SimpleKeyboardComposite.Key_Hash"), null, IKeyEventService.SCANCODE_NUMPAD_HASH, 3/*, 1, KbKeyFunc.SHIFT*/), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$  
		};
	
	/**
	 * Constructor. Supports only <code>SWT.NONE</code> style.
	 * @param parentComposite	Parent composite for the created composite.
	 * @param style				The used style for the composite.
	 * @see SWT#NONE
	 */
	public SimpleKeyboardComposite(Composite parent, int style) {
		super(parent, style);
		
		createKeyboardButtons(this, SIMPLE_LAYOUT);
	}

	/**
	 * Constructor. Uses <code>SWT.NONE</code> style.
	 * @param parentComposite	Parent composite for the created composite.
	 */
	public SimpleKeyboardComposite(Composite parent) {
		this(parent, SWT.NONE);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createControls()
	 */
	protected void createControls() {
		// NOT USED
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createLayout()
	 */
	protected Layout createLayout() {
		return createLayout(COLUMN_NUM);
	}
}
