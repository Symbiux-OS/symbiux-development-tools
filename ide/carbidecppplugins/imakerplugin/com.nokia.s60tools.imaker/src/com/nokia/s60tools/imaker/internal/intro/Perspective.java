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

package com.nokia.s60tools.imaker.internal.intro;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		createActions(layout);
		createViews(layout);
	}

	private void createViews(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		String editorArea = layout.getEditorArea();
		
		IFolderLayout views = layout.createFolder("views", IPageLayout.LEFT, 0.25f,
			    editorArea);
		views.addView("com.nokia.s60tools.imakerplugin.views.PreferencesView");
		views.addView("com.nokia.s60tools.imakerplugin.views.SettingView");

	}

	private void createActions(IPageLayout layout) {
		
	}
}
