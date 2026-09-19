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

package com.nokia.s60tools.imaker.internal.iqrf;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.Result#getInterfaces <em>Interfaces</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.Result#getConfigurations <em>Configurations</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.Result#getTargets <em>Targets</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getResult()
 * @model
 * @generated
 */
public interface Result extends EObject {
	/**
	 * Returns the value of the '<em><b>Interfaces</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.iqrf.Interface}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interfaces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interfaces</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getResult_Interfaces()
	 * @model type="iQRF.Interface" containment="true" required="true" upper="99999"
	 * @generated
	 */
	EList getInterfaces();

	/**
	 * Returns the value of the '<em><b>Configurations</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.iqrf.Configuration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configurations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configurations</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getResult_Configurations()
	 * @model type="iQRF.Configuration" containment="true" required="true" upper="99999"
	 * @generated
	 */
	EList getConfigurations();

	/**
	 * Returns the value of the '<em><b>Targets</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.iqrf.Target}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Targets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Targets</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getResult_Targets()
	 * @model type="iQRF.Target" containment="true" upper="99999"
	 * @generated
	 */
	EList getTargets();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addConfiguration(Configuration config);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addInterface(Interface intf);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addTarget(Target target);

} // Result