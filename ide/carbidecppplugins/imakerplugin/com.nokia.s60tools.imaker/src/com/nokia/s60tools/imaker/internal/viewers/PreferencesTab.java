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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.IEnvironment;
import com.nokia.s60tools.imaker.IEnvironmentManager;
import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.IMakerKeyConstants;
import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.ImageFlasherHelpContextIDs;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.StatusHandler;
import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.UITarget;
import com.nokia.s60tools.imaker.UIVariable;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
import com.nokia.s60tools.imaker.internal.dialogs.ProductSelectionDialog;
import com.nokia.s60tools.imaker.internal.managers.ProjectManager;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;
import com.nokia.s60tools.imaker.internal.wizards.NewImakerFileWizard;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapperPreferences;

public class PreferencesTab extends CTabItem {
	public static PreferencesTab currentPreferencesTab = null;

//	private enum EDIT_STATE {EMPTY,EDITED,SAVED};
//	private enum NAME_STATE {UNNAMED,NAMED};
//	private EDIT_STATE editState = EDIT_STATE.EMPTY;
//	private NAME_STATE nameState = NAME_STATE.UNNAMED;

	private Label labelVersion;
	private Text textUserDefinedParameters;
	private Button buttonSymbolFiles;
	private Button buttonVerbose;
	private Button buttonImageTypeRnd;
	private Button buttonImageTypePrd;
	private Button buttonImageTypeSubcon;
	private Text textProduct;
	private org.eclipse.swt.widgets.List listTarget;
	private Button buttonSave;
	private Button buttonSaveAs;
	private IEnvironmentManager environmentManager = null;
	private IEnvironment activeEnvironment = null;
	private PreferenceSelectionListener pListener;
	private String iMakerCoreVersion;
	private String pluginVersion;
	private String pluginDate;
	private IMakerTabsViewer tabsViewer;
	private org.eclipse.swt.widgets.List listSource;

	private IPropertyViewer tabDebug;
	private SettingsTab settingsTab;
	private PlatsimTab platsimTab;
	
	private ProjectManager projectManager;

	/**
	 * The only constructor
	 * @param parent
	 * @param style
	 * @param tabsViewer
	 */
	public PreferencesTab(CTabFolder parent, int style, IMakerTabsViewer tabsViewer) {
		super(parent, style);
		this.tabsViewer = tabsViewer;
		environmentManager  = tabsViewer.getEnvironmentManager();
		this.activeEnvironment = environmentManager.getActiveEnvironment();
		this.projectManager = tabsViewer.getProjectManager();
		
		pListener = new PreferenceSelectionListener();
		iMakerCoreVersion = Messages.getString("PreferencesTab.32"); //$NON-NLS-1$

		pluginVersion = 
			(String)Platform.getBundle(IMakerPlugin.PLUGIN_ID) //$NON-NLS-1$
			.getHeaders()
			.get("Bundle-Version"); //$NON-NLS-1$
		pluginDate = 
			(String)Platform.getBundle(IMakerPlugin.PLUGIN_ID) //$NON-NLS-1$
			.getHeaders()
			.get("Bundle-Date"); //$NON-NLS-1$

		setControl(createControl(parent));
	}
	

