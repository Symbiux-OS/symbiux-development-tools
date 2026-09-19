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


import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Image;

/**
 * Interface to providing image to copy into clipboard by <code>CopyImageToClipboardAction</code> action.
 */
public interface IImageProvider {
	
	/**
	 * Get the image object instance to copy into clipboard. Create image e.g
	 * <code>Image image = new Image(Display.getCurrent(), {@link org.eclipse.swt.widgets.Composite#getClientArea()#width}, {@link org.eclipse.swt.widgets.Composite#getClientArea()#height});</code>
	 * @return {@link Image} 
	 */
	public Image getImage();

	/**
	 * Gets Drawable.
	 * @return get the  {@link Drawable} e.g. {@link Composite} 
	 */
	public Drawable getDrawable();

}
