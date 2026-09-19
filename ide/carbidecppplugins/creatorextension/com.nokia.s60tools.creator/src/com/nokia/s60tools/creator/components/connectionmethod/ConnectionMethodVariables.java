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
 
package com.nokia.s60tools.creator.components.connectionmethod;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for connection method
 */
public class ConnectionMethodVariables extends AbstractVariables {
	
	//
	// Variables for XML element names
	//
	public static final String USEPROXY_XML_ELEMENT = "useproxy";
	public static final String PROMPTPASSWORD_XML_ELEMENT = "promptpassword";
	public static final String SECUREAUTHENTICATION_XML_ELEMENT = "secureauthentication";
	public static final String DISABLE_TEXT_AUTH_XML_ELEMENT = "disabletextauth";
	public static final String WAPWSOPTION_XML_ELEMENT = "wapwspoption";
	public static final String DATACALLLINESPEED_XML_ELEMENT = "datacalllinespeed";
	public static final String DATACALLTYPEISDN_XML_ELEMENT = "datacalltypeisdn";
	public static final String PROTOCOLLTYPE_XML_ELEMENT = "protocoltype";
	public static final String BEARERTYPE_XML_ELEMENT = "bearertype";
	
	/**
	 * Only instance of this class
	 */
	private static ConnectionMethodVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static ConnectionMethodVariables getInstance() {
		
		if(instance == null){
			instance = new ConnectionMethodVariables();
		}
		
		return instance;
	}	
	
	private ConnectionMethodVariables(){
		init();
		initFixedValues();		
		initMaxOccurValues();
	}
	
	//
	// Variables to show in UI
	//
	public static final String	CONNECTIONNAME	= "Connection name";
	public static final String	BEARERTYPE	= "Bearer type";
	public static final String	STARTPAGE	= "Start page";
	public static final String	WAPWSPOPTION	= "WAP WSP option";
	public static final String	PROTOCOLTYPE	= "Protocol type";
	public static final String	SECUREAUTHENTICATION	= "Secure authentication";
	public static final String	LOGINNAME	= "Login name";
	public static final String	LOGINPASS	= "Login password";
	public static final String	PROMPTPASSWORD	= "Prompt password";
	public static final String	GATEWAYADDRESS	= "Gateway address";
	public static final String	SUBNETMASK	= "Subnetmask";
	public static final String	DEVICEIPADDR	= "Device IP address";
	public static final String	IP4NAMESERVER1	= "IP v4 primary nameserver";
	public static final String	IP4NAMESERVER2	= "IP v4 secondary nameserver";
	public static final String	DATACALLTELNUMBER	= "Datacall phone number";
	public static final String	DATACALLTYPEISDN	= "Datacall ISDN type";
	public static final String	DATACALLLINESPEED	= "Datacall line speed";
	public static final String	USEPROXY	= "Use proxy";
	public static final String	PROXYSERVERADDRESS	= "Proxy server address";
	public static final String	PROXYPORTNUMBER	= "Proxy port number";
	public static final String	IP6NAMESERVER1	= "IP v6 primary nameserver";
	public static final String	IP6NAMESERVER2	= "IP v6 secondary nameserver";
	public static final String	DISABLETEXTAUTH	= "Disable plaintext authentication";
	public static final String	WLANNAME	= "SSID of WLAN connection";
	public static final String	WLANIPADDR	= "IP address of Device";
	public static final String	WLANSECMODE	= "WLAN Security mode";
	public static final String	WLANNETMODE	= "WLAN Network mode";
	
	//
	// Fixed variables for certain item in UI
	//
	public static final String ALL_BEARERTYPE_TYPES_AS_COMMA_SEPARATED_STRING [] = {"WLAN", "GPRS", "Datacall", "HSGSM", "Embedded", "VPN", "LAN"};
	public static final String ALL_PROTOCOLTYPE_TYPES_AS_COMMA_SEPARATED_STRING [] = {"IPV4", "IPV6"};
	public static final String ALL_DATACALLTYPEISDN_TYPES_AS_COMMA_SEPARATED_STRING [] = {"Analogue", "ISDNv110", "ISDNv120"};
	public static final String ALL_DATACALLLINESPEED_TYPES_AS_COMMA_SEPARATED_STRING [] = {"AUTOMATIC", "9600", "14400", "19200", "28800", "38400", "43200", "56000"};
	public static final String ALL_WAPWSPOPTION_TYPES_AS_COMMA_SEPARATED_STRING [] = {"Connectionless", "Connectionoriented"};
	
	
	
