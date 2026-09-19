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



import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.contact.Contact;
import com.nokia.s60tools.creator.components.contact.ContactValue;
import com.nokia.s60tools.creator.components.contact.ContactVariables;
import com.nokia.s60tools.creator.editors.IComponentProvider;


/**
 *
 */
public class ContactDialog extends AbstractDialog {
	


	
	public ContactDialog(Shell parentShell, IComponentProvider provider) {
		super(parentShell, provider);
		init();
	}

	public ContactDialog(IShellProvider parentShell, IComponentProvider provider) {
		super(parentShell, provider);
		init();
	}



	/**
	 * Initialize, creates Contact object
	 */
	private void init(){
		if(getComponent() == null){
			AbstractComponent con = new Contact(AbstractComponent.NULL_ID);
			setComponent(con);
		}		
	}

    protected AbstractComponent createNewComponent(){
		return new Contact(AbstractComponent.NULL_ID);
	}
	
	
	protected ContactValue createNewValue(String type, String value, String random, String amount) {

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
		return new ContactValue(value_, random_, amout_);
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#getItemValueAsString()
	 */
	protected String[] getItemTypesAsString() {
		return ContactVariables.getInstance().getItemValuesAsString();
	}

	/* Creating all items to dialog area
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = createDialogAreaComposite(parent);
		try {
			//create Amount area
			String [] contactSetsAsString = null;
			String contactSetSelection = null;
			if(getProvider() != null && getProvider().getComponents() != null){
				contactSetsAsString = getContactSetsAsString();
			}
			if(getComponent().hasReferenceToAnotherComponent()){
				contactSetSelection = getComponent().getReferenceToAnotherComponent().toString();
			}

			
			// create contact set link area with amount
			createAmountAreaAndLinkToOtherComponentCombo(
					composite, getContactSetRefernceText(), contactSetsAsString, contactSetSelection, true, true);		
			
			createTableArea(composite);
			addInformation(composite, AbstractVariables.DATE_FORMAT_HELP_TEXT);
			
			if(wasErrors()){
				showErrorDialog("Errors occured when dialog opened", getErrors());
			}			
			
			return composite;

		} catch (Exception e) {
			// Show error, should not be able to occur if ewerything is ok.
			e.printStackTrace();
			super.showUnableToOpenDialogErrorMsg(e);
		}			
		return null;		
	}

	
}
