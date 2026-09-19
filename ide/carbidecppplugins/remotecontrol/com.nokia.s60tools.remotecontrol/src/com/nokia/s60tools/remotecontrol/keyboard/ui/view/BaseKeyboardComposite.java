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

package com.nokia.s60tools.remotecontrol.keyboard.ui.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite;

/**
 * Base class for keyboards 
 */
public class BaseKeyboardComposite extends AbstractUiFractionComposite {

	/**
	 * Minimum width for button.
	 */
	private static final int MINIMUN_KB_WIDTH = 20;
	
	/**
	 * Key list for accessing key functionalities.
	 */
	protected Map<Button, KbKey> keyMap;
	
	/**
	 * Keyboard mediator to delegate events to.
	 */
	private IKeyboardMediator keyboardMediator;
	
	/**
	 * Constructor. Supports only <code>SWT.NONE</code> style.
	 * @param parentComposite	Parent composite for the created composite.
	 * @param style				The used style for the composite.
	 * @see SWT#NONE
	 */
	public BaseKeyboardComposite(Composite parent, int style) {
		super(parent, style);
		keyboardMediator = RemoteControlActivator.getKeyboardMediator();
		keyboardMediator.registerKeyboardMediatorClient(this);
		
		// Buttons are initialized after IKeyboardMediator because IKeyboardMediator 
		// is needed on listeners.
		// Key map has to be created before calling create method.
		keyMap = new HashMap<Button, KbKey>();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	public void dispose() {
		super.dispose();
		keyboardMediator.unregisterKeyboardMediatorClient(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createControls()
	 */
	protected void createControls() {
		// NOT USED
	}

	/**
	 * Initializes GridLayout
	 * @param numColums Number of columns
	 * @return Layout
	 */
	protected Layout createLayout(int numColums) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = numColums;
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 1;
		gridLayout.horizontalSpacing = 1;
		gridLayout.verticalSpacing = 1;
		gridLayout.makeColumnsEqualWidth = true;
		return gridLayout;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createLayout()
	 */
	protected Layout createLayout() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createLayoutData()
	 */
	protected Object createLayoutData() {
		return null;
	}
	
	/**
	 * Creating keyboard buttons.
	 * @param keyboard Keyboard composite
	 * @param keys Keys to create
	 */
	protected void createKeyboardButtons(Composite keyboard, KbKey[] keys) {
		for (int i = 0; i < keys.length; i++) {
			KbKey key = keys[i];
			if (key == null) {
				// Null key represents empty cell. Let's create space for it
				createSpace(keyboard);
			} else {
				Button btn = createButton(keyboard, key);	
				keyMap.put(btn, key);
			}
		}
	}
	
	/**
	 * Creating single button with listener that sends key press events to service.
	 * @param parent Parent component
	 * @param key Key data for this button
	 */
	protected Button createButton(Composite parent, KbKey key){
		
		int style = SWT.PUSH;
		if(key.isToggle()){
			style = SWT.TOGGLE;
		}
		
		final Button button = new Button(parent, style);
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.grabExcessHorizontalSpace = true;
		gridData.minimumWidth = MINIMUN_KB_WIDTH;
		
		if(key.getHorizontalSpan() > 1){
			gridData.horizontalSpan = key.getHorizontalSpan();			
		}	
		if(key.getVerticalSpan() > 1){
			gridData.verticalSpan = key.getVerticalSpan();	
		}
		button.setLayoutData(gridData);
		if(key.getImageKey() != null){
			Image img = ImageResourceManager.getImage(key.getImageKey());
			button.setImage(img);
		}
		else{
			button.setText(key.getLabel());			
		}
		
		// Add listeners for this key
		// Keyboard can be used by mouse or PC keyboard
		button.addKeyListener(new KeyboardKeyListener(key, this, keyboardMediator));
		button.addMouseListener(new KeyboardMouseListener(key, this, keyboardMediator));
		return button;
	}
	
	/**
	 * Creating empty space
	 * @param parent Parent component
	 */
	protected void createSpace(Composite parent){
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.grabExcessHorizontalSpace = true;
		gridData.minimumWidth = MINIMUN_KB_WIDTH;
		
		// Label without text reserves space from grid.
		// Looks like there is not any component
		Label label = new Label(parent, SWT.SIMPLE);
		gridData.horizontalSpan = 1;
		gridData.verticalSpan = 1;
		label.setLayoutData(gridData);
	}
}
