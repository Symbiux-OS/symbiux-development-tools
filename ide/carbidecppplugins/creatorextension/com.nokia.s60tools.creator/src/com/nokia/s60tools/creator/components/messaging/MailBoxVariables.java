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
 
package com.nokia.s60tools.creator.components.messaging;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for mailbox
 */
public class MailBoxVariables extends AbstractVariables {
	
	//
	// Variables to show items in UI
	//
	private static final String TO_CC_INCLUDE_LIMIT = "TO/CC include limit";
	private static final String SMTP_AUTHENTICATION = "SMTP authentication";
	private static final String REPLYTO_ADDRESS = "Replyto address";
	private static final String RECEIPT_ADDRESS = "Receipt address";
	private static final String EMAIL_ALIAS = "Email alias";
	private static final String OUTGOING_SERVER_NAME = "Outgoing server name";
	private static final String OUTGOING_PASSWORD = "Outgoing password";
	private static final String OUTGOING_LOGIN_NAME = "Outgoing login name";
	private static final String OUTGOING_PORT = "Outgoing port";
	private static final String SYNC_RATE = "Sync rate";
	private static final String MAX_EMAIL_SIZE = "Max email size";
	private static final String IMAP_IDLE_TIMEOUT = "Imap idle timeout";
	private static final String PATH_SEPARATOR = "Path separator";
	private static final String INCOMING_FOLDER_PATH = "Incoming folder path";
	private static final String ATTACHMENT_FETCH_SIZE = "Attachment fetch size";
	private static final String BODY_TEXT_SIZE_LIMIT = "Body text size limit";
	private static final String ATTACHMENT_SIZE_LIMIT = "Attachment size limit";
	private static final String INCOMING_SERVER_NAME = "Incoming server name";
	private static final String INCOMING_PASSWORD = "Incoming password";
	private static final String INCOMING_LOGINNAME = "Incoming loginname";
	private static final String INCOMING_PORT = "Incoming port";
	
	//
	// XML element names
	//	
	private static final String SEND_OPTION_XML = "sendoption";
	private static final String COPY_TO_SELF_XML = "copytoself";
	private static final String FOLDER_SYNC_TYPE_XML = "foldersynctype";
	private static final String SUBSCRIBE_TYPE_XML = "subscribetype";
	private static final String GET_EMAIL_OPTIONS_XML = "getemailoptions";
	
	//
	// Fixed variables for certain item in UI
	//
	public static final String [] POSSIBLE_VALUES_FOR_GET_EMAIL_OPTIONS = {"getheaders", "getbodytext", "getbodytextandattachments", "getattachments", "getbodyalternativetext"};
	public static final String [] POSSIBLE_VALUES_FOR_SUBSCRIBE_TYPE = {"updateneither", "updatelocal", "updateremote", "updateboth"};
	public static final String [] POSSIBLE_VALUES_FOR_FOLDER_SYNC_TYPE = {"usecombination", "uselocal", "useremote"};
	public static final String [] POSSIBLE_VALUES_FOR_COPY_TO_SELF = {"no", "to", "cc", "bcc"};
	public static final String [] POSSIBLE_VALUES_FOR_SEND_OPTION = {"immediately", "onnextconnection", "onrequest"};

	
	//
	// XML element names, not needed outside of this class
	//
	
