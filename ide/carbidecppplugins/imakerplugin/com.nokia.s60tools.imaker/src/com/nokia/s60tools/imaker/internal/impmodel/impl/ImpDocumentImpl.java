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
import com.nokia.s60tools.imaker.internal.impmodel.ImpDocument;
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Imp Document</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl#getComments <em>Comments</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl#getOrideFiles <em>Oride Files</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl#getOrideConfs <em>Oride Confs</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.impl.ImpDocumentImpl#getVariables <em>Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImpDocumentImpl extends EObjectImpl implements ImpDocument {
	/**
	 * The cached value of the '{@link #getComments() <em>Comments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComments()
	 * @generated
	 * @ordered
	 */
	protected EList<Comment> comments;

	/**
	 * The cached value of the '{@link #getOrideFiles() <em>Oride Files</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrideFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<OverrideFiles> orideFiles;

	/**
	 * The cached value of the '{@link #getOrideConfs() <em>Oride Confs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrideConfs()
	 * @generated
	 * @ordered
	 */
	protected EList<OverrideConfiguration> orideConfs;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> variables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImpDocumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImpmodelPackage.Literals.IMP_DOCUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Comment> getComments() {
		if (comments == null) {
			comments = new EObjectContainmentEList<Comment>(Comment.class, this, ImpmodelPackage.IMP_DOCUMENT__COMMENTS);
		}
		return comments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OverrideFiles> getOrideFiles() {
		if (orideFiles == null) {
			orideFiles = new EObjectContainmentEList<OverrideFiles>(OverrideFiles.class, this, ImpmodelPackage.IMP_DOCUMENT__ORIDE_FILES);
		}
		return orideFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OverrideConfiguration> getOrideConfs() {
		if (orideConfs == null) {
			orideConfs = new EObjectContainmentEList<OverrideConfiguration>(OverrideConfiguration.class, this, ImpmodelPackage.IMP_DOCUMENT__ORIDE_CONFS);
		}
		return orideConfs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Variable>(Variable.class, this, ImpmodelPackage.IMP_DOCUMENT__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Variable getVariable(String name) {
		Variable variable = null;
		if(name==null||"".equals(name)) {
			return variable;
		}
		name = name.toLowerCase();
		for (Variable v : getVariables()) {
			if(name.equals(v.getName().toLowerCase())) {
				variable = v;
				break;
			}
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImpmodelPackage.IMP_DOCUMENT__COMMENTS:
				return ((InternalEList<?>)getComments()).basicRemove(otherEnd, msgs);
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_FILES:
				return ((InternalEList<?>)getOrideFiles()).basicRemove(otherEnd, msgs);
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_CONFS:
				return ((InternalEList<?>)getOrideConfs()).basicRemove(otherEnd, msgs);
			case ImpmodelPackage.IMP_DOCUMENT__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImpmodelPackage.IMP_DOCUMENT__COMMENTS:
				return getComments();
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_FILES:
				return getOrideFiles();
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_CONFS:
				return getOrideConfs();
			case ImpmodelPackage.IMP_DOCUMENT__VARIABLES:
				return getVariables();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImpmodelPackage.IMP_DOCUMENT__COMMENTS:
				getComments().clear();
				getComments().addAll((Collection<? extends Comment>)newValue);
				return;
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_FILES:
				getOrideFiles().clear();
				getOrideFiles().addAll((Collection<? extends OverrideFiles>)newValue);
				return;
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_CONFS:
				getOrideConfs().clear();
				getOrideConfs().addAll((Collection<? extends OverrideConfiguration>)newValue);
				return;
			case ImpmodelPackage.IMP_DOCUMENT__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Variable>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ImpmodelPackage.IMP_DOCUMENT__COMMENTS:
				getComments().clear();
				return;
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_FILES:
				getOrideFiles().clear();
				return;
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_CONFS:
				getOrideConfs().clear();
				return;
			case ImpmodelPackage.IMP_DOCUMENT__VARIABLES:
				getVariables().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ImpmodelPackage.IMP_DOCUMENT__COMMENTS:
				return comments != null && !comments.isEmpty();
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_FILES:
				return orideFiles != null && !orideFiles.isEmpty();
			case ImpmodelPackage.IMP_DOCUMENT__ORIDE_CONFS:
				return orideConfs != null && !orideConfs.isEmpty();
			case ImpmodelPackage.IMP_DOCUMENT__VARIABLES:
				return variables != null && !variables.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ImpDocumentImpl
