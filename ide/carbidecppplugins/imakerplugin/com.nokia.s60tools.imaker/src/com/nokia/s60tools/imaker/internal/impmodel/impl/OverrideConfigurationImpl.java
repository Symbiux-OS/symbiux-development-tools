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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.nokia.s60tools.imaker.internal.impmodel.Comment;
import com.nokia.s60tools.imaker.internal.impmodel.CommentContainer;
import com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry;
import com.nokia.s60tools.imaker.internal.impmodel.ImpConstants;
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Override Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideConfigurationImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideConfigurationImpl#getComments <em>Comments</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.impl.OverrideConfigurationImpl#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OverrideConfigurationImpl extends EObjectImpl implements OverrideConfiguration {
	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

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
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigEntry> entries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OverrideConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImpmodelPackage.Literals.OVERRIDE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImpmodelPackage.OVERRIDE_CONFIGURATION__LINE_NUMBER, oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Comment> getComments() {
		if (comments == null) {
			comments = new EObjectContainmentEList<Comment>(Comment.class, this, ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS);
		}
		return comments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConfigEntry> getEntries() {
		if (entries == null) {
			entries = new EObjectContainmentEList<ConfigEntry>(ConfigEntry.class, this, ImpmodelPackage.OVERRIDE_CONFIGURATION__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS:
				return ((InternalEList<?>)getComments()).basicRemove(otherEnd, msgs);
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
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
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__LINE_NUMBER:
				return getLineNumber();
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS:
				return getComments();
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__ENTRIES:
				return getEntries();
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
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__LINE_NUMBER:
				setLineNumber((Integer)newValue);
				return;
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS:
				getComments().clear();
				getComments().addAll((Collection<? extends Comment>)newValue);
				return;
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends ConfigEntry>)newValue);
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
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__LINE_NUMBER:
				setLineNumber(LINE_NUMBER_EDEFAULT);
				return;
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS:
				getComments().clear();
				return;
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__ENTRIES:
				getEntries().clear();
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
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__LINE_NUMBER:
				return lineNumber != LINE_NUMBER_EDEFAULT;
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS:
				return comments != null && !comments.isEmpty();
			case ImpmodelPackage.OVERRIDE_CONFIGURATION__ENTRIES:
				return entries != null && !entries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == CommentContainer.class) {
			switch (derivedFeatureID) {
				case ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS: return ImpmodelPackage.COMMENT_CONTAINER__COMMENTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == CommentContainer.class) {
			switch (baseFeatureID) {
				case ImpmodelPackage.COMMENT_CONTAINER__COMMENTS: return ImpmodelPackage.OVERRIDE_CONFIGURATION__COMMENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer();
		result.append(ImpConstants.ORIDECONF_START);
		result.append(System.getProperty("line.separator"));
		for (ConfigEntry entry : getEntries()) {
			result.append(entry.toString());
			result.append(System.getProperty("line.separator"));
		}
		result.append(ImpConstants.DEFINE_END);
		return result.toString();
	}

} //OverrideConfigurationImpl
