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

package com.nokia.s60tools.hticonnection.services;

import java.util.List;

/**
 * A simple class to handle and deliver screen capture data.
 */
public class ScreenCaptureData {

	private Integer topLeftX;
	private Integer topLeftY;
	private Integer bottomRightX;
	private Integer bottomRightY;
	private byte[] imageData;
	
	/**
	 * Constructor.
	 */
	public ScreenCaptureData() {	
	}
	
	/**
	 * Alternative constructor.
	 * @param data Result data from com.nokia.HTI.ScreenCapturingService.ScreenCapturingService
	 * 			   List that contains Coordinates all 0 if no image was captured Top
	 *         	   left x coordinate Integer Top left y coordinate Integer Bottom
	 *         	   right x coordinate Integer Bottom right y coordinate Integer
	 *         	   Image data byte[].
	 */
	public ScreenCaptureData(List data) {
		if ((data != null) && (data.size() == 5)) {
			this.topLeftX = (Integer)data.get(0);
			this.topLeftY = (Integer)data.get(1);
			this.bottomRightX = (Integer)data.get(2);
			this.bottomRightY = (Integer)data.get(3);
			this.imageData = (byte[])data.get(4);
		}
	}

	public Integer getTopLeftX() {
		return topLeftX;
	}

	public void setTopLeftX(Integer topLeftX) {
		this.topLeftX = topLeftX;
	}

	public Integer getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(Integer topLeftY) {
		this.topLeftY = topLeftY;
	}

	public Integer getBottomRightX() {
		return bottomRightX;
	}

	public void setBottomRightX(Integer bottomRightX) {
		this.bottomRightX = bottomRightX;
	}

	public Integer getBottomRightY() {
		return bottomRightY;
	}

	public void setBottomRightY(Integer bottomRightY) {
		this.bottomRightY = bottomRightY;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
}
