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
 * A representation of the literals of the enumeration '<em><b>IMAGESECTION</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIMAGESECTION()
 * @model
 * @generated
 */
public enum IMAGESECTION implements Enumerator {
	/**
	 * The '<em><b>CORE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORE_VALUE
	 * @generated
	 * @ordered
	 */
	CORE(0, "CORE", "core"),

	/**
	 * The '<em><b>ROFS2</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROFS2_VALUE
	 * @generated
	 * @ordered
	 */
	ROFS2(1, "ROFS2", "rofs2"),

	/**
	 * The '<em><b>ROFS3</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROFS3_VALUE
	 * @generated
	 * @ordered
	 */
	ROFS3(2, "ROFS3", "rofs3"), /**
	 * The '<em><b>ANY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ANY_VALUE
	 * @generated
	 * @ordered
	 */
	ANY(3, "ANY", "*");

	/**
	 * The '<em><b>CORE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CORE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CORE
	 * @model literal="core"
	 * @generated
	 * @ordered
	 */
	public static final int CORE_VALUE = 0;

	/**
	 * The '<em><b>ROFS2</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ROFS2</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROFS2
	 * @model literal="rofs2"
	 * @generated
	 * @ordered
	 */
	public static final int ROFS2_VALUE = 1;

	/**
	 * The '<em><b>ROFS3</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ROFS3</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROFS3
	 * @model literal="rofs3"
	 * @generated
	 * @ordered
	 */
	public static final int ROFS3_VALUE = 2;

	/**
	 * The '<em><b>ANY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ANY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ANY
	 * @model literal="*"
	 * @generated
	 * @ordered
	 */
	public static final int ANY_VALUE = 3;

	/**
	 * An array of all the '<em><b>IMAGESECTION</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final IMAGESECTION[] VALUES_ARRAY =
		new IMAGESECTION[] {
			CORE,
			ROFS2,
			ROFS3,
			ANY,
		};

	/**
	 * A public read-only list of all the '<em><b>IMAGESECTION</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<IMAGESECTION> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>IMAGESECTION</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public static IMAGESECTION get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IMAGESECTION result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IMAGESECTION</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IMAGESECTION getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IMAGESECTION result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IMAGESECTION</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IMAGESECTION get(int value) {
		switch (value) {
			case CORE_VALUE: return CORE;
			case ROFS2_VALUE: return ROFS2;
			case ROFS3_VALUE: return ROFS3;
			case ANY_VALUE: return ANY;
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
	private IMAGESECTION(int value, String name, String literal) {
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
	
} //IMAGESECTION
