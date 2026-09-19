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

package com.nokia.s60tools.imaker.internal.iqrf;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory
 * @model kind="package"
 * @generated
 */
public interface IQRFPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "iQRF";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "iqrf";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IQRFPackage eINSTANCE = com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ResultImpl <em>Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.ResultImpl
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getResult()
	 * @generated
	 */
	int RESULT = 0;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__INTERFACES = 0;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__CONFIGURATIONS = 1;

	/**
	 * The feature id for the '<em><b>Targets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__TARGETS = 2;

	/**
	 * The number of structural features of the '<em>Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Settings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__SETTINGS = 1;

	/**
	 * The feature id for the '<em><b>File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__FILE_PATH = 2;

	/**
	 * The feature id for the '<em><b>Targetrefs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__TARGETREFS = 3;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.SettingImpl <em>Setting</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.SettingImpl
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getSetting()
	 * @generated
	 */
	int SETTING = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING__REF = 2;

	/**
	 * The number of structural features of the '<em>Setting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.InterfaceImpl <em>Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.InterfaceImpl
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getInterface()
	 * @generated
	 */
	int INTERFACE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Configuration Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__CONFIGURATION_ELEMENTS = 1;

	/**
	 * The number of structural features of the '<em>Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationElementImpl <em>Configuration Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationElementImpl
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getConfigurationElement()
	 * @generated
	 */
	int CONFIGURATION_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT__VALUES = 2;

	/**
	 * The number of structural features of the '<em>Configuration Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.TargetImpl
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.IMakerImpl <em>IMaker</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IMakerImpl
	 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getIMaker()
	 * @generated
	 */
	int IMAKER = 6;

	/**
	 * The feature id for the '<em><b>Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAKER__RESULT = 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAKER__QUERY = 1;

	/**
	 * The number of structural features of the '<em>IMaker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAKER_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.iqrf.Result <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Result</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Result
	 * @generated
	 */
	EClass getResult();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.iqrf.Result#getInterfaces <em>Interfaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interfaces</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Result#getInterfaces()
	 * @see #getResult()
	 * @generated
	 */
	EReference getResult_Interfaces();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.iqrf.Result#getConfigurations <em>Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configurations</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Result#getConfigurations()
	 * @see #getResult()
	 * @generated
	 */
	EReference getResult_Configurations();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.iqrf.Result#getTargets <em>Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Targets</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Result#getTargets()
	 * @see #getResult()
	 * @generated
	 */
	EReference getResult_Targets();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Configuration#getName()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getSettings <em>Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Settings</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Configuration#getSettings()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Settings();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getFilePath <em>File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Path</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Configuration#getFilePath()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_FilePath();

	/**
	 * Returns the meta object for the reference list '{@link com.nokia.s60tools.imaker.internal.iqrf.Configuration#getTargetrefs <em>Targetrefs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Targetrefs</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Configuration#getTargetrefs()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Targetrefs();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.iqrf.Setting <em>Setting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Setting</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Setting
	 * @generated
	 */
	EClass getSetting();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.Setting#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Setting#getName()
	 * @see #getSetting()
	 * @generated
	 */
	EAttribute getSetting_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.Setting#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Setting#getValue()
	 * @see #getSetting()
	 * @generated
	 */
	EAttribute getSetting_Value();

	/**
	 * Returns the meta object for the reference '{@link com.nokia.s60tools.imaker.internal.iqrf.Setting#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ref</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Setting#getRef()
	 * @see #getSetting()
	 * @generated
	 */
	EReference getSetting_Ref();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.iqrf.Interface <em>Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Interface
	 * @generated
	 */
	EClass getInterface();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.Interface#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Interface#getName()
	 * @see #getInterface()
	 * @generated
	 */
	EAttribute getInterface_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.s60tools.imaker.internal.iqrf.Interface#getConfigurationElements <em>Configuration Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configuration Elements</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Interface#getConfigurationElements()
	 * @see #getInterface()
	 * @generated
	 */
	EReference getInterface_ConfigurationElements();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement <em>Configuration Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration Element</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement
	 * @generated
	 */
	EClass getConfigurationElement();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement#getName()
	 * @see #getConfigurationElement()
	 * @generated
	 */
	EAttribute getConfigurationElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement#getDescription()
	 * @see #getConfigurationElement()
	 * @generated
	 */
	EAttribute getConfigurationElement_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Values</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement#getValues()
	 * @see #getConfigurationElement()
	 * @generated
	 */
	EAttribute getConfigurationElement_Values();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.iqrf.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Target
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.Target#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Target#getName()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.Target#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.Target#getDescription()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_Description();

	/**
	 * Returns the meta object for class '{@link com.nokia.s60tools.imaker.internal.iqrf.IMaker <em>IMaker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IMaker</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IMaker
	 * @generated
	 */
	EClass getIMaker();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.s60tools.imaker.internal.iqrf.IMaker#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Result</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IMaker#getResult()
	 * @see #getIMaker()
	 * @generated
	 */
	EReference getIMaker_Result();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.s60tools.imaker.internal.iqrf.IMaker#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IMaker#getQuery()
	 * @see #getIMaker()
	 * @generated
	 */
	EAttribute getIMaker_Query();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IQRFFactory getIQRFFactory();

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
	interface Literals  {
		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ResultImpl <em>Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.ResultImpl
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getResult()
		 * @generated
		 */
		EClass RESULT = eINSTANCE.getResult();

		/**
		 * The meta object literal for the '<em><b>Interfaces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESULT__INTERFACES = eINSTANCE.getResult_Interfaces();

		/**
		 * The meta object literal for the '<em><b>Configurations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESULT__CONFIGURATIONS = eINSTANCE.getResult_Configurations();

		/**
		 * The meta object literal for the '<em><b>Targets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESULT__TARGETS = eINSTANCE.getResult_Targets();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationImpl
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__NAME = eINSTANCE.getConfiguration_Name();

		/**
		 * The meta object literal for the '<em><b>Settings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__SETTINGS = eINSTANCE.getConfiguration_Settings();

		/**
		 * The meta object literal for the '<em><b>File Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__FILE_PATH = eINSTANCE.getConfiguration_FilePath();

		/**
		 * The meta object literal for the '<em><b>Targetrefs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__TARGETREFS = eINSTANCE.getConfiguration_Targetrefs();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.SettingImpl <em>Setting</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.SettingImpl
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getSetting()
		 * @generated
		 */
		EClass SETTING = eINSTANCE.getSetting();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SETTING__NAME = eINSTANCE.getSetting_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SETTING__VALUE = eINSTANCE.getSetting_Value();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SETTING__REF = eINSTANCE.getSetting_Ref();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.InterfaceImpl <em>Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.InterfaceImpl
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getInterface()
		 * @generated
		 */
		EClass INTERFACE = eINSTANCE.getInterface();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERFACE__NAME = eINSTANCE.getInterface_Name();

		/**
		 * The meta object literal for the '<em><b>Configuration Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE__CONFIGURATION_ELEMENTS = eINSTANCE.getInterface_ConfigurationElements();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationElementImpl <em>Configuration Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.ConfigurationElementImpl
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getConfigurationElement()
		 * @generated
		 */
		EClass CONFIGURATION_ELEMENT = eINSTANCE.getConfigurationElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_ELEMENT__NAME = eINSTANCE.getConfigurationElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_ELEMENT__DESCRIPTION = eINSTANCE.getConfigurationElement_Description();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_ELEMENT__VALUES = eINSTANCE.getConfigurationElement_Values();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.TargetImpl
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getTarget()
		 * @generated
		 */
		EClass TARGET = eINSTANCE.getTarget();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__NAME = eINSTANCE.getTarget_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__DESCRIPTION = eINSTANCE.getTarget_Description();

		/**
		 * The meta object literal for the '{@link com.nokia.s60tools.imaker.internal.iqrf.impl.IMakerImpl <em>IMaker</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IMakerImpl
		 * @see com.nokia.s60tools.imaker.internal.iqrf.impl.IQRFPackageImpl#getIMaker()
		 * @generated
		 */
		EClass IMAKER = eINSTANCE.getIMaker();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMAKER__RESULT = eINSTANCE.getIMaker_Result();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAKER__QUERY = eINSTANCE.getIMaker_Query();

	}

} //IQRFPackage
