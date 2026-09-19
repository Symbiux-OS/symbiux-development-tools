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

import java.util.LinkedHashMap;
import java.util.Map;

import com.nokia.s60tools.creator.core.CreatorEditorSettings;



/**
 * Abstract class for all Values, e.g. one contact can have n. values for field phone number.
 */

public abstract class AbstractValue {
	
	/**
	 * Empty value for int
	 */
	public static final int NULL_VALUE = 0;
	/**
	 * Empty string
	 */
	public static final String EMPTY_STRING = "";
	/**
	 * UI text for random value 
	 */
	public static final String RANDOM_TEXT = "<RND>";
	
	/**
	 * UI text for edit mode
	 */
	public static final String RANDOM_TEXT_NOT_RANDOM = "Edit mode";

	/**
	 * UI text for incvalueforeachcopy XML value
	 * @see AbstractComponent#INCVALUEFOREACHCOPY_PARAMETER_ID
	 */
	public static final String RANDOM_TEXT_INC_FOR_EACH_COPY = "Increase for each copy";// incvalueforeachcopy"
	/**
	 * Short UI text for incvalueforeachcopy XML value
	 */
	public static final String RANDOM_TEXT_INC_FOR_EACH_COPY_SHORT = "(Inc=true)";// incvalueforeachcopy"
	/**
	 * UI text for random max length
	 */
	public static final String RANDOM_TEXT_MAX_LENGTH = "Random - Max length";
	/**
	 * UI text for random default length
	 */
	public static final String RANDOM_TEXT_DEFAULT_LENGTH = "Random - Default length";
	/**
	 * UI text for random custom length
	 */
	public static final String RANDOM_TEXT_USER_DEFINED_LENGTH = "Random - Custom length";
	/**
	 * Default value for user defined length for custom random length
	 */
	public static final int USER_DEFINED_DEFAULT_LENGTH = 100;

	/**
	 * Long UI text for random max length
	 */
	public static final String RANDOM_TEXT_MAX_LENGTH_LONG = "Random value, max length";
	/**
	 * Long UI text for random custom length
	 */
	public static final String RANDOM_TEXT_DEFAULT_LENGTH_LONG = "Random value, default length";
	/**
	 * Long  UI text for random custom length
	 */
	public static final String RANDOM_TEXT_USER_DEFINED_LENGTH_LONG = "Random value, custom length";

	
	/**
	 * Value
	 */
	private String value;
	/**
	 * Type
	 */
	private String type;
	/**
	 * Is this value random
	 */
	private boolean isRandom;
	/**
	 * Amount of this value
	 */
	private int amount;
	/**
	 * ID of this value
	 */
	private int id;
	/**
	 * Max amount of this value
	 */
	private int maxAmount;
	/**
	 * Random type of this value
	 */
	private ModeTypes randomType;
	/**
	 * Custom random value length of this value
	 */
	private int randomValueLength;
	/**
	 * Is this value a contact set reference
	 */
	private boolean isContactSetReference = false;
	
	/**
	 * If component has additional parameters, like type
	 */
	private Map<String, String>additionalParameters = null;	
	
	/**
	 * Enumeration for different mode types
	 */
	public enum ModeTypes{
		RandomTypeNotRandom,
		RandomTypeDefaultLength,
		RandomTypeMaxLength,
		RandomTypeUserDefinedLength,
		ModeTypeIncValueForEachCopy
	}
	
	@SuppressWarnings("unused")
	private AbstractValue(){
		//No empty constructor always have to have type for value
	}
	
	/**
	 * Creates new Value.
	 * Attributes is set by default;
	 *  - random value is set to RandomTypes.RandomTypeNormalLenght
	 *  - random is set to true
	 *  - value is set to EMPTY_STRING 
	 *  - amount is set to 0.
	 */
	protected AbstractValue(String type){
		setType(type);
		setValue(EMPTY_STRING);
		setRandom(true);
		setModeType(ModeTypes.RandomTypeDefaultLength);
		setAmount(NULL_VALUE);
		setRandomValueLenght(NULL_VALUE);
		setId(NULL_VALUE);
		setMaxAmount(NULL_VALUE);
	}
	
