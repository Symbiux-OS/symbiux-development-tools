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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory
 * @model kind="package"
 * @generated
 */
public interface IContentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "iContent";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.nokia.com";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ic";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IContentPackage eINSTANCE = com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.ImageContentImpl <em>Image Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.ImageContentImpl
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getImageContent()
	 * @generated
	 */
	int IMAGE_CONTENT = 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_CONTENT__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Image Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_CONTENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl <em>Iby Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getIbyEntry()
	 * @generated
	 */
	int IBY_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBY_ENTRY__FILE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBY_ENTRY__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBY_ENTRY__LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBY_ENTRY__ACTION = 3;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBY_ENTRY__ENABLED = 4;

	/**
	 * The feature id for the '<em><b>Status Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBY_ENTRY__STATUS_MESSAGE = 5;

	/**
	 * The number of structural features of the '<em>Iby Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBY_ENTRY_FEATURE_COUNT = 6;


	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION <em>IMAGESECTION</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getIMAGESECTION()
	 * @generated
	 */
	int IMAGESECTION = 2;


	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.ACTION <em>ACTION</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.ACTION
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getACTION()
	 * @generated
	 */
	int ACTION = 3;


	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.model.iContent.ImageContent <em>Image Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Image Content</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.ImageContent
	 * @generated
	 */
	EClass getImageContent();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.model.iContent.ImageContent#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.ImageContent#getEntries()
	 * @see #getImageContent()
	 * @generated
	 */
	EReference getImageContent_Entries();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry <em>Iby Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iby Entry</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry
	 * @generated
	 */
	EClass getIbyEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getFile()
	 * @see #getIbyEntry()
	 * @generated
	 */
	EAttribute getIbyEntry_File();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getTarget()
	 * @see #getIbyEntry()
	 * @generated
	 */
	EAttribute getIbyEntry_Target();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getLocation()
	 * @see #getIbyEntry()
	 * @generated
	 */
	EAttribute getIbyEntry_Location();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getAction()
	 * @see #getIbyEntry()
	 * @generated
	 */
	EAttribute getIbyEntry_Action();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#isEnabled()
	 * @see #getIbyEntry()
	 * @generated
	 */
	EAttribute getIbyEntry_Enabled();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getStatusMessage <em>Status Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status Message</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry#getStatusMessage()
	 * @see #getIbyEntry()
	 * @generated
	 */
	EAttribute getIbyEntry_StatusMessage();

	/**
	 * Returns the meta object for enum '{@link com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION <em>IMAGESECTION</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>IMAGESECTION</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION
	 * @generated
	 */
	EEnum getIMAGESECTION();

	/**
	 * Returns the meta object for enum '{@link com.nokia.s60tools.imaker.internal.model.iContent.ACTION <em>ACTION</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ACTION</em>'.
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.ACTION
	 * @generated
	 */
	EEnum getACTION();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IContentFactory getIContentFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.ImageContentImpl <em>Image Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.ImageContentImpl
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getImageContent()
		 * @generated
		 */
		EClass IMAGE_CONTENT = eINSTANCE.getImageContent();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMAGE_CONTENT__ENTRIES = eINSTANCE.getImageContent_Entries();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl <em>Iby Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getIbyEntry()
		 * @generated
		 */
		EClass IBY_ENTRY = eINSTANCE.getIbyEntry();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBY_ENTRY__FILE = eINSTANCE.getIbyEntry_File();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBY_ENTRY__TARGET = eINSTANCE.getIbyEntry_Target();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBY_ENTRY__LOCATION = eINSTANCE.getIbyEntry_Location();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBY_ENTRY__ACTION = eINSTANCE.getIbyEntry_Action();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBY_ENTRY__ENABLED = eINSTANCE.getIbyEntry_Enabled();

		/**
		 * The meta object literal for the '<em><b>Status Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBY_ENTRY__STATUS_MESSAGE = eINSTANCE.getIbyEntry_StatusMessage();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION <em>IMAGESECTION</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getIMAGESECTION()
		 * @generated
		 */
		EEnum IMAGESECTION = eINSTANCE.getIMAGESECTION();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.model.iContent.ACTION <em>ACTION</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.ACTION
		 * @see com.nokia.s60tools.imaker.internal.model.iContent.impl.IContentPackageImpl#getACTION()
		 * @generated
		 */
		EEnum ACTION = eINSTANCE.getACTION();

	}

} //IContentPackage