	private Control createControl(CTabFolder parent) {
		// Create Top composite in top of the parent composite
		Composite container = new Composite(parent, SWT.FLAT);

		GridData topCompositeGridData = new GridData(SWT.FILL, SWT.FILL, true,false);
		container.setLayoutData(topCompositeGridData);
		GridLayout topCompositeGridLayout = createLayout();
		container.setLayout(topCompositeGridLayout);

		setHelpForControl(container,ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP);

		try {
			addConfigurationControls(container);
			addImageTypeSelectionControls(container);
			addFlagSelectionControls(container);
			addAdditionalParametersControl(container);
			
			addPreferencesControls(container);
			addVersionInfoControls(container);

//			// Set up context help ids
			setHelpForControl(textProduct, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_PRODUCT);
			setHelpForControl(listSource, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_TARGETS);
			setHelpForControl(listTarget, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_SELECTED_TARGETS);
			setHelpForControl(buttonImageTypeRnd, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_IMAGE_TYPE);
			setHelpForControl(buttonImageTypePrd, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_IMAGE_TYPE);
			setHelpForControl(buttonImageTypeSubcon, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_IMAGE_TYPE);
			setHelpForControl(buttonSymbolFiles, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_FLAGS);
			setHelpForControl(buttonVerbose, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_FLAGS);
			setHelpForControl(textUserDefinedParameters, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_ADDITIONAL_PARAMS);
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return container;
	}

	private GridLayout createLayout() {
		GridLayout topCompositeGridLayout = new GridLayout(2, false);
		topCompositeGridLayout.horizontalSpacing = 5; // CodForChk_Dis_Magic
		topCompositeGridLayout.verticalSpacing = 5; // CodForChk_Dis_Magic
		topCompositeGridLayout.marginWidth = 3;
		topCompositeGridLayout.marginHeight = 3;
		return topCompositeGridLayout;
	}

	private void addVersionInfoControls(Composite container) {
		labelVersion =  new Label(container, SWT.NONE);
		labelVersion.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		String versionText = getVersionText();

		labelVersion.setText(versionText + "\n" + iMakerCoreVersion); //$NON-NLS-1$
		labelVersion.setToolTipText(Messages.getString("PreferencesTab.20"));
	}

	private String getVersionText() {
		String versionText = "iMaker Extension: " + pluginVersion + ", " + pluginDate;
		return versionText;
	}

	/**
	 * @param container
	 */
	private void addPreferencesControls(Composite container) {
		Composite controlGroup = new Composite(container, SWT.NONE);
		GridLayout layoutAdditionalParameters = new GridLayout(4, true);
		controlGroup.setLayout(layoutAdditionalParameters);
		controlGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
				2, 1));

		buttonSave = new Button(controlGroup, SWT.PUSH);
		buttonSave.setEnabled(true);
		buttonSave.setText(Messages.getString("PreferencesTab.16")); //$NON-NLS-1$
		buttonSave.setToolTipText(Messages.getString("PreferencesTab.17")); //$NON-NLS-1$
		buttonSave.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
		buttonSave.addSelectionListener(pListener);