	/**
	 * Get String values for random value selection, used e.g. as CCombo values
	 * 
	 * @return random values
	 */
	public static String[] getModeValues(){
		String [] values = new String[]{
				RANDOM_TEXT_NOT_RANDOM, 
				RANDOM_TEXT_DEFAULT_LENGTH, 
				RANDOM_TEXT_MAX_LENGTH,
				RANDOM_TEXT_USER_DEFINED_LENGTH};
		return values;
	}
	/**
	 * Get String values for random value selection, used e.g. as CCombo values
	 * 
	 * @return random values
	 */
	public static String[] getModeValuesForFixedValues(){
		String [] values = new String[]{
				RANDOM_TEXT_NOT_RANDOM, 
				RANDOM_TEXT_DEFAULT_LENGTH};
		return values;
	}	
	
	/**
	 * Get String values for random value selection, used e.g. as CCombo values
	 * 
	 * @return random values
	 */
	public static String[] getModeValuesForSupportingIncValueForeEachCopy(){
		String [] values = new String[]{
				RANDOM_TEXT_NOT_RANDOM, 
				RANDOM_TEXT_DEFAULT_LENGTH, 
				RANDOM_TEXT_MAX_LENGTH,
				RANDOM_TEXT_USER_DEFINED_LENGTH,		
				RANDOM_TEXT_INC_FOR_EACH_COPY
				};
		return values;
	}		

	/**
	 * get value
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value, will remove forbidden characters for XML content by using
	 * CreatorXML.removeForbiddenChars(String)
	 * @param value
	 */
	public void setValue(String value) {
		this.value = CreatorEditorSettings.getInstance().replaceForbiddenChars(value);
	}

	/**
	 * Is this value random or not
	 * @return true if it is a random value
	 */
	public boolean isRandom() {
		return isRandom;
	}

	/**
	 * Setting this value as random or not random. 
	 * @param isRandom if false also sets this.randomType = RandomTypes.RandomTypeNotRandom
	 */
	public void setRandom(boolean isRandom) {
		this.isRandom = isRandom;
		if(!isRandom){
			this.randomType = ModeTypes.RandomTypeNotRandom;
		}
	}

	/**
	 * Get amout of this value
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Set amount of this value
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Get random type of this value
	 * @return randomtype
	 */
	public ModeTypes getModeType() {
		return randomType;
	}

	/**
	 * Set random type. 
	 * @param modeType if RandomTypes.RandomTypeNotRandom given
	 * also value set to not random, otherwise also set to random.
	 */
	public void setModeType(ModeTypes modeType) {
		this.randomType = modeType;
		if(modeType == ModeTypes.RandomTypeNotRandom || modeType == ModeTypes.ModeTypeIncValueForEachCopy){
			this.isRandom = false;
		}
		else{
			this.isRandom = true;
		}
	}
	
	/**
	 * Get random value text by this value random type
	 * @return text to show in UI
	 */
	public String getModeValueText(){
		String txt ;
		switch (getModeType()) {
		case RandomTypeNotRandom:
			txt = RANDOM_TEXT_NOT_RANDOM;
			break;
		case RandomTypeDefaultLength:
			txt = RANDOM_TEXT_DEFAULT_LENGTH;
			break;
		case RandomTypeMaxLength:
			txt = RANDOM_TEXT_MAX_LENGTH;
			break;
		case RandomTypeUserDefinedLength:
			txt = RANDOM_TEXT_USER_DEFINED_LENGTH;
			break;
		case ModeTypeIncValueForEachCopy:
			txt = RANDOM_TEXT_INC_FOR_EACH_COPY;
			break;				
		default:
			txt = EMPTY_STRING;
			break;
		}
		return txt;
	}

