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

package com.nokia.s60tools.hticonnection.resources;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.hticonnection.HtiApiActivator;

/**
 * This class manages images that are used in HTI Connection project.
 */
public class ImageResourceManager {
	
	/**
	 * Loads images to image registry.
	 * @param imagesPath Path of image directory.
	 */
	public static void loadImages(String imagesPath){

    	Display disp = Display.getCurrent();
    	ImageRegistry imgReg = HtiApiActivator.getDefault().getImageRegistry();

    	//
    	// Storing images to image registry.
    	//
    	// There are images that are under EPL license.
    	//
    	
    	// Image for base action.
    	Image img = new Image( disp, imagesPath + File.separator + "s60tools_action_icon.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.S60TOOLS_ACTION_ICON, img );

    	// HTI API specific images
    	img = new Image( disp, imagesPath + File.separator + "hti_console.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_APP_ICON, img );
    	
    	img = new Image( disp, imagesPath + File.separator + "preferences.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_PREFERENCES, img );
        
        img = new Image( disp, imagesPath + File.separator + "start_datagateway.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_START_GATEWAY, img);
        
        img = new Image( disp, imagesPath + File.separator + "stop_datagateway.png" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_STOP_GATEWAY, img);
        
        /*******************************************************************************
         * The following pieces of the graphic are taken from from Eclipse 3.4 platform 
         * and CDT project in Eclipse community, which are made available under 
         * the terms of the Eclipse Public License v1.0.
         * A copy of the EPL is provided at http://www.eclipse.org/legal/epl-v10.html
         *******************************************************************************/

        // Original location of the graphic:
        // eclipse-3.4\plugins\org.eclipse.ui.console\icons\full\clcl16\clear_co.gif
        img = new Image( disp, imagesPath + File.separator + "clear_co.gif" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_CLEAR_SCREEN, img);
        
        // Original location of the graphic:
        // eclipse-3.4\plugins\org.eclipse.ui.console\icons\full\clcl16\lock_co.gif
        img = new Image( disp, imagesPath + File.separator + "lock_co.gif" ); //$NON-NLS-1$
        imgReg.put( ImageKeys.IMG_SCROLL_LOCK, img);

	}
	
	/**
	 * Gets image descriptor for given key from plugin's image registry.
	 * @param key Key for image.
	 * @return ImageDescriptor for given image key.
	 */
	public static ImageDescriptor getImageDescriptor( String key ){
    	ImageRegistry imgReg = HtiApiActivator.getDefault().getImageRegistry();
    	return  imgReg.getDescriptor( key );		
	}	

	/**
	 * Gets image for given key from plugin's image registry.
	 * @param key Key for image.
	 * @return Image for given key.
	 */
	public static Image getImage( String key ){
    	ImageRegistry imgReg = HtiApiActivator.getDefault().getImageRegistry();
    	return  imgReg.get(key);		
	}	
}