	private static final String REQUEST_RECEIPTS_XML = "requestreceipts";
	private static final String OUTGOING_SSL_WRAPPER_XML = "outgoingsslwrapper";
	private static final String OUTGOING_SECURE_SOCKETS_XML = "outgoingsecuresockets";
	private static final String ADD_VCARD_XML = "addvcard";
	private static final String DISCONNECTED_USER_MODE_XML = "disconnectedusermode";
	private static final String ENABLE_EXPUNGE_MODE_XML = "enableexpungemode";
	private static final String MARK_SEEN_IN_SYNC_XML = "markseeninsync";
	private static final String IMAP_IDLE_COMMAND_XML = "imapidlecommand";
	private static final String DELETE_MAILS_AT_DISCONNECT_XML = "deletemailsatdisconnect";
	private static final String AUTOSEND_ON_CONNECT_XML = "autosendonconnect";
	private static final String ACKNOWLEDGE_RECEIPTS_XML = "acknowledgereceipts";
	private static final String USE_APOP_SECURE_LOGIN_XML = "useapopsecurelogin";
	private static final String INCOMING_SSL_WRAPPER_XML = "incomingsslwrapper";
	private static final String INCOMINGSECURESOCKETS_XML = "incomingsecuresockets";
	
	
	//
	// Variables to show items in UI
	//
	public static final String SEND_OPTION = "Send option";
	public static final String FOLDER_SYNC_TYPE = "Folder sync type";
	public static final String SUBSCRIBE_TYPE = "Subscribe type";
	public static final String MAILBOX_SYNC_LIMIT = "Mailbox sync limit";
	public static final String INBOX_SYNC_LIMIT = "Inbox sync limit";
	public static final String GET_EMAIL_OPTIONS = "Get email options";
	public static final String OUTGOING_CONNECTIONMETHOD_NAME = "Outgoing connectionmethod name";
	public static final String INCOMING_CONNECTIONMETHOD_NAME = "Incoming connectionmethod name";
	public static final String REQUEST_RECEIPTS = "Request receipts";
	public static final String OUTGOING_SSL_WRAPPER = "Outgoing SSL wrapper";
	public static final String OUTGOING_SECURE_SOCKETS = "Outgoing secure sockets";
	public static final String ADD_VCARD = "Add vcard";
	public static final String DISCONNECTED_USER_MODE = "Disconnected user mode";
	public static final String ENABLE_EXPUNGE_MODE = "Enable EXPUNGE mode";
	public static final String MARK_SEEN_IN_SYNC = "Mark seen in sync";
	public static final String IMAP_IDLE_COMMAND = "Imap idle command";
	public static final String DELETE_MAILS_AT_DISCONNECT = "Delete mails at disconnect";
	public static final String AUTOSEND_ON_CONNECT = "Autosend on connect";
	public static final String ACKNOWLEDGE_RECEIPTS = "Acknowledge receipts";
	public static final String INCOMING_SSL_WRAPPER = "Incoming SSL wrapper";
	public static final String INCOMING_SECURE_SOCKETS = "Incoming secure sockets";
	public static final String OWN_EMAIL = "Own email";
	public static final String COPY_TO_SELF = "Copy to self";
	public static final String USE_APOP_SECURE_LOGIN = "Use APOP secure login";
	public static final String NAME = "Name";
	public static final String CONNECTION_METHOD_NAME = "Connection method name";
	
	
		
	
	/**
	 * Only instance of this class
	 */
	private static MailBoxVariables instance;
	
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static MailBoxVariables getInstance() {
		
		if(instance == null){
			instance = new MailBoxVariables();
		}
		
		return instance;
	}	
	
	/**
	 * Private contruction
	 */
	private MailBoxVariables(){
		init();
		initFixedValues();
		initMaxOccurValues();
	}
	
	//
	//TYPES of mail box
	//
	private static final String TYPE = "Type";
	private static final String TYPE_POP3 = "POP3";
	private static final String TYPE_IMAP = "IMAP4";
	private static final String TYPE_SYNCML = "SyncML";
	
