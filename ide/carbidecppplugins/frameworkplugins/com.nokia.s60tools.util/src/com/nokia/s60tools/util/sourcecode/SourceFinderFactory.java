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

package com.nokia.s60tools.util.sourcecode;

import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.sourcecode.internal.MapSourceFinder;

/**
 * Factory class for creating {@link ISourceFinder} class implementations.
 * Real implementations is not allowed to use directly, 
 * but only through {@link ISourceFinder} interface created with this Factory.
 */
public class SourceFinderFactory {
	
	/**
	 * Creates an default implementation for {@link ISourceFinder}
	 * @return {@link ISourceFinder}
	 */
	public static ISourceFinder createSourceFinder(IConsolePrintUtility printUtility){
		// Current only one source file available and therefore using it as default.		
		return createMapSourceFinder(printUtility);
	}

	/**
	 * Creates an implementation for {@link ISourceFinder} using .map files.
	 * @return {@link ISourceFinder}
	 */
	public static ISourceFinder createMapSourceFinder(IConsolePrintUtility printUtility){		
		return new MapSourceFinder(printUtility);
	}

	/**
	 * Creates an default implementation for {@link ISourcesFinder}
	 * @return {@link ISourceFinder}
	 */
	public static ISourcesFinder createSourcesFinder(IConsolePrintUtility printUtility){			
		return new MapSourceFinder(printUtility);
	}	
		
}
