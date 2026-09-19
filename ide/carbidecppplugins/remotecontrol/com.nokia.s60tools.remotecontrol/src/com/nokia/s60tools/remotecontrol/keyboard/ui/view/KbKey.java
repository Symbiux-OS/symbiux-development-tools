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

package com.nokia.s60tools.remotecontrol.keyboard.ui.view;

/**
 * Represents a single keyboard key.
 */
public class KbKey{

	/**
	 * Supported non-character key functionalities.
	 */
	public enum KbKeyFunc{
		SHIFT, 
		CTRL, 
		SPACE,
		DEL, 
		ENTER,
		CHR
	}	
	
	/**
	 * Default label of the key
	 */
	private final String label;
	
	/**
	 * Image for the key
	 */
	private final String imageKey;
	
	/**
	 * Scancode
	 */
	private int scancode = -1;
	
	/**
	 * How many columns this key uses horizontally
	 */
	private int horizontalSpan = 1;
	
	/**
	 * How many rows this key uses vertically
	 */
	private int verticalSpan = 1;
	
	/**
	 * Label when shift button is pressed
	 */
	private String shiftKeyLabel = null;
	
	/**
	 * Key functionality for non-character keys
	 */
	private KbKeyFunc keyFunc = null;
	
	/**
	 * Is toggle button
	 */
	private boolean isToggle = false;	

	
	/**
	 * Is toggle button
	 * @return True if is toggle button
	 */
	public boolean isToggle() {
		return isToggle;
	}

	/**
	 * Get non-character key functionality
	 * @return Non-character key functionality
	 */
	public KbKeyFunc getKeyFunc() {
		return keyFunc;
	}

	/**
	 * Get label for key when shift is on
	 * @return Label for key when shift is on
	 */
	public String getShiftKeyLabel() {
		return shiftKeyLabel;
	}
	
	/**
	 * Get scancode
	 * @return scancode
	 */
	public int getScancode() {
		return scancode;
	}

	/**
	 * Get vertical span
	 * @return Vertical span
	 */
	public int getVerticalSpan() {
		return verticalSpan;
	}

	/**
	 * Get horizontal span
	 * @return Horizontal span
	 */
	public int getHorizontalSpan() {
		return horizontalSpan;
	}

	/**
	 * Get label
	 * @return Label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Get image for the key
	 * @return Image for the key
	 */
	public String getImageKey() {
		return imageKey;
	}
	
	/**
	 * Constructor
	 * @param label Default label of the key, cannot be <code>null</code> but can be empty string.
	 * @param imageKey Image for the key, if not <code>null</code> then image is used instead of labels.
	 * @param scancode Scanc ode for the key
	 * @param horizontalSpan How many columns this key uses horizontally.
	 */
	public KbKey(String label, String imageKey, int scancode, int horizontalSpan){
		this.label = label;
		this.imageKey = imageKey;
		this.scancode = scancode;
		this.horizontalSpan = horizontalSpan;
	}
	
	/**
	 * Constructor
	 * @param label Default label of the key, cannot be <code>null</code> but can be empty string.
	 * @param imageKey Image for the key, if not <code>null</code> then image is used instead of labels.
	 * @param scancode Scanc ode for the key
	 * @param horizontalSpan How many columns this key uses horizontally.
	 *  * @param verticalSpan How many rows this key uses vertically.
	 */
	public KbKey(String label, String imageKey, int scancode, int horizontalSpan, int verticalSpan){
		this(label, imageKey, scancode, horizontalSpan);
		this.verticalSpan = verticalSpan;
	}

	/**
	 * Constructor
	 * @param label Default label of the key, cannot be <code>null</code> but can be empty string.
	 * @param shifKeyLabel Label when shift button is pressed, set to <code>null</code> when shift functionality is same that default non-shift functionality.
	 * @param imageKey Image for the key, if not <code>null</code> then image is used instead of labels.
	 */
	public KbKey(String label, String shifKeyLabel, String imageKey){
		this.label = label;
		this.shiftKeyLabel = shifKeyLabel;
		this.imageKey = imageKey;			
	}

	/**
	 * Constructor
	 * @param label Default label of the key, cannot be <code>null</code> but can be empty string.
	 * @param shifKeyLabel Label when shift button is pressed, set to <code>null</code> when shift functionality is same that default non-shift functionality.
	 * @param imageKey Image for the key, if not <code>null</code> then image is used instead of labels.
	 * @param horizontalSpan How many columns this key uses horizontally.
	 */
	public KbKey(String label, String shifKeyLabel, String imageKey, int horizontalSpan){
		this(label, shifKeyLabel, imageKey);
		this.horizontalSpan = horizontalSpan;		
	}

	/**
	 * Constructor
	 * @param label Default label of the key, cannot be <code>null</code> but can be empty string.
	 * @param shifKeyLabel Label when shift button is pressed, set to <code>null</code> when shift functionality is same that default non-shift functionality.
	 * @param imageKey Image for the key, if not <code>null</code> then image is used instead of labels.
	 * @param horizontalSpan How many columns this key uses horizontally.
	 * @param verticalSpan How many rows this key uses vertically.
	 */
	public KbKey(String label, String shifKeyLabel, String imageKey, int horizontalSpan, int verticalSpan){
		this(label, shifKeyLabel, imageKey,horizontalSpan);
		this.verticalSpan  = verticalSpan;		
	}
	
	/**
	 * Constructor
	 * @param label Default label of the key, cannot be <code>null</code> but can be empty string.
	 * @param shifKeyLabel Label when shift button is pressed, set to <code>null</code> when shift functionality is same that default non-shift functionality.
	 * @param imageKey Image for the key, if not <code>null</code> then image is used instead of labels.
	 * @param horizontalSpan How many columns this key uses horizontally.
	 * @param verticalSpan How many rows this key uses vertically.
	 * @param keyFunc Key functionality for non-character keys.
	 * @see com.nokia.s60tools.remotecontrol.keyboard.ui.view.KbKey.KbKeyFunc
	 */
	public KbKey(String label, String shifKeyLabel, String imageKey, int horizontalSpan, int verticalSpan, KbKeyFunc keyFunc){
		this(label, shifKeyLabel, imageKey,horizontalSpan, verticalSpan);
		this.keyFunc = keyFunc;		
	}

	/**
	 * Constructor
	 * @param label Default label of the key, cannot be <code>null</code> but can be empty string.
	 * @param shifKeyLabel Label when shift button is pressed, set to <code>null</code> when shift functionality is same that default non-shift functionality.
	 * @param imageKey Image for the key, if not <code>null</code> then image is used instead of labels.
	 * @param horizontalSpan How many columns this key uses horizontally.
	 * @param verticalSpan How many rows this key uses vertically.
	 * @param keyFunc Key functionality for non-character keys.
	 * @param isToggle Is toggle button
	 * @see com.nokia.s60tools.remotecontrol.keyboard.ui.view.KbKey.KbKeyFunc
	 */
	public KbKey(String label, String shifKeyLabel, String imageKey, int horizontalSpan, int verticalSpan, KbKeyFunc keyFunc, boolean isToggle){
		this(label, shifKeyLabel, imageKey,horizontalSpan, verticalSpan, keyFunc);
		this.isToggle = isToggle;		
	}
}