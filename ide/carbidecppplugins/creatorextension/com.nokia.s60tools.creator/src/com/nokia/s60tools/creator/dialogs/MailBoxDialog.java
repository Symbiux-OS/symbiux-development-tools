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
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.messaging.MailBox;
import com.nokia.s60tools.creator.components.messaging.MailBoxValue;
import com.nokia.s60tools.creator.components.messaging.MailBoxVariables;
import com.nokia.s60tools.creator.editors.IComponentProvider;


/**
 *
 */
public class MailBoxDialog extends AbstractDialog {
	



	

	public MailBoxDialog(Shell sh, IComponentProvider provider) {
		super(sh, provider);
		init();
	}

	/**
	 * Initialize, creates Contact object
	 */
	private void init(){
		if(getComponent() == null){
			MailBox newBox = new MailBox(AbstractComponent.NULL_ID);
			setComponent(newBox);
		}		
	}
  
    
	
	protected MailBox createNewComponent(){
		return new MailBox(AbstractComponent.NULL_ID);
	}
	
	
	protected MailBoxValue createNewValue(String type, String value, String random, String amount) {

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
		return new MailBoxValue(value_, random_, amout_);
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#getItemValueAsString()
	 */
	protected String[] getItemTypesAsString() {
		return MailBoxVariables.getInstance().getItemValuesAsString();
	}

	/* Creating all items to dialog area
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = createDialogAreaComposite(parent);
		
		String [] typeValues = MailBoxVariables.getTypeVariables();
		String selection = null;
		if(isInEditMode()){
			MailBox box = (MailBox)getComponent();
			selection = box.getMailBoxType();
		}else{
			selection = typeValues[0];
		}
		
		//Create Amount area with type box
		createAmountAreaAndLinkToOtherComponentCombo(
				composite, "Mail box type:", typeValues, selection, false, true);
		//Create table area
		createTableArea(composite);		
		
		if(isAmountFieldsEnabled() ){
			addInformation(composite, AMOUNT_FIELD_INFO_TEXT);
		}

		
		addInformation(composite, CONNECTION_METHOD_NAME_IN_AND_OUT_HELP_TEXT);


		String possibleValuesInfoText = 
			POSSIBLE_VALUES_FOR_TXT_PART_1 
				+MailBoxVariables.INBOX_SYNC_LIMIT
				+" and "
				+MailBoxVariables.MAILBOX_SYNC_LIMIT
				+POSSIBLE_VALUES_FOR_TXT_PART_2 
				+"'All' or any integer between 1 and 999" 
				+POSSIBLE_VALUES_FOR_TXT_PART_3;
		addInformation(composite, possibleValuesInfoText);
		if(wasErrors()){
			showErrorDialog("Errors occured when dialog opened", getErrors());
		}	
		return composite;
	}	
	
}
