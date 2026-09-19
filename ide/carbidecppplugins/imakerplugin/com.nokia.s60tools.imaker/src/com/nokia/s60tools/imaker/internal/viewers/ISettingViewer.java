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

package com.nokia.s60tools.imaker.internal.viewers;

import com.nokia.s60tools.imaker.UIVariable;

public interface ISettingViewer {
	/**
	 * Update the view to reflect the fact that one of the variables
	 * was modified 
	 * 
	 * @param variable
	 */
	public void updateVariable(UIVariable variable);
}
