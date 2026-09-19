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

package com.nokia.s60tools.imaker.internal.viewers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.IEnvironmentManager;
import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.ImageFlasherHelpContextIDs;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.SWTFactory;
import com.nokia.s60tools.imaker.internal.dialogs.LaunchIMakerDialog;
import com.nokia.s60tools.imaker.internal.managers.ProjectManager;
import com.nokia.s60tools.imaker.internal.model.IObserver;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;

public class IMakerTabsViewer extends Viewer implements IObserver {
	/**
	 * This view's control, which contains a composite area of controls
	 */
	private Composite fViewerControl;
	
	/**
	 * Tab folder
	 */
	private CTabFolder fTabFolder;
		
	private Button fRevertButton;

	/**
	 * A place holder for switching between the tabs for a config and the getting started tab
	 * @since 3.2
	 */
	private Composite fTabPlaceHolder = null;
	private ViewForm fViewform;
	private IEnvironmentManager environmentManager;
	private PreferencesTab tabPreferences;
	private Button fRestoreButton;
	private Button fReloadButton;
	private SettingsTab tabSettings;
	private Combo fConfigWidget;
	private Button deleteButton;
	private DebugTab tabDebug;
	private PlatsimTab tabPlatsim;
	private LaunchIMakerDialog dialog;
	
	/**
	 * Constructs a viewer in the given composite, contained by the given
	 * launch configuration dialog.
	 * 
	 * @param parent composite containing this viewer
	 * @param manager 
	 * @param launchIMakerDialog 
	 * @param dialog containing launch configuration dialog
	 * @param selection 
	 * @throws InterruptedException 
	 */
	public IMakerTabsViewer(Composite parent, IEnvironmentManager manager, LaunchIMakerDialog dialog){
		super();
		this.environmentManager = manager;
		this.dialog = dialog;
		createControl(parent);
	}

	public void initialize() {
		ImakerProperties run = environmentManager.getActiveEnvironment().getRunProperties();
		String file = run.getActiveFile();
		if(file!=null) {
			displayImakerFile(file);
		} else {
			displayImakerFile(ProjectManager.NEW_ITEM);			
		}
	}

	/**
	 * @throws InterruptedException 
	 * 
	 */
	private void displayImakerFile(String item){
		populateConfigurations(item);
		try {
			tabPreferences.loadImakerFile(item);
		} catch (InvocationTargetException e) {}
	}

	/**
	 * 
	 */
	public void populateConfigurations(String selection) {
		fConfigWidget.removeAll();
		List<IResource> files = getProjectManager().getImakerFiles();
		fConfigWidget.add(ProjectManager.NEW_ITEM);
		fConfigWidget.select(0);
		for (int i = 0; i < files.size(); i++) {
			String item = files.get(i).getLocation().toString();
			fConfigWidget.add(item);
			if(item.equals(selection)) {
				fConfigWidget.select(i+1);
			}		
		}
		enabaleReload(selection);
	}


	private void enabaleReload(String selection) {
		if (ProjectManager.NEW_ITEM.equals(selection)) {
			fReloadButton.setEnabled(true);
		} else {
			fReloadButton.setEnabled(false);						
		}
	}

	public IEnvironmentManager getEnvironmentManager() {
		return environmentManager;
	}
	public ProjectManager getProjectManager() {
		return dialog.getProjectManager();
	}
	
	@Override
	public Control getControl() {
		return fViewerControl;
	}

	@Override
	public Object getInput() {
		return null;
	}

	@Override
	public ISelection getSelection() {
		return null;
	}

	@Override
	public void refresh() {}

