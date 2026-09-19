/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

 
 

package com.nokia.s60tools.metadataeditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.s60tools.metadataeditor.common.ProductInfoRegistry;
import com.nokia.s60tools.metadataeditor.util.MetadataEditorConsole;

/**
 * The activator class controls the plug-in life cycle
 */
public class MetadataEditorActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.s60tools.metadataeditor";	
	public static final String METADATA_EDITOR_ICON = "metadata_editor_16.png";
	public static final String METADATA_FILE_ICON = "metadata_file.png";
	public static final String METADATA_LARGE_ICON = "metadata_editor_75.png";


	// The shared instance
	private static MetadataEditorActivator plugin;
	
	/**
	 * Plugin installation location.
	 */
	private static String pluginInstallLocation;	
	
	/**
	 * The constructor
	 */
	public MetadataEditorActivator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		pluginInstallLocation = getPluginInstallPath();
		//This startup debug println has been left into the code in purpose
		System.out.println("pluginInstallLocation: " +  pluginInstallLocation); //$NON-NLS-1$

		// Call to getImagesPath requires that getPluginInstallPath() is
		// called beforehand.
		String imagesPath = getImagesPath();
		
		//This startup debug println has been left into the code in purpose
		System.out.println("imagesPath: " +  imagesPath); //$NON-NLS-1$
		
		initializeSettings();
		
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
	 * Initialized tool settings.
	 */
	private void initializeSettings() {
		String propPath = null;
		try {
			Properties props = new Properties();
			propPath = getPropertiesPath();
			System.out.println("Initializin settings from: "  +propPath);
			MetadataEditorConsole.getInstance().println("Initializin settings from: "  +propPath );
			
			FileInputStream in = new FileInputStream(propPath);
			props.load(in);	
			MetadataEditorPropertiesSetter propsSetter = new MetadataEditorPropertiesSetter();
			boolean ok = propsSetter.setProperties(props);
			if(!ok){
				MetadataEditorConsole.getInstance().println(
						"Erros while loading properties from file: "
						+propPath + ". Erros was: "
						+propsSetter.getErrors() );
				showInformationDialog("Errors while loading properties", 
						"Erros while loading properties from file: "
						+propPath + ".\n"
						+propsSetter.getErrors() );
			}
		} catch (FileNotFoundException e) {
			MetadataEditorConsole.getInstance().println(
					"Can't found settings file: " +propPath + " Using defaults instead." );
			System.out.println("Can't found settings file: " +propPath + " Using defaults instead." );					
			e.printStackTrace();
		} catch (Exception e) {
			MetadataEditorConsole.getInstance().println(
					"Errors opening settings file: " +propPath 
					+ " Using defaults instead. Error was: " +e.getMessage() );
			System.out.println("Errors opening settings file: " +propPath 
					+ " Using defaults instead. Error was: " +e.getMessage() );					
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Gets properties filename and path relative to given plugin install path.
	 * @return Path were properties are located.
	 */
	private String getPropertiesPath(){
		URL url = Platform.getInstallLocation().getURL();
		String bundleLocation = getBundle().getLocation();
		
		String startIndStr = "update@";
		String endIndStr = ".jar";
		int startIndx = bundleLocation.indexOf(startIndStr) +startIndStr.length();
		int endIndx = bundleLocation.indexOf(endIndStr);
		//Needed for running in Eclipse during develoment. 
		//If running in Carbide.c++ this is not needed
		if(endIndx == -1){
			endIndx = bundleLocation.lastIndexOf('/');
		}		
		bundleLocation = bundleLocation.substring( startIndx, endIndx );
		
		String path = url.getPath() + bundleLocation 
			+ File.separatorChar
			+ ProductInfoRegistry.getDataDirectoryName()
			+ File.separatorChar
			+ ProductInfoRegistry.getPropertiesFileName(); 
								
		File f = new File(path);
		return f.getAbsolutePath();
		
	}		
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static MetadataEditorActivator getDefault() {
		return plugin;
	}
	
	
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
    	
    	img = new Image( disp, getImagesPath() + "\\" +METADATA_EDITOR_ICON );        	 //$NON-NLS-1$
        imgReg.put( METADATA_EDITOR_ICON, img );

    	img = new Image( disp, getImagesPath() + "\\" +METADATA_FILE_ICON );        	 //$NON-NLS-1$
        imgReg.put( METADATA_FILE_ICON, img );

    	img = new Image( disp, getImagesPath() + "\\" +METADATA_LARGE_ICON);        	 //$NON-NLS-1$
        imgReg.put( METADATA_LARGE_ICON, img );
    	
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
	 * Show an information dialog
	 * @param title
	 * @param message
	 */
	private void showInformationDialog( String title, String message) {
		Shell sh = getCurrentlyActiveWbWindowShell();
		MessageDialog.openInformation(sh, title, message);
	}		
}
