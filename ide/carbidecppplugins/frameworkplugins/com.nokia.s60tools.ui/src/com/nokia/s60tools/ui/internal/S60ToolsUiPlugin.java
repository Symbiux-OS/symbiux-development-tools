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
 
 
package com.nokia.s60tools.ui.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.s60tools.ui.UiUtils;
import com.nokia.s60tools.ui.actions.OpenPreferencePageAction;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;

/**
 * The main plugin class to be used in the desktop.
 */
public class S60ToolsUiPlugin extends AbstractUIPlugin {

	/**
	 * Shared plug-in ID.
	 */
	public static final String PLUGIN_ID = "com.nokia.s60tools.ui";

	/**
	 * The shared plug-in instance.
	 */
	private static S60ToolsUiPlugin plugin;
	
	/**
	 * Plugin installation location.
	 */
	private static String pluginInstallLocation;
	
	/**
	 * The constructor.
	 */
	public S60ToolsUiPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		pluginInstallLocation = getPluginInstallPath();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static S60ToolsUiPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path); //$NON-NLS-1$
	}

	/**
	 * 
	 * @return plugin install path
	 * @throws IOException
	 */
	public String getPluginInstallPath() throws IOException{
		 // URL to the plugin's root ("/")
		URL relativeURL = getBundle().getEntry("/"); //$NON-NLS-1$
		//	Converting into local path
		URL localURL = FileLocator.toFileURL(relativeURL);
		//	Getting install location in correct form
		File f = new File(localURL.getPath());
		String pluginInstallLocation = f.getAbsolutePath();
				
		return pluginInstallLocation;		
	}
	
	/**
	 * Gets images path relative to given plugin install path.
	 * @return Path were image resources are located.
	 */
	private String getImagesPath(){
		return pluginInstallLocation
				+ File.separatorChar
				+ "icons"; //$NON-NLS-1$
	}

	/* (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     */
    protected void initializeImageRegistry(ImageRegistry imgReg) {
    	
    	//
    	// Storing images to plugin's image registry
    	//
    	Display disp = Display.getCurrent();
    	
    	Image img = new Image( disp, getImagesPath() + File.separator + "empty_banner.png" );        	 //$NON-NLS-1$
        imgReg.put( UiUtils.NOKIA_TOOLS_BANNER, img );
        
    	img = new Image( disp, getImagesPath() + File.separator + "s60tools_action_icon.png" );        	 //$NON-NLS-1$
        imgReg.put( S60ToolsBaseAction.S60TOOLS_ACTION_ICON, img );
        
    	img = new Image( disp, getImagesPath() + File.separator + "preferences.png"); //$NON-NLS-1$
        imgReg.put( OpenPreferencePageAction.IMG_PREFERENCE_PAGE, img );        
        
    }
    
	/**
	 * Returns image descriptor for the given key from the plugin's image registry.
	 * @param key Key to search descriptor for.
	 * @return Image descriptor for the given key from the plugin's image registry.
	 */
	public static ImageDescriptor getImageDescriptorForKey( String key ){
    	ImageRegistry imgReg = getDefault().getImageRegistry();
    	return  imgReg.getDescriptor( key );		
	}	

	/**
	 * Returns image for the given key from the plugin's image registry.
	 * @param key Key to search image for.
	 * @return Image for the given key from the plugin's image registry.
	 */
	public static Image getImageForKey( String key ){
    	ImageRegistry imgReg = getDefault().getImageRegistry();    	
    	return  imgReg.get(key);		
	}
	
}
