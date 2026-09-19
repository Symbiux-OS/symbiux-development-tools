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
 
package com.nokia.s60tools.creator.components.bookmark;

import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * One row of data of bookmark
 */
public class BookmarkValue extends AbstractValue {

	/**
	 * Creates new Value.
	 * @param value
	 * @param randomType
	 * @param amount
	 */
	public BookmarkValue(String value, ModeTypes randomType, int amount) {
		this();
		setValue(value);
		setModeType(randomType);
		setAmount(amount);
	}
	
	/**
	 * Creates new Value.
	 * @param value
	 * @param amount
	 */
	public BookmarkValue(String value, int amount) {
		this();
		setValue(value);
		setModeType(ModeTypes.RandomTypeNotRandom);
		setAmount(amount);
	}


	/**
	 * Creates new Value.
	 * 
	 * @param value
	 * @param isRandom if false, random type is set to {@link ModeTypes#RandomTypeNotRandom}
	 * if true random type is set to {@link ModeTypes#RandomTypeDefaultLength}
	 * @param amount
	 */
	public BookmarkValue(String value, boolean isRandom, int amount) {
		this();
		if(!isRandom){
			setModeType(ModeTypes.RandomTypeNotRandom);			
		}
		else{
			setModeType(ModeTypes.RandomTypeDefaultLength);
		}
		setValue(value);
		setAmount(amount);
	}
	
	/**
	 * Creates new Value with no random and no amount.
	 * random value is set to {@link ModeTypes#RandomTypeNotRandom} and
	 * amount is set to 0.
	 * @param value
	 */
	public BookmarkValue(String value) {
		this();
		setValue(value);
		setModeType(ModeTypes.RandomTypeNotRandom);
		setAmount(0);
	}	
	
	/**
	 * Creates new Value.
	 * Attributes is set by default;
	 *  - random value is set to {@link ModeTypes#RandomTypeDefaultLength}
	 *  - random is set to true
	 *  - value is set to EMPTY_STRING 
	 *  - amount is set to 0.
	 */
	public BookmarkValue() {
		super(CreatorEditorSettings.TYPE_BOOKMARK);
	}	

}
