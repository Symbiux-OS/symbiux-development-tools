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
package com.nokia.s60tools.imaker.internal.model.iContent.impl;

import com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;
import com.nokia.s60tools.imaker.internal.model.iContent.ImageContent;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IContentPackageImpl extends EPackageImpl implements IContentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass imageContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ibyEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum imagesectionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum actionEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IContentPackageImpl() {
		super(eNS_URI, IContentFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link IContentPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IContentPackage init() {
		if (isInited) return (IContentPackage)EPackage.Registry.INSTANCE.getEPackage(IContentPackage.eNS_URI);

		// Obtain or create and register package
		IContentPackageImpl theIContentPackage = (IContentPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof IContentPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new IContentPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theIContentPackage.createPackageContents();

		// Initialize created meta-data
		theIContentPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIContentPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IContentPackage.eNS_URI, theIContentPackage);
		return theIContentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImageContent() {
		return imageContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImageContent_Entries() {
		return (EReference)imageContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIbyEntry() {
		return ibyEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIbyEntry_File() {
		return (EAttribute)ibyEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIbyEntry_Target() {
		return (EAttribute)ibyEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIbyEntry_Location() {
		return (EAttribute)ibyEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIbyEntry_Action() {
		return (EAttribute)ibyEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIbyEntry_Enabled() {
		return (EAttribute)ibyEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIbyEntry_StatusMessage() {
		return (EAttribute)ibyEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getIMAGESECTION() {
		return imagesectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getACTION() {
		return actionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IContentFactory getIContentFactory() {
		return (IContentFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		imageContentEClass = createEClass(IMAGE_CONTENT);
		createEReference(imageContentEClass, IMAGE_CONTENT__ENTRIES);

		ibyEntryEClass = createEClass(IBY_ENTRY);
		createEAttribute(ibyEntryEClass, IBY_ENTRY__FILE);
		createEAttribute(ibyEntryEClass, IBY_ENTRY__TARGET);
		createEAttribute(ibyEntryEClass, IBY_ENTRY__LOCATION);
		createEAttribute(ibyEntryEClass, IBY_ENTRY__ACTION);
		createEAttribute(ibyEntryEClass, IBY_ENTRY__ENABLED);
		createEAttribute(ibyEntryEClass, IBY_ENTRY__STATUS_MESSAGE);

		// Create enums
		imagesectionEEnum = createEEnum(IMAGESECTION);
		actionEEnum = createEEnum(ACTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(imageContentEClass, ImageContent.class, "ImageContent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImageContent_Entries(), this.getIbyEntry(), null, "entries", null, 0, -1, ImageContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ibyEntryEClass, IbyEntry.class, "IbyEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIbyEntry_File(), ecorePackage.getEString(), "file", null, 0, 1, IbyEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIbyEntry_Target(), ecorePackage.getEString(), "target", null, 0, 1, IbyEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIbyEntry_Location(), this.getIMAGESECTION(), "location", null, 0, 1, IbyEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIbyEntry_Action(), this.getACTION(), "action", null, 0, 1, IbyEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIbyEntry_Enabled(), ecorePackage.getEBoolean(), "enabled", null, 0, 1, IbyEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIbyEntry_StatusMessage(), ecorePackage.getEString(), "statusMessage", null, 0, 1, IbyEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(imagesectionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION.class, "IMAGESECTION");
		addEEnumLiteral(imagesectionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION.CORE);
		addEEnumLiteral(imagesectionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION.ROFS2);
		addEEnumLiteral(imagesectionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION.ROFS3);
		addEEnumLiteral(imagesectionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION.ANY);

		initEEnum(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.class, "ACTION");
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.UDEB);
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.REMOVE);
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.HIDE);
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.UDEB_ADD);
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.UREL);
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.UREL_ADD);
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.REPLACE);
		addEEnumLiteral(actionEEnum, com.nokia.s60tools.imaker.internal.model.iContent.ACTION.REPLACE_ADD);

		// Create resource
		createResource(eNS_URI);
	}

} //IContentPackageImpl
