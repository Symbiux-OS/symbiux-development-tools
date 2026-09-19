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

package com.nokia.s60tools.remotecontrol.ui.views.main;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ViewPart;

import com.nokia.s60tools.hticonnection.listener.HtiConnectionManager;
import com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.RemoteControlHelpContextIDs;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpView;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.screen.ui.view.ScreenView;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * This class comprises the Main View of the Remote Control
 * application.
 */
public class MainView extends ViewPart implements SelectionListener,
										IHtiConnectionListener {
	 
	/**
	 * We can get view ID at runtime once the view is instantiated, but we
	 * also need static access to ID in order to be able to invoke the view.
	 */
	public static final String ID = "com.nokia.s60tools.remotecontrol.ui.views.main.MainView"; //$NON-NLS-1$
		
	/**
	 * Screen view tab
	 */
	private CTabItem screenViewTab;
	
	/**
	 * FTP view tab
	 */
	private CTabItem ftpViewTab;
	
	/**
	 * FTP view
	 */
	private FtpView ftpView = null;
	
	/**
	 * Screen view UI composite
	 */
	private ScreenView screenView;

	/**
	 * Tab folder
	 */
	private CTabFolder mainViewTabFolder;

	/**
	 * Contains status of connection.
	 */
	private boolean isConnected = false;
	
	/**
	 * Context activation
	 */
	private IContextActivation contextActivation = null;
	
	/**
	 * Context service
	 */
	private IContextService contextService = null;
	
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
		
		try {
			//
			// Actions invoked by content providers may set enable/disable
			// states for the actions, therefore all the action has to be
			// created before creating the controls. This makes sure that
			// it is safe to refer to all the actions after this point.
			//

			//
			// Creating controls
			//

			// The left side contains component hierarchy tree view
			SashForm mainViewSashForm = new SashForm(parent,
					SWT.HORIZONTAL);

			// The right side contains tabbed panes for showing...
			mainViewTabFolder = new CTabFolder(
					mainViewSashForm, SWT.BOTTOM);
			//... 1st tab
			createScreenViewTabControl(mainViewTabFolder);
			//... 2nd tab
			createFtpViewTabControl(mainViewTabFolder);

			// Default selection for tab folder
			mainViewTabFolder.setSelection(screenViewTab);

			// Adding listener to 
			mainViewTabFolder.addSelectionListener(this);
			
			//
			// Doing other initializations that may refer to the component
			// that has been created above.
			//
			screenView.setAsActiveView();
			
			// Setting context help IDs	
			PlatformUI.getWorkbench().getHelpSystem().setHelp(screenView, 
		    		RemoteControlHelpContextIDs.REMOTE_CONTROL_SCREEN_VIEW);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(ftpView, 
					RemoteControlHelpContextIDs.REMOTE_CONTROL_FTP_VIEW);
			
			HtiConnectionManager.getInstance().addListener(this);
			setDefaultDescription();
			
			// Set Screenview context as an active
			contextService = (IContextService)getSite().getService(IContextService.class);
			activateScreenviewContext();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Creates screen view tab.
	 * @param parentTabFolder Parent tab folder.
	 */
	private void createScreenViewTabControl(CTabFolder parentTabFolder) {
		SashForm firstFromTheLeftSashForm = new SashForm(parentTabFolder, SWT.VERTICAL);		
		screenViewTab = new CTabItem(parentTabFolder, SWT.NONE);
		screenViewTab.setControl(firstFromTheLeftSashForm);
		screenViewTab.setText(Messages.getString("MainView.Screencapture_Tabname")); //$NON-NLS-1$
		screenViewTab.setImage(ImageResourceManager.getImage(ImageKeys.IMG_SCREEN_CAPTURE_MODE));
		createScreenViewTabContents(firstFromTheLeftSashForm);
	}

	/**
	 * Creates screen view tab contents. This can be also delegated to a class of its own.
	 * @param parentComposite	Parent composite.
	 */
	private void createScreenViewTabContents(Composite parentComposite) {
		// Contents creation is delegated to an external class
		screenView = new ScreenView(parentComposite, SWT.NONE, this);	
	}

	/**
	 * Creates ftp view tab.
	 * @param parentTabFolder Parent tab folder.
	 */
	private void createFtpViewTabControl(CTabFolder parentTabFolder) {		
		SashForm secondFromTheLeftSashForm = new SashForm(parentTabFolder, SWT.VERTICAL);		
		ftpViewTab = new CTabItem(parentTabFolder, SWT.NONE);
		ftpViewTab.setControl(secondFromTheLeftSashForm);
		ftpViewTab.setText(Messages.getString("MainView.Ftp_Tabname")); //$NON-NLS-1$
		ftpViewTab.setImage(ImageResourceManager.getImage(ImageKeys.IMG_FILE_TRANSFER_MODE));
		createFtpViewTabContents(secondFromTheLeftSashForm);
	}
	
	/**
	 * Creates ftp view tab contents.
	 * @param parentComposite	Parent composite.
	 */
	private void createFtpViewTabContents(Composite parentComposite) {
		ftpView = new FtpView(parentComposite, SWT.NONE, this);
	}
	
	/**
	 * Focus request should be passed to the view's primary control.
	 */
	public void setFocus() {
		// Checking which tab is selected and setting focus.
		if(mainViewTabFolder.getSelection() == screenViewTab){
			screenView.setFocus();
		}
		else if(mainViewTabFolder.getSelection() == ftpViewTab){
			ftpView.setFocus();
		}
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
	 * Allows other classes to set default content description.
	 */
	public void setDefaultDescription() {
		Runnable updateDescription = new Runnable(){
			public void run() {
				if(isConnected) {
					updateDescription(Messages.getString("MainView.Connected_DescText")); //$NON-NLS-1$
				} else {
					updateDescription(Messages.getString("MainView.NotConnected_DescText")); //$NON-NLS-1$
				}
			}							
		};
		// Description needs to be updated from UI thread.
		Display.getDefault().asyncExec(updateDescription);
	}

	/**
	 * The view should refresh all its UI components in this method.
	 */
	public void refresh(){
	}

	/**
	 * Sets enabled/disabled states for actions commands
	 * on this view, based on the current application state.
	 * This method should be called whenever an operation is
	 * started or stopped that migh have effect on action 
	 * button states.
	 */
	public void updateActionButtonStates(){
	 // Currently there is no states that should be set
	 // here but there will surely be in future.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {
		super.dispose();
		HtiConnectionManager.getInstance().removeListener(this);
		deactivateScreenviewContext();
		screenView.dispose();
		ftpView.dispose();
	}

	/**
	 * Enables to get reference of the main view
	 * from the classes that do not actually
	 * have reference to the main view instance.
	 * @param openInvisible  If true opens up the view if it was not visible.
	 * @throws PartInitException 
	 */
	public static MainView getViewInstance(boolean openInvisible) throws PartInitException{
		
		IWorkbenchPage page = RemoteControlActivator.getCurrentlyActivePage();
		
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
				page.activate(viewPart);
			}
		}
		
		if(openInvisible && !viewAlreadyVisible){
			// View was not opened
			viewPart = page.showView(MainView.ID);							
		}	
		return ((MainView) viewPart);
	}

	/**
	 * Enables update request for the main view
	 * also from the classes that do not actually
	 * have reference to the main view instance.
	 */
	public static void update(){
		try {
			getViewInstance(false).inputUpdated();
		} catch (PartInitException e) {
			RemoteControlConsole.getInstance().println(e.getMessage(), 
					 IConsolePrintUtility.MSG_ERROR);
			e.printStackTrace();
		}
	}
	
	/**
	 * The view should refresh all its UI components in this method.
	 */
	private void inputUpdated() {
		if(ftpView != null){
			ftpView.refreshFolderContent();
		}
		
		if(screenView != null){
			screenView.refresh();
		}
		
	}

	/**
	 * Registers context menu for main view.
	 * @param menuManager Menu manager.
	 * @param selectionProvider Selection provider.
	 */
	public void registerContextMenu(MenuManager menuManager,
			ISelectionProvider selectionProvider) {
		getSite().registerContextMenu(menuManager, selectionProvider);
		
	}

	/**
	 * Returns action bars from main view.
	 * @return Action bars.
	 */
	public IActionBars getActionBars() {
		return getViewSite().getActionBars();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// Not needed.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		// Checking which tab is selected and updating action bars.
		if(e.item == screenViewTab){
			screenView.setAsActiveView();
			activateScreenviewContext();
		}
		else if(e.item == ftpViewTab){
			ftpView.setAsActiveView();
			deactivateScreenviewContext();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener#connectionTerminated()
	 */
	public void connectionTerminated() {
		isConnected = false;
		setDefaultDescription();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener#connectionStarted()
	 */
	public void connectionStarted() {
		isConnected = true;
		setDefaultDescription();
	}
	
	/**
	 * Deactivate ScreenView context
	 */
	private void deactivateScreenviewContext() {
		if (contextService != null) {
			contextService.deactivateContext(contextActivation);
		}
	}

	/**
	 * Activate ScreenView context
	 */
	private void activateScreenviewContext() {
		if (contextService != null) {
			contextActivation = contextService.activateContext("com.nokia.s60tools.remotecontrol.SreenView"); //$NON-NLS-1$
		}
	}
}