	private void init() {

		items = new LinkedHashMap<String, String>(27);
		items.put("connectionname", CONNECTIONNAME	);
		items.put(BEARERTYPE_XML_ELEMENT, BEARERTYPE	);
		items.put("startpage", STARTPAGE	);
		items.put(WAPWSOPTION_XML_ELEMENT, WAPWSPOPTION	);
		items.put(PROTOCOLLTYPE_XML_ELEMENT, PROTOCOLTYPE	);
		items.put("loginname", LOGINNAME	);
		items.put(SECUREAUTHENTICATION_XML_ELEMENT, SECUREAUTHENTICATION	);
		items.put("loginpass", LOGINPASS	);
		items.put(PROMPTPASSWORD_XML_ELEMENT, PROMPTPASSWORD	);
		items.put("gatewayaddress", GATEWAYADDRESS	);
		items.put("subnetmask", SUBNETMASK	);
		items.put("deviceipaddr", DEVICEIPADDR	);
		items.put("ip4nameserver1", IP4NAMESERVER1	);
		items.put("ip4nameserver2", IP4NAMESERVER2	);
		items.put("datacalltelnumber", DATACALLTELNUMBER	);
		items.put(DATACALLTYPEISDN_XML_ELEMENT, DATACALLTYPEISDN	);
		items.put(DATACALLLINESPEED_XML_ELEMENT, DATACALLLINESPEED	);
		items.put(USEPROXY_XML_ELEMENT, USEPROXY	);
		items.put("proxyserveraddress", PROXYSERVERADDRESS	);
		items.put("proxyportnumber", PROXYPORTNUMBER	);
		items.put("ip6nameserver1", IP6NAMESERVER1	);
		items.put("ip6nameserver2", IP6NAMESERVER2	);
		items.put(DISABLE_TEXT_AUTH_XML_ELEMENT, DISABLETEXTAUTH	);
		items.put("wlanname", WLANNAME	);
		items.put("wlanipaddr", WLANIPADDR	);
		items.put("wlansecmode", WLANSECMODE	);
		items.put("wlannetmode", WLANNETMODE	);
	}
	
	private void initFixedValues(){
		itemsValues = new LinkedHashMap<String, String[]>(4);
		itemsValues.put(BEARERTYPE_XML_ELEMENT, ALL_BEARERTYPE_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(PROTOCOLLTYPE_XML_ELEMENT, ALL_PROTOCOLTYPE_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(DATACALLTYPEISDN_XML_ELEMENT, ALL_DATACALLTYPEISDN_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(DATACALLLINESPEED_XML_ELEMENT, ALL_DATACALLLINESPEED_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(WAPWSOPTION_XML_ELEMENT, ALL_WAPWSPOPTION_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(DISABLE_TEXT_AUTH_XML_ELEMENT, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(SECUREAUTHENTICATION_XML_ELEMENT, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(PROMPTPASSWORD_XML_ELEMENT, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(USEPROXY_XML_ELEMENT, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		
	}	
	
	/**
	 * Inits Max Occur values for items, all items can occur only once.
	 */
	private void initMaxOccurValues(){
		maxOccur = new LinkedHashMap<String, Integer>(4);
		
		Integer integerOne = new Integer (1);
		
		maxOccur.put( CONNECTIONNAME	, integerOne );
		maxOccur.put( BEARERTYPE	, integerOne );
		maxOccur.put( STARTPAGE	, integerOne );
		maxOccur.put( WAPWSPOPTION	, integerOne );
		maxOccur.put( PROTOCOLTYPE	, integerOne );
		maxOccur.put( LOGINNAME	, integerOne );
		maxOccur.put( SECUREAUTHENTICATION	, integerOne );
		maxOccur.put( LOGINPASS	, integerOne );
		maxOccur.put( PROMPTPASSWORD	, integerOne );
		maxOccur.put( GATEWAYADDRESS	, integerOne );
		maxOccur.put( SUBNETMASK	, integerOne );
		maxOccur.put( DEVICEIPADDR	, integerOne );
		maxOccur.put( IP4NAMESERVER1	, integerOne );
		maxOccur.put( IP4NAMESERVER2	, integerOne );
		maxOccur.put( DATACALLTELNUMBER	, integerOne );
		maxOccur.put( DATACALLTYPEISDN	, integerOne );
		maxOccur.put( DATACALLLINESPEED	, integerOne );
		maxOccur.put( USEPROXY	, integerOne );
		maxOccur.put( PROXYSERVERADDRESS	, integerOne );
		maxOccur.put( PROXYPORTNUMBER	, integerOne );
		maxOccur.put( IP6NAMESERVER1	, integerOne );
		maxOccur.put( IP6NAMESERVER2	, integerOne );
		maxOccur.put( DISABLETEXTAUTH	, integerOne );
		maxOccur.put( WLANNAME	, integerOne );
		maxOccur.put( WLANIPADDR	, integerOne );
		maxOccur.put( WLANSECMODE	, integerOne );
		maxOccur.put( WLANNETMODE	, integerOne );		
		
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

}
