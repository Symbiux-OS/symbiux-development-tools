
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

package com.nokia.s60tools.hticonnection.ui.views.main;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.HtiConnectionHelpContextIDs;
import com.nokia.s60tools.hticonnection.actions.ClearScreenAction;
import com.nokia.s60tools.hticonnection.actions.OpenPreferencePageAction;
import com.nokia.s60tools.hticonnection.actions.ScrollLockAction;
import com.nokia.s60tools.hticonnection.actions.SelectAllAction;
import com.nokia.s60tools.hticonnection.actions.StartStopGatewayAction;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.ui.ICopyActionHandler;
import com.nokia.s60tools.ui.IStringProvider;
import com.nokia.s60tools.ui.StringArrayClipboardCopyHandler;
import com.nokia.s60tools.ui.actions.CopyFromStringProviderToClipboardAction;

/**
 * This class comprises the Main View of the HTI API 
 * application.
 */
public class MainView extends ViewPart implements IStringProvider {
	
	/**
	 * We can get view ID at runtime once the view is instantiated, but we
	 * also need static access to ID in order to be able to invoke the view.
	 */
	public static final String ID = "com.nokia.s60tools.hticonnection.ui.views.main.MainView"; //$NON-NLS-1$
	/**
	 * Viewer for showing text output.
	 */
	private MainTextViewer textViewer;
	
	//
	// Actions.
	//
	private StartStopGatewayAction startStopGatewayAction;
	private Action preferencesAction;
	private Action clearScreenAction;
	private Action scrollLockAction;
	private Action copyAction;
	private Action selectAllAction;

	/**
	 * The constructor.
	 */
	public MainView() {
	}

	/**
	 * This is called by framework when the controls for
	 * the view should be created.
	 */
	public void createPartControl(Composite parent) {

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

		createViewContents(parent);

		//
		// Doing other initializations that may refer to the component
		// that has been created above.
		//

		hookContextMenu();
		contributeToActionBars();
		
		// Connection status needs to be updated, if view was closed/re-opened.
		HtiConnection.getInstance().updateConnectionStatus();
	}

