/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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



package com.nokia.s60tools.metadataeditor.editors;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.nokia.s60tools.metadataeditor.MetadataEditorActivator;
import com.nokia.s60tools.metadataeditor.MetadataEditorHelpContextIDs;
import com.nokia.s60tools.metadataeditor.dialogs.AddLibraryDialog;
import com.nokia.s60tools.metadataeditor.util.MetadataEditorConsole;
import com.nokia.s60tools.metadataeditor.util.MetadataFilenameGenerator;
import com.nokia.s60tools.metadataeditor.xml.APIIDGenerator;
import com.nokia.s60tools.metadataeditor.xml.MetadataNotValidException;
import com.nokia.s60tools.metadataeditor.xml.MetadataXML;
import com.nokia.s60tools.metadataeditor.xml.MetadataXMLParser;

/**
 * Editor class for API Metadata Editor
 */
public class APIMetadataEditor extends MultiPageEditorPart implements
		IResourceChangeListener {

	private static final String UNEXPECTED_VALIDITY_ERROR_MSG = "Unexpected validity error occurded when setting values: ";
	private static final String SUBSYSTEM =  "Subsystem: ";
	private static final String COLLECTION = "Collection:";


	/**
	 * Fixed Width and height parameters to set UI components 
	 * precisely 
	 */
	
	private static final int DEFAULT_GROUP_WIDTH = 520;
	
	private static final int LIBS_FIELD_HEIGHT = 8;

	private static final int DESCRIPTION_TEXT_FIELD_LINES = 5;

	private static final int V_SCROLLBAR_WIDHT = 16;

	private static final int VERSION_TXT_DEFAULT_WIDTH = 19;

	private static final int DEFAULT_TEXT_AREA_WIDTH = 450;

	private static final int API_ID_TEXT_AREA_WIDTH = 232;

	private static final int BUTTON_WIDTH = 42;

	private static final int BUTTON_HEIGHT = 25;

	private static final int TYPE_COMBO_WIDTH = 47;

	private static final int CATEGORY_COMBO_WIDTH = 57;

	/**
	 * Metadata XML representing this editor view
	 */
	private MetadataXML xml;

	//Editor window widgets
	private Text APINameTxt;

	private Text APIIDTxt;

	private Text descTxt;

	private Text subsytemTxt;

	private Combo releaseCombo;

	private Combo categoryCombo;

	private Button isDeprecatedButton;

	private Combo releaseDeprecatedCombo;

	private List libs;

	private Button deleteButton;

	private Button addButton;

	private Combo HTMLDOCCombo;

	private Combo adaptationCombo;

	private Button isExtendedButton;

	private Combo extendedCombo;

	private Combo extendedDeprecatedCombo;

	private Color white;

	/**
	 * if editor data is modified, data is dirty
	 */
	private boolean isDirty;

	private Text dataVersionTxt;

	private Label subsystemLabel;
	private Button convertBtn = null;

	/**
	 * Creates a multi-page editor example.
	 */
	public APIMetadataEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Adding context sensitive help id:s to components
	 */
	private void setHelps() {

		PlatformUI.getWorkbench().getHelpSystem().setHelp(APINameTxt,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(descTxt,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(subsytemTxt,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(releaseCombo,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_RELEASE);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(categoryCombo,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_RELEASE);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(isDeprecatedButton,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_RELEASE);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				releaseDeprecatedCombo,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_RELEASE);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(libs,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_LIBS);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_LIBS);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(addButton,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_LIBS);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(HTMLDOCCombo,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_ATTRIBS);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(adaptationCombo,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_ATTRIBS);


	}

	/**
	 * Set context sensitive help id:s for extended SDK parts and convert to 2.0 button.
	 * Used only when dataversion is 1.0.
	 */
	private void setHelpsFor10version() {
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(
						isExtendedButton,
						MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_EXTENDED_SDK);

		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(
						extendedCombo,
						MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_EXTENDED_SDK);

		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(
						extendedDeprecatedCombo,
						MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_EDIT_EXTENDED_SDK);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(convertBtn,
				MetadataEditorHelpContextIDs.METADATA_EDITOR_HELP_CONVERT_FILES);		
				
	}

	/**
	 * Create Metadata Editor page.
	 * 
	 * Creating all Widgets and setting data from this.xml to them.
	 * Creating listeners for selected Widgets. 
	 * 
	 */
	private void createAPIMetadataEditorPage() {


		final Composite composite = new Composite(getContainer(), SWT.SIMPLE);

		final Shell shell = composite.getShell();

		String[] SDKVersionValues = xml.getSDKVersionValues();
		String[] categoryValues = xml.getCategoryValues(xml.getDataVersion());
		String[] noYesValues = xml.getNoYesValues();
		String[] typeValues = xml.getTypeValues();

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginRight = 20;

		composite.setLayout(gridLayout);
		RGB rgbWhite = new RGB(255, 255, 255);
		white = new Color(null, rgbWhite);
		composite.setBackground(white);

		//Create part wheres API informations
		createAPIInformationPart(composite, typeValues);

		//Create part where is release information
		createReleasePart(composite, SDKVersionValues, categoryValues);

		//Create libs and attributes part, Composite is needed for extended SDK part
		Composite attrsAndExtdSDKComposite = createLibsAndAttributesPart(
				composite, shell, noYesValues);

		//Extended SDK only occurs in version 1.0, with 1.0 data version, 
		//also conversion part will be drawn. 
		if(xml.getDataVersion().equals(MetadataXML.DATA_VERSION_1_0)){
			
			//Extended SDK part
			Composite extComposite = createExtendedSDKPart(SDKVersionValues, attrsAndExtdSDKComposite);
			//Conversion to data version 2.0 part
			createConvertTo2_0Part(composite, extComposite);
			//Help id:s to data version 1.0
			setHelpsFor10version();
			
		}
		int index = addPage(composite);
		composite.pack();
		setPageText(index, "Metadata Editor");
	}

	/**
	 * Create part of UI where are Libs and attributes: Libs, HTML Doc and Adaptation
	 * @param composite
	 * @param shell
	 * @param noYesValues
	 * @return Composite to add Extended SDK part in same composite if needed
	 */
	private Composite createLibsAndAttributesPart(final Composite composite,
			final Shell shell, String[] noYesValues) {
		//LIBS & ATTRIBUTES COMPOSITION
		Composite libs_and_attrs_Composite = new Composite(composite,
				SWT.SIMPLE);

		GridLayout libs_and_attrs_Layout = new GridLayout(2, false);
		libs_and_attrs_Layout.marginWidth = 0;
		libs_and_attrs_Layout.marginHeight = 0;
		libs_and_attrs_Layout.horizontalSpacing = 0;//THIS IS HOW CLOSE IT IS TO PREV 

		libs_and_attrs_Composite.setLayout(libs_and_attrs_Layout);
		libs_and_attrs_Composite.setBackground(white);
		GridData libs_and_attrs_GridData = new GridData(
				GridData.HORIZONTAL_ALIGN_FILL);
		libs_and_attrs_GridData.horizontalSpan = 2;
		libs_and_attrs_Composite.setLayoutData(libs_and_attrs_GridData);

		//LIBS
		final Group libsGroup = new Group(libs_and_attrs_Composite,
				SWT.SHADOW_NONE);
		libsGroup.setText("Libs:");
		GridLayout libsGrid = new GridLayout(2, false);

		libsGroup.setBackground(white);
		GridData libsGridData = new GridData();
		libsGroup.setLayout(libsGrid);
		libsGroup.setLayoutData(libsGridData);

		final int listBoxStyleBits = SWT.MULTI | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL;
		libs = new List(libsGroup, listBoxStyleBits);

		if (!xml.getLibs().isEmpty()) {
			Vector<String> v = xml.getLibs();
			String xmlLibs[] = v.toArray(new String[0]);
			libs.setItems(xmlLibs);
			libs.select(0);
		}

		int listWidth = DEFAULT_TEXT_AREA_WIDTH / 3;
		GridData listData = new GridData(listWidth, SWT.DEFAULT);
		listData.verticalSpan = 4;
		int listHeight = libs.getItemHeight() * LIBS_FIELD_HEIGHT;
		Rectangle trim = libs.computeTrim(0, 0, 0, listHeight);
		listData.heightHint = trim.height;

		libs.setLayoutData(listData);

		//LIBS BUTTONS 
		Composite buttonsComposite = new Composite(libsGroup, SWT.SIMPLE);
		GridLayout buttonsLayout = new GridLayout(1, true);
		buttonsLayout.marginWidth = 0;
		buttonsLayout.marginHeight = 0;
		buttonsComposite.setLayout(buttonsLayout);
		buttonsComposite.setLayoutData(new GridData());
		buttonsComposite.setBackground(white);

		deleteButton = new Button(buttonsComposite, SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.setLayoutData(new GridData(BUTTON_WIDTH, BUTTON_HEIGHT));

		//removing selected library
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selection = libs.getSelectionIndex();
				if (selection != -1) {
					libs.remove(selection);
					libs.select(0);
					deleteButton.setEnabled(libs.getItemCount() > 0);
					setDirty(true);
				}
			}
		});

		addButton = new Button(buttonsComposite, SWT.PUSH);
		addButton.setText("Add");
		addButton.setLayoutData(new GridData(BUTTON_WIDTH, BUTTON_HEIGHT));
		deleteButton.setEnabled(libs.getItemCount() > 0);

		final AddLibraryDialog a = new AddLibraryDialog(shell);

		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				a.open();
				if (a.getReturnCode() == IDialogConstants.OK_ID) {
					String libname = a.getText();
					if (MetadataXML.containForbiddenChars(libname)) {
						showInformationDialog("Forbidden characters removed",
								getForbiddenCharsErrorMessage("Lib"));
					}
					libname = MetadataXML.removeForbiddenChars(libname);
					libs.add(libname, libs.getItemCount());
					libs.select(0);
					deleteButton.setEnabled(libs.getItemCount() > 0);
					setDirty(true);
				}//else, cancel is pushed
			}
		});

		buttonsComposite.pack();

		//COMPOSITE FOR ATTRIBUTES AND EXTENDED SDK
		Composite attrsAndExtdSDKComposite = new Composite(
				libs_and_attrs_Composite, SWT.SIMPLE | SWT.TOP);

		GridLayout attrsAndExtdSDKCompositeLayout = new GridLayout(1, true);
		attrsAndExtdSDKCompositeLayout.marginRight = 0;
		attrsAndExtdSDKCompositeLayout.marginHeight = 0;
		attrsAndExtdSDKCompositeLayout.marginWidth = 0;
		attrsAndExtdSDKCompositeLayout.marginTop = 0;

		attrsAndExtdSDKComposite.setLayout(attrsAndExtdSDKCompositeLayout);
		attrsAndExtdSDKComposite.setLayoutData(new GridData(SWT.RIGHT,
				SWT.FILL, true, true));
		attrsAndExtdSDKComposite.setBackground(white);

		//ATTRIBUTES GROUP				
		Group attributesGroup = new Group(attrsAndExtdSDKComposite,
				SWT.SHADOW_NONE);
		attributesGroup.setText("Attributes:");
		GridLayout attributesGrid = new GridLayout(4, false);
		attributesGrid.marginRight = 0;
		attributesGrid.marginTop = 0;

		attributesGroup.setBackground(white);
		attributesGroup.setLayout(attributesGrid);
		GridData attributesData = new GridData(SWT.FILL, SWT.DEFAULT, true,
				true);
		attributesData.heightHint = 50;
		attributesGroup.setLayoutData(attributesData);

		// HTML Doc Provided
		Label html_doc_providedLabel = new Label(attributesGroup,
				SWT.HORIZONTAL);
		html_doc_providedLabel.setText("HTML Doc Provided:");
		html_doc_providedLabel.setLayoutData(new GridData());
		html_doc_providedLabel.setBackground(white);
		HTMLDOCCombo = new Combo(attributesGroup, SWT.READ_ONLY);
		HTMLDOCCombo.setItems(noYesValues);
		setComboSelection(noYesValues, HTMLDOCCombo, xml.getHtmlDocProvided());
		HTMLDOCCombo.setLayoutData(new GridData());
		addSetDirtyModifyListener(HTMLDOCCombo);

		//ADAPTATION
		Label adaptationLabel = new Label(attributesGroup, SWT.HORIZONTAL);
		adaptationLabel.setText("Adaptation:");
		adaptationLabel.setLayoutData(new GridData());
		adaptationLabel.setBackground(white);
		adaptationCombo = new Combo(attributesGroup, SWT.READ_ONLY);
		adaptationCombo.setItems(xml.getNoYesValues());
		setComboSelection(noYesValues, adaptationCombo, xml.getAdaptation());
		adaptationCombo.setLayoutData(new GridData());
		addSetDirtyModifyListener(adaptationCombo);
		return attrsAndExtdSDKComposite;
	}

	/**
	 * Createa part of editor wheres API name, ID, Data version, Type, 
	 * Description and Subsystem (Collection in data version 2.0 and onwards)
	 * @param composite
	 * @param typeValues
	 */
	private void createAPIInformationPart(final Composite composite,
			String[] typeValues) {

		//API NAME
		Label APINameLabel = new Label(composite, SWT.HORIZONTAL);
		APINameLabel.setText("Name:");
		APINameLabel.setBackground(white);
		APINameTxt = new Text(composite, SWT.LEFT | SWT.BORDER | SWT.BORDER);
		APINameTxt.setLayoutData(new GridData(DEFAULT_TEXT_AREA_WIDTH,
				SWT.DEFAULT ));
		APINameTxt.setText(xml.getAPIName());

		addModifyListener(APINameTxt);

		//API ID
		Label APIIDLabel = new Label(composite, SWT.HORIZONTAL);
		APIIDLabel.setText("ID:");
		APIIDLabel.setBackground(white);

		Composite APIComposite = new Composite(composite, SWT.SIMPLE);
		GridLayout APILayout = new GridLayout(5, false);
		APILayout.marginWidth = 0;
		APILayout.marginHeight = 0;
		APIComposite.setLayout(APILayout);
		APIComposite.setBackground(white);
		APIComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false,
				false));

		APIIDTxt = new Text(APIComposite, SWT.LEFT | SWT.BORDER);
		APIIDTxt
				.setLayoutData(new GridData(API_ID_TEXT_AREA_WIDTH, SWT.DEFAULT));
		APIIDTxt.setEnabled(false);
		APIIDTxt.setText(xml.getAPIID());

		//API DATA VERSION
		Label APIDataVersionLabel = new Label(APIComposite, SWT.HORIZONTAL);
		APIDataVersionLabel.setText("Data version:");
		APIDataVersionLabel.setLayoutData(new GridData());
		APIDataVersionLabel.setBackground(white);
		dataVersionTxt = new Text(APIComposite, SWT.LEFT | SWT.BORDER);
		dataVersionTxt.setLayoutData(new GridData(VERSION_TXT_DEFAULT_WIDTH,
				SWT.DEFAULT));
		dataVersionTxt.setEnabled(false);
		dataVersionTxt.setText(xml.getDataVersion());

		//API TYPE
		Label APITypeLabel = new Label(APIComposite, SWT.HORIZONTAL);
		APITypeLabel.setText("Type:");
		APITypeLabel.setLayoutData(new GridData());
		APITypeLabel.setBackground(white);
		Combo typeCombo = new Combo(APIComposite, SWT.READ_ONLY);
		typeCombo.setItems(typeValues);
		setComboSelection(typeValues, typeCombo, xml.getType());
		typeCombo.setLayoutData(new GridData(TYPE_COMBO_WIDTH, SWT.DEFAULT));
		typeCombo.setEnabled(false);

		//DESCRIPTION
		Label descLabel = new Label(composite, SWT.HORIZONTAL | SWT.TOP);
		descLabel.setText("Description:");
		descLabel
				.setLayoutData(new GridData(SWT.DEFAULT, SWT.TOP, false, false));
		descLabel.setBackground(white);
		descTxt = new Text(composite, SWT.LEFT | SWT.BORDER | SWT.MULTI
				| SWT.WRAP | SWT.V_SCROLL);
		int descHeight = DESCRIPTION_TEXT_FIELD_LINES * descTxt.getLineHeight();
		GridData descData = new GridData(DEFAULT_TEXT_AREA_WIDTH
				- V_SCROLLBAR_WIDHT, descHeight);

		descTxt.setLayoutData(descData);
		descTxt.setText(xml.getDescription());
		addModifyListener(descTxt);

		subsystemLabel = new Label(composite, SWT.HORIZONTAL);
		setSubsystemLabel();
		subsystemLabel.setLayoutData(new GridData());
		subsystemLabel.setBackground(white);
		subsytemTxt = new Text(composite, SWT.LEFT | SWT.BORDER);
		subsytemTxt.setLayoutData(new GridData(DEFAULT_TEXT_AREA_WIDTH,
				SWT.DEFAULT ));
		subsytemTxt.setText(xml.getSubsystem());
		addModifyListener(subsytemTxt);
	}

	/**
	 * Create part of UI where are release information: since version, catagory and deprecation
	 * @param composite
	 * @param SDKVersionValues
	 * @param categoryValues
	 */
	private void createReleasePart(final Composite composite,
			String[] SDKVersionValues, String[] categoryValues) {
		//RELEASE GROUP				
		Group releaseGroup = new Group(composite, SWT.SHADOW_NONE);
		releaseGroup.setText("Release:");
		GridLayout releaseGrid = new GridLayout(8, false);
		releaseGroup.setBackground(white);
		GridData releaseGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		releaseGridData.horizontalSpan = 2;
		releaseGroup.setLayout(releaseGrid);
		releaseGroup.setLayoutData(releaseGridData);

		//RELEASE VERSION
		Label versionLabel = new Label(releaseGroup, SWT.HORIZONTAL);
		versionLabel.setText("Since version:");
		versionLabel.setLayoutData(new GridData());
		versionLabel.setBackground(white);

		releaseCombo = new Combo(releaseGroup, SWT.READ_ONLY);
		releaseCombo.setItems(SDKVersionValues);
		setComboSelection(SDKVersionValues, releaseCombo, xml.getReleaseSince());
		releaseCombo.setLayoutData(new GridData());
		addSetDirtyModifyListener(releaseCombo);

		//RELEASE CATEGORY
		Label categoryLabel = new Label(releaseGroup, SWT.HORIZONTAL);
		categoryLabel.setText("  Category:");
		categoryLabel.setLayoutData(new GridData());
		categoryLabel.setBackground(white);
		categoryCombo = new Combo(releaseGroup, SWT.READ_ONLY);
		categoryCombo.setItems(categoryValues);
		setComboSelection(categoryValues, categoryCombo, xml
				.getReleaseCategory());
		categoryCombo.setLayoutData(new GridData(CATEGORY_COMBO_WIDTH,
				SWT.DEFAULT));
		addSetDirtyModifyListener(categoryCombo);

		//RELEASE DEPRECATED
		Label deprecatedLabel = new Label(releaseGroup, SWT.HORIZONTAL);
		deprecatedLabel.setText("   Deprecated:");
		deprecatedLabel.setLayoutData(new GridData());
		deprecatedLabel.setBackground(white);

		isDeprecatedButton = new Button(releaseGroup, SWT.CHECK);
		isDeprecatedButton.setLayoutData(new GridData());
		isDeprecatedButton.setSelection(xml.isReleaseDeprecated());
		Label deprecatedSinceLabel = new Label(releaseGroup, SWT.HORIZONTAL);
		deprecatedSinceLabel.setText(" Deprecated since:");
		deprecatedSinceLabel.setLayoutData(new GridData());
		deprecatedSinceLabel.setBackground(white);

		releaseDeprecatedCombo = new Combo(releaseGroup, SWT.READ_ONLY);
		releaseDeprecatedCombo.setItems(xml.getSDKVersionValues());
		if (xml.isReleaseDeprecated()) {
			setComboSelection(SDKVersionValues, releaseDeprecatedCombo, xml
					.getReleaseDeprecatedSince());
		}
		releaseDeprecatedCombo.setLayoutData(new GridData());
		addSetDirtyModifyListener(releaseDeprecatedCombo);

		if (!isDeprecatedButton.getSelection()) {
			releaseDeprecatedCombo.setEnabled(false);
		}

		//Release is DEPRECATED button
		isDeprecatedButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//If state changes, setting file as dirty
				if (xml.isReleaseDeprecated() != isDeprecatedButton
						.getSelection()) {
					setDirty(true);
				}
				//Changing combos to disabled/non disabled when check box is ticked
				if (isDeprecatedButton.getSelection()) {
					releaseDeprecatedCombo.setEnabled(true);
				} else {
					releaseDeprecatedCombo.setEnabled(false);
					releaseDeprecatedCombo.select(0);
				}
			}
		});
	}

	/**
	 * Setting label to subsystem. Label depends on data version, 
	 * with dataversion 1.0 its "Subsystem" and 2.0 onwards its "Collection".
	 */
	private void setSubsystemLabel() {
		if(xml.getDataVersion().equals(MetadataXML.DATA_VERSION_1_0)){
			subsystemLabel.setText(SUBSYSTEM);
		}else{
			subsystemLabel.setText(COLLECTION);
		}
	}

	/**
	 * Part in UI to convert data to version 2.0
	 * @param composite
	 * @param groupWidth
	 */
	private void createConvertTo2_0Part(Composite composite, Composite extComposite) {
		
		//Convert to 2.0 COMPOSITION
		Composite convertComposite = new Composite(
				composite, SWT.SIMPLE);

		GridLayout convertLayout = new GridLayout(1, true);
		
		convertLayout.marginRight = 0;
		convertLayout.marginHeight = 0;
		convertLayout.marginWidth = 0;
		convertLayout.marginTop = 0;
		
		convertComposite.setLayout(convertLayout);
		GridData convertData = new GridData();
		convertData.horizontalSpan = 4;
		convertComposite.setLayoutData(convertData);
		convertComposite.setBackground(white);
		

		//LIBS
		final Group convertGroup = new Group(convertComposite,
				SWT.SHADOW_NONE);
		convertGroup.setText("Metadata Data Version Conversion:");
		GridLayout convertGrid = new GridLayout(2, false);
		
		convertGroup.setBackground(white);
		GridData libsGridData = new GridData(DEFAULT_GROUP_WIDTH , SWT.DEFAULT);//GridData.FILL_HORIZONTAL
		libsGridData.horizontalSpan = 8;
		convertGroup.setLayout(convertGrid);
		convertGroup.setLayoutData(libsGridData);		
		
		
		convertBtn  = new Button(convertGroup, SWT.PUSH);
		convertBtn .setText("Convert to v. 2.0");
		convertBtn .addSelectionListener(new ConvertTo2_0_Listener(extComposite, convertComposite));
	}
	
	/**
	 * Listener for convert button
	 */
	private class ConvertTo2_0_Listener extends SelectionAdapter{
		
		private Composite extComposite;
		private Composite convertComposite;
		
		public ConvertTo2_0_Listener(Composite extComposite, Composite convertComposite){
			this.extComposite = extComposite;
			this.convertComposite = convertComposite;
		}
		// conversion to version 2.0		
		public void widgetSelected(SelectionEvent event) {

			try {
				//Setting data from UI to XML, when conversion is done, 
				//and if release category is changed, it wont change right if data is not set. 
				setDataFromEditorToXML();
			} catch (NoSuchAlgorithmException e) {
				//This really cannot occur, because NoSuchAlgorithmException
				//is thrown when id is generated, and now we have existing API.
				MetadataEditorConsole.getInstance().println(
								"NoSuchAlgorithmException on conversion: " + e);
				e.printStackTrace();
			}
			
			// update xml data version
			xml.updateToVersion2_0();
			// update data version to UI
			dataVersionTxt.setText(xml.getDataVersion());
			// update subsystem label
			setSubsystemLabel();
			// update category values to combo
			String[] categoryValues = xml.getCategoryValues(xml.getDataVersion());
			categoryCombo.setItems(categoryValues);
			//setting selection to category combo
			setComboSelection(categoryValues, categoryCombo, xml.getReleaseCategory());
			// then clean extended SDK part
			extComposite.dispose();
			// finally clean convert composite
			convertComposite.dispose();

		}	
	}


	/**
	 * Create extended SDK parts
	 * @param SDKVersionValues
	 * @param attrsAndExtdSDKComposite
	 */
	private Composite createExtendedSDKPart(String[] SDKVersionValues,
			Composite attrsAndExtdSDKComposite) {
		//EXTENDED GROUP		

		Composite extComposite = new Composite(
				attrsAndExtdSDKComposite, SWT.SIMPLE);
		GridLayout attrsAndExtdSDKCompositeLayout = new GridLayout(1, true);
		attrsAndExtdSDKCompositeLayout.marginRight = 0;
		attrsAndExtdSDKCompositeLayout.marginHeight = 0;
		attrsAndExtdSDKCompositeLayout.marginWidth = 0;
		attrsAndExtdSDKCompositeLayout.marginTop = 0;

		extComposite.setLayout(attrsAndExtdSDKCompositeLayout);
		extComposite.setLayoutData(new GridData(SWT.RIGHT,
				SWT.FILL, true, true));
		extComposite.setBackground(white);		
		
		
		Group extendedGroup = new Group(extComposite,
				SWT.SHADOW_NONE);
		extendedGroup.setText("Extended SDK:");
		GridLayout extendedCompositeGrid = new GridLayout(3, false);
		extendedCompositeGrid.marginRight = 0;
		extendedCompositeGrid.marginBottom = 0;

		extendedGroup.setBackground(white);
		extendedGroup.setLayout(extendedCompositeGrid);
		GridData extendedGroupGridData = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		extendedGroupGridData.verticalAlignment = SWT.END;
		extendedGroup.setLayoutData(extendedGroupGridData);

		//EXTENDED SDK
		isExtendedButton = new Button(extendedGroup, SWT.CHECK);
		isExtendedButton.setLayoutData(new GridData());
		isExtendedButton.setSelection(xml.isExtendedSDK());
		Label extSDKLabel = new Label(extendedGroup, SWT.HORIZONTAL);
		extSDKLabel.setText("Is Extended SDK   ");
		extSDKLabel.setLayoutData(new GridData());
		extSDKLabel.setBackground(white);

		Composite extendedValuesComposite = new Composite(extendedGroup,
				SWT.SIMPLE);
		GridLayout extendedValuesLayout = new GridLayout(2, false);
		extendedValuesLayout.marginRight = 0;
		extendedValuesLayout.marginBottom = 0;

		extendedValuesComposite.setLayout(extendedValuesLayout);
		extendedValuesComposite.setBackground(white);
		GridData extendedValuesGridData = new GridData();
		extendedValuesComposite.setLayoutData(extendedValuesGridData);

		//EXTENDED SKD SINCE
		Label extSinceLabel = new Label(extendedValuesComposite, SWT.HORIZONTAL);
		extSinceLabel.setText("Since version:");
		extSinceLabel.setLayoutData(new GridData());
		extSinceLabel.setBackground(white);

		extendedCombo = new Combo(extendedValuesComposite, SWT.READ_ONLY);
		extendedCombo.setItems(SDKVersionValues);
		if (xml.isExtendedSDK()) {
			setComboSelection(SDKVersionValues, extendedCombo, xml
					.getExtendedSDKSince());
		}
		extendedCombo.setLayoutData(new GridData());
		addSetDirtyModifyListener(extendedCombo);
		if (!isExtendedButton.getSelection()) {
			extendedCombo.setEnabled(false);
			extendedCombo.select(0);
		}

		//EXTENDED SDK DEPRECATED SINCE
		Label extDeprecatedLabel = new Label(extendedValuesComposite,
				SWT.HORIZONTAL);
		extDeprecatedLabel.setText("Deprecated since:");
		extDeprecatedLabel.setLayoutData(new GridData());
		extDeprecatedLabel.setBackground(white);
		extendedDeprecatedCombo = new Combo(extendedValuesComposite,
				SWT.READ_ONLY);
		extendedDeprecatedCombo.setItems(xml.getSDKVersionValues());
		if (xml.isExtendedSDKDeprecated()) {
			setComboSelection(SDKVersionValues, extendedDeprecatedCombo, xml
					.getExtendedSDKDeprecatedSince());
		}
		extendedDeprecatedCombo.setLayoutData(new GridData());
		addSetDirtyModifyListener(extendedDeprecatedCombo);
		if (!isExtendedButton.getSelection()) {
			extendedDeprecatedCombo.setEnabled(false);
			extendedDeprecatedCombo.select(0);
		}
		//EXTENDED SDK CHECK BOX LISTENER
		isExtendedButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				//If state changes, setting file as dirty
				if (xml.isExtendedSDK() != isExtendedButton.getSelection()) {
					setDirty(true);
				}
				//Changing combos to disabled/non disabled when check box is ticked
				if (isExtendedButton.getSelection()) {
					extendedCombo.setEnabled(true);
					extendedDeprecatedCombo.setEnabled(true);
				} else {
					extendedCombo.setEnabled(false);
					extendedCombo.select(0);
					extendedDeprecatedCombo.setEnabled(false);
					extendedDeprecatedCombo.select(0);
				}
			}
		});
		
		return extComposite;
	}

	/**
	 * Set Editor as dirty when modification to data has been made
	 * @param txt
	 */
	private void addModifyListener(Text txt) {
		txt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setDirty(true);
			}
		});
	}

	/**
	 * Set Editor as dirty when modification to data has been made
	 * @param combo
	 */
	private void addSetDirtyModifyListener(final Combo combo) {
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setDirty(true);
			}
		});
	}

	/**
	 * Selects given value as selected to {@link Combo}
	 * @param values
	 * @param combo
	 * @param value
	 */
	private void setComboSelection(String[] values, Combo combo, String value) {
		if (!value.equals(MetadataXML.NOT_SET)) {
			for (int i = 0; i < values.length; i++) {
				if (value.equals(values[i])) {
					combo.select(i);
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createPages()
	 */
	protected void createPages() {

		createAPIMetadataEditorPage();
		//When opening very first time, listeners will set file to dirty
		//Setting file back to not dirty
		if (xml.isValid()) {
			setDirty(false);
		}
		//Setting context sensitive help id:s to components
		setHelps();

	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#dispose()
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
		white.dispose();
	}

	/**
	 * If Metadata XML is modified, editor is dirty and can be saved.
	 * 
	 * If editor data is changed and then changed back to original
	 * data contents, status still remain as dirty.
	 */
	public boolean isDirty() {
		return this.isDirty;
	}

	/**
	 * Sets data from UI to {@link #xml} If some text widgets contains 
	 * forbidden characters, those will be removed and 
	 * information notice will be given to user. Forbidden characters
	 * in XML data is <, > and &.
	 * 
	 * @throws NoSuchAlgorithmException if something fails when creating ID to API.
	 */
	private void setDataFromEditorToXML() throws NoSuchAlgorithmException {

		//This is not allowed to throw an exception, because if it is thrown
		//data setting is incomplete and some validity errors stays unknown 

		StringBuffer errors = new StringBuffer();

		//If XML is just created new file, we need to create unique ID
		//That can be done only once, setting APIName will set XML to not new
		if (xml.isNew()
				&& !APINameTxt.getText().trim().equals(MetadataXML.NOT_SET)) {
			APIIDGenerator gen = new APIIDGenerator();
			xml.setAPIID(gen.getID(APINameTxt.getText()));
			this.APIIDTxt.setText(xml.getAPIID());
			if (MetadataXML.containForbiddenChars(APINameTxt.getText())) {
				errors.append(getForbiddenCharsErrorMessage("API Name"));
			}
			xml.setAPIName(APINameTxt.getText());
			this.APINameTxt.setText(xml.getAPIName());
		}
		//Else file is not new one
		else {
			xml.setAPIName(APINameTxt.getText());
			if (MetadataXML.containForbiddenChars(APINameTxt.getText())) {
				errors.append(getForbiddenCharsErrorMessage("API Name"));
				this.APINameTxt.setText(xml.getAPIName());
			}
		}

		xml.setDescription(descTxt.getText());
		if (MetadataXML.containForbiddenChars(descTxt.getText())) {
			errors.append(getForbiddenCharsErrorMessage("Description"));
			this.descTxt.setText(xml.getDescription());
		}

		xml.setSubsystem(subsytemTxt.getText());
		if (MetadataXML.containForbiddenChars(subsytemTxt.getText())) {
			errors.append(getForbiddenCharsErrorMessage(xml.getSubsystemString()));
			this.subsytemTxt.setText(xml.getSubsystem());
		}

		//Category must set first, so release since attribute check will work properly
		xml.setReleaseCategory(categoryCombo.getText());
		try {
			xml.setReleaseSince(releaseCombo.getText());
		} catch (MetadataNotValidException e) {
			//Should not be able to happen, because values is coming from list
			MetadataEditorConsole.getInstance().println(UNEXPECTED_VALIDITY_ERROR_MSG +e);
			e.printStackTrace();
		}
		xml.setReleaseDeprecated(isDeprecatedButton.getSelection());
		if (xml.isReleaseDeprecated()) {
			try {
				xml.setReleaseDeprecatedSince(releaseDeprecatedCombo.getText());
			} catch (MetadataNotValidException e) {
				//Should not be able to happend, because values is comimg from list
				MetadataEditorConsole.getInstance().println(UNEXPECTED_VALIDITY_ERROR_MSG +e);				
				e.printStackTrace();
			}
		}
		String newLibs[] = libs.getItems();
		Vector<String> libsVector = new Vector<String>(newLibs.length);
		for (int i = 0; i < newLibs.length; i++) {
			libsVector.add(newLibs[i]);
		}
		xml.setLibs(libsVector);
		xml.setHtmlDocProvided(xml.isYes(HTMLDOCCombo.getText()));
		xml.setAdaptation(xml.isYes(adaptationCombo.getText()));

		//With 1.0 data version there is also extended SDK datas
		if (xml.getDataVersion().equals(MetadataXML.DATA_VERSION_1_0)){
			//If data is extended SDK
			setExtendedSDKDataFromEditorToXML();
		}

		//Finally showing an information notice if there was forbidden characted
		//that was removed from text fields.
		if (errors.length() > 1) {
			showInformationDialog("Forbidden characters removed", errors
					.toString());
		}

	}

	/**
	 * Sets Extended SDK data from UI to {@link #xml} 
	 * 
	 * For Extended SDK part only, use only with data version 1.0,
	 * with data version 2.0 Extended SDK is removed.
	 */
	private void setExtendedSDKDataFromEditorToXML() {
		xml.setExtendedSDK(isExtendedButton.getSelection());

		if(xml.isExtendedSDK()) {
			try {
				xml.setExtendedSDKSince(extendedCombo.getText());
			} catch (MetadataNotValidException e) {
				//Should not be able to happend, because values is comimg from list
				e.printStackTrace();
			}
			//If extended SDK is set as deprecated, setting combo value
			if (!extendedDeprecatedCombo.getText().equals(MetadataXML.NOT_SET)) {
				try {
					xml.setExtendedSDKDeprecatedSince(extendedDeprecatedCombo
							.getText());
				} catch (MetadataNotValidException e) {
					//Should not be able to happen, because values is coming from list
					MetadataEditorConsole.getInstance().println(UNEXPECTED_VALIDITY_ERROR_MSG +e);					
					e.printStackTrace();
				}				
			}
			//Otherwise extended SDK is not deprecated, setting here making sure
			//that there is no values set in xml
			else {
				xml.setExtendedSDKDeprecated(false);
			}
		}
	}

	/**
	 * Create a error message when forbidden characters (forbidden to XML) occurs
	 * @param fieldName
	 * @return error message
	 */
	private String getForbiddenCharsErrorMessage(String fieldName) {
		StringBuffer buf = new StringBuffer();
		buf.append(fieldName);
		buf.append(" cannot contain the following characters: ");
		for (int i = 0; i < MetadataXML.FORBIDDEN_CHARS.length; i++) {
			//Dialogs prints & -char only if its &&
			if (MetadataXML.FORBIDDEN_CHARS[i].equals("&")) {
				buf.append("&&");
			} else {
				buf.append(MetadataXML.FORBIDDEN_CHARS[i]);
			}
			if (i < (MetadataXML.FORBIDDEN_CHARS.length - 1)) {
				buf.append(", ");
			}
		}
		buf.append(". Those characters are removed.");
		buf.append("\n");
		return buf.toString();
	}

	/**
	 * Show an error dialog
	 * @param title
	 * @param message
	 * @param errors
	 */
	private void showErrorDialog(String title, String message, String errors) {
		Status status = new Status(IStatus.ERROR,
				MetadataEditorActivator.PLUGIN_ID, 0, errors, null);
		Shell sh;
		sh = getShell();

		ErrorDialog.openError(sh, title, message, status);
	}

	/**
	 * Get shell, checks if Shell is available for different purposes.
	 * @return shell
	 */
	private Shell getShell() {
		Shell sh;
		if (getContainer() != null) {
			try {
				sh = getContainer().getShell();
			} catch (SWTException e) {
				sh = MetadataEditorActivator.getCurrentlyActiveWbWindowShell();
			}
		} else {
			sh = MetadataEditorActivator.getCurrentlyActiveWbWindowShell();
		}
		return sh;
	}

	/**
	 * Show an information dialog
	 * @param title
	 * @param message
	 */
	private void showInformationDialog(String title, String message) {
		Shell sh;
		sh = getShell();

		MessageDialog.openInformation(sh, title, message);
	}
	

	/**
	 * Saves the document.
	 * If API name is changed, also filename will be changed.
	 * 
	 * If API name is changed, and the file is external file opened
	 * outside of Eclipse (Carbide) workspace finally the file will be
	 * closed and information notice will be given to user, saying that 
	 * the file must be reopend. 
	 * 
	 * @see #renameExternalFile(String) documentation for additional information.
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor) 
	 */
	public void doSave(IProgressMonitor monitor) {


		InputStream stream = null;
		try {

			//Setting data from UI to this.xml
			setDataFromEditorToXML();

			if (!xml.isValid()) {
				showErrorDialog("Metadata XML is not valid",
						"Save is not allowed.", xml.getValidityErrors());
				return;
			}

			boolean isNameChanged = xml.isAPINameChanged();


			IEditorInput editorInput = getEditorInput();

			//Type is IFileEditorInput allways when file is opened from project
			//If file opened outside project, editor type is IPathEditorInput
			if (editorInput instanceof IFileEditorInput) {
				IFile file;
				//Just making sure that stream is closed before renaming file
				try {
					stream = openContentStream();

					IFileEditorInput iFile = (IFileEditorInput) editorInput;
					file = iFile.getFile();

					if (file.exists()) {
						file.setContents(stream, true, true, monitor);
					} else {
						file.create(stream, true, monitor);
					}			
				} finally {
					stream.close();
				}

				//If API name is changed, renaming file
				if (isNameChanged) {
					renameFile(file);
				}

			}
			//Else file is opened outside of Carbide project
			else {
				IPathEditorInput javaInput = (IPathEditorInput) editorInput;
				IPath path = javaInput.getPath();				
				String fileLocation = path.toOSString();
				//First write changes to file
				xml.toFile(fileLocation);
				
				//If API name is changed, renaming file
				if (isNameChanged) {
					renameExternalFile(fileLocation);
				}				
			}

			setDirty(false);

		} catch (CoreException e) {
			showErrorDialog("Errors on save", "Save was not complete.", e
					.getMessage());
			MetadataEditorConsole.getInstance().println(
					"CoreException on save: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			showErrorDialog("Errors on save", "Save was not complete.", e
					.getMessage());
			MetadataEditorConsole.getInstance().println(
					"IOException on save: " + e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			showErrorDialog("Errors on save", "Save was not complete.", e
					.getMessage());
			MetadataEditorConsole.getInstance().println(
					"NoSuchAlgorithmException on save: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			showErrorDialog("Errors on save", "Save was not complete.", e
					.getMessage());
			MetadataEditorConsole.getInstance().println(
					"Exception on save: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Renames external file when API name is changed by user.
	 * 
	 * Editor will be closed and information notice will be given to user.
	 * 
	 * Editor must be closed because renaming file will let old "deleted" file
	 * as open to Metadata Editor, so then it's possible to edit file what
	 * does not exist. 
	 * 
	 * @param fileLocation
	 * @throws Exception
	 */
	
	private void renameExternalFile(String fileLocation) throws Exception {
		
		java.io.File javaFile = new java.io.File(fileLocation);
		String newFileName = MetadataFilenameGenerator.createMetadataFilename(xml
				.getAPIName());
		String newFileLocation = 
			fileLocation.substring(0, fileLocation.lastIndexOf('\\')+1);
		newFileLocation += newFileName;
		java.io.File newJavaFile = new java.io.File(newFileLocation);
		
		javaFile.renameTo(newJavaFile);
		
		setPartName(newFileName);

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();

		//runnable to close old file
		final IWorkspaceRunnable runClose = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
					IWorkbench wb = PlatformUI.getWorkbench();
					IWorkbenchWindow wbWindow = wb.getActiveWorkbenchWindow();
					IWorkbenchPage wbPage = wbWindow.getActivePage();
					wbPage.closeEditor(wbPage.getActiveEditor(), false);
			}
		};

		showInformationDialog("Reopen external file",
				"The external file under editing will be closed. API name change" 
				+" caused renaming of the file into "
				+newFileName +" and the file must be reopened."
				+"\n\n\rPlease reopen the renamed file by using 'File -> Open File...'"
				+" and browse for file '"
				+newFileLocation +"'.");

		MetadataEditorConsole.getInstance().println(
				"External file: " + fileLocation 
				+" was renamed to: " +newFileLocation
				+" and was closed. Reopen must do to edit file again.");

		
		//Starting runnable to close old file 
		workspace.run(runClose, null, IWorkspace.AVOID_UPDATE, null);
		

	}	
	
	/**
	 * Renames file when API name is changed by user.
	 * 
	 * Doing next steps in following order: 
	 * 			1) copy file
	 * 			2) Close old file
	 *			3) Open new file 
	 *			4) Delete old file
	 *          5) Show an information dialog that the file name was changed
	 * 
	 * @param file
	 * @throws CoreException
	 */
	private void renameFile(final IFile file) throws Exception {
		
		//creating new filename
		IPath path = file.getFullPath();
		path = path.removeLastSegments(1);
		final String fileName = MetadataFilenameGenerator.createMetadataFilename(xml
				.getAPIName());
		final IPath newPath = path.append(fileName);

		//Doing copy...
		file.copy(newPath, true, null);
		String containerName = file.getFullPath().removeLastSegments(1)
				.toOSString();
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = wsRoot.findMember(new Path(containerName));

		IContainer container = (IContainer) resource;
		final IFile newFile = container.getFile(new Path(fileName));

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();

		
		// Runnable to delete old file and finaly show an information dialog 
		final IWorkspaceRunnable runDelete = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {

				file.delete(true, true, null);

				MetadataEditorConsole.getInstance().println(
						"API name changed, filename changed also from: "
								+ file.getFullPath() + " to: " + newPath);

				//Finally show an information dialog that the file name was changed
				showInformationDialog( "Metadata file name changed", "API name change" 
						+" caused renaming of the metadata file into "
						+fileName );				
			}
		};

		//Runnable to open new file
		final IWorkspaceRunnable runOpen = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				// do the actual work in here

				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, newFile, true);

				} catch (PartInitException e) {
					e.printStackTrace();
					Status status = new Status(IStatus.ERROR,
							"com.nokia.s60tools.metadataeditor", 0, e
									.getMessage(), e);

					throw new CoreException(status);
				} finally {
					//When new file opened, deleting old file
					workspace.run(runDelete, null, IWorkspace.AVOID_UPDATE,
							null);
				}

			}
		};

		//runnable to close old file
		final IWorkspaceRunnable runClose = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					IWorkbench wb = PlatformUI.getWorkbench();
					IWorkbenchWindow wbWindow = wb.getActiveWorkbenchWindow();
					IWorkbenchPage wbPage = wbWindow.getActivePage();
					wbPage.closeEditor(wbPage.getActiveEditor(), false);

				} finally {
					//When old file closed, opening new file
					workspace.run(runOpen, null, IWorkspace.AVOID_UPDATE, null);
				}

			}
		};

		//Startting runnable to close old file whitch starts runnable to 
		//open new file when finished, which starts runnable to delete
		//old file when finished.
		workspace.run(runClose, null, IWorkspace.AVOID_UPDATE, null);
		

		

	}

	/**
	 * Saves the document as another file. 
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	public void doSaveAs() {

		try {
			IEditorInput editorInput = getEditorInput();

			//Setting data from UI to this.xml
			setDataFromEditorToXML();

			if (!xml.isValid()) {
				showErrorDialog("Metadata XML is not valid",
						"Save is not allowed.", xml.getValidityErrors());
				return;
			}
			

			SaveAsDialog saveas = new SaveAsDialog(getContainer().getShell());

			//Setting default saveasdialog file name and location
			//If API name is changed IFileEditorInput is still the same
			//and if orginal file is not saved yet (xml.isAPINameChanged())
			//Setting default name to UI as new generated file name instead of old filename and path
			if (editorInput instanceof IFileEditorInput && !xml.isAPINameChanged()) {
				IFileEditorInput iFile = (IFileEditorInput) editorInput;				
				saveas.setOriginalFile(iFile.getFile());
			} else {
				IPathEditorInput javaInput = (IPathEditorInput) editorInput;
				IPath path = javaInput.getPath();
				
				if(xml.isAPINameChanged()){
					String fileName = MetadataFilenameGenerator.createMetadataFilename(xml
							.getAPIName());					
					saveas.setOriginalName(fileName);
				}
				else{
					saveas.setOriginalName(path.lastSegment());
				}
				
			}

			saveas.open();

			if (SaveAsDialog.CANCEL == saveas.getReturnCode()) {
				return;
			}

			if (saveas.getResult() == null
					|| !saveas.getResult().toString().endsWith(".metaxml")
					|| !saveas.getResult().getFileExtension().equals("metaxml")) {
				showErrorDialog("Wrong file type",
						"Save as was not complete. Please correct file type.",
						"File type must be .metaxml");
				return;
			}

			String containerName = saveas.getResult().removeLastSegments(1)
					.toOSString();
			String fileName = saveas.getResult().lastSegment();

			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(new Path(containerName));

			//This is also check by SaveAsDialog, double check
			if (resource == null || !resource.exists()
					|| !(resource instanceof IContainer)) {
				showErrorDialog(
						"Container must exist",
						"Save as was not complete, container must exist. Please select existing container.",
						"Conteiner does not exist");
				return;
			}
			IContainer container = (IContainer) resource;
			final IFile file = container.getFile(new Path(fileName));
			//Creating temp file because given API name must be found
			InputStream stream = openContentStream();
			ProgressMonitorPart monitor = new ProgressMonitorPart(
					getContainer(), new GridLayout());
			file.create(stream, true, monitor);
			stream.close();

		} catch (CoreException e) {
			showErrorDialog("Errors on save as", "Save as was not complete.",
					e.getMessage());
			MetadataEditorConsole.getInstance().println(
					"CoreException on save as: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			showErrorDialog("Errors on save as", "Save as was not complete.",
					e.getMessage());
			MetadataEditorConsole.getInstance().println(
					"IOException on save as: " + e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			showErrorDialog("Errors on save as", "Save as was not complete.",
					e.getMessage());
			MetadataEditorConsole.getInstance().println(
					"NoSuchAlgorithmException on save as: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			showErrorDialog("Errors on save as", "Save as was not complete.",
					e.getMessage());
			MetadataEditorConsole.getInstance().println(
					"Exception on save as: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Open a stream from XML file that this class stores.
	 * Client must close the opened stream.
	 * @return {@link InputStream}
	 */
	private InputStream openContentStream() {
		return new ByteArrayInputStream(xml.toString().getBytes());
	}

	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * Checks that the input is an instance of {@link IFileEditorInput}
	 * or {@link IPathEditorInput} when opened file is external file 
	 * outside of Carbide workspace.
	 * 
	 * Sets title, icon and XML data to widgets.
	 * 
	 * @throws PartInitException if input was not what's expected.
	 * @see org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */

	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput)
				&& !(editorInput instanceof IPathEditorInput)) {
			throw new PartInitException(
					"Invalid Input: Input type must be IFileEditorInput or IPathEditorInput, given input type was: "
							+ editorInput.getClass().getName());
		}
		setTitle(editorInput);
		setTitleImage(MetadataEditorActivator
				.getImageForKey(MetadataEditorActivator.METADATA_EDITOR_ICON));
		setMetadataXML(editorInput);

		super.init(site, editorInput);
	}

	/**
	 * When open a file, parsing and setting {@link #xml} from file to object.
	 * 
	 * If file was old existing file, parsing file and set it to {@link #xml},
	 * if file was new just created file, creating new xml object and setting
	 * it to {@link #xml}
	 * 
	 * @param input editor input given by framework.
	 */
	private void setMetadataXML(IEditorInput input) {

		String fileName = "";
		try {

			String xmlString;
			String fileLocation;
			//Type is IFileEditorInput allways when file is opened from project
			//If file opened outside project, editor type is IPathEditorInput			
			if (input instanceof IFileEditorInput) {
				IFileEditorInput iFile = (IFileEditorInput) input;
				IFile file = iFile.getFile();
				fileLocation = file.getLocation().toOSString();
				fileName = file.getName();
				InputStream in = file.getContents();
				xmlString = getFileContents(in);
				in.close();
				in = null;

			} else {
				IPath path = ((IPathEditorInput) input).getPath();
				fileLocation = path.toOSString();
				fileName = path.lastSegment();
				java.io.File fi = path.toFile();
				FileInputStream in = new FileInputStream(fi);
				xmlString = getFileContents(in);
				in.close();
				in = null;
			}

			//if file is just created new API metadatafile with wizard
			//creating new xml object and opening it
			if (xmlString.startsWith(MetadataXML.NEW_API_METADATA_FILE_UID)) {
				//When removin unique ID from new file, API name remains
				xmlString = xmlString.substring(
						MetadataXML.NEW_API_METADATA_FILE_UID.length()).trim();
				this.xml = new MetadataXML(xmlString, fileLocation);
				setDirty(true);
				MetadataEditorConsole.getInstance().println(
						"Metadata XML file: '" + xml.getFileName()
								+ "' created.");
			}
			//Otherwise file is old existing metadata file, parsing it and setting 
			//xml to this.xml
			else {
				this.xml = parseXML(fileLocation);
				setDirty(false);
				MetadataEditorConsole.getInstance().println(
						"Metadata XML file: '" + xml.getFileName()
								+ "' opened.");
			}

		} catch (MetadataNotValidException e) {
			//If parsing was ok, but xml was not walid setting xml so user can correct errors
			if (e.getMetadataXML() != null) {
				this.xml = e.getMetadataXML();
			}
			MetadataEditorConsole.getInstance().println(
					"MetadataNotValidException on init: " + e.toString());
			showErrorDialog("Errors on metadata XML", "Metadata XML file "
					+ fileName + " could not be parsed.", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			MetadataEditorConsole.getInstance().println(
					"Exception on init: " + e.toString());
			showErrorDialog("Error",
					"Metadata editor could not be opened. Errors on "
							+ fileName, e.getMessage());
		}

	}

	/**
	 * Get XML String from stream
	 * @param inputStream
	 * @return file contents
	 * @throws CoreException
	 * @throws IOException
	 */
	private String getFileContents(InputStream inputStream) throws CoreException,
			IOException {

		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buf = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			buf.append(line);
		}
		String xmlString = buf.toString();
		// Closing buffers
		br.close();
		isr.close();
		return xmlString;
	}

	/**
	 * Parse XML file to XML object
	 * @param fileName
	 * @return metadata XML this class represents.
	 * @throws MetadataNotValidException
	 */
	private MetadataXML parseXML(String fileName)
			throws MetadataNotValidException {
		MetadataXMLParser parser = new MetadataXMLParser();
		MetadataXML metadata = parser.parse(fileName);
		return metadata;
	}

	/**
	 * Setting editor title as file name
	 * @param editorInput
	 */
	private void setTitle(IEditorInput editorInput) {

		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput iFile = (IFileEditorInput) editorInput;
			IFile file = iFile.getFile();
			setPartName(file.getName());
		} else {
			IPath path = ((IPathEditorInput) editorInput).getPath();
			String filename = path.lastSegment();
			setPartName(filename);
		}
	}


	/**
	 * Save As is always allowed.
	 * @return <code>true</code>
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Closes all project files on project close.
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
							.getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) getEditorInput()).getFile()
								.getProject().equals(event.getResource())) {
							IEditorPart editorPart = pages[i]
									.findEditor(getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	/**
	 * If editor contents is modified, save is allowed
	 * @param isDirty
	 */
	private void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		if (isDirty) {
			firePropertyChange(PROP_DIRTY);
		} else {
			firePropertyChange(PROP_INPUT);
		}
	}

}
