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

package com.nokia.s60tools.remotecontrol.resources;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;

/**
 * This class manages images that are used in Remote Control project.
 */
public class ImageResourceManager {
	
	/**
	 * Loads images to image registry.
	 * @param imagesPath Path of image directory.
	 */
	public static void loadImages(String imagesPath){
		
    	Display disp = Display.getCurrent();
    	ImageRegistry imgReg = RemoteControlActivator.getDefault().getImageRegistry();

    	//
    	// Storing images to image registry.
    	//
    	
    	Image img = new Image( disp, imagesPath + File.separator + "s60tools_action_icon.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.S60TOOLS_ACTION_ICON, img );

    	// Remote Control specific images
    	img = new Image( disp, imagesPath + File.separator + "remote_control.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_APP_ICON, img );
    	        
    	img = new Image( disp, imagesPath + File.separator + "screen_capture.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_SCREEN_CAPTURE_MODE, img );

    	img = new Image( disp, imagesPath + File.separator + "file_transfer.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_FILE_TRANSFER_MODE, img );
        
    	img = new Image( disp, imagesPath + File.separator + "preferences.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_PREFERENCES, img );        

    	img = new Image( disp, imagesPath + File.separator + "save_screenshot.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_SAVE_SCREENSHOT, img );
        
        img = new Image( disp, imagesPath + File.separator + "download.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_DOWNLOAD, img );
        
        img = new Image( disp, imagesPath + File.separator + "upload.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_UPLOAD, img );
        
        img = new Image( disp, imagesPath + File.separator + "new_folder.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_NEW_FOLDER, img );
        
        img = new Image( disp, imagesPath + File.separator + "disc_drive.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_DISC_DRIVE, img );
        
        img = new Image( disp, imagesPath + File.separator + "disc_drive_dimmed.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_DISC_DRIVE_DIMMED, img );
        
        img = new Image( disp, imagesPath + File.separator + "folder.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_FOLDER, img );
        
    	img = new Image( disp, imagesPath + File.separator + "screenshots_start_taking.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_START_MULTI_SCREENSHOT, img );
        
    	img = new Image( disp, imagesPath + File.separator + "screenshots_stop_taking.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_STOP_MULTI_SCREENSHOT, img );
        
    	img = new Image( disp, imagesPath + File.separator + "go_to_directory.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_GO_TO_DIRECTORY, img );
        
    	img = new Image( disp, imagesPath + File.separator + "refresh.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_REFRESH, img );
        
    	img = new Image( disp, imagesPath + File.separator + "download_as.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_DOWNLOAD_AS, img );
        
    	img = new Image( disp, imagesPath + File.separator + "download_and_open.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_DOWNLOAD_AND_OPEN, img );
        
    	img = new Image( disp, imagesPath + File.separator + "disc_drive_external_drives.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_EXTERNAL_DRIVE, img );
        
    	img = new Image( disp, imagesPath + File.separator + "disc_drive_other_types.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_UNKNOWN_DRIVE, img );
        
    	img = new Image( disp, imagesPath + File.separator + "keyboard_view.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_KEYBOARD_VIEW, img );
        
    	img = new Image( disp, imagesPath + File.separator + "keyboard_switch_layout.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_SWITCH_KEYBOARD_MODE, img );
        
    	img = new Image( disp, imagesPath + File.separator + "navigation_keys_icon.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_NAVI_KEYS, img );
        
    	img = new Image( disp, imagesPath + File.separator + "navigation_key_up.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_NAVI_UP, img );
        
    	img = new Image( disp, imagesPath + File.separator + "navigation_key_down.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_NAVI_DOWN, img );
        
    	img = new Image( disp, imagesPath + File.separator + "navigation_key_left.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_NAVI_LEFT, img );
        
    	img = new Image( disp, imagesPath + File.separator + "navigation_key_right.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_NAVI_RIGHT, img );
        
        
        
        
	}
	
	/**
	 * Gets image descriptor for given key from plugin's image registry.
	 * @param key Key for image.
	 * @return ImageDescriptor for given image key.
	 */
	public static ImageDescriptor getImageDescriptor( String key ){
    	ImageRegistry imgReg = RemoteControlActivator.getDefault().getImageRegistry();
    	return  imgReg.getDescriptor( key );		
	}	

	/**
	 * Gets image for given key from plugin's image registry.
	 * @param key Key for image.
	 * @return Image for given key.
	 */
	public static Image getImage( String key ){
    	ImageRegistry imgReg = RemoteControlActivator.getDefault().getImageRegistry();
    	return  imgReg.get(key);		
	}	
	
}