	@Override
	public void setInput(Object input) {

	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {

	}
	/**
	 * Creates this viewer's control This area displays the name of the launch
	 * configuration currently being edited, as well as a tab folder of tabs
	 * that are applicable to the launch configuration.
	 *
	 * @return the composite used for launch configuration editing
	 */
	private void createControl(Composite parent) {
		fViewerControl = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		fViewerControl.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		fViewerControl.setLayoutData(gd);
		
        fViewform = new ViewForm(fViewerControl, SWT.FLAT | SWT.BORDER);
        layout = new GridLayout(1, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        fViewform.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		fViewform.setLayoutData(gd);
        fViewform.setTopLeft(null);
        
        Composite mainComp = new Composite(fViewform, SWT.FLAT);
        layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        mainComp.setLayout(layout);
        fViewform.setContent(mainComp);

        Composite comboComp = new Composite(mainComp, SWT.NONE);
        comboComp.setLayout(new GridLayout(3, false));
        comboComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label fNameLabel = new Label(comboComp, SWT.HORIZONTAL | SWT.LEFT);
        fNameLabel.setText(Messages.getString("IMakerTabsViewer.1"));
        fNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
        
        fConfigWidget = new Combo(comboComp, SWT.NONE);
        fConfigWidget.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fConfigWidget.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent se) {
				try {
					String item = getSelectedItem();
					enabaleReload(item);
					tabPreferences.loadImakerFile(item);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent se) {
				widgetSelected(se);
			}
		});
        PlatformUI.getWorkbench().getHelpSystem().setHelp(fConfigWidget, ImageFlasherHelpContextIDs.IMAKERDIALOG_CONFIGURATION);
        
        fConfigWidget.setToolTipText(Messages.getString("IMakerTabsViewer.2"));
        deleteButton = new Button(comboComp, SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.setEnabled(false);
        deleteButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));

		fTabPlaceHolder = new Composite(mainComp, SWT.NONE);
        layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		fTabPlaceHolder.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		fTabPlaceHolder.setLayoutData(gd);
		        
