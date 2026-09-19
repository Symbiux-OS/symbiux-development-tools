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

package com.nokia.s60tools.imaker.internal.wrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlatsimManager {
	private static final String DEFAULT_LOCATION = "rd_sw\\platsim";
	private static final String INSTANCES_FOLDER = "instances";
	private String epocroot = null;
	public PlatsimManager(String epocroot) {
		this.epocroot = epocroot;
	}
	
	public String getDefaulfLocation() {
		return epocroot + DEFAULT_LOCATION;
	}
	public List<String> getInstances() {
		List<String> ins = new ArrayList<String>();
		File f = new File(epocroot+DEFAULT_LOCATION+"\\"+INSTANCES_FOLDER);
		File[] files = f.listFiles();
		if(files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if(file.isDirectory()) {
					File ini = new File(file.getAbsolutePath()+"\\platsim.ini");
					if(ini.exists()) {
						ins.add(file.getName());
					}
				}
			}			
		}
		return ins;
	}
}
