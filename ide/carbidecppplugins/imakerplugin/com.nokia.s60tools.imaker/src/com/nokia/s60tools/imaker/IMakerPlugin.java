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

package com.nokia.s60tools.imaker;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.nokia.s60tools.imaker.internal.managers.EnvironmentManager;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapper;

/**
 * The activator class controls the plug-in life cycle
 */
public class IMakerPlugin extends AbstractUIPlugin {

    // The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.s60tools.imaker"; //$NON-NLS-1$

	// The shared instance
	private static IMakerPlugin plugin;
	
	/**
	 * The constructor
	 */
	public IMakerPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
    public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static IMakerPlugin getDefault() {
		return plugin;
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
	 * @return Environment manager singleton
	 */
	public static IEnvironmentManager getEnvironmentManager() {
		return EnvironmentManager.getInstance();
	}
	
	/**
	 * Creates iMaker wrapper object for the iMaker Command line tool located in the specified environment
	 * @param epocRoot The root of the environment where iMaker is located
	 * @param isImakerTool if true <b>epocRoot</b> is expected to be full path to iMaker executable file, otherwise 
	 * it is assumed to be the root of the environment where iMaker is located
	 * @return IIMakerWrapper object
	 */
	public static IIMakerWrapper getImakerWrapper(String epocRoot, boolean isImakerTool) {
		if(isImakerTool) {
			List<String> tool = new ArrayList<String>();
			tool.add(epocRoot);
			IIMakerWrapper wrapper = new IMakerWrapper(tool);	
			return wrapper;
		} else {
			List<String> imaker = IMakerUtils.getImakerTool(epocRoot);
			IIMakerWrapper wrapper = new IMakerWrapper(imaker);	
			return wrapper;			
		}
	}

	private class ImakerSchedulingRule implements ISchedulingRule {

		public boolean contains(ISchedulingRule rule) {
			if(rule instanceof ImakerSchedulingRule) {
				return true;
			}
			return false;
		}

		public boolean isConflicting(ISchedulingRule rule) {
			if(rule instanceof ImakerSchedulingRule) {
				return true;
			}
			return false;
		}
		
	};
	
	private ISchedulingRule imakerRule = new ImakerSchedulingRule();
	
	public ISchedulingRule getImakerRule() {
		return imakerRule;
	}
}
