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

import com.nokia.s60tools.imaker.internal.impmodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ImpmodelFactoryImpl extends EFactoryImpl implements ImpmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ImpmodelFactory init() {
		try {
			ImpmodelFactory theImpmodelFactory = (ImpmodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.s60.com/xml/imp/1"); 
			if (theImpmodelFactory != null) {
				return theImpmodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ImpmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImpmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ImpmodelPackage.IMP_DOCUMENT: return createImpDocument();
			case ImpmodelPackage.OVERRIDE_FILES: return createOverrideFiles();
			case ImpmodelPackage.OVERRIDE_CONFIGURATION: return createOverrideConfiguration();
			case ImpmodelPackage.FILE_LIST_ENTRY: return createFileListEntry();
			case ImpmodelPackage.CONFIG_ENTRY: return createConfigEntry();
			case ImpmodelPackage.VARIABLE: return createVariable();
			case ImpmodelPackage.COMMENT: return createComment();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImpDocument createImpDocument() {
		ImpDocumentImpl impDocument = new ImpDocumentImpl();
		return impDocument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideFiles createOverrideFiles() {
		OverrideFilesImpl overrideFiles = new OverrideFilesImpl();
		return overrideFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfiguration createOverrideConfiguration() {
		OverrideConfigurationImpl overrideConfiguration = new OverrideConfigurationImpl();
		return overrideConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileListEntry createFileListEntry() {
		FileListEntryImpl fileListEntry = new FileListEntryImpl();
		return fileListEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigEntry createConfigEntry() {
		ConfigEntryImpl configEntry = new ConfigEntryImpl();
		return configEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImpmodelPackage getImpmodelPackage() {
		return (ImpmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ImpmodelPackage getPackage() {
		return ImpmodelPackage.eINSTANCE;
	}

} //ImpmodelFactoryImpl
