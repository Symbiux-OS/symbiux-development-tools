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
package com.nokia.s60tools.imaker.internal.actions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class EditorClearActionDelegate implements IEditorActionDelegate {

	public void setActiveEditor(IAction arg0, IEditorPart arg1) {}

	public void run(IAction action) {
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow aWindow = workbench.getActiveWorkbenchWindow();
				IWorkbenchPage aPage = aWindow.getActivePage();
				TextEditor activeEditor = (TextEditor)aPage.getActiveEditor();
				IDocumentProvider documentProvider = activeEditor.getDocumentProvider();
				IDocument document = documentProvider.getDocument(activeEditor.getEditorInput());
				document.set("");
				IEditorInput input = activeEditor.getEditorInput();
				IResource resource = (IResource)input.getAdapter(IResource.class);
				try {
					resource.clearHistory(null);
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
				if(resource!=null) {
					int depth = IResource.DEPTH_INFINITE;
					try {
						IMarker[] markers = resource.findMarkers(null, true, depth);
						ResourcesPlugin.getWorkspace().deleteMarkers(markers);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				activeEditor.doSave(null);
				try {
					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void selectionChanged(IAction action, ISelection selection) {}

}
