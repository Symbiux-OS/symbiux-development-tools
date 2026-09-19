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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.IMakerKeyConstants;
import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.ImageFlasherHelpContextIDs;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.UITarget;
import com.nokia.s60tools.imaker.internal.managers.ProjectManager;
import com.nokia.s60tools.imaker.internal.model.FileToImage;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory;
import com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;
import com.nokia.s60tools.imaker.internal.model.iContent.ImageContent;
import com.nokia.s60tools.imaker.internal.providers.CheckBoxEditingSupport;
import com.nokia.s60tools.imaker.internal.providers.ComboEditingSupport;
import com.nokia.s60tools.imaker.internal.providers.TextEditingSupport;

public class DebugTab extends CTabItem implements IPropertyViewer {
	public static final String CHECKED_IMAGE 	= "checked";
	public static final String UNCHECKED_IMAGE  = "unchecked";
	public static final String WARNING_IMAGE    = "warning";
	public static final String ERROR_IMAGE      = "error";
	public static final String REL_PATTERN      = ".*epoc32.release.+?(urel).*";
	public static final String DEBUG_PATTERN    = ".*epoc32.release.+?(udeb).*";

	// For the checkbox images
	private static ImageRegistry imageRegistry = IMakerPlugin.getDefault().getImageRegistry();
	
	private IMakerTabsViewer tabsViewer;
	private ProjectManager projectManager;
	private TableViewer tableViewer;
	private PreferencesTab mainTab;
	private IContentFactory factory;
	
	static {
		String iconPath = "icons/"; 
		imageRegistry.put(CHECKED_IMAGE, IMakerPlugin.getImageDescriptor( 
				iconPath + CHECKED_IMAGE + ".gif"
		)
		);
		imageRegistry.put(UNCHECKED_IMAGE, IMakerPlugin.getImageDescriptor( 
				iconPath + UNCHECKED_IMAGE + ".gif"
		)
		);
		imageRegistry.put(WARNING_IMAGE, IMakerPlugin.getImageDescriptor( 
				iconPath + WARNING_IMAGE + ".gif"
		)
		);	
		imageRegistry.put(ERROR_IMAGE, IMakerPlugin.getImageDescriptor( 
				iconPath + ERROR_IMAGE + ".gif"
		)
		);	
	}

	public DebugTab(CTabFolder parent, int style, IMakerTabsViewer viewer, PreferencesTab main) {
		super(parent, style);
		this.tabsViewer = viewer;
		this.projectManager = tabsViewer.getProjectManager();
		setControl(createControl(parent));
		this.mainTab = main;
		this.factory = IContentFactory.eINSTANCE;
	}
	
