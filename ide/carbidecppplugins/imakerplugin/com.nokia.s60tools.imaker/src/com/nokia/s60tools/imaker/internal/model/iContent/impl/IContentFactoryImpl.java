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
package com.nokia.s60tools.imaker.internal.model.iContent.impl;

import com.nokia.s60tools.imaker.internal.model.iContent.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.nokia.s60tools.imaker.internal.model.ImakerProperties;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage;
import com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;
import com.nokia.s60tools.imaker.internal.model.iContent.ImageContent;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IContentFactoryImpl extends EFactoryImpl implements IContentFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IContentFactory init() {
		try {
			IContentFactory theIContentFactory = (IContentFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.nokia.com"); 
			if (theIContentFactory != null) {
				return theIContentFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IContentFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IContentFactoryImpl() {
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
			case IContentPackage.IMAGE_CONTENT: return createImageContent();
			case IContentPackage.IBY_ENTRY: return createIbyEntry();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case IContentPackage.IMAGESECTION:
				return createIMAGESECTIONFromString(eDataType, initialValue);
			case IContentPackage.ACTION:
				return createACTIONFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case IContentPackage.IMAGESECTION:
				return convertIMAGESECTIONToString(eDataType, instanceValue);
			case IContentPackage.ACTION:
				return convertACTIONToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImageContent createImageContent() {
		ImageContentImpl imageContent = new ImageContentImpl();
		return imageContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IbyEntry createIbyEntry() {
		IbyEntryImpl ibyEntry = new IbyEntryImpl();
		return ibyEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IMAGESECTION createIMAGESECTIONFromString(EDataType eDataType, String initialValue) {
		IMAGESECTION result = IMAGESECTION.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIMAGESECTIONToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ACTION createACTIONFromString(EDataType eDataType, String initialValue) {
		ACTION result = ACTION.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertACTIONToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IContentPackage getIContentPackage() {
		return (IContentPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IContentPackage getPackage() {
		return IContentPackage.eINSTANCE;
	}

	public IbyEntry createEntryFromString(String initialValue) {
		String[] parts = initialValue.split(ImakerProperties.IBYENTRY_FIELDS_SEPARATOR);
		if(parts.length>=4) {
			IbyEntry entry = IContentFactory.eINSTANCE.createIbyEntry();
			entry.setEnabled(new Boolean(parts[0]));
//			entry.setDebug(new Boolean(parts[1]));
			entry.setFile(parts[1]);
			entry.setTarget(parts[2]);
			entry.setLocation(IMAGESECTION.get(parts[3]));
			return entry;
		}
		return null;
	}	
} //IContentFactoryImpl
