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

package com.nokia.s60tools.imaker.internal.impmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comment Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.CommentContainer#getComments <em>Comments</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage#getCommentContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface CommentContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.s60tools.imaker.internal.impmodel.Comment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpmodelPackage#getCommentContainer_Comments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Comment> getComments();

} // CommentContainer
