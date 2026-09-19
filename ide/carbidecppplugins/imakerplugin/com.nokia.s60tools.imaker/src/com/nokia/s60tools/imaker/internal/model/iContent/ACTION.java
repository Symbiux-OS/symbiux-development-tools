/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.s60tools.imaker.internal.model.iContent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>ACTION</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getACTION()
 * @model
 * @generated
 */
public enum ACTION implements Enumerator {
	/**
	 * The '<em><b>UDEB</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UDEB_VALUE
	 * @generated
	 * @ordered
	 */
	UDEB(0, "UDEB", "udeb"), /**
	 * The '<em><b>REMOVE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REMOVE_VALUE
	 * @generated
	 * @ordered
	 */
	REMOVE(1, "REMOVE", "remove"), /**
	 * The '<em><b>HIDE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HIDE_VALUE
	 * @generated
	 * @ordered
	 */
	HIDE(2, "HIDE", "hide"),

	/**
	 * The '<em><b>UDEB ADD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UDEB_ADD_VALUE
	 * @generated
	 * @ordered
	 */
	UDEB_ADD(3, "UDEB_ADD", "udeb-add"),

	/**
	 * The '<em><b>UREL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UREL_VALUE
	 * @generated
	 * @ordered
	 */
	UREL(4, "UREL", "urel"),

	/**
	 * The '<em><b>UREL ADD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UREL_ADD_VALUE
	 * @generated
	 * @ordered
	 */
	UREL_ADD(5, "UREL_ADD", "urel-add"),

	/**
	 * The '<em><b>REPLACE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLACE_VALUE
	 * @generated
	 * @ordered
	 */
	REPLACE(6, "REPLACE", "replace"),

	/**
	 * The '<em><b>REPLACE ADD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLACE_ADD_VALUE
	 * @generated
	 * @ordered
	 */
	REPLACE_ADD(7, "REPLACE_ADD", "replace-add");

	/**
	 * The '<em><b>UDEB</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UDEB</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UDEB
	 * @model literal="udeb"
	 * @generated
	 * @ordered
	 */
	public static final int UDEB_VALUE = 0;

	/**
	 * The '<em><b>REMOVE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REMOVE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REMOVE
	 * @model literal="remove"
	 * @generated
	 * @ordered
	 */
	public static final int REMOVE_VALUE = 1;

	/**
	 * The '<em><b>HIDE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HIDE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HIDE
	 * @model literal="hide"
	 * @generated
	 * @ordered
	 */
	public static final int HIDE_VALUE = 2;

	/**
	 * The '<em><b>UDEB ADD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UDEB ADD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UDEB_ADD
	 * @model literal="udeb-add"
	 * @generated
	 * @ordered
	 */
	public static final int UDEB_ADD_VALUE = 3;

	/**
	 * The '<em><b>UREL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UREL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UREL
	 * @model literal="urel"
	 * @generated
	 * @ordered
	 */
	public static final int UREL_VALUE = 4;

	/**
	 * The '<em><b>UREL ADD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UREL ADD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UREL_ADD
	 * @model literal="urel-add"
	 * @generated
	 * @ordered
	 */
	public static final int UREL_ADD_VALUE = 5;

	/**
	 * The '<em><b>REPLACE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REPLACE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLACE
	 * @model literal="replace"
	 * @generated
	 * @ordered
	 */
	public static final int REPLACE_VALUE = 6;

	/**
	 * The '<em><b>REPLACE ADD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REPLACE ADD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLACE_ADD
	 * @model literal="replace-add"
	 * @generated
	 * @ordered
	 */
	public static final int REPLACE_ADD_VALUE = 7;

	/**
	 * An array of all the '<em><b>ACTION</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ACTION[] VALUES_ARRAY =
		new ACTION[] {
			UDEB,
			REMOVE,
			HIDE,
			UDEB_ADD,
			UREL,
			UREL_ADD,
			REPLACE,
			REPLACE_ADD,
		};

	/**
	 * A public read-only list of all the '<em><b>ACTION</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ACTION> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>ACTION</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ACTION get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ACTION result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ACTION</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ACTION getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ACTION result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ACTION</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ACTION get(int value) {
		switch (value) {
			case UDEB_VALUE: return UDEB;
			case REMOVE_VALUE: return REMOVE;
			case HIDE_VALUE: return HIDE;
			case UDEB_ADD_VALUE: return UDEB_ADD;
			case UREL_VALUE: return UREL;
			case UREL_ADD_VALUE: return UREL_ADD;
			case REPLACE_VALUE: return REPLACE;
			case REPLACE_ADD_VALUE: return REPLACE_ADD;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ACTION(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ACTION
