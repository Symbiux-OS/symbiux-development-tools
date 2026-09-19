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
 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelFactory
 * @model kind="package"
 * @generated
 */
public interface ImpmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "impmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.s60.com/xml/imp/1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "imp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ImpmodelPackage eINSTANCE = com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.CommentContainer <em>Comment Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.CommentContainer
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getCommentContainer()
	 * @generated
	 */
	int COMMENT_CONTAINER = 8;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_CONTAINER__COMMENTS = 0;

	/**
	 * The number of structural features of the '<em>Comment Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl <em>Imp Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getImpDocument()
	 * @generated
	 */
	int IMP_DOCUMENT = 0;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMP_DOCUMENT__COMMENTS = COMMENT_CONTAINER__COMMENTS;

	/**
	 * The feature id for the '<em><b>Oride Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMP_DOCUMENT__ORIDE_FILES = COMMENT_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Oride Confs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMP_DOCUMENT__ORIDE_CONFS = COMMENT_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMP_DOCUMENT__VARIABLES = COMMENT_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Imp Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMP_DOCUMENT_FEATURE_COUNT = COMMENT_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer <em>Line Number Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getLineNumberContainer()
	 * @generated
	 */
	int LINE_NUMBER_CONTAINER = 6;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_NUMBER_CONTAINER__LINE_NUMBER = 0;

	/**
	 * The number of structural features of the '<em>Line Number Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_NUMBER_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideFilesImpl <em>Override Files</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideFilesImpl
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getOverrideFiles()
	 * @generated
	 */
	int OVERRIDE_FILES = 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_FILES__LINE_NUMBER = LINE_NUMBER_CONTAINER__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_FILES__COMMENTS = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_FILES__ENTRIES = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Override Files</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_FILES_FEATURE_COUNT = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideConfigurationImpl <em>Override Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideConfigurationImpl
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getOverrideConfiguration()
	 * @generated
	 */
	int OVERRIDE_CONFIGURATION = 2;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_CONFIGURATION__LINE_NUMBER = LINE_NUMBER_CONTAINER__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_CONFIGURATION__COMMENTS = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_CONFIGURATION__ENTRIES = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Override Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERRIDE_CONFIGURATION_FEATURE_COUNT = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.FileListEntryImpl <em>File List Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.FileListEntryImpl
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getFileListEntry()
	 * @generated
	 */
	int FILE_LIST_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_LIST_ENTRY__LINE_NUMBER = LINE_NUMBER_CONTAINER__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_LIST_ENTRY__SOURCE = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_LIST_ENTRY__TARGET = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_LIST_ENTRY__ACTIONS = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>File List Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_LIST_ENTRY_FEATURE_COUNT = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ConfigEntryImpl <em>Config Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ConfigEntryImpl
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getConfigEntry()
	 * @generated
	 */
	int CONFIG_ENTRY = 4;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_ENTRY__LINE_NUMBER = LINE_NUMBER_CONTAINER__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_ENTRY__TARGET = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_ENTRY__ACTION = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_ENTRY__LOCATION = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Config Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_ENTRY_FEATURE_COUNT = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.VariableImpl
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 5;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__LINE_NUMBER = LINE_NUMBER_CONTAINER__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__VALUE = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.CommentImpl
	 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 7;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__LINE_NUMBER = LINE_NUMBER_CONTAINER__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__COMMENT = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = LINE_NUMBER_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument <em>Imp Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Imp Document</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpDocument
	 * @generated
	 */
	EClass getImpDocument();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getOrideFiles <em>Oride Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Oride Files</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getOrideFiles()
	 * @see #getImpDocument()
	 * @generated
	 */
	EReference getImpDocument_OrideFiles();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getOrideConfs <em>Oride Confs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Oride Confs</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getOrideConfs()
	 * @see #getImpDocument()
	 * @generated
	 */
	EReference getImpDocument_OrideConfs();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getVariables()
	 * @see #getImpDocument()
	 * @generated
	 */
	EReference getImpDocument_Variables();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles <em>Override Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Override Files</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles
	 * @generated
	 */
	EClass getOverrideFiles();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles#getEntries()
	 * @see #getOverrideFiles()
	 * @generated
	 */
	EReference getOverrideFiles_Entries();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration <em>Override Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Override Configuration</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration
	 * @generated
	 */
	EClass getOverrideConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration#getEntries()
	 * @see #getOverrideConfiguration()
	 * @generated
	 */
	EReference getOverrideConfiguration_Entries();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.FileListEntry <em>File List Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File List Entry</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.FileListEntry
	 * @generated
	 */
	EClass getFileListEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.FileListEntry#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.FileListEntry#getSource()
	 * @see #getFileListEntry()
	 * @generated
	 */
	EAttribute getFileListEntry_Source();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.FileListEntry#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.FileListEntry#getTarget()
	 * @see #getFileListEntry()
	 * @generated
	 */
	EAttribute getFileListEntry_Target();

	/**
	 * Returns the meta object for the reference list '{@link com.nokia.s60tools.imaker.internal.impmodel.FileListEntry#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Actions</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.FileListEntry#getActions()
	 * @see #getFileListEntry()
	 * @generated
	 */
	EReference getFileListEntry_Actions();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry <em>Config Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config Entry</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry
	 * @generated
	 */
	EClass getConfigEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry#getTarget()
	 * @see #getConfigEntry()
	 * @generated
	 */
	EAttribute getConfigEntry_Target();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry#getAction()
	 * @see #getConfigEntry()
	 * @generated
	 */
	EAttribute getConfigEntry_Action();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry#getLocation()
	 * @see #getConfigEntry()
	 * @generated
	 */
	EAttribute getConfigEntry_Location();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.Variable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.Variable#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.Variable#getValue()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Value();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer <em>Line Number Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Line Number Container</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer
	 * @generated
	 */
	EClass getLineNumberContainer();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer#getLineNumber()
	 * @see #getLineNumberContainer()
	 * @generated
	 */
	EAttribute getLineNumberContainer_LineNumber();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.impmodel.Comment#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.Comment#getComment()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Comment();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.impmodel.CommentContainer <em>Comment Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment Container</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.CommentContainer
	 * @generated
	 */
	EClass getCommentContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.impmodel.CommentContainer#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comments</em>'.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.CommentContainer#getComments()
	 * @see #getCommentContainer()
	 * @generated
	 */
	EReference getCommentContainer_Comments();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ImpmodelFactory getImpmodelFactory();

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
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl <em>Imp Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getImpDocument()
		 * @generated
		 */
		EClass IMP_DOCUMENT = eINSTANCE.getImpDocument();

		/**
		 * The meta object literal for the '<em><b>Oride Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMP_DOCUMENT__ORIDE_FILES = eINSTANCE.getImpDocument_OrideFiles();

		/**
		 * The meta object literal for the '<em><b>Oride Confs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMP_DOCUMENT__ORIDE_CONFS = eINSTANCE.getImpDocument_OrideConfs();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMP_DOCUMENT__VARIABLES = eINSTANCE.getImpDocument_Variables();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideFilesImpl <em>Override Files</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideFilesImpl
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getOverrideFiles()
		 * @generated
		 */
		EClass OVERRIDE_FILES = eINSTANCE.getOverrideFiles();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OVERRIDE_FILES__ENTRIES = eINSTANCE.getOverrideFiles_Entries();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideConfigurationImpl <em>Override Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideConfigurationImpl
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getOverrideConfiguration()
		 * @generated
		 */
		EClass OVERRIDE_CONFIGURATION = eINSTANCE.getOverrideConfiguration();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OVERRIDE_CONFIGURATION__ENTRIES = eINSTANCE.getOverrideConfiguration_Entries();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.FileListEntryImpl <em>File List Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.FileListEntryImpl
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getFileListEntry()
		 * @generated
		 */
		EClass FILE_LIST_ENTRY = eINSTANCE.getFileListEntry();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_LIST_ENTRY__SOURCE = eINSTANCE.getFileListEntry_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_LIST_ENTRY__TARGET = eINSTANCE.getFileListEntry_Target();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_LIST_ENTRY__ACTIONS = eINSTANCE.getFileListEntry_Actions();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ConfigEntryImpl <em>Config Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ConfigEntryImpl
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getConfigEntry()
		 * @generated
		 */
		EClass CONFIG_ENTRY = eINSTANCE.getConfigEntry();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_ENTRY__TARGET = eINSTANCE.getConfigEntry_Target();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_ENTRY__ACTION = eINSTANCE.getConfigEntry_Action();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_ENTRY__LOCATION = eINSTANCE.getConfigEntry_Location();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.VariableImpl
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__VALUE = eINSTANCE.getVariable_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer <em>Line Number Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getLineNumberContainer()
		 * @generated
		 */
		EClass LINE_NUMBER_CONTAINER = eINSTANCE.getLineNumberContainer();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE_NUMBER_CONTAINER__LINE_NUMBER = eINSTANCE.getLineNumberContainer_LineNumber();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.CommentImpl
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__COMMENT = eINSTANCE.getComment_Comment();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.impmodel.CommentContainer <em>Comment Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.impmodel.CommentContainer
		 * @see com.nokia.s60tools.imaker.internal.impmodel.impl.ImpmodelPackageImpl#getCommentContainer()
		 * @generated
		 */
		EClass COMMENT_CONTAINER = eINSTANCE.getCommentContainer();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT_CONTAINER__COMMENTS = eINSTANCE.getCommentContainer_Comments();

	}

} //ImpmodelPackage
