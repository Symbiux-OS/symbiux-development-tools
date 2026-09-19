/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies). 
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
 
package com.nokia.s60tools.creator.components.filetype;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for file
 */
public class FileTypeVariables extends AbstractVariables {
	

	/**
	 * UI text for "Encryption Type"
	 */
	public static final String ENCRYPTION_TYPE = "Encryption Type";
	
	/**
	 * XML element "right"
	 */
	public static final String RIGHT_XML = "right";
	//
	//Constants for UI, privates because of prefixes (Play, Print...)
	//
	private static final String ENDTIME = "Endtime";
	private static final String STARTTIME = "Starttime";
	private static final String ACCUMULATED = "Accumulated";
	private static final String INTERVAL = "Interval";
	private static final String COUNT = "Count";
	

	//
	//constants for XML element names
	//
	public static final String COUNT_XML = "count";
	public static final String INTERVAL_XML = "interval";
	public static final String ENDTIME_XML = "endtime";
	public static final String STARTTIME_XML = "starttime";
	public static final String ACCUMULATED_XML = "accumulated";
	public static final String ENCRYPTION_TYPE_XML = "encryption";
	public static final String TYPE_XML = "type";


	//
	//Constants for print
	//
	public static final String PRINT_TYPE = "print";
	public static final String PRINTINTERVAL = PRINT_TYPE + INTERVAL_XML;
	public static final String PRINTENDTIME =  PRINT_TYPE + ENDTIME_XML;
	public static final String PRINTSTARTTIME =  PRINT_TYPE + STARTTIME_XML;
	public static final String PRINTCOUNT =  PRINT_TYPE + COUNT_XML;
	public static final String PRINTACCUMULATED =  PRINT_TYPE + ACCUMULATED_XML;
	
	public static final String [][] PRINT_KEYS = {{
		PRINTCOUNT, PRINTINTERVAL, PRINTSTARTTIME, PRINTENDTIME, PRINTACCUMULATED
	},{
		COUNT_XML, INTERVAL_XML, STARTTIME_XML, ENDTIME_XML, ACCUMULATED_XML
	}};
	
	//
	//Constants for execute
	//	
	public static final String EXECUTE_TYPE = "execute";
	public static final String EXECUTEINTERVAL = EXECUTE_TYPE + INTERVAL_XML;
	public static final String EXECUTEENDTIME = EXECUTE_TYPE + ENDTIME_XML;
	public static final String EXECUTESTARTTIME = EXECUTE_TYPE + STARTTIME_XML;
	public static final String EXECUTECOUNT = EXECUTE_TYPE + COUNT_XML; 
	public static final String EXECUTEACCUMULATED = EXECUTE_TYPE + ACCUMULATED_XML;
	public static final String [][] EXECUTE_KEYS = {{
		EXECUTECOUNT, EXECUTEINTERVAL, EXECUTESTARTTIME, EXECUTEENDTIME, EXECUTEACCUMULATED 
	},{
		COUNT_XML, INTERVAL_XML, STARTTIME_XML, ENDTIME_XML, ACCUMULATED_XML		
	}};
	
	//
	//Constants for display
	//	
	public static final String DISPLAY_TYPE = "display";
	public static final String DISPLAYINTERVAL = DISPLAY_TYPE + INTERVAL_XML;
	public static final String DISPLAYENDTIME = DISPLAY_TYPE + ENDTIME_XML;
	public static final String DISPLAYSTARTTIME = DISPLAY_TYPE + STARTTIME_XML;
	public static final String DISPLAYCOUNT = DISPLAY_TYPE + COUNT_XML;
	public static final String DISPLAYACCUMULATED = DISPLAY_TYPE + ACCUMULATED_XML;
	public static final String [][] DISPLAY_KEYS = {{
		DISPLAYCOUNT, DISPLAYINTERVAL, DISPLAYSTARTTIME, DISPLAYENDTIME, DISPLAYACCUMULATED
	},{
		COUNT_XML, INTERVAL_XML, STARTTIME_XML, ENDTIME_XML, ACCUMULATED_XML	
	}};
	
