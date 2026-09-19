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


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;
import com.nokia.s60tools.imaker.internal.viewers.ISettingViewer;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapperPreferences;

/**
 * Class represents a simple container for data presented in Configuration,
 * Target and Setting objects (EMF). It only contains the most needed data
 * to be used in upper layers (UI).
 * 
 */
public class UIConfiguration {
//	public static final HashMap<String,String> UI_TARGETS = new HashMap<String,String>();
//	static {
//		UI_TARGETS.put("all",null);
//		UI_TARGETS.put("core",null);
//		UI_TARGETS.put("flash",null);
//		UI_TARGETS.put("image",null);
//		UI_TARGETS.put("mmc",null);
//		UI_TARGETS.put("rofs2",null);
//		UI_TARGETS.put("rofs3",null);
//		UI_TARGETS.put("rofs4",null);
//		UI_TARGETS.put("uda",null);
//		UI_TARGETS.put("udaerase",null);
//		UI_TARGETS.put("variant",null);
//		UI_TARGETS.put("default",null);
//	};
	/** Private instance variables */
	private String           productName  = null;
	private String           platformName = null;
	private String           makeFileName = null;
	private String           filePath     = null;
	private String           defaultHWID  = null;
	private List<String>     hwids        = null;
	private List<UITarget>   targets      = null;
	private List<UIVariable> variables    = null;
	private boolean          defaultConfig = false;
	

	private Set<ISettingViewer> changeListeners = new HashSet<ISettingViewer>();
	private IIMakerWrapper wrapper;
	private boolean loaded = false;
	
	/**
	 * Default constructor. Gets a Configuration object as a parameter
	 * and goes through it to find the needed data.
	 * 
	 * @param conf Configuration object to analyze
	 * @param wrapper 
	 */
	public UIConfiguration(Configuration conf, IIMakerWrapper wrapper) {
		this.wrapper = wrapper;
		if (conf != null) {
			makeFileName = conf.getName();
			filePath     = conf.getFilePath();
			targets      = new ArrayList<UITarget>();
			variables    = new ArrayList<UIVariable>();
			Target target;
			
			Iterator<Target> targetIter = conf.getTargetrefs().iterator();
			
			// Go through targets.
			while((targetIter != null) && targetIter.hasNext()) {
				target = targetIter.next();
				targets.add(new UITarget(target.getName(), target.getDescription()));
			}
			
			Iterator<Setting> settingIter = conf.getSettings().iterator();
			
			// Go through settings.
			while((settingIter != null) && settingIter.hasNext()) {
				Setting set                  = settingIter.next();
				ConfigurationElement element = set.getRef();
				String name = set.getName();
				if (name==null) continue;
				String value                 = name.toLowerCase();
				String elementName           = element.getName().toLowerCase();
				
				// Is setting a product name
				if ((value != null) && (set != null) && (elementName != null)) {
					// Put setting into variable list
					UIVariable var = new UIVariable(name, set.getValue(),
							"variable", element.getDescription(), element.getValues());
					variables.add(var);
					
					if (elementName.equals(IMakerWrapperPreferences.PRODUCT_NAME)) {
						productName = set.getValue();
					} else if (elementName.equals(IMakerWrapperPreferences.COREPLATFORM_NAME) ||
							elementName.equals(IMakerWrapperPreferences.UIPLATFORM_NAME)) {
						// Is setting a platform name
						platformName = set.getValue();
					} else if (elementName.equals(IMakerWrapperPreferences.HWID)) {
						hwids = new ArrayList<String>();
						StringTokenizer tokenizer = new StringTokenizer(set.getValue(),
								IMakerWrapperPreferences.HWID_DELIMITER);
						// Tokenize the list of HWIDs and loop through it.
						// Search for a default HWID which is marked by
						// a special character at the end of it.
						while (tokenizer.hasMoreTokens()) {
							String token = tokenizer.nextToken();
							if ((token != null) && (token.charAt(
								token.length()-1) == 
									IMakerWrapperPreferences.HWID_DEFAULT_MARKER)) {
								token = token.substring(0, token.length()-1);
								defaultHWID = token;
							}
							token = token.trim();
							hwids.add(token);
							if ((defaultHWID == null) || defaultHWID.equals("")) {
								defaultHWID = token;
							}
						}
						Collections.sort(hwids);
					}
				} else {
					// Something else found entirely?
				}
			}
		}
	}

	public boolean isDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(boolean def) {
		this.defaultConfig = def;
	}

