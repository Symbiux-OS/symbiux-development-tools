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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import org.eclipse.swt.graphics.Image;

/**
 * Interface for data items.
 */
public interface IFtpObject{
	
	/**
	 * Returns name of the object.
	 * @return Name of the object.
	 */
	public String getName();
	/**
	 * Returns image for the object.
	 * @return image for the object.
	 */
	public Image getImage();
}