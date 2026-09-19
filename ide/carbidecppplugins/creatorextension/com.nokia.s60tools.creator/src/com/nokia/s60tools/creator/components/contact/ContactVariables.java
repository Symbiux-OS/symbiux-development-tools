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

package com.nokia.s60tools.creator.components.contact;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.filetype.FileTypeVariables;

/**
 * Variables for contact
 */
public class ContactVariables extends AbstractVariables

{

	/**
	 * Constant for founding id element supports <code>incvalueforeachcopy</code>.
	 * S60 Side of Creator is implemented so if "number" or "phone" exist on element name
	 * it supports <code>incvalueforeachcopy</code>.
	 */
	private static final String NUMBER = "number";

	/**
	 * Constant for founding id element supports <code>incvalueforeachcopy</code>.
	 * S60 Side of Creator is implemented so if "number" or "phone" exist on element name
	 * it supports <code>incvalueforeachcopy</code>.
	 */
	private static final String PHONE = "phone";

	public static final String THUMBNAILID_XML_ELEMENT = "thumbnailid";

	private static ContactVariables instance;

	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static ContactVariables getInstance() {

		if (instance == null) {
			instance = new ContactVariables();
		}

		return instance;
	}

	private ContactVariables() {
		init();
		initFixedValues();
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}
	

	/**
	 * Label text for "first name" memory entry item
	 */
	public static final String QTN_PHOB_LBL_FIRST_NAME = "First name";

	/**
	 * Memory entry item label "Middle name" 
	 */
	public static final String QTN_PHOB_LBL_MIDDLE_NAME = "Middle name";

	/**
	 * Label text for "last name" memory entry item
	 */
	public static final String QTN_PHOB_LBL_LAST_NAME = "Last name";

	/**
	 * Label text for "first name reading" memory entry item, used in japanese
	 * Phonebook only (not visible in other languages)
	 */
	public static final String QTN_PHOB_LBL_FIRST_READING = "First name reading";

	/**
	 * Label text for "last name reading" memory entry item, used in japanese
	 * Phonebook only (not visible in other languages)
	 */
	public static final String QTN_PHOB_LBL_LAST_READING = "Last name reading";

	/**
	 * Label text for prefix memory entry item. l:
	 */
	public static final String QTN_PHOB_LBL_PREFIX = "Prefix";

	/**
	 * Label text for suffix memory entry item. l:
	 */
	public static final String QTN_PHOB_LBL_SUFFIX = "Suffix";

	/**
	 * Label text for "Company Name" memory entry item
	 */
	public static final String QTN_PHOB_LBL_COMPANY_NAME = "Company Name";

	/**
	 * Memory entry item label "Department" 
	 */
	public static final String QTN_PHOB_LBL_DEPT_NAME = "Department";

	/**
	 * Label text for "Job Title" memory entry item
	 */
	public static final String QTN_PHOB_LBL_JOB_TITLE = "Job Title";

	/**
	 * Label text for "Nick Name" memory entry item. l:
	 */
	public static final String QTN_SIMP_LBL_NICK = "Nick name";

	/**
	 * Memory entry item label "Assistant name"
	 */
	public static final String QTN_PHOB_LBL_ASSISTANT_NAME = "Assistant name";

	/**
	 * Memory entry item label "Spouse" 
	 */
	public static final String QTN_PHOB_LBL_SPOUSE = "Spouse";

	/**
	 * Memory entry item label "Children" 
	 */
	public static final String QTN_PHOB_LBL_CHILDREN = "Children";

	/**
	 * Label text for "Phone Number (Standard)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_NUMBER_STANDARD = "Phone Number";

	/**
	 * Label text for "Phone Number (Home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_NUMBER_HOME = "Phone Number (Home)";

	/**
	 * Label text for "Phone Number (Work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_NUMBER_WORK = "Phone Number (Work)";

	/**
	 * Label text for "Phone Number (Mobile)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_NUMBER_MOBILE = "Phone Number (Mobile)";

	/**
	 * Label text for "Mobile (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_NUMBER_MOBILE_HOME = "Mobile (home)";

	/**
	 * Label text for "Mobile (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_NUMBER_MOBILE_WORK = "Mobile (work)";

	/**
	 * Label text for "video no." memory entry item
	 */
	public static final String QTN_PHOB_LBL_VIDEO = "Video no.";

	/**
	 * Label text for "video no. (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_VIDEO_HOME = "Video no. (home)";

	/**
	 * Label text for "video no. (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_VIDEO_WORK = "Video no. (work)";

	/**
	 * Memory entry item label "Car phone"
	 */
	public static final String QTN_PHOB_LBL_CAR_TEL = "Car phone";

	/**
	 * Memory entry item label "Assistant phone"
	 */
	public static final String QTN_PHOB_LBL_NUMBER_ASSISTANT = "Assistant phone";

