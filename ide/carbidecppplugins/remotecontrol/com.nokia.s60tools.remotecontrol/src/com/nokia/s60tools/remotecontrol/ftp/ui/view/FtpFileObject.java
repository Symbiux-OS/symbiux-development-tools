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

/**
 * Object for storing file information. 
 */
public class FtpFileObject extends FtpNamedObject  implements IFtpObject{
	
	/**
	 * Size of the file.
	 */
	private final long size;

	/**
	 * Constructor.
	 * @param name Name of file
	 * @param size Size of the file.
	 */
	public FtpFileObject(String name, long size) {
		super(name);
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ftp.ui.view.IFtpObject#getImage()
	 */
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	}

	/**
	 * Gets size of the file in bytes.
	 * @return Size of the file in bytes.
	 */
	public long getSize() {
		return size;
	}
}