/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.remotecontrol.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * A class to help image handling.
 */
public class ImageHelper {

	/**
	 * Default mime type for returning image.
	 */
	private static String defaultMimeType = "image/png";
	
	/**
	 * Overlay given image on top of another given image.
	 * @param imgData Image data of image.
	 * @param overlayData Image data of image to overlay.
	 * @param posX X-axis position of the overlay image.
	 * @param posY Y-axis position of the overlay image.
	 * @param mimeType Mime type of the returned image.
	 * @return Image data of combined image.
	 * @throws IOException
	 */
	public static byte[] overlayImage(byte[] imgData, byte[] overlayData, Integer posX, 
			Integer posY, String mimeType) throws IOException {
		
		if (mimeType == null) mimeType = defaultMimeType;
		// Remove "image/" from mimeType.
		mimeType = mimeType.substring(6);
		
		// Change overlay image data into BufferedImage form.
		InputStream inputOverlay = new ByteArrayInputStream(overlayData);
		BufferedImage overlayImg = ImageIO.read(inputOverlay);			

		// Change original image data into BufferedImage form.
		InputStream inputOrig = new ByteArrayInputStream(imgData);
		BufferedImage origImg = ImageIO.read(inputOrig);
				
		// Overlay image on top of original image.
		Graphics2D g = origImg.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.drawImage(overlayImg, posX, posY, null);
        g.dispose();
 				
		// Change BufferedImage back to byte[] -form.
        ByteArrayOutputStream resultData = new ByteArrayOutputStream();
        ImageIO.write(origImg, mimeType, resultData);
        
        // Return combined data.
        return resultData.toByteArray();

	}
	
}