		createTabFolder(fTabPlaceHolder);
		
		
		Composite buttonComp = new Composite(mainComp, SWT.NONE);
		GridLayout buttonCompLayout = new GridLayout();
		buttonCompLayout.numColumns = 2;
		buttonCompLayout.makeColumnsEqualWidth = false;
		buttonComp.setLayout(buttonCompLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		buttonComp.setLayoutData(gd);

		fRestoreButton = new Button(buttonComp, SWT.PUSH);
		fRestoreButton.setText(Messages.getString("IMakerTabsViewer.0")); //$NON-NLS-1$
		fRestoreButton.setToolTipText(Messages.getString("IMakerTabsViewer.11")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		fRestoreButton.setLayoutData(gd);
		SWTFactory.setButtonWidthHint(fRestoreButton);
		fRestoreButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleRestorePressed();
			}
		});
		
		fReloadButton = new Button(buttonComp, SWT.PUSH);
		fReloadButton.setText(Messages.getString("IMakerTabsViewer.12")); //$NON-NLS-1$
		fReloadButton.setToolTipText(Messages.getString("IMakerTabsViewer.13")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		fReloadButton.setLayoutData(gd);
		SWTFactory.setButtonWidthHint(fReloadButton);
		fReloadButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleReloadPressed();
			}
		});
		
        Dialog.applyDialogFont(parent);
	}

	
	protected void handleNameModified() {
		if(!fConfigWidget.getText().equals("")) {
			deleteButton.setEnabled(true);
		}
	}

	private void handleRestorePressed() {
		tabPreferences.restore();
	}
		
	private void handleReloadPressed() {
		tabPreferences.reload(getSelectedItem());
	}
	
	protected void handleRevertPressed() {
		if(fTabFolder.getSelection() == tabSettings) {
			tabPreferences.refreshSettingsTab(null);
		} else {}
	}

	/**
	 * Creates the tab folder for displaying config instances
	 * @param parent
	 */
	private void createTabFolder(Composite parent) {
		if (fTabFolder == null) {
			Composite tabComposite = new Composite(parent, SWT.NONE);
	        GridLayout layout = new GridLayout(1, false);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			tabComposite.setLayout(layout);
			tabComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
			ColorRegistry reg = JFaceResources.getColorRegistry();
			Color c1 = reg.get("org.eclipse.ui.workbench.ACTIVE_TAB_BG_START"), //$NON-NLS-1$
				  c2 = reg.get("org.eclipse.ui.workbench.ACTIVE_TAB_BG_END"); //$NON-NLS-1$
			fTabFolder = new CTabFolder(tabComposite, SWT.NO_REDRAW_RESIZE | SWT.NO_TRIM | SWT.FLAT);

			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.horizontalSpan = 2;
			fTabFolder.setSelectionBackground(new Color[] {c1, c2},	new int[] {100}, true);
			fTabFolder.setSelectionForeground(reg.get("org.eclipse.ui.workbench.ACTIVE_TAB_TEXT_COLOR")); //$NON-NLS-1$
			fTabFolder.setSimple(PlatformUI.getPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS));
			fTabFolder.setLayoutData(gd);
	        fTabFolder.setBorderVisible(true);
			fTabFolder.setFont(tabComposite.getFont());
			fTabFolder.addSelectionListener(new SelectionListener() {
				
//				@Override
				public void widgetSelected(SelectionEvent e) {
					if(e.item instanceof DebugTab) {
						tabDebug.refesh();
					}
				}
				
//				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			createTabs(fTabFolder);
			fTabFolder.setSelection(0);
		}
	}

	private void createTabs(CTabFolder tabFolder) {
		tabPreferences = new PreferencesTab(tabFolder, SWT.NONE, this);
		PreferencesTab.currentPreferencesTab = tabPreferences;
		tabPreferences.setText(Messages.getString("IMakerTabsViewer.3")); //$NON-NLS-1$
		tabPreferences.setToolTipText(Messages.getString("IMakerTabsViewer.4"));
		tabPreferences.setImage(getTabImage(1));
		tabSettings = new SettingsTab(tabFolder, SWT.NONE);
		tabSettings.setText(Messages.getString("IMakerTabsViewer.5")); //$NON-NLS-1$
		tabSettings.setToolTipText(Messages.getString("IMakerTabsViewer.6"));
		tabSettings.setImage(getTabImage(2));        
        tabDebug = new DebugTab(tabFolder, SWT.NONE, this, tabPreferences);
        tabDebug.setImage(getTabImage(3));        
        tabDebug.setText(Messages.getString("IMakerTabsViewer.7")); //$NON-NLS-1$
        tabDebug.setToolTipText(Messages.getString("IMakerTabsViewer.8"));

        tabPlatsim = new PlatsimTab(tabFolder, SWT.NONE);
        tabPlatsim.setImage(getTabImage(4));        
        tabPlatsim.setText(Messages.getString("IMakerTabsViewer.9")); //$NON-NLS-1$
        tabPlatsim.setToolTipText(Messages.getString("IMakerTabsViewer.10"));
        
        tabPreferences.setSettings(tabSettings);
        tabPreferences.setDebug(tabDebug);
        tabPreferences.setPlatsim(tabPlatsim);
	}

	private Image getTabImage(int tab) {
		ImageDescriptor descriptor = null;
		switch (tab) {
		case 1:
			descriptor = IMakerPlugin.getImageDescriptor("icons/preferences.gif");
			return descriptor.createImage();
		case 2:
			descriptor = IMakerPlugin.getImageDescriptor("icons/settings.gif"); //$NON-NLS-1$
			return descriptor.createImage();
		case 3:
			descriptor = IMakerPlugin.getImageDescriptor("icons/content.gif"); //$NON-NLS-1$
			return descriptor.createImage();
		case 4:
			descriptor = IMakerPlugin.getImageDescriptor("icons/platsim.png"); //$NON-NLS-1$
			return descriptor.createImage();
		default:
			break;
		}
		return null;
	}

	public void update(Object selection) {
		if(selection!=null) {
			setNameField((ImakerProperties) selection);
			deleteButton.setEnabled(false);
		} else {
			fConfigWidget.setText("");
			deleteButton.setEnabled(false);
		}
	}

	private void setNameField(ImakerProperties prop) {
//		fConfigWidget.setText(prop.getFilename());
	}

	public String handleRunPressed() {
		String item = getSelectedItem();
		tabPreferences.runPressed(item);
		return item;
	}

	public String getSelectedItem() {
		int index = fConfigWidget.getSelectionIndex();
		return fConfigWidget.getItem(index);
	}

	public Button getFRevertButton() {
		return fRevertButton;
	}
	
	public void restoreSelection()
	{
		fConfigWidget.select(0);
	}

}