		buttonSaveAs = new Button(controlGroup, SWT.PUSH);
		buttonSaveAs.setText(Messages.getString("PreferencesTab.18")); //$NON-NLS-1$
		buttonSaveAs.setToolTipText(Messages.getString("PreferencesTab.19")); //$NON-NLS-1$
		buttonSaveAs.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
		buttonSaveAs.addSelectionListener(pListener);
	}

	private void addAdditionalParametersControl(Composite container) {
		Label label = new Label(container, SWT.NONE);
		label.setText(Messages.getString("PreferencesTab.14")); //$NON-NLS-1$

		label.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false,
						1, 1));
		
		Composite comp = new Composite(container,SWT.NONE);
		RowLayout layout = new RowLayout();
		layout.type = SWT.HORIZONTAL;
		layout.marginLeft = 0;
		comp.setLayout(layout);
		comp
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
						1, 1));

		// User defined parameters text
		textUserDefinedParameters = new Text(comp, SWT.BORDER);
		textUserDefinedParameters.setText("");
		textUserDefinedParameters.setToolTipText(Messages.getString("PreferencesTab.15"));		
		RowData rData = new RowData();
		rData.width = 335;
		textUserDefinedParameters.setLayoutData(rData);
	}

	private void addFlagSelectionControls(Composite container) {
		Label label = new Label(container, SWT.NONE);
		label.setText(Messages.getString("PreferencesTab.9")); //$NON-NLS-1$
		
		label.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false,
						1, 1));
		Composite comp = new Composite(container,SWT.NONE);
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		layout.spacing = 2;
		layout.marginLeft = 0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
						1, 1));

		buttonSymbolFiles = new Button(comp, SWT.CHECK);
		buttonSymbolFiles.setText(Messages.getString("PreferencesTab.10"));
		buttonSymbolFiles.setToolTipText(Messages.getString("PreferencesTab.11"));
		buttonVerbose = new Button(comp, SWT.CHECK);
		buttonVerbose.setText(Messages.getString("PreferencesTab.12"));
		buttonVerbose.setToolTipText(Messages.getString("PreferencesTab.13"));
	}

	/**
	 * Adds image selection user interface components
	 * @param container
	 */
	private void addImageTypeSelectionControls(Composite container) {
		Label label = new Label(container, SWT.NONE);
		label.setText(Messages.getString("PreferencesTab.2")); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false,
						1, 1));
		Composite comp = new Composite(container,SWT.NONE);
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		layout.spacing = 2;
		layout.marginLeft = 0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
						1, 1));

		// Rnd/Prd/Subcon
		buttonImageTypeRnd = new Button(comp,SWT.RADIO);
		buttonImageTypeRnd.setText(Messages.getString("PreferencesTab.3"));
		buttonImageTypeRnd.setToolTipText(Messages.getString("PreferencesTab.4"));
		buttonImageTypeRnd.setSelection(true);
		buttonImageTypePrd = new Button(comp,SWT.RADIO);
		buttonImageTypePrd.setText(Messages.getString("PreferencesTab.5"));
		buttonImageTypePrd.setToolTipText(Messages.getString("PreferencesTab.6"));
		buttonImageTypeSubcon = new Button(comp,SWT.RADIO);
		buttonImageTypeSubcon.setText(Messages.getString("PreferencesTab.7"));
		buttonImageTypeSubcon.setToolTipText(Messages.getString("PreferencesTab.8"));
	}


	/**
	 * Add configuration user interface components
	 * @param container
	 */
	private void addConfigurationControls(Composite container) {
		Label label = null;

		label = new Label(container, SWT.NONE);
		label.setText(Messages.getString("PreferencesTab.0")); //$NON-NLS-1$

		// Product combo
		createProductWidgets(container);

		// Target selection label
		label = new Label(container, SWT.NONE);
		label.setText(Messages.getString("PreferencesTab.1"));
		label.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false,
						1, 1));

		addTargetLists(container);
	}
	/**
	 * @param container
	 */
	private void createProductWidgets(Composite container) {
		Composite product = new Composite(container,SWT.NONE);
		product.setLayoutData(new GridData(SWT.BEGINNING, SWT.NONE, false, false, 1, 1));
		product.setLayout(new GridLayout(2, false));
		textProduct = new Text(product, SWT.BORDER);
		textProduct.setText("");
		textProduct.setEditable(false);
		GridData layoutData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 1, 1);
		layoutData.widthHint = 280;
		textProduct.setLayoutData(layoutData);
		
		Button select = new Button(product,SWT.PUSH);
		select.setText("&Change...");
		select.addSelectionListener(new SelectionListener() {
			
//			@Override
			public void widgetSelected(SelectionEvent se) {
				ProductSelectionDialog selectDialog = new ProductSelectionDialog(getControl().getShell(),
						activeEnvironment);
				boolean ret = selectDialog.displayDialog();
				if(ret) {
					UIConfiguration config = selectDialog.getSelectedConfiguration();
					refreshProduct(config);
				}
			}
			
//			@Override
			public void widgetDefaultSelected(SelectionEvent se) {
				widgetSelected(se);
			}
		});
		select.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void addTargetLists(Composite parent) {
		Composite top = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 6;
		layout.verticalSpacing = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		top.setLayout(layout);
		top.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		listSource = createListComponent(top, "&All");
		listSource.addSelectionListener(new SelectionListener() {
			
//			@Override
			public void widgetSelected(SelectionEvent se) {
				String item = listSource.getItem(listSource.getSelectionIndex());
				UIConfiguration p = activeEnvironment.getCurrentProduct();
				listSource.setToolTipText(p.getTarget(item).getDescription());
			}
			
//			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		createListButtons(top);
		listTarget = createListComponent(top, "&Selected");
		listTarget.addSelectionListener(new SelectionListener() {
			
//			@Override
			public void widgetSelected(SelectionEvent se) {
				int index = listTarget.getSelectionIndex();
				if(index!=-1) {
					String item = listTarget.getItem(index);
					UIConfiguration p = activeEnvironment.getCurrentProduct();
					listTarget.setToolTipText(p.getTarget(item).getDescription());					
				}
			}
			
//			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}
	

	private org.eclipse.swt.widgets.List createListComponent(Composite top,
			String txt) {
		Composite comp = new Composite(top,SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.verticalSpacing = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		comp.setLayout(layout);
		comp
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
						1, 1));
		GridData gData;
		Label label = new Label(comp,SWT.NONE);
		label.setText(txt);
		org.eclipse.swt.widgets.List list;
		list = new org.eclipse.swt.widgets.List(comp, SWT.MULTI | SWT.V_SCROLL
				| SWT.BORDER);
		gData = new GridData();
		gData.widthHint = 132;
		gData.heightHint = 132;
		list.setLayoutData(gData);
		return list;
	}
	
	private void createListButtons(Composite top) {
		Composite bComp = new Composite(top,SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = 1;
		layout.verticalSpacing = 1;
		bComp.setLayout(layout);
		bComp.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, false, false, 1,
				1));
		Button button = new Button(bComp,SWT.PUSH);
		button.setText(">");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String[] selections = listSource.getSelection();
				addToListTarget(selections);
				listSource.setToolTipText("");
			}
		});
		
		GridData gData = new GridData();
		gData.verticalAlignment = SWT.END;
		button.setLayoutData(gData);
		button = new Button(bComp,SWT.PUSH);
		button.setText("<");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String[] selections = listTarget.getSelection();
				addToListSource(selections);
				listTarget.setToolTipText("");
			}
		});
		gData = new GridData();
		gData.verticalAlignment = SWT.CENTER;
		button.setLayoutData(gData);
	}	
	

	private void moveItemsInList(org.eclipse.swt.widgets.List source,
			org.eclipse.swt.widgets.List target, String[] items) {
		List<String> sourceItems = Arrays.asList(source.getItems());
		for (int i = 0; i < items.length; i++) {
			String item = items[i];
			if(sourceItems.contains(item)) {
				source.remove(item);
				target.add(item);				
			}
		}
	}

	protected void addToListSource(String[] selections) {
		moveItemsInList(listTarget, listSource, selections);
		String[] items = listSource.getItems();
		Arrays.sort(items);
		listSource.removeAll();
		listSource.setItems(items);
	}

	protected void addToListTarget(String[] selections) {
		UIConfiguration pr = activeEnvironment.getCurrentProduct();
		moveItemsInList(listSource, listTarget, selections);
		for (int i = 0; i < selections.length; i++) {
			String target = selections[i];
			UITarget tr = pr.getTarget(target);
			if(tr.getSteps()==null) {
				String targetSteps = activeEnvironment.getTargetSteps(target);
				tr.setSteps(targetSteps);
			}
		}
	}


	private void setHelpForControl(Control container, String id) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(container, id);
	}

	private void resetAdditionalFields() {
		buttonImageTypeRnd.setSelection(true);
		buttonImageTypePrd.setSelection(false);
		buttonImageTypeSubcon.setSelection(false);
		buttonSymbolFiles.setSelection(false);
		buttonVerbose.setSelection(false);
		textUserDefinedParameters.setText("");
	}


	private void loadConfigurations() throws InvocationTargetException {
		try {
			iMakerCoreVersion = activeEnvironment.getIMakerCoreVersion();
			
			fixVersionText();
			activeEnvironment.load();
			if(activeEnvironment.isLoaded()) {
				UIConfiguration def = getDefaultConfig();
				if(def==null) {
					def = activeEnvironment.getConfigurations().get(0);				
				}
				refreshProduct(def);
			}
		} catch (IMakerCoreNotFoundException e) {
			StatusHandler.handle(IStatus.ERROR,"iMaker Core was not found.",e); //$NON-NLS-1$
			return;
		} catch (IMakerCoreExecutionException e) {
			StatusHandler.handle(IStatus.ERROR,"An error has occurred while executing iMaker Core. The selected environment cannot be used with iMaker extension.",e); //$NON-NLS-1$
			return;
		}
	}

	/**
	 * @param environment
	 * @return
	 */
	private UIConfiguration getDefaultConfig() {
		List<UIConfiguration> configs;
		try {
			configs = activeEnvironment.getConfigurations();
			UIConfiguration ret = getSelectedProduct();
			for (UIConfiguration config : configs) {
				if(config.isDefaultConfig()) {
					ret = config;
					break;
				}
			}
			return ret;
		} catch (InvocationTargetException e) {
			return null;
		}
	}

	private void refreshProduct(UIConfiguration product) {
		if(product==null) return;
		clearWidgets();
		try {
			product.load();
		} catch (Throwable e) {
			StatusHandler.handle(IStatus.ERROR,"An error has occurred while executing iMaker Core.",e);
			return;
		}
		textProduct.setText(product.getConfigurationName());
		textProduct.setData(product);
		activeEnvironment.setCurrentProduct(product);

		List<UITarget> targets = product.getFilteredTargets();
		if(targets!=null) {
			for(UITarget target: targets) {
				listSource.add(target.getName());
			}						
		}
		settingsTab.setInput(product);
		textProduct.setToolTipText(product.getFilePath());
		activatePlatsimTab(product);
	}


	private void activatePlatsimTab(UIConfiguration product) {
		platsimTab.activate(product.isPlatsimConfiguration());
	}

	/**
	 * 
	 */
	private void clearWidgets() {
		listSource.removeAll();
		listTarget.removeAll();
		resetAdditionalFields();
		settingsTab.setInput(null);
		tabDebug.clear();
		platsimTab.clear();
	}

	private void fixVersionText() {
		StringBuilder version = new StringBuilder(labelVersion.getText());
		int index = version.indexOf(Messages.getString("PreferencesTab.32")); //$NON-NLS-1$
		if(index != -1) {
			version.replace(index, version.length(), Messages.getString("PreferencesTab.32") + " " + iMakerCoreVersion);
			labelVersion.setText(version.toString());			
		}
	}


	private void savePreferencesToFile(String filePath) {
		ImakerProperties uiValues = new ImakerProperties();
		uiValues.putAll(getUIValues());
		
		UIConfiguration current = null;
		current = activeEnvironment.getCurrentProduct();
		
		if (filePath == null||filePath.equals(ProjectManager.NEW_ITEM)) {
			IStructuredSelection selection = new StructuredSelection(tabsViewer
					.getProjectManager().getProject());
			IWorkbench workbench = PlatformUI.getWorkbench();

			NewImakerFileWizard wizard = new NewImakerFileWizard(uiValues, current,
					this);
			wizard.init(workbench, selection);

			WizardDialog dialog = new WizardDialog(workbench
					.getActiveWorkbenchWindow().getShell(), wizard);
			dialog.open();

			if (wizard.isSaved()) {
				IFile file = wizard.getFile();
				String loc = file.getLocation().toString();
				tabsViewer.populateConfigurations(loc);
			}
		} else {
			IFile file = (IFile)projectManager.getImakerFile(new Path(filePath));
			uiValues.saveToFile(file.getLocation().toFile());
			try {
				file.refreshLocal(IResource.DEPTH_ZERO, null);
			} catch (CoreException e) {
				IStatus status = new Status(Status.WARNING,IMakerPlugin.PLUGIN_ID,"Refreshing file ("+file.getLocation().toOSString()+")failled!",e);
				IMakerPlugin.getDefault().getLog().log(status);
				e.printStackTrace();
			}
		}
	}

	private ImakerProperties getUIValues() {
		ImakerProperties prop = new ImakerProperties();
		try {
			UIConfiguration config = (UIConfiguration) textProduct.getData();
			if(config!=null) {
				addField(prop,IMakerKeyConstants.PRODUCT,config.getConfigurationName());
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < listTarget.getItemCount(); i++) {
				sb.append(listTarget.getItem(i));
				if(i<listTarget.getItemCount()-1) {
					sb.append(" ");
				}
			}
			if(sb.length()>0) {
				addField(prop,IMakerKeyConstants.TARGET_LIST,sb.toString());
			}
			if(buttonImageTypePrd.getSelection()) {
				addField(prop,IMakerKeyConstants.TYPE,Messages.getString("PreferencesTab.23")); //$NON-NLS-1$
			} else if(buttonImageTypeSubcon.getSelection()) {
				addField(prop,IMakerKeyConstants.TYPE,Messages.getString("PreferencesTab.24")); //$NON-NLS-1$
			} else {
				addField(prop,IMakerKeyConstants.TYPE,Messages.getString("PreferencesTab.25")); //$NON-NLS-1$
			}
			if(buttonVerbose.getSelection()) {
				addField(prop,IMakerKeyConstants.VERBOSE,Messages.getString("PreferencesTab.26")); //$NON-NLS-1$
			}
			if(buttonSymbolFiles.getSelection()) {
				addField(prop,IMakerKeyConstants.SYMBOLFILES,Messages.getString("PreferencesTab.27")); //$NON-NLS-1$
			}
			addField(prop, IMakerKeyConstants.ADDITIONAL_PARAMETERS, 
					textUserDefinedParameters.getText());
			platsimTab.addToProperties(prop);
			tabDebug.addToProperties(prop);
		} catch(Exception e) {}
		return prop;
	}

	private void addField(Properties properties, String name, String value) {
		if((name==null)||(name.equals(""))||(value==null)||(value.equals(""))) { //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		properties.setProperty(name, value);
	}

	private void fillUIForm(ImakerProperties prop) throws InvocationTargetException {
		String targetProduct = (String) prop.remove(IMakerKeyConstants.PRODUCT);
		if(targetProduct==null) return;
		boolean found = false;
		List<UIConfiguration> configs = activeEnvironment.getConfigurations();
		for (UIConfiguration config : configs) {
			if(config.getConfigurationName().equals(targetProduct)) {
				refreshProduct(config);
				found = true;
				break;
			}
		}

		if (!found) {
			IStatus status = new Status(Status.ERROR,IMakerPlugin.PLUGIN_ID,"Unable to fill dialog ui, because product: "+targetProduct+" not found!");
			IMakerPlugin.getDefault().getLog().log(status);			
			clearWidgets();
			StatusHandler.handle(IStatus.ERROR,"Unable to fill dialog ui, because product: "+targetProduct+" not found!",null);	
			tabsViewer.restoreSelection();
			loadImakerFile(ProjectManager.NEW_ITEM);
			return;
		}
		
		String targets = (String) prop.remove(IMakerKeyConstants.TARGET_LIST);
		if((targets!=null)&&!(targets.equals(""))) { //$NON-NLS-1$
			String[] tars = targets.split(" "); //$NON-NLS-1$
			addToListTarget(tars);
		}
		
		String str = (String)prop.remove(IMakerKeyConstants.TYPE);
		if(str!=null){
			buttonImageTypeRnd.setSelection(false);
			if(str.equals(Messages.getString("PreferencesTab.24"))) { //$NON-NLS-1$
				buttonImageTypeSubcon.setSelection(true);
			} else if(str.equals(Messages.getString("PreferencesTab.23"))) { //$NON-NLS-1$
				buttonImageTypePrd.setSelection(true);
			} else {
				buttonImageTypeRnd.setSelection(true);
			}
		}

		str = (String)prop.remove(IMakerKeyConstants.VERBOSE);
		if((str!=null)&&!(str.equals(""))) { //$NON-NLS-1$
			buttonVerbose.setSelection(true);
		}
		str = (String)prop.remove(IMakerKeyConstants.SYMBOLFILES);
		if((str!=null)&&!(str.equals(""))) { //$NON-NLS-1$
			buttonSymbolFiles.setSelection(true);
		}
		String adds = (String)prop.remove(IMakerKeyConstants.ADDITIONAL_PARAMETERS);
		if(adds!=null) {
			textUserDefinedParameters.setText(adds);
		}
		updateSettings(prop);
		tabDebug.restoreFromProperties(prop);
		platsimTab.restoreFromProperties(prop);
	}

	public void setSettings(SettingsTab tabSettings) {
		this.settingsTab = tabSettings;
	}

	/**
	 *
	 */
	private class PreferenceSelectionListener implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}

		public void widgetSelected(SelectionEvent e) {
			Widget source = e.widget;			
			if(source==buttonSave) {
				savePreferencesToFile(tabsViewer.getSelectedItem());
			} else if(source==buttonSaveAs) {
				savePreferencesToFile(null);
			} else {}
		}
	}

	public void revertSettingsTab() {

	}

	/**
	 * Called when something changed in the settingsTab.
	 * @param modVariable 
	 */
	public void refreshSettingsTab(UIVariable modVariable) {
		if(!settingsTab.isDirty()) {
			return;
		}
		String drive = activeEnvironment.getDrive();
		IRunnableWithProgress op = new IMakerSettingsUpdater(null,drive);
		executeRunnable(op);
		if (modVariable!=null) {
			String text = textUserDefinedParameters.getText();
			if(text!=null) {
				text = text.trim();
				if(!"".equals(text)) {
					String varString = getVariableString(modVariable);
					if (text.indexOf(varString)==-1) {
						text = text +" " + varString;
					}
				} else {
					text = getVariableString(modVariable);
				}
			} else {
				text = getVariableString(modVariable);
			}
			textUserDefinedParameters.setText(text);
		}
		activatePlatsimTab(activeEnvironment.getCurrentProduct());
	}

	private String getVariableString(UIVariable variable) {
		String value = variable.getValue();
		if(value.contains(" ")) {
			return variable.getName()+"=\""+value+"\"";
		} else {
			return variable.getName()+"="+value;					
		}
	}

	private void executeRunnable(IRunnableWithProgress op) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		Shell shell = win != null ? win.getShell() : null;
		try {
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(shell);
			progressMonitorDialog.run(true, false, op);
			settingsTab.getTableViewer().refresh();
		} catch (InvocationTargetException e) {
			StatusHandler.handle(IStatus.ERROR,Messages.getString("Error.1"),e.getTargetException()); //$NON-NLS-1$
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class IMakerSettingsUpdater implements IRunnableWithProgress {
		private List<String> modifiedVariables=null;
		private String environment;
		
		public IMakerSettingsUpdater(List<String> modifiedVariables, String env) {
			this.modifiedVariables = modifiedVariables;
			this.environment = env;
		}
		
		public void run(IProgressMonitor monitor)
		throws InvocationTargetException, InterruptedException {
			try {
				IIMakerWrapper wrapper = activeEnvironment.getImakerWrapper();
				ArrayList<String> parameters = new ArrayList<String>();
				UIConfiguration currentConfiguration = activeEnvironment.getCurrentProduct();
				parameters.add(environment +
						IMakerWrapperPreferences.DEFAULT_COMMAND);
				parameters.add(currentConfiguration.getFilePath());
				UIConfiguration configuration=null;
				List<UIVariable> incVars = null;
				if(modifiedVariables==null) {
					incVars = IMakerUtils.getCommandlineIncludeVariables(currentConfiguration.getVariables());
					List<String> varList = IMakerUtils.convertVariablesToStrings(incVars);
					parameters.addAll(varList);
					configuration = wrapper.getConfiguration(parameters, monitor);
				} else {
					incVars = IMakerUtils.convertStringsToVariables(modifiedVariables);
					parameters.addAll(modifiedVariables);
					configuration = wrapper.getConfiguration(parameters, monitor);
				}
				for (UIVariable variable : incVars) {
					UIVariable v = findVariable(configuration,variable);
					if(v!=null) {
						v.setInclude(true);
					}
				}
				currentConfiguration.setVariables(configuration.getVariables());
			} catch (Exception e) {
				throw new InvocationTargetException(e);
			}
			monitor.worked(100);
			monitor.done();
		}
		private UIVariable findVariable(UIConfiguration configuration,
				UIVariable var) {
			List<UIVariable> variables = configuration.getVariables();
			for (UIVariable variable : variables) {
				if(var.equals(variable)) {
					return variable;
				}
			}
			return null;
		}
	}

	public void runPressed(String item) {
		if(item.equals(ProjectManager.NEW_ITEM)) {
			ImakerProperties run = activeEnvironment.getRunProperties();
			run.clear();
			Properties uiValues = getUIValues();
			run.putAll(uiValues);
			run.setActiveFile(null);
		} else {
			savePreferencesToFile(item);
			ImakerProperties run = activeEnvironment.getRunProperties();
			run.setActiveFile(item);
		}
	}

	public void setDebug(IPropertyViewer debug) {
		tabDebug = debug;
	}

	/**get
	 * @param item
	 * @throws InterruptedException 
	 */
	public void loadImakerFile(String item) throws InvocationTargetException {
		if(!item.equals(ProjectManager.NEW_ITEM)) {
			IFile file = (IFile)projectManager.getImakerFile(new Path(item));
			if(file!=null) {
				ImakerProperties prop = null;
				prop = ImakerProperties.createFromFile(file);
				
				if (prop.isEmpty())
				{
					StatusHandler.handle(IStatus.ERROR,"Unable to load preferences from file "+file.getName()+". Invalid or corrupted IMP file.",null);	
					tabsViewer.restoreSelection();
					loadImakerFile(ProjectManager.NEW_ITEM);
					return;
				}
				if (!prop.containsKey(IMakerKeyConstants.PRODUCT))
				{
					StatusHandler.handle(IStatus.ERROR,"Unable to load product from file "+file.getName()+". Invalid or corrupted IMP file.",null);	
					tabsViewer.restoreSelection();
					loadImakerFile(ProjectManager.NEW_ITEM);
					return;
				}
				if (!prop.containsKey(IMakerKeyConstants.TYPE))
				{
					StatusHandler.handle(IStatus.ERROR,"Unable to load image type from file "+file.getName()+". Invalid or corrupted IMP file. TYPE not given",null);	
					tabsViewer.restoreSelection();
					loadImakerFile(ProjectManager.NEW_ITEM);
					return;
				}
				fillUIForm(prop);				
			}
		} else {
			loadDefaults();
			fillUIForm(activeEnvironment.getRunProperties());
		}
	}


	private void updateSettings(Properties content) {
		List<String> modified = new ArrayList<String>();
		
		String mods = (String)content.get(IMakerKeyConstants.MODIFIED_SETTINGS);
		if(mods==null) return;
		String[] parts;
		if(mods!=null) {
			parts = mods.split(ImakerProperties.SEPARATOR);
			for (int i = 0; i < parts.length; i++) {
				modified.add(parts[i]);
			}			
		}
		mods = (String) content.get(IMakerKeyConstants.ADDITIONAL_PARAMETERS);
		if(mods!=null) {
			parts = mods.split(ImakerProperties.SEPARATOR);
			for (int i = 0; i < parts.length; i++) {
				modified.add(parts[i]);
			}
		}
		String drive = activeEnvironment.getDrive();
		IRunnableWithProgress op = new IMakerSettingsUpdater(modified,drive);
		executeRunnable(op);
	}


	/**
	 * @throws InterruptedException 
	 * 
	 */
	private void loadDefaults() throws InvocationTargetException {
		loadConfigurations();
		tabDebug.clear();
		platsimTab.clear();
		updateDefaultTarget(getDefaultConfig());
	}

	/**
	 * 
	 */
	public void restore() {
		UIConfiguration def = getDefaultConfig();
		refreshProduct(def);
		updateDefaultTarget(def);
	}

	/**
	 * @param def 
	 * 
	 */
	private void updateDefaultTarget(UIConfiguration def) {
		if(def==null) return;
		String target = "default";
		addToListTarget(new String[]{target});
	}

	public void setPlatsim(PlatsimTab tabPlatsim) {
		this.platsimTab = tabPlatsim;
	}

	public String[] getSelectedTargets() {
		return listTarget.getItems();
	}

	public UIConfiguration getSelectedProduct() {
		return activeEnvironment.getCurrentProduct();
	}


	/**
	 * Add the given target to the list of selected targets if
	 * it is not already there
	 * @param name
	 */
	public void addTarget(String newTarget) {
		addToListTarget(new String[]{newTarget.toLowerCase()});
	}


	public void reload(String selection) {
		if(ProjectManager.NEW_ITEM.equals(selection)) {
			try {
				ImakerProperties ui = getUIValues();
				getSelectedProduct().reload();
				fillUIForm(ui);
			} catch (Throwable e) {
				StatusHandler.handle(IStatus.ERROR,"An error has occurred while executing iMaker Core.",e);
				return;
			}
		}
	}
}
