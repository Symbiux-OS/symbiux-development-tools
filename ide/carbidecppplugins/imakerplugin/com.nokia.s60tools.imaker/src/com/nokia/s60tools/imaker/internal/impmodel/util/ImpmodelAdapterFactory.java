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

package com.nokia.s60tools.imaker.internal.impmodel.util;

import com.nokia.s60tools.imaker.internal.impmodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage
 * @generated
 */
public class ImpmodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ImpmodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImpmodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ImpmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImpmodelSwitch<Adapter> modelSwitch =
		new ImpmodelSwitch<Adapter>() {
			@Override
			public Adapter caseImpDocument(ImpDocument object) {
				return createImpDocumentAdapter();
			}
			@Override
			public Adapter caseOverrideFiles(OverrideFiles object) {
				return createOverrideFilesAdapter();
			}
			@Override
			public Adapter caseOverrideConfiguration(OverrideConfiguration object) {
				return createOverrideConfigurationAdapter();
			}
			@Override
			public Adapter caseFileListEntry(FileListEntry object) {
				return createFileListEntryAdapter();
			}
			@Override
			public Adapter caseConfigEntry(ConfigEntry object) {
				return createConfigEntryAdapter();
			}
			@Override
			public Adapter caseVariable(Variable object) {
				return createVariableAdapter();
			}
			@Override
			public Adapter caseLineNumberContainer(LineNumberContainer object) {
				return createLineNumberContainerAdapter();
			}
			@Override
			public Adapter caseComment(Comment object) {
				return createCommentAdapter();
			}
			@Override
			public Adapter caseCommentContainer(CommentContainer object) {
				return createCommentContainerAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument <em>Imp Document</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpDocument
	 * @generated
	 */
	public Adapter createImpDocumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles <em>Override Files</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles
	 * @generated
	 */
	public Adapter createOverrideFilesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration <em>Override Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration
	 * @generated
	 */
	public Adapter createOverrideConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.FileListEntry <em>File List Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.FileListEntry
	 * @generated
	 */
	public Adapter createFileListEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry <em>Config Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry
	 * @generated
	 */
	public Adapter createConfigEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer <em>Line Number Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer
	 * @generated
	 */
	public Adapter createLineNumberContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.Comment
	 * @generated
	 */
	public Adapter createCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.s60tools.imaker.internal.impmodel.CommentContainer <em>Comment Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.CommentContainer
	 * @generated
	 */
	public Adapter createCommentContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ImpmodelAdapterFactory
