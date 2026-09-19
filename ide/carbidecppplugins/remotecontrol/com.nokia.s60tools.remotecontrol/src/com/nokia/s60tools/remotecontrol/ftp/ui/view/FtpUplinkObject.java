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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Object for storing information of upper folder. 
 */
public class FtpUplinkObject extends FtpNamedObject  implements IFtpObject{
	
	/**
	 * Constructor.
	 */
	public FtpUplinkObject() {
		super(Messages.getString("FtpUplinkObject.Up_One_Level")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ftp.ui.view.IFtpObject#getImage()
	 */
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_UP);
	}
}