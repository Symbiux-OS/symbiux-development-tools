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

package com.nokia.s60tools.remotecontrol.job;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.ListenerList;

/**
 * Singleton class that is created on plugin
 * startup, and is kept active as long as plugin is active.
 * 
 * The purpose of this class is to enable shutdown of all
 * ongoing jobs on forced shutdown. All the jobs implementing
 * <code>IManageableJob</code> should register itself to 
 * this class and unregister when completed.
 */
public class RemoteControlJobManager {

	/**
	 * Singleton instance.
	 */
	static private RemoteControlJobManager instance = null;

	/**
	 * List of registered jobs.
	 */
	private ArrayList<IManageableJob> registeredJobs = null;

	/**
	 * Listeners interested in job completions operations.
	 */
	private ListenerList listeners = null;
	
	/**
	 * Public Singleton instance accessor.
	 * @return Returns instance of this singleton class-
	 */
	public static RemoteControlJobManager getInstance(){
		if( instance == null ){
			instance = new RemoteControlJobManager();
		}
		return instance;		
	}	
	
	/**
	 * Private default constructor.
	 */
	private RemoteControlJobManager() {
		registeredJobs = new ArrayList<IManageableJob>();
		listeners = new ListenerList();
	}
	
	/**
	 * Register job
	 * @param job Job
	 */
	public void registerJob(IManageableJob job){
		registeredJobs.add(job);
	}

	/**
	 * Unregister job
	 * @param job Job
	 */
	public void unregisterJob(IManageableJob job){
		registeredJobs.remove(job);
		Object[] listenerArray = listeners.getListeners();
		for (int i = 0; i < listenerArray.length; i++) {
			IJobCompletionListener listenerObj 
								= (IJobCompletionListener) listenerArray[i];
			listenerObj.backgroundJobCompleted(job);
		}
	}
	
	/**
	 * Shutdown registered jobs
	 */
	public void shutdown(){
		for (Iterator<IManageableJob> iter = registeredJobs.iterator(); iter.hasNext();) {
			IManageableJob job = iter.next();
			job.forcedShutdown();			
		}
		registeredJobs.clear();
		// Giving a moment for processes to really shutdown
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add listener 
	 * @param obj Listener
	 */
	public void addListener(IJobCompletionListener obj){
		listeners.add(obj);
	}
	
	/**
	 * Remove listener
	 * @param obj Listener
	 */
	public void removeListener(IJobCompletionListener obj){
		listeners.remove(obj);
	}
}
