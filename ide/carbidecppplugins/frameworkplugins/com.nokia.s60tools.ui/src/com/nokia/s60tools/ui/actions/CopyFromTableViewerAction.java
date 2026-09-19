/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.nokia.s60tools.ui.ICopyActionHandler;
import com.nokia.s60tools.ui.internal.Messages;

/**
 * This is an example of reusable copy handler action
 * that utilizes <code>ICopyActionHandler</code> interface
 * and acts as client for a concrete copy handler subclassed
 * from <code>AbstractTextClipboardCopyHandler</code> class.
 * 
 * Usage:
 * 
 * <code>
 * <pre>
 * 
 *   TableViewer myTableViewer;
 *   Action myTableViewerCopyAction;
 *   MyConcreteClipboardCopyHandler copyHandler = new MyConcreteClipboardCopyHandler();		
 *   myTableViewerCopyAction = new CopyFromTableViewerAction(myTableViewer,
 *                                                           new ICopyActionHandler[]{ copyHandler });
 * </pre>
 * </code>
 *
 * @see com.nokia.s60tools.ui.ICopyActionHandler
 * @see com.nokia.s60tools.ui.AbstractTextClipboardCopyHandler
 */
public class CopyFromTableViewerAction extends Action{
	
	/**
	 * Table viewer from which to get objects to be copied
	 * when action is triggered.
	 */
	private final TableViewer viewer;
	
	/**
	 * Array of copy handler candidates offering copying services.
	 * The list is gone through until copy handler supporting the selected
	 * objects has been found.
	 */
	private final ICopyActionHandler[] copyHandlerArr;

	/**
	 * Constructor using default description (Copy) and tooltip text
	 * (Copies the selected information to clipboard).
	 * @param viewer Table viewer from which to get objects to be copied.
	 * @param copyHandlerArr Array of copy handler candidates offering copying services.
	 */
	public CopyFromTableViewerAction(TableViewer viewer, ICopyActionHandler[] copyHandlerArr){		
		this.viewer = viewer;
		this.copyHandlerArr = copyHandlerArr;
		setText(Messages.getString("CopyFromTableViewerAction.Copy_Action_Text")); //$NON-NLS-1$
		setToolTipText(Messages.getString("CopyFromTableViewerAction.Copy_Action_Tooltip"));		 //$NON-NLS-1$
	}
	
	/**
	 * Constructor allowing creator to define description and
	 * tooltip text.
	 * @param viewer Table viewer from which to get objects to be copied.
	 * @param copyHandlerArr Array of copy handler candidates offering copying services.
	 * @param actionDescrText Action's text that is visible in menus etc.
	 * @param actionTooltipText Hover text shown when mouse cursor is over the action.
	 */
	public CopyFromTableViewerAction(TableViewer viewer, ICopyActionHandler[] copyHandlerArr,
									 String actionDescrText, String actionTooltipText){		
		this.viewer = viewer;
		this.copyHandlerArr = copyHandlerArr;
		setText(actionDescrText);
		setToolTipText(actionTooltipText);		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IAction#run()
	 */	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public void run() {
		
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		List<Object> objList = selection.toList();
		for (int i = 0; i < copyHandlerArr.length; i++) {
			ICopyActionHandler handler = copyHandlerArr[i];
			if(handler.acceptAndCopy(objList)){
				// Handler accepted the input
				break;
			}
			
		}
	}
}