	private void init() {

		items = new LinkedHashMap<String, String>(14);
		
		items.put("name",NAME);
		items.put("ownemail",OWN_EMAIL);
		items.put(COPY_TO_SELF_XML,COPY_TO_SELF);
		items.put(USE_APOP_SECURE_LOGIN_XML,USE_APOP_SECURE_LOGIN);			
		
		items.put("incomingport", INCOMING_PORT);
		items.put(INCOMING_SSL_WRAPPER_XML, INCOMING_SSL_WRAPPER);
		items.put(INCOMINGSECURESOCKETS_XML, INCOMING_SECURE_SOCKETS);
		items.put("incomingloginname", INCOMING_LOGINNAME);
		items.put("incomingpassword", INCOMING_PASSWORD);
		items.put("incomingservername", INCOMING_SERVER_NAME);
		items.put("incomingconnectionmethod", INCOMING_CONNECTIONMETHOD_NAME);
		items.put(ACKNOWLEDGE_RECEIPTS_XML, ACKNOWLEDGE_RECEIPTS);
		items.put("attachmentsizelimit", ATTACHMENT_SIZE_LIMIT);
		items.put(AUTOSEND_ON_CONNECT_XML, AUTOSEND_ON_CONNECT);
		items.put("bodytextsizelimit", BODY_TEXT_SIZE_LIMIT);
		items.put(DELETE_MAILS_AT_DISCONNECT_XML, DELETE_MAILS_AT_DISCONNECT);
		items.put("attachmentfetchsize", ATTACHMENT_FETCH_SIZE);
		items.put("incomingfolderpath", INCOMING_FOLDER_PATH);
		items.put("pathseparator", PATH_SEPARATOR);
		items.put(GET_EMAIL_OPTIONS_XML, GET_EMAIL_OPTIONS);
		items.put(IMAP_IDLE_COMMAND_XML, IMAP_IDLE_COMMAND);
		items.put("imapidletimeout", IMAP_IDLE_TIMEOUT);
		items.put("maxemailsize", MAX_EMAIL_SIZE);
		items.put(SUBSCRIBE_TYPE_XML, SUBSCRIBE_TYPE);
		items.put("syncrate", SYNC_RATE);
		items.put(FOLDER_SYNC_TYPE_XML, FOLDER_SYNC_TYPE);
		items.put(MARK_SEEN_IN_SYNC_XML, MARK_SEEN_IN_SYNC);
		items.put(ENABLE_EXPUNGE_MODE_XML, ENABLE_EXPUNGE_MODE);
		items.put("inboxsynclimit", INBOX_SYNC_LIMIT);
		items.put("mailboxsynclimit", MAILBOX_SYNC_LIMIT);
		items.put(DISCONNECTED_USER_MODE_XML, DISCONNECTED_USER_MODE);
		items.put("outgoingport", OUTGOING_PORT);
		items.put(OUTGOING_SSL_WRAPPER_XML, OUTGOING_SSL_WRAPPER);
		items.put(OUTGOING_SECURE_SOCKETS_XML, OUTGOING_SECURE_SOCKETS);
		items.put("outgoingloginname", OUTGOING_LOGIN_NAME);
		items.put("outgoingpassword", OUTGOING_PASSWORD);
		items.put("outgoingservername", OUTGOING_SERVER_NAME);
		items.put("outgoingconnectionmethod", OUTGOING_CONNECTIONMETHOD_NAME);
		items.put(ADD_VCARD_XML, ADD_VCARD);
		items.put("emailalias", EMAIL_ALIAS);
		items.put("receiptaddress", RECEIPT_ADDRESS);
		items.put("replytoaddress", REPLYTO_ADDRESS);
		items.put(REQUEST_RECEIPTS_XML, REQUEST_RECEIPTS);
		items.put("smtpauth", SMTP_AUTHENTICATION);
		items.put(SEND_OPTION_XML, SEND_OPTION);
		items.put("toccincludelimit", TO_CC_INCLUDE_LIMIT);		
		
				
		additionalItems = new LinkedHashMap<String, String>(13);
		additionalItems.put(AbstractComponent.TYPE_PARAMETER_ID,TYPE);
	}
	