	/**
	 * Label text for "VOIP call" memory entry item
	 */
	public static final String QTN_PHOB_LBL_VOIP = "Tel. Internet";

	/**
	 * Label text for "VOIP call (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_VOIP_HOME = "Tel. Internet (home)";

	/**
	 * Label text for "VOIP call (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_VOIP_WORK = "Tel. Internet (work)";

	/**
	 * Label text for "push to talk" memory entry item
	 */
	public static final String QTN_PHOB_LBL_POC = "PTT";

	/**
	 * Label text for "share view" memory entry item
	 */
	public static final String QTN_PHOB_LBL_SWIS = "Share view";

	/**
	 * Label text for "SIP" memory entry item
	 */
	public static final String QTN_PHOB_LBL_SIP = "SIP";

	/**
	 * Label text for "Fax Number" memory entry item
	 */
	public static final String QTN_PHOB_LBL_FAX = "Fax";

	/**
	 * Label text for "Fax (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_FAX_HOME = "Fax (home)";

	/**
	 * Label text for "Fax (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_FAX_WORK = "Fax (work)";

	/**
	 * Label text for "Pager Number" memory entry item
	 */
	public static final String QTN_PHOB_LBL_PAGER = "Pager Number";

	/**
	 * Label text for "Email Address" memory entry item
	 */
	public static final String QTN_PHOB_LBL_EMAIL = "Email";

	/**
	 * Label text for "Email (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_EMAIL_HOME = "Email (home)";

	/**
	 * Label text for "Email (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_EMAIL_WORK = "Email (work)";

	/**
	 * Label text for "URL" memory entry item 
	 */
	public static final String QTN_PHOB_LBL_URL = "URL";

	/**
	 * Label text for "URL (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_URL_HOME = "URL (home)";

	/**
	 * Label text for "URL (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_URL_WORK = "URL (work)";

	/**
	 * Label text for "Postal Address" memory entry item
	 */
	public static final String QTN_PHOB_LBL_ADDRESS = "Postal Address";

	/**
	 * Label text for "P.O.Box" memory entry item
	 */
	public static final String QTN_PHOB_LBL_POBOX = "P.O.Box";

	/**
	 * Label text for "Extencion" memory entry item
	 */
	public static final String QTN_PHOB_LBL_EXTENCION = "Extension";

	/**
	 * Label text for "Street" memory entry item
	 */
	public static final String QTN_PHOB_LBL_STREET = "Street";

	/**
	 * Label text for "Postal code" memory entry item
	 */
	public static final String QTN_PHOB_LBL_POSTAL_CODE = "Postal code";

	/**
	 * Label text for "City" memory entry item 
	 */
	public static final String QTN_PHOB_LBL_CITY = "City";

	/**
	 * Label text for "State" memory entry item
	 */
	public static final String QTN_PHOB_LBL_STATE = "State";

	/**
	 * Label text for "Country" memory entry item
	 */
	public static final String QTN_PHOB_LBL_COUNTRY = "Country";

	/**
	 * Label text for "Address (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_ADDRESS_HOME = "Address (home)";

	/**
	 * Label text for "P.O.Box (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_POBOX_HOME = "P.O.Box (home)";

	/**
	 * Label text for "Extencion (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_EXTENCION_HOME = "Extension (home)";

	/**
	 * Label text for "Street (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_STREET_HOME = "Street (home)";

	/**
	 * Label text for "Postal code (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_POSTAL_CODE_HOME = "Postal code (home)";

	/**
	 * Label text for "City (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_CITY_HOME = "City (home)";

	/**
	 * Label text for "State (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_STATE_HOME = "State (home)";

	/**
	 * Label text for "Country (home)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_COUNTRY_HOME = "Country (home)";

	/**
	 * Label text for "Address (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_ADDRESS_WORK = "Address (work)";

	/**
	 * Label text for "P.O.Box (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_POBOX_WORK = "P.O.Box (work)";

	/**
	 * Label text for "Extencion (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_EXTENCION_WORK = "Extension (work)";

	/**
	 * Label text for "Street (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_STREET_WORK = "Street (work)";

	/**
	 * Label text for "Postal code (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_POSTAL_CODE_WORK = "Postal code (work)";

	/**
	 * Label text for "City (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_CITY_WORK = "City (work)";

	/**
	 * Label text for "State (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_STATE_WORK = "State (work)";

	/**
	 * Label text for "Country (work)" memory entry item
	 */
	public static final String QTN_PHOB_LBL_COUNTRY_WORK = "Country (work)";

	/**
	 * Label text for "DTMF String" memory entry item
	 */
	public static final String QTN_PHOB_LBL_DTMF = "DTMF";

