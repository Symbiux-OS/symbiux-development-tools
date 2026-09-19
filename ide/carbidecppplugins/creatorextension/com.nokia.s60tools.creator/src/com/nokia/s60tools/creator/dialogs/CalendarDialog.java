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


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.calendar.Calendar;
import com.nokia.s60tools.creator.components.calendar.CalendarValue;
import com.nokia.s60tools.creator.components.calendar.CalendarVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.editors.IComponentProvider;


/**
 *
 */
public class CalendarDialog extends AbstractDialog {
	


	
	private String calendarType = null;


//	public CalendarDialog(IShellProvider parentShell, IComponentProvider provider) {
//
//		super(parentShell, provider);
//
//
//	}
	
	public CalendarDialog(Shell parentShell, String calendarType, IComponentProvider provider) {
		super(parentShell, provider);
		this.calendarType = calendarType;
		init(calendarType);
	}
//	public CalendarDialog(IShellProvider parentShell, IComponentProvider provider, String calendarType) {
//		this(parentShell, provider);
//		this.calendarType = calendarType;
//		init(calendarType);
//	}	
	
	public CalendarDialog(Shell sh, IComponentProvider provider) {
		super(sh, provider);
		Calendar cal = (Calendar)provider.getEditable();
		init(cal.getEventType());		
	}
	/**
	 * Initialize, creates Contact object
	 */
	private void init(String calendarType){
		this.calendarType = calendarType.toLowerCase();
		if(getComponent() == null){
			AbstractComponent comp = new Calendar(AbstractComponent.NULL_ID, calendarType);
			setComponent(comp);
		}		
		setAmountFieldsEnabled(false);
	}
  
    
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#createNewComponent()
	 */
	protected Calendar createNewComponent(){
		return new Calendar(AbstractComponent.NULL_ID, calendarType);
	}
	
	
	protected CalendarValue createNewValue(String type, String value, String random, String amount) {

		int amout_ = 0;
		ModeTypes random_ = ModeTypes.RandomTypeNotRandom;
		String value_ = value;
		
		//If amount is set
		if(amount != null && amount.trim().length() > 0){
			amout_ = Integer.parseInt(amount);
		}
		
		//if random is selected
		if(random != null && random.trim().length() > 0 && !random.equals(AbstractValue.EMPTY_STRING)){
			random_ = AbstractValue.getModeTypeByText(random);
			if(random_ != ModeTypes.RandomTypeNotRandom){
				value_ = AbstractValue.RANDOM_TEXT;
			}
		}
		CalendarValue cal = new CalendarValue(type, value_, random_, amout_);
		return cal;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#getItemValueAsString()
	 */
	protected String[] getItemTypesAsString() {
		return CalendarVariables.getInstance().getItemValuesAsString(calendarType);
	}
	
	/* Creating all items to dialog area
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		Composite composite = createDialogAreaComposite(parent);
		setTableColumnHeaderAmount(MAX_AMOUNT_TEXT);
		
		//If type is Appointment, there is Attendees, showing additional help information for that
		boolean isAppointment = calendarType.equalsIgnoreCase(CreatorEditorSettings.TYPE_APPOINTMENT) 
				|| calendarType.equalsIgnoreCase(CreatorEditorSettings.TYPE_APPOINTMENT_XML_ELEMENT);

		//Create also add new contact set button, if this is appointment dialog 
		createAmountArea(composite, isAppointment);			

		createTableArea(composite);

		if(isAppointment){

			String attendeInfo = "For '" +CalendarVariables.ATTENDEE +"' use format: 'attendee email' or 'attendee email | attendee common name'";
			addInformation(composite, attendeInfo);

			String possibleValuesInfoText = 
					CalendarVariables.RECURRENTFREQUENCY_POSSIBLE_VALUES_HELP_TEXT
					+POSSIBLE_VALUES_FOR_TXT_PART_3;
			addInformation(composite, possibleValuesInfoText);	

		}
		//If type is Appointment, there is Attendees, showing additional help information for that
		else if(calendarType.equalsIgnoreCase(CreatorEditorSettings.TYPE_TODO) 
				|| calendarType.equalsIgnoreCase(CreatorEditorSettings.TYPE_TODO_XML_ELEMENT)){


			String possibleValuesInfoText = 
				POSSIBLE_VALUES_FOR_TXT_PART_1 
					+CalendarVariables.PRIORITY
					+POSSIBLE_VALUES_FOR_TXT_PART_2 
					+CalendarVariables.PRIORITY_POSSIBLE_VALUES_HELP_TEXT
					+POSSIBLE_VALUES_FOR_TXT_PART_3;
			addInformation(composite, possibleValuesInfoText);			
		}		
		addInformation(composite, AbstractVariables.DATE_TIME_AND_DATE_FORMAT_HELP_TEXT);
				
		if(wasErrors()){
			showErrorDialog("Errors occured when dialog opened", getErrors());
		}		
		return composite;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
	    super.configureShell(shell);
//	    Calendar cal = (Calendar)getComponent();
	    if(isInEditMode()){
	    	shell.setText("Edit " +getComponent().getValueById(calendarType) +"(s)");        	
	    }else{
	    	shell.setText("Add " + getComponent().getValueById(calendarType) +"(s)");
	    }
	}		

	
}
