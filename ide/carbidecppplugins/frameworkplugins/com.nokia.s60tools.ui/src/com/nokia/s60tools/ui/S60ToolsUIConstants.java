/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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

/**
 * This class defines a set constants that are specified
 * in Carbide branding guideline:<br><br>
 * 
 * <b>CARBIDE LOOK & FEEL SPECIFICATION<br>
 * Top of Eclipse User Interface Guidelines</b>
 *
 */
public class S60ToolsUIConstants {

	/**
	 * Margin between title bar and icon placeholder
	 * Defined in section <b>3.2.2 Wizard dialogs</b>.
	 */
	public static final int MARGIN_BTW_TITLE_BAR_AND_ICON_PLACEHOLDER= 10; //c

	/**
	 * Margin between frame and icon placeholder
	 * Defined in section <b>3.2.2 Wizard dialogs</b>.
	 */
	public static final int MARGIN_BTW_FRAME_AND_ICON_PLACEHOLDER= 17; //d

	/**
	 * Margin between title bar and title text
	 * Defined in section <b>3.2.2 Wizard dialogs</b>.
	 */
	public static final int MARGIN_BTW_TITLE_BAR_AND_TITLE_TEXT= 20; //e

	/**
	 * Margin between between text fields
	 * Defined in section <b>3.2.2 Wizard dialogs</b>.
	 */
	public static final int MARGIN_BTW_TEXT_FIELDS= 12; //f

	/**
	 * Margin between frame and its contents
	 * Defined in section <b>3.2.2 Wizard dialogs</b>.
	 */
	public static final int MARGIN_BTW_FRAME_AND_CONTENTS= 13; //g
	
	/**
	 * Wizards should by default have this width.
	 * Width can be enforced to a wizard instance
	 * with the following kind of code:
	 * <code>
	 * WizardDialog wizDialog = new ... // Creating concrete dialog instance
	 * // Forcing preferred initial size
	 * wizDialog.getShell().setSize(S60ToolsUIConstants.WIZARD_DEFAULT_WIDTH, 
	 *                              S60ToolsUIConstants.WIZARD_DEFAULT_HEIGHT);
	 * </code>				              		
	 */
	public static final int WIZARD_DEFAULT_WIDTH = 440;
	
	/**
	 * Wizards should by default have this height.
	 * Height can be enforced to a wizard instance
	 * with the following kind of code:
	 * <code>
	 * WizardDialog wizDialog = new ... // Creating concrete dialog instance
	 * // Forcing preferred initial size
	 * wizDialog.getShell().setSize(S60ToolsUIConstants.WIZARD_DEFAULT_WIDTH, 
	 *                              S60ToolsUIConstants.WIZARD_DEFAULT_HEIGHT);
	 * </code>				              		
	 */
	public static final int WIZARD_DEFAULT_HEIGHT = 463;
	
}
