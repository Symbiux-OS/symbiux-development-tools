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

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

import com.nokia.s60tools.hticonnection.services.IKeyEventService;
import com.nokia.s60tools.remotecontrol.keyboard.ui.view.KbKey.KbKeyFunc;

/**
 * Qwerty keyboard
 */
public class QwertyKeyboardComposite extends BaseKeyboardComposite {

	/**
	 * Number of colums
	 */
	private static final int COLUMN_NUM = 12;
	
	/**
	 * Is shift pressed down. Affects to button's label.
	 */
	boolean shiftMode = false;
	
	/**
	 * Layout for keys.
	 */
	private static final KbKey  QWERTY_LAYOUT[] = { 
		new KbKey("1", "!", null), new KbKey("2", "\"", null), new KbKey("3", "£", null), new KbKey("4", "$", null), new KbKey("5", "€", null), new KbKey("6", "%", null), new KbKey("7", "&&", null), new KbKey("8", "*", null), new KbKey("9", "(", null), new KbKey("0", ")", null), new KbKey("BS", null, IKeyEventService.SCANCODE_CLEAR, 2, 1), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ //$NON-NLS-16$ //$NON-NLS-17$ //$NON-NLS-18$ //$NON-NLS-19$ //$NON-NLS-20$ //$NON-NLS-21$
		new KbKey("q", "Q", null), new KbKey("w", "W", null), new KbKey("e", "E", null), new KbKey("r", "R", null), new KbKey("t", "T", null), new KbKey("y", "Y", null), new KbKey("u", "U", null), new KbKey("i", "I", null), new KbKey("o", "O", null), new KbKey("p", "P", null), new KbKey("Enter", null, IKeyEventService.SCANCODE_ENTER, 2, 2), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ //$NON-NLS-16$ //$NON-NLS-17$ //$NON-NLS-18$ //$NON-NLS-19$ //$NON-NLS-20$ //$NON-NLS-21$
		new KbKey("a", "A", null), new KbKey("s", "S", null), new KbKey("d", "D", null), new KbKey("f", "F", null), new KbKey("g", "G", null), new KbKey("h", "H", null), new KbKey("j", "J", null), new KbKey("k", "K", null), new KbKey("l", "L", null), new KbKey("m", "M", null),  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ //$NON-NLS-16$ //$NON-NLS-17$ //$NON-NLS-18$ //$NON-NLS-19$ //$NON-NLS-20$
		new KbKey("z", "Z", null), new KbKey("x", "X", null), new KbKey("c", "C", null), new KbKey("v", "V", null), new KbKey("b", "B", null), new KbKey("n", "N", null), new KbKey("m", "M", null), new KbKey(",", "<", null), new KbKey(".", ">", null), new KbKey("/", "?", null), new KbKey("Shift", null, null, 2, 1, KbKeyFunc.SHIFT, true), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ //$NON-NLS-16$ //$NON-NLS-17$ //$NON-NLS-18$ //$NON-NLS-19$ //$NON-NLS-20$ //$NON-NLS-21$
		new KbKey("Chr", null, IKeyEventService.SCANCODE_CHR, 2), new KbKey("+", "=", null), new KbKey("#", "~", null), new KbKey("", null, IKeyEventService.SCANCODE_SPACE, 6, 1), new KbKey("-", "_", null), new KbKey("´", "@", null)   //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
		};
	
	/**
	 * Constructor. Supports only <code>SWT.NONE</code> style.
	 * @param parentComposite	Parent composite for the created composite.
	 * @param style				The used style for the composite.
	 * @see SWT#NONE
	 */
	public QwertyKeyboardComposite(Composite parent, int style) {
		super(parent, style);
		
		createKeyboardButtons(this, QWERTY_LAYOUT);
	}

	/**
	 * Constructor. Uses <code>SWT.NONE</code> style.
	 * @param parentComposite	Parent composite for the created composite.
	 */
	public QwertyKeyboardComposite(Composite parent) {
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

	/**
	 * Toggles shift mode, and adjusts keyboard accordingly.
	 */
	private void toggleShiftMode(){
		shiftMode = !(shiftMode);
		Set<Button> keySet = keyMap.keySet();
		for (Button button : keySet) {
			KbKey kbKey = keyMap.get(button);
			if(kbKey.getKeyFunc() != null){
				// Labels are not adjusted func btns
				continue;
			}
			if(shiftMode){
				String shifKeyLabel = kbKey.getShiftKeyLabel();
				if(shifKeyLabel != null){
					button.setText(shifKeyLabel);					
				}
			}
			else{
				button.setText(kbKey.getLabel());				
			}			
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.ui.view.BaseKeyboardComposite#createButton(org.eclipse.swt.widgets.Composite, com.nokia.s60tools.remotecontrol.keyboard.ui.view.KbKey)
	 */
	protected Button createButton(Composite parent, KbKey key){
		
		Button button = super.createButton(this, key);		
		
		// Shift key needs own key listener because it changes button's layout
		if(key.getKeyFunc() == KbKeyFunc.SHIFT){
			Listener keyPressListener = new Listener(){
				public void handleEvent(Event event) {				
					toggleShiftMode();
				}
			};
			button.addListener(SWT.Selection, keyPressListener);
		}
		
		return button;
	}
}
