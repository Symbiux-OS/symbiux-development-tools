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



package com.nokia.s60tools.ui.wizards;

import java.io.File;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.dialogs.IDialogSettings;

import com.nokia.s60tools.ui.internal.resources.Messages;

import java.util.*;

/**
 * Wizard page for querying user for: Symbol, map and image files.
 * 
 * This page can also be used to query selge.ini location, but this
 * functionality is hidden from the user at the moment. It might 
 * be restored in the future.
 *
 */
public abstract class DebugMetadataWizardPage extends S60ToolsWizardPage implements
		SelectionListener, ModifyListener, DropTargetListener {

	// defines for map file radio buttons
	final int NO_MAP_FILES_RADIO = 0;
	final int SDK_MAP_FILES_FOLDER_RADIO = 1;
	final int MAP_FILES_ZIP_RADIO = 2;
	final int MAP_FILES_FOLDER_RADIO = 3;
	
	// width hint for all buttons in the page
	final int BUTTON_WIDTH = 60;
	// height hint for all lists in the page
	final int LIST_HEIGHT = 30;
	
	// filters for file selection dialogs
	static final String SELGE_INI_FILTER = "selge.ini";
	static final String SYMBOL_FILTER = "*.symbol";
	static final String ZIP_FILTER = "*.zip";
	static final String[] IMAGE_FILTER = {"*.fpsx", "*.img", "*.v??", ".c??", "*.*"};
	static final String[] TRACE_FILTER = {"*.xml", "*.zip" };
	
	// wizard saves last entered values to these tags in dialog_settings.xml
	static final String LAST_USED_SELGE_INI_FILES = "SelgeIniFiles"; //$NON-NLS-1$
	static final String LAST_USED_SYMBOL_FILES = "SymbolFiles"; //$NON-NLS-1$
	static final String LAST_USED_DECODING_STYLE = "SymbolFileWasUsedLast"; //$NON-NLS-1$
	static final String LAST_USED_MAPFILES_FOLDER = "MapFilesFolder"; //$NON-NLS-1$
	static final String LAST_USED_MAPZIP_FILES = "MapZipFiles"; //$NON-NLS-1$
	static final String LAST_USED_IMAGE_FILES = "ImageFiles"; //$NON-NLS-1$
	static final String LAST_USED_TRACE_FILES = "TraceFiles"; //$NON-NLS-1$
	static final String LAST_USED_MAPFILE_TYPE = "LastUsedMapFileType"; //$NON-NLS-1$
	static final String LAST_USED_SDK = "LastUsedSdk"; //$NON-NLS-1$

	// top level containers
	protected Composite composite;
	protected Group groupSelge; // selge group is hidden at all times. Might be restored in the future

	// selge.ini page items. Page cannot be show at the moment. Might be restored in the future.
	protected Button buttonSelgeRadio;
	protected Button buttonSelgeBrowse;
	protected Button buttonShowSelgeDetails;
	protected Text textSelgeDetails;
	protected Combo comboSelge;
	protected Label labelSelge;

	// symbol page items
	protected Button buttonSymbolRadio = null;
	protected Button buttonMapFolderBrowse;
	protected Button buttonZipBrowse;
	protected Button buttonMapFilesZipRadio;
	protected Button buttonMapFilesFolderRadio;
	protected Button buttonSdkFolderRadio;
	protected Button buttonNoMapFilesRadio;
	protected Button buttonAddSymbol;
	protected Button buttonRemoveSymbol;
	protected Button buttonAddTraceFile;
	protected Button buttonRemoveTraceFile;
	protected Button buttonAddImageFile;
	protected Button buttonRemoveImageFile;
	protected Combo comboMapFilesFolder;
	protected Combo comboMapFilesZip;
	protected Combo comboSdkFolder;
	protected Group groupSymbolFiles;
	protected Group groupMapFiles;
	protected Group groupImageFiles;
	protected Group groupTraceFiles;
	protected Group groupSelgeSymbolRadios;
	protected Label labelMapFilesFolder;
	protected Label labelZip;
	protected Label labelSdkFolder;
	protected Label labelNoMapFiles;
	protected List listSymbols;
	protected List listImageFiles;
	protected List listTraceFiles;
	
	// owner can pass in ROM IDs so that we can check that correct symbol is selected
	protected String[] romIds = null;
	
	// holds SDK names and their folders
	protected HashMap<String, String> sdkFolders = null;
	
	// default location of selge.ini. 
	protected String defaultSelgeIniFile = "\\\\Vagrp014\\groups4\\Builds\\S60RnD\\S60_5_0\\SelgeIniFile\\selge.ini"; //$NON-NLS-1$
	
	// defines whether image files selection should be visible in the page
	boolean showImageAndTraceFilesGroup = false;
	
	// section where this wizard pages data is saved
	IDialogSettings previousValuesSection;
	
	// defines how many previous user entered values are saved
	final int MAX_SAVED_VALUES;
	
	/**
	 * Constructor.
	 * 
	 * @param pageName name of the page
	 * @param title page title
	 * @param imageFilesGroup if true, image files are queried from user. If false, image file are not queried
	 * @param section section where wizard reads/saves previously entered values
	 * @param maxSavedValues how many previously entered values are saved
	 */
	public DebugMetadataWizardPage(String pageName, 
									String title, 
									boolean imageAndTraceFilesGroup,
									IDialogSettings section,
									int maxSavedValues){
		super(pageName);
		MAX_SAVED_VALUES = maxSavedValues;
		showImageAndTraceFilesGroup = imageAndTraceFilesGroup;
		previousValuesSection = section;
			
		setTitle(title);
			
		setDescription(Messages.getString("DebugMetadataWizardPage.SelgeIniDescription")); //$NON-NLS-1$
		setPageComplete(true);
	 }
	
	public abstract String getHelpContext();
	public abstract boolean canFinish();
	public abstract HashMap<String, String> getSdkMapFolders(); 
	public abstract boolean folderContainsMapFiles(String folder);
	public abstract boolean zipContainsMapFiles(String path);
	
	

	@Override
	public void recalculateButtonStates() {
	}

	@Override
	
	public void setInitialFocus() {
		if (buttonSelgeRadio.getSelection())
			comboSelge.setFocus();
		else
			listSymbols.setFocus();
	}

	/**
	 * @see org.eclipse.jface.wizard.WizardPage
	 */
	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		composite.setLayout(gl);
		
		groupSelgeSymbolRadios = new Group(composite, SWT.NONE);
		groupSelgeSymbolRadios.setLayout(gl);
		groupSelgeSymbolRadios.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Selge.ini radio button
		buttonSelgeRadio = new Button(groupSelgeSymbolRadios, SWT.RADIO);
		buttonSelgeRadio.setText(Messages.getString("DebugMetadataWizardPage.UseSelgeIni")); //$NON-NLS-1$
		buttonSelgeRadio.setSelection(!symbolFileWasUsedPreviously());
		buttonSelgeRadio.addSelectionListener(this);
		
		// symbol & map files radio button
		buttonSymbolRadio = new Button(groupSelgeSymbolRadios, SWT.RADIO);
		buttonSymbolRadio.setText(Messages.getString("DebugMetadataWizardPage.DefinePathsManually")); //$NON-NLS-1$
		buttonSymbolRadio.setSelection(symbolFileWasUsedPreviously());
		
		createSelgeGroup();
		
		createSymbolGroup();
		
		setHelps();
		
		setInitialFocus();

		// selge.ini is not used at the moment, but it might be restored later
		hideSelgeIniSelection();
		
		Transfer[] types = new Transfer[] { FileTransfer.getInstance() };
	    DropTarget dropTarget = new DropTarget(composite, DND.DROP_COPY);
	    dropTarget.setTransfer(types);
	    dropTarget.addDropListener(this);		
		
		setControl(composite);
	}
	

	/**
	 * Creates UI controls for selge.ini usage
	 *
	 */
	void createSelgeGroup() {
		// Selge.ini group
		groupSelge = new Group(composite, SWT.NONE);
		GridLayout gl = new GridLayout();
		GridData selgeGd = new GridData(GridData.FILL_BOTH);
		gl.numColumns = 2;
		groupSelge.setLayout(gl);
		
		// show selge group if user used selge.ini last time, hide if symbol & map files was used
		groupSelge.setVisible(!symbolFileWasUsedPreviously()); 
		selgeGd.exclude = symbolFileWasUsedPreviously();
		groupSelge.setLayoutData(selgeGd);
		
		// Selge.ini label
		labelSelge = new Label(groupSelge, SWT.LEFT);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		labelSelge.setLayoutData(gd);
		labelSelge.setText(Messages.getString("DebugMetadataWizardPage.SelgeIniFile")); //$NON-NLS-1$
		
		// Selge.ini combo
		comboSelge = new Combo(groupSelge, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		comboSelge.setLayoutData(gd);
		comboSelge.addModifyListener(this);
		
		String[] lastUsed = getPreviousPaths(LAST_USED_SELGE_INI_FILES);
		// set the previous selge.ini file as default
		if (lastUsed != null) {
			comboSelge.setItems(lastUsed);
			comboSelge.select(0);
		// user has not defined selge.ini previously, show default location
		} else {
			comboSelge.setText(defaultSelgeIniFile);
		}
		
		// Selge.ini browse button
		buttonSelgeBrowse = new Button(groupSelge, SWT.PUSH);
		buttonSelgeBrowse.setText(Messages.getString("DebugMetadataWizardPage.Browse")); //$NON-NLS-1$
		buttonSelgeBrowse.addSelectionListener(this);
		
		// Show selge.ini details button is not yet used i.e. shown to user
		GridData gdDetailsButton = new GridData();
		gdDetailsButton.horizontalSpan = 2;
		buttonShowSelgeDetails = new Button(groupSelge, SWT.PUSH);
		buttonShowSelgeDetails.setText(Messages.getString("DebugMetadataWizardPage.ShowDetails")); //$NON-NLS-1$
		buttonShowSelgeDetails.setLayoutData(gdDetailsButton);
		buttonShowSelgeDetails.addSelectionListener(this);
		buttonShowSelgeDetails.setVisible(false);
		
		// textbox for selge.ini details. Not yet used.
		textSelgeDetails = new Text(groupSelge, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
		textSelgeDetails.setLayoutData(new GridData(GridData.FILL_BOTH));
	}
	
	/**
	 * Creates UI controls for symbol & map & image files usage.
	 *
	 */
	void createSymbolGroup() {
		// Symbol group
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		GridData gd = new GridData(GridData.FILL_BOTH);
		
		boolean visible = symbolFileWasUsedPreviously();
		// show symbol group if user used symbol & map files last time, hide if selge.ini was used
		gd.exclude = !visible;
		if (visible)
			setDescription(Messages.getString("DebugMetadataWizardPage.SymbolDescription")); //$NON-NLS-1$
		
		// Symbol files group
		groupSymbolFiles = new Group(composite, SWT.LEFT);
		groupSymbolFiles.setLayout(gl);
		groupSymbolFiles.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridData gdTwoSpan = new GridData(GridData.FILL_HORIZONTAL);
		gdTwoSpan.horizontalSpan = 2;
		groupSymbolFiles.setText(Messages.getString("DebugMetadataWizardPage.SymbolFiles")); //$NON-NLS-1$
		
		// Symbol file list
		listSymbols = new List(groupSymbolFiles, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridData gdHorizontalTop = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		gdHorizontalTop.verticalSpan = 2;
		gdHorizontalTop.heightHint = LIST_HEIGHT;
		listSymbols.setLayoutData(gdHorizontalTop);

		// read last used symbol files to combo
		String[] lastUsed = getPreviousPaths(LAST_USED_SYMBOL_FILES);
		if (lastUsed != null) {
			listSymbols.setItems(lastUsed);
		}				

		GridData gdButtons = new GridData();
		gdButtons.widthHint =  BUTTON_WIDTH;
		
		// Add Symbol file button
		buttonAddSymbol = new Button(groupSymbolFiles, SWT.PUSH);
		buttonAddSymbol.setText(Messages.getString("DebugMetadataWizardPage.Add")); //$NON-NLS-1$
		buttonAddSymbol.setLayoutData(gdButtons);
		buttonAddSymbol.addSelectionListener(this);

		// Remove Symbol file button
		buttonRemoveSymbol = new Button(groupSymbolFiles, SWT.PUSH);
		buttonRemoveSymbol.setText(Messages.getString("DebugMetadataWizardPage.Remove")); //$NON-NLS-1$
		buttonRemoveSymbol.setLayoutData(gdButtons);
		buttonRemoveSymbol.addSelectionListener(this);
		
		createMapFilesGroup(composite);
		
		// if image files UI is wanted, create it
		if (showImageAndTraceFilesGroup) {
			createTraceFilesGroup(composite);
			createImageFilesGroup(composite);
		}
	}
	
	/**
	 * Creates UI controls for map files usage.
	 *
	 */
	void createMapFilesGroup(Composite parent) {
		// Map Files group
		groupMapFiles = new Group(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.verticalIndent = 10;
		GridLayout gl = new GridLayout();
		gl.numColumns = 3;
		groupMapFiles.setLayout(gl);
		groupMapFiles.setText(Messages.getString("DebugMetadataWizardPage.MapFiles")); //$NON-NLS-1$
		groupMapFiles.setLayoutData(gd);		
		
		// empty label to make layout correct
		new Label(groupMapFiles, SWT.LEFT);
		
		// Map files folder label
		labelMapFilesFolder = new Label(groupMapFiles, SWT.LEFT);
		GridData gdTwoSpanWithIndent = new GridData(GridData.FILL_HORIZONTAL);
		gdTwoSpanWithIndent.horizontalSpan = 2;
		gdTwoSpanWithIndent.verticalIndent = 10;
		labelMapFilesFolder.setLayoutData(gdTwoSpanWithIndent);
		labelMapFilesFolder.setText(Messages.getString("DebugMetadataWizardPage.MapFilesFolder")); //$NON-NLS-1$
		
		// Map files folder radio button
		buttonMapFilesFolderRadio = new Button(groupMapFiles, SWT.RADIO);
		buttonMapFilesFolderRadio.addSelectionListener(this);
		
		// Map files folder combo
		comboMapFilesFolder = new Combo(groupMapFiles, SWT.BORDER);
		GridData gdHorizontal = new GridData(GridData.FILL_HORIZONTAL);
		comboMapFilesFolder.setLayoutData(gdHorizontal);
		comboMapFilesFolder.addModifyListener(this);
		
		// read last used map file folders to combo
		String[] lastUsed = getPreviousPaths(LAST_USED_MAPFILES_FOLDER);
		if (lastUsed != null) {
			comboMapFilesFolder.setItems(lastUsed);
			comboMapFilesFolder.select(0);
		}						
		
		// Map files folder browse button
		buttonMapFolderBrowse = new Button(groupMapFiles, SWT.PUSH);
		buttonMapFolderBrowse.setText(Messages.getString("DebugMetadataWizardPage.Browse")); //$NON-NLS-1$
		buttonMapFolderBrowse.addSelectionListener(this);
		
		// empty label to make layout correct
		new Label(groupMapFiles, SWT.LEFT);

		// MapFiles.zip label
		labelZip = new Label(groupMapFiles, SWT.LEFT);
		GridData gdTwoSpan = new GridData(GridData.FILL_HORIZONTAL);
		gdTwoSpan.horizontalSpan = 2;
		labelZip.setLayoutData(gdTwoSpan);
		labelZip.setText(Messages.getString("DebugMetadataWizardPage.MapFilesZip")); //$NON-NLS-1$
		
		// MapFiles.zip radio button
		buttonMapFilesZipRadio = new Button(groupMapFiles, SWT.RADIO);
		buttonMapFilesZipRadio.addSelectionListener(this);
		
		// MapFiles.zip combo
		comboMapFilesZip = new Combo(groupMapFiles, SWT.BORDER);
		comboMapFilesZip.setLayoutData(gdHorizontal);
		comboMapFilesZip.addModifyListener(this);
		
		// read last used mapfiles.zip file paths to combo
		lastUsed = getPreviousPaths(LAST_USED_MAPZIP_FILES);
		if (lastUsed != null) {
			comboMapFilesZip.setItems(lastUsed);
			comboMapFilesZip.select(0);
		}						
		
		// MapFiles.zip browse button
		buttonZipBrowse = new Button(groupMapFiles, SWT.PUSH);
		buttonZipBrowse.setText(Messages.getString("DebugMetadataWizardPage.Browse")); //$NON-NLS-1$
		buttonZipBrowse.addSelectionListener(this);
		
		// empty label to make layout correct
		new Label(groupMapFiles, SWT.LEFT);
		
		// SDK folder label
		labelSdkFolder = new Label(groupMapFiles, SWT.LEFT);
		labelSdkFolder.setLayoutData(gdTwoSpan);
		labelSdkFolder.setText(Messages.getString("DebugMetadataWizardPage.SdkMapFilesFolder")); //$NON-NLS-1$
		
		// SDK folder radio button
		buttonSdkFolderRadio = new Button(groupMapFiles, SWT.RADIO);
		buttonSdkFolderRadio.addSelectionListener(this);

		// SDK folder combo
		comboSdkFolder = new Combo(groupMapFiles, SWT.BORDER | SWT.READ_ONLY);
		comboSdkFolder.setLayoutData(gdHorizontal);
		comboSdkFolder.addModifyListener(this);
		comboSdkFolder.setVisibleItemCount(50);
		
		sdkFolders = getSdkMapFolders();
		
		// if no SDK folders were found disable SDK combo
		if (sdkFolders == null || sdkFolders.size() < 1) {
			disableSdkSelection();
		// SDK folders were found, fill SDK combo with those values
		} else {
			Set<String> keySet = sdkFolders.keySet();
			String[] keys = keySet.toArray(new String[keySet.size()]);
			Arrays.sort(keys);
			comboSdkFolder.setItems(keys);
		}
		
		// read which SDK folder was used last, and select it in SDK combo if possible
		lastUsed = getPreviousPaths(LAST_USED_SDK);
		if (lastUsed != null && sdkFolders != null && buttonSdkFolderRadio.getEnabled()) {
			String[] items = comboSdkFolder.getItems();
			for (int i = 0; i < items.length; i++) {
				if (items[i].equalsIgnoreCase(lastUsed[0])) {
					comboSdkFolder.select(i);
					break;
				}
			}
		}										
		
		// empty label to make layout correct
		new Label(groupMapFiles, SWT.LEFT);
		
		// No map files radio button
		buttonNoMapFilesRadio = new Button(groupMapFiles, SWT.RADIO);
		buttonNoMapFilesRadio.addSelectionListener(this);
		
		// No map files label
		labelNoMapFiles = new Label(groupMapFiles, SWT.LEFT);
		labelNoMapFiles.setText(Messages.getString("DebugMetadataWizardPage.NoMapFiles")); //$NON-NLS-1$
		labelNoMapFiles.setLayoutData(gdTwoSpan);
		
		// read how map files were used last time (i.e. which radio button
		// was selected) and select the same radio button this time.
		int lastUsedMapRadio = lastUsedMapFileType();
		switch (lastUsedMapRadio) {
		case MAP_FILES_FOLDER_RADIO:
			buttonMapFilesFolderRadio.setSelection(true);
			break;
		case MAP_FILES_ZIP_RADIO:
			buttonMapFilesZipRadio.setSelection(true);
			break;
		case SDK_MAP_FILES_FOLDER_RADIO:
			buttonSdkFolderRadio.setSelection(true);
			break;
		default:
			buttonNoMapFilesRadio.setSelection(true);
			break;
		}
		
		enableMapSelections();
	}
	
	/**
	 * Creates UI controls for OST trace dictionary files usage.
	 *
	 */
	void createTraceFilesGroup(Composite parent) {
		// Image files group
		groupTraceFiles = new Group(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.verticalIndent = 10;
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		groupTraceFiles.setLayout(gl);
		groupTraceFiles.setText(Messages.getString("DebugMetadataWizardPage.TraceFiles")); //$NON-NLS-1$
		groupTraceFiles.setLayoutData(gd);
		
		// OST trace dictionary file list
		listTraceFiles = new List(groupTraceFiles, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridData gdHorizontalTop = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		gdHorizontalTop.verticalSpan = 2;
		gdHorizontalTop.heightHint = LIST_HEIGHT;
		listTraceFiles.setLayoutData(gdHorizontalTop);

		// read last used trace dictionary files and fill them into the list
		String[] lastUsed = getPreviousPaths(LAST_USED_TRACE_FILES);
		if (lastUsed != null) {
			listTraceFiles.setItems(lastUsed);
		}				

		GridData gdButtons = new GridData();
		gdButtons.widthHint =  BUTTON_WIDTH;
		
		// Add trace dictionary file button
		buttonAddTraceFile = new Button(groupTraceFiles, SWT.PUSH);
		buttonAddTraceFile.setText(Messages.getString("DebugMetadataWizardPage.Add")); //$NON-NLS-1$
		buttonAddTraceFile.setLayoutData(gdButtons);
		buttonAddTraceFile.addSelectionListener(this);

		// Remove trace dictionary file button
		buttonRemoveTraceFile = new Button(groupTraceFiles, SWT.PUSH);
		buttonRemoveTraceFile.setText(Messages.getString("DebugMetadataWizardPage.Remove")); //$NON-NLS-1$
		buttonRemoveTraceFile.setLayoutData(gdButtons);
		buttonRemoveTraceFile.addSelectionListener(this);
	}
	
	/**
	 * Creates UI controls for image files usage.
	 *
	 */
	void createImageFilesGroup(Composite parent) {
		// Image files group
		groupImageFiles = new Group(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.verticalIndent = 10;
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		groupImageFiles.setLayout(gl);
		groupImageFiles.setText(Messages.getString("DebugMetadataWizardPage.ImageFiles")); //$NON-NLS-1$
		groupImageFiles.setLayoutData(gd);		
		
		// Image file list
		listImageFiles = new List(groupImageFiles, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridData gdHorizontalTop = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		gdHorizontalTop.verticalSpan = 2;
		gdHorizontalTop.heightHint = LIST_HEIGHT;
		listImageFiles.setLayoutData(gdHorizontalTop);

		// read last used image files and fill them into the list
		String[] lastUsed = getPreviousPaths(LAST_USED_IMAGE_FILES);
		if (lastUsed != null) {
			listImageFiles.setItems(lastUsed);
		}				

		GridData gdButtons = new GridData();
		gdButtons.widthHint =  BUTTON_WIDTH;
		
		// Add image file button
		buttonAddImageFile = new Button(groupImageFiles, SWT.PUSH);
		buttonAddImageFile.setText(Messages.getString("DebugMetadataWizardPage.Add")); //$NON-NLS-1$
		buttonAddImageFile.setLayoutData(gdButtons);
		buttonAddImageFile.addSelectionListener(this);

		// Remove image file button
		buttonRemoveImageFile = new Button(groupImageFiles, SWT.PUSH);
		buttonRemoveImageFile.setText(Messages.getString("DebugMetadataWizardPage.Remove")); //$NON-NLS-1$
		buttonRemoveImageFile.setLayoutData(gdButtons);
		buttonRemoveImageFile.addSelectionListener(this);
	}
	
	/**
	 * This'll disable selge.ini selection from user
	 */
	void hideSelgeIniSelection() {
		// hide radios group
		GridData  gd = (GridData)groupSelgeSymbolRadios.getLayoutData();
		gd.exclude = true;
		groupSelgeSymbolRadios.setVisible(false);
		buttonSymbolRadio.setSelection(true);
		
		// show symbol group
// ROWS COMMENTED SO THAT THEY CAN BE RESTORED INCASE SELGE.INI USAGE IS RESTORED		
//		gd = (GridData)groupSymbol.getLayoutData();
//		groupSymbol.setVisible(true);
//		gd.exclude = false;
//		
		// hide selge group
		gd = (GridData)groupSelge.getLayoutData();
		gd.exclude = true;
		groupSelge.setVisible(false);
		buttonSelgeRadio.setSelection(false);
		
		setDescription(Messages.getString("DebugMetadataWizardPage.SymbolDescription")); //$NON-NLS-1$
		
		composite.layout();	
	}
	
	/**
	 * Return the default selge.ini file 
	 */
	public String getDefaultSelgeIniFile() {
		return defaultSelgeIniFile;
	}
	
	/**
	 * Returns the selected selge.ini file. Returns empty if symbol group
	 * is visible.
	 */
	public String getSelgeIniFile() {
		if (buttonSymbolRadio.getSelection()) {
			return ""; //$NON-NLS-1$
		} else {
			return comboSelge.getText();
		}
	}
	
	/**
	 * Returns symbols files selected by user. If selge.ini group is visible,
	 * null is returned. 
	 */
	public String[] getSymbolFiles() {
		if (buttonSelgeRadio.getSelection()) {
			return null;
		}
		
		return listSymbols.getItems();
	}
	
	/**
	 * Returns the selected map files folder. Return value depends on which
	 * map files radio button is selected. Returns empty if MapFiles.zip radio
	 * button is selected.  
	 */
	public String getMapFilesFolder() {
		// if selge.ini group is visible, return empty
		if (buttonSelgeRadio.getSelection()) {
			return ""; //$NON-NLS-1$
		// MapFiles.zip radio is selected, return empty
		}else if (buttonMapFilesZipRadio.getSelection()) {
			return ""; //$NON-NLS-1$
		// Map Files folder radio button is selected
		} else if (buttonMapFilesFolderRadio.getSelection()) {
			return comboMapFilesFolder.getText();
		// "SDK Map files folder" radio button is selected, return correct sdk folder
		} else if (buttonSdkFolderRadio.getSelection() && 
				   buttonSdkFolderRadio.getEnabled()   &&
				   sdkFolders != null && sdkFolders.size() > 0 &&
				   comboSdkFolder.getSelectionIndex() >= 0) {
			return sdkFolders.get(comboSdkFolder.getText());
		// otherwise return empty
		} else {
			return ""; //$NON-NLS-1$
		}
	}
	
	/**
	 * Returns the MapFiles.zip location user has provided. If MapFiles.zip radio button
	 * is not selected, this will return empty. 
	 */
	public String getMapFilesZip() {
		// if selge.ini group is visible, return empty
		if (buttonSelgeRadio.getSelection()) {
			return ""; //$NON-NLS-1$
		// return empty if MapFiles.zip radio button is not selected
		} else if (!buttonMapFilesZipRadio.getSelection()) {
			return ""; //$NON-NLS-1$
		}
		
		return comboMapFilesZip.getText();
	}
	
	/**
	 * Returns image files selected by user. If selge.ini group is visible,
	 * null is returned. 
	 */
	public String[] getImageFiles() {
		if (buttonSelgeRadio.getSelection()) {
			return null;
		}
		
		return listImageFiles.getItems();
	}
	
	/**
	 * Returns trace dictionary files selected by user. 
	 */
	public String[] getTraceFiles() {
		assert listTraceFiles != null;

		return listTraceFiles.getItems();
	}

	/**
	 * Returns the provided ROM IDs 
	 */
	public String[] getRomIds() {
		return romIds;
	}
	

	/**
	 * Disables "SDK Map files folder" radio button & combo & label
	 */
	void disableSdkSelection() {
		if (buttonSdkFolderRadio.getSelection())
			buttonNoMapFilesRadio.setSelection(true);
		buttonSdkFolderRadio.setEnabled(false);
		comboSdkFolder.setEnabled(false);
	}
	
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	public void widgetSelected(SelectionEvent event) {
		// change between selge.ini "view" and symbol&map files "view"
		if (event.widget == buttonSelgeRadio || event.widget == buttonSymbolRadio) {
			this.setErrorMessage(null);
// ROWS COMMENTED SO THAT THEY CAN BE RESTORED INCASE SELGE.INI USAGE IS RESTORED			
/*			boolean selgeButtonSelected = buttonSelgeRadio.getSelection();
			GridData gd = (GridData)groupSymbol.getLayoutData();
			boolean visible = !(gd.exclude = selgeButtonSelected);
			groupSymbol.setVisible(visible);
			
			gd = (GridData)groupSelge.getLayoutData();
			visible = !(gd.exclude = !selgeButtonSelected);
			groupSelge.setVisible(visible);
			if (visible) {
				setDescription(Messages.getString("DebugMetadataWizardPage.SelgeIniDescription")); //$NON-NLS-1$
			} else {
*/				setDescription(Messages.getString("DebugMetadataWizardPage.SymbolDescription")); //$NON-NLS-1$
//			}
			
			try {
				getWizard().getContainer().updateButtons();
			} catch (Exception E) {}
			
			composite.layout();	
			
		// selge.ini browse button pressed
		} else if (event.widget == buttonSelgeBrowse) {
			FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN);
			dialog.setText(Messages.getString("DebugMetadataWizardPage.SelectSelgeIniFile")); //$NON-NLS-1$
			String[] filterExt = {SELGE_INI_FILTER};
	        dialog.setFilterExtensions(filterExt);
	        dialog.setFilterPath(comboSelge.getText());
			String result = dialog.open();
			comboSelge.setText(result);
			
		// show selge.ini details button pressed
		} else if (event.widget == buttonShowSelgeDetails) {
						
		// Add symbol file button pressed
		} else if (event.widget == buttonAddSymbol) {
			FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN | SWT.MULTI);
			dialog.setText(Messages.getString("DebugMetadataWizardPage.SelectSymbolFile")); //$NON-NLS-1$
			String[] filterExt = {SYMBOL_FILTER};
	        dialog.setFilterExtensions(filterExt);
			String result = dialog.open();
			// if user selected files, check that files don't already exist in the list
			// and add them if they don't already exist.
			if (result != null) {
				String[] fileNames = dialog.getFileNames();
				if (fileNames != null) {
					String path = dialog.getFilterPath() + File.separator;
					for (int j = 0; j < fileNames.length; j++) {
						String filePath = path + fileNames[j];
						String[] listItems = listSymbols.getItems();
						if (listItems != null && listItems.length > 0) {
							boolean found = false;
							for (int i = 0; i < listItems.length; i++) {
								String listItem = listItems[i];
								if (listItem.compareToIgnoreCase(filePath) == 0) {
									found = true;
									break;
								}
							}
							if (!found)
								listSymbols.add(filePath);
						} else {
							listSymbols.add(filePath);
						}
					}
				}
			}

		// Remove symbol file button pressed
		} else if (event.widget == buttonRemoveSymbol) {
			int[] selectedItems = listSymbols.getSelectionIndices();
			if (selectedItems != null && selectedItems.length > 0) {
				listSymbols.remove(selectedItems);
			}

		// Add trace file button pressed
		} else if (event.widget == buttonAddTraceFile) {
			FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN | SWT.MULTI);
			dialog.setText(Messages.getString("DebugMetadataWizardPage.SelectTraceFile")); //$NON-NLS-1$
			String[] filterExt = TRACE_FILTER; 
	        dialog.setFilterExtensions(filterExt);
			String result = dialog.open();
			// if user selected files, check that files don't already exist in the list
			// and add them if they don't already exist.
			if (result != null) {
				String[] fileNames = dialog.getFileNames();
				if (fileNames != null) {
					String path = dialog.getFilterPath() + File.separator;
					for (int j = 0; j < fileNames.length; j++) {
						String filePath = path + fileNames[j];
						String[] listItems = listTraceFiles.getItems();
						if (listItems != null && listItems.length > 0) {
							boolean found = false;
							for (int i = 0; i < listItems.length; i++) {
								String listItem = listItems[i];
								if (listItem.compareToIgnoreCase(filePath) == 0) {
									found = true;
									break;
								}
							}
							if (!found)
								listTraceFiles.add(filePath);
						} else {
							listTraceFiles.add(filePath);
						}
					}
				}
			}
		
		// Remove OST trace file button pressed
		} else if (event.widget == buttonRemoveTraceFile) {
			int[] selectedItems = listTraceFiles.getSelectionIndices();
			if (selectedItems != null && selectedItems.length > 0) {
				listTraceFiles.remove(selectedItems);
			}			

			// Add image file button pressed
		} else if (event.widget == buttonAddImageFile) {
			FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN | SWT.MULTI);
			dialog.setText(Messages.getString("DebugMetadataWizardPage.SelectImageFile")); //$NON-NLS-1$
			String[] filterExt = IMAGE_FILTER; 
	        dialog.setFilterExtensions(filterExt);
			String result = dialog.open();
			// if user selected files, check that files don't already exist in the list
			// and add them if they don't already exist.
			if (result != null) {
				String[] fileNames = dialog.getFileNames();
				if (fileNames != null) {
					String path = dialog.getFilterPath() + File.separator;
					for (int j = 0; j < fileNames.length; j++) {
						String filePath = path + fileNames[j];
						String[] listItems = listImageFiles.getItems();
						if (listItems != null && listItems.length > 0) {
							boolean found = false;
							for (int i = 0; i < listItems.length; i++) {
								String listItem = listItems[i];
								if (listItem.compareToIgnoreCase(filePath) == 0) {
									found = true;
									break;
								}
							}
							if (!found)
								listImageFiles.add(filePath);
						} else {
							listImageFiles.add(filePath);
						}
					}
				}
			}
		
		// Remove image file button pressed
		} else if (event.widget == buttonRemoveImageFile) {
			int[] selectedItems = listImageFiles.getSelectionIndices();
			if (selectedItems != null && selectedItems.length > 0) {
				listImageFiles.remove(selectedItems);
			}			

		// Map files folder browse button pressed
		} else if (event.widget == buttonMapFolderBrowse) {
			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setText(Messages.getString("DebugMetadataWizardPage.MapFilesFolder")); //$NON-NLS-1$
			String result = dialog.open();
			comboMapFilesFolder.setText(result);
			
		// MapFiles.zip browse button pressed
		} else if (event.widget == buttonZipBrowse) {
			FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN);
			dialog.setText(Messages.getString("DebugMetadataWizardPage.SelectMapFilesZip")); //$NON-NLS-1$
			String[] filterExt = {ZIP_FILTER};
	        dialog.setFilterExtensions(filterExt);
			String result = dialog.open();
			comboMapFilesZip.setText(result);
			
		// map files radio selection changed
		} else if (event.widget == buttonMapFilesFolderRadio || event.widget == buttonMapFilesZipRadio ||
				   event.widget == buttonSdkFolderRadio || event.widget == buttonNoMapFilesRadio || event.widget == comboSdkFolder) {
			enableMapSelections();
		}

		updateButtons();
	}
	
	/**
	 * Disables/Enables map file labels & combos & buttons accordingly to which
	 * Map Files radio button is selected.
	 */
	void enableMapSelections() {
		comboMapFilesFolder.setEnabled(false);
		buttonMapFolderBrowse.setEnabled(false);
		comboMapFilesZip.setEnabled(false);
		buttonZipBrowse.setEnabled(false);
		comboSdkFolder.setEnabled(false);

		// Map Files folder radio button is selected
		if (buttonMapFilesFolderRadio.getSelection()) {
			comboMapFilesFolder.setEnabled(true);
			buttonMapFolderBrowse.setEnabled(true);
		// MapFiles.zip radio button is selected
		} else if (buttonMapFilesZipRadio.getSelection()) {
			comboMapFilesZip.setEnabled(true);
			buttonZipBrowse.setEnabled(true);
		// SDK map files radio button is selected
		} else if (buttonSdkFolderRadio.getSelection()) {
			comboSdkFolder.setEnabled(true);
		}
	}
	
	public void modifyText(ModifyEvent event) {
		this.setErrorMessage(null);
		updateButtons();
	}
	
	/**
	 * Checks that selected selge.ini file is valid.
	 *
	 */
	private boolean checkSelgeIniFile() {
		this.setErrorMessage(null);
		File selge = new File(comboSelge.getText());
		if (selge.isFile() && selge.exists() && selge.getName().trim().toLowerCase().equals(SELGE_INI_FILTER)) {
			return true;
		} else {
			if (comboSelge.getText().length() > 0)
				this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.InvalidSelgeIni")); //$NON-NLS-1$
			return false;
		}
	}

	/**
	 * Checks that atleast one symbol file is in the list 
	 */
	boolean checkSymbol() {
		if (listSymbols.getItemCount() < 1)
			return false;
		
		return true;
	}
	
	/**
	 *  Checks that Map files folder or MapFiles.zip file is valid.
	 */
	boolean checkMapFiles(boolean cannotBeEmpty) {
		// Map Files folder radio button is selected
		if (buttonMapFilesFolderRadio.getSelection()) {
			try {
				File f = new File(comboMapFilesFolder.getText());
				// given folder must exist and it must contain map files
				if (f.isDirectory() && f.exists()) {
					if (folderContainsMapFiles(comboMapFilesFolder.getText())) {
						return true;
					}
					this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.SelectedMapFilesFolderContainsNoMapFiles")); //$NON-NLS-1$
					return false;
				}
				this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.InvalidMapFilesFolder")); //$NON-NLS-1$
				return false;
			} catch (Exception e) {
				this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.InvalidMapFilesFolder")); //$NON-NLS-1$
				return false;
			}
		// MapFiles.zip radio button is selected
		} else if (buttonMapFilesZipRadio.getSelection()) {
			try {
				File f = new File(comboMapFilesZip.getText());
				// given zip file must exist and is must contain map files
				if (f.isFile() && f.exists() && f.getName().toLowerCase().endsWith("zip")) {
					if (zipContainsMapFiles(comboMapFilesZip.getText())) {
						return true;
					}
					this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.SelectedZipContainsNoMapFiles")); //$NON-NLS-1$
					return false;
				}
				this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.InvalidMapFilesZip")); //$NON-NLS-1$
				return false;
			} catch (Exception e) {
				this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.InvalidMapFilesZip")); //$NON-NLS-1$
				return false;
			}
		// SDK Map files folder" radio button is selected
		} else if (buttonSdkFolderRadio.getSelection()) {
			// an item must be selected in the combo
			if (comboSdkFolder.getSelectionIndex() >= 0) {
				return true;
			}
			this.setErrorMessage(Messages.getString("DebugMetadataWizardPage.SdkMapFilesFolderNotSelected")); //$NON-NLS-1$
			return false;
		} else if (buttonNoMapFilesRadio.getSelection() && cannotBeEmpty) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * ROM IDs can be passed with this method. If the ROM ID list contains
	 * multiple ROM IDs, only selge.ini group will be available for user. 
	 */
	public void setRomIds(String[] ids) {
		if (buttonSymbolRadio != null) {
			// if symbol&map group radio button is selected, and there are
			// multiple ROM IDs, show selge.ini group
			if (buttonSymbolRadio.getSelection() && ids.length > 1) {
				buttonSymbolRadio.setSelection(false);
// ROWS COMMENTED SO THAT THEY CAN BE RESTORED INCASE SELGE.INI USAGE IS RESTORED
/*				groupSymbol.setVisible(false);
				GridData gd = (GridData)groupSymbol.getLayoutData();
				gd.exclude = true;
				gd = (GridData)groupSelge.getLayoutData();
				gd.exclude = false;
*/				groupSelge.setVisible(true);
				buttonSelgeRadio.setSelection(true);
				setDescription(Messages.getString("DebugMetadataWizardPage.SelgeIniDescription")); //$NON-NLS-1$
				updateButtons();
				this.setMessage(Messages.getString("DebugMetadataWizardPage.RomIdsDoNotMatch"), 2); //$NON-NLS-1$
				composite.layout();	
			}
			this.buttonSymbolRadio.setEnabled(!(ids.length > 1));
		}
	}
	
	public boolean canFlipToNextPage() {
		return canProceed();
	}
	
	/**
	 * We can proceed to next page if selge.ini is a valid file (if selge.ini is used).
	 * If selge.ini is not used, we'll check that symbol file and mapfiles.zip is valid or empty
	 * @return true if we can proceed to next page, false if not
	 */
	protected boolean canProceed() {
		// selge.ini is used, check that selge.ini is valid
		if (buttonSelgeRadio.getSelection()) {
			return checkSelgeIniFile();
		// selge.ini is not used
		} else {
			this.setErrorMessage(null);

			// no symbols provided
			if (listSymbols.getItemCount() < 1) {
				if (checkMapFiles(true))
					return true;
				
			// symbols are provided
			} else {
				if (checkSymbols() && checkMapFiles(false))
					return true;
			}
			
			return false;
		}
	}	
	
	boolean checkSymbols() {
		for (int i = 0; i < listSymbols.getItemCount(); i++) {
			File f = new File(listSymbols.getItem(i));
			if (!f.exists() || !f.isFile()) {
				this.setErrorMessage("Invalid symbol files");
				return false;
			}
		}
		return true;
	}	
	/**
	 * Saves the values currently displayed in the UI. These values are
	 * automatically shown when this UI is shown the next time.
	 */
	public void saveUserEnteredData() {
		// selge.ini is used
		if (buttonSelgeRadio.getSelection()) {
			saveValue(comboSelge.getText(), LAST_USED_SELGE_INI_FILES, false);
			savePreviousDecodingStyle(false);
		// symbol & map files are used
		} else {
			savePreviousDecodingStyle(true);
			saveValues(listSymbols.getItems(), LAST_USED_SYMBOL_FILES); 
			saveValue(comboMapFilesZip.getText(), LAST_USED_MAPZIP_FILES, false);
			saveValue(comboMapFilesFolder.getText(), LAST_USED_MAPFILES_FOLDER, false);
			saveValue(comboSdkFolder.getText(), LAST_USED_SDK, true);

			if (showImageAndTraceFilesGroup) {
				saveValues(listImageFiles.getItems(), LAST_USED_IMAGE_FILES);
				saveValues(listTraceFiles.getItems(), LAST_USED_TRACE_FILES);
			}
			
			int mapType = NO_MAP_FILES_RADIO;
			if (buttonMapFilesFolderRadio.getSelection())
				mapType = MAP_FILES_FOLDER_RADIO;
			else if (buttonMapFilesZipRadio.getSelection())
				mapType = MAP_FILES_ZIP_RADIO;
			else if (buttonSdkFolderRadio.getSelection())
				mapType = SDK_MAP_FILES_FOLDER_RADIO;
			savePreviousMapFilesType(mapType);
		}
	}
	
	/**
	 * Updates next, finish button states
	 */
	protected void updateButtons() {
		try {
			getWizard().getContainer().updateButtons();
		} catch (Exception E) {}		
	}
	
	/**
	 * Set help contexts for UI items.
	 */
	protected void setHelps() {
		String helpContext = getHelpContext();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonSelgeRadio, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonSymbolRadio, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonSelgeBrowse, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(listSymbols, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonZipBrowse, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(comboSelge, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonAddSymbol, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonRemoveSymbol, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(comboMapFilesZip, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonMapFolderBrowse, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonMapFilesZipRadio, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonMapFilesFolderRadio, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonSdkFolderRadio, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonNoMapFilesRadio, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(comboMapFilesFolder, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(comboSdkFolder, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonShowSelgeDetails, helpContext);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(textSelgeDetails, helpContext);
		if (showImageAndTraceFilesGroup) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(listImageFiles, helpContext);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonAddImageFile, helpContext);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonRemoveImageFile, helpContext);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(listTraceFiles, helpContext);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonAddTraceFile, helpContext);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(buttonRemoveTraceFile, helpContext);
		}
	}	
	
	/**
	 * If symbol file group was used when this UI was used last time, returns true. 
	 * Otherwise returns false.  
	 */	
	boolean symbolFileWasUsedPreviously() {
		try {
			boolean retval = false;
			if (previousValuesSection != null) {
				retval = previousValuesSection.getBoolean(LAST_USED_DECODING_STYLE);
			}
			return retval;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Saves whether selge.ini group or symbol group is used for decoding.
	 * This info can be restored when this UI is shown the next time. 
	 */
	void savePreviousDecodingStyle(boolean symbolFile) {
		try {
			if (previousValuesSection != null) {
				previousValuesSection.put(LAST_USED_DECODING_STYLE, symbolFile);
			}			
		} catch (Exception E) {
		}
	}
	
	/**
	 * Returns which map files radio button was selected the last time this 
	 * UI was used. 
	 */
	int lastUsedMapFileType() {
		try {
			int retval = 0;
			if (previousValuesSection != null) {
				retval = previousValuesSection.getInt(LAST_USED_MAPFILE_TYPE);
			}
			return retval;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * Saves which map file radio button is selected.
	 * This info can be restored when this UI is shown the next time.
	 */
	void savePreviousMapFilesType(int type) {
		try {
			if (previousValuesSection != null) {
				previousValuesSection.put(LAST_USED_MAPFILE_TYPE, type);
			}
		} catch (Exception E) {
		}
	}
	
	/**
	 * Returns previously entered values of given item. 
	 */
	String[] getPreviousPaths(String item) {
		String[] retVal = null;
		if (previousValuesSection != null) {
			retVal = previousValuesSection.getArray(item);
		}
		
		return retVal;
	}
	
	/**
	 * Saves values so that they can be used next this this UI is used. 
	 */
	void saveValues(String[] values, String item) {
		try {
			if (previousValuesSection != null) {
				previousValuesSection.put(item, values);
			}
		} catch (Exception E) {
		}
	}
	
	/**
	 * Saves given value to correct section in dialog_settings.xml
	 * @param value value to save
	 * @param item name of the array which contains correct values
	 * @param saveJustOne if true, only 'value' is saved. If false, 'value' is added to previously used values.
	 */
	void saveValue(String value, String item, boolean saveJustOne) {
		if (previousValuesSection != null) {
			String[] previousValues = previousValuesSection.getArray(item);
			
			// No previous values exist
			if (previousValues == null) {
				previousValues = new String[1];
				previousValues[0] = value;
			// Previous values exists
			} else {
				int valuesCount = previousValues.length;
				
				boolean valueExisted = false;
				// see if passed value already exist.
				for (int i = 0; i < valuesCount; i++) {
					if (previousValues[i].compareToIgnoreCase(value) == 0) {
						valueExisted = true;
						
						// passed value exists, move it to first position
						for (int j = i; j > 0; j--) {
							previousValues[j] = previousValues[j-1];
						}
						previousValues[0] = value;
						
						break;
					}
				}
				
				// passed value did not exist, add it to first position (and move older values "down")
				if (!valueExisted) {
					if (valuesCount >= MAX_SAVED_VALUES) {
						for (int i = valuesCount-1; i > 0; i--) {
							previousValues[i] = previousValues[i-1];
						}
						previousValues[0] = value;
					} else {
						String[] values = new String[valuesCount + 1];
						values[0] = value;
						for (int i = 0; i < valuesCount; i++) {
							values[i+1] = previousValues[i];
						}
						previousValues = values;
					}
				}
			}

			if (saveJustOne) {
				previousValues = new String[1];
				previousValues[0] = value;
			}
			
			previousValuesSection.put(item, previousValues);
		}
	}	
	
	public void dragEnter(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}

	public void dragLeave(DropTargetEvent event) {
		// nothing to be done
	}

	public void dragOperationChanged(DropTargetEvent event) {
		// nothing to be done
	}

	public void dragOver(DropTargetEvent event) {
		event.feedback = DND.FEEDBACK_NONE;
	}

	public void drop(DropTargetEvent event) {
		// we accept only file drops
		if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
			if (event.data != null) {
				String[] files = (String[])event.data;
				executeDrop(files);
			}
		}
	}

	public void dropAccept(DropTargetEvent event) {
		// nothing to be done
	}
	
	/**
	 * Executes drop functionality when files are drag&dropped to wizard page.
	 * Checks whether dropped files are supported and places them into correct UI element
	 * @param files drag&dropped files (paths)
	 */
	protected void executeDrop(String[] files) {
		for (int i = 0; i < files.length; i++) {
			String file = files[i].toLowerCase();
			
			// symbols file, add to symbols list
			if (file.endsWith(".symbol") &&
				!listContains(listSymbols, file)) {
				listSymbols.add(files[i]);
			}
			
			// image file, add to images list
			if ( isImageFile(file) &&
				 !listContains(listImageFiles, file)) {
				listImageFiles.add(files[i]);
			}
			
			// zip file, select correct radio button and add file to combo
			if (file.endsWith(".zip")) {
				comboMapFilesZip.setText(file);
				if (buttonMapFilesFolderRadio.getSelection())
					buttonMapFilesFolderRadio.setSelection(false);
				else if (buttonNoMapFilesRadio.getSelection())
					buttonNoMapFilesRadio.setSelection(false);
				else if (buttonSdkFolderRadio.getSelection())
					buttonSdkFolderRadio.setSelection(false);
				buttonMapFilesZipRadio.setSelection(true);
				enableMapSelections();
			}
		}
		
		updateButtons();
	}
	
	protected boolean isImageFile(String file) {
		if (file.endsWith(".fpsx") ||
			file.endsWith(".img"))
			return true;
		
		// .C?? and .V?? file
		if (file.substring(file.length()-4, file.length()-3).equals(".") &&
			(file.substring(file.length()-3, file.length()-2).equalsIgnoreCase("v") ||
			 file.substring(file.length()-3, file.length()-2).equalsIgnoreCase("c")))
			return true;

		return false;
	}
	
	protected boolean listContains(List list, String item) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getItem(i).equalsIgnoreCase(item))
				return true;
		}
		
		return false;
	}
	
}
