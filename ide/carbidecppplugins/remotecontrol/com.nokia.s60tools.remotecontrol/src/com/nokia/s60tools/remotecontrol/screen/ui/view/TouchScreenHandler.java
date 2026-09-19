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

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.listener.HtiConnectionManager;
import com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener;
import com.nokia.s60tools.hticonnection.services.HTIScreenMode;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.IKeyEventService;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * This class implements mouse handling over the screen.
 */
public class TouchScreenHandler implements MouseListener, IHtiConnectionListener {

	/**
	 * View which uses this handler.
	 */
	private final ScreenView screenView;
	
	/**
	 * Last mouse down event location.
	 */
	private Point mouseDownPoint;

	/**
	 * Time when last mouse down event happened.
	 */
	private long mouseDownTime;
	
	/**
	 * This value used in addition to event length, when tap screen events are sent to the device.
	 */
	private final int TIMEOUT_TIME_ADDITION = 3000;
	
	/**
	 * User is informed about the tap screen event only if time of the tap event
	 * is greater than this time limit(ms).
	 */
	private final int SHOW_NOTIFICATION_TIME_LIMIT = 2000;

	/**
	 * This is used to keep track if mouse down event was inside the image.
	 */
	private boolean outOfBoundary;
	
	/**
	 * Contains status of connection.
	 */
	private boolean isConnected = false;
	
	/**
	 * Constructor.
	 * @param screenView View which uses this handler.
	 */
	public TouchScreenHandler(ScreenView screenView) {
		this.screenView = screenView;
		mouseDownPoint = new Point(0, 0);
		mouseDownTime = System.currentTimeMillis();
		HtiConnectionManager.getInstance().addListener(this);
	}

	/**
	 * Disposes this listener.
	 */
	public void dispose() {
		HtiConnectionManager.getInstance().removeListener(this);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent event) {
		// Double click event is not used.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent event) {
		// Storing and converting event location based on screen orientation.
		if(event.button != 1) {
			// Only first button can cause tap screen events.
			return;
		}
		mouseDownPoint = getZoomedPoint(event);
		HTIScreenMode screenMode = screenView.getScreenSettings().getScreenMode();
		outOfBoundary = isOutOfBoundary(mouseDownPoint, screenMode);
		
		mouseDownPoint = convertPointOrientation(mouseDownPoint, screenMode);
		mouseDownTime = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent event) {
		if(event.button != 1) {
			// Only first button can cause tap screen events.
			return;
		}

		// Getting needed variables.
		HTIScreenMode screenMode = screenView.getScreenSettings().getScreenMode(); 
		Point eventPointOnImage = getZoomedPoint(event);
		int holdDownTime = (int) (System.currentTimeMillis() - mouseDownTime);
		Point convertedPoint = convertPointOrientation(eventPointOnImage, screenMode); 
		
		// Checking event boundaries.
		if(outOfBoundary || isOutOfBoundary(eventPointOnImage, screenMode)) {
			// Mouse up or down event was outside of the image. Doing nothing.
			return;
		}
		
		// Creating tap screen event, that needs to be run in normal thread.
		// Running from UI thread would prevent using the Carbide before event has passed.
		Thread tapScreenThread = new TapScreenThread(mouseDownPoint, convertedPoint, holdDownTime);
		tapScreenThread.start();
	}

	/**
	 * Thread for tap/drag events.
	 */
	private class TapScreenThread extends Thread {
		
		// Tap/drag event details.
		private final Point upPoint;
		private final Point downPoint;
		private final int holdDownTime;

