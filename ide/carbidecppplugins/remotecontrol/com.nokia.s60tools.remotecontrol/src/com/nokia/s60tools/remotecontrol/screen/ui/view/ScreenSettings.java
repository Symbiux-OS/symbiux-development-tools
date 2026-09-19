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

package com.nokia.s60tools.remotecontrol.screen.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.hticonnection.services.HTIScreenMode;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferenceConstants;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.screen.ui.actions.ZoomAction;
import com.nokia.s60tools.remotecontrol.screen.ui.actions.ZoomAction.ZoomFactor;

/**
 * This class stores current settings for the screen view.
 */
public class ScreenSettings {

	/**
	 * Screen image should be accessed only via synchronized getters and setters!
	 */
	private static Image screenImage = null;
	
	/**
	 * Screen image as bytes.
	 */
	private static byte[] imageData = null;

	/**
	 * Percentage of how much screenImage is currently resized on screen.
	 */
	private double zoomPercent = 1.0;
	
	/**
	 * Contains currently used zoom factor. Using 100% at default.
	 */
	private ZoomAction.ZoomFactor zoomFactor = ZoomFactor.ZOOM_TO_100_PERCENT;

	/**
	 * Screen mode
	 */
	private HTIScreenMode screenMode = null;
	
	/**
	 * Screen capture refresh time
	 */
	private int refreshTime = 1000;
	
	/**
	 * Timeout for request
	 */
	private int timeoutTime = 2000;
	
	/**
	 * Color mode
	 */
	private byte colorChoice = RCPreferenceConstants.colorCodes[0];
	
	/**
	 * Flag keeping track if all screenshots should be saved.
	 */
	private boolean savingAllScreenshots = false;
	
	/**
	 * Keeps track on how many images have been saved since starting to save all
	 * screenshots.
	 */
	private int imagesSaved;
	
	/**
	 * Constructor.
	 */
	public ScreenSettings() {
		imagesSaved = 0;
	}
	
	/**
	 * Get refresh time and color choice values from prefstore
	 */
	public void getPreferences() {
		refreshTime = RCPreferences.getRefreshDelayInt();
		colorChoice = RCPreferences.getColorModeByte();
	}
	
	/**
	 * Synchronized getter for screen image.
	 * @return the screenImage
	 */
	public static synchronized Image getScreenImage(){
		return screenImage;
	}

	/**
	 * @param imageData the imageData to set
	 */
	public static synchronized void setImageData(byte[] imageData) {
		ScreenSettings.imageData = imageData;
	}
	
	/**
	 * Synchronized getter for screen data.
	 * @return the imageData
	 */
	public static synchronized byte[] getImageData() {
		return imageData;
	}
	
	/**
	 * Synchronized setter for screen image.
	 * This method handles disposing earlier image, if it exists.
	 * @param screenImage the screenImage to set
	 * @param imageData the data for image.
	 */
	public static synchronized void setScreenImage(Image screenImage, byte[] imageData) {
		
		ScreenSettings.setImageData(imageData);
		
		// Old Image needs to be disposed, so that it won't use resources. 
		if(ScreenSettings.screenImage != null) {
			ScreenSettings.screenImage.dispose();
		}
		
		ScreenSettings.screenImage = screenImage;
	}

	/**
	 * Copies the current screen image and returns it as new image instance.
	 * Caller of this method needs to dispose image after it is not needed.
	 * @return Copy instance of the current screen image.
	 */
	public static Image getScreenShotCopy(){
		return new Image(Display.getCurrent(), getScreenImage(), SWT.IMAGE_COPY);
	}
	
	/**
	 * Synchronized getter for screen mode.
	 * @return the screenMode
	 */
	public synchronized HTIScreenMode getScreenMode(){
		return screenMode;
	}

	/**
	 * Synchronized setter for screen mode.
	 * @param screenMode the screenMode to set
	 */
	public synchronized void setScreenMode(HTIScreenMode screenMode) {
		this.screenMode = screenMode;
	}
	
	/**
	 * Sets zoom percent used for current screen.
	 * @param zoomPercent The zoomPercent used for current screen.
	 */
	public synchronized void setZoomPercent(double zoomPercent) {
		this.zoomPercent = zoomPercent;
	}

	/**
	 * Gets zoom percent used for current screen.
	 * @return The zoomPercent used for current screen.
	 */
	public synchronized double getZoomPercent() {
		return zoomPercent;
	}
	
	/**
	 * Sets zoom factor for current screen.
	 * @param zoomFactor The zoomFactor to set.
	 */
	public synchronized void setZoomFactor(ZoomAction.ZoomFactor zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	/**
	 * Gets zoom factor for current screen.
	 * @return The zoomFactor.
	 */
	public synchronized ZoomAction.ZoomFactor getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * Getter for refresh time.
	 * @return Delay between capturing new screen.
	 */
	public int getRefreshTime() {
		return refreshTime;
	}

	/**
	 * Getter for timeout time.
	 * @return Current timeout for screen capture.
	 */
	public int getTimeoutTime() {
		return timeoutTime;
	}

	/**
	 * Getter for color choice.
	 * @return Currently selected color choice.
	 */
	public byte getColorChoice() {
		return colorChoice;
	}

	/**
	 * Sets if all screenshots should be saved.
	 * @param savingAllScreenshots True if all screens should be saved. False otherwise.
	 */
	public void setSavingAllScreenshots(boolean savingAllScreenshots) {
		if(!this.savingAllScreenshots && savingAllScreenshots) {
			// Starting to save all screenshots. Resetting save count.
			imagesSaved = 0;
		}
		this.savingAllScreenshots = savingAllScreenshots;
	}

	/**
	 * Gets if all screenshots should be saved.
	 * @return True if all screens should be saved. False otherwise.
	 */
	public boolean isSavingAllScreenshots() {
		return savingAllScreenshots;
	}

	/**
	 * Updates amount of images saved.
	 */
	public void imageSaved() {
		imagesSaved++;
	}

	/**
	 * Returns amount of images saved since starting saving all screenshots.
	 * @return the imagesSaved
	 */
	public int getImagesSaved() {
		return imagesSaved;
	}
}
