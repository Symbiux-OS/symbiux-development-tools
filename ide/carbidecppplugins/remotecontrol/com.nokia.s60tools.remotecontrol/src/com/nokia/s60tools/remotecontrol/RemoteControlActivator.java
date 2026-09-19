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


package com.nokia.s60tools.remotecontrol;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.s60tools.remotecontrol.common.ProductInfoRegistry;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpUtils;
import com.nokia.s60tools.remotecontrol.job.RemoteControlJobManager;
import com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator;
import com.nokia.s60tools.remotecontrol.keyboard.KeyboardMediator;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * The main plugin class to be used in the desktop.
 */
public class RemoteControlActivator extends AbstractUIPlugin {

	private static final String PLUGIN_ID = "com.nokia.s60tools.remotecontrol.plugin"; //$NON-NLS-1$

	/**
	 * Shared plugin instance.
	 */
	private static RemoteControlActivator plugin;
	
	/**
	 * Plugin installation location.
	 */
	private static String pluginInstallLocation;
	
	/**
	 * Storing preferences
	 */
	private static IPreferenceStore prefsStore;	
	
	/**
	 * Manager for jobs
	 */
	private static RemoteControlJobManager pluginJobManager;

	/**
	 * Keyboard event mediator utility class that is available for whole plug-in life time.
	 * Though being singleton class, it should be only queried from Activator 
	 * and used via IKeyboardMediator interface.
	 */
	private static IKeyboardMediator keyboardMediator = new KeyboardMediator(RemoteControlConsole.getInstance());
	
	/**
	 * This member variable is only used internally for JUnit testing purposes.
	 */
	private String alternatePluginInstallPath = null;
	
	/**
	 * The constructor.
	 */
	public RemoteControlActivator() {
		plugin = this;
		pluginJobManager = RemoteControlJobManager.getInstance();
	}

	/**
	 * Gets plug-in's installation path.
	 * @return plug-in's installation path.
	 * @throws IOException
	 */
	private String getPluginInstallPath() throws IOException{
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
	 * Returns runtime workspace directory path for the Remote Control, which
	 * can be used e.g. to store temporary data.
	 * @return Workspace directory path for the Remote Control.
	 */
	public String getPluginWorkspacePath() {
		IPath runtimeWorkspacePath = this.getStateLocation();
		runtimeWorkspacePath.makeAbsolute();		
		return runtimeWorkspacePath.toOSString();
	}
	
	/**
	 * Returns directory that is used for opened temporary files.
	 * @return directory that is used for opened temporary files.
	 */
	public String getDownloadTempDir() {
		String tempDir = getPluginWorkspacePath();
		tempDir = FtpUtils.addFileSepatorToEnd(tempDir);
		tempDir = tempDir + "temp" + File.separator; //$NON-NLS-1$
		return tempDir;
	}
	
	/**
	 * Gets images path relative to given plug-in install path.
	 * @return Path were image resources are located.
	 */
	private String getImagesPath(){
		return pluginInstallLocation
				+ File.separatorChar
				+ ProductInfoRegistry.getImagesDirectoryName();
	}
	
	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		//This startup debug print has been left into the code in purpose
		System.out.println(Messages.getString("RemoteControlActivator.Startup_ConsoleMsg"));  //$NON-NLS-1$
		
		pluginInstallLocation = getPluginInstallPath();

		//This startup debug print has been left into the code in purpose
		System.out.println(Messages.getString("RemoteControlActivator.Plugin_Install_Location_ConsoleMsg")  //$NON-NLS-1$
				+ pluginInstallLocation);

		// Call to getImagesPath requires that getPluginInstallPath() is
		// called beforehand.
		String imagesPath = getImagesPath();
		
		//This startup debug print has been left into the code in purpose
		System.out.println(Messages.getString("RemoteControlActivator.Images_Path_ConsoleMsg") //$NON-NLS-1$
				+ imagesPath);
		
		// Loading images required by this plug-in
		ImageResourceManager.loadImages(imagesPath);
		
		// Initialize settings
		initializeSettings(pluginInstallLocation);
	}

	/**
	 * This method is the correct place for initializing plugin-wide settings during start-up.
	 * @param pluginInstallLocation
	 */
	private void initializeSettings(String pluginInstallLocation) {
		
		// Initialize default values for preferences
		RCPreferences.initDefaultValues();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		// Shutting down registered ongoing background jobs
		pluginJobManager.shutdown();
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static RemoteControlActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path Path to image file
	 * @return The image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
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
	
	/**
	 * Gets plug-in ID.
	 * @return plug-in ID.
	 */
	public static String getPluginID() {
		return PLUGIN_ID;
	}
	
	/**
	 * Returns reference to keyboard mediator utility.
	 * @return the keyboardMediator Keyboard mediator.
	 */
	public static IKeyboardMediator getKeyboardMediator() {
		return keyboardMediator;
	}

	//
	// Internally used methods.
	//
	
	/**
	 * This constructor is only used for setting alternate
	 * plugin install path  just used for JUnit tests.
	 * @param alternateInstallPath Alternate plug-in install path.
	 */
	private RemoteControlActivator(String alternateInstallPath){
		alternatePluginInstallPath = alternateInstallPath;		
	}

	/**
	 * Creates activator instance if not already created, with different plugin install path. 
	 * This creates always a new instance of the activator, and need to be called 
	 * only when needed.
	 * 
	 * This methods to be used only from JUnit tests
	 * and can be used to set alternate plugin install path 
	 * that is just used for JUnit tests.
	 * 
	 * @param alternateInstallPath Alternate plugin install path used for testing.
	 * @return Reference to activator instance
	 */
	public static RemoteControlActivator createWithAlternateSettings(String alternateInstallPath){
		plugin = new RemoteControlActivator(alternateInstallPath);			
		return plugin;
	}
}
