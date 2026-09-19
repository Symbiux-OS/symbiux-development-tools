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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage
 * @generated
 */
public interface ImpmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ImpmodelFactory eINSTANCE = com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Imp Document</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Imp Document</em>'.
	 * @generated
	 */
	ImpDocument createImpDocument();

	/**
	 * Returns a new object of class '<em>Override Files</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Override Files</em>'.
	 * @generated
	 */
	OverrideFiles createOverrideFiles();

	/**
	 * Returns a new object of class '<em>Override Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Override Configuration</em>'.
	 * @generated
	 */
	OverrideConfiguration createOverrideConfiguration();

	/**
	 * Returns a new object of class '<em>File List Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File List Entry</em>'.
	 * @generated
	 */
	FileListEntry createFileListEntry();

	/**
	 * Returns a new object of class '<em>Config Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Config Entry</em>'.
	 * @generated
	 */
	ConfigEntry createConfigEntry();

	/**
	 * Returns a new object of class '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable</em>'.
	 * @generated
	 */
	Variable createVariable();

	/**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
	Comment createComment();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ImpmodelPackage getImpmodelPackage();

} //ImpmodelFactory