	/**
	 * Label text for "Date" memory entry item 
	 */
	public static final String QTN_PHOB_LBL_DATE = "Birthday";

	/**
	 * Memory entry item label "Anniversary" 
	 */
	public static final String QTN_PHOB_LBL_ANNIVERSARY = "Anniversary";

	/**
	 * Label text for "Note" memory entry item
	 */
	public static final String QTN_PHOB_LBL_NOTE = "Note";

	/**
	 * Memory entry item label "Synchronization"
	 */
	public static final String QTN_PHOB_LBL_SYNCHRONIZATION = "Synchronization";

	/**
	 * Label text for "thumbnail" memory entry item
	 */
	public static final String QTN_PHOB_LBL_THUMBNAILPATH = "Thumbnail path";
	
	/**
	 * Label for Thumbnail ID, not existing value in S60 Device, only occurs in script mode.
	 */
	public static final String QTN_PHOB_LBL_THUMBNAILID = "Thumbnail ID";
	

	/**
	 * Label text for "Image" memory entry item
	 */
	public static final String QTN_PHOB_LBL_IMAGE = "Image";

	/**
	 * Label text for "Text for call" memory entry item
	 */
	public static final String QTN_PHOB_LBL_TEXT = "Text for call";

	/**
	 * Label text for "Personal Ringing Tone Indication" memory entry item
	 */
	public static final String QTN_PHOB_LBL_TONE = "Ring tone path";
	
	//
	//few items found also in VPbkEngFieldTypes.rss, but those cause Emulator to crash, so not taken into use
	//
	
	/**
	 * Label text for Location privacy indicator, found in VPbkEngFieldTypes.rss
	 */
	public static final String R_VPBK_FIELD_TYPE_LOCPRIVACY = "Location privacy indicator";
	
	/**
	 * Label text for Wv(IM) address, found in VPbkEngFieldTypes.rss
	 */	
	public static final String R_VPBK_FIELD_TYPE_WVADDRESS = "Wv(IM) address";	

