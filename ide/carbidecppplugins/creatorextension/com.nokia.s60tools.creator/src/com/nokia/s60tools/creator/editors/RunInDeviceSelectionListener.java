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

package com.nokia.s60tools.creator.editors;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.nokia.s60tools.creator.job.CreatorJobManager;
import com.nokia.s60tools.creator.job.RunInDeviceJob;
import com.nokia.s60tools.creator.preferences.CreatorPreferences;
import com.nokia.s60tools.creator.util.CreatorEditorConsole;

public class RunInDeviceSelectionListener implements SelectionListener {
	
	private static final String SYMBIAN_FILE_SEPARATOR = "\\";
	private final String filePath;
	private final String fileName;

	public RunInDeviceSelectionListener(String fileName, String filePath){
		this.fileName = fileName;
		this.filePath = filePath;		
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		String targetFileNameAndPath = getTargetFilePath();

		String jobName = "Executing script: '" +fileName +"' in device.";

		//Checking if job already running, not start if is
		boolean jobAllreadyRunning = CreatorJobManager.getInstance().isJobAlreadyRunning(RunInDeviceJob.class.getName());
		if(!jobAllreadyRunning){
			RunInDeviceJob job = new RunInDeviceJob(jobName, filePath,  targetFileNameAndPath, fileName);
			job.schedule();
		}
		//else printing message
		else{
			CreatorEditorConsole.getInstance().println("Excecution of script: '" +fileName +"' already running, running scripts simultaneously not allowed.", CreatorEditorConsole.MSG_WARNING);
		}
	}

	/**
	 * Get path for target file
	 * @return path for target file
	 */
	private String getTargetFilePath() {
		//Getting folder from references and check if folder ends with symbian file separator
		//note File.separatorchar cannot be used!
		String targetFolder = CreatorPreferences.getDeviceSaveFolded();
		if(!targetFolder.endsWith(SYMBIAN_FILE_SEPARATOR)){
			targetFolder = targetFolder + SYMBIAN_FILE_SEPARATOR;
		}
		return targetFolder +fileName;
	}

}
