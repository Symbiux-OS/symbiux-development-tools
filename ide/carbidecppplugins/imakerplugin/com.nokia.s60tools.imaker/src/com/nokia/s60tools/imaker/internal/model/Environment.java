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
package com.nokia.s60tools.imaker.internal.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.IEnvironment;
import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapper;

public class Environment implements IEnvironment {
	private String drive;
	private String iMakerCoreVersion=null;
	private boolean loaded;
	private List<UIConfiguration> configurations;
	private UIConfiguration currentProduct;
	private IIMakerWrapper wrapper = null;
	private String targetSteps = null;
	private ImakerProperties runProperties=null; //runtime selected properties
	
	public Environment(String drive) {
		this.drive = drive;
		this.setImakerWrapper(new IMakerWrapper(IMakerUtils.getImakerTool(drive)));
		runProperties = new ImakerProperties();		
	}
	
	public IIMakerWrapper getImakerWrapper() {
		return this.wrapper;
	}
	
	public void setImakerWrapper(IIMakerWrapper wrapper) {
		this.wrapper = wrapper;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#getDrive()
	 */
	public String getDrive() {
		return drive;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#isLoaded()
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#reLoad()
	 */
	public List<UIConfiguration> reLoad()  throws InvocationTargetException {
		loaded = false;
		return load();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#load()
	 */
	public List<UIConfiguration> load() throws InvocationTargetException {
		if(loaded) {
			return configurations;
		}
		IRunnableWithProgress op = new IMakerCoreRunnable();
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(getDisplay().getActiveShell());
		try {
			progressMonitorDialog.run(true, true, op);
		} catch (InvocationTargetException e) {
			configurations = null;
			loaded = false;
			throw e;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loaded=true;
		return configurations;
	}

	private Display getDisplay() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		return display;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#getConfigurations()
	 */
	public List<UIConfiguration> getConfigurations() throws InvocationTargetException {
		if(loaded) {
			return configurations;
		} else {
			load();
			return configurations;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#getConfigurationByFilePath(java.lang.String)
	 */
	public UIConfiguration getConfigurationByFilePath(String filePath) {
		if(configurations==null) {
			return null;
		}
		for (Iterator<UIConfiguration> iterator = configurations.iterator(); iterator.hasNext();) {
			UIConfiguration config = iterator.next();
			if(config.getFilePath().equals(filePath)) {
				return config;
			}
		}
		return null;
	}

	private class IMakerCoreRunnable implements IRunnableWithProgress {
		public void run(IProgressMonitor monitor)
		throws InvocationTargetException, InterruptedException {
			try {
				configurations = getImakerWrapper().getConfigurations(monitor,null);
				if(configurations.size()==0) {
					IMakerCoreExecutionException ex = new IMakerCoreExecutionException(Messages.getString("Error.1"));
					InvocationTargetException te = new InvocationTargetException(ex);
					throw te;
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

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#getIMakerCoreVersion()
	 */
	public String getIMakerCoreVersion() throws IMakerCoreNotFoundException, IMakerCoreExecutionException {
		if(iMakerCoreVersion==null) {
			iMakerCoreVersion = getImakerWrapper().getIMakerCoreVersion();
		}
		return iMakerCoreVersion;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#setCurrentProduct(com.nokia.s60tools.imaker.internal.model.UIConfiguration)
	 */
	public void setCurrentProduct(UIConfiguration uic) {
		this.currentProduct = uic;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.model.IEnvironment#getCurrentProduct()
	 */
	public UIConfiguration getCurrentProduct() {
		return currentProduct;
	}
	
	public String getTargetSteps(final String target) {
		targetSteps = null;
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(getDisplay().getActiveShell());
		try {
			progressMonitorDialog.run(true, false, new IRunnableWithProgress() {
				
//				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {
						targetSteps = wrapper.getTargetSteps(target, getCurrentProduct().getFilePath(), monitor);
					} catch (IMakerCoreExecutionException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
		}
		return targetSteps;
	}

	public ImakerProperties getRunProperties() {
		return runProperties;
	}
}
