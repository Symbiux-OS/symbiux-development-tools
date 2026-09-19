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
package com.nokia.s60tools.symbianfoundationtemplates;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.s60tools.symbianfoundationtemplates.resources.Messages;

/**
 * The activator class controls the plug-in life cycle
 */
public class SymbianFoundationTemplates extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.s60tools.symbianfoundationtemplates"; //$NON-NLS-1$

	// The shared instance
	private static SymbianFoundationTemplates plugin;
	
	/**
	 * The constructor
	 */
	public SymbianFoundationTemplates() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SymbianFoundationTemplates getDefault() {
		return plugin;
	}
	
	/**
	 * Show the error dialog.
	 * 
	 * @param error the error message.
	 * @param reason the reason of the error.
	 */
	public void showErrorDialog(String error, String reason) {
		IStatus status = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, reason, null);
		
		ErrorDialog.openError(Display.getCurrent().getActiveShell(),
				Messages.getString("ErrorMessageTitle"), //$NON-NLS-1$
				error, status);
	}
	
	/**
	 * Get the location of the templates associated with this plugin.
	 * 
	 * @return the location of templates
	 */
	public String getTemplatesPath() {
		return getRootPath() + "templates"; //$NON-NLS-1$
	}
	
	/**
	 * Get the location of the images associated with this plugin.
	 * 
	 * @return the location of images
	 */
	public String getImagesPath() {
		return getRootPath() + "images"; //$NON-NLS-1$
	}
	
	/**
	 * Get the plugin root path of this plugin
	 * @return the plugin installation path
	 */
	private String getRootPath() {
		URL relativeURL = getBundle().getEntry("/"); //$NON-NLS-1$
		
		URL localURL = null;
		try {
			localURL = FileLocator.toFileURL(relativeURL);
		} catch(IOException ioe) {
		}
		
		if(localURL == null)
			return null;
		
		File f = new File(localURL.getPath());
		
		return f.getAbsolutePath() + File.separator;
	}
}
