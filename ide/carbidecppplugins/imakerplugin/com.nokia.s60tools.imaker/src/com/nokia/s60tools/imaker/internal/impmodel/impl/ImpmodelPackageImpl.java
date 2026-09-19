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

package com.nokia.s60tools.imaker.internal.impmodel.impl;

import com.nokia.s60tools.imaker.internal.impmodel.Comment;
import com.nokia.s60tools.imaker.internal.impmodel.CommentContainer;
import com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry;
import com.nokia.s60tools.imaker.internal.impmodel.FileListEntry;
import com.nokia.s60tools.imaker.internal.impmodel.ImpDocument;
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelFactory;
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage;
import com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
public class ImpmodelPackageImpl extends EPackageImpl implements ImpmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass impDocumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass overrideFilesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass overrideConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileListEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lineNumberContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentContainerEClass = null;

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
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ImpmodelPackageImpl() {
		super(eNS_URI, ImpmodelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ImpmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ImpmodelPackage init() {
		if (isInited) return (ImpmodelPackage)EPackage.Registry.INSTANCE.getEPackage(ImpmodelPackage.eNS_URI);

		// Obtain or create and register package
		ImpmodelPackageImpl theImpmodelPackage = (ImpmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ImpmodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ImpmodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theImpmodelPackage.createPackageContents();

		// Initialize created meta-data
		theImpmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theImpmodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ImpmodelPackage.eNS_URI, theImpmodelPackage);
		return theImpmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImpDocument() {
		return impDocumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImpDocument_OrideFiles() {
		return (EReference)impDocumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImpDocument_OrideConfs() {
		return (EReference)impDocumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImpDocument_Variables() {
		return (EReference)impDocumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOverrideFiles() {
		return overrideFilesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOverrideFiles_Entries() {
		return (EReference)overrideFilesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOverrideConfiguration() {
		return overrideConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOverrideConfiguration_Entries() {
		return (EReference)overrideConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileListEntry() {
		return fileListEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileListEntry_Source() {
		return (EAttribute)fileListEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileListEntry_Target() {
		return (EAttribute)fileListEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFileListEntry_Actions() {
		return (EReference)fileListEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfigEntry() {
		return configEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigEntry_Target() {
		return (EAttribute)configEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigEntry_Action() {
		return (EAttribute)configEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigEntry_Location() {
		return (EAttribute)configEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariable_Value() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLineNumberContainer() {
		return lineNumberContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLineNumberContainer_LineNumber() {
		return (EAttribute)lineNumberContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComment() {
		return commentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComment_Comment() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommentContainer() {
		return commentContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommentContainer_Comments() {
		return (EReference)commentContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImpmodelFactory getImpmodelFactory() {
		return (ImpmodelFactory)getEFactoryInstance();
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
		impDocumentEClass = createEClass(IMP_DOCUMENT);
		createEReference(impDocumentEClass, IMP_DOCUMENT__ORIDE_FILES);
		createEReference(impDocumentEClass, IMP_DOCUMENT__ORIDE_CONFS);
		createEReference(impDocumentEClass, IMP_DOCUMENT__VARIABLES);

		overrideFilesEClass = createEClass(OVERRIDE_FILES);
		createEReference(overrideFilesEClass, OVERRIDE_FILES__ENTRIES);

		overrideConfigurationEClass = createEClass(OVERRIDE_CONFIGURATION);
		createEReference(overrideConfigurationEClass, OVERRIDE_CONFIGURATION__ENTRIES);

		fileListEntryEClass = createEClass(FILE_LIST_ENTRY);
		createEAttribute(fileListEntryEClass, FILE_LIST_ENTRY__SOURCE);
		createEAttribute(fileListEntryEClass, FILE_LIST_ENTRY__TARGET);
		createEReference(fileListEntryEClass, FILE_LIST_ENTRY__ACTIONS);

		configEntryEClass = createEClass(CONFIG_ENTRY);
		createEAttribute(configEntryEClass, CONFIG_ENTRY__TARGET);
		createEAttribute(configEntryEClass, CONFIG_ENTRY__ACTION);
		createEAttribute(configEntryEClass, CONFIG_ENTRY__LOCATION);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);
		createEAttribute(variableEClass, VARIABLE__VALUE);

		lineNumberContainerEClass = createEClass(LINE_NUMBER_CONTAINER);
		createEAttribute(lineNumberContainerEClass, LINE_NUMBER_CONTAINER__LINE_NUMBER);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__COMMENT);

		commentContainerEClass = createEClass(COMMENT_CONTAINER);
		createEReference(commentContainerEClass, COMMENT_CONTAINER__COMMENTS);
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
		impDocumentEClass.getESuperTypes().add(this.getCommentContainer());
		overrideFilesEClass.getESuperTypes().add(this.getLineNumberContainer());
		overrideFilesEClass.getESuperTypes().add(this.getCommentContainer());
		overrideConfigurationEClass.getESuperTypes().add(this.getLineNumberContainer());
		overrideConfigurationEClass.getESuperTypes().add(this.getCommentContainer());
		fileListEntryEClass.getESuperTypes().add(this.getLineNumberContainer());
		configEntryEClass.getESuperTypes().add(this.getLineNumberContainer());
		variableEClass.getESuperTypes().add(this.getLineNumberContainer());
		commentEClass.getESuperTypes().add(this.getLineNumberContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(impDocumentEClass, ImpDocument.class, "ImpDocument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImpDocument_OrideFiles(), this.getOverrideFiles(), null, "orideFiles", null, 0, -1, ImpDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getImpDocument_OrideConfs(), this.getOverrideConfiguration(), null, "orideConfs", null, 0, -1, ImpDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getImpDocument_Variables(), this.getVariable(), null, "variables", null, 0, -1, ImpDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(impDocumentEClass, this.getVariable(), "getVariable", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(overrideFilesEClass, OverrideFiles.class, "OverrideFiles", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOverrideFiles_Entries(), this.getFileListEntry(), null, "entries", null, 0, -1, OverrideFiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(overrideConfigurationEClass, OverrideConfiguration.class, "OverrideConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOverrideConfiguration_Entries(), this.getConfigEntry(), null, "entries", null, 0, -1, OverrideConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileListEntryEClass, FileListEntry.class, "FileListEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileListEntry_Source(), ecorePackage.getEString(), "source", null, 0, 1, FileListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileListEntry_Target(), ecorePackage.getEString(), "target", null, 0, 1, FileListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileListEntry_Actions(), this.getConfigEntry(), null, "actions", null, 0, -1, FileListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configEntryEClass, ConfigEntry.class, "ConfigEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfigEntry_Target(), ecorePackage.getEString(), "target", null, 0, 1, ConfigEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigEntry_Action(), ecorePackage.getEString(), "action", null, 0, 1, ConfigEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigEntry_Location(), ecorePackage.getEString(), "location", null, 0, 1, ConfigEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), ecorePackage.getEString(), "name", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariable_Value(), ecorePackage.getEString(), "value", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lineNumberContainerEClass, LineNumberContainer.class, "LineNumberContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLineNumberContainer_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1, LineNumberContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComment_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentContainerEClass, CommentContainer.class, "CommentContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommentContainer_Comments(), this.getComment(), null, "comments", null, 0, -1, CommentContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ImpmodelPackageImpl
