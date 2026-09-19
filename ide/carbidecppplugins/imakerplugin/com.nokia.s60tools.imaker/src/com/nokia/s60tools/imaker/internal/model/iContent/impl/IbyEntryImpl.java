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

import com.nokia.s60tools.imaker.internal.model.iContent.ACTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentPackage;
import com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Iby Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl#getFile <em>File</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl#getAction <em>Action</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.model.iContent.impl.IbyEntryImpl#getStatusMessage <em>Status Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IbyEntryImpl extends EObjectImpl implements IbyEntry {
	/**
	 * The default value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected String file = FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTarget() <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected String target = TARGET_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final IMAGESECTION LOCATION_EDEFAULT = IMAGESECTION.CORE;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected IMAGESECTION location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getAction() <em>Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected static final ACTION ACTION_EDEFAULT = ACTION.UDEB;

	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected ACTION action = ACTION_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatusMessage() <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatusMessage() <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusMessage()
	 * @generated
	 * @ordered
	 */
	protected String statusMessage = STATUS_MESSAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IbyEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IContentPackage.Literals.IBY_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFile(String newFile) {
		String oldFile = file;
		file = newFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IContentPackage.IBY_ENTRY__FILE, oldFile, file));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(String newTarget) {
		String oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IContentPackage.IBY_ENTRY__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IMAGESECTION getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(IMAGESECTION newLocation) {
		IMAGESECTION oldLocation = location;
		location = newLocation == null ? LOCATION_EDEFAULT : newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IContentPackage.IBY_ENTRY__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ACTION getAction() {
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAction(ACTION newAction) {
		ACTION oldAction = action;
		action = newAction == null ? ACTION_EDEFAULT : newAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IContentPackage.IBY_ENTRY__ACTION, oldAction, action));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IContentPackage.IBY_ENTRY__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusMessage(String newStatusMessage) {
		String oldStatusMessage = statusMessage;
		statusMessage = newStatusMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IContentPackage.IBY_ENTRY__STATUS_MESSAGE, oldStatusMessage, statusMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IContentPackage.IBY_ENTRY__FILE:
				return getFile();
			case IContentPackage.IBY_ENTRY__TARGET:
				return getTarget();
			case IContentPackage.IBY_ENTRY__LOCATION:
				return getLocation();
			case IContentPackage.IBY_ENTRY__ACTION:
				return getAction();
			case IContentPackage.IBY_ENTRY__ENABLED:
				return isEnabled();
			case IContentPackage.IBY_ENTRY__STATUS_MESSAGE:
				return getStatusMessage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IContentPackage.IBY_ENTRY__FILE:
				setFile((String)newValue);
				return;
			case IContentPackage.IBY_ENTRY__TARGET:
				setTarget((String)newValue);
				return;
			case IContentPackage.IBY_ENTRY__LOCATION:
				setLocation((IMAGESECTION)newValue);
				return;
			case IContentPackage.IBY_ENTRY__ACTION:
				setAction((ACTION)newValue);
				return;
			case IContentPackage.IBY_ENTRY__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case IContentPackage.IBY_ENTRY__STATUS_MESSAGE:
				setStatusMessage((String)newValue);
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
			case IContentPackage.IBY_ENTRY__FILE:
				setFile(FILE_EDEFAULT);
				return;
			case IContentPackage.IBY_ENTRY__TARGET:
				setTarget(TARGET_EDEFAULT);
				return;
			case IContentPackage.IBY_ENTRY__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case IContentPackage.IBY_ENTRY__ACTION:
				setAction(ACTION_EDEFAULT);
				return;
			case IContentPackage.IBY_ENTRY__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case IContentPackage.IBY_ENTRY__STATUS_MESSAGE:
				setStatusMessage(STATUS_MESSAGE_EDEFAULT);
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
			case IContentPackage.IBY_ENTRY__FILE:
				return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
			case IContentPackage.IBY_ENTRY__TARGET:
				return TARGET_EDEFAULT == null ? target != null : !TARGET_EDEFAULT.equals(target);
			case IContentPackage.IBY_ENTRY__LOCATION:
				return location != LOCATION_EDEFAULT;
			case IContentPackage.IBY_ENTRY__ACTION:
				return action != ACTION_EDEFAULT;
			case IContentPackage.IBY_ENTRY__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case IContentPackage.IBY_ENTRY__STATUS_MESSAGE:
				return STATUS_MESSAGE_EDEFAULT == null ? statusMessage != null : !STATUS_MESSAGE_EDEFAULT.equals(statusMessage);
		}
		return super.eIsSet(featureID);
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
		result.append("data-override=");
		result.append(file);
		result.append(" ");
		result.append(target);
		return result.toString();
	}

//	@Override
	public void append(StringBuffer sb) {
		sb.append(isEnabled());
		sb.append(";");
		sb.append(getAction().getLiteral());
		sb.append(";");
		sb.append(getFile());
		sb.append(";");
		sb.append(getTarget());
		sb.append(";");
		sb.append(getLocation());
	}

	/*
	public boolean equals(Object obj) {
		if(obj instanceof IbyEntry) {
			IbyEntry other = (IbyEntry) obj;
			if (super.equals(obj)==false) {
				return false;
			}
			String f = getFile();
			String t = getTarget();
			if(f!=null&&t!=null&&f.equals(other.getFile())&&t.equals(other.getTarget())) {
				return true;
			} else if(f!=null&&t==null&&f.equals(other.getFile())&&other.getTarget()==null) {
				return true;
			} else if(f==null&&t!=null&&other.getFile()==null&&t.equals(other.getTarget())) {
				return true;
			} else if(f==null&&t==null&&other.getFile()==null&&other.getTarget()==null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	*/
} //IbyEntryImpl
