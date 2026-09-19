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
 
 
package com.nokia.s60tools.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Provides and abstract base class for sorters
 * that do sorting according some criteria which
 * can be changed during sorter object lifetime.
 * Stores sorting criteria, and in addition 
 * provides some sorting helper methods.
 * 
 * Each inheriting class is forced to
 * implement <code>compare</code> method.
 */
public abstract class S60ToolsViewerSorter extends ViewerSorter {

	/**
	 * By default there is no sort criteria set.
	 * Sort criterias should be expressed as
	 * positive integers.
	 */
	public static final int CRITERIA_NO_SORT = -1;
	
	/**
	 * Sorting criteria currently in effect.
	 */
	protected int sortCriteria = CRITERIA_NO_SORT;

	
	/**
	 * This is the radix to be given into Integer.parseInt
	 * when parsing hexadecimal numbers.
	 */
	protected static final int HEX_RADIX = 16;
	
	/**
	 * Alphabetic sort.
	 * @param str1 1st string to compare.
	 * @param str2 2nd  string to compare.
	 * @return Returns a negative integer, zero, 
	 * 	       or a positive integer as the first argument 
	 * 	       is less than, equal to, or greater 
	 *	       than the second. 
	 */
	protected int alphabeticSort(String str1, String str2){
		return str1.compareTo(str2);
	}
	
	/**
	 * Numeric sort.
	 * @param num1 1st number to compare.
	 * @param num2 2nd  number to compare.
	 * @return Returns a negative integer, zero, 
	 * 	       or a positive integer as the first argument 
	 * 	       is less than, equal to, or greater 
	 *	       than the second. 
	 */
	protected int numericSort(long num1, long num2){
		long result = (num1 - num2);
		// Trying to avoid situation where long -> int 
		// casting might fail => instead making
		// the needed comparisons instead.
		if(result > 0){
			return 1;
		}
		else if(result < 0){
			return -1;
		}
		else{
			return 0;
		}
	}

	/**
	 * Numeric sort for strings that should 
	 * fit into long integer represenation.
	 * @param numStr1 1st decimal number as string to compare.
	 * @param numStr2 2nd decimal number as string to compare.
	 * @return Returns a negative integer, zero, 
	 * 	       or a positive integer as the first argument 
	 * 	       is less than, equal to, or greater 
	 *	       than the second. 
	 */
	protected int numericSortFromDecString(String numStr1, String numStr2){

		long num1 = 0;
		long num2 = 0;

		try {
			num1 = Long.parseLong(numStr1);
		} catch (NumberFormatException e) {
			// num1 is not an integer
			// 1st argument is less than 2nd => negative
			return -1;			
		} 
		
		try {
			num2 = Long.parseLong(numStr2);
		} catch (NumberFormatException e) {
			// num2 is not an integer
            // 1st argument is greater than 2nd => positive
			return 1;
		}
		
		// Both arguments were normal numeric values
		return numericSort(num1, num2);
	}
	
	
	/**
	 * Parses hexadecimal character string prefixed with 0x
	 * into numeric representation.
	 * @param hexString Hexadecimal character string
	 * @return Corresponding long integer
	 */
	protected long parseHexString(String hexString){		
		if(hexString.startsWith("0x")){ //$NON-NLS-1$
			// Skipping "0x" characters from the start
			hexString = hexString.substring(2);
		}
		return Long.parseLong(hexString, HEX_RADIX);		
	}

	/**
	 * Numeric sort for hexadecimal strings that should 
	 * fit into long integer represenation.
	 * @param hexStr1 1st hexadecimal number as string to compare.
	 * @param hexStr2 2nd hexadecimal number as string to compare.
	 * @return Returns a negative integer, zero, 
	 * 	       or a positive integer as the first argument 
	 * 	       is less than, equal to, or greater 
	 *	       than the second. 
	 */
	protected int numericSortFromHexString(String hexStr1, String hexStr2){

		long num1 = 0;
		long num2 = 0;

		try {
			num1 = parseHexString(hexStr1);
		} catch (NumberFormatException e) {
			// num1 is not an integer
			// 1st argument is less than 2nd => negative
			return -1;			
		} 
		
		try {
			num2 = parseHexString(hexStr2);
		} catch (NumberFormatException e) {
			// num2 is not an integer
            // 1st argument is greater than 2nd => positive
			return 1;
		}
		
		// Both arguments were normal numeric values
		return numericSort(num1, num2);
	}
	
	
	/**
	 * Gets currently active sort criteria.
	 * @return Returns the sortCriteria.
	 */
	public int getSortCriteria() {
		return sortCriteria;
	}

	/**
	 * Sets currently active sort criteria.
	 * @param sortCriteria The sortCriteria to set.
	 */
	public void setSortCriteria(int sortCriteria) {
		this.sortCriteria = sortCriteria;
	}
		
	/**
	 * Comparison operation for two objects.
	 * @param viewer Reference to the viewer component.
	 * @param e1 1st object to compare.
	 * @param e2 2nd object to compare.
	 * @return Returns a negative integer, zero, 
	 * 	       or a positive integer as the first argument 
	 * 	       is less than, equal to, or greater 
	 *	       than the second. 
	 *
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	abstract public int compare(Viewer viewer, Object e1, Object e2);

}