	//
	//Constants for play
	//	
	public static final String PLAY_TYPE = "play";
	public static final String PLAYINTERVAL = PLAY_TYPE + INTERVAL_XML;
	public static final String PLAYENDTIME = PLAY_TYPE + ENDTIME_XML;
	public static final String PLAYSTARTTIME = PLAY_TYPE + STARTTIME_XML;
	public static final String PLAYCOUNT = PLAY_TYPE + COUNT_XML;
	public static final String PLAYACCUMULATED = PLAY_TYPE + ACCUMULATED_XML;
	public static final String [][] PLAY_KEYS = {{
		PLAYCOUNT, PLAYINTERVAL, PLAYSTARTTIME, PLAYENDTIME,  PLAYACCUMULATED 
	},{
		COUNT_XML, INTERVAL_XML, STARTTIME_XML, ENDTIME_XML, ACCUMULATED_XML
	}};	

	
	/**
	 * Prefix for "DRM-CD"
	 */
	public static final String DRM_CD = "DRM-CD";
	/**
	 * Prefix for "DRM-FL"
	 */
	public static final String DRM_FL = "DRM-FL";
	
	//
	// Prefixes for DRM-CD variables
	//
	private static final String DRM_CD_PRINT = DRM_CD + " Print ";
	private static final String DRM_CD_EXECUTE = DRM_CD + " Execute ";
	private static final String DRM_CD_DISPLAY = DRM_CD + " Display ";
	private static final String DRM_CD_PLAY = DRM_CD + " Play ";
	
	public static final String DURATIONS_HELP_TEXT = "For '" +ACCUMULATED + "' and '" +INTERVAL +"' -fields use format: "
		+"P[n]Y[n]M[n]DT[n]H[n]M[n]S. Where P is start point for duration, Y stands for year, M for month, D for day, " 
		+"T is time part start point, H stands for hour, M for minute and S for second. "
		+" E.g. P1Y2M3DT4H5M6S.";
	
	
	private static FileTypeVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static FileTypeVariables getInstance() {
		
		if(instance == null){
			instance = new FileTypeVariables();
		}
		
		return instance;
	}	
	
	private FileTypeVariables(){
		init();
		initFixedValues();
		initMaxOccurValues();
	}
	
	public static final String TYPE = "Type";
	public static final String DIR = "Directory";
	public static final String [] ALL_FILE_TYPES_AS_COMMA_SEPARATED_STRING = {"3GPP-70kB", "AAC-100kB", "AMR-20kB", "XLS-15kB", "GIF-2kB", "JPEG-200kB", "JPEG-25kB", "JPEG-500kB", "MIDI-10kB", "MP3-250kB", "PNG-15kB", "PPT-40kB", "RM-95kB", "RNG-1kB", "TXT-10kB", "TXT-70kB", "WAV-20kB", "DOC-20kB", "SWF-15kB", "JAD-1kB", "JAR-10kB", "TIF-25kB", "MXMF-40kB", "BMP-25kB", "JP2-65kB", "SVG-15kB", "HTML-20kB", "VCF-1kB", "VCS-1kB", "MP4-200kB", "SISX-10kB", "RAM-1kB", "WMV-200kB", "WMA-50kB"};
	public static final String [] ALL_PICTURE_FILE_TYPES_AS_COMMA_SEPARATED_STRING = {"GIF-2kB", "JPEG-200kB", "JPEG-25kB", "JPEG-500kB", "PNG-15kB", "TIF-25kB", "BMP-25kB", "JP2-65kB", "SVG-15kB"};
		