	/**
	 * Creates text textViewer for viewing output.
	 * @param parent Parent composite.
	 */
	private void createViewContents(Composite parent) {
		textViewer = new MainTextViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		textViewer.initializeSettings();

		// Creating selection changed listener to enable/disable copy action as needed.
		ISelectionChangedListener consoleSelectionListener = new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				int length = textViewer.getSelectedRange().y;
				// Action is enabled when length of selection is different than zero;
				copyAction.setEnabled(length != 0);
			}};
		textViewer.addSelectionChangedListener(consoleSelectionListener);
		
		// Setting context help IDs	
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(textViewer.getControl(), 
	    		HtiConnectionHelpContextIDs.HTI_CONNECTION_MAIN_VIEW);
	}
	
	/**
	 * Hooking context menu.
	 */
	private void hookContextMenu() {
		
		//
		// Context menu for text viewer
		//
		MenuManager menuMgr = new MenuManager("#TextViewerPopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				MainView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(textViewer.getControl());
		textViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, textViewer);
	}

	/**
	 * Filling action bars.
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillViewPullDownMenu(bars.getMenuManager());
		fillViewToolBar(bars.getToolBarManager());
	}

	/**
	 * Filling pull down menu.
	 * @param manager Menu manager. 
	 */
	private void fillViewPullDownMenu(IMenuManager manager) {
		manager.add(preferencesAction);
	}

	/**
	 * Filling context menu.
	 * @param manager Menu manager.
	 */
	private void fillContextMenu(IMenuManager manager) {
		manager.add(copyAction);
		manager.add(selectAllAction);
		manager.add(new Separator());
		manager.add(clearScreenAction);
		manager.add(new Separator());
		manager.add(scrollLockAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	/**
	 * Filling toolbar.
	 * @param manager Menu manager.
	 */
	private void fillViewToolBar(IToolBarManager manager) {
		manager.add(startStopGatewayAction);
		manager.add(new Separator());
		manager.add(preferencesAction);
		manager.add(new Separator());
		manager.add(clearScreenAction);
		manager.add(scrollLockAction);
	}

	/**
	 * Creating all needed actions.
	 */
	private void createMainMenuActions() {
		preferencesAction = new OpenPreferencePageAction();
		clearScreenAction = new ClearScreenAction(this);
		scrollLockAction = new ScrollLockAction(this);
		selectAllAction = new SelectAllAction(this);
		startStopGatewayAction = new StartStopGatewayAction();
		
		// Menu is no longer needed after "current connection" -feature
		/*
		// Creator connection menu.
		IMenuCreator creator = new ConnectionMenuCreator();
		startStopGatewayAction.setMenuCreator(creator);
		*/
		
		StringArrayClipboardCopyHandler copyHandler = new StringArrayClipboardCopyHandler();		
		copyAction = new CopyFromStringProviderToClipboardAction(this, new ICopyActionHandler[]{ copyHandler });
		copyAction.setEnabled(false);
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
		textViewer.refresh();
	}
		
	/**
	 * Sets enabled/disabled states for actions commands
	 * on this view, based on the current application state.
	 * This method should be called whenever an operation is
	 * started or stopped that might have effect on action 
	 * button states.
	 */
	public void updateActionButtonStates(){
		// Setting state depending on datagateway status.
		
		startStopGatewayAction.setRunning(
				HtiConnection.getInstance().getConnectionStatus() != ConnectionStatus.SHUTDOWN);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {
		LogDocument.getInstance().removeDocumentListener(textViewer);
		super.dispose();
	}

	/**
	 * Enables to open main view from classes that don't have
	 * reference to the main view instance and are not UI threads.
	 * This method only opens the view if it was not visible.
	 * @param setFocus True if focus should be set for view.
	 */
	public static void openMainViewAsync(final boolean setFocus){
		// We want to activate/create the view if it isn't open.
		// do not need to use the instance after it is returned.
		Runnable dialogRunnable = new Runnable() {
			public void run() {
				try {
					MainView.getViewInstance(setFocus);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		};
		// Opening main view in default UI thread.
		Display.getDefault().asyncExec(dialogRunnable);
	}
	
	/**
	 * Enables to get reference of the main view
	 * from the classes that do not actually
	 * have reference to the main view instance.
	 * This method opens opens up the view if it
	 * was not visible.
	 * @param setFocus True if focus should be set for view.
	 * @return Instance of main view.
	 * @throws PartInitException
	 */
	public static MainView getViewInstance(boolean setFocus) throws PartInitException{
		
		IWorkbenchPage page = HtiApiActivator.getCurrentlyActivePage();
		
		boolean viewAlreadyVisible = false;
		IViewPart viewPart = null;
		
		// Checking if view is already open
		IViewReference[] viewRefs = page.getViewReferences();
		for (int i = 0; i < viewRefs.length; i++) {
			IViewReference reference = viewRefs[i];
			String id = reference.getId();
			if(id.equalsIgnoreCase(MainView.ID)){
				viewAlreadyVisible = true;
				// Found, restoring the view
				viewPart = reference.getView(true);
				// Activating the view if wanted.
				if(setFocus) {
					page.activate(viewPart);
				}
			}
		}
		// View was not opened
		if(! viewAlreadyVisible){
			if(setFocus){
				// Opening and setting focus for view.
				viewPart = page.showView(MainView.ID);
			} else {
				// Opening the view, but not setting focus.
				viewPart = page.showView(MainView.ID, null, IWorkbenchPage.VIEW_VISIBLE);
			}
		}	
		return ((MainView) viewPart);
	}
	
	/**
	 * Passing the focus request to the textViewer's control.
	 */
	public void setFocus() {
		textViewer.getControl().setFocus();
	}

	/**
	 * Returns console viewer.
	 * @return Viewer used in console.
	 */
	public MainTextViewer getMainTextViewer(){
		return textViewer;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.ui.IStringProvider#getString()
	 */
	public String getString() {
		// Getting current selection and returning it.
		return textViewer.getSelectedText();
	}
}