	public String getDefaultTarget() {
		for (UITarget target : getAllTargets()) {
			if(target.getName().equals("default")) {
				return target.getName();
			}
		}
		return null;
	}
	
	/**
	 * Get method for productName
	 * 
	 * @return name of the product
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 * Get method for makeFileName
	 * 
	 * @return name of the makefile
	 */
	public String getMakeFileName() {
		return makeFileName;
	}
	
	/**
	 * Get method for filePath
	 * 
	 * @return file path
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Get method for default HWID
	 * 
	 * @return default HWID
	 */
	public String getDefaultHWID() {
		return defaultHWID;
	}
	
	/**
	 * Get method for target names
	 * 
	 * @return list of targets
	 */
	public List<UITarget> getAllTargets() {
		return targets;
	}
	
	/**
	 * Get method for variables
	 * 
	 * @return list of variables.
	 */
	public List<UIVariable> getVariables() {
		return variables;
	}
	
	/**
	 * Get method for HWIDs
	 * 
	 * @return a list of HWIDs
	 */
	public List<String> getHWIDs() {
		return hwids;
	}

	/**
	 * Get platform name
	 * 
	 * @return name of the platform
	 */
	public String getPlatformName() {
		return platformName;
	}
	
	public void removeChangeListener(ISettingViewer viewer) {
		changeListeners.remove(viewer);
	}
	
	public void addChangeListener(ISettingViewer viewer) {
		changeListeners.add(viewer);
	}
	
	public void variableChanged(UIVariable variable) {
		Iterator<ISettingViewer> iterator = changeListeners.iterator();
		while (iterator.hasNext())
			iterator.next().updateVariable(variable);
	}
	
	public String toString() {
		StringBuffer out = new StringBuffer();
		for(UIVariable v: variables) {
			if(v.isModified()) {
				out.append(v.toString()+"\n");
			}
		}
		return out.toString();
	}

	public void setTargets(List<UITarget> targets) {
		this.targets=targets;
	}

	public void setVariables(List<UIVariable> variables) {
		this.variables=variables;
	}
	
	public String getConfigurationName() {
		String path = getFilePath();
		String start = "image_conf_";
		int index = path.indexOf(start);
		return path.substring(index+start.length(), path.length()-3);
	}

	public List<UITarget> getFilteredTargets() {
//		List<UITarget> temp = new ArrayList<UITarget>();
		return getAllTargets();
//
//		String name = null;
//		for (UITarget target : all) {
//			name = target.getName();
//			if(UI_TARGETS.containsKey(name)) {
//				UI_TARGETS.put(name, target.getDescription());
//				temp.add(target);
//			}
//		}
//		return temp;
	}

	public boolean isPlatsimConfiguration() {
		List<UIVariable> vars = this.getVariables();
		for (UIVariable var : vars) {
			if(var.getName().equals(IMakerWrapperPreferences.USE_PLATSIM)) {
				String value = var.getValue();
				return (value.equals("1"))?true:false;
			}
		}
		return false;
	}

	public UITarget getTarget(String name) {
		for (UITarget t: getAllTargets()) {
			if(name.equals(t.getName())) {
				return t;
			}
		}
		return null;
	}

	public void reload() throws Throwable {
		loaded = false;
		load();
	}
	
	public void load() throws Throwable {
		if(loaded ) {
			return;
		}
		Display display = PlatformUI.getWorkbench().getDisplay();
		IRunnableWithProgress op = new IMakerCoreRunnable();
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(display.getActiveShell());
		try {
			progressMonitorDialog.run(true, false, op);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (InterruptedException e) {
			throw e;
		}
		loaded=true;		
	}

	private class IMakerCoreRunnable implements IRunnableWithProgress {
		public void run(IProgressMonitor monitor)
		throws InvocationTargetException, InterruptedException {
			try {
				List<UIConfiguration> confs = wrapper.getConfigurations(monitor,getFilePath());
				if(!confs.isEmpty()) {
					UIConfiguration configuration = confs.get(0);
					targets.clear();
					targets.addAll(configuration.getAllTargets());
					variables.clear();
					variables.addAll(configuration.getVariables());
					makeFileName = configuration.getConfigurationName();
					filePath     = configuration.getFilePath();
					productName = configuration.getProductName();
					platformName = configuration.getPlatformName();					
				}
			} catch (IMakerCoreExecutionException e) {
				InvocationTargetException te = new InvocationTargetException(e);
				throw te;
			} catch (IMakerCoreNotFoundException e) {
				InvocationTargetException te = new InvocationTargetException(e);
				throw te;
			}
		}
	}
}
