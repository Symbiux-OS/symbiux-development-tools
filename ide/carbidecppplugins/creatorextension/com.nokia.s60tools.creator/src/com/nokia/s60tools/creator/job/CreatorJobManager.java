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

 
package com.nokia.s60tools.creator.job;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.jobs.Job;


/**
 * Singleton class that is created on plugin
 * startup, and is kept active as long as plugin is active.
 * 
 * The purpose of this class is to enable shutdown of all
 * ongoing jobs on forced shutdown. All the jobs implementing
 * <code>IManageableJob</code> should register itself to 
 * this class and unregister when completed.
 */
public class CreatorJobManager {

	/**
	 * Singleton instance.
	 */
	static private CreatorJobManager instance = null;

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
	public static CreatorJobManager getInstance(){
		if( instance == null ){
			instance = new CreatorJobManager();
		}
		return instance;		
	}	
	
	/**
	 * Private default constructor.
	 */
	private CreatorJobManager() {
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

	/**
	 * Check if job with given class name is allready running 
	 * @param jobClassName get by using  {@link Class#getName()} for used {@link Job} -class.
	 * @return <code>true</code> if job with given {@link Class} name is already running
	 * <code>false</code> otherwise.
	 */
	public boolean isJobAlreadyRunning(String jobClassName) {
		for (Iterator<IManageableJob> iter = registeredJobs.iterator(); iter.hasNext();) {
			IManageableJob job = iter.next();
			if(job.getClass().getName().equals(jobClassName)){
				return true;
			}
		}		
		return false;
	}
}
