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
 
package com.nokia.s60tools.creator.components;

import com.nokia.s60tools.creator.components.bookmark.Bookmark;
import com.nokia.s60tools.creator.components.bookmark.BookmarkFolder;
import com.nokia.s60tools.creator.components.bookmark.BookmarkFolderValue;
import com.nokia.s60tools.creator.components.bookmark.BookmarkValue;
import com.nokia.s60tools.creator.components.calendar.Calendar;
import com.nokia.s60tools.creator.components.calendar.CalendarValue;
import com.nokia.s60tools.creator.components.connectionmethod.ConnectionMethod;
import com.nokia.s60tools.creator.components.connectionmethod.ConnectionMethodValue;
import com.nokia.s60tools.creator.components.contact.Contact;
import com.nokia.s60tools.creator.components.contact.ContactGroup;
import com.nokia.s60tools.creator.components.contact.ContactGroupValue;
import com.nokia.s60tools.creator.components.contact.ContactSet;
import com.nokia.s60tools.creator.components.contact.ContactSetValue;
import com.nokia.s60tools.creator.components.contact.ContactValue;
import com.nokia.s60tools.creator.components.filetype.FileType;
import com.nokia.s60tools.creator.components.filetype.FileTypeValue;
import com.nokia.s60tools.creator.components.impsserver.IMPSServer;
import com.nokia.s60tools.creator.components.impsserver.IMPSServerValue;
import com.nokia.s60tools.creator.components.landmark.Landmark;
import com.nokia.s60tools.creator.components.landmark.LandmarkValue;
import com.nokia.s60tools.creator.components.log.Log;
import com.nokia.s60tools.creator.components.log.LogValue;
import com.nokia.s60tools.creator.components.messaging.MailBox;
import com.nokia.s60tools.creator.components.messaging.MailBoxValue;
import com.nokia.s60tools.creator.components.messaging.Message;
import com.nokia.s60tools.creator.components.messaging.MessageValue;
import com.nokia.s60tools.creator.components.note.Note;
import com.nokia.s60tools.creator.components.note.NoteValue;
import com.nokia.s60tools.creator.components.savedpage.SavedPage;
import com.nokia.s60tools.creator.components.savedpage.SavedPageFolder;
import com.nokia.s60tools.creator.components.savedpage.SavedPageFolderValue;
import com.nokia.s60tools.creator.components.savedpage.SavedPageValue;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * Helper class to get a real implementing class of {@link AbstractComponent} 
 * and {@link AbstractValue} -classes.
 */
public class ComponentServices {
	
	/**
	 * Get a component by String in XML file (<xs:complexType>)
	 * @param componentType
	 * @return a Component, null if not found or elementName was null,
	 * a UnknownCompoment if component was unknown 
	 */
	public static AbstractComponent getComponentByXMLElementName(String componentType){
		
		if(componentType == null){
			return null;
		}
		
		if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_XML_ELEMENT)){
			return new Contact(AbstractComponent.NULL_ID);
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_NOTE_XML_ELEMENT)){
			return new Note(AbstractComponent.NULL_ID);
		}	
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_BOOKMARK_XML_ELEMENT)){
			return new Bookmark(AbstractComponent.NULL_ID);
		}			
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_BOOKMARK_FOLDER_XML_ELEMENT)){
			return new BookmarkFolder(AbstractComponent.NULL_ID);
		}			
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_SAVED_PAGE_XML_ELEMENT)){
			return new SavedPage(AbstractComponent.NULL_ID);
		}			
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_SAVED_PAGE_FOLDER_XML_ELEMENT)){
			return new SavedPageFolder(AbstractComponent.NULL_ID);
		}	
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_LOG_XML_ELEMENT)){
			return new Log(AbstractComponent.NULL_ID);
		}	
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_IMPS_SERVER_XML_ELEMENT)){
			return new IMPSServer(AbstractComponent.NULL_ID);
		}		
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONNECTION_METHOD_XML_ELEMENT)){
			return new ConnectionMethod(AbstractComponent.NULL_ID);
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CALENDAR_XML_ELEMENT)){
			return new Calendar(AbstractComponent.NULL_ID);
		}			
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_MAIL_BOX_XML_ELEMENT)){
			return new MailBox(AbstractComponent.NULL_ID);
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_MESSAGE_XML_ELEMENT)){
			return new Message(AbstractComponent.NULL_ID);
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_SET_XML_ELEMENT)){
			return new ContactSet(AbstractComponent.NULL_ID);
		}		
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_GROUP_XML_ELEMENT)){
			return new ContactGroup(AbstractComponent.NULL_ID);
		}		
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_LANDMARK_XML_ELEMENT)){
			return new Landmark(AbstractComponent.NULL_ID);
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_FILE_XML_ELEMENT)){
			return new FileType(AbstractComponent.NULL_ID);
		}			
		else{ 
			return new UnknownCompoment(AbstractComponent.NULL_ID);
		}
		
	}
	
	/**
	 * Get a component by String in XML file (<xs:complexType>)
	 * @param componentType
	 * @return a Component, null if not found or elementName was null,
	 * a UnknownValue if component was unknown
	 */
	public static AbstractValue getValueByXMLElementName(String componentType){
		
		
		if(componentType == null){
			return null;
		}
		
		if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_XML_ELEMENT)){
			return new ContactValue();
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_NOTE_XML_ELEMENT)){
			return new NoteValue();
		}		
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_BOOKMARK_XML_ELEMENT)){
			return new BookmarkValue();
		}		
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_BOOKMARK_FOLDER_XML_ELEMENT)){
			return new BookmarkFolderValue();
		}	
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_SAVED_PAGE_XML_ELEMENT)){
			return new SavedPageValue();
		}		
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_SAVED_PAGE_FOLDER_XML_ELEMENT)){
			return new SavedPageFolderValue();
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_LOG_XML_ELEMENT)){
			return new LogValue();
		}		
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_IMPS_SERVER_XML_ELEMENT)){
			return new IMPSServerValue();
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONNECTION_METHOD_XML_ELEMENT)){
			return new ConnectionMethodValue();
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CALENDAR_XML_ELEMENT)){
			return new CalendarValue();
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_MAIL_BOX_XML_ELEMENT)){
			return new MailBoxValue();
		}			
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_MESSAGE_XML_ELEMENT)){
			return new MessageValue();
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_SET_XML_ELEMENT)){
			return new ContactSetValue();
		}			
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_GROUP_XML_ELEMENT)){
			return new ContactGroupValue();
		}			
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_LANDMARK_XML_ELEMENT)){
			return new LandmarkValue();
		}
		else if(componentType.trim().equalsIgnoreCase(CreatorEditorSettings.TYPE_FILE_XML_ELEMENT)){
			return new FileTypeValue();
		}			
		else{ 
			return new UnknownValue();
		}
		
	}	

}
