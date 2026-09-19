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
 
 
package com.nokia.s60tools.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.ui.S60ToolsUIConstants;

/**
 * Implements a list box dialog with read-only text content.
 * The dialog can be parameterized with title, resizable property,
 * scroll bar owning property, and with size attributes. 
 * Dialog has just OK button which closes the dialog.<br>
 *
 * Usage example:
 *
 * <code>
 * <pre>
 * 
 * // Parent shell
 * Shell sh = Display.getCurrent().getActiveShell()
 * 
 * String dialogTitle = "Product Name";
 * String listBoxContent = "My List Box Content String";
 * 
 * S60ToolsListBoxDialog dlg = new S60ToolsListBoxDialog(sh, 
 *                                                       dialogTitle, 
 *                                                       listBoxContent, 
 *                                                       false, // not resizable
 *                                                       true, // no vertical scroll bar
 *                                                       true,  // has horizontal scroll bar
 *                                                       300,    // default width
 *                                                       250    // default height
 *                                                       );
 * dlg.create();
 * dlg.open();			
 *  
 * </pre> 
 * </code>
 * 
 * It is also possible to enhance the dialog with Cancel button and
 * with optional message for the contents shown in the list box.
 * 
 * <code>
 * <pre>
 * 		
 * // Parent shell
 * Shell sh = Display.getCurrent().getActiveShell()
 * 
 * String dialogTitle = "Product Name";
 * String listBoxContent = "My List Box Content String";
 * String confirmMsg = "Do you want to really perform this operation?";
 * 
 * S60ToolsListBoxDialog dlg = new S60ToolsListBoxDialog(sh, 
 *                                                       dialogTitle, 
 *                                                       listBoxContent, 
 *                                                       false, // not resizable
 *                                                       true,  // no vertical scroll bar
 *                                                       true,  // has horizontal scroll bar
 *                                                       300,   // default width
 *                                                       250    // default height
 *               										 true, 	//Creating Cancel button
 *                                                       confirmMsg // message show above text box
 *                );		 
 *		dlg.create();
 *		int userAnswer = dlg.open();
 *		if(userAnswer == Dialog.OK){
 *			// Performing queried operation only, if user selected OK
 *          ...
 *			}					
 *		}
 *
 * </pre> 
 * </code>
 * 
 * @see org.eclipse.jface.dialogs.Dialog
 */
public class S60ToolsListBoxDialog extends Dialog{

	/**
	 * List box dialog title.
	 */
	private final String dialogTitle;
	
	/**
	 * List box dialog content.
	 */
	private final String listBoxContentString;
	
	/**
	 * List box dialog text control for showing the content.
	 */
	private Text listBoxContentTextControl;
	
	/**
	 * Owning of vertical scroll bar.
	 */
	private final boolean hasVerticalScrollBar;
	
	/**
	 * Owning of horizontal scroll bar.
	 */
	private final boolean hasHorizontalScrollBar;
	
	/**
	 * Preferred width.
	 */
	private final int widthHint;
	
	/**
	 * Preferred height.
	 */
	private final int heightHint;

	/**
	 * Default style for the list box dialog 
	 */
	private static final int DEFAULT_STYLE = SWT.MULTI | SWT.READ_ONLY | SWT.BORDER;

	/**
	 * This is set to <code>true</code> if the dialog has a Cancel button, 
	 * otherwise set to <code>false</code>.
	 */
	private final boolean hasCancelButton;

	/**
	 * This stores optional text that can be shown 
	 * on top of the text box control.
	 */
	private final String optionalTextAboveListBox;
	
	/**
	 * This is the UI control for showing <code>optionalTextAboveListBox</code>.
	 */
	private Label labelControlAboveListBox;

	/**
	 * ID for context sensitive-help, <code>null</code> means not used/supported by default.
	 */
	private String contextHelpID = null;	
		
	/**
	 * Constructor, with OK button only and with no extra Label.
	 * @param parentShell Parent shell.
	 * @param dialogTitle Dialog title.
	 * @param listBoxContent Content to be showed in the dialog.
	 * @param isResizable If <code>true</code>, dialog will be resizable.
	 * @param hasVerticalScrollBar If <code>true</code>, dialog will have vertical scroll bar.
	 * @param hasHorizontalScrollBar If <code>true</code>, dialog will have horizontal scroll bar.
	 * @param widthHint	Preferred width for the dialog.
	 * @param heightHint Preferred height for the dialog.
	 */
	public S60ToolsListBoxDialog(Shell parentShell, 
			                     String dialogTitle,
								 String listBoxContent,
								 boolean isResizable, 
								 boolean hasVerticalScrollBar,
								 boolean hasHorizontalScrollBar,
								 int widthHint,
								 int heightHint) {

		
		this(parentShell,dialogTitle, listBoxContent, isResizable, 
				hasVerticalScrollBar, hasHorizontalScrollBar,
				widthHint,heightHint, false, null );
	}
	
