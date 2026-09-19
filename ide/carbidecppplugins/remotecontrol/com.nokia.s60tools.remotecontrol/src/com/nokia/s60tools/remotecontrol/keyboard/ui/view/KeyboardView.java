/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.remotecontrol.keyboard.ui.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.RemoteControlHelpContextIDs;
import com.nokia.s60tools.remotecontrol.keyboard.ui.actions.NaviPaneAction;
import com.nokia.s60tools.remotecontrol.keyboard.ui.actions.SwitchKeyboardAction;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferenceConstants.KeyboardLayout;

/**
 * This class comprises the Main View of the AppDep
 * application.
 * 
 * This example main view demonstrated the creation of a 
 * view with two tabbed view. The second tab also demonstrated
 * how context menus can be attached to some control
 */
public class KeyboardView extends ViewPart {
	 
	/**
	 * We can get view ID at runtime once the view is instantiated, but we
	 * also need static access to ID in order to be able to invoke the view.
	 */
	public static final String ID = "com.nokia.s60tools.remotecontrol.keyboard.ui.view.KeyboardView"; //$NON-NLS-1$
		
	//
	// Actions
	//
	
	/**
	 * Navi pane action
	 */
	private Action naviPaneAction;
	
	/**
	 * Switch keyboard action
	 */
	private Action switchKeyboardAction;
	
	//
	// Controls and related classes (providers etc.)
	// 
	
	private Composite container = null;
	/**
	 * Navi pane composite
	 */
	private NaviPaneComposite naviPane = null;	
	
	/**
	 * Simple keyboard composite
	 */
	private SimpleKeyboardComposite simpleKeyboard = null;					
	
	/**
	 * QWERTY keyboard composite
	 */
	private QwertyKeyboardComposite qwertyKeyboard = null;	
	
	/**
	 * Parent composite
	 */
	private Composite parent;

	/**
	 * The constructor.
	 */
	public KeyboardView() {
	}

