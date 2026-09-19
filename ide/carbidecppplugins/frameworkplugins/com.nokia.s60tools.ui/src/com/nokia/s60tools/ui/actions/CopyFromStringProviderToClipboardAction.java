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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;

import com.nokia.s60tools.ui.ICopyActionHandler;
import com.nokia.s60tools.ui.IStringProvider;
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
 *   public class MyStringProvider implements IStringProvider{
 *      public String getString(){return "myString";}    
 *   }
 *   
 *   ...
 *   
 *   List list = new List(...) //List from org.eclipse.swt.widgets
 *   MyStringProvider myStringProvider = new MyStringProvider();
 *   StringArrayClipboardCopyHandler copyHandler = new StringArrayClipboardCopyHandler();		
 *   Action myCopyAction = new CopyFromStringProviderToClipboardAction(myStringProvider,
 *                                                           new ICopyActionHandler[]{ copyHandler });
 *                                                           
 *   MenuManager menuMgr = new MenuManager("#PopupCopyMenu");
 *   menuMgr.addMenuListener(new IMenuListener() {
 * 			public void menuAboutToShow(IMenuManager manager) {
 *				ThisClass.this.fillViewContextMenu(manager);
 *			}
 *		});
 *	 Menu menu = menuMgr.createContextMenu(list);
 *	 list.setMenu(menu);   
 *
 *  ...
 *
 * 	 private void fillViewContextMenu(IMenuManager manager) {
 *		manager.add(actionCompPropertiesDataCopy);
 *		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
 *	 }  
 *                                                      
 * </pre>
 * </code>
 *
 * @see com.nokia.s60tools.ui.ICopyActionHandler
 * @see com.nokia.s60tools.ui.AbstractTextClipboardCopyHandler
 */
public class CopyFromStringProviderToClipboardAction extends Action{
	
	
	/**
	 * Array of copy handler candidates offering copying services.
	 * The list is gone through until copy handler supporting the selected
	 * objects has been found.
	 */
	private final ICopyActionHandler[] copyHandlerArr;
	private final IStringProvider stringProvider;

	/**
	 * Constructor using default description (Copy) and tooltip text
	 * (Copies the selected information to clipboard).
	 * @param  stringProvider Class which provides a String.
	 * @param copyHandlerArr Array of copy handler candidates offering copying services.
	 */
	public CopyFromStringProviderToClipboardAction(IStringProvider stringProvider,  ICopyActionHandler[] copyHandlerArr){		
		this.stringProvider = stringProvider;
		this.copyHandlerArr = copyHandlerArr;
		setText(Messages.getString("CopyFromTableViewerAction.Copy_Action_Text")); //$NON-NLS-1$
		setToolTipText(Messages.getString("CopyFromTableViewerAction.Copy_Action_Tooltip"));		 //$NON-NLS-1$
	}
	
	/**
	 * Constructor allowing creator to define description and
	 * tooltip text.
	 * @param stringProvider Class which provides a String
	 * @param copyHandlerArr Array of copy handler candidates offering copying services.
	 * @param actionDescrText Action's text that is visible in menus etc.
	 * @param actionTooltipText Hover text shown when mouse cursor is over the action.
	 */
	public CopyFromStringProviderToClipboardAction(IStringProvider stringProvider, ICopyActionHandler[] copyHandlerArr,
									 String actionDescrText, String actionTooltipText){		
		this.stringProvider = stringProvider;
		this.copyHandlerArr = copyHandlerArr;
		setText(actionDescrText);
		setToolTipText(actionTooltipText);		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IAction#run()
	 */	
	public void run() {
		
		List<Object> objList = new ArrayList<Object>(1);
		objList.add(stringProvider.getString());
		for (int i = 0; i < copyHandlerArr.length; i++) {
			ICopyActionHandler handler = copyHandlerArr[i];
			if(handler.acceptAndCopy(objList)){
				// Handler accepted the input
				break;
			}
			
		}
	}
}
