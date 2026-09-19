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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;

import com.nokia.s60tools.hticonnection.services.DriveInfo;

/**
 * Provides data to elements in tableviewer.
 */
class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
	
	/**
	 * Table for which labels are provided.
	 */
	private final Table table;
	
	/**
	 * Length of the free space image, that is created dynamically.
	 */
	private static final int IMAGE_LENGTH = 16;
	/**
	 * Height of the free space image, that is created dynamically.
	 */
	private static final int IMAGE_HEIGHT = 16;
	
	/**
	 * Images that are used to show currently available space.
	 * Free space images are created when label provider is created and disposed at the end.
	 * This is done to guarantee correct image disposal.
	 */
	private Image[] freeSpaceImages;
	
	/**
	 * Constructor.
	 * @param table Labels are provided for this table.
	 */
	public ViewLabelProvider(Table table) {
		this.table = table;
		createImages();
	}

	/**
	 * Creates images that are used to show currently available space on drive.
	 */
	private void createImages() {
		// Creating images.
		freeSpaceImages = new Image[IMAGE_LENGTH + 1];
		
		for(int i = 0;i < IMAGE_LENGTH + 1;i++) {
					
			// Creating image.
			Image freeImage = new Image(Display.getDefault(), IMAGE_LENGTH, IMAGE_HEIGHT);
			GC gc = new GC(freeImage);
			
			// Drawing image.
			gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
			gc.fillRectangle(0, 0, i, IMAGE_HEIGHT);
			gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			gc.fillRectangle(i, 0, IMAGE_LENGTH - i, IMAGE_HEIGHT);
			
			gc.dispose();
			
			freeSpaceImages[i] = freeImage;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
	 */
	public void dispose() {
		for(Image image : freeSpaceImages) {
			image.dispose();
		}
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object obj, int index) {
		if(index >= table.getColumnCount()) {
			// No such column.
			return null;
		}
		
		String columnText = table.getColumn(index).getText();
		
		if(columnText.equals(FtpView.NAME_COLUMN_NAME)) {
			// Name
			IFtpObject ftpObj = (IFtpObject) obj;
			return ftpObj.getName();
		}
		else if(columnText.equals(FtpView.SIZE_COLUMN_NAME) && obj instanceof FtpDriveObject) {
			// Size for drive
			FtpDriveObject ftpObj = (FtpDriveObject) obj;
			if(ftpObj.getDriveInfo() != null) {
				return convertSize(ftpObj.getDriveInfo().getSize());
			}
		}
		else if(columnText.equals(FtpView.FREE_SPACE_COLUMN_NAME) && obj instanceof FtpDriveObject) {
			// Free space for drive
			FtpDriveObject ftpObj = (FtpDriveObject) obj;
			if(ftpObj.getDriveInfo() != null) {
				return convertSize(ftpObj.getDriveInfo().getFreeSpace());
			}
		}
		else if(columnText.equals(FtpView.SIZE_COLUMN_NAME) && obj instanceof FtpFileObject) {
			// Size for file
			FtpFileObject ftpObj = (FtpFileObject) obj;
			return convertSize(ftpObj.getSize());
		}
		
		// No text for other fields.
		return null;
	}
	
	/**
	 * Converts size to proper value and returns it as a string.
	 * @param size Size to be converted.
	 * @return Converted size as a string.
	 */
	private String convertSize(long size) {
		if(size >= 1024) {
			// Converting to kilobytes.
			return "" + (size / 1024) + " kB"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		// Returning as bytes.
		return "" + size + " B"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object obj, int index) {
		
		if(index >= table.getColumnCount()) {
			// No such column.
			return null;
		}
		
		String columnText = table.getColumn(index).getText();
		
		if(columnText.equals(FtpView.NAME_COLUMN_NAME)) {
			// Only first column objects have images.
			IFtpObject ftpObj = (IFtpObject) obj;
			return ftpObj.getImage();
		}
		else if(columnText.equals(FtpView.FREE_SPACE_COLUMN_NAME) && obj instanceof FtpDriveObject) {
			// Getting free space for drive, if information image is available. Can also return null.
			FtpDriveObject ftpObj = (FtpDriveObject) obj;
			return getFreeSpaceImage(ftpObj.getDriveInfo());
		}
		
		return null;
	}
	
	/**
	 * Returns image based on the free and used space in the drive.
	 * @param driveInfo Drive information.
	 * @return Image based on the free and used space in the drive. Or null if
	 * there is no size/free space information.
	 */
	private Image getFreeSpaceImage(DriveInfo driveInfo) {
		// Returning null if there is no drive information.
		if(driveInfo == null) {
			return null;
		}
		
		// Getting needed variables.
		long freeSpace = driveInfo.getFreeSpace();
		long size = driveInfo.getSize();
		double free = 0;
		if(size != 0) {
			free = (double)freeSpace/size;
		}
		
		// Calculating which image should be used.
		
		int freeSpaceSize = (int)Math.round(IMAGE_LENGTH * free);
		
		return freeSpaceImages[freeSpaceSize];
	}
}