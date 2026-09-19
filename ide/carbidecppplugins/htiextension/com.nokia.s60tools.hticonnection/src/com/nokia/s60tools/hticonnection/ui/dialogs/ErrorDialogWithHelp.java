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

package com.nokia.s60tools.hticonnection.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.HtiConnectionHelpContextIDs;
import com.nokia.s60tools.hticonnection.common.ProductInfoRegistry;

/**
 * Message box that contains help tray button at the left bottom corner of dialog.
 * Help contains information about different error situations.
 */
public class ErrorDialogWithHelp extends TrayDialog implements SelectionListener {

	/**
	 * Message to be shown in dialog.
	 */
	private final String message;
	/**
	 * Possible No button, or null stays as null if not used.
	 */
	private Button noButton = null;
	/**
	 * Possible Yes button, or null stays as null if not used.
	 */
	private Button yesButton = null;
	/**
	 * Possible Ok button, or null stays as null if not used.
	 */
	private Button okButton = null;
	/**
	 * Style bits for message box.
	 */
	private final int style;
	/**
	 * Marginal used in message box between items and at the sides.
	 */
	private final int DEFAULT_MARGIN = 12;
	/**
	 * Amount of columns in dialog area.
	 */
	private int DIALOG_COLUMN_COUNT = 2;
	/**
	 * Context sensitive help that is used as default for this dialog.
	 */
	private final String DEFAULT_CONTEXT_HELP = HtiConnectionHelpContextIDs.HTI_CONNECTION_ERROR_MESSAGE; 
	
	/**
	 * Constructor. Use SWT.YES, SWT.NO, and SWT.OK to define buttons in dialog.
	 * Dialog.open() will return code of which button was pressed. 
	 * @param message User visible message.
	 * @param style Style bits. Only SWT.YES, SWT.NO, and SWT.OK are supported
	 * currently.
	 */
	public ErrorDialogWithHelp(String message, int style){
		super(HtiApiActivator.getCurrentlyActiveWbWindowShell());
		this.message = message;
		this.style = style;
	}
	
	/**
	 * Constructor. Use SWT.YES, SWT.NO, and SWT.OK to define buttons in dialog.
	 * Dialog.open() will return code of which button was pressed. 
	 * @param message User visible message.
	 * @param shell Parent shell for the new instance.
	 * @param style Style bits. Only SWT.YES, SWT.NO, and SWT.OK are supported
	 * currently.
	 */
	public ErrorDialogWithHelp(String message, Shell shell, int style) {
		super(shell);
		this.message = message;
		this.style = style;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		// Creating dialog area composite
		Composite dialogAreaComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(DIALOG_COLUMN_COUNT, false);
		layout.marginHeight = DEFAULT_MARGIN ;
		layout.marginWidth = DEFAULT_MARGIN;
		layout.horizontalSpacing = DEFAULT_MARGIN;
		dialogAreaComposite.setLayout(layout);
		dialogAreaComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Creating image.
		Label messageLabel = new Label(dialogAreaComposite, SWT.HORIZONTAL);
		messageLabel.setImage(Display.getDefault().getSystemImage(SWT.ICON_ERROR));
		messageLabel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));

		// Creating label.
		messageLabel = new Label(dialogAreaComposite, SWT.HORIZONTAL | SWT.LEFT);
		messageLabel.setText(message);
		messageLabel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
		
		// Setting context help ID.
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialogAreaComposite, HtiConnectionHelpContextIDs.HTI_CONNECTION_ERROR_MESSAGE);
		
		return dialogAreaComposite;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	    // Creating Ok button.
		if((style & SWT.OK) == SWT.OK) {
			okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
			okButton.addSelectionListener(this);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(okButton, DEFAULT_CONTEXT_HELP);
		}
		
        // Creating Yes button
		if((style & SWT.YES) == SWT.YES) {
			yesButton = createButton(parent, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, true);
			yesButton.addSelectionListener(this);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(yesButton, DEFAULT_CONTEXT_HELP);
		}
		
        //Creating No button
		if((style & SWT.NO) == SWT.NO) {
			noButton = createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, false);
			noButton.addSelectionListener(this);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(noButton, DEFAULT_CONTEXT_HELP);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		// Not calling super.configureShell to prevent icon showing up.
		// This is to keep message layout similar to other messages.
		
		// Setting default layout.
		Layout layout = getLayout();
		if (layout != null) {
			shell.setLayout(layout);
		}
		
		// Setting title.
		shell.setText(ProductInfoRegistry.getProductName());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// Not implemented.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		// Handling button selected events.
		if(yesButton != null && e.getSource() == yesButton) {
			setReturnCode(SWT.YES);
			this.close();
		}
		else if(noButton != null && e.getSource() == noButton) {
			setReturnCode(SWT.NO);
			this.close();
		}		
		else if(okButton != null && e.getSource() == okButton) {
			setReturnCode(SWT.OK);
			this.close();
		}		
	}
}