	/**
	 * This is called by framework when the controls for
	 * the view should be created.
	 */
	public void createPartControl(Composite parent) {
		this.parent = parent;
		
		try {
			//
			// Actions invoked by content providers may set enable/disable
			// states for the actions, therefore all the action has to be
			// created before creating the controls. This makes sure that
			// it is safe to refer to all the actions after this point.
			//
			createMainMenuActions();
			
			//
			// Creating controls
			//
			createLayouts();
			
			//
			// Doing other initializations that may refer to the component
			// that has been created above.
			//
			fillViewActionBars();
			
			// Setting context help IDs	
			PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, 
		    		RemoteControlHelpContextIDs.REMOTE_CONTROL_KEYBOARD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create keyboard layouts. 
	 */
	private void createLayouts() {
		// Dispose container and layouts if exists and create new ones 
		if (container != null) {
			container.dispose();
		}
		if (naviPane != null) {
			naviPane.dispose();
			naviPane = null;
		}
		if (simpleKeyboard != null) {
			simpleKeyboard.dispose();
			simpleKeyboard = null;
		}
		if (qwertyKeyboard != null) {
			qwertyKeyboard.dispose();
			qwertyKeyboard = null;
		}
		
		container = new Composite(parent, SWT.SIMPLE);
		container.setLayout(new GridLayout(1, false));
		
		// Check from preferences which layouts are selected to be shown
		// and create them
		if (RCPreferences.getShowNaviPane()) {
			naviPane = new NaviPaneComposite(container, SWT.NONE);
			naviPane.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_CENTER));
		}
		if (RCPreferences.getKeyboardLayout() == KeyboardLayout.SIMPLE) {
			simpleKeyboard = new SimpleKeyboardComposite(container, SWT.NONE);
			simpleKeyboard.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_CENTER));
		} else {
			qwertyKeyboard = new QwertyKeyboardComposite(container, SWT.NONE);
			qwertyKeyboard.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_CENTER));
		}
		
		// Draw components
		parent.layout(true);
	}

	/**
	 * Create main menu actions in here. The same actions
	 * can be also used to populate context menus, if needed.
	 */
	private void createMainMenuActions() {
		// Create actions
		naviPaneAction = new NaviPaneAction(this);
		switchKeyboardAction = new SwitchKeyboardAction(this);
		
		// Set action states according to preferences
		naviPaneAction.setChecked(RCPreferences.getShowNaviPane());
	}

	/**
	 * This method fills action bars (no need for further modifications).
	 */
	private void fillViewActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillViewMainMenu(bars.getMenuManager());
		fillViewToolBar(bars.getToolBarManager());
	}

	/**
	 * View's main menu is populated in here.
	 * @param manager Menu manager instance.
	 */
	private void fillViewMainMenu(IMenuManager manager) {
		manager.add(naviPaneAction);
		manager.add(new Separator());
		manager.add(switchKeyboardAction);
		manager.add(new Separator());
	}

	/**
	 * View's tool bar is populated in here.
	 * @param manager Tool bar instance.
	 */
	private void fillViewToolBar(IToolBarManager manager) {
		
		manager.add(naviPaneAction);
		manager.add(new Separator());
		manager.add(switchKeyboardAction);
	}
	
	/**
	 * Focus request should be passed to the view's primary control.
	 */
	public void setFocus() {
		parent.setFocus();
	}
	
	/**
	 * Allows other classes to update content description.
	 * @param newContentDescription New description.
	 */
	public void updateDescription(String newContentDescription){
		setContentDescription(newContentDescription);
		IToolBarManager tbManager = getViewSite().getActionBars().getToolBarManager();
		tbManager.update(true);
	}

	/**
	 * The view should refresh all its UI components in this method.
	 */
	public void refresh(){
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {
		super.dispose();
		if (naviPane != null) {
			naviPane.dispose();
			naviPane = null;
		}
		if (simpleKeyboard != null) {
			simpleKeyboard.dispose();
			simpleKeyboard = null;
		}
		if (qwertyKeyboard != null) {
			qwertyKeyboard.dispose();
			qwertyKeyboard = null;
		}
	}

	/**
	 * Enables to get reference of the main view
	 * from the classes that do not actually
	 * have reference to the main view instance.
	 * This method opens activates/opens up the 
	 * view if it was not visible.
	 * @throws PartInitException 
	 */
	public static KeyboardView getViewInstance() throws PartInitException{
		
		IWorkbenchPage page = RemoteControlActivator.getCurrentlyActivePage();
		
		boolean viewAlreadyVisible = false;
		IViewPart viewPart = null;
		
		// Checking if view is already open
		IViewReference[] viewRefs = page.getViewReferences();
		for (int i = 0; i < viewRefs.length; i++) {
			IViewReference reference = viewRefs[i];
			String id = reference.getId();
			if(id.equalsIgnoreCase(KeyboardView.ID)){
				viewAlreadyVisible = true;
				// Found, restoring the view
				viewPart = reference.getView(true);
				page.activate(viewPart);
			}
		}
		// View was not opened
		if(! viewAlreadyVisible){
			viewPart = page.showView(KeyboardView.ID);							
		}	
		return ((KeyboardView) viewPart);
	}

	/**
	 * Enables update request for the main view
	 * also from the classes that do not actually
	 * have reference to the main view instance.
	 */
	public static void update(){
		try {		
			getViewInstance().inputUpdated();
			
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The view should refresh all its UI components in this method.
	 */
	private void inputUpdated() {		
	}
	
	/** 
	 * Show/hide navi pane
	 * @param show Shows navi pane if true, else hides it
	 */
	public void showNaviPane(boolean show) {
		RCPreferences.setShowNaviPane(show);
		createLayouts();
	}
	
	/** 
	 * Switch keyboard layout between simple and qwerty
	 * @param layout layout to be shown
	*/
	public void switchKeyboardLayout(KeyboardLayout layout) {
		RCPreferences.setKeyboardLayout(layout);
		createLayouts();
	}
}
