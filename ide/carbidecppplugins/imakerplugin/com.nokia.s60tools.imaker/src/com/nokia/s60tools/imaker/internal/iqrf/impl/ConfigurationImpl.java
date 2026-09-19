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
package com.nokia.s60tools.imaker.internal.iqrf.impl;


import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;
import com.nokia.s60tools.imaker.internal.iqrf.util.IQRFUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl#getSettings <em>Settings</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl#getFilePath <em>File Path</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl#getTargetrefs <em>Targetrefs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigurationImpl extends EObjectImpl implements Configuration {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSettings() <em>Settings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSettings()
	 * @generated
	 * @ordered
	 */
	protected EList settings = null;

	/**
	 * The default value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_PATH_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilePath()
	 * @generated
	 * @ordered
	 */
	protected String filePath = FILE_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTargetrefs() <em>Targetrefs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetrefs()
	 * @generated
	 * @ordered
	 */
	protected EList targetrefs = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return IQRFPackage.Literals.CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IQRFPackage.CONFIGURATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSettings() {
		if (settings == null) {
			settings = new EObjectContainmentEList(Setting.class, this, IQRFPackage.CONFIGURATION__SETTINGS);
		}
		return settings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilePath(String newFilePath) {
		String oldFilePath = filePath;
		filePath = newFilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IQRFPackage.CONFIGURATION__FILE_PATH, oldFilePath, filePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTargetrefs() {
		if (targetrefs == null) {
			targetrefs = new EObjectResolvingEList(Target.class, this, IQRFPackage.CONFIGURATION__TARGETREFS);
		}
		return targetrefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public void addSetting(Setting set) {
		// Ensure that you remove @generated or mark it @generated NOT
		if (settings == null) {
			settings = new EObjectContainmentEList(Setting.class, this, IQRFPackage.CONFIGURATION__SETTINGS);
		}
		settings.add(set);
		//throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public void addTargetRef(Target target) {
		// Ensure that you remove @generated or mark it @generated NOT
		if (targetrefs == null) {
			targetrefs = new EObjectResolvingEList(Target.class, this, IQRFPackage.CONFIGURATION__TARGETREFS);
		}
		targetrefs.add(target);
		//throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IQRFPackage.CONFIGURATION__SETTINGS:
				return ((InternalEList)getSettings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IQRFPackage.CONFIGURATION__NAME:
				return getName();
			case IQRFPackage.CONFIGURATION__SETTINGS:
				return getSettings();
			case IQRFPackage.CONFIGURATION__FILE_PATH:
				return getFilePath();
			case IQRFPackage.CONFIGURATION__TARGETREFS:
				return getTargetrefs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IQRFPackage.CONFIGURATION__NAME:
				setName((String)newValue);
				return;
			case IQRFPackage.CONFIGURATION__SETTINGS:
				getSettings().clear();
				getSettings().addAll((Collection)newValue);
				return;
			case IQRFPackage.CONFIGURATION__FILE_PATH:
				setFilePath((String)newValue);
				return;
			case IQRFPackage.CONFIGURATION__TARGETREFS:
				getTargetrefs().clear();
				getTargetrefs().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case IQRFPackage.CONFIGURATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IQRFPackage.CONFIGURATION__SETTINGS:
				getSettings().clear();
				return;
			case IQRFPackage.CONFIGURATION__FILE_PATH:
				setFilePath(FILE_PATH_EDEFAULT);
				return;
			case IQRFPackage.CONFIGURATION__TARGETREFS:
				getTargetrefs().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IQRFPackage.CONFIGURATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IQRFPackage.CONFIGURATION__SETTINGS:
				return settings != null && !settings.isEmpty();
			case IQRFPackage.CONFIGURATION__FILE_PATH:
				return FILE_PATH_EDEFAULT == null ? filePath != null : !FILE_PATH_EDEFAULT.equals(filePath);
			case IQRFPackage.CONFIGURATION__TARGETREFS:
				return targetrefs != null && !targetrefs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", filePath: ");
		result.append(filePath);
		result.append(')');
		return result.toString();
	}
	
	public boolean equals(Object object) {
		if(object==null || !(object instanceof Configuration)) return false;
		Configuration other = (Configuration)object;
		boolean self = IQRFUtil.equals(getName(),other.getName()) && 
					   IQRFUtil.equals(getFilePath(),other.getFilePath());
		if(!self) return false;
		
		Iterator iter1 = getSettings().iterator();
		Iterator iter2 = other.getSettings().iterator();
		while (iter1.hasNext() || iter2.hasNext())
		{
			Setting s1, s2;
			s1 = (Setting)iter1.next();
			s2 = (Setting)iter2.next();
			if(!s1.equals(s2)) return false;
		}
		
		Iterator iterT1 = getTargetrefs().iterator();
		Iterator iterT2 = other.getTargetrefs().iterator();
		while (iterT1.hasNext() || iterT2.hasNext())
		{
			Target t1, t2;
			t1 = (Target)iterT1.next();
			t2 = (Target)iterT2.next();
			if(!t1.equals(t2)) return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int hashMultiplier = 41;
		int result = 7;
		result=result*hashMultiplier + getName().hashCode();
		result=result*hashMultiplier + getFilePath().hashCode();
		return result;
	}
} //ConfigurationImpl