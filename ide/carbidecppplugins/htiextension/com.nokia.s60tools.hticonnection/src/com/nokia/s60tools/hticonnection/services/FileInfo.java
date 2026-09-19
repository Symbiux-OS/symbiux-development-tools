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

package com.nokia.s60tools.hticonnection.services;

/**
 * Contains information of file
 */
public class FileInfo {
	
	/**
	 * The name of the file.
	 */
	private String name;
	
	/**
	 * The size of the file.
	 */
	private long size;

	/**
	 * Constructor.
	 * @param name Name of the file.
	 * @param size Size of the file.
	 */
	public FileInfo(String name, long size) {
		this.setName(name);
		this.setSize(size);
	}
	
	/**
	 * Constructor.
	 */
	public FileInfo() {
	}

	/**
	 * Getter for name.
	 * @return The name of the file.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for name.
	 * @param name The name of the file.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for size.
	 * @return The size of the file.
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Setter for size.
	 * @param size The size of the file.
	 */
	public void setSize(long size) {
		this.size = size;
	}
}
