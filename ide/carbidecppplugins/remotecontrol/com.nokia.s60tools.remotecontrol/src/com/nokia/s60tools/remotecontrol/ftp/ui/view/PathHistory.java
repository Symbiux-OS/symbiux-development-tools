/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies). 
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

import java.util.ArrayList;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;

/**
 * Class to store and fetch path history data.
 */
public class PathHistory {

	// Name of path history -preference in preference store.
	public static String CACHE_NAME = "PathComposite.pathHistory";
	// Number of paths stored into path history.
	private static Integer PATH_HISTORY_SIZE = 10;
	// Minimum length of a path saved in path history.
	private static Integer PATH_MIN_LEN = 3;
	
	private ArrayList<IPath> pathHistory;
	private IPath previousPath;
	
	/**
	 * Add one path to history.
	 * @param path Path to be stored.
	 */
	public void addPathToHistory(IPath newPath) {
		
		// Don't add same path twice.
		if (previousPath != null && newPath.equals(previousPath)) return;
		
		// Check path minimum length.
		if (newPath.toString().length() < PATH_MIN_LEN) {
			previousPath = newPath;
			return;
		}
		
		// Does path already exist in path history?
		boolean visited = checkPathExists(newPath);
		// Was last move one segment up? ex. C:\path1 -> C:\path1\path2
		boolean oneUp = checkGoingOneSegmentUp(newPath);
		// Was last move one segment down? ex. C:\path1\path2 -> C:\path1
		boolean oneDown = checkBackingOneSegmentDown(newPath);
		// Is there two name paths on top of path history?
		boolean doubleOnTop = checkTwoFirstMatch();
	
		// Special case: if one segment down, we need to show last path in path
		// history but not add a new one. So we need to make two same paths on top
		// because first path history path is not shown in path history drop down.
		if (oneDown && !doubleOnTop) {
			makeDoubleOnTop();
		}
		
		// Related to previous if: if stopped going down, we need to remove the 
		// double from top.
		if (!oneDown && doubleOnTop) {
			removeFirst();
		}
		
		// Next there are the cases and how to act in each case.
		if (!visited && !oneUp && !oneDown) {
			addNewToTop(newPath);
		} else if (visited && !oneUp && !oneDown) {
			liftExistingToTop(newPath);
		} else if (!visited && oneUp && !oneDown) {
			changeOneOnTop(newPath);
		} else if (visited && oneUp && !oneDown) {
			removeFirst();
			liftExistingToTop(newPath);
		} else if (visited && !oneUp && oneDown) {
			removeExisting(newPath);
		}
				
		// Remove tail from path history.
		if (getPathHistory().size() > PATH_HISTORY_SIZE+2) {
			getPathHistory().removeAll(getPathHistory().subList(PATH_HISTORY_SIZE+2, getPathHistory().size()));
		}
		
		// Save path history changes.
		storePathHistory();
		previousPath = newPath;
	}
	
	/**
	 * Check path is already listed in path history.
	 * @param path Path to be checked.
	 */
	private boolean checkPathExists(IPath newPath) {
		// Check if path exists in the path history.
		for (IPath path : getPathHistory()) {
			if (path.equals(newPath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if last movement was backing toward root in path hierarchy.
	 * @param path Path to be checked.
	 */
	private boolean checkBackingOneSegmentDown(IPath newPath) {
		if (previousPath != null) {
			// If the whole currentPatch if included in newPath.
			if (previousPath.matchingFirstSegments(newPath) == newPath.segmentCount()) {
				// If newPath is one segment longer than currentPath.
				if ((previousPath.segmentCount() - newPath.segmentCount()) == 1) {
					// Return true only if moved one segment downwards in the path.
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if last movement was going toward leaves in path hierarchy.
	 * @param path Path to be checked.
	 */
	private boolean checkGoingOneSegmentUp(IPath newPath) {		
		if (pathHistory.get(0) != null) {
			// If the whole currentPatch if included in newPath.
			if (newPath.matchingFirstSegments(pathHistory.get(0)) == pathHistory.get(0).segmentCount()) {
				// If newPath is one segment longer than currentPath.
				if ((newPath.segmentCount() - pathHistory.get(0).segmentCount()) == 1) {
					// Return true only if moved one segment downwards in the path.
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if two first paths in path history are the same.
	 */
	private boolean checkTwoFirstMatch() {
		return (getPathHistory().size() > 1 && getPathHistory().get(0).equals(getPathHistory().get(1)));
	}
	
	/**
	 * Add new path to top of path history.
	 * @param newPath Path to be added.
	 */
	private void addNewToTop(IPath newPath) {
		getPathHistory().add(0, newPath);
	}
	
	/**
	 * Lift already existing path from path history to top position.
	 * @param newPath Path to be lifted.
	 */
	private void liftExistingToTop(IPath newPath) {
		getPathHistory().remove(newPath);
		getPathHistory().add(0, newPath);
	}
	
	/**
	 * Change the value of first path in path history.
	 * @param newPath Path to be added on top as a replacement.
	 */
	private void changeOneOnTop(IPath newPath) {
		getPathHistory().remove(0);
		getPathHistory().add(0, newPath);
	}
	
	/**
	 * Remove first path from path history.
	 */
	private void removeFirst() {
		getPathHistory().remove(0);
	}
	
	/**
	 * Remove already existing path from path history.
	 * @param newPath Path to be removed.
	 */
	private void removeExisting(IPath newPath) {
		getPathHistory().remove(newPath);
	}
	
	/**
	 * Copy first path of path history to top to create two same paths on top.
	 */
	private void makeDoubleOnTop() {
		getPathHistory().add(0, getPathHistory().get(0));
	}
	
	/**
	 * Return the path history for combo's dropdown.
	 * Remember: First path history path (current path) is not returned.
	 */
	public String[] getPathHistoryAsStringArray() {
		ArrayList<String> resultList = new ArrayList<String>();
		for (int i = 1; i < getPathHistory().size() && i < PATH_HISTORY_SIZE+1; i++) {
			resultList.add(getPathHistory().get(i).toString());
		}
		return resultList.toArray(new String[resultList.size()]);
	}
	
	/**
	 * Store path history to preference store. Only store first <PATH_HISTORY_SIZE>+2 paths.
	 */
	private void storePathHistory() {
		IPreferenceStore prefStore= RemoteControlActivator.getPrefsStore();
		// Form data into a string separated with commas.
		String storeString = "";
		for (int i = 0; (i < getPathHistory().size()) && (i < PATH_HISTORY_SIZE+2); i++) {
			storeString += getPathHistory().get(i).toString() + ",";
		}
		// Remove last comma.
		storeString = storeString.substring(0, storeString.length()-1);
		// Store string.
		prefStore.setValue(CACHE_NAME, storeString);
	}
	
	/**
	 * Return path history.
	 */
	private ArrayList<IPath> getPathHistory() {
		// If path history is requested for the first time.
		if (pathHistory == null) {
			IPreferenceStore prefStore= RemoteControlActivator.getPrefsStore();
			String[] pathStringArray = prefStore.getString(CACHE_NAME).split(",");
			pathHistory = new ArrayList<IPath>();
			for (String pathString : pathStringArray) {
				pathHistory.add(new Path(pathString));
			}
		}
		return pathHistory;
	}
}
