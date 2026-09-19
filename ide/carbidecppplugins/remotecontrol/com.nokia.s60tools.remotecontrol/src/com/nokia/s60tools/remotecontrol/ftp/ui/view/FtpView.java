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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;

import com.nokia.s60tools.hticonnection.listener.HtiConnectionManager;
import com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.actions.OpenPreferencePageAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.CopyAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.CutAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.DeleteAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.DownloadFileAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.DownloadFileAndOpenAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.DownloadFileAsAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.MakeDirAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.PasteAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.RefreshAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.RenameAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.actions.UploadFileAction;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.TableViewerComparator.Columns;
import com.nokia.s60tools.remotecontrol.job.DeleteDirJob;
import com.nokia.s60tools.remotecontrol.job.DeleteFileJob;
import com.nokia.s60tools.remotecontrol.job.FileUploadJob;
import com.nokia.s60tools.remotecontrol.job.IJobCompletionListener;
import com.nokia.s60tools.remotecontrol.job.IManageableJob;
import com.nokia.s60tools.remotecontrol.job.MakeDirJob;
import com.nokia.s60tools.remotecontrol.job.PasteJob;
import com.nokia.s60tools.remotecontrol.job.RemoteControlJobManager;
import com.nokia.s60tools.remotecontrol.job.RenameJob;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferencePage;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite;
import com.nokia.s60tools.remotecontrol.ui.views.main.MainView;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * This view is for listing files for FTP transfer.
 */
