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
package com.nokia.s60tools.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import com.nokia.s60tools.ui.internal.S60ToolsUiPlugin;

/**
 * Miscellaneous utility methods related to UI.
 */
public class UiUtils {

	/**
	 * Empty banner image name.
	 */
	public static String NOKIA_TOOLS_BANNER = "empty_banner.png"; //$NON-NLS-1$

    /**
     * Method for retrieving wizard banner background for the given icon.
     * @param toolWizardIcon Tool's wizard icon to be added on top of background banner.
     * @return given icon with the wizard banner background
     */
    public static ImageDescriptor getBannerImageDescriptor(ImageDescriptor toolWizardIcon) {

    	// Getting background image.
    	ImageDescriptor bannerDesc = S60ToolsUiPlugin.getImageDescriptorForKey(NOKIA_TOOLS_BANNER);

	    // If icon is null, return only the banner
	    if(toolWizardIcon == null)
	        return bannerDesc;

	    Image bannerImage = bannerDesc.createImage();
	    Image iconImage = toolWizardIcon.createImage();

	    GC imageGC = new GC(bannerImage);

	    // Draw icon over the banner, in the middle
	    imageGC.drawImage(iconImage,
	        bannerImage.getBounds().width / 2 - iconImage.getBounds().width / 2,
	        bannerImage.getBounds().height / 2 - iconImage.getBounds().height / 2);

	    ImageDescriptor finalDesc = ImageDescriptor.createFromImageData(bannerImage.getImageData());

	    // Dispose graphics
	    imageGC.dispose();
	    bannerImage.dispose();
	    iconImage.dispose();

	    return finalDesc;
	}
}
