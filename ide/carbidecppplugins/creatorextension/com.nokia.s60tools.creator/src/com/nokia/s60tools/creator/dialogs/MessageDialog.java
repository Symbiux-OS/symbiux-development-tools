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
import com.nokia.s60tools.creator.components.messaging.Message;
import com.nokia.s60tools.creator.components.messaging.MessageValue;
import com.nokia.s60tools.creator.components.messaging.MessageVariables;
import com.nokia.s60tools.creator.editors.IComponentProvider;


/**
 *
 */
public class MessageDialog extends AbstractDialog {
	

	private String messagingType = null;


	public MessageDialog(Shell parentShell, IComponentProvider provider) {

		super(parentShell, provider);
		Message msg = (Message)provider.getEditable();
		init(msg.getMessageType());

	}
	
	public MessageDialog(Shell parentShell, String messagingType, IComponentProvider provider) {
		super(parentShell, provider);
		this.messagingType = messagingType;
		init(messagingType);
	}

	
	/**
	 * Initialize, creates Contact object
	 */
	private void init(String messagingType){
		this.messagingType = messagingType.toLowerCase();
		if(getComponent() == null){
			AbstractComponent comp = new Message(AbstractComponent.NULL_ID, messagingType);
			setComponent(comp);
		}		
		setAmountFieldsEnabled(false);
	}
  
    
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#createNewComponent()
	 */
	protected Message createNewComponent(){
		return new Message(AbstractComponent.NULL_ID, messagingType);
	}
	
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#createNewValue(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	protected MessageValue createNewValue(String type, String value, String random, String amount) {

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
			if(random_ != ModeTypes.RandomTypeNotRandom && random_ != ModeTypes.ModeTypeIncValueForEachCopy){
				value_ = AbstractValue.RANDOM_TEXT;
			}
		}
		MessageValue mes = new MessageValue(value_, random_, amout_);
		mes.setType(type);
		return mes;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#getItemValueAsString()
	 */
	protected String[] getItemTypesAsString() {
		return MessageVariables.getInstance().getItemValuesAsString(messagingType);
	}
	
	/* Creating all items to dialog area
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = createDialogAreaComposite(parent);		
		setTableColumnHeaderAmount(MAX_AMOUNT_TEXT);

		//Create also add new contact set button
		createAmountArea(composite, true);			

		createTableArea(composite);	
				
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
	    if(isInEditMode()){
	    	shell.setText("Edit " + getComponent().getValueById( messagingType )+"(s)");        	
	    }else{
	    	shell.setText("Add " + getComponent().getValueById( messagingType ) +"(s)");
	    }
	}		

	
}
