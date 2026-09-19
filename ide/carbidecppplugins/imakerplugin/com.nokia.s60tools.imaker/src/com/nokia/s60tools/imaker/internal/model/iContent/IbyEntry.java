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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iby Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getFile <em>File</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getTarget <em>Target</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getLocation <em>Location</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getAction <em>Action</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getStatusMessage <em>Status Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIbyEntry()
 * @model
 * @generated
 */
public interface IbyEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(String)
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIbyEntry_File()
	 * @model
	 * @generated
	 */
	String getFile();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(String value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' attribute.
	 * @see #setTarget(String)
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIbyEntry_Target()
	 * @model
	 * @generated
	 */
	String getTarget();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getTarget <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' attribute.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(String value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION
	 * @see #setLocation(IMAGESECTION)
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIbyEntry_Location()
	 * @model
	 * @generated
	 */
	IMAGESECTION getLocation();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(IMAGESECTION value);

	/**
	 * Returns the value of the '<em><b>Action</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.s60tools.imaker.internal.model.iContent.ACTION}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' attribute.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.ACTION
	 * @see #setAction(ACTION)
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIbyEntry_Action()
	 * @model
	 * @generated
	 */
	ACTION getAction();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getAction <em>Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' attribute.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.ACTION
	 * @see #getAction()
	 * @generated
	 */
	void setAction(ACTION value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIbyEntry_Enabled()
	 * @model
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Status Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status Message</em>' attribute.
	 * @see #setStatusMessage(String)
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#getIbyEntry_StatusMessage()
	 * @model
	 * @generated
	 */
	String getStatusMessage();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getStatusMessage <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Message</em>' attribute.
	 * @see #getStatusMessage()
	 * @generated
	 */
	void setStatusMessage(String value);

	void append(StringBuffer sb);

} // IbyEntry
