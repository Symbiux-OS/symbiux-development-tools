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
package com.nokia.s60tools.util.python;

import java.io.BufferedInputStream;
import java.io.File;

/**
 * Contains static methods to fetch the python installation path and to validate the installed version against the given version.
 */
public class PythonUtilities {

	private static final String PATH_VARIABLE = "PATH";
	private static final String PYTHON = "python";
	private static final String PYTHON_EXE = "python.exe";
	
	/**
	 * This method scans the PATH environment variable, to fetch the python installation path
	 * @returns installation path of python. Returns <code>null</code> if python is not installed/found. 
	 */
	public static String getPythonPath()
	{
		String pathValue = System.getenv(PATH_VARIABLE); 
		String [] values = pathValue.split(File.pathSeparator); 
			
		if(values != null)
		{
			for(String s:values){
				if(s.toLowerCase().contains(PYTHON)){
					
					if(!s.endsWith(File.separator))
						s += File.separator;
					
					return s + PYTHON_EXE; 
				}
			}
		}
		return null;
	}
	
	/**
	 * This method validates the installed python version against the given version.
	 * If the python version installed in the system, is less than the given version, 
	 * or version information fetch fails, then the method returns <code>false</code>. 
	 * Otherwise, the method returns <code>true</code>.
	 * @param pythonVersion version to be validated.
	 */
	public static boolean validatePython(String pythonVersion)
	{
		try
		{
			String pythonPath = getPythonPath();
			
			if(pythonPath != null)
			{
				String[] args = {pythonPath, "-V"}; 
				Process pr = Runtime.getRuntime().exec(args);
				BufferedInputStream br = new BufferedInputStream(pr.getErrorStream());
				pr.waitFor();
				
				StringBuffer bf = new StringBuffer(""); 
				int r = -1;
				while((r = br.read()) != -1)
				{
					char ch = (char)r;
					bf.append(ch);				
				}
		 	
				StringBuffer version=new StringBuffer(""); 
				char[] str=bf.toString().toCharArray();
		 	
				//We are interested in only the major version of Python
				//So, reading only 3 characters from the version string
				for (int i = 0; i < str.length; i++) {
					if(Character.isDigit(str[i]))
					{
						version.append(str[i]);
						version.append(str[i+1]);
						version.append(str[i+2]);
						break;
					}
				}
				
				float actualVersion = Float.parseFloat(version.toString());
				float inputVersion = Float.parseFloat(pythonVersion);
		 	
		 		if(actualVersion >= inputVersion)
		 		{
		 			return true; 
		 		}
			
			}
		}catch(Exception e){			
			e.printStackTrace();
		}
		return false;
				
	}
	
}
