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
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getSettings <em>Settings</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getFilePath <em>File Path</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getTargetrefs <em>Targetrefs</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getConfiguration()
 * @model
 * @generated
 */
public interface Configuration extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getConfiguration_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Settings</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.iqrf.Setting}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Settings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Settings</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getConfiguration_Settings()
	 * @model type="iQRF.Setting" containment="true" required="true" upper="999999"
	 * @generated
	 */
	EList getSettings();

	/**
	 * Returns the value of the '<em><b>File Path</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Path</em>' attribute.
	 * @see #setFilePath(String)
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getConfiguration_FilePath()
	 * @model default=""
	 * @generated
	 */
	String getFilePath();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getFilePath <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Path</em>' attribute.
	 * @see #getFilePath()
	 * @generated
	 */
	void setFilePath(String value);

	/**
	 * Returns the value of the '<em><b>Targetrefs</b></em>' reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.iqrf.Target}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Targetrefs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Targetrefs</em>' reference list.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getConfiguration_Targetrefs()
	 * @model type="iQRF.Target" upper="99999"
	 * @generated
	 */
	EList getTargetrefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addSetting(Setting setting);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addTargetRef(Target target);

} // Configuration