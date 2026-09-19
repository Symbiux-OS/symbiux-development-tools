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

package com.nokia.s60tools.hticonnection.services;

/**
 * This class contains information screen.
 * This includes screen rotation, color mode, focus index, and size.
 */
public class HTIScreenMode {
	
	// Rotation
	
	/**
	 * Screen is rotation 0 degrees.
	 */
	public static final int ROTATION_NONE = 0;
	/**
	 * Screen is rotation 90 degrees.
	 */
	public static final int ROTATION_90 = 1;
	/**
	 * Screen is rotation 180 degrees.
	 */
	public static final int ROTATION_180 = 2;
	/**
	 * Screen is rotation 270 degrees.
	 */
	public static final int ROTATION_270 = 3;
	
	// Color mode
	
	/**
	 * Color mode no colors.
	 */
	public static final int MODE_ENONE = 0;
	/**
	 * Color mode grey, 2 colors.
	 */
	public static final int MODE_EGRAY2 = 1;
	/**
	 * Color mode grey, 4 colors.
	 */
	public static final int MODE_EGRAY4 = 2;
	/**
	 * Color mode grey 16 colors.
	 */
	public static final int MODE_EGRAY16 = 3;
	/**
	 * Color mode grey, 256 colors.
	 */
	public static final int MODE_EGRAY256 = 4;
	/**
	 * Color mode 16 colors.
	 */
	public static final int MODE_ECOLOR16 = 5;
	/**
	 * Color mode 256 colors.
	 */
	public static final int MODE_ECOLOR256 = 6;
	/**
	 * Color mode 64K colors.
	 */
	public static final int MODE_ECOLOR64K = 7;
	/**
	 * Color mode 16M colors.
	 */
	public static final int MODE_ECOLOR16M = 8;
	/**
	 * Color mode RGB colors.
	 */
	public static final int MODE_ERGB = 9;
	/**
	 * Color mode 4k colors.
	 */
	public static final int MODE_ECOLOR4K = 0xa;
	/**
	 * Color mode 16M colors.
	 */
	public static final int MODE_ECOLOR16MU = 0xb;
	/**
	 * Color mode 16M colors.
	 */
	public static final int MODE_ECOLOR16MA = 0xc;

	/**
	 * Screen is rotation values.
	 */
	public static final String ROTATION[] = new String[] {
			"0°",  //$NON-NLS-1$
			"90°",  //$NON-NLS-1$
			"180°",  //$NON-NLS-1$
			"270°"  //$NON-NLS-1$
		};
		
	/**
	 * Color mode values.
	 */
	public static final String MODE[] = new String[] {
			"ENone", //$NON-NLS-1$
			"EGray2", //$NON-NLS-1$
			"EGray4", //$NON-NLS-1$
			"EGray16", //$NON-NLS-1$
			"EGray256", //$NON-NLS-1$
			"EColor16", //$NON-NLS-1$
			"EColor256", //$NON-NLS-1$
			"EColor64K", //$NON-NLS-1$
			"EColor16M", //$NON-NLS-1$
			"ERgb", //$NON-NLS-1$
			"EColor4K", //$NON-NLS-1$
			"EColor16MU", //$NON-NLS-1$
			"EColor16MA", //$NON-NLS-1$
		};

	
	/**
	 * The index number of the screen whose attributes are returned.
	 */
	private int index;
	/**
	 * The width of the screen in pixels.
	 */
	private int width;
	/**
	 * The height of the screen in pixels.
	 */
	private int height;
	/**
	 * The rotation of the screen. 0 = normal, 1 = 90, 2 = 180, 3 = 270.
	 */
	private int rotation;
	/**
	 * The display mode of the screen. 
	 */
	private int mode;
	/**
	 * The index number of the screen that currently has focus.
	 * Note that this can be different than the currently selected screen.
	 */
	private int focusIndex;
	
	/**
	 * Constructor.
	 * @param index The index number of the screen whose attributes are returned.
	 * @param width The width of the screen in pixels.
	 * @param height The height of the screen in pixels.
	 * @param rotation The rotation of the screen. 0 = normal, 1 = 90, 2 = 180, 3 = 270.
	 * @param mode The display mode of the screen. 
	 * @param focusIndex The index number of the screen that currently has focus.
	 */
	public HTIScreenMode(int index, int width, int height, int rotation, int mode, int focusIndex) {
		this.index = index;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		this.mode = mode;
		this.focusIndex = focusIndex;
	}
	
	/**
	 * Gets the index number of the screen whose attributes are returned.
	 * @return index number of the screen whose attributes are returned.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gets the width of the screen in pixels.
	 * @return the width of the screen in pixels.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height of the screen in pixels.
	 * @return the height of the screen in pixels.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Gets the rotation of the screen in degrees. 0 = normal, 1 = 90, 2 = 180, 3 = 270.
	 * @return the rotation of the screen.
	 */
	public int getRotation() {
		return rotation;
	}
	
	/**
	 * Gets the display mode of the screen. 
	 * @return the display mode of the screen. 
	 */
	public int getMode() {
		return mode;
	}
	
	/**
	 * Gets the index number of the screen that currently has focus.
	 * Note that this can be different than the currently selected screen.
	 * @return the index number of the screen that currently has focus.
	 */
	public int getFocusIndex() {
		return focusIndex;
	}
	
	/**
	 * Returns a string representation of the screen mode.
	 */
	public String toString() {
		return "[screen " + index + ": " + width + "x" + height + " @ " + MODE[mode] + ROTATION[rotation] + ", focus: " + focusIndex + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + focusIndex;
		result = prime * result + height;
		result = prime * result + index;
		result = prime * result + mode;
		result = prime * result + rotation;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HTIScreenMode other = (HTIScreenMode) obj;
		if (focusIndex != other.focusIndex)
			return false;
		if (height != other.height)
			return false;
		if (index != other.index)
			return false;
		if (mode != other.mode)
			return false;
		if (rotation != other.rotation)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
}