	private void initFixedValues(){
		itemsValues = new LinkedHashMap<String, String[]>(4);
		itemsValues.put(INCOMINGSECURESOCKETS_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(INCOMING_SSL_WRAPPER_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(USE_APOP_SECURE_LOGIN_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(ACKNOWLEDGE_RECEIPTS_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(AUTOSEND_ON_CONNECT_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(DELETE_MAILS_AT_DISCONNECT_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(IMAP_IDLE_COMMAND_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(MARK_SEEN_IN_SYNC_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(ENABLE_EXPUNGE_MODE_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(DISCONNECTED_USER_MODE_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(ADD_VCARD_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(OUTGOING_SECURE_SOCKETS_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(OUTGOING_SSL_WRAPPER_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);
		itemsValues.put(REQUEST_RECEIPTS_XML, YES_NO_TYPES_AS_COMMA_SEPARATED_STRING);	
		
		itemsValues.put(GET_EMAIL_OPTIONS_XML, POSSIBLE_VALUES_FOR_GET_EMAIL_OPTIONS);	
		itemsValues.put(SUBSCRIBE_TYPE_XML,  POSSIBLE_VALUES_FOR_SUBSCRIBE_TYPE);	
		itemsValues.put(FOLDER_SYNC_TYPE_XML,  POSSIBLE_VALUES_FOR_FOLDER_SYNC_TYPE);	
		itemsValues.put(COPY_TO_SELF_XML, POSSIBLE_VALUES_FOR_COPY_TO_SELF);	
		itemsValues.put(SEND_OPTION_XML, POSSIBLE_VALUES_FOR_SEND_OPTION);	
	
	}	
	
	/**
	 * Inits Max Occur valus for items
	 */
	private void initMaxOccurValues(){
		maxOccur = new LinkedHashMap<String, Integer>(4);
		
		Integer integerOne = new Integer (1);
		
		maxOccur.put(NAME, integerOne );
		maxOccur.put(OWN_EMAIL, integerOne );
		maxOccur.put(COPY_TO_SELF, integerOne );
		maxOccur.put(USE_APOP_SECURE_LOGIN, integerOne );					
		maxOccur.put(INCOMING_PORT, integerOne );
		maxOccur.put(INCOMING_SSL_WRAPPER, integerOne );
		maxOccur.put(INCOMING_SECURE_SOCKETS, integerOne );
		maxOccur.put(INCOMING_LOGINNAME, integerOne );
		maxOccur.put(INCOMING_PASSWORD, integerOne );
		maxOccur.put(INCOMING_SERVER_NAME, integerOne );
		maxOccur.put(INCOMING_CONNECTIONMETHOD_NAME, integerOne );
		maxOccur.put(ACKNOWLEDGE_RECEIPTS, integerOne );
		maxOccur.put(ATTACHMENT_SIZE_LIMIT, integerOne );
		maxOccur.put(AUTOSEND_ON_CONNECT, integerOne );
		maxOccur.put(BODY_TEXT_SIZE_LIMIT, integerOne );
		maxOccur.put(DELETE_MAILS_AT_DISCONNECT, integerOne );
		maxOccur.put(ATTACHMENT_FETCH_SIZE, integerOne );
		maxOccur.put(INCOMING_FOLDER_PATH, integerOne );
		maxOccur.put(PATH_SEPARATOR, integerOne );
		maxOccur.put(GET_EMAIL_OPTIONS, integerOne );
		maxOccur.put(IMAP_IDLE_COMMAND, integerOne );
		maxOccur.put(IMAP_IDLE_TIMEOUT, integerOne );
		maxOccur.put(MAX_EMAIL_SIZE, integerOne );
		maxOccur.put(SUBSCRIBE_TYPE, integerOne );
		maxOccur.put(SYNC_RATE, integerOne );
		maxOccur.put(FOLDER_SYNC_TYPE, integerOne );
		maxOccur.put(MARK_SEEN_IN_SYNC, integerOne );
		maxOccur.put(ENABLE_EXPUNGE_MODE, integerOne );
		maxOccur.put(INBOX_SYNC_LIMIT, integerOne );
		maxOccur.put(MAILBOX_SYNC_LIMIT, integerOne );
		maxOccur.put(DISCONNECTED_USER_MODE, integerOne );
		maxOccur.put(OUTGOING_PORT, integerOne );
		maxOccur.put(OUTGOING_SSL_WRAPPER, integerOne );
		maxOccur.put(OUTGOING_SECURE_SOCKETS, integerOne );
		maxOccur.put(OUTGOING_LOGIN_NAME, integerOne );
		maxOccur.put(OUTGOING_PASSWORD, integerOne );
		maxOccur.put(OUTGOING_SERVER_NAME, integerOne );
		maxOccur.put(OUTGOING_CONNECTIONMETHOD_NAME, integerOne );
		maxOccur.put(ADD_VCARD, integerOne );
		maxOccur.put(EMAIL_ALIAS, integerOne );
		maxOccur.put(RECEIPT_ADDRESS, integerOne );
		maxOccur.put(REPLYTO_ADDRESS, integerOne );
		maxOccur.put(REQUEST_RECEIPTS, integerOne );
		maxOccur.put(SMTP_AUTHENTICATION, integerOne );
		maxOccur.put(SEND_OPTION, integerOne );
		maxOccur.put(TO_CC_INCLUDE_LIMIT, integerOne );	
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractVariables#getInstanceImpl()
	 */
	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

	/**
	 * Get values for "Type"
	 * @return fixed values for Type
	 */
	public static String[] getTypeVariables() {
		
		return new String[]{TYPE_IMAP, TYPE_POP3, TYPE_SYNCML };
	}

}
