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
import java.util.Set;
import java.util.Vector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.creator.CreatorActivator;
import com.nokia.s60tools.creator.CreatorHelpContextIDs;
import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.calendar.Calendar;
import com.nokia.s60tools.creator.components.contact.Contact;
import com.nokia.s60tools.creator.components.contact.ContactGroup;
import com.nokia.s60tools.creator.components.contact.ContactSet;
import com.nokia.s60tools.creator.components.contact.ContactSetVariables;
import com.nokia.s60tools.creator.components.messaging.MailBox;
import com.nokia.s60tools.creator.components.messaging.MailBoxVariables;
import com.nokia.s60tools.creator.components.messaging.Message;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.editors.CreatorScriptEditor;
import com.nokia.s60tools.creator.editors.IAddComponentListener;
import com.nokia.s60tools.creator.editors.IComponentProvider;
import com.nokia.s60tools.creator.util.CreatorEditorConsole;

/**
 *
 */
public abstract class AbstractDialog extends Dialog {


	//
	// Variables for UI texts (column topics)
	//
	protected static final String AMOUNT_TXT = "Amount";
	protected static final String MODE_TXT = "Mode";
	protected static final String ITEM_VALUE_TXT = "Item value";
	protected static final String ITEM_TYPE_TXT = "Item type";
	protected static final String CLEAR_TXT = "Clear";

	//
	// Variables for UI component lengths
	//
	protected static final int TEXT_FIELD_LENGTH = 200;
	protected static final int AMOUNT_FIELD_LENGTH = 50;
	private static final int COLUMN_WIDTH_FOR_ONE_CHAR = 10;
	
	//
	// Help texts for UI
	//
	public static final String POSSIBLE_VALUES_FOR_TXT_PART_1 = "Possible values for '";
	public static final String POSSIBLE_VALUES_FOR_TXT_PART_2 = "' are: ";
	public static final String POSSIBLE_VALUES_FOR_TXT_PART_3 = ".";
	
	
	public static final String MAX_AMOUNT_TEXT = "Max amount";	
	public static final String AMOUNT_FIELD_INFO_TEXT = "If amount field is empty, amount 1 is used.";
	

	//
	// UI texts
	//
	private static final String RANDOM_LEN_MAX = "Max";
	private static final String RANDOM_LEN_DEFAULT = "Default";
	private static final String SET_RANDOM_TXT = "Set random";
	private static final String ADD_ROW_TXT = "Add row";
	
	
	/**
	 * Unexpected error message
	 */
	private static final String UNEXPECTED_ERROR_WHEN_CREATING_TABLE_AREA_ERROR_WAS = 
		"Unexpected error when creating table area, error was: ";


	/**
	 * How many lines is shown in dialog by default
	 */
	private static final int INITIAL_ITEMS_NUMBER_IN_TABLE = 20;
	/**
	 * How many items can be in combo (max)
	 */
	private static final int MAX_ITEMS_IN_COMBO = 30;	

	
	/**
	 * UI help text for refering to connection method 
	 */
	public static final String CONNECTION_METHOD_NAME_HELP_TEXT = POSSIBLE_VALUES_FOR_TXT_PART_1 
		+MailBoxVariables.CONNECTION_METHOD_NAME
		+POSSIBLE_VALUES_FOR_TXT_PART_2 
		+"'Default' or any existing " +MailBoxVariables.CONNECTION_METHOD_NAME 
		+POSSIBLE_VALUES_FOR_TXT_PART_3;	

	/**
	 * UI help text for refering to incoming and outgoing connection method 
	 */
	public static final String CONNECTION_METHOD_NAME_IN_AND_OUT_HELP_TEXT = POSSIBLE_VALUES_FOR_TXT_PART_1 
		+MailBoxVariables.INCOMING_CONNECTIONMETHOD_NAME
		+ " and "
		+MailBoxVariables.OUTGOING_CONNECTIONMETHOD_NAME
		+POSSIBLE_VALUES_FOR_TXT_PART_2 
		+"'Default' or any existing " +MailBoxVariables.CONNECTION_METHOD_NAME 
		+POSSIBLE_VALUES_FOR_TXT_PART_3;
	
	
	//
	// private fields
	//
	
	private AbstractComponent component = null;
	private boolean isInEditMode;
	private Color white;
	private Color grey;
	protected Table itemsTable;
	protected Text amoutTxtToComponent;
	
	private boolean isAmountFieldsEnabled = true;
	private boolean isRandomFieldsEnabled = true;
	private IComponentProvider provider = null;
	private CCombo linkToOtherComponentCombo = null;
	private boolean isTableEnabled = true;
	private String tableColumnHeaderAmount;
	private Text extraNbrToComponent = null;
	private Text extraTxtToComponent = null;
	private String labelForExtraNumberField;
	private String labelForExtraTextField;
	private boolean wasErrorsWithDatas = false;
	private Button addContactSetButton = null;
	private Button addRowButton;


	/**
	 * errors
	 */
	private Vector<String> errors = null;

	/**
	 * @param parentShell
	 */
	private AbstractDialog(IShellProvider parentShell) {
		super(parentShell);
		throw new RuntimeException("Not accepted");
	}

	/**
	 * @param parentShell
	 */
	private AbstractDialog(Shell parentShell) {
		super (parentShell);
		throw new RuntimeException("Not accepted");
	}
	

	/**
	 * @param parentShell
	 * @param component
	 */
	public AbstractDialog(Shell parentShell, IComponentProvider provider){
		super(parentShell);		
		this.provider = provider;
		init();
	}

	/**
	 * @param parentShell
	 * @param component
	 */
	public AbstractDialog(IShellProvider parentShell, IComponentProvider provider){
		super(parentShell);
		this.provider = provider;
		init();
	}


	/**
	 * Init
	 */
	private void init(){
		RGB rgbWhite = new RGB(255, 255, 255);
		white = new Color(null, rgbWhite);
		
		//Get grey color from parent
		grey = super.getParentShell().getBackground();
		
		setInEditMode(provider.isInEditMode());
		if(isInEditMode){
			this.component = provider.getEditable();
		}
		
		tableColumnHeaderAmount = AMOUNT_TXT;
	}
	
	/**
	 * Set context sensitive help id
	 * @param control
	 * @param id
	 */
	protected void setContextSensitiveHelpID(Control control, String id){
		
		 PlatformUI.getWorkbench().getHelpSystem().setHelp(control,id);		 
		 
	}
	/**
	 * Set context sensitive help id to item by component type
	 * @param control
	 */
	protected void setContextSensitiveHelpIDByComponentType(Control control){
		
		 if(getComponent() instanceof ContactSet){			 
			 PlatformUI.getWorkbench().getHelpSystem().setHelp(control,CreatorHelpContextIDs.CREATOR_HELP_CONTACT_SET);			 
		 }else if(getComponent() instanceof Contact || getComponent() instanceof ContactGroup){
			 PlatformUI.getWorkbench().getHelpSystem().setHelp(control,CreatorHelpContextIDs.CREATOR_HELP_CONTACTS);			 
		 }else if(getComponent() instanceof Message){			 
			 PlatformUI.getWorkbench().getHelpSystem().setHelp(control,CreatorHelpContextIDs.CREATOR_HELP_MESSAGES);
		 }else if(getComponent() instanceof Calendar){			 
			 PlatformUI.getWorkbench().getHelpSystem().setHelp(control,CreatorHelpContextIDs.CREATOR_HELP_CALENDAR);
		 }else{
			 PlatformUI.getWorkbench().getHelpSystem().setHelp(control,CreatorHelpContextIDs.CREATOR_HELP_GENERIC_COMPONENT);
		 }
		 
	}	

	/**
	 * SuperClass for all Dialogs holds instance to component created or edited. 
	 * Implementing classes will know what type of {@link AbstractComponent} needs to be handle. 
	 * @return component
	 */
	public AbstractComponent getComponent() {
		return component;
	}



	/**
	 * Set component
	 * @param component
	 */
	protected void setComponent(AbstractComponent component) {
		this.component = component;
	}
	
	/**
	 * Every dialog must be able to create a new Component by it's own type.
	 * @return AbstractComponent
	 */
	protected abstract AbstractComponent createNewComponent();

	/**
	 * Every dialog must be able to create a new Value by it's own type.
	 * @return AbstractValue
	 */
	protected abstract AbstractValue createNewValue(String type, String value, String random, String amount);

	/**
	 * Is Dialog in Edit mode (or add new mode)
	 * @return true if dialog is in edit mode, false otherwise
	 */
	protected boolean isInEditMode() {
		return isInEditMode;
	}


