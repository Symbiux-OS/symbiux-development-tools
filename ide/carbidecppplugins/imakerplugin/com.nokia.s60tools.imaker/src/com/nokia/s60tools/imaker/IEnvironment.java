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
import java.util.List;

import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;

public interface IEnvironment {

	public abstract String getDrive();

	public abstract boolean isLoaded();

	public abstract List<UIConfiguration> reLoad() throws InvocationTargetException;

	public abstract List<UIConfiguration> load() throws InvocationTargetException;

	public abstract List<UIConfiguration> getConfigurations() throws InvocationTargetException;

	public abstract UIConfiguration getConfigurationByFilePath(String filePath);

	public abstract String getIMakerCoreVersion()
			throws IMakerCoreNotFoundException, IMakerCoreExecutionException;

	/**
	 * Sets the currently selected configuration in this environment
	 * @param uic
	 */
	public abstract void setCurrentProduct(UIConfiguration uic);

	public abstract UIConfiguration getCurrentProduct();
	
	public abstract IIMakerWrapper getImakerWrapper();
	public abstract void setImakerWrapper(IIMakerWrapper wrapper);
	public abstract String getTargetSteps(String target);

	public abstract ImakerProperties getRunProperties();
}