	private void init() {

		items = new LinkedHashMap<String, String>(71);
		
		//First 10 items exist in same order that they appear in Device when creating new contact
		
		items.put("firstname", QTN_PHOB_LBL_FIRST_NAME);
		items.put("lastname", QTN_PHOB_LBL_LAST_NAME);
		items.put("mobilephonegen", QTN_PHOB_LBL_NUMBER_MOBILE);
		items.put("landphonegen", QTN_PHOB_LBL_NUMBER_STANDARD);
		items.put("emailgen", QTN_PHOB_LBL_EMAIL);
		items.put("videonumbergen", QTN_PHOB_LBL_VIDEO);
		items.put("voipgen", QTN_PHOB_LBL_VOIP);
		items.put("poc", QTN_PHOB_LBL_POC);
		items.put("company", QTN_PHOB_LBL_COMPANY_NAME);
		items.put("jobtitle", QTN_PHOB_LBL_JOB_TITLE);

		items.put("note", QTN_PHOB_LBL_NOTE);
		items.put("middlename", QTN_PHOB_LBL_MIDDLE_NAME);
		items.put("secondname", QTN_SIMP_LBL_NICK);
		

		items.put("prefix", QTN_PHOB_LBL_PREFIX);
		items.put("suffix", QTN_PHOB_LBL_SUFFIX);

		items.put("addrlabelgen", QTN_PHOB_LBL_ADDRESS);
		items.put("addrpogen", QTN_PHOB_LBL_POBOX);
		items.put("addrextgen", QTN_PHOB_LBL_EXTENCION);
		items.put("addrstreetgen", QTN_PHOB_LBL_STREET);
		items.put("addrlocalgen", QTN_PHOB_LBL_CITY);
		items.put("addrregiongen", QTN_PHOB_LBL_STATE);
		items.put("addrpostcodegen", QTN_PHOB_LBL_POSTAL_CODE);
		items.put("addrcountrygen", QTN_PHOB_LBL_COUNTRY);

		items.put("addrlabelhome", QTN_PHOB_LBL_ADDRESS_HOME);
		items.put("addrpohome", QTN_PHOB_LBL_POBOX_HOME);
		items.put("addrexthome", QTN_PHOB_LBL_EXTENCION_HOME);
		items.put("addrstreethome", QTN_PHOB_LBL_STREET_HOME);
		items.put("addrlocalhome", QTN_PHOB_LBL_CITY_HOME);
		items.put("addrregionhome", QTN_PHOB_LBL_STATE_HOME);
		items.put("addrpostcodehome", QTN_PHOB_LBL_POSTAL_CODE_HOME);
		items.put("addrcountryhome", QTN_PHOB_LBL_COUNTRY_HOME);

		items.put("addrlabelwork", QTN_PHOB_LBL_ADDRESS_WORK);
		items.put("addrpowork", QTN_PHOB_LBL_POBOX_WORK);
		items.put("addrextwork", QTN_PHOB_LBL_EXTENCION_WORK);
		items.put("addrstreetwork", QTN_PHOB_LBL_STREET_WORK);
		items.put("addrlocalwork", QTN_PHOB_LBL_CITY_WORK);
		items.put("addrregionwork", QTN_PHOB_LBL_STATE_WORK);
		items.put("addrpostcodework", QTN_PHOB_LBL_POSTAL_CODE_WORK);
		items.put("addrcountrywork", QTN_PHOB_LBL_COUNTRY_WORK);

		items.put("swis", QTN_PHOB_LBL_SWIS);
		items.put("sip", QTN_PHOB_LBL_SIP);
		items.put("dtmfstring", QTN_PHOB_LBL_DTMF);
		items.put("department", QTN_PHOB_LBL_DEPT_NAME);
		items.put("asstname", QTN_PHOB_LBL_ASSISTANT_NAME);
		items.put("spouse", QTN_PHOB_LBL_SPOUSE);
		items.put("children", QTN_PHOB_LBL_CHILDREN);
		items.put("anniversary", QTN_PHOB_LBL_ANNIVERSARY);
		items.put("synchronization", QTN_PHOB_LBL_SYNCHRONIZATION);
		items.put("thumbnailpath", QTN_PHOB_LBL_THUMBNAILPATH);
		items.put(THUMBNAILID_XML_ELEMENT, QTN_PHOB_LBL_THUMBNAILID);
		items.put("callerobjtext",QTN_PHOB_LBL_TEXT);
		items.put("ringtonepath",QTN_PHOB_LBL_TONE);
		items.put("callerobjimg",QTN_PHOB_LBL_IMAGE);
		items.put("birthday",QTN_PHOB_LBL_DATE);

		items.put("landphonehome", QTN_PHOB_LBL_NUMBER_HOME);
		items.put("landphonework", QTN_PHOB_LBL_NUMBER_WORK);

		items.put("mobilephonehome", QTN_PHOB_LBL_NUMBER_MOBILE_HOME);
		items.put("mobilephonework", QTN_PHOB_LBL_NUMBER_MOBILE_WORK);

		items.put("faxnumbergen", QTN_PHOB_LBL_FAX);
		items.put("faxnumberhome", QTN_PHOB_LBL_FAX_HOME);
		items.put("faxnumberwork", QTN_PHOB_LBL_FAX_WORK);
		items.put("pagernumber", QTN_PHOB_LBL_PAGER);

		items.put("videonumberhome", QTN_PHOB_LBL_VIDEO_HOME);
		items.put("videonumberwork", QTN_PHOB_LBL_VIDEO_WORK);

		items.put("voiphome", QTN_PHOB_LBL_VOIP_HOME);
		items.put("voipwork", QTN_PHOB_LBL_VOIP_WORK);

		items.put("asstphone", QTN_PHOB_LBL_NUMBER_ASSISTANT);
		items.put("carphone", QTN_PHOB_LBL_CAR_TEL);

		items.put("urlgen", QTN_PHOB_LBL_URL);
		items.put("urlhome", QTN_PHOB_LBL_URL_HOME);
		items.put("urlwork", QTN_PHOB_LBL_URL_WORK);

		items.put("emailhome", QTN_PHOB_LBL_EMAIL_HOME);
		items.put("emailwork", QTN_PHOB_LBL_EMAIL_WORK);

		
		//Following items not found in devices and causes emulator crash, can be taken into use if wanted
		//items found in VPbkEngFieldTypes.rss
		items.put("locprivacy",R_VPBK_FIELD_TYPE_LOCPRIVACY);//r_vpbk_field_type_locprivacy (Location privacy indicator)
		items.put("wvaddress", R_VPBK_FIELD_TYPE_WVADDRESS);//R_VPBK_FIELD_TYPE_WVADDRESS (Wv(IM) address)
	
	}
	
	private void initFixedValues(){
		itemsValues = new LinkedHashMap<String, String[]>(4);
		itemsValues.put(THUMBNAILID_XML_ELEMENT, FileTypeVariables.ALL_PICTURE_FILE_TYPES_AS_COMMA_SEPARATED_STRING);		
	}

	/**
	 * Check if <code>incvalueforeachcopy</code> is supported for type.
	 * @param type as in UI, not as in XML.
	 * @return <code>true</code> if <code>incvalueforeachcopy</code> is supported.
	 */
	public boolean isTypeSupportingIncValueForEachCopy(String type) {
		
		//phone number
		String id = getIdByValue(type);
		if(id == null){
			return false;
		}
		
		return id.contains(PHONE)  || id.contains(NUMBER);
	}

}
