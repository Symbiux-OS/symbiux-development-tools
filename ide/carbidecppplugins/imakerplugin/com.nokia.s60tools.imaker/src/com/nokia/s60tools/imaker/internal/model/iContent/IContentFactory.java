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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage
 * @generated
 */
public interface IContentFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IContentFactory eINSTANCE = com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Image Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Image Content</em>'.
	 * @generated
	 */
	ImageContent createImageContent();

	/**
	 * Returns a new object of class '<em>Iby Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Iby Entry</em>'.
	 * @generated
	 */
	IbyEntry createIbyEntry();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IContentPackage getIContentPackage();
	
	IbyEntry createEntryFromString(String initialValue);
} //IContentFactory
