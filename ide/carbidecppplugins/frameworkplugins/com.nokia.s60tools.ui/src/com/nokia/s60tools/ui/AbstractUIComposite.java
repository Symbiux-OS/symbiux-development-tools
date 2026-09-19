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
package com.nokia.s60tools.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

/**
 * Abstract class that defines an abstract template 
 * for the composites that should be used to populate
 * the contents a tab area or sub are inside a tab.
 * 
 * Example usage (without comment blocks for making example shorter):
 * 
 * <code>
 * public class MyUIComposite extends AbstractUIComposite {
 *	private final int COLUMN_COUNT = 1;
 *	
 *  public MyUIComposite(Composite parentComposite) {
 * 		super(parentComposite);
 *	}
 *
 *	protected Layout createLayout() {
 *		return new GridLayout(COLUMN_COUNT, false);
 *	}
 *
 *	protected Object createLayoutData(){
 *		return 	new GridData(GridData.FILL_HORIZONTAL);
 *	}
 *	
 *	protected void createControls() {
 *		Label testLabel = new Label(this, SWT.HORIZONTAL | SWT.LEFT);
 *		testLabel.setText("TODO: Add widgets for the UI composite here...");
 *	}
 * </code>
 */
public abstract class AbstractUIComposite extends Composite {
	
	/**
	 * Constructor.
	 * @param parentComposite	Parent composite for the created composite.
	 * @param style				The used style for the composite.
	 */
	public AbstractUIComposite(Composite parentComposite, int style) {
		super(parentComposite, style);
		initComposite();
	}
	
	/**
	 * Constructor.
	 * @param parentComposite	Parent composite for the created composite.
	 */
	public AbstractUIComposite(Composite parentComposite) {
		super(parentComposite, SWT.NONE);
		initComposite();
	}
	
	/**
	 * Performs template methods that take care
	 * of finalizing composite construction.
	 */
	private void initComposite(){
		setLayout(createLayout());
		setLayoutData(createLayoutData());
		createControls();
	}
	
	/**
	 * Template method to be implemented in sub classes.
	 * Sub class should initialize the composite's layout
	 * in the implementation of this method.
	 * @return Layout for the composite.
	 */
	protected abstract Layout createLayout();

	/**
	 * Template method to be implemented in sub classes.
	 * Sub class should initialize the composite's layout data
	 * in the implementation of this method.
	 * @return Layout for the composite.
	 */
	protected abstract Object createLayoutData();

	/**
	 * Template method to be implemented in sub classes.
	 * Sub class should create all the composite's controls
	 * in the implementation of this method.
	 */
	protected abstract void createControls();
}
