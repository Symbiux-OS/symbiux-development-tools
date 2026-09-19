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


package com.nokia.s60tools.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;

import com.nokia.s60tools.ui.internal.S60ToolsUiPlugin;

/**
 * A base action that can be used by inheriting
 * it and by overriding default run()-method.
 * 
 * By sub-classing this action it is easy to create actions for prototyping
 * purposes during the development start-up phases.
 */
public class S60ToolsBaseAction extends Action {

	/**
	 * Image key for default S60 tools action icon
	 */
	public final static String S60TOOLS_ACTION_ICON = "S60TOOLS_ACTION_ICON"; //$NON-NLS-1$		
	
	/**
	 * Constructor.
	 */
	public S60ToolsBaseAction(){
		super();
	}

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
		this(text, tooltip, IAction.AS_PUSH_BUTTON, S60ToolsUiPlugin.getImageDescriptorForKey(S60TOOLS_ACTION_ICON));
	}
	
	
	/**
	 * Constructor
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 * @param buttonStyle Style supported
	 * @param imageDescriptor ImageDescriptor of the image used for the action. 
	 */
	public S60ToolsBaseAction(String text, String tooltip, int buttonStyle, ImageDescriptor imageDescriptor){
		super(text, buttonStyle);
		setToolTipText(tooltip);
		if(imageDescriptor != null){
			setImageDescriptor(imageDescriptor);						
		}
	}
	
}