public class FtpView extends AbstractUiFractionComposite implements
				IHtiConnectionListener, IJobCompletionListener, IConnectionStatusProvider, SelectionListener {
	 
	/**
	 * We can get view ID at runtime once the view is instantiated, but we
	 * also need static access to ID in order to be able to invoke the view.
	 */
	public static final String ID = "com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpView"; //$NON-NLS-1$
	/**
	 * Default column width for sizes.
	 */
	private static final int SIZE_COLUMN_DEFAULT_WIDTH = 90;
	/**
	 * Column width for free space.
	 */
	private int freeSpaceColumnWidth = SIZE_COLUMN_DEFAULT_WIDTH;
	/**
	 * Default column width for name.
	 */
	private static final int NAME_COLUMN_DEFAULT_WIDTH = 200;
	/**
	 * Name for size column.
	 */
	public static final String SIZE_COLUMN_NAME = Messages.getString("FtpView.Size_ColumnTitle"); //$NON-NLS-1$
	/**
	 * Name for name column.
	 */
	public static final String NAME_COLUMN_NAME = Messages.getString("FtpView.Name_ColumnTitle"); //$NON-NLS-1$
	/**
	 * Name for free space column.
	 */
	public static final String FREE_SPACE_COLUMN_NAME = Messages.getString("FtpView.FreeSpace_ColumnTitle"); //$NON-NLS-1$
	
	/**
	 * Open preferences action
	 */
	private Action preferencesAction;
	
	/**
	 * Download action
	 */
	private Action downloadAction;
	
	/**
	 * Download as action
	 */
	private Action downloadAsAction;
	
	/**
	 * Download and open
	 */
	private Action downloadAndOpenAction;
	
	/**
	 * Upload action
	 */
	private Action uploadAction;
	
	/**
	 * Make directory action
	 */
	private Action makeDirAction;
	
	/**
	 * Delete action
	 */
	private Action deleteAction;
	
	private Action copyAction;
	private Action cutAction;
	private Action pasteAction;
	private Action renameAction;
	
	/**
	 * Refresh action
	 */
	private Action refreshAction;
	
	/**
	 * Table viewer 
	 */
	private TableViewer viewer;

	/**
	 * Content provider
	 */
	private ViewContentProvider contentProvider;

	/**
	 * Main view
	 */
	private final MainView mainView;
	
	/**
	 * Is HTI connected to target
	 */
	private boolean isConnected = false;

	/**
	 * Keeps track if Remote Control has been connected after start.
	 * This is needed to determine if temp directory needs to be cleaned.
	 */	
	private boolean isFirstConnection = true;

	/**
	 * Selection changed listener
	 */
	private TableViewerSelectionChangedListener selectionChangedListener;
	
	/**
	 * Double click listener
	 */
	private TableViewerDoubleClickListener doubleClickListener;

	/**
	 * Path composite
	 */
	private PathComposite pathComposite;

	/**
	 * Property change listener
	 */
	private IPropertyChangeListener listener = null;

	/**
	 * Constructor.
	 * @param parentComposite	Parent composite for the created composite.
	 * @param style				The used style for the composite.
	 * @param mainView			Instance of MainView
	 */
	public FtpView(Composite parentComposite, int style, MainView mainView) {
		super(parentComposite, style);
		this.mainView = mainView;
		HtiConnectionManager.getInstance().addListener(this);
		RemoteControlJobManager.getInstance().addListener(this);
		createPropertyChangeListener();
	}

	/**
	 * Create change listener to prefstore preferences 
	 */
	private void createPropertyChangeListener() {
		
		// Folders need to be refreshed when prefstore values are changed
		listener =
			   new IPropertyChangeListener() {
			      public void propertyChange(PropertyChangeEvent event) {
			    	  refreshFolderContent();
			      }
			   };
			   
			   RCPreferences.addPropertyChangeListener(listener);
	}
	
	/**
	 * Getter for default grid layout.
	 * @return Default grid data layout.
	 */
	private GridData getDefaultGridData(){				
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		return gridData;
	}
	
	/**
	 * Creates table viewer for viewing file listing.
	 * @parent Parent composite
	 */
	private void createViewContents(Composite parent) {
		
		// Creating path field.
		
		pathComposite = new PathComposite(parent, this);

		// Creating file view.
		
		viewer = new TableViewer(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		viewer.getControl().setLayoutData(getDefaultGridData());		
		contentProvider = new ViewContentProvider(this);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new ViewLabelProvider(viewer.getTable()));
		viewer.setInput(contentProvider.getInput());
		
		configureTable();
		viewer.setComparator(new TableViewerComparator(Columns.NAME, SWT.UP));
		
		// Adding drop support.
		FileDropTargetListener fileDropTargetListener = new FileDropTargetListener(viewer, contentProvider);
		viewer.addDropSupport(DND.DROP_COPY, fileDropTargetListener.getTransferTypes(), fileDropTargetListener);
	}
	
	/**
	 * Configures table in the viewer and creates columns for it. 
	 */
	private void configureTable() {
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		
		// Name column.
		TableColumn column = new TableColumn(table, SWT.NONE, 0);
		column.setText(NAME_COLUMN_NAME);
		column.setWidth(NAME_COLUMN_DEFAULT_WIDTH);
		column.addSelectionListener(this);
		// Name column is used as default sorting column.
		table.setSortColumn(column);
		table.setSortDirection(SWT.UP);
		
		// Free space column.
		column = new TableColumn(table, SWT.NONE, 1);
		column.setText(FREE_SPACE_COLUMN_NAME);
		column.setAlignment(SWT.RIGHT);
		column.setWidth(SIZE_COLUMN_DEFAULT_WIDTH);
		column.addSelectionListener(this);
		
		// Size column.
		column = new TableColumn(table, SWT.NONE, 2);
		column.setText(SIZE_COLUMN_NAME);
		column.setAlignment(SWT.RIGHT);
		column.setWidth(SIZE_COLUMN_DEFAULT_WIDTH);
		column.addSelectionListener(this);
	}

	/**
	 * Create main menu actions in here. The same actions
	 * can be also used to populate context menus, if needed.
	 */
	private void createMainMenuActions() {
		preferencesAction = new OpenPreferencePageAction(Messages.getString("FtpView.Preferences_MenuItem"),  //$NON-NLS-1$
				Messages.getString("FtpView.Open_Preferences_Tooltip"), IAction.AS_PUSH_BUTTON,  //$NON-NLS-1$
				ImageKeys.IMG_PREFERENCES, RCPreferencePage.Tabs.FTP);
		downloadAction = new DownloadFileAction(viewer, contentProvider);
		downloadAsAction = new DownloadFileAsAction(viewer, contentProvider);
		downloadAndOpenAction = new DownloadFileAndOpenAction(viewer, contentProvider);
		uploadAction = new UploadFileAction(viewer, contentProvider);
		makeDirAction = new MakeDirAction(viewer, contentProvider);
		deleteAction = new DeleteAction(viewer, contentProvider);
		copyAction = new CopyAction(viewer, contentProvider);
		cutAction = new CutAction(viewer, contentProvider);
		pasteAction = new PasteAction(viewer, contentProvider);
		renameAction = new RenameAction(viewer, contentProvider);
		refreshAction = new RefreshAction(this);
	}

	/**
	 * This method fills action bars (no need for further modifications).
	 */
	protected void fillViewActionBars() {
		IActionBars bars = mainView.getActionBars();
		fillViewMainMenu(bars.getMenuManager());
		fillViewToolBar(bars.getToolBarManager());
	}

	/**
	 * View's main menu is populated in here.
	 * @param manager Menu manager instance.
	 */
	private void fillViewMainMenu(IMenuManager manager) {
		manager.removeAll();
		manager.add(downloadAction);
		manager.add(downloadAsAction);
		manager.add(uploadAction);
		manager.add(preferencesAction);
		manager.add(refreshAction);
		updateActionStates();
		manager.update(true);
	}

	/**
	 * View's tool bar is populated in here.
	 * @param manager Tool bar instance.
	 */
	private void fillViewToolBar(IToolBarManager manager) {	
		manager.removeAll();	
		manager.add(makeDirAction);
		manager.add(refreshAction);
		manager.add(preferencesAction);
		updateActionStates();
		manager.update(true);
	}

	/**
	 * Updates action button states.
	 */
	private void updateActionStates() {
		
		
		if (isSelectionFile() || isSelectionFolder()) {
			deleteAction.setEnabled(true);
			copyAction.setEnabled(true);
			cutAction.setEnabled(true);
		} else {
			deleteAction.setEnabled(false);
			copyAction.setEnabled(false);
			cutAction.setEnabled(false);
		}
		if (!isSelectionUpLink() && contentProvider.getDirectoryPath() != null) {
			makeDirAction.setEnabled(true);
		} else {
			makeDirAction.setEnabled(false);
		}
		if (isSelectionFile()) {
			downloadAction.setEnabled(true);
		} else {
			downloadAction.setEnabled(false);
		}
		if (isSelectionSingleFile()) {
			downloadAsAction.setEnabled(true);
			downloadAndOpenAction.setEnabled(true);
		} else {
			downloadAsAction.setEnabled(false);
			downloadAndOpenAction.setEnabled(false);
		}
		if (isSelectionSingleFile() || isSelectionSingleFolder()) {
			renameAction.setEnabled(true);
		} else {
			renameAction.setEnabled(false);
		}
		if (!isSelectionUpLink() && contentProvider.getDirectoryPath() != null) {
			uploadAction.setEnabled(true);
		} else {
			uploadAction.setEnabled(false);
		}
		if (!isSelectionUpLink() &&
				!isSelectionMultiFolder() &&
				contentProvider.getDirectoryPath() != null &&
				contentProvider.getFileOperation() != ViewContentProvider.OPERATION.NONE) {
			pasteAction.setEnabled(true);
		} else {
			pasteAction.setEnabled(false);
		}
		
		// refresh is disabled on root view
		if (contentProvider.getDirectoryPath() != null) {
			refreshAction.setEnabled(true);
		} else {
			refreshAction.setEnabled(false);
		}
	}
	
	/**
	 * Fills context menu.
	 * @param manager used to fill menu.
	 */
	private void fillContextMenu(IMenuManager manager) {
		manager.removeAll();
		manager.add(downloadAction);
		manager.add(downloadAsAction);
		manager.add(downloadAndOpenAction);
		manager.add(uploadAction);
		manager.add(deleteAction);
		
		manager.add(new Separator());
		
		// Copy, cut, paste, and rename are shown only if they are supported in the device.
		if(contentProvider.supportsCopyMoveRename()) {
			manager.add(copyAction);
			manager.add(cutAction);
			manager.add(pasteAction);
			manager.add(renameAction);
			
			manager.add(new Separator());
		}
		
		manager.add(makeDirAction);
		manager.add(refreshAction);
		manager.add(preferencesAction);
		updateActionStates();

		manager.update(true);
	}
	
	/**
	 * Allows other classes to update content description.
	 * @param newContentDescription New description.
	 */
	public void updateDescription(String newContentDescription){
		mainView.updateDescription(newContentDescription);
	}

	/**
	 * The view should refresh all its UI components in this method.
	 */
	public void refresh(){
		viewer.refresh();
		pathComposite.setConnected(isConnected);
		
		String currentPath = contentProvider.getDirectoryPath();
		
		refreshAction.setEnabled(isConnected);
		
		if(currentPath != null) {
			pathComposite.setPath(currentPath);
			makeDirAction.setEnabled(isConnected);
		} else {
			pathComposite.setPath(""); //$NON-NLS-1$
			makeDirAction.setEnabled(false);
		}
	}
	
	/**
	 * Updates contents of viewer with files and dirs in current directory 
	 * and then refreshes viewer.
	 */
	public void refreshFolderContent() {
		updatePathThenRefresh(contentProvider.getDirectoryPath());
	}
	
	/**
	 * Updates contents of viewer with files and dirs with given directory and 
	 * then refreshes viewer.
	 * @param directoryPath Path to device directory which contents are to be updated.
	 */
	public void updatePathThenRefresh(String directoryPath) {
		// Creating thread for updating so that called doesn't have to wait if there is long queue.
		UpdateViewerThread thread = new UpdateViewerThread(directoryPath);
		thread.start();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {
		super.dispose();
		RemoteControlJobManager.getInstance().removeListener(this);
		HtiConnectionManager.getInstance().removeListener(this);
		viewer.removeDoubleClickListener(doubleClickListener);
		viewer.removeSelectionChangedListener(selectionChangedListener);
		// Remove property listener
		if(listener != null){
			RCPreferences.removePropertyChangeListener(listener);
		}
	}

	/**
	 * Makes necessary changes to context menu and action bars.
	 */
	public void setAsActiveView(){
		hookContextMenu();
		fillViewActionBars();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#setFocus()
	 */
	@Override
	public boolean setFocus() {
		return pathComposite.setFocus() ? true : super.setFocus();
	}
	
	/**
	 * Hooks context menu to the current view.
	 */
	protected void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				FtpView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		mainView.registerContextMenu(menuMgr, viewer);
	}
	
	/**
	 * Is selected item(s) a file(s)
	 * @return True if file else false
	 */
	private boolean isSelectionFile() {
		boolean retVal = false;
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object[] selectionArray = selection.toArray();
		
		if (selection.size() > 0) {
			// Checking that all selected items are files.
			for(Object selectedObject : selectionArray) {
				if (FtpFileObject.class.isInstance(selectedObject)) {
					retVal = true;
				} else {
					retVal = false;
					break;
				}
			}
		}

		return retVal;
	}
	
	/**
	 * Is size of selection bigger than 1 and does it contain folder(s).
	 * @return True if size is bigger than 1 and contains one or more folders, false otherwise.
	 */
	private boolean isSelectionMultiFolder() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object[] selectionArray = selection.toArray();
		
		if (selection.size() > 1) {
			// Checking if there is is folder in selection.
			for(Object selectedObject : selectionArray) {
				if (FtpFolderObject.class.isInstance(selectedObject)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Is selected item a single file
	 * @return True if single file else false
	 */
	private boolean isSelectionSingleFile() {
		boolean retVal = false;
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
		
		if (selection.size() == 1){
			if (selectedObject != null) {
				if (FtpFileObject.class.isInstance(selectedObject)) {
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
	/**
	 * Is selected item a single folder
	 * @return True if single folder else false
	 */
	private boolean isSelectionSingleFolder() {
		boolean retVal = false;
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
		
		if (selection.size() == 1){
			if (selectedObject != null) {
				if (FtpFolderObject.class.isInstance(selectedObject)) {
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
	/**
	 * Is selected item a folder
	 * @return True if folder else false
	 */
	private boolean isSelectionFolder() {
		boolean retVal = false;
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
		if (selectedObject != null) {
			if (FtpFolderObject.class.isInstance(selectedObject)) {
				retVal = true;
			}
		}
		return retVal;
	}
		
	/**
	 * Is selected item a Up link
	 * @return True if Up link else false
	 */
	private boolean isSelectionUpLink() {
		boolean retVal = false;
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
		if (selectedObject != null) {
			if (FtpUplinkObject.class.isInstance(selectedObject)) {
				retVal = true;
			}
		}
		return retVal;
	}
	
	/**
	 * Hooks double click action to view. 
	 */
	private void hookDoubleClickAction() {
		doubleClickListener = new TableViewerDoubleClickListener(viewer, contentProvider, this);
		viewer.addDoubleClickListener(doubleClickListener);
	}
	
	/**
	 * Hooks selection action to view. 
	 */
	private void hookSelectionAction() {
		selectionChangedListener = new TableViewerSelectionChangedListener(this);
		viewer.addSelectionChangedListener(selectionChangedListener);
	}
	
	/**
	 * Updates toolbar and menu depending of selection
	 */
	public void selectionChanged() {
		updateActionStates();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createControls()
	 */
	protected void createControls() {
		try {
			//
			// Creating controls
			//
			createViewContents(this);
			
			//
			// Actions invoked by content providers may set enable/disable
			// states for the actions, therefore all the action has to be
			// created before creating the controls. This makes sure that
			// it is safe to refer to all the actions after this point.
			//
			createMainMenuActions();

			//
			// Doing other initializations that may refer to the component
			// that has been created above.
			//

			hookDoubleClickAction();
			hookSelectionAction();

		} catch (Exception e) {
			e.printStackTrace();
			RemoteControlConsole.getInstance().println(Messages.getString("FtpView.Failed_To_Create_File_Transfer_View_ConsoleMsg") + e  //$NON-NLS-1$
					, RemoteControlConsole.MSG_ERROR);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createLayout()
	 */
	@Override
	protected Layout createLayout() {
		GridLayout gridLayout = new GridLayout(1, false);
		return gridLayout;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createLayoutData()
	 */
	@Override
	protected Object createLayoutData() {
		return getDefaultGridData();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener#connectionTerminated()
	 */
	public void connectionTerminated() {
		isConnected = false;
		updatePathThenRefresh(null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.listener.IHtiConnectionListener#connectionStarted()
	 */
	public void connectionStarted() {
		isConnected = true;
		updatePathThenRefresh(null);
		if(isFirstConnection) {
			// Temporary directory is cleaned when connection is estabilished
			// at the first time.
			isFirstConnection = false;
			Thread thread = new TempDirCleaner();
			Display.getDefault().asyncExec(thread);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ftp.ui.view.IConnectionStatusProvider#isConnected()
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.job.IJobCompletionListener#backgroundJobCompleted(com.nokia.s60tools.remotecontrol.job.IManageableJob)
	 */
	public void backgroundJobCompleted(IManageableJob jobObject) {
		if (FileUploadJob.class.isInstance(jobObject)
				|| MakeDirJob.class.isInstance(jobObject)
				|| DeleteDirJob.class.isInstance(jobObject)
				|| DeleteFileJob.class.isInstance(jobObject)
				|| RenameJob.class.isInstance(jobObject)
				|| PasteJob.class.isInstance(jobObject)) {
			
			// Ui need to be updated for showing updated files in file view
			updatePathThenRefresh(contentProvider.getDirectoryPath());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// Not implemented.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		// Handling events from columns.
		if(e.getSource() instanceof TableColumn) {
			TableColumn column = (TableColumn)e.getSource();
			Table table = column.getParent();

			// Getting sorting information.
			
			int sortDirection;
			if(table.getSortColumn() == column) {
				sortDirection = (table.getSortDirection() == SWT.UP) ? SWT.DOWN : SWT.UP;
			} else {
				sortDirection = SWT.UP;
			}
			table.setSortColumn(column);
			table.setSortDirection(sortDirection);
			
			// Comparator will sort rows after it has been set.
			viewer.setComparator(new TableViewerComparator(Columns.values()[table.indexOf(column)], sortDirection));
		}
	}

	/**
	 * Updates columns in table.
	 */
	private void updateColumns(boolean isRoot) {
		Table table = viewer.getTable();
		
		if(isRoot) {
			// Adding free space column when browsing root
			if(table.getColumnCount() == 2) {
				TableColumn column = new TableColumn(table, SWT.NONE, 1);
				column.setText(FREE_SPACE_COLUMN_NAME);
				column.setAlignment(SWT.RIGHT);
				column.setWidth(freeSpaceColumnWidth);
				column.addSelectionListener(this);
			}
		}
		else {
			// Removing free space column when browsing directory
			if(table.getColumnCount() == 3) {
				if(table.getSortColumn() == table.getColumn(1)) {
					// Have to set new sort column.
					table.setSortColumn(table.getColumn(0));
					table.setSortDirection(SWT.UP);
					viewer.setComparator(new TableViewerComparator(Columns.NAME, SWT.UP));
				}
				freeSpaceColumnWidth = table.getColumn(1).getWidth();
				table.getColumn(1).dispose();
			}
		}
	}
	
	/**
	 * Thread that is created to update file listing in view.
	 * First listing needs to be updated in normal thread to prevent
	 * UI hanging up. Then viewer needs to be refreshed from UI thread.
	 * This must be run as normal thread.
	 */
	private class UpdateViewerThread extends Thread {
		
		// Path to be updated.
		private final String directoryPath;

		/**
		 * Constructor.
		 * @param directoryPath Path that will be updated.
		 */
		public UpdateViewerThread(String directoryPath) {
			this.directoryPath = directoryPath;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			// Updating directory listing.
			final String updatedPath = contentProvider.updatePath(directoryPath);
			
			// Runnable for updating UI
			Runnable updateView = new Runnable() {
				public void run() {
					boolean isRoot = (updatedPath == null || updatedPath.equals(""));  //$NON-NLS-1$
					updateColumns(isRoot);
					refresh();
				}
			};

			// UI updates from background. Threads has to be queued
			// into UI thread in order not to cause invalid thread access
			Display.getDefault().asyncExec(updateView);
		}
	}
	
	/**
	 * Thread for cleaning temporary directory from unnecessary files.
	 */
	private class TempDirCleaner extends Thread {
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			cleanTempDir(RemoteControlActivator.getDefault().getDownloadTempDir());
		}
		
		/**
		 * Goes through files in temporary folder and deletes those that are currently not open.
		 * @param path temporary directory to be cleaned.
		 */
		public void cleanTempDir(String path) {
			File root = new File(path);
			if(!root.exists()) {
				root.mkdirs();
			}
			if(root.exists()) {
				File[] files = root.listFiles();
				for(File file : files) {
					if(!isFileOpen(file)) {
						// If file fails to be deleted, it might be open in system editor.
						// No need to check if operation failed.
						file.delete();
					}
				}
			}
		}
	
		/**
		 * Checks if file has been opened in File store editor.
		 * @param file File that is to be checked.
		 * @return True if file is open in an editor. False otherwise.
		 */
		private boolean isFileOpen(File file) {
			
			IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
			
			// Windows
			for(IWorkbenchWindow window : windows) {
				IWorkbenchPage[] pages = window.getPages();
				// Pages
				for(IWorkbenchPage page : pages) {
					IEditorReference[] references = page.getEditorReferences();
					// Editors
					for(IEditorReference reference : references) {
						// Editor needs to be restored, otherwise it can null.
						IEditorPart part = reference.getEditor(true);
						// All files are opened in FileStoreEditors, because they are external files.
						if(part != null && part.getEditorInput() instanceof FileStoreEditorInput) {
							FileStoreEditorInput input = (FileStoreEditorInput)part.getEditorInput();
							if(input.getURI().compareTo(file.toURI()) == 0) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}
	}
}
