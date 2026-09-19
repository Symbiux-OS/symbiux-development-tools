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
 
package com.nokia.s60tools.creator.dialogs;

import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.components.calendar.CalendarVariables;
import com.nokia.s60tools.creator.components.messaging.MessageVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.editors.IComponentProvider;
import com.nokia.s60tools.creator.util.CreatorEditorConsole;

/**
 * Helper class to found correct real implementing Dialog class
 * for {@link AbstractDialog} by selected component type
 */
public class DialogLauncher {
	
	/**
	 * Get dialog by component type. Used when Add dialog is launched.
	 * @param componentType
	 * @param sh
	 * @return aDialog
	 */
	public static AbstractDialog getDialog(String componentType, Shell sh, final IComponentProvider provider) 
	{
		
		if(componentType.equals(CreatorEditorSettings.TYPE_CONTACT)){
			return new ContactDialog(sh, provider);
		}
		else if(componentType.equals(CreatorEditorSettings.TYPE_CONTACT_SET)){
			return new ContactSetDialog(sh, provider);
		}		
		else if(componentType.equals(CreatorEditorSettings.TYPE_CONTACT_GROUP)){
			return new ContactGroupDialog(sh, provider);
		}		
		else if(componentType.equals(CreatorEditorSettings.TYPE_NOTE)){
			return new NoteDialog(sh, provider);
		}
		else if(componentType.equals(CreatorEditorSettings.TYPE_BOOKMARK)){
			return new BookmarkDialog(sh, provider);
		}			
		else if(componentType.equals(CreatorEditorSettings.TYPE_BOOKMARK_FOLDER)){
			return new BookmarkFolderDialog(sh, provider);
		}	
		else if(componentType.equals(CreatorEditorSettings.TYPE_SAVED_PAGE)){
			return new SavedPageDialog(sh, provider);
		}			
		else if(componentType.equals(CreatorEditorSettings.TYPE_SAVED_PAGE_FOLDER)){
			return new SavedPageFolderDialog(sh, provider);
		}
		else if(componentType.equals(CreatorEditorSettings.TYPE_LOG)){
			return new LogDialog(sh, provider);
		}		
		else if(componentType.equals(CreatorEditorSettings.TYPE_IMPS_SERVER)){
			return new IMPSServerDialog(sh, provider);
		}
		else if(componentType.equals(CreatorEditorSettings.TYPE_CONNECTION_METHOD)){
			return new ConnectionMethodDialog(sh, provider);
		}
		else if(
				componentType.equals(CreatorEditorSettings.TYPE_TODO)
				|| componentType.equals(CreatorEditorSettings.TYPE_APPOINTMENT)
				|| componentType.equals(CreatorEditorSettings.TYPE_EVENT)
				|| componentType.equals(CreatorEditorSettings.TYPE_REMINDER)
				|| componentType.equals(CreatorEditorSettings.TYPE_ANNIVERSARY)				
		){
			return new CalendarDialog(sh, 
					CalendarVariables.getInstance().getIdByValue(componentType), provider);
		}
		else if(
				componentType.equals(CreatorEditorSettings.TYPE_MESSAGE_SMS)
				|| componentType.equals(CreatorEditorSettings.TYPE_MESSAGE_MMS)
				|| componentType.equals(CreatorEditorSettings.TYPE_MESSAGE_AMS)
				|| componentType.equals(CreatorEditorSettings.TYPE_MESSAGE_EMAIL)
				|| componentType.equals(CreatorEditorSettings.TYPE_MESSAGE_SMART)
				|| componentType.equals(CreatorEditorSettings.TYPE_MESSAGE_IR)				
				|| componentType.equals(CreatorEditorSettings.TYPE_MESSAGE_BT)
		){
			return new MessageDialog(sh, 
					MessageVariables.getInstance().getIdByValue(componentType), provider);
		}		
		else if(componentType.equals(CreatorEditorSettings.TYPE_MAIL_BOX)){
			return new MailBoxDialog(sh, provider);
		}
		else if(componentType.equals(CreatorEditorSettings.TYPE_LANDMARK)){
			return new LandmarkDialog(sh, provider);
		}
		else if(componentType.equals(CreatorEditorSettings.TYPE_FILE)){
			return new FileTypeDialog(sh, provider);
		}			
		else{
			CreatorEditorConsole.getInstance().println("Unknown dialog type: " + componentType, CreatorEditorConsole.MSG_ERROR);
			
			return null;
		}
		
	}
	/**
	 * Get dialog by component. Used when Edit dialog is launched.
	 * @param component
	 * @param sh
	 * @return aDialog
	 */
	public static AbstractDialog getDialog(final IComponentProvider provider, Shell sh) 
	{		
		
		if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_CONTACT)){
			return new ContactDialog(sh, provider);
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_CONTACT_SET)){
			return new ContactSetDialog(sh, provider);
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_CONTACT_GROUP)){
			return new ContactGroupDialog(sh, provider);
		}				
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_NOTE)){
			return new NoteDialog(sh, provider);
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_BOOKMARK)){
			return new BookmarkDialog(sh, provider);
		}			
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_BOOKMARK_FOLDER)){
			return new BookmarkFolderDialog(sh, provider);
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_SAVED_PAGE)){
			return new SavedPageDialog(sh, provider);
		}			
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_SAVED_PAGE_FOLDER)){
			return new SavedPageFolderDialog(sh, provider);
		}			
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_LOG)){
			return new LogDialog(sh, provider);
		}			
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_IMPS_SERVER)){
			return new IMPSServerDialog(sh, provider);
		}	
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_CONNECTION_METHOD)){
			return new ConnectionMethodDialog(sh, provider);
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_CALENDAR)){
			return new CalendarDialog(sh, provider); 
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_MESSAGE)){
			return new MessageDialog(sh, provider); 
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_MAIL_BOX)){
			return new MailBoxDialog(sh, provider);
		}	
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_LANDMARK)){
			return new LandmarkDialog(sh, provider);
		}
		else if(provider.getEditable().getType().equals(CreatorEditorSettings.TYPE_FILE)){
			return new FileTypeDialog(sh, provider);
		}			
		else{
			CreatorEditorConsole.getInstance().println("Unknown dialog type: " + provider.getEditable().getType(), CreatorEditorConsole.MSG_ERROR);
			return null;
		}
	}
	

}
