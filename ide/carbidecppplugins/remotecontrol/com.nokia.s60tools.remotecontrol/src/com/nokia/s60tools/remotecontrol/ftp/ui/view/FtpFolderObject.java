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
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;

/**
 * Object for storing folder information. 
 */
public class FtpFolderObject extends FtpNamedObject  implements IFtpObject{
	
	/**
	 * Constructor.
	 * @param name Name of folder
	 */
	public FtpFolderObject(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ftp.ui.view.IFtpObject#getImage()
	 */
	public Image getImage() {
		return ImageResourceManager.getImage(ImageKeys.IMG_FOLDER);
	}
}