	/**
	 * Constructor, with support for Cancel button, extra label 
	 * but not context-sensitive help ID.
	 * @param parentShell Parent shell.
	 * @param dialogTitle Dialog title.
	 * @param listBoxContent Content to be showed in the dialog.
	 * @param isResizable If <code>true</code>, dialog will be resizable.
	 * @param hasVerticalScrollBar If <code>true</code>, dialog will have vertical scroll bar.
	 * @param hasHorizontalScrollBar If <code>true</code>, dialog will have horizontal scroll bar.
	 * @param widthHint	Preferred width for the dialog.
	 * @param heightHint Preferred height for the dialog.
	 * @param hasCancelButton If <code>true</code>, dialog will have also Cancel button.
	 * @param optionalTextAboveListBox If not <code>null</code>, dialog will have also a label above the list box.
	 */
	public S60ToolsListBoxDialog(Shell parentShell, 
			                     String dialogTitle,
								 String listBoxContent,
								 boolean isResizable, 
								 boolean hasVerticalScrollBar,
								 boolean hasHorizontalScrollBar,
								 int widthHint,
								 int heightHint,
								 boolean hasCancelButton,
								 String optionalTextAboveListBox) {

		this(parentShell,dialogTitle, listBoxContent, isResizable, 
				hasVerticalScrollBar, hasHorizontalScrollBar,
				widthHint,heightHint, hasCancelButton, 
				optionalTextAboveListBox, null );
	}	
	
	
	/**
	 * Constructor, with support for Cancel button, extra label 
	 * and context-sensitive help ID.
	 * @param parentShell Parent shell.
	 * @param dialogTitle Dialog title.
	 * @param listBoxContent Content to be showed in the dialog.
	 * @param isResizable If <code>true</code>, dialog will be resizable.
	 * @param hasVerticalScrollBar If <code>true</code>, dialog will have vertical scroll bar.
	 * @param hasHorizontalScrollBar If <code>true</code>, dialog will have horizontal scroll bar.
	 * @param widthHint	Preferred width for the dialog.
	 * @param heightHint Preferred height for the dialog.
	 * @param hasCancelButton If <code>true</code>, dialog will have also Cancel button.
	 * @param optionalTextAboveListBox If not <code>null</code>, dialog will have also a label above the list box.
	 * @param contextHelpID ID for context sensitive-help, <code>null</code> means not used.
	 */
	public S60ToolsListBoxDialog(Shell parentShell, 
								 String dialogTitle,
								 String listBoxContent,
								 boolean isResizable, 
								 boolean hasVerticalScrollBar,
								 boolean hasHorizontalScrollBar,
								 int widthHint,
								 int heightHint,
								 boolean hasCancelButton,
								 String optionalTextAboveListBox,
								 String contextHelpID) {

		super(parentShell);
		if(isResizable){
			setShellStyle(getShellStyle() | SWT.RESIZE);			
		}		
		this.dialogTitle = dialogTitle;
		this.listBoxContentString = listBoxContent;
		this.hasVerticalScrollBar = hasVerticalScrollBar;
		this.hasHorizontalScrollBar = hasHorizontalScrollBar;
		this.widthHint = widthHint;
		this.heightHint = heightHint;
		
		this.hasCancelButton = hasCancelButton;
		this.optionalTextAboveListBox = optionalTextAboveListBox;
		this.contextHelpID = contextHelpID;		
	}	
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		//If label is set, showing it
		if(optionalTextAboveListBox != null){
			Composite container = new Composite(parent, SWT.NO_FOCUS);
			labelControlAboveListBox = new Label(container, SWT.LEFT);
			container.setLayout(new GridLayout(1,false));
			labelControlAboveListBox.setText(optionalTextAboveListBox);
		}		
		
		Composite dialogAreaComposite = (Composite) super.createDialogArea(parent);		
	
		//
		//Adding custom controls
		//
		final int cols = 1;	  
		GridLayout gdl = new GridLayout(cols, false);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = widthHint;
		gd.heightHint = heightHint;
		dialogAreaComposite.setLayout(gdl);
		dialogAreaComposite.setLayoutData(gd);
		
		int style = DEFAULT_STYLE;
		
		if(hasHorizontalScrollBar){
			style = style | SWT.H_SCROLL;
		}
		
		if(hasVerticalScrollBar){
			style = style | SWT.V_SCROLL;
		}
		
		GridLayout gdl2 = new GridLayout(cols, false);
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		final int margins = 2 * S60ToolsUIConstants.MARGIN_BTW_FRAME_AND_CONTENTS;
		gd2.widthHint = widthHint - margins;
		gd2.heightHint = heightHint - margins;
		
		dialogAreaComposite.setLayoutData(gd2);

		listBoxContentTextControl = new Text(dialogAreaComposite, style);
		listBoxContentTextControl.setText(listBoxContentString);		
		dialogAreaComposite.setLayout(gdl2);
		listBoxContentTextControl.setLayoutData(gd2);				
		
		// Optionally adding context-sensitive help support
		if(contextHelpID != null){
			PlatformUI.getWorkbench().getHelpSystem().setHelp(listBoxContentTextControl, contextHelpID);
		}
		
		return dialogAreaComposite;
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (dialogTitle != null)
            shell.setText(dialogTitle);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
        // Creating OK button
        Button okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
		// Optionally adding context-sensitive help support
		if(contextHelpID != null){
			PlatformUI.getWorkbench().getHelpSystem().setHelp(okButton, contextHelpID);
		}
        //if cancel button also required
        if(hasCancelButton){
            Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL,
                    true);
    		// Optionally adding context-sensitive help support
    		if(contextHelpID != null){
    			PlatformUI.getWorkbench().getHelpSystem().setHelp(cancelButton, contextHelpID);
    		}        	
        }
    }
       
}