	/**
	 * Set Dialog to edit mode
	 * @param isInEditMode true if Dialog is in edit mode
	 */
	protected void setInEditMode(boolean isInEditMode) {
		this.isInEditMode = isInEditMode;
	}
	
	
	/**
	 * Selects given value as default to Combo
	 * @param values
	 * @param combo
	 * @param value
	 */
	protected void setComboSelection(String[] values, CCombo combo, String value) {
		if (values!= null && combo != null && value != null && !value.equals(AbstractValue.EMPTY_STRING)) {
			for (int i = 0; i < values.length; i++) {
				if (value.equalsIgnoreCase(values[i])) {
					combo.select(i);
					break;
				}
			}
		}
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#open()
	 */
	public int open(){
		return super.open();
	}

	/**
	 * @return Listener for making sure that text field contains only numbers
	 * Also back space and delete buttons is allowed
	 */
	protected VerifyListener getNumberVerifyListener() {		
		return new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				char c = e.character;
				int i = c; // 0=48, 9=57, del = 127, backspace = 8
				//If event is "" it's a clear command and must be accepted, otherwise only numbers, del and backspace is accepted commands
				if ((e.text != null && e.text.equals(AbstractValue.EMPTY_STRING) )|| (i >= 48 && i <= 57) || i == 8 || i == 127) {
					e.doit = true;// If text is not number, don't do it (set
									// text just typed)
				} else {
					e.doit = false;
				}
			}
		};
	}

	/**
	 * Check if valueText should be enabled or not and setting text to it
	 * 
	 * @param valueTxt
	 * @param randomTxt
	 */
	protected void setValueTextAndEnabling(final Text valueTxt,
			String txtToValue, String randomTxt) {

		//just in case taking of valueText size limit, if there was user defined on, other text will be also limited
		valueTxt.setTextLimit(Text.LIMIT);

		// If random is selected, text field is disabled and showing that random
		// mode is on
		if (randomTxt.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH)
				|| randomTxt
						.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG)) 
		{			
			removeNumberVerifyListener(valueTxt);//If there is number verify listener, that must be removed
			valueTxt.setText(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG);
			valueTxt.setEnabled(false);			
		} 
		else if (randomTxt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH)
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG)) 
		{
			removeNumberVerifyListener(valueTxt);//If there is number verify listener, that must be removed
			valueTxt.setText(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG);
			valueTxt.setEnabled(false);
		}
		else if (randomTxt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH)
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH_LONG)) 
		{
			valueTxt.setEnabled(true);
			try {				
				Integer.parseInt(txtToValue);
				valueTxt.setText(txtToValue);
			} catch (NumberFormatException e) {
				// When there was a text, an error will occur, skipping that one
				valueTxt.setText("" +AbstractValue.USER_DEFINED_DEFAULT_LENGTH);
				valueTxt.setFocus(); 
				valueTxt.selectAll();
			}
			//even if given data was valid or not, we add verify listener
			finally{
				//We need number verify listener when user defined len is selected
				valueTxt.addVerifyListener(getNumberVerifyListener());
				//When field is for custom random length field, text limit will be 9
				valueTxt.setTextLimit(9);					
			}
		}		

		// If just started edit mode, must put initial value to txt field
		else if (txtToValue != null && txtToValue.trim().length() > 0) {
			removeNumberVerifyListener(valueTxt);//If there is number verify listener, that must be removed			
			valueTxt.setEnabled(true);
			valueTxt.setText(CreatorEditorSettings.getInstance()
					.replaceEntitiesWithChars(txtToValue));
		}
		// else value must be enabled, and if there was a random mode txt,
		// cleaning in
		else {
			removeNumberVerifyListener(valueTxt);//If there is number verify listener, that must be removed			
			valueTxt.setEnabled(true);
			if (valueTxt.getText() != null
					&& (valueTxt.getText().equals(
							AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH)
							|| valueTxt.getText().equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG) 
							|| valueTxt.getText().equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH))
							|| valueTxt.getText().equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG)
							) 
			{
				valueTxt.setText(AbstractValue.EMPTY_STRING);
			}
		}
		
		
	}
	
	/**
	 * Check if valueText should be enabled or not and setting text to it
	 * 
	 * @param valueCombo
	 * @param randomTxt
	 */
	protected void setValueTextAndEnabling(final CCombo valueCombo,
			String txtToValue, String randomTxt, String typeComboSelection) {
		
				
		// If random is selected, text field is disabled and showing that random
		// mode is on. With fixed values, random lenght cannot be definede, so it allways is 
		//random with default lenght
		boolean isRandomValue = CreatorEditorSettings.isRandomText(randomTxt);
		
		if (isRandomValue ) 
		{			
			valueCombo.setText(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG);
			valueCombo.setEnabled(false);			
		} 

		
		// If just started edit mode, must put initial value to txt field
		else if (txtToValue != null && txtToValue.trim().length() > 0) {
			
			//When type is contact set reference, selection must be done with contactsetref as String, not by its value, which is just its id, e.g. "1"
			boolean isContactSetReference = CreatorEditorSettings.isContactSetReference(typeComboSelection);
			String selection;
			if(isContactSetReference){
				selection = getContactSetStringById(txtToValue);
			}else{
				 selection = txtToValue;
			}
			valueCombo.setEnabled(true);
			valueCombo.setText(AbstractValue.EMPTY_STRING);					
			String items[] = valueCombo.getItems();
			if(isContactSetReference && selection == null){
				valueCombo.setText(AbstractValue.EMPTY_STRING);
				String errMsg = typeComboSelection 
						+" can not be set, because contact-set: '" +txtToValue +"' was not found.";
				CreatorEditorConsole.getInstance().println(errMsg , CreatorEditorConsole.MSG_ERROR);
				addError(errMsg);
				
			}else{
				for (int i = 0; i < items.length; i++) {
					if(selection.equalsIgnoreCase(items[i])){
						valueCombo.setText(selection);
						break;
					}
				}
			}
			
		}
		// else value must be enabled, and if there was a random mode txt,
		// cleaning in
		else {
			valueCombo.setEnabled(true);
			if (valueCombo.getText() != null
					&& (valueCombo.getText().equals(
							AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH)
							|| valueCombo.getText().equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG) 
							|| valueCombo.getText().equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH))
							|| valueCombo.getText().equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG)
							|| valueCombo.getText().equals(AbstractValue.RANDOM_TEXT)
							) 
			{
				valueCombo.setText(AbstractValue.EMPTY_STRING);
			}
		}

		
	}	

	/**
	 * Remove verify listener(s) from widget
	 * @param widget
	 */
	private void removeNumberVerifyListener(final Widget widget) {
		if( widget.isListening(SWT.Verify)){
			Listener [] listeners = widget.getListeners(SWT.Verify);
			for (int i = 0; i < listeners.length; i++) {
				widget.removeListener(SWT.Verify, listeners[i]);
			}
		}
	}

	
	/**
	 * @return white color
	 */
	protected Color getWhite() {
		return white;
	}
	
	/**
	 * @return grey color
	 */
	protected Color getGrey() {
		return grey;
	}	

	/**
	 * Create table columns with header names:
	 * Item Type, Item Value, Random, Amount
	 */
	protected void createTableColums() {
		//
		//Create columns, set column widths
		//
		TableColumn columnType = new TableColumn(itemsTable, SWT.NONE);
		columnType.setWidth (200);
		columnType.setText(ITEM_TYPE_TXT);
		TableColumn columnValue = new TableColumn(itemsTable, SWT.NONE);
		columnValue.setWidth (300);
		columnValue.setText(ITEM_VALUE_TXT);
		TableColumn columnMode = new TableColumn(itemsTable, SWT.CENTER);
		columnMode.setWidth (180);
		columnMode.setText(MODE_TXT);
		TableColumn columnAmount = new TableColumn(itemsTable, SWT.NONE);
		columnAmount.setWidth (tableColumnHeaderAmount.length() * COLUMN_WIDTH_FOR_ONE_CHAR);
		columnAmount.setText(tableColumnHeaderAmount);

		//REMOVE btn
		TableColumn columnRemove = new TableColumn(itemsTable, SWT.NONE);
		columnRemove.setWidth (40);
	}

	/**
	 * Create a empty row to table
	 */
	protected void addRow(boolean openItemType, boolean setFocusToRow) throws Exception{
		addRow(AbstractValue.EMPTY_STRING, AbstractValue.EMPTY_STRING, 
				AbstractValue.EMPTY_STRING, AbstractValue.EMPTY_STRING, openItemType, setFocusToRow);
	}

	/**
	 * Create a row to table, and setting values to rows (if needed)
	 * @param key
	 * @param value
	 */
	protected void addRow(String key, AbstractValue value, boolean setFocusToRow) throws Exception{
		
		String valueStr = value.getValue();

		if(value.getModeType() == ModeTypes.RandomTypeUserDefinedLength){
			valueStr = "" +value.getRandomValueLenght();
		}
		addRow(key, valueStr, value.getModeValueText(), ""+ value.getAmount(), true, setFocusToRow );
	}

	/**
	 * Get item types allready existing in dialog
	 * @return
	 */
	private String [] getAddedItems(){
		
		
		Vector<String> addedItems = new Vector<String>();
		if(itemsTable != null){
			
			TableItem [] items = itemsTable.getItems();
			
			for (int i = 0; i < items.length; i++) {
				
				TableItem item = items[i];
				
				if(item != null){
			
					String key = item.getText(0);					
					addedItems.add(key);
					
				}
			}
		}
		return (String[])addedItems.toArray(new String[0]);
	}
	
	/**
	 * Get Item types (showable names) as String. 
	 * @return item names
	 * @param addedItems items allready added to Script
	 */	
	private String [] getItemTypesAsString(String [] addedItems){
	
		String[] itemsString = getItemTypesAsString();
		
		if(addedItems == null || addedItems.length == 0){
			return itemsString;
		}
		
		Vector<String> items = new Vector<String>(itemsString.length);
		for (int i = 0; i < itemsString.length; i++) {
			items.add(itemsString[i]);
		}						
		
		//Checking what items are currently added and what is supporting only one item in one script		
		
		for (int i = 0; i < addedItems.length; i++) {
			int itemMaxOccur = getComponent().itemMaxOccur(addedItems[i]);
			
			if(itemMaxOccur == 1){
				items.remove(addedItems[i]);
			}
		}
		
		return (String[]) items.toArray(new String[0]);
	}
	
	
	/**
	 * Check item amount enablation by item name
	 * @param itemName
	 * @param amout Text
	 * @return <code>true</code> if enabled, <code>false</code> otherwise.
	 */
	private void setAmountTextEnabletion(String itemName, Text amountText){
		
		int itemMaxOccur = getComponent().itemMaxOccur(itemName);
		boolean enable = (itemMaxOccur == 1) ? false : true;
		amountText.setEnabled(enable);
		
		if(!enable){
			amountText.setBackground(getGrey());				
		}else{
			amountText.setBackground(getWhite());
		}		
		
	}
	
	/**
	 * Get Item types (showable names) as String. 
	 * @return item names
	 */	
	protected abstract String [] getItemTypesAsString();
	
	/**
	 * Get Item values (showable names)
	 * @param itemType - one of types given by {@link #getItemTypesAsString()}
	 * @return item names if itemType has fixed values or contac-set references 
	 * or <code>null</code> if itemType has no fixed values.
	 */	
	private String [] getItemValueAsString(String itemType){
		
		String[] valuesForItemType;
		
		//If item is contact set reference, returning 
		if(CreatorEditorSettings.isContactSetReference(itemType)){
			valuesForItemType = getContactSetsAsString();
			if(valuesForItemType == null){
				valuesForItemType=new String[]{AbstractValue.EMPTY_STRING};
			}
		}
		else{
			valuesForItemType = getComponent().getValuesForItemType(itemType);
		}
		return valuesForItemType;
	}
	
	
	
	/**
	 * Create a row to table, and setting values to rows (if needed)
	 * @param key
	 * @param value
	 * @param randomValueText
	 * @param amount
	 */
	private void addRow(String key, String value, String randomValueText, 
			String amount, boolean openItemType, boolean setFocusToRow) throws Exception{

		String[] allreadyAddedItems = getAddedItems();
		String[] itemTypesAsString = getItemTypesAsString(allreadyAddedItems);
		
		//If there is allready all items supported added to table, adding row wont affect (Might occurr when "Add Row" is pushed
		if(itemTypesAsString == null || itemTypesAsString.length < 1 
				&& key.equals(AbstractValue.EMPTY_STRING) && value.equals(AbstractValue.EMPTY_STRING)){
			return;
		}
		
		final TableItem item = new TableItem (itemsTable, SWT.NONE);
		final int itemIndex = itemsTable.indexOf(item);
			
	
		//
		//CCombo for selecting item type
		//
		
		TableEditor typeComboEditor = new TableEditor(itemsTable);
		// Item names
		final CCombo typeCombo = new CCombo(itemsTable, SWT.READ_ONLY);
		//Allready added items will be update after all lines are added.
		typeCombo.setItems(itemTypesAsString);
		int visibleItemsCount = itemTypesAsString.length;
		if(visibleItemsCount > 30){
			visibleItemsCount=30;
		}
		typeCombo.setVisibleItemCount(visibleItemsCount);
		final String typeComboSelection = getTypeComboSelection(key, itemIndex, openItemType);
		typeCombo.setText(typeComboSelection);//// Select the previously selected item from the cell: combo.select(combo.indexOf(item.getText(column)));
		typeCombo.setBackground(getWhite());
		item.setText(0, typeCombo.getText());
		
		typeComboEditor.grabHorizontal = true;
		typeComboEditor.setEditor(typeCombo, item, 0);
	
		
		//
		//Text for typing item value
		//
		TableEditor valueEditor = new TableEditor(itemsTable);
		
		//checking if text or combo is needed
		String [] fixedValuesForType = getItemValueAsString(typeComboSelection);
		boolean isValuesInCombo = fixedValuesForType != null && fixedValuesForType.length > 0;
		Text valueTxt = null;
		CCombo valueCombo = null;

		if(isValuesInCombo){
			// Adding action to clear Button
			valueCombo = addValueComboToTable(fixedValuesForType, value,
					item, randomValueText, typeComboSelection);
			valueEditor.grabHorizontal = true;
			valueEditor.setEditor(valueCombo, item, 1);
			
			
		}else{
			valueTxt = createValueText(value, randomValueText,
					setFocusToRow, item, typeComboSelection);
			valueEditor.grabHorizontal = true;
			valueEditor.setEditor(valueTxt, item, 1);
			
		}
		
	
		//
		// CCombo for Random value selection.
		//
		// selecting random, max len, normal len, or no selection.
		// If random is selected, disabling item value combo
		//
		
		TableEditor modeEditor = new TableEditor(itemsTable);
		final CCombo modeCombo = new CCombo(itemsTable, SWT.READ_ONLY );
		String[] modeValues = getModeValues(isValuesInCombo, typeComboSelection);
		modeCombo.setItems(modeValues);
		
		modeCombo.setText(getModeComboSelection(randomValueText, modeValues));
		
		item.setText(2, modeCombo.getText());
		modeCombo.setBackground(getWhite());				
	
		modeEditor.grabHorizontal = true;		
		modeEditor.setEditor(modeCombo, item, 2);
		
		modeCombo.setEnabled(isRandomFieldsEnabled);
		if(!isRandomFieldsEnabled){
			modeCombo.setBackground(getGrey());
		}else{
			modeCombo.setBackground(getWhite());
		}
	
		//
		//Text field for Amount text 
		//
		
		TableEditor amountEditor = new TableEditor(itemsTable);
		final Text amountTxt = new Text(itemsTable, SWT.NONE);
		amountTxt.setTextLimit(5);	
		//don't show if amount is not set (is 0)
		if(!amount.equals("0")){
			amountTxt.setText(amount);
		}
		item.setText(3, amountTxt.getText());
		
		// Verify that amount is typed with numbers
		amountTxt.addVerifyListener(getNumberVerifyListener());
		
		//Update item when modify	
		amountTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				item.setText(3, amountTxt.getText());
			}
		});
		
		//In some cases amount fields is not enabled. 
		//Future improvement idea, possibility to enable/disable amountTxt by selected type.
		if(CreatorEditorSettings.isContactSetReference(key) || CreatorEditorSettings.isContactSetReference(typeComboSelection)){
			amountTxt.setEnabled(true);//always enabled with contact set reference
			modeCombo.setEnabled(false);
			modeCombo.setBackground(getGrey());
		}
		else if(!isAmountFieldsEnabled){
			setModeComboEnablation(typeComboSelection, modeCombo);
			amountTxt.setEnabled(isAmountFieldsEnabled);			
		}
		else{
			setModeComboEnablation(typeComboSelection, modeCombo);
			setAmountTextEnabletion(typeComboSelection, amountTxt);	
		}

		
		amountEditor.grabHorizontal = true;
		amountEditor.setEditor(amountTxt, item, 3);
		
		//
		// Remove btn
		//
		TableEditor clrBtnEditor = new TableEditor(itemsTable);
		final Button clearBtn = new Button(itemsTable, SWT.PUSH);
		clearBtn.setLayoutData(new GridData( GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		clearBtn.setFont(getButtonFont());
		clearBtn.setText(CLEAR_TXT);
		clearBtn.pack ();//This must be called, otherwise button is not visible
		clrBtnEditor.minimumWidth = clearBtn.getSize ().x + 5;//Using +5 to fit button precisely to column 
		clrBtnEditor.horizontalAlignment = SWT.CENTER;


		//Setting all listeners to item value, depending on item type, listeners will be for
		//Text or CCombo type of value.
		setItemValueListeners(item, clrBtnEditor, valueEditor, typeCombo, fixedValuesForType,
				isValuesInCombo, valueTxt, valueCombo, modeCombo, amountTxt,
				clearBtn);		
		
		clrBtnEditor.setEditor(clearBtn, item, 4);
		
		// Update item when modify
		typeCombo.addSelectionListener(new TypeComboSelectionListener(item,
				clrBtnEditor, valueEditor, typeCombo, valueTxt, valueCombo,
				modeCombo, amountTxt, clearBtn));	
	
		itemsTable.addListener(SWT.SetData, new TypeComboItemsSetterListener(typeCombo));
		
		setContextSensitiveHelpIDByComponentType(typeCombo);
		setContextSensitiveHelpID(modeCombo, CreatorHelpContextIDs.CREATOR_HELP_RANDOM_VALUES);
		setContextSensitiveHelpIDByComponentType(amountTxt);
		setContextSensitiveHelpIDByComponentType(clearBtn);
	}

	private void setModeComboEnablation(final String typeComboSelection,
			final CCombo modeCombo) {
		boolean isModeEnabled = getComponent().getVariables().isModeEnabledForKey(typeComboSelection);
		modeCombo.setEnabled(isModeEnabled);
		if(!isModeEnabled){
			modeCombo.setBackground(getGrey());				
		}else{
			modeCombo.setBackground(getWhite());
		}
	}

	/**
	 * Get selection for mode combo
	 * @param modeText
	 * @param modeValues
	 * @return
	 */
	private String getModeComboSelection(String modeText, String [] modeValues) {
		
		if(modeText != null && !modeText.equals(AbstractValue.EMPTY_STRING)){
			for (int i = 0; i < modeValues.length; i++) {
				if(modeText.equalsIgnoreCase(modeValues[i])){
					return modeText;
				}
			}
		}
		
		return modeValues[0];
	}

	/**
	 * Get values for mode combo
	 * @param isValuesInCombo
	 * @param typeComboSelection
	 * @return values for mode combo
	 */
	private String[] getModeValues(boolean isValuesInCombo, String typeComboSelection) {

		boolean incValueSupported = isTypeSupportingIncValueForeEachCopy(typeComboSelection);
		
		String[] modeValues;
		
		if(!incValueSupported && isValuesInCombo){
			modeValues = AbstractValue.getModeValuesForFixedValues();
		}
		else if(!incValueSupported){
			modeValues = AbstractValue.getModeValues();
		}
		//else incValueSupported == true
		else{
			modeValues = AbstractValue.getModeValuesForSupportingIncValueForeEachCopy();
		}

		return modeValues;
	}
	
	/**
	 * Check if component is supporting <code>incvalueforeachcopy</code> parameter.
	 * @param type
	 * @return <code>true</code> if supporting.
	 */
	private boolean isTypeSupportingIncValueForeEachCopy (String type){

		return getComponent().isTypeSupportingIncValueForEachCopy(type);
		
	}	

	/**
	 * Set listeners related to value item (Text or CCombo)
	 * @param item
	 * @param clrButtonEditor
	 * @param valueEditor
	 * @param typeCombo
	 * @param fixedValuesForType
	 * @param isValuesInCombo
	 * @param valueTxt
	 * @param valueCombo
	 * @param modeCombo
	 * @param amountTxt
	 * @param clearBtn
	 */
	private void setItemValueListeners(final TableItem item,
			TableEditor clrButtonEditor, TableEditor valueEditor, final CCombo typeCombo,
			String[] fixedValuesForType, boolean isValuesInCombo,
			Text valueTxt, CCombo valueCombo, final CCombo modeCombo,
			final Text amountTxt, final Button clearBtn) {
		
		//
		//First remove existing listeners, so there will be no extra listeners with non existing objects
		//
		
		Listener[] listeners = modeCombo.getListeners(SWT.Selection);
		for (int i = 0; i < listeners.length; i++) {
			modeCombo.removeListener(SWT.Selection, listeners[i]);
		}	
		listeners = clearBtn.getListeners(SWT.Selection);
		for (int i = 0; i < listeners.length; i++) {
			clearBtn.removeListener(SWT.Selection, listeners[i]);			
		}
		listeners = item.getListeners(SWT.Modify);
		for (int i = 0; i < listeners.length; i++) {
			item.removeListener(SWT.Modify, listeners[i]);			
		}		
		if(valueCombo != null){
			listeners = valueCombo.getListeners(SWT.SetData);
			for (int i = 0; i < listeners.length; i++) {
				valueCombo.removeListener(SWT.SetData, listeners[i]);			
			}	
		}
		
		
		if(isValuesInCombo){
			
			valueCombo.setVisibleItemCount(fixedValuesForType.length);			
			
			//Update item text when text is modified (and random Combo when value text is modified by fulfill random button)
			valueCombo.addModifyListener( new ValueModifyListener(item, valueCombo, typeCombo));
			//Add listener to check this value enablation by selection of another value
			item.addListener(SWT.Modify,  new CheckItemValueEnablationsListener(typeCombo, valueCombo, modeCombo, amountTxt));
			
			 		
			//Update value text item text when combo selection is changed
			modeCombo.addSelectionListener(new ModeComboSelectionListener(modeCombo, valueCombo, item));
			item.addListener(SWT.Modify, new SetAsRandomValueItemListener(item, valueCombo, modeCombo));	
			
			// Adding action to clear Button
			clearBtn.addSelectionListener(getClearButtonSelectionListener(item, typeCombo, valueCombo, modeCombo,
					amountTxt));		
			
    		//Adding listener to update items when new contact set is created, if there is contactSet creation button, and 
			//if we have contact set reference as type.
			if(addContactSetButton != null && CreatorEditorSettings.isContactSetReference(typeCombo.getText())){
				addContactSetButton.addListener(SWT.SetData, new AddNewContactSetButtonListener(valueCombo));
			}
			

		}else{
			//Update item text when text is modified (and random Combo when value text is modified by fulfill random button)
			valueTxt.addModifyListener( new ValueModifyListener(item, valueTxt, typeCombo));					
			//Add listener to check this value enablation by selection of another value
			item.addListener(SWT.Modify,  new CheckItemValueEnablationsListener(typeCombo, valueTxt, modeCombo, amountTxt));
			
			//Update value text item text when combo selection is changed
			modeCombo.addSelectionListener(new ModeComboSelectionListener(modeCombo, valueTxt, item));			
			//When fulfill all values with random -button modifies values, setting also combo and text field values
			item.addListener(SWT.Modify, new SetAsRandomValueItemListener(item, valueTxt, modeCombo));	
			
			// Adding action to clear Button
			clearBtn.addSelectionListener(getClearButtonSelectionListener(item, typeCombo, valueTxt, modeCombo,
					amountTxt));			
	
		}

	}



	/**
	 * Creates a value text
	 * @param value
	 * @param randomValueText
	 * @param setFocusToRow
	 * @param item
	 * @param typeComboSelection
	 * @return
	 */
	private Text createValueText(String value, String randomValueText,
			boolean setFocusToRow, final TableItem item, String typeComboSelection) {
		final Text valueTxt = new Text(itemsTable, SWT.NONE);
		valueTxt.setFont(getUnicodeFont());
		setValueTextAndEnabling(valueTxt, value, randomValueText);	
		if(setFocusToRow){
			valueTxt.setFocus(); 
		}
		item.setText(1, valueTxt.getText());
    	if(CreatorEditorSettings.isContactSetReference(valueTxt.getText())){
    		setContextSensitiveHelpID(valueTxt, CreatorHelpContextIDs.CREATOR_HELP_CONTACT_SET);
    	}else{
    		setContextSensitiveHelpIDByComponentType(valueTxt);
    	}
    	
    	setTipTextToValue(valueTxt, typeComboSelection);
    	
		return valueTxt;
	}
	
	/**
	 * Sets tip text to value if needed
	 * @param valueTxt
	 * @param typeComboSelection
	 */
	private void setTipTextToValue(Text valueTxt, String typeComboSelection) {
		AbstractVariables var = getComponent().getVariables();
		String tipText = var.getTipText(typeComboSelection);
		if(tipText != null){
			valueTxt.setToolTipText(tipText);
		}
	}

	/**
	 * Listener for mode selection combo
	 */
	private class ModeComboSelectionListener implements SelectionListener{
	
		private  CCombo modeCombo = null;
		private  Text text = null;
		private  TableItem item = null;
		private  CCombo combo = null;

		public ModeComboSelectionListener(final CCombo modeCombo, final Text text, final TableItem item){
			this.modeCombo = modeCombo;
			this.text = text;
			this.item = item;
			
		}
		public ModeComboSelectionListener(final CCombo modeCombo, final CCombo combo, final TableItem item){
			this.modeCombo = modeCombo;
			this.combo = combo;
			this.item = item;
			
		}		
		
        public void widgetSelected(SelectionEvent event) {
        	
        	String rndTxt = modeCombo.getText();
        	
        	if(text!=null){
	        	if(CreatorEditorSettings.isContactSetReference(rndTxt)){ 
	        		setContextSensitiveHelpID(text, CreatorHelpContextIDs.CREATOR_HELP_CONTACT_SET);
	        	}else{
	        		setContextSensitiveHelpIDByComponentType(text);
	        	}
	        	item.setText(2, rndTxt);
	        	setValueTextAndEnabling(text, null, rndTxt);
        	}
        	else{
	        	if(CreatorEditorSettings.isContactSetReference(rndTxt)){ 
	        		setContextSensitiveHelpID(combo, CreatorHelpContextIDs.CREATOR_HELP_CONTACT_SET);
	        	}else{
	        		setContextSensitiveHelpIDByComponentType(combo);
	        	}
	        	combo.setEnabled(false);
	        	item.setText(2, rndTxt);
	        	setValueTextAndEnabling(combo, null, rndTxt, null);
        	}
        }

		public void widgetDefaultSelected(SelectionEvent e) {
			//Not needed
		}
      }	


	/**
	 * Class for listening value text changes
	 */
	private class ValueModifyListener implements ModifyListener

	{
		private TableItem item = null;
		private Text valueText = null;
		private CCombo valueCombo = null;
		private final CCombo typeCombo;
		
		public ValueModifyListener(TableItem item , Text valueText, final CCombo typeCombo){
			this.item = item;
			this.valueText = valueText;
			this.typeCombo = typeCombo;			
		}		
		public ValueModifyListener(TableItem item , CCombo valueCombo, final CCombo typeCombo){
			this.item = item;
			this.valueCombo = valueCombo;
			this.typeCombo = typeCombo;			
		}			
		public void modifyText(ModifyEvent e) {						
			
			if(valueText != null){				
				item.setText(1, valueText.getText());				
			}
			else{
	            item.setText(1, valueCombo.getText());	       
				//Check that if something must do to some values by the selection
				checkIfNeedToCallCheckItemValueEnablationListenersAndCallIfNeeded(
						valueCombo, valueText, typeCombo);
				
	            
			}
		}

     }	
	
	/**
	 * Just for enabling to cast Events when using set as random functionality
	 */
	private class SetRandomEvent extends Event{
		
	}
	
	/**
	 * Just for enabling to cast Event when checking 
	 * if we should disable/enable some valus by some selection made
	 */
	private class CheckItemValueEnablationsEvent extends Event{

		private String type;
		private String value;


		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		public void setType(String type) {
			this.type = type;			
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}	
	
	
	/**
	 * Listener class for setting values as random, used by button.
	 */
	private class SetAsRandomValueItemListener implements Listener{

		TableItem item = null;
		Text valueTxt = null;
		CCombo randomCombo = null;
		private CCombo valueCombo = null;

		public SetAsRandomValueItemListener(TableItem item, Text valueTxt, CCombo randomCombo){
			this.item = item;
			this.valueTxt = valueTxt;
			this.randomCombo = randomCombo;
			
		}		
		public SetAsRandomValueItemListener(TableItem item,CCombo valueCombo, CCombo randomCombo){
			this.item = item;
			this.valueCombo = valueCombo;
			this.randomCombo = randomCombo;
			
		}
		
		public void handleEvent(Event e) {

			//Checking that if we have wanted event
			if(!(e instanceof SetRandomEvent)){
				return;
			}
			
			String txt = item.getText(1);
						
			//If value is in text, but not in combo
			if (valueTxt!=null) {
				
				if (e.doit) {
					removeNumberVerifyListener(valueTxt);
				}
				if (txt != null) {
					setValueTextAndEnablingAndRandomComboSelection(txt,
							valueTxt, randomCombo);
				}
			}
			//else value is in combo
			else{				
				setValueTextAndEnablingAndRandomComboSelection(txt, valueCombo, randomCombo);
			}			
		}		
	}	
	
	/**
	 * Listener class for enabling/disabling items by some other value set in dialog.
	 * E.g. With File, crypted with CRM-FL, then all CRM-CD parameters will be disabled.
	 */
	private class CheckItemValueEnablationsListener implements Listener{

		CCombo typeCombo;
		Text valueTxt = null;
		Text amountTxt = null;
		CCombo modeCombo = null;
		private CCombo valueCombo = null;

		public CheckItemValueEnablationsListener(CCombo typeCombo, Text valueTxt, CCombo modeCombo, Text amountTxt){
			this.typeCombo = typeCombo;
			this.valueTxt = valueTxt;
			this.modeCombo = modeCombo;
			this.amountTxt = amountTxt;
			
		}		
		public CheckItemValueEnablationsListener(CCombo typeCombo,CCombo valueCombo, CCombo modeCombo, Text amountTxt){
			this.typeCombo = typeCombo;
			this.valueCombo = valueCombo;
			this.modeCombo = modeCombo;
			this.amountTxt = amountTxt;
		}
		
		public void handleEvent(Event e) {
						
			if(! ( e instanceof CheckItemValueEnablationsEvent )){
				return;
			}
			
			CheckItemValueEnablationsEvent event = (CheckItemValueEnablationsEvent)e;
			String type = event.getType();
			String value = event.getValue();
			String typeToBeDisabled = typeCombo.getText();

			enableOrDisableRowItems(type, value, typeToBeDisabled, valueTxt, valueCombo, modeCombo, amountTxt);
						
		}

		
	}	

	/**
	 * Enables or disable items of row 
	 * @param type
	 * @param value
	 * @param typeToBeDisabled
	 * @param valueTxt
	 * @param valueCombo
	 * @param modeCombo
	 */
	private void enableOrDisableRowItems(String type, String value,
			String typeToBeDisabled,
			Text valueTxt,
			CCombo valueCombo,
			CCombo modeCombo, Text amountTxt) {
				
		boolean enableAllValues = !getComponent().isTypeDisabledByTypeAndValue(type, value, typeToBeDisabled);
		boolean enableValue = enableAllValues;
		
		String currentValueTxt;
		if (valueTxt!=null) {
			currentValueTxt = valueTxt.getText();
		}else{
			currentValueTxt = valueCombo.getText();
		}
		
		//Check if current value is random value now
		boolean isRandomValue = CreatorEditorSettings.isRandomText(currentValueTxt);		
		//If value is random, and we try to enable values, dont do that
		if (isRandomValue && enableAllValues) 
		{
			enableValue = false;
		}

		//If value is in text, but not in combo
		if (valueTxt!=null) {
			valueTxt.setEnabled(enableValue);
		}
		//else value is in combo
		else{
			valueCombo.setEditable(enableValue);
		}
		
		//dont enable mode combo if its not enabled to this type
		boolean isModeEnabled = getComponent().getVariables().isModeEnabledForKey(typeToBeDisabled);		
		if(isModeEnabled){
			modeCombo.setEnabled(enableAllValues);
		}
		
		//amount field can be enabled or disabled if its in use
		if(isAmountFieldsEnabled()){
			amountTxt.setEnabled(enableAllValues);
		}
	}	
	
	/**
	 * Listener to set items to Type Combo when selection is made
	 */
	private class TypeComboItemsSetterListener implements Listener{
		
		private final CCombo typeCombo;

		public TypeComboItemsSetterListener(CCombo typeCombo){
			this.typeCombo = typeCombo;
			
		}

		public void handleEvent(Event event) {
			
			if(event.doit && event.data != null && event.data instanceof String[]){				
				String [] items = (String[]) event.data;			
				String selectionText = typeCombo.getText();
				typeCombo.setItems(items);
				int count = items.length;
				if(count > MAX_ITEMS_IN_COMBO){
					count = MAX_ITEMS_IN_COMBO;
				}
				typeCombo.setVisibleItemCount(count);
				typeCombo.setText(selectionText);
			}
		}
		
	}
	
	/**
	 * Get listener for type combo.
	 * There is logic for changing value field from text to combo and vice versa. 
	 * @param item
	 * @param typeCombo
	 * @param valueTxt
	 * @param randomCombo
	 * @param amountTxt
	 * @return
	 */
	private class TypeComboSelectionListener implements SelectionListener{

		private TableItem item;
		private CCombo typeCombo;
		private Text valueTxt;
		private Text amountTxt;
		private CCombo valueCombo;
		private CCombo modeCombo;
		private TableEditor valueEditor;
		private TableEditor clrBtnEditor;
		private final Button clearBtn;

		public TypeComboSelectionListener ( TableItem item,
				TableEditor clrButtonEditor, TableEditor valueEditor,  CCombo typeCombo,
				Text valueTxt, CCombo valueCombo,  CCombo modeCombo,
				 Text amountTxt,  Button clearBtn){
			this.clrBtnEditor = clrButtonEditor;
			this.valueEditor = valueEditor;
			this.item = item;
			this.typeCombo = typeCombo;
			this.valueTxt = valueTxt;
			this.valueCombo = valueCombo;
			this.amountTxt = amountTxt;
			this.modeCombo = modeCombo;
			this.clearBtn = clearBtn;
			
		}		


		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent event) {
			
	        String typeComboSelection = typeCombo.getText();
			item.setText(0, typeComboSelection);

			String [] fixedValuesForType = getItemValueAsString(typeComboSelection);
			//Will values be in combo afterwards 
			boolean isValuesInCombo = fixedValuesForType != null && fixedValuesForType.length > 0;
			//Was value field type changed by user selection? Will be true if before value was text, and now it will be combo and so on...
			boolean isValueFieldTypeChanged = 
				isValuesInCombo && valueCombo == null || !isValuesInCombo && valueTxt == null 
				? true : false;
			
			
	  		if(CreatorEditorSettings.isContactSetReference(typeComboSelection))
	  		{
				amountTxt.setEnabled(true);//always enabled with contact set reference
				modeCombo.setEnabled(false);
				modeCombo.setText(AbstractValue.RANDOM_TEXT_NOT_RANDOM);//Setting to edit mode when contact-set is selected
				modeCombo.setBackground(getGrey());

			}else if(!isAmountFieldsEnabled){
				amountTxt.setText(AbstractValue.EMPTY_STRING);				
				amountTxt.setEnabled(isAmountFieldsEnabled);
				setModeComboEnablation(typeComboSelection, modeCombo);				
			}else{
				amountTxt.setText(AbstractValue.EMPTY_STRING);				
				setAmountTextEnabletion(typeComboSelection, amountTxt);
				setModeComboEnablation(typeComboSelection, modeCombo);								
			}

			
			// Just changing contents when value remains in combo
			if (isValuesInCombo && !isValueFieldTypeChanged) {
				valueCombo.setItems(fixedValuesForType);
				valueCombo.setVisibleItemCount(fixedValuesForType.length);

				//When values was in combo and type was changed to contact set, we must set listeners again to listen contact-set changes.
				setItemValueListeners(item, clrBtnEditor, valueEditor, typeCombo, fixedValuesForType,
						isValuesInCombo, null, valueCombo, modeCombo, amountTxt,
						clearBtn);					
			}
			//value remains in text
			else if (!isValuesInCombo && !isValueFieldTypeChanged) {
				valueTxt.setText(AbstractValue.EMPTY_STRING);
				valueTxt.setEnabled(true);
			}
			//else isValueFieldTypeChanged == true
			else{
				//Removing old control when new will be created
				Control editable = valueEditor.getEditor();
				editable.dispose();

				//value changed from text to combo
				if (isValuesInCombo ) {
					valueCombo = addValueComboToTable(fixedValuesForType, AbstractValue.EMPTY_STRING, 
							item, AbstractValue.EMPTY_STRING, typeComboSelection);
					valueEditor.setEditor(valueCombo, item, 1);
					
					setItemValueListeners(item, clrBtnEditor, valueEditor, typeCombo, fixedValuesForType,
							isValuesInCombo, null, valueCombo, modeCombo, amountTxt,
							clearBtn);		
					valueTxt = null;
					
					
				} else// if (!isValuesInCombo && isValueFieldTypeChanged) 
					{
					valueTxt = createValueText(AbstractValue.EMPTY_STRING, AbstractValue.EMPTY_STRING, true, item, typeComboSelection);
					valueTxt.setEnabled(true);
					valueEditor.setEditor(valueTxt, item, 1);

					setItemValueListeners(item, clrBtnEditor, valueEditor, typeCombo, null,
							isValuesInCombo, valueTxt, null, modeCombo, amountTxt,
							clearBtn);					
					valueCombo = null;//For removing listners 
					
				}
			}

			//setting modeCombo values after all other changes are made
			String modeComboValues [] = getModeValues(isValuesInCombo, typeComboSelection);
			modeCombo.setItems(modeComboValues);
			modeCombo.setText(getModeComboSelection(AbstractValue.EMPTY_STRING, modeComboValues));
			item.setText(2, modeCombo.getText());			
			
			//Notify table listeners to update item combos
			notifyTypeComboDataListeners(); 						
			
		}


		//Not needed.
		public void widgetDefaultSelected(SelectionEvent e) {			
		}
	}	
	
	/**
	 * Notify table listeners to update type combo data
	 */
	private void notifyTypeComboDataListeners() {
		
		String addedItems[] = getAddedItems();//get items added allready to dialog
		String toBeSetItems[] = getItemTypesAsString(addedItems);//get items to be set to all item types
		
		Event itemsComboUpdateEvent = new Event();
		itemsComboUpdateEvent.doit = true;
		itemsComboUpdateEvent.data = toBeSetItems;
		
		//Enabling / disabling add row button if there is no rows able to add
		if(toBeSetItems.length == 0 && addRowButton != null){
			addRowButton.setEnabled(false);
		}else if(addRowButton != null){
			addRowButton.setEnabled(true);
		}//else no action 
		
		//Notify all type-combos that new data must be set to combos
		itemsTable.notifyListeners(SWT.SetData, itemsComboUpdateEvent);
	}

	/**
	 * Get listener for Clear button
	 * @param item
	 * @param typeCombo
	 * @param valueTxt
	 * @param randomCombo
	 * @param amountTxt
	 * @return
	 */
	private SelectionAdapter getClearButtonSelectionListener(
			final TableItem item, final CCombo typeCombo, final Text valueTxt,
			final CCombo randomCombo, final Text amountTxt) {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				

				// Clearing data, also item texts must be cleared
				typeCombo.setText(AbstractValue.EMPTY_STRING);
				item.setText(0, AbstractValue.EMPTY_STRING);
				valueTxt.setText(AbstractValue.EMPTY_STRING);
				item.setText(1, AbstractValue.EMPTY_STRING);
				randomCombo.setText(AbstractValue.EMPTY_STRING);
				item.setText(2, AbstractValue.EMPTY_STRING);
				amountTxt.setText(AbstractValue.EMPTY_STRING);
				item.setText(3, AbstractValue.EMPTY_STRING);

				typeCombo.setEnabled(true);
				valueTxt.setEnabled(true);
				randomCombo.setEnabled(true);
				amountTxt.setEnabled(true);
				
				randomCombo.setBackground(getWhite());
				
				//Notify table listeners to update item combos
				notifyTypeComboDataListeners();	
			}
		};
	}
	
	/**
	 * Get listener for Clear button
	 * @param item
	 * @param typeCombo
	 * @param valueCombo
	 * @param randomCombo
	 * @param amountTxt
	 * @return
	 */
	private SelectionAdapter getClearButtonSelectionListener(
			final TableItem item, final CCombo typeCombo, final CCombo valueCombo,
			final CCombo randomCombo, final Text amountTxt) {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				// Clearing data, also item texts must be cleared
				typeCombo.setText(AbstractValue.EMPTY_STRING);
				item.setText(0, AbstractValue.EMPTY_STRING);
				valueCombo.setText(AbstractValue.EMPTY_STRING);
				item.setText(1, AbstractValue.EMPTY_STRING);
				randomCombo.setText(AbstractValue.EMPTY_STRING);
				item.setText(2, AbstractValue.EMPTY_STRING);
				amountTxt.setText(AbstractValue.EMPTY_STRING);
				item.setText(3, AbstractValue.EMPTY_STRING);

				typeCombo.setEnabled(true);
				valueCombo.setEnabled(true);
				randomCombo.setEnabled(true);
				amountTxt.setEnabled(true);
				
				randomCombo.setBackground(getWhite());
			
				//Notify table listeners to update item combos
				notifyTypeComboDataListeners();					
			}
		};
	}	
	
	
	/**
	 * When "Set Random" button is pushed, and user wants to set all values as random
	 * editing value field and random combo selection
	 * @param txt
	 * @param valueTxt
	 * @param modeCombo
	 */
	private void setValueTextAndEnablingAndRandomComboSelection(String txt,
			Text valueTxt, CCombo modeCombo) {
		
		valueTxt.setTextLimit(Text.LIMIT);

		if(txt == null){
			valueTxt.setEnabled(true);
			valueTxt.setText(AbstractValue.EMPTY_STRING);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_NOT_RANDOM);
		}
		// If random is selected, text field is disabled and showing that random
		// mode is on
		
		//random mode with default len
		else if (txt.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH)
				|| txt.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG)) {

			valueTxt.setEnabled(false);
			valueTxt.setText(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH);
		} 
		//Random mode with max len
		else if (txt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH)
				|| txt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG)) {
			valueTxt.setEnabled(false);
			valueTxt.setText(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_MAX_LENGTH);
		}
		//random mode with user defined len
		else if (txt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH)
				|| txt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH_LONG)) {
			valueTxt.setEnabled(true);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH);
		}
		//mode is RANDOM_TEXT_INC_FOR_EACH_COPY This should not be able to occur, because RANDOM_TEXT_INC_FOR_EACH_COPY
		//value are always phone numbers and will be on text field, just in case implemented.
		else if (txt.equals(AbstractValue.RANDOM_TEXT_INC_FOR_EACH_COPY)) {
			valueTxt.setEnabled(true);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_INC_FOR_EACH_COPY);
		}

		//default, not random
		else{
			valueTxt.setEnabled(true);
			valueTxt.setText(txt);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_NOT_RANDOM);
		}		
	}	
	
	/**
	 * Checks values from type and value (from valueTxt if not null or from valuecombo if txt was null)
	 * and then checks if other items must check they enablations,
	 * and enables/disables if required
	 * 
	 * @param txt
	 * @param valueCombo
	 * @param typeCombo
	 */
	private void checkIfNeedToCallCheckItemValueEnablationListenersAndCallIfNeeded(
			CCombo valueCombo, Text valueText, CCombo typeCombo) {
		
		String value;
		if(valueCombo != null){
			value = valueCombo.getText();
		}else{
			value = valueText.getText();
		}		
		String type = typeCombo.getText();

		callCheckItemValueEnablationListeners(type, value);
	}
	
	/**
	 * Check all items from itemsTable, and enables/disables those if required by some else values
	 */
	private void checkIfNeedToCallItemValueEnablationListners(){
		TableItem[] items = itemsTable.getItems();
		//Looping through all items in table
		for (int i = 0; i < items.length; i++) {

			TableItem item = items[i];
			if (item != null) {
				String type = item.getText(0);
				String value = item.getText(1);
				
				callCheckItemValueEnablationListeners(type, value);
			}
		}		
	}

	/**
	 * Calls listeners to check they values if required by given type and value.
	 * 
	 * @see AbstractComponent#hasTypeLimitationsForOtherValues(String, String)
	 * 
	 * @param value
	 * @param type
	 */
	private void callCheckItemValueEnablationListeners(String type, String value) {
		
		//check from component, if that type and value combination needs to call listeners		
		boolean wakeUpListners = getComponent().hasTypeLimitationsForOtherValues(type, value);
		
		//If we need to wake up listners, doing so
		if(wakeUpListners){
		
			TableItem[] items = itemsTable.getItems();
			//Looping through all items in table
			for (int i = 0; i < items.length; i++) {
	
				TableItem item = items[i];
				if (item != null) {
					//Creating special event, so other listeners can check that if they are not intressed of this event
					CheckItemValueEnablationsEvent e = new CheckItemValueEnablationsEvent();
					e.setType(type);
					e.setValue(value);
					e.doit = true;
					item.notifyListeners(SWT.Modify, e);
				}
			}
		}
	}
	
	/**
	 * When "Set Random" button is pushed, and user wants to set all values as random
	 * editing value field and random combo selection
	 * @param txt
	 * @param valueCombo
	 * @param modeCombo
	 */
	private void setValueTextAndEnablingAndRandomComboSelection(String txt,
			CCombo valueCombo, CCombo modeCombo) {
		
		valueCombo.setTextLimit(Text.LIMIT);

		if(txt == null){
			valueCombo.setEnabled(true);
			valueCombo.setText(AbstractValue.EMPTY_STRING);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_NOT_RANDOM);
		}
		// If random is selected, text field is disabled and showing that random
		// mode is on
		
		//random mode with default len
		else if (txt.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH)
				|| txt.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG)) {

			valueCombo.setEnabled(false);
			valueCombo.setText(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH);
		} 
		//Random mode with max len
		else if (txt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH)
				|| txt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG)) {
			valueCombo.setEnabled(false);
			valueCombo.setText(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_MAX_LENGTH);
		}
		//random mode with user defined len
		else if (txt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH)
				|| txt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH_LONG)) {
			valueCombo.setEnabled(true);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH);
		}
		//mode is RANDOM_TEXT_INC_FOR_EACH_COPY This should not be able to occur, because RANDOM_TEXT_INC_FOR_EACH_COPY
		//value are always phone numbers and will be on text field, just in case implemented.
		else if (txt.equals(AbstractValue.RANDOM_TEXT_INC_FOR_EACH_COPY)) {
			valueCombo.setEnabled(true);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_INC_FOR_EACH_COPY);
		}
		//default, not random
		else{
			valueCombo.setEnabled(true);
			valueCombo.setText(txt);
			modeCombo.setText(AbstractValue.RANDOM_TEXT_NOT_RANDOM);
		}		
	}		


	/**
	 * Adds one value combo to table 
	 * @param values
	 * @param selectedValue
	 * @param item
	 * @param randomValueText
	 * @param typeComboSelection
	 * @return
	 */
	private CCombo addValueComboToTable(String[] values, String selectedValue,
			final TableItem item, String randomValueText, String typeComboSelection) {
		TableEditor editor;
		//
		//value combo
		//
    	boolean isContactSetReference = CreatorEditorSettings.isContactSetReference(typeComboSelection);    		
		
		editor = new TableEditor(itemsTable);
		final CCombo valueCombo = new CCombo(itemsTable, SWT.NONE);
		valueCombo.setItems(values);
		valueCombo.setEditable(false);
		valueCombo.setBackground(getWhite());
		if(selectedValue != null){
			setValueTextAndEnabling(valueCombo, selectedValue, randomValueText, typeComboSelection);		
		}

		item.setText(1, valueCombo.getText());
			
		valueCombo.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent event) {
	        	item.setText(1, valueCombo.getText());
	        }
	      });		
	
		editor.grabHorizontal = true;
		editor.setEditor(valueCombo, item, 1);
		
		if(isContactSetReference){
    		setContextSensitiveHelpID(valueCombo, CreatorHelpContextIDs.CREATOR_HELP_CONTACT_SET);
    	}else{
    		setContextSensitiveHelpIDByComponentType(valueCombo);
    	}		
		
		return valueCombo;
	}	

	/**
	 * Get selection value for type Combo by item content (key) and itemIndex
	 * @param key for returning back if it was not empty
	 * @param itemIndex a row index just adding
	 * @param openItemType 
	 * @return a showable Item name or empty string, key if it was not empty or next value for items,
	 *  or empty string if index was over last item index 
	 */
	private String getTypeComboSelection(String key, int itemIndex, boolean openItemType) {
		if(key == null ){
			return AbstractValue.EMPTY_STRING;
		}
		else if(key.equals(AbstractValue.EMPTY_STRING) 
				&& getItemTypesAsString().length > itemIndex)
		{
			String setThisItemAsDefaultSelection = "";
			if(openItemType){
				setThisItemAsDefaultSelection = getItemTypesAsString()[itemIndex];
			}
			return setThisItemAsDefaultSelection;
		}else{
			return key;
		}
	}

	/**
	 * @return unicode Font
	 */
	protected Font getUnicodeFont() {
		Font defaultFont = itemsTable.getFont();
		FontData defaulFD [] = defaultFont.getFontData();		
		FontData fd = new FontData("Arial Unicode MS", defaulFD[0].getHeight(),  defaulFD[0].getStyle());
		return new Font(Display.getCurrent(), fd);
	}

	/**
	 * Gets 1 size smaller font for the button
	 * @return same Font with smaller size
	 */
	protected Font getButtonFont() {
		Font defaultFont = itemsTable.getFont();
		FontData defaulFD [] = defaultFont.getFontData();
		FontData fd = new FontData(defaulFD[0].getName(), defaulFD[0].getHeight()-1, defaulFD[0].getStyle());
		Font font = new Font(Display.getCurrent(), fd);		
		return font;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
	    // Creating just OK button
	    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
	            true);
	    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL,
	            true);     
	    
	}

	
	/* 
	 * Collecting Component data from table fields 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
			
		//When in edit mode, just removing all attributes and replace them with current editor values
		if(isInEditMode()){
			getComponent().removeAllAttributes();
		}else{
			//otherwise creating new component
			setComponent(createNewComponent());
		}
		
		if(itemsTable != null){
			
			TableItem [] items = itemsTable.getItems();
			
			for (int i = 0; i < items.length; i++) {
				
				TableItem item = items[i];
				
				if(item != null){
			
					String type = item.getText(0);
					String value = item.getText(1);
					
					if(type != null && type.trim().length()>0
							&& value != null && value.trim().length()>0){
						value = value.trim();//Editor will produce script with no extra spaces.
						
						AbstractValue aValue = createNewValue(type, value, item.getText(2), item.getText(3));
						
						//If field is Contact set reference
						if(CreatorEditorSettings.isContactSetReference(type)){
							
							AbstractComponent compToReference = getProvider().getComponents()
							.getComponentByComponentString(	value );
							
							aValue.setContactSetReference(true);
							aValue.setRandom(false);//contact set reference cannot be random
							int id = 0;
							try {
								id=compToReference.getId();
							} catch (Exception e) {
								id = 0;
								showWarningDialog("Contact Set not exist", "Contact Set with id: '" +value +"' doesn't exist, please use existing Contact Set IDs.");
							}
							aValue.setId(id);
							
							int maxamount = 0;
							try {
								maxamount=Integer.parseInt(item.getText(3));
							} catch (Exception e) {
								maxamount = 0;
							}
							aValue.setMaxAmount(maxamount);							
						}						
						 
						//Setting random type as user defined and set length by user definition
						else if(aValue.getModeValueText().equalsIgnoreCase(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH)){
							aValue.setModeType(ModeTypes.RandomTypeUserDefinedLength);
							int randomLen;
							try {
								randomLen = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								e.printStackTrace();
								randomLen = AbstractValue.USER_DEFINED_DEFAULT_LENGTH;
								showWarningDialog("Invalid random value", "Invalid user defined random lenght value: '" 
										+value +"'. Value was set to: '" +AbstractValue.USER_DEFINED_DEFAULT_LENGTH +"'.");
								
							}
							aValue.setRandomValueLenght(randomLen);
						}
						
						Vector<AbstractValue> v = getComponent().getAttribute(type);
						if(v == null){
							v = new Vector<AbstractValue>();
						}
						v.add(aValue);
						
						getComponent().setAttribute(type, v);
					}
				}			
			}
			
		}
			//Amount txt for component level		
			if(amoutTxtToComponent != null){
				String amount = amoutTxtToComponent.getText();
				if(amount != null && amount.trim().length() > 0){
					try {
						int am = Integer.parseInt(amount);
						getComponent().setAmount(am);		
					} catch (Exception e) {					
						//just in case take Exception for parseInt, should not be occur, because of text field data validation
						e.printStackTrace();
						getComponent().setAmount(0);
					}
				}		
			}
			
			
			//If extra field number is used
			if(extraNbrToComponent != null){
				getComponent().addAdditionalParameter(getComponent().getIdByValue(labelForExtraNumberField), extraNbrToComponent.getText());
			}
			//If extra field txt is used			
			if(extraTxtToComponent != null){
				getComponent().addAdditionalParameter(getComponent().getIdByValue(labelForExtraTextField), extraTxtToComponent.getText());				
			}
			
			//Set link to another component if any
			setLinkToOtherComponentByComboSelection();
			
			super.okPressed();
	
		}
	
	/**
	 * Show an confirmation dialog
	 * @param title
	 * @param message
	 * @return true if OK pressed, false otherwise
	 */
	protected boolean showConfirmationDialog(String title, String message) {
		Shell sh;
		if (getShell() != null) {			
			try {
				sh = getShell();
			} catch (SWTException e) {
				sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
			}
			
		} else {
			sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
		}

		return MessageDialog.openConfirm(sh, title, message);
	}	
	

	/**
	 * Show an information dialog
	 * @param title
	 * @param message
	 */
	protected void showInformationDialog(String title, String message) {
		Shell sh;
		if (getShell() != null) {			
			try {
				sh = getShell();
			} catch (SWTException e) {
				sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
			}
			
		} else {
			sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
		}

		MessageDialog.openInformation(sh, title, message);
	}
	/**
	 * Show an warning dialog
	 * @param title
	 * @param message
	 */
	protected void showWarningDialog(String title, String message) {
		Shell sh;
		if (getShell() != null) {			
			try {
				sh = getShell();
			} catch (SWTException e) {
				sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
			}
			
		} else {
			sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
		}

		MessageDialog.openWarning(sh, title, message);
	}	
	/**
	 * Show an error dialog
	 * @param title
	 * @param message
	 */
	protected void showErrorDialog(String title, String message) {
		Shell sh;
		if (getShell() != null) {			
			try {
				sh = getShell();
			} catch (SWTException e) {
				sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
			}
			
		} else {
			sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
		}

		MessageDialog.openError(sh, title, message);
	}	
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		try{
			Composite composite = createDialogAreaComposite(parent);
			GridLayout gridLayout = new GridLayout();
			composite.setLayout(gridLayout);
	
			//Create Amount area
			createAmountArea(composite);		
			
			//Create table area
			createTableArea(composite);		
			
			if(/*showAmountInfoText &&*/ isAmountFieldsEnabled){
				addInformation(composite, AMOUNT_FIELD_INFO_TEXT);
			}		
			
			if(wasErrors()){
				showErrorDialog("Errors occured when dialog opened", getErrors());
			}
			
			return composite;
		}catch(Exception e){
			e.printStackTrace();
			showUnableToOpenDialogErrorMsg(e);
		}
		return null;
	}

	/**
	 * Creates a composite for dialog area
	 * @param parent
	 * @return a Composite
	 */
	protected Composite createDialogAreaComposite(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		return composite;
	}

	/**
	 * Creating all items to dialog area, a real implementation for createDialogArea(Composite parent)
	 */
	protected void createTableArea(Composite composite) {
		final int cols = 1;	  
		GridLayout gdl = new GridLayout(cols, false);
		GridData gd = new GridData(GridData.FILL_BOTH);
		
	
		Composite tableComposite = new Composite(composite, SWT.SIMPLE);
		tableComposite.setLayout(gdl);
		tableComposite.setLayoutData(gd);
		
		itemsTable = new Table (tableComposite, SWT.BORDER | SWT.MULTI);
		GridData tableGd = new GridData(GridData.FILL_BOTH);
		tableGd.heightHint = 300;	
		itemsTable.setLayoutData(tableGd);
		itemsTable.setLinesVisible (true);
		itemsTable.setHeaderVisible(true);	
		itemsTable.setEnabled(isTableEnabled);
	
		//Create columns to table
		createTableColums();
		
		AbstractComponent component = (AbstractComponent) getComponent();
		Set<String> keys = component.getKeys();
		Vector<String> keysV = new Vector<String>(keys);
		
		
		//Create rows to table +2 is for new items
		int actualComponentItemCount = component.getAttributeCount();// +2;
		int allCompoenentItemCount = getItemTypesAsString().length;
		//Always create at least 12 rows
		if(!isInEditMode() && actualComponentItemCount < INITIAL_ITEMS_NUMBER_IN_TABLE  ){
			if(allCompoenentItemCount > INITIAL_ITEMS_NUMBER_IN_TABLE){
				actualComponentItemCount = INITIAL_ITEMS_NUMBER_IN_TABLE;
			}
			else{
				actualComponentItemCount = allCompoenentItemCount;
			}
		}
		
		
		//Looping through table and add existing data or empty rows
		for (int i=0; i<actualComponentItemCount; i++) {
			//TableEditor editor = new TableEditor (itemsTable);						
			
			//Creating empty rows if there is no data allready added at least 12 rows
			if (i >= component.getAttributeCount()){
				try {
					addRow(!isInEditMode(), false);
				} catch (Exception e) {
					handleTableRowCreationError(e);
				}
			}
			//rows of the table where is data added
			else{
				String key = (String) keysV.get(i);
				Vector<AbstractValue> values = component.getAttribute(key);
				for (Iterator<AbstractValue> iterator = values.iterator(); iterator.hasNext();) {
					AbstractValue value = (AbstractValue) iterator
							.next();
					try {
						addRow(key, value, false);
					} catch (Exception e) {
						handleTableRowCreationError(e);
					}	
					
				}
				//Adding one row to Table
						
			}//else
		}
		
		//Notify listeners to update values when all rows are set
		checkIfNeedToCallItemValueEnablationListners();
		
		//Notify table listeners to update item combos
		notifyTypeComboDataListeners();	
		
		//Create add row button
		createAddRowButton(tableComposite);
		
		if(wasErrorsWithDatas){
			showErrorDialog("Errors on script", "There was some errors when opening component, see Console for details.");
		}
	}

	/**
	 * Set wasErrorsWithDatas as true and prints console error message
	 * @param e
	 */
	private void handleTableRowCreationError(Exception e) {
		wasErrorsWithDatas  = true;
		e.printStackTrace();
		CreatorEditorConsole.getInstance().println(UNEXPECTED_ERROR_WHEN_CREATING_TABLE_AREA_ERROR_WAS +e, CreatorEditorConsole.MSG_ERROR);
	}

	/**
	 * Creates an Add row button to selected composite
	 * @param parent
	 */
	private void createAddRowButton(Composite parent) {
		addRowButton = new Button(parent, SWT.PUSH);	
		addRowButton.setText(ADD_ROW_TXT);
		//Add add row functionality
		addRowButton.addSelectionListener(new SelectionAdapter(){
			
			public void widgetSelected(SelectionEvent event) {
				try {
					addRow(true, true);
					//Notify table listeners to update item combos
					notifyTypeComboDataListeners();						
					
					//notify also listners if needed
					checkIfNeedToCallItemValueEnablationListners();					
				} catch (Exception e) {
					handleTableRowCreationError(e);					
				}
			}
			
		});
		setContextSensitiveHelpID(addRowButton, CreatorHelpContextIDs.CREATOR_HELP_MODIFY_COMPONENT);
	}

	/**
	 * Creates a button to launch "Create new Contact-set" -dialog.
	 * @param composite
	 * @param addIndent - If add some empty space before creating button |"    " <BUTTON>|
	 */
	protected void createAddNewContactSetButton(Composite composite, boolean addIndent){
		
		
		if(addIndent){
			//Adding empty labe for decoration purposes
			Label emptyText = new Label(composite, SWT.SIMPLE);
			emptyText.setBackground(getGrey());
			emptyText.setText("       ");			
		}
	
		addContactSetButton = new Button(composite, SWT.PUSH);
		addContactSetButton.setText(ContactSetVariables.ADD_CONTACT_SET_TXT);		
	
		//add button listener
		addContactSetButton.addSelectionListener(provider.getAddNewContactSetComponentListener(getShell(), provider));
		addContactSetButton.setData(linkToOtherComponentCombo);
		
		//When a new component was added, must update data in list, doing it through listener, 
		//which is provided to dialog through event.widget.data 
		addContactSetButton.setData(new AddNewContactSetButtonListener (null));
		
	}

	/**
	 * Update link list when new component was added
	 */
	private class AddNewContactSetButtonListener implements IAddComponentListener{
		
		private CCombo combo = null;


		AddNewContactSetButtonListener(CCombo combo){
			this.combo = combo;
			
		}
		

		/* (non-Javadoc)
		 * @see com.nokia.s60tools.creator.editors.IAddComponentListener#componentAdded(com.nokia.s60tools.creator.components.AbstractComponent)
		 */
		public void componentAdded(AbstractComponent comp) {
			
			if(comp != null && comp.isValid() && linkToOtherComponentCombo != null){
				String[] contactSetsAsString = getContactSetsAsString();
				//If list is empty, cant just add one just created, so setting all values
				String selection = linkToOtherComponentCombo.getText();
				linkToOtherComponentCombo.setItems(contactSetsAsString);
				if(selection != null){
					linkToOtherComponentCombo.setText(selection);
				}
				linkToOtherComponentCombo.setEnabled(true);		
				
			}
			
			
			//Woke upp also other listeners who's intressed of this event! (those who has combo!=null)
			Event event = new Event();
			event.doit = true;
			addContactSetButton.notifyListeners(SWT.SetData, event);
			
		}


		/* (non-Javadoc)
		 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
		 */
		public void handleEvent(Event event) {
			if(combo != null && event.doit){
				String text = combo.getText();
				String[] contactSetsAsString = getContactSetsAsString();
				combo.setItems(contactSetsAsString);
				int length = contactSetsAsString.length;
				if(length > MAX_ITEMS_IN_COMBO){
					length = MAX_ITEMS_IN_COMBO;
				}
				combo.setVisibleItemCount(length);
				combo.setText(text);
			}			
		}
	}

	/**
	 * Create Amount area with label and text field to add label
	 * @param parent
	 */
	protected void createAmountArea(Composite parent) {
		Composite amountComp = new Composite(parent,SWT.SIMPLE);
		amountComp.setLayout(new GridLayout(5, false));
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 45;
		amountComp.setLayoutData(gridData);
				
		createAmountAreaImpl(amountComp);
		createFulFillWithRandomDatas(amountComp);	
	}
	
	/**
	 * Create Amount area with label and text field to add label
	 * @param parent
	 * @param addCreateNewContactSetButton <code>true</code> if Botton for creating contact-set(s)
	 * will be created, <code>false</code> otherwise.
	 */	
	protected void createAmountArea(Composite parent, boolean addCreateNewContactSetButton) {
		
		if(!addCreateNewContactSetButton){
			createAmountArea(parent);
		}
		else{
			Composite amountComp = new Composite(parent,SWT.SIMPLE);
			amountComp.setLayout(new GridLayout(7, false));
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.heightHint = 45;
			amountComp.setLayoutData(gridData);
					
			createAmountAreaImpl(amountComp);
			//Create button to create new contact sets
			createAddNewContactSetButton(amountComp, true);			
			
			createFulFillWithRandomDatas(amountComp);				
		}
	}	

	/**
	 * Create Amount area with label and text field to add label.
	 * Takes real composite as parameter, not parent
	 * @param composite
	 */	
	private void createAmountAreaImpl(Composite amountComp) {
		Label amoutLb = new Label(amountComp,SWT.NONE);
		amoutLb.setText(AMOUNT_TXT);
		
		amoutTxtToComponent = new Text(amountComp, SWT.LEFT | SWT.BORDER);
		amoutTxtToComponent.setLayoutData(new GridData(AMOUNT_FIELD_LENGTH,
				SWT.DEFAULT));
		amoutTxtToComponent.setTextLimit(10);
		if(getComponent().getAmount() > 0){
			amoutTxtToComponent.setText( "" +getComponent().getAmount());
		}
		//Verify listener must be set after setting text
		amoutTxtToComponent.addVerifyListener(getNumberVerifyListener());		
		
		setContextSensitiveHelpID(amoutTxtToComponent, CreatorHelpContextIDs.CREATOR_HELP_GENERIC_COMPONENT);
		
	}

	/**
	 * Creates 
	 * @param amountComp
	 */
	private void createFulFillWithRandomDatas(Composite amountComp) {
		//
		//Fulfill items with random -functionality buttons
		//
		
		Label fulFillLb1 = new Label(amountComp,SWT.NONE);
		fulFillLb1.setText("          Set all values as random with length:");
		
		Composite radioComp = new Composite(amountComp, SWT.SIMPLE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		radioComp.setLayout(rowLayout);
		final Button selectLenDefaultBtn = new Button(radioComp, SWT.RADIO);
		selectLenDefaultBtn.setText(RANDOM_LEN_DEFAULT);
		selectLenDefaultBtn.setSelection(true);
		final Button selectLenMaxBtn = new Button(radioComp, SWT.RADIO);
		selectLenMaxBtn.setText(RANDOM_LEN_MAX);
		Button setAsRandom = new Button(amountComp, SWT.PUSH);
		setAsRandom.setText(SET_RANDOM_TXT);
		setAsRandom.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String len = AbstractVariables.RANDOM_LEN_DEFAULT_XML_VALUE;
				boolean selection = selectLenMaxBtn.getSelection();
				if(selection){
					len = AbstractVariables.RANDOM_LEN_MAX_XML_VALUE;
				}
				boolean setAllValuesAsRandom = showConfirmationDialog("Set all Values as random", "Are you sure that you want to set all values as random? All existing values will be replaced by random value with "  +len +" length.");
				if(setAllValuesAsRandom){
					setAllItemsAsRandom(selection);
				}
			}
		});
		
		setContextSensitiveHelpID(selectLenDefaultBtn, CreatorHelpContextIDs.CREATOR_HELP_GENERIC_COMPONENT);
		setContextSensitiveHelpID(selectLenMaxBtn, CreatorHelpContextIDs.CREATOR_HELP_GENERIC_COMPONENT);
		setContextSensitiveHelpID(setAsRandom, CreatorHelpContextIDs.CREATOR_HELP_GENERIC_COMPONENT);

	}
	
	/**
	 * Creates a composite and creates extra numberfield and extra text field to it
	 * @param parent
	 * @param labelForNumberField
	 * @param numberFieldValue
	 * @param labelForTextField
	 * @param textFieldValue
	 */
	protected void createNumberAndTextFields(Composite parent, String labelForNumberField, 
			int numberFieldValue, String labelForTextField, String textFieldValue){
		
		Composite comp = new Composite(parent,SWT.SIMPLE);
		comp.setLayout(new GridLayout(4, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));		
		
		createExtraNumberField(labelForNumberField, numberFieldValue, comp);
		
		createExtraTextField(labelForTextField, textFieldValue, comp);
	
	}

	/**
	 * Creates an extra text field and label to composite
	 * @param labelForTextField
	 * @param textFieldValue text value for field, or <code>null</code> if don't want any
	 * @param comp
	 */
	protected void createExtraTextField(String labelForTextField,
			String textFieldValue, Composite comp) {
		this.labelForExtraTextField = labelForTextField;		
		Label txtLb = new Label(comp,SWT.NONE);
		txtLb.setText(labelForTextField);
		
		extraTxtToComponent = new Text(comp, SWT.LEFT | SWT.BORDER);
		extraTxtToComponent.setLayoutData(new GridData(TEXT_FIELD_LENGTH,
				SWT.DEFAULT ) );
		if(textFieldValue != null){
			extraTxtToComponent.setText(textFieldValue.trim());
		}
		
		setContextSensitiveHelpIDByComponentType(extraTxtToComponent);
	}

	/**
	 * Creates an extra number field and label to composite
	 * @param labelForNumberField
	 * @param numberFieldValue number value to field, give 0 if don't want any
	 * @param comp
	 */
	protected void createExtraNumberField(String labelForNumberField,
			int numberFieldValue, Composite comp) {
		this.labelForExtraNumberField = labelForNumberField;
		Label nbrLb = new Label(comp,SWT.NONE);
		nbrLb.setText(labelForExtraNumberField);
		
		extraNbrToComponent = new Text(comp, SWT.LEFT | SWT.BORDER);
		extraNbrToComponent.setLayoutData(new GridData(AMOUNT_FIELD_LENGTH,
				SWT.DEFAULT));
		extraNbrToComponent.setTextLimit(10);
		if(numberFieldValue > 0){
			extraNbrToComponent.setText( "" +numberFieldValue);
		}
		//Verify listener must be set after setting text
		extraNbrToComponent.addVerifyListener(getNumberVerifyListener());
		setContextSensitiveHelpIDByComponentType(extraNbrToComponent);
	}
	
	/**
	 * Create Amount area with label and text field to add label
	 * @param parent
	 */	
	protected void createAmountAreaAndLinkToOtherComponentCombo(Composite parent, String comboLabel,
			String[] contentToComponentLinkCombo, String contactSetSelection, 
			boolean addCreateNewContactSetButton, boolean createFullFillWithRandom) {
		if(addCreateNewContactSetButton){
			createAmountAreaAndLinkToOtherComponentComboWithCreateButton(
					parent, comboLabel, contentToComponentLinkCombo, contactSetSelection, 
					createFullFillWithRandom);
		}else{
			createAmountAreaAndLinkToOtherComponentCombo(
					parent, comboLabel, contentToComponentLinkCombo, contactSetSelection,
					createFullFillWithRandom);
		}
		if(getComponent()  instanceof Contact) {
			setContextSensitiveHelpID(linkToOtherComponentCombo, CreatorHelpContextIDs.CREATOR_HELP_CONTACT_SET );			
		}else{
			setContextSensitiveHelpIDByComponentType(linkToOtherComponentCombo);
		}	
	}
	/**
	 * Create Amount area with label and text field to add label
	 * @param parent
	 */	
	private void createAmountAreaAndLinkToOtherComponentCombo(Composite parent, String comboLabel,
			String[] contentToComponentLinkCombo, String contactSetSelection, 
			boolean createFullFillWithRandom) {
		Composite amountComp = new Composite(parent,SWT.SIMPLE);
		int numColumns = 7;

		amountComp.setLayout(new GridLayout(numColumns, false));
		amountComp.setLayoutData(new GridData(GridData.FILL_BOTH));		
		
		createAmountAreaImpl(amountComp);

		createLinkToOtherComponentPart(amountComp, comboLabel, contentToComponentLinkCombo,
				contactSetSelection, false);
		
		if(createFullFillWithRandom){
			createFulFillWithRandomDatas(amountComp);
		}
		
		
	}
	/**
	 * Create Amount area with label and text field to add label
	 * @param parent
	 */	
	private void createAmountAreaAndLinkToOtherComponentComboWithCreateButton(Composite parent, String comboLabel,
			String[] contentToComponentLinkCombo, String contactSetSelection, 
			boolean createFullFillWithRandom) {
		Composite amountComp = new Composite(parent,SWT.SIMPLE);
		int numColumns = 5;
		amountComp.setLayout(new GridLayout(numColumns, false));
		amountComp.setLayoutData(new GridData(GridData.FILL_BOTH));		
		
		createAmountAreaImpl(amountComp);
		if(createFullFillWithRandom){
			createFulFillWithRandomDatas(amountComp);
		}

		createLinkToOtherComponentPart(parent, comboLabel, contentToComponentLinkCombo,
					contactSetSelection, true);			
		
	}	

	protected void createLinkToOtherComponentPart(Composite parent, String comboLabel,
			String[] contentToComponentLinkCombo, String contactSetSelection,
			boolean addCreateNewContactSetButton) {

		Composite linkComp = new Composite(parent,SWT.SIMPLE);
		int numColumns = 2;
		if(addCreateNewContactSetButton){
			numColumns ++;
		}

		
		linkComp.setLayout(new GridLayout(numColumns, false));
		linkComp.setLayoutData(new GridData(GridData.FILL_BOTH));			
		//
		// Create link to other component
		//			
		
		Label setLb = new Label(linkComp,SWT.NONE);
		setLb.setText(comboLabel);
		
		linkToOtherComponentCombo  = new CCombo(linkComp, SWT.READ_ONLY | SWT.BORDER );
		linkToOtherComponentCombo .setBackground(getWhite());
					
		//If there is some values, setting them to combo
		if(contentToComponentLinkCombo != null && contentToComponentLinkCombo.length > 0){
			setComponentsToOtherComponentLinkCombo(contentToComponentLinkCombo);
		}//Otherwise combo will be disabled
		else{
			linkToOtherComponentCombo .setEnabled(false);
		}

		
		//If we want to create button for add new contact-set
		if(addCreateNewContactSetButton){
			//Create button for creating new contact-sets
			createAddNewContactSetButton(linkComp, false);
		}
		
		
		if(contactSetSelection != null){
			setComboSelection(contentToComponentLinkCombo, linkToOtherComponentCombo, contactSetSelection);
		}
	}


	/**
	 * Update items to other component link -Combo
	 * @param contentToComponentLinkCombo
	 */
	protected void setComponentsToOtherComponentLinkCombo(
			String[] contentToComponentLinkCombo) {
		if(linkToOtherComponentCombo != null){
			linkToOtherComponentCombo.setItems(contentToComponentLinkCombo);
		}
	}	
	
	/**
	 * Set all items in table as random.
	 * @param setAsMaxLength
	 */
	private void setAllItemsAsRandom(boolean setAsMaxLength) {

		TableItem[] items = itemsTable.getItems();

		//Looping through all items in table
		for (int i = 0; i < items.length; i++) {

			TableItem item = items[i];

			if (item != null) {
				Event e = new SetRandomEvent();
				e.doit = true;
				item.notifyListeners(SWT.Modify, e);
				
				String key = item.getText(0);

				//When item is contact set reference, it cannot be random, or if random fields is disabled, they cannot be set as random.
				if(isRandomFieldsEnabled() && !CreatorEditorSettings.isContactSetReference(key) && getComponent().getVariables().isModeEnabledForKey(key)){										
					//When there is a key, setting value as random
					if (key != null && key.trim().length() > 0) {
						//setting value as max or default
						if(setAsMaxLength){
							item.setText(1, AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG);
							item.setText(2, AbstractValue.RANDOM_TEXT_MAX_LENGTH);
							
						}
						else{
							item.setText(1, AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG);
							item.setText(2, AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH);
						}
						//Notify listeners by self
						item.notifyListeners(SWT.Modify, e);
						
					}
				}
			}
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
	    super.configureShell(shell);
	    if(isInEditMode()){
	    	shell.setText("Edit " +getComponent().getType() +"(s)");        	
	    }else{
	    	shell.setText("Add " +getComponent().getType() +"(s)");
	    }
	}	
	
	/**
	 * Adds an information label to view
	 * @param txt
	 */
	protected void addInformation(Composite composite, String txt){
		Composite infoComp = new Composite(composite,SWT.SIMPLE);
		infoComp.setLayout(new GridLayout(1, false));
		infoComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		GridData gd = new GridData(CreatorScriptEditor.EDITOR_DEFAULT_WIDTH, SWT.DEFAULT);
		Label info = new Label(infoComp,SWT.WRAP );
		info.setLayoutData(gd);
		info.setText(txt);		
	}

	/**
	 * Get is amount fields enabled or not
	 * @return true if is enabled
	 */
	protected boolean isAmountFieldsEnabled() {
		return isAmountFieldsEnabled;
	}

	/**
	 * Set amount fields enable or disable in table 
	 * @param isAmountFieldsEnabled
	 */
	protected void setAmountFieldsEnabled(boolean isAmountFieldsEnabled) {
		this.isAmountFieldsEnabled = isAmountFieldsEnabled;
	}
	
	/**
	 * Sets table enabled or disabled, default is enabled
	 * @param b
	 */
	protected void setTableEnabled(boolean isTableEnabled) {
		this.isTableEnabled = isTableEnabled;
	}
	


	/**
	 * Get {@link IComponentProvider} interface
	 * @return interface to provide component services
	 */
	protected IComponentProvider getProvider() {
		return provider;
	}

	/**
	 * Get selection text from other component selection Combo
	 * @return selection text, or null if not created
	 */
	private String getLinkToOtherComponentComboSelection() {
		
		String txt = null;
		if(linkToOtherComponentCombo != null){
			txt = linkToOtherComponentCombo.getText();
		}
		return txt;
	}

	/**
	 * Sets link to another component
	 */
	private void setLinkToOtherComponentByComboSelection() { 
		
		//text from combo
		String contactSetLinkText = getLinkToOtherComponentComboSelection();
		if(contactSetLinkText != null && contactSetLinkText.trim().length() >0){
		
			//future improvement idea, "linkToOtherComponentCombo" is not the best name for additional component level data combo
			//also better way to handle data from that is to let real Dialog implementation to handle it.
			
			//If component is mailbox, linktoanother component is actually type
			if(getComponent() instanceof MailBox){
				MailBox box = (MailBox) getComponent();
				box.setMailBoxType(contactSetLinkText.trim());
			}
			else{
				//get a component which the reference points
				AbstractComponent compToReference = getProvider().getComponents()
					.getComponentByComponentString(	contactSetLinkText);
				//set reference to this component
				getComponent().setReferenceToAnotherComponent(compToReference);

				
			}
		}
		//There is possibility that contact set reference is removed, and thats why must remove existing reference
		else{
			getComponent().setReferenceToAnotherComponent(null);			
		}

	}

	/**
	 * Get Contact Sets as String
	 * @return contact sets
	 */
	protected String[] getContactSetsAsString() {
		
		Vector<AbstractComponent> contSets = getProvider().getComponents().getComponents(CreatorEditorSettings.TYPE_CONTACT_SET);
	
		String[] arr = null;
		if(contSets != null && contSets.size() > 0){
			arr = new String[contSets.size()+1];
			int i = 1;
			arr[0]  = AbstractValue.EMPTY_STRING;//To be able to reset selection
			for (Iterator<AbstractComponent> iterator = contSets.iterator(); iterator.hasNext();) {
				AbstractComponent comp = (AbstractComponent) iterator
						.next();
				arr[i] = comp.toString();
				i++;
			}
		}
		return arr;
	}
	
	/**
	 * Get Contact Set as String or <code>null</code> if not found
	 * @param contact set id
	 * @return Get Contact Set as String or <code>null</code> if not found
	 */
	protected String getContactSetStringById(String id) {
		try {
			int idInt = Integer.parseInt(id);
			return getContactSetStringById(idInt);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get Contact Set as String or <code>null</code> if not found
	 * @param contact set id
	 * @return Get Contact Set as String or <code>null</code> if not found
	 */
	protected String getContactSetStringById(int id) {
		
		Vector<AbstractComponent> contSets = getProvider().getComponents().getComponents(CreatorEditorSettings.TYPE_CONTACT_SET);
	
			for (Iterator<AbstractComponent> iterator = contSets.iterator(); iterator.hasNext();) {
				AbstractComponent comp = (AbstractComponent) iterator
						.next();
				if(comp.getId() == id){
					return comp.toString();
				}
			}
		return null;
	}	

	/**
	 * Set amount of table column headers if not default value
	 * @param tableColumnHeaderAmount
	 */
	protected void setTableColumnHeaderAmount(String tableColumnHeaderAmount) {
		this.tableColumnHeaderAmount = tableColumnHeaderAmount;
	}

	/**
	 * Greate link to another component combo
	 * @param parent
	 * @param contentToComponentLinkCombo
	 * @param contactSetSelection
	 */
	protected void createLinkToOtherComponentCombo(Composite parent, String[] contentToComponentLinkCombo,
			String comboLabel,
			String contactSetSelection) {

		Composite comp = new Composite(parent,SWT.SIMPLE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));		
				
		Label setLb = new Label(comp,SWT.NONE);
		setLb.setText(comboLabel);
		
		linkToOtherComponentCombo  = new CCombo(comp, SWT.READ_ONLY | SWT.BORDER );
		linkToOtherComponentCombo .setBackground(getWhite());
					
		//If there is some values, setting them to combo
		if(contentToComponentLinkCombo != null && contentToComponentLinkCombo.length > 0){
			setComponentsToOtherComponentLinkCombo(contentToComponentLinkCombo);
		}//Otherwise combo will be disabled
		else{
			linkToOtherComponentCombo .setEnabled(false);
		}
		
		if(contactSetSelection != null){
			setComboSelection(contentToComponentLinkCombo, linkToOtherComponentCombo, contactSetSelection);
		}
				
	}

	/**
	 * Check if random fields are enabled for this dialog
	 * @return <code>true</code> if random fields are enabled <code>false</code> othrewise.
	 */
	protected boolean isRandomFieldsEnabled() {
		return isRandomFieldsEnabled;
	}

	/**
	 * Set if random fields are enabled for this dialog
	 * @param isRandomFieldsEnabled <code>true</code> if random fields are enabled <code>false</code> othrewise.
	 */
	protected void setRandomFieldsEnabled(boolean isRandomFieldsEnabled) {
		this.isRandomFieldsEnabled = isRandomFieldsEnabled;
	}	


	/***
	 * Get Text for contact-set reference check box
	 * @return UI text for associate this component to contact-set
	 */
	protected String getContactSetRefernceText(){
		return "Associate this " +getComponent().getType() +" to Contact-set: ";

	}

	/**
	 * Show error message dialog when dialog was not able to open
	 * @param e {@link Exception} which was thrown
	 */
	protected void showUnableToOpenDialogErrorMsg(Exception e) {
		showErrorDialog("Error", "Errors occurded when editor dialog was opened, see console for details.");
		CreatorEditorConsole.getInstance().println("Errors occurded when try to open editor dialog for compoent:'" 
				+getComponent().getType() + "', reason: " +e, CreatorEditorConsole.MSG_ERROR);
		
	}
	
	/**
	 * Add an error
	 * @param errorMsg
	 */
	protected void addError(String errorMsg){
		if(errors == null){
			errors = new Vector<String>();
		}
		errors.add(errorMsg);
		
	}
	
	/**
	 * Get all errors, one error will be its own line separeted with "\n"
	 * @return errors occurred when dialog was opened
	 */
	protected String getErrors(){
		if(errors == null){
			return "";
		}else{
			StringBuffer b = new StringBuffer();
			String newLine = "\n";
			for (Iterator<String> iterator = errors.iterator(); iterator.hasNext();) {
				String err= (String) iterator.next();
				b.append(err);
				b.append(newLine);
			}
			//remove last new line
			b.delete(b.length()-newLine.length(), b.length());
			return b.toString();
		}
	}
	
	/**
	 * Was there any errors when creating dialog
	 * @return <code>true</code> if there was some errors, e.g. unable to set value to combo
	 */
	protected boolean wasErrors(){
		return errors != null;
	}
	
}
