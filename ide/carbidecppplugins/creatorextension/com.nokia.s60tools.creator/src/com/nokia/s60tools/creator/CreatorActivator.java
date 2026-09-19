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
package com.nokia.s60tools.creator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.s60tools.creator.common.ProductInfoRegistry;
import com.nokia.s60tools.ui.UiUtils;


/**
 * The activator class controls the plug-in life cycle
 */
public class CreatorActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.s60tools.creator";
	
	public static final String CREATOR_SCRIPT_EDITOR_ICON = "creator_16x16.png";
	public static final String NOKIA_TOOLS_BANNER = "empty_banner.png";
	public static final String CREATOR_SCRIPT_LARGE_ICON = "creator_55x46.png";	

	// The shared instance
	private static CreatorActivator plugin;

	/**
	 * Storing preferences
	 */
	private static IPreferenceStore prefsStore;
	
	/**
	 * Plugin installation location.
	 */
	private static String pluginInstallLocation;	
	
	/**
	 * The constructor
	 */
	public CreatorActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		pluginInstallLocation = getPluginInstallPath();
		//This startup debug println has been left into the code in purpose
		System.out.println("pluginInstallLocation: " +  pluginInstallLocation); //$NON-NLS-1$
		
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
	public static CreatorActivator getDefault() {
		return plugin;
	}
	
	/**
	 * Gets images path relative to given plugin install path.
	 * @return Path were image resources are located.
	 */
	private String getImagesPath(){
		return pluginInstallLocation
				+ File.separatorChar
				+ ProductInfoRegistry.getImagesDirectoryName();
	}		
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     */
    protected void initializeImageRegistry(ImageRegistry imgReg) {
    	
    	//
    	// Storing images to plugin's image registry
    	//
    	Display disp = Display.getCurrent();
    	Image img = null;
    	
    	img = new Image( disp, getImagesPath() + "\\" +CREATOR_SCRIPT_EDITOR_ICON );        	 //$NON-NLS-1$
        imgReg.put( CREATOR_SCRIPT_EDITOR_ICON, img );

    	img = new Image( disp, getImagesPath() + "\\" +NOKIA_TOOLS_BANNER );        	 //$NON-NLS-1$
        imgReg.put( NOKIA_TOOLS_BANNER, img );

    	img = new Image( disp, getImagesPath() + "\\" +CREATOR_SCRIPT_LARGE_ICON);        	 //$NON-NLS-1$
        imgReg.put( CREATOR_SCRIPT_LARGE_ICON, img );
    	
    }		

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
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
	
	
	/**
	 * @return path where plug-in is installed
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
	 * This must be called from UI thread. If called
	 * from non-ui thread this returns <code>null</code>.
	 * @return Currently active workbench page.
	 */
	public static IWorkbenchPage getCurrentlyActivePage(){
		return getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}	
	
	/**
	 * This must be called from UI thread. If called
	 * from non-ui thread this returns <code>null</code>.
	 * @return The shell of the currently active workbench window..
	 */
	public static Shell getCurrentlyActiveWbWindowShell(){
		IWorkbenchPage page = getCurrentlyActivePage();
		if(page != null){
			return page.getWorkbenchWindow().getShell();
		}
		return null;
	}

	/**
	 * Get Wizard icon with banner
	 * @return
	 */
	public static ImageDescriptor getWizardImage() {
		return UiUtils.getBannerImageDescriptor(getImageDescriptorForKey(CREATOR_SCRIPT_LARGE_ICON));

	}	
	
	/**
	 * Returns the PreferenceStore where plugin preferences are stored
	 * 
	 * @return the PreferenceStore where plugin preferences are stored
	 */
	public static IPreferenceStore getPrefsStore(){
		if (prefsStore == null){
			prefsStore = getDefault().getPreferenceStore();
		}
		
		return prefsStore;
	}
	
}
