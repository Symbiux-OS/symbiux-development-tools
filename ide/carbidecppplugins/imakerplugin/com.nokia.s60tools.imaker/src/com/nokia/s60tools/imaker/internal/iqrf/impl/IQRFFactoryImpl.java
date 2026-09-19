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


import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.IMaker;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage;
import com.nokia.s60tools.imaker.internal.iqrf.Interface;
import com.nokia.s60tools.imaker.internal.iqrf.Result;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IQRFFactoryImpl extends EFactoryImpl implements IQRFFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IQRFFactory init() {
		try {
			IQRFFactory theIQRFFactory = (IQRFFactory)EPackage.Registry.INSTANCE.getEFactory("iqrf"); 
			if (theIQRFFactory != null) {
				return theIQRFFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IQRFFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IQRFFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IQRFPackage.RESULT: return createResult();
			case IQRFPackage.CONFIGURATION: return createConfiguration();
			case IQRFPackage.SETTING: return createSetting();
			case IQRFPackage.INTERFACE: return createInterface();
			case IQRFPackage.CONFIGURATION_ELEMENT: return createConfigurationElement();
			case IQRFPackage.TARGET: return createTarget();
			case IQRFPackage.IMAKER: return createIMaker();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Result createResult() {
		ResultImpl result = new ResultImpl();
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration createConfiguration() {
		ConfigurationImpl configuration = new ConfigurationImpl();
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Setting createSetting() {
		SettingImpl setting = new SettingImpl();
		return setting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface createInterface() {
		InterfaceImpl interface_ = new InterfaceImpl();
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationElement createConfigurationElement() {
		ConfigurationElementImpl configurationElement = new ConfigurationElementImpl();
		return configurationElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target createTarget() {
		TargetImpl target = new TargetImpl();
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IMaker createIMaker() {
		IMakerImpl iMaker = new IMakerImpl();
		return iMaker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IQRFPackage getIQRFPackage() {
		return (IQRFPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static IQRFPackage getPackage() {
		return IQRFPackage.eINSTANCE;
	}

} //IQRFFactoryImpl
