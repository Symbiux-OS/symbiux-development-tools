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


package com.nokia.s60tools.hticonnection.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

import com.nokia.s60tools.hticonnection.resources.ImageKeys;
import com.nokia.s60tools.hticonnection.resources.ImageResourceManager;

/**
 * A base action that can be used by inheriting
 * it and by overriding default run()-method.
 * 
 * The action itself can be used for prototyping
 * during the development start-up phases.
 */
public class S60ToolsBaseAction extends Action {

	/**
	 * Constructor
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 */
	public S60ToolsBaseAction(String text, String tooltip){
		// Creates action by using default push button style.
		this(text, tooltip, IAction.AS_PUSH_BUTTON);
	}

	/**
	 * Constructor
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 * @param buttonStyle Style supported
	 */
	public S60ToolsBaseAction(String text, String tooltip, int buttonStyle){
		this(text, tooltip, IAction.AS_PUSH_BUTTON, ImageKeys.S60TOOLS_ACTION_ICON);
	}
	
	/**
	 * Constructor
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 * @param buttonStyle Style supported
	 * @param imageKey Image of the image used for the action. 
	 *                 Can be set to <code>null</code>, if no image is needed.
	 */
	public S60ToolsBaseAction(String text, String tooltip, int buttonStyle, String imageKey){
		super(text, buttonStyle);
		setToolTipText(tooltip);
		if(imageKey != null){
			setImageDescriptor(ImageResourceManager.
					getImageDescriptor(imageKey));						
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		// Extending classes implement run.
	}
}
