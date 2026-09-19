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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.filetype.FileType;
import com.nokia.s60tools.creator.components.filetype.FileTypeValue;
import com.nokia.s60tools.creator.components.filetype.FileTypeVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.editors.IComponentProvider;


/**
 *
 */
public class FileTypeDialog extends AbstractDialog {
	

	
	public FileTypeDialog(Shell sh, IComponentProvider provider) {
		super(sh, provider);
		init();
	}

	/**
	 * Initialize, creates Contact object
	 */
	private void init(){
		if(getComponent() == null){
			AbstractComponent comp = new FileType(AbstractComponent.NULL_ID);
			setComponent(comp);
		}		
	}
  
    
	
	protected FileType createNewComponent(){
		return new FileType(AbstractComponent.NULL_ID);
	}
	
	
	protected FileTypeValue createNewValue(String type, String value, String random, String amount) {

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
		return new FileTypeValue(value_, random_, amout_);
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#getItemValueAsString()
	 */
	protected String[] getItemTypesAsString() {
		return FileTypeVariables.getInstance().getItemValuesAsString();
	}
	
	/* Creating all items to dialog area
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = createDialogAreaComposite(parent);
		//Create Amount area
		createAmountArea(composite);
		//Create table area
		createTableArea(composite);		
		
		if(isAmountFieldsEnabled() ){
			addInformation(composite, AMOUNT_FIELD_INFO_TEXT);
		}		
		
		String possibleValuesInfoText = "If '" +FileTypeVariables.TYPE +"' is left empty, an empty '"+FileTypeVariables.DIR +"' will be created.";
		addInformation(composite, possibleValuesInfoText);		
		
		//Add help text for date-time fields
		addInformation(composite, AbstractVariables.DATE_TIME_AND_DATE_FORMAT_HELP_TEXT);
		
		//Format help text for accumulated and interval, format is: P1Y2M3DT4H5M6S (Iso8601).
		//@see http://en.wikipedia.org/wiki/ISO_8601#Durations		
		addInformation(composite, FileTypeVariables.DURATIONS_HELP_TEXT);		
		
		if(wasErrors()){
			showErrorDialog("Errors occured when dialog opened", getErrors());
		}		
		
		return composite;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.dialogs.AbstractDialog#okPressed()
	 */
	protected void okPressed() {
		
		
		super.okPressed();
		
		//Checking if there are encryption parameters but not type set						
		Vector<AbstractValue> enc = getComponent().getAttribute(FileTypeVariables.ENCRYPTION_TYPE);
		
		//Check If DRM_FL or DRM_CD is selected 
		boolean isDRMCDorRandom = false;
		if(enc != null){
			for (Iterator<AbstractValue> iterator = enc.iterator(); iterator.hasNext();) {
				AbstractValue val = (AbstractValue) iterator.next();
				if(FileTypeVariables.DRM_CD.equalsIgnoreCase(val.getValue())){
					isDRMCDorRandom = true;
				}else if(CreatorEditorSettings.isRandomText(val.getValue())){
					isDRMCDorRandom = true;
				}	
			}
		}

		//If encryption type has not been set or its set to DRM_FL, and there is at least one encryption item set, showing warning dialog
		if(enc == null ||  enc.isEmpty() || !isDRMCDorRandom){
			if(hasEncryptionItem()){
				//Showing warning dialog 
				showWarningDialog("Check " +FileTypeVariables.ENCRYPTION_TYPE, 
						"There is at least one '" +FileTypeVariables.DRM_CD +"' encryption parameter set, but '" +FileTypeVariables.ENCRYPTION_TYPE
						+"' is not set to '" +FileTypeVariables.DRM_CD +"'. All encryption parameters will lost on save if '" +FileTypeVariables.ENCRYPTION_TYPE
						+"' is not set to '" +FileTypeVariables.DRM_CD +"'. You can open dialog with 'Edit' -button and set '" +FileTypeVariables.ENCRYPTION_TYPE
						+"' to '" +FileTypeVariables.DRM_CD +"'.");			
			}
		}
	}
	
	/**
	 * Check if in this file has on item of encryption
	 * @return <code>true</code> if has at least one
	 */
	private boolean hasEncryptionItem() {
		return (hasEncryptionItem(FileTypeVariables.PRINT_KEYS)
		|| hasEncryptionItem(FileTypeVariables.DISPLAY_KEYS)
		|| hasEncryptionItem(FileTypeVariables.EXECUTE_KEYS)
		|| hasEncryptionItem(FileTypeVariables.PLAY_KEYS));
	}

	/**
	 * Check if one of given encryption items found in this file
	 * @param keys
	 * @return <code>true</code> if file has one of keys
	 */
	boolean hasEncryptionItem(String [][] keys){
		for (int i = 0; i < keys[0].length; i++) {
			String id = keys[0][i];
			String value = getComponent().getValueById(id);
			Vector<AbstractValue> enc = getComponent().getAttribute(value);			
			if(enc != null && !enc.isEmpty()){
				return true;
			}				
		}		
		return false;
	}


}
