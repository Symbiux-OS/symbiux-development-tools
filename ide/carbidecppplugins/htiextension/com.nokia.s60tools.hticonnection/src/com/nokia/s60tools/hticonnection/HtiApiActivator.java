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

package com.nokia.s60tools.hticonnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.s60tools.hticonnection.common.ProductInfoRegistry;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.RequestQueueManager;
import com.nokia.s60tools.hticonnection.preferences.HtiApiPreferences;
import com.nokia.s60tools.hticonnection.preferences.IHtiApiPreferences;
import com.nokia.s60tools.hticonnection.resources.ImageResourceManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class HtiApiActivator extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.s60tools.hticonnection"; //$NON-NLS-1$

	// The shared instance
	private static HtiApiActivator plugin;
	
	/**
	 * Shared singleton preferences instance available for whole plug-in lifetime
	 */
	private static IHtiApiPreferences preferences;
	
	/**
	 * Keeps HTI requests in queue and runs them.
	 */
	private RequestQueueManager queueManager;
	/**
	 * Manages connection status and tests connection when necessary.
	 */
	private HtiConnection connectionManager;
	
	/**
	 * Storing preferences
	 */
	private static IPreferenceStore prefsStore;
	
	/**
	 * Plugin installation location.
	 */
	private static String pluginInstallLocation;
	
	/**
	 * This member variable is only used internally for JUnit testing purposes.
	 */
	private String alternatePluginInstallPath = null;
	
	/**
	 * The constructor
	 */
	public HtiApiActivator() {
		plugin = this;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		pluginInstallLocation = getPluginInstallPath();
		
		//This startup debug println has been left into the code in purpose
		System.out.println("pluginInstallLocation: " +  pluginInstallLocation); //$NON-NLS-1$
		
		// Call to getImagesPath requires that getPluginInstallPath() is
		// called beforehand.
		String imagesPath = getImagesPath();
		
		//This startup debug println has been left into the code in purpose
		System.out.println("imagesPath: " +  imagesPath); //$NON-NLS-1$
		
		// Loading images required by this plug-in
		ImageResourceManager.loadImages(imagesPath);
		
		//This startup debug println has been left into the code in purpose
		System.out.println("HTI-API Plugin STARTUP..."); //$NON-NLS-1$

		initializeSettings();
	}
	
	/**
	 * This method is the correct place for initializing plugin-wide settings during start-up.
	 */
	private void initializeSettings() {
		queueManager = RequestQueueManager.getInstance();
		connectionManager = HtiConnection.getInstance();
		// Initialization is not needed before starting activator and tests don't require initialization.
		connectionManager.init();

		// Initialize default values for preferences
		getPreferences().initDefaultValues();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		//This shutdown debug println has been left into the code in purpose
		System.out.println("HTI-API Plugin SHUTDOWN..."); //$NON-NLS-1$

		// Stopping and clearing the request queue.
		queueManager.stop();
		connectionManager.stop();

		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Returns the shared instance.
	 * @return the shared instance
	 */
	public static HtiApiActivator getDefault() {
		return plugin;
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
	 * Gets plug-in's installation path.
	 * @return plug-in's installation path.
	 * @throws IOException
	 */
	public String getPluginInstallPath() throws IOException{
		// Returning alternate install path, if set for JUnit testing purposes.
		if(alternatePluginInstallPath != null){
			return alternatePluginInstallPath;
		}
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
	
	/**
	 * Returns the PreferenceStore where plugin preferences are stored
	 * @return the PreferenceStore where plugin preferences are stored
	 */
	public static IPreferenceStore getPrefsStore(){
		if (prefsStore == null){
			prefsStore = getDefault().getPreferenceStore();
		}
		
		return prefsStore;
	}
	
	/**
	 * Gets if preference store is initialized.
	 * @return True if preference store is initialized.
	 */
	public static boolean isPrefStoreinitialized() {
		if (prefsStore != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets reference to plug-in's preference store.
	 * @return Reference to plug-in's preference store.
	 */
	public static IHtiApiPreferences getPreferences(){
		if(preferences == null){
			preferences = new HtiApiPreferences();
		}
		return preferences;
	}
	
	//
	// Internally used methods.
	//
	
	/**
	 * This constructor is only used for setting alternate
	 * preference store just used for JUnit tests.
	 * @param alternatePreferences Alternate preference store implementation.
	 * @param alternateInstallPath Alternate plug-in install path.
	 */
	private HtiApiActivator(IHtiApiPreferences alternatePreferences, String alternateInstallPath){
		preferences = alternatePreferences;
		alternatePluginInstallPath = alternateInstallPath;		
	}

	/**
	 * Creates activator instance if not already created, with reference 
	 * to alternate preference store, and with different plugin install path. 
	 * This creates always a new instance of the activator, and need to be called 
	 * only when needed.
	 * 
	 * This methods to be used only from JUnit tests
	 * and can be used to set alternate preference store 
	 * that is just used for JUnit tests.
	 * 
	 * @param alternatePreferences Alternate preference store implementation.
	 * @param alternateInstallPath Alternate plugin install path used for testing.
	 * @return Reference to activator instance
	 */ 
	public static HtiApiActivator createWithAlternateSettings(IHtiApiPreferences alternatePreferences, String alternateInstallPath){
		plugin = new HtiApiActivator(alternatePreferences, alternateInstallPath);			
		return plugin;
	}
}