		/**
		 * Constructor.
		 * @param downPoint Location where mouse down event happened.
		 * @param upPoint Location where mouse up event happened.
		 * @param holdDownTime Time between down and up events.
		 */
		public TapScreenThread(Point downPoint, Point upPoint, int holdDownTime) {
			this.downPoint = downPoint;
			this.upPoint = upPoint;
			this.holdDownTime = holdDownTime;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {

			if(!isConnected) {
				return;
			}
			
			// Notifying user if tap screen takes longer than defined time limit.
			if(holdDownTime > SHOW_NOTIFICATION_TIME_LIMIT) {
				Runnable updateDescription = new Runnable(){
					public void run() {
						screenView.updateDescription(Messages.getString("TouchScreenHandler.TapScreenToolBar_Msg"));	 //$NON-NLS-1$
					}							
				};
				// Description needs to be updated from UI thread.
				Display.getDefault().syncExec(updateDescription);			
			}

			try {
				if(downPoint.x == upPoint.x 
						&& downPoint.y == upPoint.y) {
					// Mouse is in same place as where it started. Sending tap event.
					IKeyEventService service = HTIServiceFactory.createKeyEventService(RemoteControlConsole.getInstance());
					service.tapScreen(downPoint.x, downPoint.y, 1, holdDownTime, 0, holdDownTime + TIMEOUT_TIME_ADDITION);
				} 
				else {
					// Mouse has been moved. Sending drag event.
					IKeyEventService service = HTIServiceFactory.createKeyEventService(RemoteControlConsole.getInstance());
					service.tapAndDrag(downPoint.x, downPoint.y, upPoint.x, upPoint.y,
							holdDownTime, holdDownTime + TIMEOUT_TIME_ADDITION);
				}
			} catch (ServiceShutdownException e) {
				// Services have been purposely shut down. No need to report error.
			} catch (Exception e) {
				// Problem is reported already via RemoteControlConsole given as parameter to service.
			}
			
			// Removing the notification.
			if(holdDownTime > SHOW_NOTIFICATION_TIME_LIMIT) {
				Runnable updateDescription = new Runnable(){
					public void run() {
						screenView.setDefaultDescription();
					}							
				};
				// Description needs to be updated from UI thread.
				Display.getDefault().syncExec(updateDescription);			
			}
		}
	}
	
	/**
	 * Checks if image is zoomed and returns location on image
	 * where event happened.
	 * @param event Location where mouse event happened.
	 * @return Location on screen image where event happened.
	 */
	private Point getZoomedPoint(MouseEvent event) {
		int xLocationZoomed = (int) (event.x / screenView.getScreenSettings().getZoomPercent());
		int yLocationZoomed = (int) (event.y / screenView.getScreenSettings().getZoomPercent());
		
		return new Point(xLocationZoomed, yLocationZoomed);
	}
	
	/**
	 * Converts originalPoint to correct coordinates based on screen rotation.
	 * @param originalPoint Original point to be converted.
	 * @param screenMode Screen settings.
	 * @return Point that is converted based on screen rotation.
	 */
	private Point convertPointOrientation(Point originalPoint, HTIScreenMode screenMode) {
		// Default value that matches when screen rotation is 0°.
		Point convertedPoint = new Point(originalPoint.x, originalPoint.y);

		// Converting point if screen has been rotated.
		
		// Screen is rotated 90°.
		if(screenMode.getRotation() == 1){
			convertedPoint = new Point(screenMode.getHeight() - originalPoint.y,
					originalPoint.x);
		}
		// Screen is rotated 180°.
		else if(screenMode.getRotation() == 2){
			convertedPoint = new Point(screenMode.getWidth() - originalPoint.x,
					screenMode.getHeight() - originalPoint.y);
		}
		// Screen is rotated 270°.
		else if(screenMode.getRotation() == 3){
			convertedPoint = new Point(originalPoint.y,
					screenMode.getWidth() - originalPoint.x);
		}
		
		return convertedPoint;
	}

	/**
	 * Checks if screenPoint is outside of the screen limits. This needs to
	 * be checked from original image limits with possible difference caused by zoom.
	 * @param screenPoint Point that is checked.
	 * @param screenMode Screen settings.
	 * @return True if point is outside of the screen boundaries. True if point is within the screen.
	 */
	private boolean isOutOfBoundary(Point screenPoint, HTIScreenMode screenMode) {
		// Getting needed variables.
		int screenWidth = screenMode.getWidth();
		int screenHeight = screenMode.getHeight();
		int pointX = screenPoint.x;
		int pointY = screenPoint.y;

		// Checking screen boundaries so that the point is not outside of the screen.
		if(pointX < 0 || pointY < 0 || pointX > screenWidth || pointY > screenHeight){
			return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener#connectionTerminated()
	 */
	public void connectionTerminated() {
		isConnected = false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener#connectionStarted()
	 */
	public void connectionStarted() {
		isConnected = true;
	}
}
