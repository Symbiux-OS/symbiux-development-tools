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

package com.nokia.s60tools.imaker.internal.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nokia.s60tools.imaker.IEnvironment;
import com.nokia.s60tools.imaker.IEnvironmentManager;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.internal.model.Environment;

public class EnvironmentManager implements IEnvironmentManager {
	private static IEnvironmentManager instance = null;
	private ArrayList<IEnvironment> environments;
	private IEnvironment activeEnvironment;
	private File lastRun;

	public static IEnvironmentManager getInstance() {
		if(instance==null) {
			instance = new EnvironmentManager();
		}
		return instance;
	}

	private EnvironmentManager() {
		environments = new ArrayList<IEnvironment>();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.managers.IEnvironmentManager#setActiveEnvironment(java.lang.String)
	 */
	public void setActiveEnvironment(String drive) {
		this.activeEnvironment = getEnvironmentByDrive(drive);
	}
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.managers.IEnvironmentManager#getActiveEnvironment()
	 */
	public IEnvironment getActiveEnvironment() {
		return activeEnvironment;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.managers.IEnvironmentManager#getEnvironmentByDrive(java.lang.String)
	 */
	public IEnvironment getEnvironmentByDrive(String drive) {
		List<IEnvironment> enviroments = getEnviroments();
		for (Iterator<IEnvironment> iterator = enviroments.iterator(); iterator.hasNext();) {
			IEnvironment environment = (IEnvironment) iterator.next();
			if(environment.getDrive().startsWith(drive)) {
				return environment;
			}
		}
		if(IMakerUtils.iMakerCoreExists(drive)) {
			Environment newEnv = new Environment(drive);
			environments.add(newEnv);
			return newEnv;
		}
		//print error
		System.out.println("iMaker extension can't be used with this project!");
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.managers.IEnvironmentManager#getEnviroments()
	 */
	public List<IEnvironment> getEnviroments() {
		List<String> availableEnvironments = IMakerUtils.getAvailableSDKs();
		for (Iterator<String> iterator = availableEnvironments.iterator(); iterator.hasNext();) {
			String env = iterator.next();
			if(!environmentAlreadyAvailable(env)) {
				environments.add(new Environment(env));
			}
		}
		return environments;
	}

	private boolean environmentAlreadyAvailable(String env) {
		for (Iterator<IEnvironment> iterator = environments.iterator(); iterator.hasNext();) {
			IEnvironment environment = iterator.next();
			if(environment.getDrive().equals(env)) {
				return true;
			}
		}
		return false;
	}

	public void setLastRun(File file) {
		this.lastRun  = file;
	}
	
	public File getLastRun() {
		return lastRun;
	}
}
