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

import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * Component type representing unknown component level element in XML Script.
 * E.g. In XML, there was component, added by user manually (without Creator Script Editor)
 * that is not supported with Creator Script Editor. 
 * 
 * This component type exist, because of error messages can be then full filled with detailed information. 
 */
public class UnknownCompoment extends AbstractComponent {

	/**
	 * Construction
	 * @param id of component
	 */
	protected UnknownCompoment(int id) {
		super(id);
	}

	/**
	 * Not implemented
	 */
	public String getIdByValue(String value) {
		return value;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getType()
	 */
	public String getType() {
		return CreatorEditorSettings.TYPE_UNKNOWN;
	}

	/**
	 * Not implemented
	 */
	public String getValueById(String id) {
		return id;
	}

	/**
	 * Not implemented
	 */
	public String getXMLElementName() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public boolean isValid() {
		return false;
	}


	/**
	 * Not implemented
	 */
	public void updateEntryTypeSpecificDataFields(
			AbstractComponent entryWithUpdatedData) {
	}

	/* Returns allways null.
	 * (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getValuesForItemType(java.lang.String)
	 */
	public String[] getValuesForItemType(String itemType) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getVariables()
	 */
	public AbstractVariables getVariables(){
		return null;
	}
}
