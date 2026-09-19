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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import org.eclipse.swt.graphics.Image;

import com.nokia.s60tools.hticonnection.services.DriveInfo;
import com.nokia.s60tools.hticonnection.services.DriveInfo.MediaType;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;

/**
 * Object for storing drive information. 
 */
public class FtpDriveObject extends FtpNamedObject  implements IFtpObject{
	
	/**
	 * Connection status provider.
	 */
	private final IConnectionStatusProvider connectionStatusProvider;
	
	/**
	 * Information of drive
	 */
	private final DriveInfo driveInfo;

	/**
	 * Constructor.
	 * @param name Name of drive
	 * @param driveInfo Information of drive
	 * @param connectionStatusProvider Provides connection status. 
	 */
	public FtpDriveObject(String name, DriveInfo driveInfo, IConnectionStatusProvider connectionStatusProvider) {
		super(name);
		this.driveInfo = driveInfo;
		this.connectionStatusProvider = connectionStatusProvider;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ftp.ui.view.IFtpObject#getImage()
	 */
	public Image getImage() {
		if(connectionStatusProvider.isConnected()) {
			if(driveInfo == null) {
				// No drive information. Returning default image.
				return ImageResourceManager.getImage(ImageKeys.IMG_DISC_DRIVE);
			}
			MediaType driveType = driveInfo.getType();
			
			// Checking for permanent drive.
			if(driveType == MediaType.HARD_DISK
					|| driveType == MediaType.RAM
					|| driveType == MediaType.FLASH
					|| driveType == MediaType.ROM) {
				return ImageResourceManager.getImage(ImageKeys.IMG_DISC_DRIVE);
			}
			// Checking for external drive.
			else if(driveType == MediaType.FLOPPY
					|| driveType == MediaType.CDROM
					|| driveType == MediaType.REMOTE
					|| driveType == MediaType.NAND_FLASH
					|| driveType == MediaType.ROTATING_MEDIA) {
				return ImageResourceManager.getImage(ImageKeys.IMG_EXTERNAL_DRIVE);
			}
			// Otherwise it is unknown drive.
			else {
				return ImageResourceManager.getImage(ImageKeys.IMG_UNKNOWN_DRIVE);
			}
		}
		return ImageResourceManager.getImage(ImageKeys.IMG_DISC_DRIVE_DIMMED);
	}

	/**
	 * Get drive information, or <code>null</code> if there is no information for drive.
	 * @return the driveInfo Information of drive, or <code>null</code> if there is no information.
	 */
	public DriveInfo getDriveInfo() {
		return driveInfo;
	}
}