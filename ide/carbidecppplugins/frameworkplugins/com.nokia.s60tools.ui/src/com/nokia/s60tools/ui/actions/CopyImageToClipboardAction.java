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
package com.nokia.s60tools.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.ui.IImageProvider;
import com.nokia.s60tools.ui.internal.Messages;

/**
 * Action class for copying Image to clipboard
 */
public class CopyImageToClipboardAction extends S60ToolsBaseAction {
	
	
	/**
	 * Image to copy to clipboard
	 */
	private IImageProvider imageProvider;

	/**
	 * Constructor
	 * @param provider image provider that provides {@link Image} and {@link Drawable}
	 * @see com.nokia.s60tools.ui.IImageProvider
	 */
	public CopyImageToClipboardAction(IImageProvider provider) {
		super(Messages.getString("CopyFromTableViewerAction.Copy_Action_Text"), //$NON-NLS-N1
				Messages.getString("CopyFromTableViewerAction.Copy_Action_Tooltip"), //$NON-NLS-N1
				IAction.AS_PUSH_BUTTON, null);
		this.imageProvider = provider;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		performCopy();
	}	
	
	/**
	 * Performs copy action by copying Image to clipboard.
	 */
	private void performCopy(){
		
		//Runnable is used to avoid e.g. pop-up menu to appear in image in case when 
		//image is based on screen capture.
		Runnable runCopy = new Runnable(){

			public void run() {
				if (imageProvider != null && imageProvider.getDrawable() != null && imageProvider.getImage() != null) {
					GC gc = new GC(imageProvider.getDrawable());
					Image image = imageProvider.getImage();
					gc.copyArea(image, 0, 0);
					gc.dispose();
					
					Display disp = Display.getCurrent();
					Clipboard clipBrd  = new Clipboard(disp);
					
					ImageTransfer imageTransfer = ImageTransfer.getInstance();
					clipBrd.setContents(new Object[]{image.getImageData()}, 
							new Transfer[]{imageTransfer});
					clipBrd.dispose();
				}				
			}						
		};
		
		Display.getDefault().asyncExec(runCopy);

	}
	
}