	private void init() {

		items = new LinkedHashMap<String, String>(4);
		items.put(TYPE_XML, TYPE);
		items.put("directory", DIR);

		items.put(ENCRYPTION_TYPE_XML, ENCRYPTION_TYPE);

		items.put(PLAYCOUNT, DRM_CD_PLAY + COUNT);
		items.put(PLAYSTARTTIME, DRM_CD_PLAY + STARTTIME);
		items.put(PLAYENDTIME, DRM_CD_PLAY + ENDTIME);
		items.put(PLAYINTERVAL, DRM_CD_PLAY + INTERVAL);
		items.put(PLAYACCUMULATED, DRM_CD_PLAY + ACCUMULATED);

		items.put(DISPLAYCOUNT, DRM_CD_DISPLAY + COUNT);
		items.put(DISPLAYSTARTTIME, DRM_CD_DISPLAY + STARTTIME);
		items.put(DISPLAYENDTIME, DRM_CD_DISPLAY + ENDTIME);
		items.put(DISPLAYINTERVAL, DRM_CD_DISPLAY + INTERVAL);
		items.put(DISPLAYACCUMULATED, DRM_CD_DISPLAY + ACCUMULATED);

		items.put(EXECUTECOUNT, DRM_CD_EXECUTE + COUNT);
		items.put(EXECUTESTARTTIME, DRM_CD_EXECUTE + STARTTIME);
		items.put(EXECUTEENDTIME, DRM_CD_EXECUTE + ENDTIME);
		items.put(EXECUTEINTERVAL, DRM_CD_EXECUTE + INTERVAL);
		items.put(EXECUTEACCUMULATED, DRM_CD_EXECUTE + ACCUMULATED);

		items.put(PRINTCOUNT, DRM_CD_PRINT + COUNT);
		items.put(PRINTSTARTTIME, DRM_CD_PRINT + STARTTIME);
		items.put(PRINTENDTIME, DRM_CD_PRINT + ENDTIME);
		items.put(PRINTINTERVAL, DRM_CD_PRINT + INTERVAL);
		items.put(PRINTACCUMULATED, DRM_CD_PRINT + ACCUMULATED);
		
		
		additionalItems = new LinkedHashMap<String, String>(4);
		additionalItems.put(COUNT_XML, COUNT_XML);
		additionalItems.put(INTERVAL_XML, INTERVAL_XML);
		additionalItems.put(ENDTIME_XML, ENDTIME_XML);
		additionalItems.put(STARTTIME_XML, STARTTIME_XML);
		additionalItems.put(ACCUMULATED_XML, ACCUMULATED_XML);
		additionalItems.put(RIGHT_XML, RIGHT_XML);

	}
	
	/**
	 * Inits Max Occur valus for items
	 */
	private void initMaxOccurValues(){
		maxOccur = new LinkedHashMap<String, Integer>(4);
		
		Integer integerOne = new Integer (1);
		maxOccur.put(TYPE, integerOne );//Should it be able to add more than one file? If So, also S60 side must be changed.
		maxOccur.put(DIR, integerOne );
		maxOccur.put(ENCRYPTION_TYPE, integerOne );
		
		maxOccur.put( DRM_CD_PLAY + COUNT, integerOne );
		maxOccur.put( DRM_CD_PLAY + STARTTIME, integerOne );
		maxOccur.put( DRM_CD_PLAY + ENDTIME, integerOne );
		maxOccur.put( DRM_CD_PLAY + INTERVAL, integerOne );
		maxOccur.put( DRM_CD_PLAY + ACCUMULATED, integerOne );

		maxOccur.put( DRM_CD_DISPLAY + COUNT, integerOne );
		maxOccur.put( DRM_CD_DISPLAY + STARTTIME, integerOne );
		maxOccur.put( DRM_CD_DISPLAY + ENDTIME, integerOne );
		maxOccur.put( DRM_CD_DISPLAY + INTERVAL, integerOne );
		maxOccur.put( DRM_CD_DISPLAY + ACCUMULATED, integerOne );

		maxOccur.put( DRM_CD_EXECUTE + COUNT, integerOne );
		maxOccur.put( DRM_CD_EXECUTE + STARTTIME, integerOne );
		maxOccur.put( DRM_CD_EXECUTE + ENDTIME, integerOne );
		maxOccur.put( DRM_CD_EXECUTE + INTERVAL, integerOne );
		maxOccur.put( DRM_CD_EXECUTE + ACCUMULATED, integerOne );

		maxOccur.put( DRM_CD_PRINT + COUNT, integerOne );
		maxOccur.put( DRM_CD_PRINT + STARTTIME, integerOne );
		maxOccur.put( DRM_CD_PRINT + ENDTIME, integerOne );
		maxOccur.put( DRM_CD_PRINT + INTERVAL, integerOne );
		maxOccur.put( DRM_CD_PRINT + ACCUMULATED, integerOne );		
	}
	
