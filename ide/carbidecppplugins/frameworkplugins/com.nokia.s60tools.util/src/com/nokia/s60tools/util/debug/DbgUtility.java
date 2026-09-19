/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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
 
 
package com.nokia.s60tools.util.debug;

import com.nokia.s60tools.util.internal.Messages;

/**
 * Simple console debug utility that can be configured
 * via property settings given as command line parameter 
 * to JVM (-Dproperty=value).
 * 
 * JVM parameter usage example:
 * 
 * <code>
 * <pre>
 *  -Dcom.nokia.s60tools.debug=true -Dcom.nokia.s60tools.debugpriority=class|operation
 * </pre>
 * </code>
 * 
 * The aforementioned JVM arguments enable debugging, and print out
 * all the debug messages with priority <code>PRIORITY_CLASS</code> 
 * and <code>PRIORITY_OPERATION</code> but omit debug prints 
 * with <code>PRIORITY_LOOP</code>.<br><br> 
 * 
 * After the following parameter is added in the end of the previous parameters
 * 
 * <code>
 * <pre> 
 *  -Dcom.nokia.s60tools.debugfilter=MyClass
 * </pre>
 * </code>
 * 
 * only those debug messages that start with string "MyClass" are printed to stdout.
 * 
 */
public class DbgUtility {
	
	/**
	 * Debug prints using this priority generate lots of information because priority
	 * should be used only when debugging happens in a tight loop.
	 */
	public static String PRIORITY_LOOP = "loop"; //$NON-NLS-1$
	/**
	 * Debug prints using this priority generate a moderate amount of information because priority 
	 * should be used only when there is need to debug operation level information.
	 */
	public static String PRIORITY_OPERATION = "operation"; //$NON-NLS-1$
	/**
	 * Debug prints using this priority generate a small amount of information because priority 
	 * should be used only when a class is initialized or deinitialized.
	 */	
	public static String PRIORITY_CLASS = "class";	 //$NON-NLS-1$

	/**
	 * Value for this property can be any OR-combination of 
	 * the priority types that are declared above.
	 * 
	 *  For example, <code>loop | operation | class</code>
	 *   
	 * The default value is <code>class</code>. 
	 */
	private static String PROPERTY_PRIORITY = "com.nokia.s60tools.debugpriority"; //$NON-NLS-1$
	
	/**
	 * Value for this property can be either <code>true</code> or <code>false</code>.
	 */
	private static String PROPERTY_DEBUG = "com.nokia.s60tools.debug"; //$NON-NLS-1$
	
	/**
	 * This property filters messages in a such way that it passes through
	 * only the mesages starting with the string defined in this property.
	 */
	private static String PROPERTY_DEBUG_FILTER = "com.nokia.s60tools.debugfilter"; //$NON-NLS-1$

	//
	// Setting up the default values.
	//
	private static String DEFAULT_DEBUG_PRIORITY = "class"; //$NON-NLS-1$
	private static String DEFAULT_DEBUG_FILTER = "";	 //$NON-NLS-1$
	private static String CURRENT_DEBUG_PRIORITY = System.getProperty(PROPERTY_PRIORITY, DEFAULT_DEBUG_PRIORITY);
	private static String CURRENT_DEBUG_FILTER = System.getProperty(PROPERTY_DEBUG_FILTER, DEFAULT_DEBUG_FILTER);		
	
	/**
	 * Prints message to stdout if it is higher or equal with the current 
	 * debug priority and passes the currently used debugging filter.
	 * @param priorityString Priority type of the message.
	 * @param msg Message string.
	 */
	public static void println(String priorityString, String msg){
		if (Boolean.getBoolean(PROPERTY_DEBUG)
			&&
			priorityMatchesWithCurrentDebugSetting(priorityString)
			&&
			passesFilter(msg)
			) {
			System.out.println(msg);
		}
	}
	
	/**
	 * Sets a new debug priority.
	 * @param priority New debug priority
	 */
	public static void setCurrentDebugPriority(String priority){
		if(
			(priority.compareToIgnoreCase(PRIORITY_CLASS)== 0)
			||
			(priority.compareToIgnoreCase(PRIORITY_LOOP)== 0)
			||
			(priority.compareToIgnoreCase(PRIORITY_OPERATION)== 0)
			){
			CURRENT_DEBUG_PRIORITY = priority;
		}
		else
		{
			throw new IllegalArgumentException(Messages.getString("DbgUtility.Invalid_Debug_Priority_RunTime_Exception_Msg")); //$NON-NLS-1$
		}
	}

	/**
	 * Debug priority filter.
	 * @param priorityString Priority of the currently handled message.
	 * @return Returns <code>true</code> if the debug message passes 
	 *                 the filter, otherwise return <code>false</code>.
	 */
	private static boolean priorityMatchesWithCurrentDebugSetting(String priorityString){
		return (CURRENT_DEBUG_PRIORITY.toLowerCase()).indexOf(priorityString) >= 0;	
	}

	/**
	 * Debug string start prefix filter.
	 * @param msg Debug message to be checked for filtering.
	 * @return Returns <code>true</code> if the debug message passes 
	 *                 the filter, otherwise return <code>false</code>.
	 */
	private static boolean passesFilter(String msg){
		if(!CURRENT_DEBUG_FILTER.equalsIgnoreCase("")){ //$NON-NLS-1$
			return msg.startsWith(CURRENT_DEBUG_FILTER);	
		}
		else{
			return true;
		}
	}

}
