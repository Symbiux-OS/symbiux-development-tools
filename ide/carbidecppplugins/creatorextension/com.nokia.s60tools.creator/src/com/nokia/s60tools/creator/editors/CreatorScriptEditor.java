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
package com.nokia.s60tools.creator.editors;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.nokia.s60tools.creator.CreatorActivator;
import com.nokia.s60tools.creator.CreatorHelpContextIDs;
import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.Components;
import com.nokia.s60tools.creator.components.contact.ContactSetVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.dialogs.AbstractDialog;
import com.nokia.s60tools.creator.dialogs.DialogLauncher;
import com.nokia.s60tools.creator.util.CreatorEditorConsole;
import com.nokia.s60tools.creator.xml.CreatorScriptNotValidException;
import com.nokia.s60tools.creator.xml.CreatorXML;
import com.nokia.s60tools.creator.xml.CreatorXMLParser;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.util.resource.FileUtils;

/**
 * Creator script editor - Editor area. Stores all components and handles adding, removing and editing a Script.
 */
public class CreatorScriptEditor extends MultiPageEditorPart implements IResourceChangeListener, IComponentProvider{

	private static final String ADD_TXT = " Add ";

	
	public static final int COMPONENT_LIST_WIDTH = 500;

	public static final int EDITOR_DEFAULT_WIDTH = 600;

	/**
	 * Fixed Width and height parameters to set UI components 
	 * precisely 
	 */
	private static final int COMPONENT_LIST_ITEMS_HEIGHT_HINT = 8;

	/** The text editor used in page 0. */
	private TextEditor editor;
	
	/**
	 * All components
	 */
	private Components components;
	

	
	//Editor window widgets

	private List componentsList;
	
	private Combo componentCombo;

	private Button removeBtn;

	private Button editBtn;

	private boolean isDirty;

	private Color white;

	private boolean isEditLaunched = false;

	private Button addComponentButton;

	private Button addContactSetButton;
	
	private Button runInDeviceButton = null;	
	
	
	
	
	
	/**
	 * Creates a multi-page editor example.
	 */
	public CreatorScriptEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private void setContextSensitiveHelpIDs(){
		 PlatformUI.getWorkbench().getHelpSystem().setHelp(componentCombo, 
		    		CreatorHelpContextIDs.CREATOR_HELP_ADD_COMPONENT);
		 PlatformUI.getWorkbench().getHelpSystem().setHelp(addComponentButton, 
		    		CreatorHelpContextIDs.CREATOR_HELP_ADD_COMPONENT);		
		 
		 PlatformUI.getWorkbench().getHelpSystem().setHelp(editBtn, 
		    		CreatorHelpContextIDs.CREATOR_HELP_MODIFY_COMPONENT);		
		 PlatformUI.getWorkbench().getHelpSystem().setHelp(removeBtn, 
		    		CreatorHelpContextIDs.CREATOR_HELP_MODIFY_COMPONENT);		
		 PlatformUI.getWorkbench().getHelpSystem().setHelp(componentsList, 
		    		CreatorHelpContextIDs.CREATOR_HELP_MODIFY_COMPONENT);		

		 if(runInDeviceButton != null){
			 PlatformUI.getWorkbench().getHelpSystem().setHelp(runInDeviceButton, 
			    		CreatorHelpContextIDs.RUN_IN_DEVICE_PAGE);					 
		 }
		 
	}