	private void duplicateSelection() {
		ISelection selection = tableViewer.getSelection();
		if(selection!=null) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			IbyEntry item = (IbyEntry) ss.getFirstElement();
			IbyEntry copy = factory.createIbyEntry();
			copy.setAction(item.getAction());
			copy.setEnabled(item.isEnabled());
			copy.setFile(item.getFile());
			copy.setTarget(item.getTarget());
			copy.setLocation(item.getLocation());
			ImageContent ic = getInput();
			int index = ic.getEntries().indexOf(item);
			ic.getEntries().add(index+1, copy);
			tableViewer.refresh();
		}
	}
	private void deleteSelection() {
		ISelection selection = tableViewer.getSelection();
		if(selection!=null) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object[] objects = ss.toArray();
			for (int i = 0; i < objects.length; i++) {
				IbyEntry fe = (IbyEntry) objects[i];
				ImageContent ic = getInput();
				ic.getEntries().remove(fe);						
			}
			tableViewer.refresh();
		}
	}
	
	private Control createControl(CTabFolder parent) {
		Composite top = new Composite(parent,SWT.NONE);
		top.setLayout(new GridLayout(2,false));
		top.setLayoutData(new GridData(GridData.FILL_BOTH));
		setHelpForControl(top, ImageFlasherHelpContextIDs.IMAKERDIALOG_CONTENTTAB);
		
		// create table
		Composite tableComp = getNewComposite(top);
		Table table = new Table(tableComp,SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		GridData gridData = new GridData(GridData.FILL,GridData.FILL,true,true);
		gridData.heightHint = 180;
		table.setLayoutData(gridData);
		table.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent e) {
				if ((e.character == ' ')) {
					StructuredSelection ss = (StructuredSelection) tableViewer.getSelection();
					Iterator it = ss.iterator();
					while (it.hasNext()) {
						IbyEntry entry = (IbyEntry)it.next();
						entry.setEnabled(!entry.isEnabled());
						tableViewer.refresh(entry);						
					}
				}
			}
			
			public void keyPressed(KeyEvent e) {}
		});
		
		// create controls
		Composite controls = getNewComposite(top);
		Button button = new Button(controls,SWT.PUSH|SWT.LEFT);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.setText(Messages.getString("DebugTab.2"));
		button.setToolTipText(Messages.getString("DebugTab.3"));
		button.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				IbyEntry entry = factory.createIbyEntry();
				ImageContent input = getInput();
				FileToImage file = new FileToImage();
				AddEditFileToTransferDialog dialog= new AddEditFileToTransferDialog(getControl().getShell(), file);
				dialog.setTitle(Messages.getString("FileTransferTab.8"));
				if (dialog.open() != Window.OK) {
					return;
				}
				entry.setEnabled(file.getEnabled());
				entry.setFile(file.getHostPath().substring(2));
				entry.setTarget(file.getTargetPath());
				input.getEntries().add(entry);
				tableViewer.refresh();
			}
			

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		button = new Button(controls,SWT.PUSH|SWT.LEFT);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.setText(Messages.getString("DebugTab.4"));
		button.setToolTipText(Messages.getString("DebugTab.5"));
		button.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				deleteSelection();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		button = new Button(controls,SWT.PUSH|SWT.LEFT);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.setText(Messages.getString("DebugTab.24"));
		button.setToolTipText(Messages.getString("DebugTab.25"));
		button.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				duplicateSelection();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		button = new Button(controls,SWT.PUSH|SWT.LEFT);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.setText(Messages.getString("DebugTab.6"));
		button.setToolTipText(Messages.getString("DebugTab.7"));
		button.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				ImageContent input = getInput();
				if(input!=null) {
					
					if (!input.getEntries().isEmpty()) {
						boolean confirm = MessageDialog
								.openQuestion(
										getControl().getShell(),
										"Remove Entries",
										"Clear the list before adding new entries, if any?");
						if (confirm) {
							input.getEntries().clear();							
						}
					}
					projectManager.populate(input);
					tableViewer.refresh();
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Composite enableButtons = new Composite(top,SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		enableButtons.setLayout(layout);
		enableButtons.setLayoutData(new GridData());
		
		button = new Button(enableButtons,SWT.PUSH|SWT.LEFT);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.setText(Messages.getString("DebugTab.8"));
		button.setToolTipText(Messages.getString("DebugTab.9"));
		button.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				ImageContent ic = getInput();
				if(ic!=null) {
					for (IbyEntry entry: ic.getEntries()) {
						entry.setEnabled(true);
					}
					tableViewer.refresh();
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		button = new Button(enableButtons,SWT.PUSH|SWT.LEFT);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.setText(Messages.getString("DebugTab.10"));
		button.setToolTipText(Messages.getString("DebugTab.11"));
		button.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				ImageContent ic = getInput();
				if(ic!=null) {
					for (IbyEntry entry: ic.getEntries()) {
						entry.setEnabled(false);
					}
					tableViewer.refresh();
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		tableViewer = new TableViewer(table);

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		tableViewer.setContentProvider(new DebugContentProvider());

		createColumns(tableViewer);
		
		tableViewer.getTable().addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent e) {
				if(e.character == SWT.DEL) {
					deleteSelection();
				}
			}
			
			public void keyPressed(KeyEvent e) {
			}
		});
		tableViewer.setInput(getDefaultInput());
		return top;	
	}

	private void setHelpForControl(Control container, String id) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(container, id);
	}
	private Object getDefaultInput() {
		IContentFactory factory = IContentFactory.eINSTANCE;
		ImageContent ic = factory.createImageContent();
		return ic;
	}

	private Composite getNewComposite(Composite top) {
		Composite comp = new Composite(top,SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		comp.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.grabExcessHorizontalSpace = true;
		comp.setLayoutData(layoutData);
		return comp;
	}

	private void createColumns(TableViewer viewer) {
		int columnSizes[] = {20,50,130,130,60,60};
		
		TableViewerColumn column = new TableViewerColumn(viewer,SWT.NONE);
		column.getColumn().setText(Messages.getString("DebugTab.12"));
		column.getColumn().setToolTipText(Messages.getString("DebugTab.13"));
		column.getColumn().setWidth(columnSizes[0]);
		column.setLabelProvider(new ColumnLabelProvider() {	
			
			@Override
			public Image getImage(Object element) {
				if(isWarning(element)) {
					return imageRegistry.get(WARNING_IMAGE);					
				} else if(isError(element)) {
					return imageRegistry.get(ERROR_IMAGE);
				} else {
					return null;
				}
			}

			private IbyEntry getEntry(Object element) {
				return (IbyEntry) element;
			}
			
			private boolean isWarning(Object element) {
				IbyEntry entry = getEntry(element);
				String location = " "+entry.getLocation().toString().toUpperCase()+" ";
				String targets[] = mainTab.getSelectedTargets();
				UIConfiguration pr = mainTab.getSelectedProduct();
				for (int i = 0; i < targets.length; i++) {
					String t = targets[i];
					UITarget target = pr.getTarget(t);
					if(target!=null&&target.getSteps()!=null) {
						int index = target.getSteps().indexOf(location);
						if(index!=-1) {
							return false;
						}
					}
				}
				if(entry.getLocation()!=IMAGESECTION.ANY) {
					mainTab.addTarget(entry.getLocation().getName());					
				}
				return false;
			}

			private boolean isError(Object element) {
				IbyEntry entry = getEntry(element);
				String path = projectManager.getRoot()+entry.getFile();
				File f = new File(path);
				if(!f.exists()) {
					String message = Messages.getString("DebugTab.1");
					entry.setStatusMessage(message.replace("xxx", f.getAbsolutePath()));
					return true;
				}
				return false;
			}
			
			
			@Override
			public String getText(Object element) {
				if(isWarning(element) || isError(element)) {
					return getEntry(element).getStatusMessage();
				}
				return "";
			}
			
		});
//		column.setEditingSupport(new CheckBoxEditingSupport(viewer, 0));
		
		column = new TableViewerColumn(viewer,SWT.CENTER);
		column.getColumn().setText(Messages.getString("DebugTab.14"));
		column.getColumn().setToolTipText(Messages.getString("DebugTab.15"));
		column.getColumn().setWidth(columnSizes[1]);
		column.setLabelProvider( new ColumnLabelProvider() {	
			
			@Override
			public Image getImage(Object element) {
				IbyEntry ie = (IbyEntry) element;
				String key = ie.isEnabled() ? CHECKED_IMAGE : UNCHECKED_IMAGE;
				return imageRegistry.get(key);
			}

			@Override
			public String getText(Object element) {
				return "";
			}
		});
		column.setEditingSupport(new CheckBoxEditingSupport(viewer, 1));
		
		column = new TableViewerColumn(viewer,SWT.NONE);
		column.getColumn().setText(Messages.getString("DebugTab.16"));
		column.getColumn().setToolTipText(Messages.getString("DebugTab.17"));
		column.getColumn().setWidth(columnSizes[2]);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IbyEntry e = (IbyEntry) element;
				return e.getFile();
			}
		});
		column.setEditingSupport(new TextEditingSupport(viewer,2));

		
		column = new TableViewerColumn(viewer,SWT.NONE);
		column.getColumn().setText(Messages.getString("DebugTab.18"));
		column.getColumn().setToolTipText(Messages.getString("DebugTab.19"));
		column.getColumn().setWidth(columnSizes[3]);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IbyEntry e = (IbyEntry) element;
				return e.getTarget();
			}
		});
		column.setEditingSupport(new TextEditingSupport(viewer,3));
		
		column = new TableViewerColumn(viewer,SWT.NONE);
		column.getColumn().setText(Messages.getString("DebugTab.20"));
		column.getColumn().setToolTipText(Messages.getString("DebugTab.21"));
		column.getColumn().setWidth(columnSizes[4]);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IbyEntry e = (IbyEntry) element;
				return e.getLocation().getLiteral();
			}
		});
		column.setEditingSupport(new ComboEditingSupport(viewer,4));
		
		column = new TableViewerColumn(viewer,SWT.LEFT);
		column.getColumn().setText(Messages.getString("DebugTab.22"));
		column.getColumn().setToolTipText(Messages.getString("DebugTab.23"));
		column.getColumn().setWidth(columnSizes[5]);
		column.setLabelProvider( new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IbyEntry e = (IbyEntry) element;
				return e.getAction().getLiteral();
			}
		});
		column.setEditingSupport(new ComboEditingSupport(viewer,5));		
	}
	
	
	private class DebugContentProvider  implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			ImageContent ic = (ImageContent) inputElement;
			return ic.getEntries().toArray();
		}

		public void dispose() {}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.viewers.IPropertyViewer#addToProperties(com.nokia.s60tools.imaker.internal.model.ImakerProperties)
	 */
	public void addToProperties(ImakerProperties prop) {
		ImageContent input = getInput();
		List<IbyEntry> ls = new ArrayList<IbyEntry>();
		ls.addAll(input.getEntries());
		prop.put(IMakerKeyConstants.DEBUGFILES, ls);			

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imaker.internal.viewers.IPropertyViewer#restoreFromProperties(com.nokia.s60tools.imaker.internal.model.ImakerProperties)
	 */
	public void restoreFromProperties(ImakerProperties prop) {
		ImageContent input = getInput();
		EList<IbyEntry> entries = input.getEntries();
		entries.clear();
		Object ents = prop.get(IMakerKeyConstants.DEBUGFILES);
		if(ents!=null) {
			List<IbyEntry> ls = (List<IbyEntry>) ents;
			entries.addAll(ls);
		}
		tableViewer.refresh();
	}

	private ImageContent getInput() {
		return (ImageContent) tableViewer.getInput();
	}

	public void refesh() {
		tableViewer.refresh();
	}

//	@Override
	public void clear() {
		getInput().getEntries().clear();
		refesh();
	}
}
