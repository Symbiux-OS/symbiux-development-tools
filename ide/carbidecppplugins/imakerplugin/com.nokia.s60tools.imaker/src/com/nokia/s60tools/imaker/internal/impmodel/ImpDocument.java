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

package com.nokia.s60tools.imaker.internal.impmodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Imp Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getOrideFiles <em>Oride Files</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getOrideConfs <em>Oride Confs</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getVariables <em>Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage#getImpDocument()
 * @model
 * @generated
 */
public interface ImpDocument extends CommentContainer {
	/**
	 * Returns the value of the '<em><b>Oride Files</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oride Files</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oride Files</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage#getImpDocument_OrideFiles()
	 * @model containment="true"
	 * @generated
	 */
	EList<OverrideFiles> getOrideFiles();

	/**
	 * Returns the value of the '<em><b>Oride Confs</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oride Confs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oride Confs</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage#getImpDocument_OrideConfs()
	 * @model containment="true"
	 * @generated
	 */
	EList<OverrideConfiguration> getOrideConfs();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.impmodel.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage#getImpDocument_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Variable getVariable(String name);

} // ImpDocument