	/**
	 * Get random type by random type text
	 * @param mode
	 * @return random type
	 */
	public static ModeTypes getModeTypeByText(String mode) {
		
		if(mode == null || mode.equals(RANDOM_TEXT_NOT_RANDOM)){
			return ModeTypes.RandomTypeNotRandom;
		}
		else if(mode.equals(RANDOM_TEXT_DEFAULT_LENGTH) || mode.equals(RANDOM_TEXT_DEFAULT_LENGTH_LONG) ){
			return ModeTypes.RandomTypeDefaultLength;
		}
		else if(mode.equals(RANDOM_TEXT_MAX_LENGTH) || mode.equals(RANDOM_TEXT_MAX_LENGTH_LONG) ){
				return ModeTypes.RandomTypeMaxLength;
		}
		else if(mode.equals(RANDOM_TEXT_USER_DEFINED_LENGTH) || mode.equals(RANDOM_TEXT_USER_DEFINED_LENGTH_LONG)){
			return ModeTypes.RandomTypeUserDefinedLength;
		}		
		else if(mode.equals(RANDOM_TEXT_INC_FOR_EACH_COPY)){
			return ModeTypes.ModeTypeIncValueForEachCopy;
		}				
		else{
			return ModeTypes.RandomTypeNotRandom;
		}
		
	}

	/**
	 * Get user defined value for random length
	 * @return random value length
	 */
	public int getRandomValueLenght() {
		return randomValueLength;
	}

	/**
	 * Setting user defined length to random value
	 * @param randomValueLenght if set to > 0 random type is also set to RandomTypes.RandomTypeUserSetLength
	 * if set to <= random type is also set to RandomTypes.RandomTypeDefaultLenght
	 */
	public void setRandomValueLenght(int randomValueLenght) {
		this.randomValueLength = randomValueLenght;
		if(randomValueLenght > 0){
			setModeType(ModeTypes.RandomTypeUserDefinedLength);
		}else{
			//value len cannot be 0, so setting default len instead
			setModeType(ModeTypes.RandomTypeDefaultLength);
		}
	}

	
	/**
	 * Get type
	 * @return type of this value
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get ID
	 * @return id of this value
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set ID
	 * @param id of this value
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get max amount
	 * @return maxAmount of this value
	 */
	public int getMaxAmount() {
		return maxAmount;
	}

	/**
	 * Set Max amount
	 * @param maxAmount of this value
	 */
	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * Set this value to contact set reference
	 * @param isContactSetReference <code>true</code> if this value is contact set reference
	 * <code>false</code> otherwise.
	 */
	public void setContactSetReference(boolean isContactSetReference) {
		this.isContactSetReference  = isContactSetReference;
		
	}

	/**
	 * Is this value a contact set referece
	 * @return <code>true</code> if its a contact set reference, <code>false</code> otherwise.
	 */
	public boolean isContactSetReference() {
		return isContactSetReference;
	}

	/**
	 * Get additional parameters
	 * @return additionalParameters
	 */
	public Map<String, String> getAdditionalParameters() {
		if(additionalParameters == null){
			additionalParameters = new LinkedHashMap<String, String>();
		}		
		return additionalParameters;
	}


	/**
	 * Set additional parameters
	 * @param additionalParameters
	 */
	public void setAdditionalParameters(Map<String, String> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}
	

	/**
	 * Add one additional parameter
	 * @param type
	 * @param value
	 */
	public void addAdditionalParameter(String type, String value) {
		getAdditionalParameters().put(type, CreatorEditorSettings.getInstance().replaceForbiddenChars(value));
	}
	
	/**
	 * Get additional parameter
	 * @param type
	 * @return value, or null if not exist
	 */
	public String getAdditionalParameter(String type) {
		return getAdditionalParameters().get(type);
	}	
	
	/**
	 * Does this component have some additional parameters
	 * @return true if this component has some additional parameter, false otherwise
	 */
	public boolean hasAdditionalParameters(){
		return additionalParameters != null && getAdditionalParameters().size() > 0 ? true : false;
	}

	/**
	 * Removes all additional parameters
	 * Note: attributes is not going to be removed. Use {@link AbstractComponent#removeAllAttributes()}
	 */
	public void removeAdditionalParameters() {
		additionalParameters = new LinkedHashMap<String, String>();
	}


}
