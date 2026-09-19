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

package com.nokia.s60tools.hticonnection.core;

import java.util.List;

import com.nokia.HTI.HTIMessage;
import com.nokia.HTI.ApplicationControlService.AppStatus;
import com.nokia.s60tools.hticonnection.services.DriveInfo;
import com.nokia.s60tools.hticonnection.services.FileInfo;
import com.nokia.s60tools.hticonnection.services.HTIScreenMode;
import com.nokia.s60tools.hticonnection.services.HTIVersion;

/**
 * Class to contain result of request. 
 */
public class RequestResult {

	// Data for the request result.
	private byte[] byteData = null;
	private boolean booleanData;
	private String[] stringArrayData = null;
	private String stringData = null;
	private HTIMessage htiMessage = null;
	private List<String> listData = null;
	private AppStatus appStatus = null;
	private HTIScreenMode screenMode = null;
	private DriveInfo[] driveInfoData = null;
	private HTIVersion versionData = null;
	private FileInfo[] fileInfoData = null;
	
	//
	// Constructors.
	//

	/**
	 * Constructor for byte data.
	 * @param byteData Data as bytes.
	 */
	public RequestResult(byte[] byteData){
		this.byteData = byteData;
	}

	/**
	 * Constructor for boolean data.
	 * @param booleanData Boolean value..
	 */
	public RequestResult(boolean booleanData){
		this.booleanData = booleanData;
	}
	
	/**
	 * Constructor for String array data.
	 * @param stringData Data as String array.
	 */
	public RequestResult(String[] stringData){
		this.stringArrayData = stringData;
	}
	
	/**
	 * Constructor for String data.
	 * @param stringData Data as String.
	 */
	public RequestResult(String stringData){
		this.stringData = stringData;
	}
	
	/**
	 * Constructor for HTIMessage.
	 * @param htiMessage Result as HTIMessage.
	 */
	public RequestResult(HTIMessage htiMessage){
		this.htiMessage = htiMessage;
	}
	
	/**
	 * Constructor for List.
	 * @param listData Result as List.
	 */
	public RequestResult(List<String> listData){
		this.listData = listData;
	}
	
	/**
	 * Constructor for AppStatus.
	 * @param appStatus Application status.
	 */
	public RequestResult(AppStatus appStatus){
		this.appStatus = appStatus;
	}

	/**
	 * Constructor for screen mode.
	 * @param screen mode Result as HTIScreenMode.
	 */
	public RequestResult(HTIScreenMode screenMode){
		this.screenMode = screenMode;
	}
	
	/**
	 * Constructor for DriveInfo array data.
	 * @param driveInfoData Data as DriveInfo array.
	 */
	public RequestResult(DriveInfo[] driveInfoData){
		this.driveInfoData = driveInfoData;
	}
	
	/**
	 * Constructor for version data.
	 * @param versionData Data as HTIVersion.
	 */
	public RequestResult(HTIVersion versionData){
		this.versionData = versionData;
	}

	/**
	 * Constructor for FileInfo array data.
	 * @param versionData Data as FileInfo array.
	 */
	public RequestResult(FileInfo[] fileInfoData){
		this.fileInfoData = fileInfoData;
	}
	
	//
	// Getters.
	//
	
	/**
	 * Method to get byte data result.
	 * @return Data as bytes or null, if there is no byte data.
	 */
	public byte[] getByteData(){
		return byteData ;
	}
	
	/**
	 * Method to get boolean data result.
	 * @return Datavalue.
	 */
	public boolean getBooleanData(){
		return booleanData ;
	}
	
	/**
	 * Method to get String array data result.
	 * @return Data as String array or null, if there is no String array data.
	 */
	public String[] getStringArrayData(){
		return stringArrayData ;
	}
	
	/**
	 * Method to get String data result.
	 * @return Data as String or null, if there is no String data.
	 */
	public String getStringData(){
		return stringData ;
	}
	
	/**
	 * Method to get HTIMessage result.
	 * @return Data as HTIMessage or null, if there is no result.
	 */
	public HTIMessage getHTIMessage(){
		return htiMessage ;
	}
	
	/**
	 * Method to get List result.
	 * @return Data as List or null, if there is no result.
	 */
	public List<String> getListData(){
		return listData ;
	}
	
	/**
	 * Method to get AppStatus result.
	 * @return Data as AppStatus or null, if there is no result.
	 */
	public AppStatus getAppStatus(){
		return appStatus ;
	}

	/**
	 * Method to get screen mode result.
	 * @return Data as HTIScreenMode or null, if there is no result.
	 */
	public HTIScreenMode getScreenMode() {
		return screenMode;
	}
	
	/**
	 * Method to get DriveInfo array data result.
	 * @return Data as DriveInfo array or null, if there is no DriveInfo array data.
	 */
	public DriveInfo[] getDriveInfoArrayData(){
		return driveInfoData ;
	}
	
	/**
	 * Method to get HTIVersion data result.
	 * @return Data as HTIVersion or null, if there is no HTIVersion data.
	 */
	public HTIVersion getHTIVersionData(){
		return versionData ;
	}
	
	/**
	 * Method to get FileInfo array data result.
	 * @return Data as FileInfo array or null, if there is no FileInfo array data.
	 */
	public FileInfo[] getFileInfoArrayData() {
		return fileInfoData;
	}
}