	/**
	 * Create Creator Editor page.
	 * 
	 * Creating all Widgets and setting data from this.xml to them.
	 * Creating listeners for selected Widgets. 
	 * 
	 */
	private void createCreatorScriptEditorPage() {


		final Composite composite = new Composite(getContainer(), SWT.SIMPLE);
		final Shell shell = composite.getShell();

		GridLayout gridLayout = new GridLayout();//2, false
		composite.setLayout(gridLayout);

		GridData gridData = new GridData();
 		gridData.horizontalAlignment = GridData.FILL;
 		gridData.grabExcessHorizontalSpace = true;
		composite.setLayoutData(gridData);
		
		RGB rgbWhite = new RGB(255, 255, 255);
		white = new Color(null, rgbWhite);
		composite.setBackground(white);

		//Create part of UI where is components to add, contact set adding button and run in device button
		createButtonsPart(composite, shell);		
		
		
		//COMPONENTS
		Group componentsGroup = new Group(composite, SWT.SHADOW_NONE);
		componentsGroup.setText("Components added:");
		GridLayout componentsGrid = new GridLayout(2, false);
		componentsGroup.setBackground(white);
		GridData componentsGridData = new GridData(EDITOR_DEFAULT_WIDTH, SWT.DEFAULT/*GridData.HORIZONTAL_ALIGN_FILL*/);
		componentsGridData.horizontalAlignment = GridData.FILL;
		componentsGridData.grabExcessHorizontalSpace = true;	
		componentsGridData.verticalAlignment = GridData.FILL;
		componentsGridData.grabExcessVerticalSpace = true;

		componentsGroup.setLayout(componentsGrid);
		componentsGroup.setLayoutData(componentsGridData);
		
		
		final int listBoxStyleBits = SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL;
		componentsList = new List(componentsGroup,listBoxStyleBits);
		//If font not found or something else unexpected occur, don't fail whole program
		try {
			componentsList.setFont(getUnicodeFont());
		} catch (Exception e) {
			e.printStackTrace();
			CreatorEditorConsole.getInstance().println("Could not set list font to Unicode, reason: " +e.getMessage(), CreatorEditorConsole.MSG_ERROR);
		}
		GridData listData = new GridData(COMPONENT_LIST_WIDTH, SWT.DEFAULT);
		listData.horizontalAlignment = GridData.FILL;
		listData.grabExcessHorizontalSpace = true;
		listData.verticalAlignment = GridData.FILL;
		listData.grabExcessVerticalSpace = true;

		int listHeight = componentsList.getItemHeight() * COMPONENT_LIST_ITEMS_HEIGHT_HINT;
		Rectangle trim = componentsList.computeTrim(0, 0, 0, listHeight);
		listData.heightHint = trim.height;		
		componentsList.setLayoutData(listData);
		
		componentsList.addSelectionListener(getEnableButtonsListener());	
		
		
		//
		//Setting components data to selection box
		//
		
		
		redrawComponentList();

		Composite btnComp = new Composite(componentsGroup, SWT.SIMPLE);
		btnComp.setLayout(new GridLayout(1, false));
		GridData btnGrid = new GridData();
		btnGrid.verticalAlignment = SWT.BEGINNING;
		btnGrid.horizontalAlignment = SWT.BEGINNING;
		btnComp.setLayoutData(btnGrid);//GridData.FILL_BOTH	
		btnComp.setBackground(white);
		
		removeBtn = new Button(btnComp,SWT.PUSH);
		removeBtn.setText("Remove");
		removeBtn.setEnabled(false);
		
		editBtn = new Button(btnComp,SWT.PUSH);
		editBtn.setText("   Edit   ");
		editBtn.setEnabled(false);//By Default edit is not available, but when a component is selected, its enabled 
		
		//Open Edit Dialog when Edit button has been pushed
		editBtn.addSelectionListener(getEditButtonListener(this, shell));			
		
		//Adding action to remove Button
		removeBtn.addSelectionListener(getRemoveComponentListener());			
		
		
		
		int pageIndex = addPage(composite);
		composite.pack();
		setPageText(pageIndex, "Creator Script Editor");
	}

