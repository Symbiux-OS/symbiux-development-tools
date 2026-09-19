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

package com.nokia.s60tools.remotecontrol.screen.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

import com.nokia.s60tools.remotecontrol.screen.ui.view.ScreenView;

/**
 * Action that toggles full screen mode on and off.
 */
public class ZoomAction  extends Action {

	/**
	 * Reference to screen view.
	 */
	private ScreenView screenView;
	/**
	 * Zoom factor for the action.
	 */
	private final ZoomFactor zoomFactor;
	
	/**
	 * Zoom factors supported by Zoom functionality.
	 * All zoom factors keep the original picture ratio.
	 */
	public enum ZoomFactor{
		ZOOM_TO_FIT, 
		ZOOM_TO_HEIGHT, 
		ZOOM_TO_WIDTH, 
		ZOOM_TO_50_PERCENT, 
		ZOOM_TO_100_PERCENT, 
		ZOOM_TO_200_PERCENT, 
		ZOOM_TO_400_PERCENT 
	}

	/**
	 * Constructor
	 * @param view View object that owns the action.
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 */
	public ZoomAction(ScreenView screenView, String text, String tooltip, ZoomFactor zoomFactor){
		super(text, IAction.AS_CHECK_BOX);
		this.zoomFactor = zoomFactor;
		setToolTipText(tooltip);
		this.screenView = screenView;
		if(zoomFactor == screenView.getScreenSettings().getZoomFactor()){
			setChecked(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		screenView.getScreenSettings().setZoomFactor(zoomFactor);
		screenView.updateZoom();
	}

}
