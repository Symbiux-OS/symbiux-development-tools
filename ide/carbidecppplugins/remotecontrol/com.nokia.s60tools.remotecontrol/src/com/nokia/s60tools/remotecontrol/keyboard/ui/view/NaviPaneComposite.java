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
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Navi keys pane 
 */
public class NaviPaneComposite extends BaseKeyboardComposite {

	/**
	 * Number of colums
	 */
	private static final int COLUMN_NUM = 9;
	
	/**
	 * Layout for keys.
	 */
	private static final KbKey NAVI_LAYOUT[] = {
		new KbKey(Messages.getString("NaviPaneComposite.Key_LeftSof"), null, IKeyEventService.SCANCODE_SOFTLEFT, 3), new KbKey(Messages.getString("NaviPaneComposite.Key_RightSoft"), null, IKeyEventService.SCANCODE_SOFTRIGHT, 3), null, new KbKey(Messages.getString("NaviPaneComposite.Key_Power"), null, IKeyEventService.SCANCODE_POWER, 2), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new KbKey(Messages.getString("NaviPaneComposite.Key_Send"), null, IKeyEventService.SCANCODE_SEND, 2), new KbKey(Messages.getString("NaviPaneComposite.Key_NaviNorth"), ImageKeys.IMG_NAVI_UP, IKeyEventService.SCANCODE_NAVI_NORTH, 2), new KbKey(Messages.getString("NaviPaneComposite.Key_End"), null, IKeyEventService.SCANCODE_END, 2), null, new KbKey(Messages.getString("NaviPaneComposite.Key_Edit"), null, IKeyEventService.SCANCODE_EDIT, 2), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		new KbKey(Messages.getString("NaviPaneComposite.Key_NaviWest"), ImageKeys.IMG_NAVI_LEFT, IKeyEventService.SCANCODE_NAVI_WEST, 2), new KbKey(Messages.getString("NaviPaneComposite.Key_Select"), null, IKeyEventService.SCANCODE_NAVI_CENTERPUSH, 2), new KbKey(Messages.getString("NaviPaneComposite.Key_NaviEast"), ImageKeys.IMG_NAVI_RIGHT, IKeyEventService.SCANCODE_NAVI_EAST, 2), null, new KbKey(Messages.getString("NaviPaneComposite.Key_Voice"), null, IKeyEventService.SCANCODE_NAVI_VOICE, 2), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		new KbKey(Messages.getString("NaviPaneComposite.Key_App"), null, IKeyEventService.SCANCODE_APP, 2), new KbKey(Messages.getString("NaviPaneComposite.Key_NaviSouth"), ImageKeys.IMG_NAVI_DOWN, IKeyEventService.SCANCODE_NAVI_SOUTH, 2), new KbKey(Messages.getString("NaviPaneComposite.Key_Clear"), null, IKeyEventService.SCANCODE_CLEAR, 2), null, new KbKey(Messages.getString("NaviPaneComposite.Key_Camera"), null, IKeyEventService.SCANCODE_NAVI_CAMERA, 2) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		};
	
	/**
	 * Constructor. Supports only <code>SWT.NONE</code> style.
	 * @param parentComposite	Parent composite for the created composite.
	 * @param style				The used style for the composite.
	 * @see SWT#NONE
	 */
	public NaviPaneComposite(Composite parent, int style) {
		super(parent, style);
		
		createKeyboardButtons(this, NAVI_LAYOUT);
	}

	/**
	 * Constructor. Uses <code>SWT.NONE</code> style.
	 * @param parentComposite	Parent composite for the created composite.
	 */
	public NaviPaneComposite(Composite parent) {
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