	private void createButtonsPart(final Composite composite, final Shell shell) {
		//ADD COMPONENT GROUP				
		Group addComponentGroup = new Group(composite, SWT.SHADOW_NONE);
		addComponentGroup.setText("Add a component to script:");
		GridLayout componentGrid = new GridLayout(7, false);
		addComponentGroup.setBackground(white);
		GridData componentGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		componentGridData.horizontalAlignment = GridData.FILL;
		componentGridData.grabExcessHorizontalSpace = true;		
		addComponentGroup.setLayout(componentGrid);
		addComponentGroup.setLayoutData(componentGridData);

		//SELECT COMPONENT LABEL
		Label compoenentLabel = new Label(addComponentGroup, SWT.HORIZONTAL);
		compoenentLabel.setText("Select component to add:");
		compoenentLabel.setLayoutData(new GridData());
		compoenentLabel.setBackground(white);

		//Components to select
		componentCombo = new Combo(addComponentGroup, SWT.READ_ONLY | SWT.DROP_DOWN);
		componentCombo.setVisibleItemCount(CreatorEditorSettings.getInstance().getComponents().length);
		componentCombo.setItems(CreatorEditorSettings.getInstance().getComponents());
		componentCombo.setLayoutData(new GridData());
		
		
		addComponentButton = new Button(addComponentGroup, SWT.PUSH);
		addComponentButton.setText(ADD_TXT);		

		//add button listener
		addComponentButton.addSelectionListener(getAddComponentButtonListener(shell, this));		

		
		//Create own button for contact-set:s
		Label nullText = new Label(addComponentGroup, SWT.HORIZONTAL);
		nullText.setBackground(white);
		nullText.setText("       ");//Just for making some space between buttons.
		addContactSetButton = new Button(addComponentGroup, SWT.PUSH);
		addContactSetButton.setText(ContactSetVariables.ADD_CONTACT_SET_TXT);		

		//add button listener
		addContactSetButton.addSelectionListener(getAddNewContactSetComponentListener(shell, this));

		
		//create button for run in device
		Label nullText2 = new Label(addComponentGroup, SWT.HORIZONTAL);
		nullText2.setBackground(white);
		nullText2.setText("       ");//Just for making some space between buttons.
		//Checking if HTI is supported at all, if not, not creating button at all.
		if(isHTIAvailable()){
			runInDeviceButton = new Button(addComponentGroup, SWT.PUSH);
			runInDeviceButton.setText("Run in device via HTI");
			runInDeviceButton.addSelectionListener(new RunInDeviceSelectionListener(components.getFileName(), components.getFilePath()));
		}
	}
	
	
	/**
	 * Checking if HTI connection package is available.
	 * @return <code>true</code> if HTI is available, <code>false</code> otherwise.
	 */
	private boolean isHTIAvailable() {

		try {
			//If HTI is not available, Class HTIServiceFactory does not found and NoClassDefFoundError is thrown.
			//There are different deliverables available and with all, HTI is not bundled with Carbide. 
			HTIServiceFactory
				.createFTPService(CreatorEditorConsole.getInstance());
		} catch (NoClassDefFoundError e) {
			CreatorEditorConsole.getInstance().println("HTI Connection is not available, Run in Device via HTI functionality is disabled.");
			return false;
		}			
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.editors.IComponentProvider#getAddNewContactSetComponentListener(org.eclipse.swt.widgets.Shell, com.nokia.s60tools.creator.editors.IComponentProvider)
	 */
	public SelectionListener getAddNewContactSetComponentListener(final Shell shell,
			final IComponentProvider provider) {
		
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				//tell listeners that a component was added to list	
				IAddComponentListener listener = null;
				if(event.widget != null){
					Object o = event.widget.getData();
					if(o instanceof IAddComponentListener){
						//if listener was provided, passing it through to compont adding
						listener = (IAddComponentListener)o;
					}
				}
				
				isEditLaunched = false;

				// Get a dialog by selection type
				final AbstractDialog aComponentAddDialog = DialogLauncher
						.getDialog(CreatorEditorSettings.TYPE_CONTACT_SET, shell, provider);
				// Open dialog
				openAddNewComponentDialog(aComponentAddDialog, listener);				
			}
		};
	}

	/**
	 * Get listener for Add button
	 * @param shell
	 * @return Listener
	 */
	private SelectionAdapter getAddComponentButtonListener(final Shell shell, final IComponentProvider provider) {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				isEditLaunched = false;

				// Get a dialog by selection type
				final AbstractDialog aComponentAddDialog = DialogLauncher
						.getDialog(componentCombo.getText(), shell, provider);
				openAddNewComponentDialog(aComponentAddDialog, null);
			}

		};
	}
	/**
	 * Open a dialog
	 * @param aComponentAddDialog
	 */
	private void openAddNewComponentDialog(
			final AbstractDialog aComponentAddDialog, IAddComponentListener listener) {
		// Open dialog
		aComponentAddDialog.open();
		if (aComponentAddDialog.getReturnCode() == IDialogConstants.OK_ID) {
			// After dialog closed, get component(s) created
			AbstractComponent comp = aComponentAddDialog.getComponent();
			addComponent(comp, listener); 

		}// else, cancel is pushed
	}

	
	/**
	 * Get Listener for enabling buttons or not
	 * @return Listener
	 */
	private SelectionAdapter getEnableButtonsListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
	
				boolean buttonsEnabled = componentsList.getSelectionIndex() != -1 ? true : false ;
				removeBtn.setEnabled(buttonsEnabled);

				//Edit only enabled when one row is selected
				if(buttonsEnabled && componentsList.getSelection().length == 1){
					editBtn.setEnabled(buttonsEnabled);
				}else{
					editBtn.setEnabled(false);
				}

			}
		};
	}


	/**
	 * Get Listener for Edit button
	 * @param shell
	 * @return Listener
	 */
	private SelectionAdapter getEditButtonListener(final IComponentProvider provider, final Shell shell) {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				isEditLaunched = true;

				//Get a dialog by selection type
				int compIndex = componentsList.getSelectionIndex();
				AbstractComponent comp = provider.getEditable();

				final AbstractDialog aComponentEditDialog = DialogLauncher.getDialog(provider, shell) ;//new AddContactDialog(shell);
				//Open dialog
				aComponentEditDialog.open();
				if (aComponentEditDialog.getReturnCode() == IDialogConstants.OK_ID) {
					//After dialog closed, get component(s) created
					//AbstractComponent editedComp= aComponentEditDialog.getComponent();
					AbstractComponent editedComp= aComponentEditDialog.getComponent();
					
					//Add component to list and check if its valid
					if(editedComp.isValid()){
						getComponents().updateComponent(comp, editedComp);
						componentsList.setItem(compIndex, editedComp.toString());
						setDirty(true);
					}
					else{
						//set error message
						showErrorDialog("Component is not valid", "Component edited is not valid", "Please edit valid values to component.");
					}
				}//else, cancel is pushed
			}
		};
	}


	/**
	 * Handle component(s) removal
	 * 
	 * @return Listener
	 */
	private SelectionAdapter getRemoveComponentListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				int[] indexes = componentsList.getSelectionIndices();
				Vector<AbstractComponent> componentsToBeRemoved = new Vector<AbstractComponent>(indexes.length);
				
				//First just collecting components to be removed, because when Contact-set is removed a 
				//components referenced to that will be changed, and seeking components left requires
				//component list update to be found after that
				for (int i = 0; i < indexes.length; i++) {
					AbstractComponent comp = getComponentBySelection(indexes[i]);
					componentsToBeRemoved.add(comp);
				}				

				//actually removing components and references to component to be removed
				for (Iterator<AbstractComponent> iterator = componentsToBeRemoved.iterator(); iterator.hasNext();) {
					AbstractComponent comp = (AbstractComponent) iterator.next();
					//remove references to that component
					getComponents().removeReferencesToComponent(comp);		
					//remove component
					getComponents().remove(comp);					
				}
				
				componentsList.remove(indexes);
				
				//Update component list, because many components may changed				
				redrawComponentList();				
				setDirty(true);
			}

		};
	}
	
	private void addComponentToList(AbstractComponent aComponent, List componentsList) {
		componentsList.add(  aComponent.toString());
	}
	
	private void redrawComponentList() {
		componentsList.removeAll();
		//Get all component types (Contacts, Connection methods, messages...)
		Set<String> allComponentTypes = getComponents().getComponentTypes();
		//Looping through all component types one by one
		for (Iterator<String> allCompKeysIt = allComponentTypes.iterator(); allCompKeysIt.hasNext();) {			
			String compType = (String) allCompKeysIt.next();
			Vector<AbstractComponent> allComponentsByType = getComponents().getComponents(compType);
	
			//Looping through all components in one component type
			for (Iterator<AbstractComponent> iterator = allComponentsByType.iterator(); iterator.hasNext();) {
				AbstractComponent aComponent = (AbstractComponent) iterator.next();			
				addComponentToList(aComponent, componentsList);
			}			
		}
	}

	
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		
		try {
			createCreatorScriptEditorPage();
			setContextSensitiveHelpIDs();			
		} catch (Exception e) {
			CreatorEditorConsole.getInstance().println("Unexpected error occurs: " + e, CreatorEditorConsole.MSG_ERROR);			
			e.printStackTrace();			
		}

	}
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		//getEditor(0).doSave(monitor);
				
		InputStream stream = null;
		try {
			IEditorInput editorInput = getEditorInput();
			//Type is IFileEditorInput allways when file is opened from project
			//If file opened outside project, editor type is IPathEditorInput
			if (editorInput instanceof IFileEditorInput) {
				IFile file;
				//Just making sure that stream is closed before renaming file
				try {
					stream = openUTF8ContentStream(getComponents().toXMLString());

					IFileEditorInput iFile = (IFileEditorInput) editorInput;
					file = iFile.getFile();

					if (file.exists()) {
						file.setContents(stream, true, true, monitor);
						file.setCharset(FileUtils.ENCODING_TYPE_UTF_8, monitor);	
					} else {
						file.create(stream, true, monitor);
						file.setCharset(FileUtils.ENCODING_TYPE_UTF_8, monitor);
					}			
				} finally {
					stream.close();
				}
			}
			//Else file is opened outside of Carbide project
			else if (editorInput instanceof IPathEditorInput) {
				IPathEditorInput javaInput = (IPathEditorInput) editorInput;
				IPath path = javaInput.getPath();				
				String fileLocation = path.toOSString();
				FileUtils.writeToFile(fileLocation, getComponents().toXMLString(), FileUtils.ENCODING_TYPE_UTF_8);
			}else{// if (editorInput instanceof FileStoreEditorInput) {
				FileStoreEditorInput inp = ((FileStoreEditorInput) editorInput);	
				URI uri = inp.getURI();				
				java.io.File fi = new java.io.File(uri);
				String fileLocation = fi.getAbsolutePath();
				FileUtils.writeToFile(fileLocation, getComponents().toXMLString(), FileUtils.ENCODING_TYPE_UTF_8);				
			}				
			
			setDirty(false);
			
		} catch (Exception e) {
			showErrorDialog("Errors on save", "Save was not compleate.",
					e.getMessage());
			e.printStackTrace();
		}
		
	}
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {

		
		try {
			IEditorInput editorInput = getEditorInput();
			SaveAsDialog saveas = new SaveAsDialog(getContainer().getShell());
			//Setting default saveasdialog file name and location
			//If API name is changed IFileEditorInput is still the same
			//and if orginal file is not saved yet (xml.isAPINameChanged())
			//Setting default name to UI as new generated file name instead of old filename and path
			if (editorInput instanceof IFileEditorInput ) {
				IFileEditorInput iFile = (IFileEditorInput) editorInput;				
				saveas.setOriginalFile(iFile.getFile());
			} 
			//Else file is opened outside of Carbide project
			else if (editorInput instanceof IPathEditorInput) {
				IPathEditorInput javaInput = (IPathEditorInput) editorInput;
				IPath path = javaInput.getPath();				
				saveas.setOriginalName(path.lastSegment());				
			}else{// if (editorInput instanceof FileStoreEditorInput) {
				FileStoreEditorInput inp = ((FileStoreEditorInput) editorInput);
				saveas.setOriginalName(inp.getName());				
			}			

			saveas.open();
			
			if (SaveAsDialog.CANCEL == saveas.getReturnCode()) {
				return;
			}

			if (saveas.getResult() == null
					|| !saveas.getResult().toString().endsWith(".creatorxml")
					|| !saveas.getResult().getFileExtension().equals("creatorxml")) {
				showErrorDialog("Wrong file type",
						"Save as was not complete. Please correct file type.",
						"File type must be .creatorxml");
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
			InputStream stream = openUTF8ContentStream(getComponents().toXMLString());
			ProgressMonitorPart monitor = new ProgressMonitorPart(
					getContainer(), new GridLayout());
			file.create(stream, true, monitor);
			file.setCharset(FileUtils.ENCODING_TYPE_UTF_8, null);			
			stream.close();

			
		} catch (Exception e) {
			showErrorDialog("Errors on save as", "Save as was not compleate.",
					e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Open a stream from components in UTF-8 format
	 * Client must close the opened stream.
	 * @return components XML as stream
	 * @throws UnsupportedEncodingException 
	 */
	private InputStream openUTF8ContentStream(String string) throws UnsupportedEncodingException {
		return openContentStream(string, FileUtils.ENCODING_TYPE_UTF_8);
	}
	/**
	 * Open a stream from components in specified character set format
	 * Client must close the opened stream.
	 * @return components XML as stream
	 * @throws UnsupportedEncodingException 
	 */
	private InputStream openContentStream(String string, String charset) throws UnsupportedEncodingException {
		if(charset == null){
			charset = FileUtils.ENCODING_TYPE_UTF_8;
		}
		InputStream in = new ByteArrayInputStream(string.getBytes(charset));	
		return in;
	}		
	
	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}
	/**
	 * Checks that the input is an instance of <code>IFileEditorInput, IPathEditorInput or FileStoreEditorInput</code>.
	 * And sets Title and content of file.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput)
				&& !(editorInput instanceof IPathEditorInput)
				&& !(editorInput instanceof FileStoreEditorInput) ){
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput or IPathEditorInput, was: "
							+ editorInput.getClass().getName());
		}
		setTitle(editorInput);
		setTitleImage(CreatorActivator
				.getImageForKey(CreatorActivator.CREATOR_SCRIPT_EDITOR_ICON));		

		parseXMLAndSetComponents(editorInput);
		super.init(site, editorInput);

	}
	
	/**
	 * When open a file, setting this.xml from file.
	 * If file was old existing file, parsing file and set it to this.xml
	 * if file was new just created file, creating new xml object and setting
	 * it to this.xml
	 * @param input
	 */
	private void parseXMLAndSetComponents(IEditorInput input) {

		String fileName = "";
		String filePath = "";
		try {

			String xmlString;
			String charSet = null;
			//Type is IFileEditorInput allways when file is opened from project
			//If file opened outside project in Carbide 1.2 editor type is IPathEditorInput
			//and in Carbide 1.3 its FileStoreEditorInput
			if (input instanceof IFileEditorInput) {
				IFileEditorInput iFile = (IFileEditorInput) input;
				IFile file = iFile.getFile();
				fileName = file.getName();
				filePath = file.getLocation().toOSString();
				InputStream in = file.getContents();
				//Found out file charset, and opening file contents by that. So also non UTF-8 files is working
				//Note! If e.g. file is edited in Windows, and charset is cp1252, then "official" (windows-1252) charset name must be in XML file
				//e.g. <?xml version="1.0" encoding="windows-1252"?>, otherwise parsing XML file will fail.
				charSet = file.getCharset();
				xmlString = getFileContentsWithCharSet(in, charSet);
				in.close();
				in = null;

			} else if(input instanceof IPathEditorInput){
				IPath path = ((IPathEditorInput) input).getPath();
				fileName = path.lastSegment();
				filePath = path.toOSString();
				java.io.File fi = path.toFile();				
				FileInputStream in = new FileInputStream(fi);
				xmlString = getFileContentsAsUTF8(in);
				in.close();
				in = null;
			}else{
				FileStoreEditorInput inp = ((FileStoreEditorInput) input);	
				URI uri = inp.getURI();				
				java.io.File fi = new java.io.File(uri);
				fileName = fi.getName();
				filePath = fi.getAbsolutePath();				
				FileInputStream in = new FileInputStream(fi);
				xmlString = getFileContentsAsUTF8(in);
				in.close();
				in = null;

			}
			
			//if file is just created new API metadatafile with wizard
			//creating new xml object and opening it
			if (xmlString.startsWith(CreatorXML.NEW_API_CREATOR_FILE_UID)) {
				components = new Components();
				setDirty(true);
			}
			//Otherwise file is old existing metadata file, parsing it and setting 
			//xml to this.xml
			else {
				components = parseXML(fileName, openContentStream(xmlString, charSet));
				setDirty(false);
				CreatorEditorConsole.getInstance().println(
						"Creator Script XML file: '" + fileName
								+ "' opened.");
			}

			components.setFileName(fileName);
			components.setFilePath(filePath);

		} catch (CreatorScriptNotValidException e) {
			//If parsing was ok, but xml was not walid setting xml so user can correct errors
			CreatorEditorConsole.getInstance().println(
					"CreatorScriptNotValidException on init: " + e.toString());
			showErrorDialog("Errors on Creator Script XML", "Creator XML file "
					+ fileName + " could not be parsed.", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			CreatorEditorConsole.getInstance().println(
					"Exception on init: " + e.toString());
			showErrorDialog("Error",
					"Creator editor could not be opened. Errors on "
							+ fileName, e.getMessage());
		}

	}
	
	/**
	 * Get xml String from stream
	 * @param is
	 * @return
	 * @throws CoreException
	 * @throws IOException
	 */
	private String getFileContentsAsUTF8(InputStream is) throws CoreException,
			IOException {

		int READ_BYTES = 3;
		
		StringBuffer buf = new StringBuffer();
		PushbackInputStream pushIn = new PushbackInputStream(is, READ_BYTES);

		byte[] bomBytes = new byte[READ_BYTES];//for reading 3 first bytes
		pushIn.read(bomBytes,0, READ_BYTES);//read 3 bytes
		
		boolean isBomFile = false;

		//BOM Files starts with 0xEF, 0xBB, 0xBF
		if(bomBytes[0] == (byte)0xEF && bomBytes[1] == (byte)0xBB && bomBytes[2] == (byte)0xBF){
			isBomFile = true;
		}
		
		//If file was not started with BOM, must put first characters to buffer 
		if (!isBomFile) {
			pushIn.unread(bomBytes, 0, READ_BYTES);
		}
	
		InputStreamReader isr = new InputStreamReader(pushIn, FileUtils.ENCODING_TYPE_UTF_8);
		BufferedReader br = new BufferedReader(isr);
		
		String line;
		while ((line = br.readLine()) != null) {			
			buf.append(line);
		}
		String xmlString = buf.toString();
		// Closing streams
		pushIn.close();
		br.close();
		isr.close();
		return xmlString;
	}	
	
	/**
	 * Get xml String from stream
	 * @param is
	 * @param character set
	 * @return
	 * @throws CoreException
	 * @throws IOException
	 */
	private String getFileContentsWithCharSet(InputStream is, String charset) throws CoreException,
			IOException {
		
		if(charset == null){
			charset = FileUtils.ENCODING_TYPE_UTF_8;
		}
		if(charset.equalsIgnoreCase(FileUtils.ENCODING_TYPE_UTF_8)){
			return getFileContentsAsUTF8(is);
		}
		
		StringBuffer buf = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is, charset);
		BufferedReader br = new BufferedReader(isr);
		
		String line;
		while ((line = br.readLine()) != null) {			
			buf.append(line);
		}
		String xmlString = buf.toString();
		// Closing streams
		is.close();
		br.close();
		isr.close();
		return xmlString;

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
		} else if (editorInput instanceof IPathEditorInput) {
			IPath path = ((IPathEditorInput) editorInput).getPath();
			String filename = path.lastSegment();
			setPartName(filename);
		}else if (editorInput instanceof FileStoreEditorInput) {
			FileStoreEditorInput inp = ((FileStoreEditorInput) editorInput);
			String filename = inp.getName();
			setPartName(filename);
		}		
	}	
	
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}
	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i<pages.length; i++){
						if(((FileEditorInput)editor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
	}

	
	/**
	 * Get Arial Unicode MS font
	 * @return
	 */
	private Font getUnicodeFont() {
		Font defaultFont = componentsList.getFont();
		FontData defaulFD [] = defaultFont.getFontData();
		//Font Arial Unicode MS is Supplied with Microsoft Office 2002 (XP) and Microsoft Office 2003.
		//@see http://www.alanwood.net/unicode/fonts.html
		FontData fd = new FontData("Arial Unicode MS", defaulFD[0].getHeight(),  defaulFD[0].getStyle());
		return new Font(Display.getCurrent(), fd);
	}
	
	/**
	 * Parse xml String to XML object
	 * @param fileName
	 * @return
	 * @throws MetadataNotValidException
	 */
	private Components parseXML(String fileName, InputStream inUTF8)
			throws CreatorScriptNotValidException {
		CreatorXMLParser parser = new CreatorXMLParser();
		//This can be optimized by sending Reader as parameter instead of Stream,
		//That will also require logic update when opening file and checking how file was opened 
		//(was it a new file, project file or outside of the project file)
		Components comps = parser.parse(inUTF8);
		
		if(parser.wasErrors()){
			showErrorDialog("Errors on parsing", 
					"There was some errors when parsing file: " +fileName, 
					parser.getErrors());
		}
		
		return comps;
	}
	
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.editors.IComponentProvider#getComponents()
	 */
	public Components getComponents() {
		return components;
	}
	
	/**
	 * Get selected component
	 * @param selectionIndex
	 * @return Component
	 */
	private AbstractComponent getComponentBySelection(int selectionIndex) {
		String componentToString = componentsList.getItem(selectionIndex);

//		AbstractComponent comp = (AbstractComponent)componentsList.getData("" +selectionIndex);
		AbstractComponent comp = getComponents().getComponentByComponentString(componentToString);
		return comp;
	}
	
	/**
	 * Show an error dialog
	 * @param title
	 * @param message
	 * @param errors
	 */
	private void showErrorDialog(String title, String message, String errors) {
		Status status = new Status(IStatus.ERROR,
				"com.nokia.s60tools.metadataeditor", 0, errors, null);
		Shell sh;
		if (getContainer() != null) {
			try {
				sh = getContainer().getShell();
			} catch (SWTException e) {
				sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
			}
		} else {
			sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
		}

		ErrorDialog.openError(sh, title, message, status);
	}

	/**
	 * Show an information dialog
	 * @param title
	 * @param message
	 */
	@SuppressWarnings("unused")
	private void showInformationDialog(String title, String message) {
		Shell sh;
		if (getContainer() != null) {			
			try {
				sh = getContainer().getShell();
			} catch (SWTException e) {
				sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
			}
			
		} else {
			sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
		}

		MessageDialog.openInformation(sh, title, message);
	}	
	
	/**
	 * If data is modified, editor is dirty and can be saved
	 * NOTE: If editor data is changed and then changed back to original
	 * data contents, status still remain as dirty
	 */
	public boolean isDirty() {
		return this.isDirty;
	}	
	
	/**
	 * If editor contents is modified, save is allowed
	 * @param isDirty
	 */
	private void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		if(runInDeviceButton!=null){
			runInDeviceButton.setEnabled(!isDirty);
		}
		if (isDirty) {
			firePropertyChange(PROP_DIRTY);
		} else {
			firePropertyChange(PROP_INPUT);
		}
	}



	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.editors.IComponentProvider#getComponents(java.lang.String)
	 */
	public Vector<AbstractComponent> getComponents(String type) {		
		return getComponents().getComponents(type);
	}


	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.editors.IComponentProvider#getEditable()
	 */
	public AbstractComponent getEditable() {
		//Get a dialog by selection type
		int compIndex = componentsList.getSelectionIndex();
		AbstractComponent comp = null;
		if(compIndex != -1){
			comp = getComponentBySelection(compIndex);
		}
		return comp;
	}


	public boolean isInEditMode() {
		return isEditLaunched ;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.editors.IComponentProvider#addComponent(com.nokia.s60tools.creator.components.AbstractComponent)
	 */
	public void addComponent(AbstractComponent comp, IAddComponentListener listener) {

		//Adding a component to list, currently using outside of editor only when a contact set is created by dialog, not by editor

		if (comp.isValid()) {
			getComponents().addComponent(comp);
			addComponentToList(comp, componentsList);
			setDirty(true);
			if(listener != null){
				// tell listeners that a component was added to list
				listener.componentAdded(comp);
			}
		} else {
			// Error handling
			showErrorDialog("Component is not valid",
					"Component added is not valid",
					"Not valid component");
		}		
		
	}
	
}