	/**
	 * Check if this key is a encryption value
	 * @param key (not a XML key but UI key)
	 * @return
	 */
	public static boolean isEncryptionValue(String key){
		return key.startsWith(DRM_CD);
	}
	
	private void initFixedValues(){
		itemsValues = new LinkedHashMap<String, String[]>(4);

		//For enabling creation of empty type (then directory will be created) we first add a empty slot for list
		String [] items = new String[ALL_FILE_TYPES_AS_COMMA_SEPARATED_STRING.length + 1];
		items [0] = AbstractValue.EMPTY_STRING;
		for (int i = 1; i < items.length; i++) {
			items[i] = ALL_FILE_TYPES_AS_COMMA_SEPARATED_STRING[i - 1];
		}
		
		itemsValues.put(TYPE_XML, items);
		
		
		itemsValues.put(ENCRYPTION_TYPE_XML, new String[] {DRM_FL, DRM_CD});

	}	

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

	/**
	 * Get ID used in editor to <right><[element]> 
	 * @param rightType
	 * @param elementName
	 * @return Interal ID for  <right><[element]>
	 */
	public static String getEncryptionId(String rightType, String elementName) {
		
		if(rightType.equalsIgnoreCase(DISPLAY_TYPE)){
			return getEncryptionElemnInternalId(DISPLAY_TYPE, elementName);
		}
		else if(rightType.equalsIgnoreCase(PLAY_TYPE)){
			return getEncryptionElemnInternalId(PLAY_TYPE, elementName);
		}
		else if(rightType.equalsIgnoreCase(EXECUTE_TYPE)){
			return getEncryptionElemnInternalId(EXECUTE_TYPE, elementName);
		}
		else if(rightType.equalsIgnoreCase(PRINT_TYPE)){
			return getEncryptionElemnInternalId(PRINT_TYPE, elementName);
		}
		
		return null;
	}
	
	/**
	 * Get id for encryption element used only in object model, not in XML
	 * @param type
	 * @param elemn
	 * @return id
	 */
	private static String getEncryptionElemnInternalId(String type, String elemn){
		
		
		if(elemn.equalsIgnoreCase(COUNT_XML)){
			return type + COUNT_XML;
		}
		else if(elemn.equalsIgnoreCase(INTERVAL_XML)){
			return type + INTERVAL_XML;
		}
		else if(elemn.equalsIgnoreCase(ENDTIME_XML)){
			return type + ENDTIME_XML;
		}
		else if(elemn.equalsIgnoreCase(STARTTIME_XML)){
			return type + STARTTIME_XML;
		}		
		else if(elemn.equalsIgnoreCase(ACCUMULATED_XML)){
			return type + ACCUMULATED_XML;
		}			
		else{
			return null;
		}
	
	}


	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractVariables#isModeEnabledForKey(java.lang.String)
	 */
	public boolean isModeEnabledForKey(String key) {
		if(key != null && key.equals(ENCRYPTION_TYPE)){
			return false;
		}
		else{
			return true;
		}
	}		
	
}
