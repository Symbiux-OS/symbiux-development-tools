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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * This class represents a selection of objects 
 * that are part of some UI component that implements
 * <code>org.eclipse.jface.viewers.ISelectionProvider</code> interface.
 * 
 * This class can be used to make programmatic selection,
 * for example, in the following way:
 * 
 * <code>
 * <pre>
 * // Some component supporting selections
 * ISelectionProvider selProvider = (ISelectionProvider) ...;
 * // In this example we select a single object
 * ConcreteObjectToSelect objToSelect = ...;
 * // Creating selection
 * ProgrammaticSelection newSelection;
 *                        = new ProgrammaticSelection(
 *                                          new Object[]{
 *                                          objToSelect
 *                                                            }
 *                                                   );
 * // Setting selection
 * selProvider.setSelection(newSelection);                                         
 *
 * </pre>
 * </code>
 * 
 * NOTE: Some selection providers like subclasses of <code>org.eclipse.jface.viewers.Viewer</code>
 * have a slightly different setSelection method.
 * 
 * @see org.eclipse.jface.viewers.ISelectionProvider
 * @see org.eclipse.jface.viewers.Viewer
 */
public class ProgrammaticSelection implements IStructuredSelection {

	/**
	 * Object list to include into selection.
	 */
	ArrayList<Object> objectList = null;
	
	/**
	 * Constructor
	 * @param items Objects to include into selection.
	 */
	public ProgrammaticSelection(Object[] items){

		// Node array cannot be empty one	
		if( ! (items.length > 0) ){
			throw new IllegalArgumentException();
		}
		
		objectList = new ArrayList<Object>();
		
		for (int i = 0; i < items.length; i++) {
			objectList.add(items[i]);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredSelection#getFirstElement()
	 */
	public Object getFirstElement() {
		return objectList.get(0);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredSelection#iterator()
	 */
	public Iterator<Object> iterator() {
		return objectList.iterator();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredSelection#size()
	 */
	public int size() {
		return objectList.size();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredSelection#toArray()
	 */
	public Object[] toArray() {
		return objectList.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredSelection#toList()
	 */
	public List<Object> toList() {
		return objectList;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelection#isEmpty()
	 */
	public boolean isEmpty() {
		return false;
	}

}
