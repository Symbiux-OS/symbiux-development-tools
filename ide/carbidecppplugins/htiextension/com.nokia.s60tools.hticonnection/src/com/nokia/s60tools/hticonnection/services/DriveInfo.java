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
 * Contains information of disk drive
 */
public class DriveInfo {
	
	
	/**
	 * The root path of the drive
	 */
	private String rootPath;
	
	/**
	 * The name of the drive
	 */
	private String name;
	
	/**
	 * The type of media
	 */
	private MediaType type;
	
	/**
	 * Unique identifier number of the drive
	 */
	private long uid;
	
	/**
	 * The total size of the drive in bytes
	 */
	private long size;
	
	/**
	 * Free space on the drive in bytes
	 */
	private long freeSpace;
	
	/**
	 * Media type of drive
	 */
	public enum MediaType {
		NOT_PRESET,
		UNKNOWN,
		FLOPPY,
		HARD_DISK,
		CDROM,
		RAM,
		FLASH,
		ROM,
		REMOTE,
		NAND_FLASH,
		ROTATING_MEDIA
	};
	
	/**
	 * Constructor
	 */
	public DriveInfo() {
		
	}
	
	
	/**
	 * Constructor
	 * @param rootPath The root path of the drive
	 * @param name The name of the drive
	 * @param type The type of media
	 * @param uid Unique identifier number of the drive
	 * @param size The total size of the drive in bytes
	 * @param freeSpace Free space on the drive in bytes
	 */
	public DriveInfo(String rootPath, String name, MediaType type, long uid, long size, long freeSpace) {
		this.rootPath = rootPath; 
		this.name = name; 
		this.type = type; 
		this.uid = uid; 
		this.size = size; 
		this.freeSpace = freeSpace;
	}
	
	/**
	 * Get root path of the drive
	 * @return The root path of the drive
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * Set root path of the drive
	 * @param rootPath The root path of the drive
	 */
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	/**
	 * Get the name of the drive
	 * @return The name of the drive
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the drive
	 * @param name The name of the drive
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the type of media
	 * @return The type of media
	 */
	public MediaType getType() {
		return type;
	}

	/**
	 * Set the type of media
	 * @param type The type of media
	 */
	public void setType(MediaType type) {
		this.type = type;
	}

	/**
	 * Get unique identifier number of the drive
	 * @return Unique identifier number of the drive
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * Set unique identifier number of the drive
	 * @param uid Unique identifier number of the drive
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * Get the total size of the drive in bytes
	 * @return The total size of the drive in bytes
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Set the total size of the drive in bytes
	 * @param size The total size of the drive in bytes
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * Get free space on the drive in bytes
	 * @return Free space on the drive in bytes
	 */
	public long getFreeSpace() {
		return freeSpace;
	}

	/**
	 * Set free space on the drive in bytes
	 * @param freeSpace Free space on the drive in bytes
	 */
	public void setFreeSpace(long freeSpace) {
		this.freeSpace = freeSpace;
	}

}
