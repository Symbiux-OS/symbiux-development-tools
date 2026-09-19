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

public class FileToImage {
	private String fHostPath = ""; 
	private String fTargetPath = ""; 
	private boolean fEnabled = true;
	
	public FileToImage() {}
	
	public FileToImage(String hostPath, String targetPath, boolean enabled) {
		fHostPath = hostPath;
		fTargetPath = targetPath;
		fEnabled = enabled;
	}

	public String getHostPath() {
		return fHostPath;
	}
	
	public void setHostPath(String path) {
		fHostPath = path;
	}

	public String getTargetPath() {
		return fTargetPath;
	}
	
	public	void setTargetPath(String path) {
		fTargetPath = path;
	}

	public boolean getEnabled() {
		return fEnabled;
	}

	public void setEnabled(boolean enabled) {
		fEnabled = enabled;
	}
}
