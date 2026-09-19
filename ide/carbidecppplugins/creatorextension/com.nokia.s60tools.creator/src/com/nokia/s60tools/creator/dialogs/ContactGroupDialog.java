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


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.contact.ContactGroup;
import com.nokia.s60tools.creator.components.contact.ContactGroupValue;
import com.nokia.s60tools.creator.components.contact.ContactGroupVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.editors.IComponentProvider;


/**
 *
 */
public class ContactGroupDialog extends AbstractDialog {
	

	public ContactGroupDialog(Shell sh, IComponentProvider provider) {
		super(sh, provider);
		init();
	}

	/**
	 * Initialize, creates Contact object
	 */
	private void init(){
		if(getComponent() == null){
			AbstractComponent comp = new ContactGroup(AbstractComponent.NULL_ID);
			setComponent(comp);
		}		
	}
  
    
	
	protected ContactGroup createNewComponent(){
		return new ContactGroup(AbstractComponent.NULL_ID);
	}
	
	
	protected ContactGroupValue createNewValue(String type, String value, String random, String amount) {

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
		return new ContactGroupValue(value_, random_, amout_);
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#getItemValueAsString()
	 */
	protected String[] getItemTypesAsString() {
		return ContactGroupVariables.getInstance().getItemValuesAsString();
	}

	/* Creating all items to dialog area
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
			
		try {
			Composite composite = createDialogAreaComposite(parent);
			ContactGroup group = (ContactGroup)getComponent();
			
			
			//In edit mode there might be allreary some content
			String textValue =  CreatorEditorSettings.getInstance().replaceEntitiesWithChars(group.getName());
			
			Composite comp = new Composite(composite,SWT.SIMPLE);
			comp.setLayout(new GridLayout(4, false));
			comp.setLayoutData(new GridData(GridData.FILL_BOTH));				
			createExtraTextField(ContactGroupVariables.NAME, textValue, comp);	
			
			//Create button to create new contact sets
			createAddNewContactSetButton(comp, true);				

			setTableColumnHeaderAmount(MAX_AMOUNT_TEXT);
			setRandomFieldsEnabled(false);
			createTableArea(composite); //Not creating at all, because there is no values for there
			
			if(wasErrors()){
				showErrorDialog("Errors occured when dialog opened", getErrors());
			}		
		
			return composite;
		} catch (Exception e) {
			e.printStackTrace();
			showUnableToOpenDialogErrorMsg(e);
		}
		return null;
	}		
	
}
