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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage;
import com.nokia.s60tools.imaker.internal.iqrf.Interface;
import com.nokia.s60tools.imaker.internal.iqrf.Result;
import com.nokia.s60tools.imaker.internal.iqrf.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ResultImpl#getInterfaces <em>Interfaces</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ResultImpl#getConfigurations <em>Configurations</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ResultImpl#getTargets <em>Targets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResultImpl extends EObjectImpl implements Result {
	/**
	 * The cached value of the '{@link #getInterfaces() <em>Interfaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfaces()
	 * @generated
	 * @ordered
	 */
	protected EList interfaces = null;

	/**
	 * The cached value of the '{@link #getConfigurations() <em>Configurations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList configurations = null;

	/**
	 * The cached value of the '{@link #getTargets() <em>Targets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargets()
	 * @generated
	 * @ordered
	 */
	protected EList targets = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return IQRFPackage.Literals.RESULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInterfaces() {
		if (interfaces == null) {
			interfaces = new EObjectContainmentEList(Interface.class, this, IQRFPackage.RESULT__INTERFACES);
		}
		return interfaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConfigurations() {
		if (configurations == null) {
			configurations = new EObjectContainmentEList(Configuration.class, this, IQRFPackage.RESULT__CONFIGURATIONS);
		}
		return configurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTargets() {
		if (targets == null) {
			targets = new EObjectContainmentEList(Target.class, this, IQRFPackage.RESULT__TARGETS);
		}
		return targets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public void addConfiguration(Configuration config) {
		// Ensure that you remove @generated or mark it @generated NOT
		if (configurations == null) {
			configurations = new EObjectContainmentEList(Configuration.class, this, IQRFPackage.RESULT__CONFIGURATIONS);
		}
		configurations.add(config);
		//throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public void addInterface(Interface intf) {
		// Ensure that you remove @generated or mark it @generated NOT
		if (interfaces == null) {
			interfaces = new EObjectContainmentEList(Interface.class, this, IQRFPackage.RESULT__INTERFACES);
		}
		interfaces.add(intf);
		//throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public void addTarget(Target target) {
		// Ensure that you remove @generated or mark it @generated NOT
		if (this.targets == null) {
			this.targets = new EObjectContainmentEList(Target.class, this, IQRFPackage.RESULT__TARGETS);
		}
		this.targets.add(target);
		//throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IQRFPackage.RESULT__INTERFACES:
				return ((InternalEList)getInterfaces()).basicRemove(otherEnd, msgs);
			case IQRFPackage.RESULT__CONFIGURATIONS:
				return ((InternalEList)getConfigurations()).basicRemove(otherEnd, msgs);
			case IQRFPackage.RESULT__TARGETS:
				return ((InternalEList)getTargets()).basicRemove(otherEnd, msgs);
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
			case IQRFPackage.RESULT__INTERFACES:
				return getInterfaces();
			case IQRFPackage.RESULT__CONFIGURATIONS:
				return getConfigurations();
			case IQRFPackage.RESULT__TARGETS:
				return getTargets();
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
			case IQRFPackage.RESULT__INTERFACES:
				getInterfaces().clear();
				getInterfaces().addAll((Collection)newValue);
				return;
			case IQRFPackage.RESULT__CONFIGURATIONS:
				getConfigurations().clear();
				getConfigurations().addAll((Collection)newValue);
				return;
			case IQRFPackage.RESULT__TARGETS:
				getTargets().clear();
				getTargets().addAll((Collection)newValue);
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
			case IQRFPackage.RESULT__INTERFACES:
				getInterfaces().clear();
				return;
			case IQRFPackage.RESULT__CONFIGURATIONS:
				getConfigurations().clear();
				return;
			case IQRFPackage.RESULT__TARGETS:
				getTargets().clear();
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
			case IQRFPackage.RESULT__INTERFACES:
				return interfaces != null && !interfaces.isEmpty();
			case IQRFPackage.RESULT__CONFIGURATIONS:
				return configurations != null && !configurations.isEmpty();
			case IQRFPackage.RESULT__TARGETS:
				return targets != null && !targets.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	public boolean equals(Object object) {
		if(object==null || !(object instanceof Result)) return false;
		Result result = (Result)object;
		
		// Iterate through targets and compare their member values.
		Iterator iterT1 = getTargets().iterator();
		Iterator iterT2 = result.getTargets().iterator();
		while (iterT1.hasNext() || iterT2.hasNext())
		{
			Target t1, t2;
			t1 = (Target)iterT1.next();
			t2 = (Target)iterT2.next();
			if(!t1.equals(t2)) return false;
		}
		
		// Compare Interfaces
		// Iterate through interfaces and compare their member values.
		Iterator iterI1 = getInterfaces().iterator();
		Iterator iterI2 = result.getInterfaces().iterator();
		while (iterI1.hasNext() || iterI2.hasNext())
		{
			Interface i1, i2;
			i1 = (Interface)iterI1.next();
			i2 = (Interface)iterI2.next();
			if(!i1.equals(i2)) return false;
		}
		
		// Compare Configuration
		Iterator iterC1 = getConfigurations().iterator();
		Iterator iterC2 = result.getConfigurations().iterator();
		while (iterC1.hasNext() || iterC2.hasNext())
		{
			Configuration c1, c2;
			c1 = (Configuration)iterC1.next();
			c2 = (Configuration)iterC2.next();
			if(!c1.equals(c2)) return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int hashMultiplier = 41;
		int result = 7;
		result=result*hashMultiplier + getInterfaces().hashCode();
		result=result*hashMultiplier + getConfigurations().hashCode();
		result=result*hashMultiplier + getTargets().hashCode();
		return result;
	}
} //ResultImpl