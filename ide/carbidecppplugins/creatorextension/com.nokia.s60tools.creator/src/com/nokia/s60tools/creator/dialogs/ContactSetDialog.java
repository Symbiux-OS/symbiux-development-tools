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


import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.contact.ContactSet;
import com.nokia.s60tools.creator.components.contact.ContactSetValue;
import com.nokia.s60tools.creator.components.contact.ContactSetVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.editors.IComponentProvider;


/**
 *
 */
public class ContactSetDialog extends AbstractDialog {
	



	
	public ContactSetDialog(Shell sh, IComponentProvider provider) {
		super(sh, provider);
		init();
	}

	/**
	 * Initialize, creates Contact object
	 */
	private void init(){
		if(getComponent() == null){
			AbstractComponent comp = new ContactSet(getNextId());
			setComponent(comp);
		}		
	}
  
    
	
	protected ContactSet createNewComponent(){
		//If we are in edit mode, when creating new component for replacing old, dont create new id
		int idToComponent = isInEditMode() ? getComponent().getId() : getNextId();
		return new ContactSet(idToComponent);
	}
	
	
	protected ContactSetValue createNewValue(String type, String value, String random, String amount) {

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
		return new ContactSetValue(value_, random_, amout_);
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#getItemValueAsString()
	 */
	protected String[] getItemTypesAsString() {
		return ContactSetVariables.getInstance().getItemValuesAsString();
	}

	/* Creating all items to dialog area
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		AbstractValue val = createNewValue(ContactSetVariables.ID, "" +getComponent().getId(), null, null);
		Vector<AbstractValue> value = new Vector<AbstractValue>(1);
		value.add(val);
		getComponent().setAttribute(ContactSetVariables.ID, value);
		
		Composite composite = createDialogAreaComposite(parent);
		
		//No amount area with contact sets
		
		ContactSet set = (ContactSet)getComponent();
		//In edit mode there might be already some content
		int numberValue = set.getNumberOfExistingContacts();		
		
		//Number of existing fields area
		Composite comp = new Composite(composite,SWT.SIMPLE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));				
		createExtraNumberField(AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_DIALOG_TEXT, numberValue, comp);		
					
		
		//Amount fields is disabled
		setAmountFieldsEnabled(false);
		setTableEnabled(false);
		
		createTableArea(composite);

		addInformation(composite, "Type Contact-Set is only technical type, used to link Contacts to Contact Groups, Messages, and Calendars.");
		
		addInformation(composite, "'" + AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_DIALOG_TEXT
				+"' means that existing contacts found in device is used, and number of contacs will be added to Contact-set created.");

		if(wasErrors()){
			showErrorDialog("Errors occured when dialog opened", getErrors());
		}
		
		return composite;
	}	
	
	
	/**
	 * Get next free id for contact set
	 * @return id
	 */
	private int getNextId(){
		Vector<AbstractComponent> sets =
			getProvider().getComponents(CreatorEditorSettings.TYPE_CONTACT_SET);
		//if there is no any, id is 1
		if(sets == null || sets.size() == 0){
			return 1;
		}else{
			//find all existing ids
			Vector<Integer> ids = new Vector<Integer>(sets.size());
			for (Iterator<AbstractComponent> iterator = sets.iterator(); iterator.hasNext();) {
				ContactSet set = (ContactSet) iterator
						.next();
				ids.add(new Integer( set.getId()));
			}
			//if one is removed in the midle, next free id might be smaller than count
			for (int i = 1; i < ids.size()+1; i++) {
				if(!ids.contains(new Integer(i))){
					return i;
				}
			}
			//otherwise next free id is next number of numbers of sets
			return ids.size() +1;

		}
	}
	
}
