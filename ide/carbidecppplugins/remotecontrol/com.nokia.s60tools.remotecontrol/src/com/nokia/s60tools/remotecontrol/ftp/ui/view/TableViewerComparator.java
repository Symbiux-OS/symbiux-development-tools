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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.nokia.s60tools.hticonnection.services.DriveInfo;

/**
 * Comparator that is used to sort rows in File Transfer view.
 */
public class TableViewerComparator extends ViewerComparator {

	/**
	 * Columns used in File Transfer view.
	 */
	public enum Columns {
		NAME,
		SIZE,
		FREE
	}

	/**
	 * Column that is used to sort data.
	 */
	private Columns sortColumn;
	/**
	 * Direction to which rows are sorted. Value should be either SWT.UP or SWT.DOWN.
	 */
	private final int sortDirection;

	// Results result.
	private final int COMPARE_FIRST_BIGGER = 1;
	private final int COMPARE_FIRST_SMALLER = -1;
	private final int COMPARE_EQUALS = 0;
	
	
	/**
	 * Constructor.
	 * @param sortColumn Column that is used to sort data.
	 * @param sortDirection Direction to which rows are sorted. Value should be SWT.UP or SWT.DOWN.
	 */
	public TableViewerComparator(Columns sortColumn, int sortDirection) {
		this.sortColumn = sortColumn;
		this.sortDirection = sortDirection;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		switch (sortColumn) {
		case NAME:
			return compareNames(e1, e2);
		case SIZE:
			return compareSizes(e1, e2);
		case FREE:
			return compareFreeSpace(e1, e2);
		default:
			return COMPARE_EQUALS;
		}
	}

	/**
	 * Compares objects in free space column.
	 * @param e1 First element to be compared.
	 * @param e2 Second element to be compared.
	 * @return Negative number if first object should be before second object. Positive number if
	 * first object should be after second object. Zero if order doesn't matter.
	 */
	private int compareFreeSpace(Object e1, Object e2) {
		// Checking for uplink object.
		if(e1 instanceof FtpUplinkObject) {
			return COMPARE_FIRST_SMALLER;
		}
		if(e2 instanceof FtpUplinkObject) {
			return COMPARE_FIRST_BIGGER;
		}
		
		// Comparing free space for drives.
		if(e1 instanceof FtpDriveObject && e2 instanceof FtpDriveObject) {
			DriveInfo driveInfo1 = ((FtpDriveObject)e1).getDriveInfo();
			DriveInfo driveInfo2 = ((FtpDriveObject)e2).getDriveInfo();
			if(driveInfo1 != null && driveInfo2 != null) {
				long size1 = driveInfo1.getFreeSpace();
				long size2 = driveInfo2.getFreeSpace();
				
				return compareValues(size1, size2);
			}
		}
		
		return COMPARE_EQUALS;
	}

	/**
	 * Compares objects in size column.
	 * @param e1 First element to be compared.
	 * @param e2 Second element to be compared.
	 * @return Negative number if first object should be before second object. Positive number if
	 * first object should be after second object. Zero if order doesn't matter.
	 */
	private int compareSizes(Object e1, Object e2) {
		// Checking for uplink object.
		if(e1 instanceof FtpUplinkObject) {
			return COMPARE_FIRST_SMALLER;
		}
		if(e2 instanceof FtpUplinkObject) {
			return COMPARE_FIRST_BIGGER;
		}
		
		// Comparing size of drives.
		if(e1 instanceof FtpDriveObject && e2 instanceof FtpDriveObject) {
			DriveInfo driveInfo1 = ((FtpDriveObject)e1).getDriveInfo();
			DriveInfo driveInfo2 = ((FtpDriveObject)e2).getDriveInfo();
			if(driveInfo1 != null && driveInfo2 != null) {
				long size1 = driveInfo1.getSize();
				long size2 = driveInfo2.getSize();
				
				return compareValues(size1, size2);
			}
		}
		
		// Comparing size of files.
		if(e1 instanceof FtpFileObject && e2 instanceof FtpFileObject) {
			long size1 = ((FtpFileObject)e1).getSize();
			long size2 = ((FtpFileObject)e2).getSize();
			
			return compareValues(size1, size2);
		}
		
		// Checking if objects are file and folder.
		if(e1 instanceof FtpFolderObject && e2 instanceof FtpFileObject) {
			return (sortDirection == SWT.UP) ? COMPARE_FIRST_SMALLER : COMPARE_FIRST_BIGGER;
		}
		if(e1 instanceof FtpFileObject && e2 instanceof FtpFolderObject) {
			return (sortDirection == SWT.UP) ? COMPARE_FIRST_BIGGER : COMPARE_FIRST_SMALLER;
		}
		
		// Checking for folders.
		if(e1 instanceof FtpFolderObject && e2 instanceof FtpFolderObject) {
			return compareNames(e1, e2);
		}
		
		return COMPARE_EQUALS;
	}

	/**
	 * Compares two long numbers.
	 * @param e1 First number to be compared.
	 * @param e2 Second number to be compared.
	 * @return Negative number if first number should be before second number. Positive number if
	 * first number should be after second number. Zero if order doesn't matter.
	 */
	private int compareValues(long size1, long size2) {
		if(size1 == size2) {
			return COMPARE_EQUALS;
		}
		
		if(size1 > size2) {
			return (sortDirection == SWT.UP) ? COMPARE_FIRST_BIGGER : COMPARE_FIRST_SMALLER;
		} else {
			return (sortDirection == SWT.UP) ? COMPARE_FIRST_SMALLER : COMPARE_FIRST_BIGGER;
		}
	}

	/**
	 * Compares objects in name column.
	 * @param e1 First element to be compared.
	 * @param e2 Second element to be compared.
	 * @return Negative number if first object should be before second object. Positive number if
	 * first object should be after second object. Zero if order doesn't matter.
	 */
	private int compareNames(Object e1, Object e2) {
		// Checking for uplink object.
		if(e1 instanceof FtpUplinkObject) {
			return COMPARE_FIRST_SMALLER;
		}
		if(e2 instanceof FtpUplinkObject) {
			return COMPARE_FIRST_BIGGER;
		}
		
		// Checking if objects are file and folder.
		if(e1 instanceof FtpFolderObject && e2 instanceof FtpFileObject) {
			return (sortDirection == SWT.UP) ? COMPARE_FIRST_SMALLER : COMPARE_FIRST_BIGGER;
		}
		if(e1 instanceof FtpFileObject && e2 instanceof FtpFolderObject) {
			return (sortDirection == SWT.UP) ? COMPARE_FIRST_BIGGER : COMPARE_FIRST_SMALLER;
		}
		
		// Comparing names.
		if(e1 instanceof FtpNamedObject && e2 instanceof FtpNamedObject) {
			String name1 = ((FtpNamedObject)e1).getName();
			String name2 = ((FtpNamedObject)e2).getName();
			return (sortDirection == SWT.UP) ? name1.compareToIgnoreCase(name2)
											: name2.compareToIgnoreCase(name1);
		}
		
		return COMPARE_EQUALS;
	}
	
}
