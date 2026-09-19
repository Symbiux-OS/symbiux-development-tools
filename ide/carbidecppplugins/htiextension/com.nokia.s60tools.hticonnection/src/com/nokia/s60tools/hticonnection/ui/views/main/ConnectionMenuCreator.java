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

package com.nokia.s60tools.hticonnection.ui.views.main;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.actions.NewConnectionAction;
import com.nokia.s60tools.hticonnection.actions.SelectConnectionAction;
import com.nokia.s60tools.hticonnection.connection.HTIService;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * This class creates menu from currently known HTI connections.
 */
public class ConnectionMenuCreator implements IMenuCreator {

	/**
	 * Manager used to create connection menu.
	 */
	MenuManager menuManager;

	/**
	 * Constructor.
	 */
	public ConnectionMenuCreator() {
		menuManager = new MenuManager();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuCreator#dispose()
	 */
	public void dispose() {
		if(menuManager != null) {
			menuManager.removeAll();
			menuManager.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
	 */
	public Menu getMenu(Control parent) {
		IConnection currentConn = HtiApiActivator.getPreferences().getCurrentConnection();
		Collection<IConnection> connections = getHtiConnections();
		
		// Cleaning old manager and actions.
		menuManager.removeAll();
		menuManager.dispose();
		menuManager = new MenuManager();
		
		// Adding new actions.
		
		menuManager.add(new NewConnectionAction());
		menuManager.add(new Separator());
		
		if(connections.size() > 0) {
			// Own action for each connection.
			for(IConnection connection : connections) {
				SelectConnectionAction action = new SelectConnectionAction(connection);
				menuManager.add(action);
				
				if(connection.equals(currentConn)) {
					action.setChecked(true);
				}
			}
		} else {
			// Action for informing that there are no connections.
			menuManager.add(new EmptyConnectionAction());
		}
		
		return menuManager.createContextMenu(parent);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
	 */
	public Menu getMenu(Menu parent) {
		// This method is not used. 
		return null;
	}

	/**
	 * Returns all HTI connections.
	 * @return HTI connections.
	 */
	private Collection<IConnection> getHtiConnections() {
		// Getting needed variables.
		Collection<IConnection> allConns = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		ArrayList<IConnection> htiConns = new ArrayList<IConnection>();
		
		// Getting HTI connections.
		for(IConnection conn : allConns) {
			if(HTIService.getCompatibleConnectionTypeIds().contains(conn.getConnectionType().getIdentifier())) {
				htiConns.add(conn);
			}
		}
		
		return htiConns;
	}
	
	/**
	 * Empty action, that is used when there are know connections.
	 */
	private class EmptyConnectionAction extends Action {
		/**
		 * Constructor.
		 */
		public EmptyConnectionAction() {
			super(Messages.getString("ConnectionMenuCreator.NoConnection_Action_Text")); //$NON-NLS-1$
			this.setEnabled(false);
		}
	}
}
