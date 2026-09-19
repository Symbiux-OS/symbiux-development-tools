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

package com.nokia.s60tools.hticonnection.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.ISelectedConnectionInfo;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatusChangedListener;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus.EConnectionStatus;
import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.actions.OpenPreferencePageAction;
import com.nokia.s60tools.hticonnection.connection.HTIConnectedService;
import com.nokia.s60tools.hticonnection.connection.HTIConnectionStatus;
import com.nokia.s60tools.hticonnection.connection.HTIService;
import com.nokia.s60tools.hticonnection.gateway.DataGatewayManager;
import com.nokia.s60tools.hticonnection.listener.HtiConnectionManager;
import com.nokia.s60tools.hticonnection.preferences.HtiApiPreferenceConstants;
import com.nokia.s60tools.hticonnection.preferences.HtiApiPreferencePage;
import com.nokia.s60tools.hticonnection.preferences.HtiApiPreferences;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.services.HTIVersion;
import com.nokia.s60tools.hticonnection.ui.dialogs.ErrorDialogWithHelp;
import com.nokia.s60tools.hticonnection.ui.dialogs.HtiApiMessageBox;
import com.nokia.s60tools.hticonnection.ui.views.main.MainView;
import com.nokia.s60tools.util.cmdline.UnsupportedOSException;

/**
 * This class manages current connection and holds it status information.
 */
public class HtiConnection {

	/**
	 * Status of current connection. Used to device if new requests should
	 * be accepted. Connection is in shutdown status when plug-in is started.
	 */
	private ConnectionStatus currentStatus = ConnectionStatus.SHUTDOWN;
	
	/**
	 * Thread is used to test connection when connection has been started.
	 */
	private ConnectionCheckerThread connectionChecker = null;
	
	/**
	 * Instance of this singleton class.
	 */
	private static HtiConnection instance = null;

	/**
	 * HTI version to which HTI Connection is currently connected.
	 */
	private HTIVersion version = null;
	
	/**
	 * Currently used connection.
	 */
	private IConnection currentConnection = null;
	
	/**
	 * Stores instance of data gateway, that is used for communication between HTI API and device.
	 */
	private DataGatewayManager gatewayManager = null;
	
	/**
	 * Listener for listening changes in connections.
	 */
	private IConnectionListener connectionListener = null;
	
	/**
	 * Listener for listening changes in current connection status.
	 */
	private IConnectionStatusChangedListener currentConnectionStatusListener = null;
	
	/**
	 * To point right connection when IConnectionStatusChangedListener is added and removed.
	 */
	private IConnection currentlyOpenConnection = null;
	
	/**
	 * Enumeration for status of the current connection.
	 */
	public enum ConnectionStatus {
		SHUTDOWN, // Connection has been shut down.
		TESTING,  // Testing connection. Used when testing connection from preferences.
		CONNECTING, //  Connection is just started or there have been problems in connection.
		CONNECTED // Connection is working.
	};
	
	/**
	 * Private constructor to prevent creating new instances.
	 */
	private HtiConnection(){
		gatewayManager = new DataGatewayManager();
	}
	
	/**
	 * Only one instance can exist at one time.
	 * @return Current instance.
	 */
	public static synchronized HtiConnection getInstance(){
		if( instance == null ){
			instance = new HtiConnection();
		}
		return instance;
	}
	
	/**
	 * Initializes listeners and settings.
	 */
	public void init() {
		// Add listeners
		// Create "current connection" listener
		connectionListener = new ConnectionChangedListener();
		RemoteConnectionsActivator.getConnectionsManager().addConnectionListener(connectionListener);
		
		currentConnection = HtiApiActivator.getPreferences().getCurrentConnection();
	}
	
	/**
	 * Stops thread that checks if connection is up.
	 */
	public void stop() {
		setConnectionStatus(ConnectionStatus.SHUTDOWN);
		// Remove listeners
		RemoteConnectionsActivator.getConnectionsManager().removeConnectionListener(connectionListener);
		
		connectionChecker = null;
		
		if(gatewayManager != null){
			stopConnection();
		}
	}
	
	/**
	 * Starts connection with current settings.
	 * @param isTesting True if connection is started only to test connection settings.
	 * @return True if connection was started successfully. False otherwise.
	 */
	public boolean startConnection(boolean isTesting) {
		IConnection connection = getCurrentConnection(); 
		if(gatewayManager.isReady()) {
			// Old gateway needs to be stopped before starting new.
			stopConnection();
		}
		
		if(connection == null) {
			String msg;
			List<IConnection> htiConns = getHTIConnections();
			if(htiConns.size() > 0) {
				msg = new String(Messages.getString("HtiConnection.ConnectionNotSelected_ErrMsg")); //$NON-NLS-1$
			} else {
				msg = new String(Messages.getString("HtiConnection.NoConnections_ErrMsg")); //$NON-NLS-1$
			}
			
			ErrorDialogRunnable runnable = new ErrorDialogRunnable(msg);
			Display.getDefault().asyncExec(runnable);
			return false;
		}
		
		// Connection status needs to be set before starting gateway, so that connection is set to use.
		ConnectionStatus status = (isTesting) ? ConnectionStatus.TESTING : ConnectionStatus.CONNECTING;
		setConnectionStatus(status);
		
		boolean result = startGateway(connection, isTesting);
		
		if (result) {
			currentlyOpenConnection = connection;
			// Add status listener to the connection
			if (currentlyOpenConnection instanceof IConnection2) {
				currentConnectionStatusListener = new ConnectionStatusListener();
				((IConnection2) currentlyOpenConnection)
						.addStatusChangedListener(currentConnectionStatusListener);
			}
		}
		
		return result;
	}
	
	/**
	 * Starts datagateway with current settings.
	 * @param isTesting True if datagateway is started only to test connection settings.
	 * @return True if datagateway was started successfully. False otherwise.
	 */
	private boolean startGateway(IConnection connection, boolean isTesting) {
		try {
			boolean started = false;
			// Connection status needs to be checked here, because setting connection status can take a while,
			// when connection is set in to use. User could have stopped the connection during that time.
			if(getConnectionStatus() != ConnectionStatus.SHUTDOWN) {
				// Connection string needs to be updated, before starting connection.
				// Otherwise old information might be shown.
				started = gatewayManager.startGateway(connection, isTesting);
			}
			
			if(started && !isTesting) {
				// Connection needs to be tested after datagateway has been started.
				connectionChecker = new ConnectionCheckerThread();
				connectionChecker.start();
			}
			else if(!started) {
				setConnectionStatus(ConnectionStatus.SHUTDOWN);
			}
			
			return started;
		
		} catch (UnsupportedOSException e) {
			Runnable runnable = new Runnable() {
				public void run() {
					// Only Windows operating system is supported.
					String errMsg = Messages.getString("HtiConnection.FailedToStartGateway_ErrMsg"); //$NON-NLS-1$
					HtiApiMessageBox msgBox = new HtiApiMessageBox(errMsg, SWT.ICON_ERROR | SWT.OK);
					msgBox.open();
				}
				
			};
			// Messagebox needs to be opened from UI thread.
			Display.getDefault().asyncExec(runnable);
			
			return false;
		}
	}
	
	/**
	 * Stops datagateway.
	 */
	public void stopConnection() {
		setConnectionStatus(ConnectionStatus.SHUTDOWN);
		gatewayManager.stopGateway();
		
		// Remove status listener from the connection
		if ((currentlyOpenConnection instanceof IConnection2) &&
			(currentConnectionStatusListener != null)) {
			((IConnection2) currentlyOpenConnection)
					.removeStatusChangedListener(currentConnectionStatusListener);
		}
		currentlyOpenConnection = null;
	}
	
	/**
	 * Tests given connection. Shuts down existing connection.
	 * This method is synchronized, because there can come multiple test
	 * request at the same time from Remote Connections.
	 */
	public synchronized IStatus testConnection(IConnection testConnection) {
		
		IConnection origConn = getCurrentConnection();
		
		EStatus testStatus = EStatus.UNKNOWN;
		String shortDesc = Messages.getString("HtiConnection.NotConnected_StatusMsg"); //$NON-NLS-1$
		String longDesc = Messages.getString("HtiConnection.ConnectionFailed_StatusMsg"); //$NON-NLS-1$
		
		try {
			// Existing gateway needs to be stopped.
			stopConnection();
			
			// New connection should be defined.
			setCurrentConnection(testConnection, true);
			
			// Gateway can be started now.
			if(startConnection(true)) {
				if(RequestQueueManager.getInstance().testConnection()) {
					HTIVersion version = getHTIVersion();
					testStatus = EStatus.UP;
					shortDesc = Messages.getString("HtiConnection.Connected_StatusMsg"); //$NON-NLS-1$
					longDesc = Messages.getString("HtiConnection.Connected_LongStatusMsg") + version.toString(); //$NON-NLS-1$
				} else {
					testStatus = EStatus.DOWN;
					shortDesc = Messages.getString("HtiConnection.NotConnected_StatusMsg"); //$NON-NLS-1$
					longDesc = Messages.getString("HtiConnection.ConnectionFailed_StatusMsg"); //$NON-NLS-1$
				}
			} else {
				testStatus = EStatus.DOWN;
				shortDesc = Messages.getString("HtiConnection.NotConnected_StatusMsg"); //$NON-NLS-1$
				longDesc = Messages.getString("HtiConnection.FailedToStartGateway_StatusMsg"); //$NON-NLS-1$
			}
			
		} catch (Exception e) {
			if (e.getMessage().equals("com.nokia.HTI.HTIException: HTI NOT INITIALIZED")) { //$NON-NLS-1$
				// HTI agent is not responding
				testStatus = EStatus.DOWN;
				shortDesc = Messages.getString("HtiConnection.NotConnected_StatusMsg"); //$NON-NLS-1$
				longDesc = Messages.getString("HtiConnection.HtiNotResponding_StatusMsg"); //$NON-NLS-1$
			} else {
				// Connection could not be created
				testStatus = EStatus.DOWN;
				shortDesc = Messages.getString("HtiConnection.NotConnected_StatusMsg"); //$NON-NLS-1$
				longDesc = Messages.getString("HtiConnection.ConnectionFailed_StatusMsg"); //$NON-NLS-1$
			}
		} finally {
			stopConnection();
			setCurrentConnection(origConn, false);
		}
		
		return new HTIConnectionStatus(null, testStatus, shortDesc, longDesc);
	}
	
	/**
	 * Set HTI version to which HTI Connection is currently connected.
	 * @param version HTI version to which HTI Connection is currently connected.
	 */
	public void setHTIVersion(HTIVersion version) {
		this.version = version;
	}
	
	/**
	 * Get HTI version to which HTI Connection is currently connected.
	 * @return HTI version to which HTI Connection is currently connected,
	 * or null if connection is down.
	 */
	public HTIVersion getHTIVersion() {
		return version;
	}
	
	/**
	 * Returns all connections which support connection types that HTI Connection can use. 
	 * @return List of connections that support connection types that HTI Connection can use.
	 */
	public List<IConnection> getHTIConnections() {
		
		// Getting needed variables.
		Collection<IConnection> connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		Collection<String> connectionTypes = HTIService.getCompatibleConnectionTypeIds();
		List<IConnection> htiConnections = new ArrayList<IConnection>();
		
		// Going through all connection.
		for(IConnection conn : connections) {	
			if(connectionTypes.contains(conn.getConnectionType().getIdentifier())) {
				htiConnections.add(conn);
			}
		}
		
		return htiConnections;
	}
	
	/**
	 * Returns current status of connection.
	 * @return Current status of connection.
	 */
	public ConnectionStatus getConnectionStatus() {
		synchronized(currentStatus) {
			return currentStatus;
		}
	}

	/**
	 * Updates connection status in views description.
	 */
	public void updateConnectionStatus() {
		Runnable updater = new ConnectionStatusUpdater(getCurrentConnection());
		// Updating connection status asynchronously in default UI thread.
		Display.getDefault().asyncExec(updater);
	}
	
	/**
	 * Sets current status of connection.
	 * @param currentStatus The currentStatus to set.
	 */
	public void setConnectionStatus(ConnectionStatus newStatus) {
		// Flagging status change information.
		boolean connectionStarted = false;
		boolean connectionTerminated = false;
		boolean connectionUp = false;
		boolean connectionDown = false;
		boolean needsUpdate = false;
		
		// Checking new status and updating current status.
		synchronized(currentStatus) {
			if (newStatus == ConnectionStatus.SHUTDOWN
					&& currentStatus != ConnectionStatus.SHUTDOWN) {
				// Shutting down the connection and connection tester.
				connectionChecker = null;
				connectionDown = true;
			}
			else if (newStatus == ConnectionStatus.CONNECTED
					&& currentStatus != ConnectionStatus.CONNECTED) {
				connectionUp = true;
			}
			else if (newStatus != ConnectionStatus.CONNECTED
					&& currentStatus == ConnectionStatus.CONNECTED) {
				connectionDown = true;
			}
			
			// Checking if it is needed to start/stop using current connection.
			if (newStatus != ConnectionStatus.SHUTDOWN
					&& currentStatus == ConnectionStatus.SHUTDOWN) {
				connectionStarted = true;
			}
			else if (newStatus == ConnectionStatus.SHUTDOWN
					&& currentStatus != ConnectionStatus.SHUTDOWN) {
				connectionTerminated = true;
			}
			
			needsUpdate = (currentStatus != newStatus);
			currentStatus = newStatus;
		} // End synchronized
		
		// Updating and informing outside of the synchronized block to prevent
		// synchronization slowing down connection status queries.
		
		if(needsUpdate) {
			updateConnectionStatus();
		}
		
		if(connectionUp) {
			// Inform listeners of starting the connection
			HtiConnectionManager.getInstance().informConnectionStarted();
		}
		else if(connectionDown) {
			// Inform listeners of connection termination
			HtiConnectionManager.getInstance().informConnectionTerminated();
			setHTIVersion(null);
			RequestQueueManager.getInstance().cancelRequestsInQueue();
		}
		
		if(connectionStarted) {
			// Connection needs to be set as used, before starting datagateway.
			// This must be done as last operation, because useConnection can take some time and
			// it is good to update other components first to prevent information lagging behind.
			IConnection conn = getCurrentConnection();
			if(conn != null) {
				conn.useConnection(true);
			}
		}
		else if(connectionTerminated) {
			// Connection is not needed anymore.
			IConnection conn = getCurrentConnection();
			if(conn != null) {
				conn.useConnection(false);
			}
		}
	}
	
	/**
	 * Shows message to user if exception is caused by communication problems.
	 * This method also changes connection state to CONNECTNG, so service won't accept further requests.
	 * @param exception Exception to be checked and reported.
	 * @return Returns true if error was reported and connection is set to CONNECTING state, false otherwise.
	 */
	public boolean reportConnectionError(ExecutionException exception) {
		
		String msg;
		
		// Checking if there are problems with connection and handling problems.
		if (exception.getMessage().equals("com.nokia.HTI.HTIException: HTI NOT INITIALIZED")) { //$NON-NLS-1$
			msg = Messages.getString("HtiConnection.HtiNotResponding_MsgBoxMsg"); //$NON-NLS-1$
		}
		else if( exception.getMessage().equals("java.net.ConnectException: Connection refused: connect")) { //$NON-NLS-1$
			msg = Messages.getString("HtiConnection.ConnectionFailed_MsgBoxMsg"); //$NON-NLS-1$
		}
		else {
			// HTI was initialized correctly and connection was established.
			// So this shouldn't be connection issue and no need to change to CONNECTING state.
			return false;
		}
		
		if(HtiConnection.getInstance().getConnectionStatus() != ConnectionStatus.SHUTDOWN) {
			// Setting connection status to testing state here to prevent extra error messages.
			HtiConnection.getInstance().setConnectionStatus(ConnectionStatus.CONNECTING);
		}
		
		ErrorDialogRunnable runnable = new ErrorDialogRunnable(msg);
		Display.getDefault().asyncExec(runnable);
		return true;
	}
	
	/**
	 * Sets currently used connection. Saves connection to preferences, if it isn't a test connection.
	 * @param currentConnection Connection to be set.
	 * @param isTestConnection True if connection is set for test purposes. False otherwise.
	 */
	public synchronized void setCurrentConnection(IConnection connection, boolean isTestConnection) {
		if(currentConnection != null && currentConnection != connection) {
			// Connection can be set as not used, because HTI Connection uses only currentConnection.
			currentConnection.useConnection(false);
		}
		this.currentConnection = connection;
		
		if(!isTestConnection) {
			// ID of normal connection need to be saved to preferences so that it will be used later as default.
			String connectionID = (currentConnection == null) ? HtiApiPreferenceConstants.DEFAULT_CONNECTION_ID 
					: currentConnection.getIdentifier();
			// Do not change default if it is "current connection".
			if (!HtiApiActivator.getPreferences().getConnectionID().equals(HtiApiPreferences.SELECTION_ID_CURRENT) &&
				!connectionID.equals(HtiApiActivator.getPreferences().getConnectionID())) {
				HtiApiActivator.getPreferences().setConnectionID(connectionID);
			}
		}
	}
	
	/**
	 * Gets currently used connection.
	 * @return Currently used connection, or <code>null</code> if connection has not been set.
	 */
	public synchronized IConnection getCurrentConnection() {
		return currentConnection;
	}

	/**
	 * Call when connection changes.
	 * @param newConnection new connection
	 * @throws CoreException
	 */
	public void changeConnection(String newConnection) throws CoreException {
			
		String currentConnectionId = null;
		if (getCurrentConnection() != null) currentConnectionId = getCurrentConnection().getIdentifier();
		boolean isConnected = getConnectionStatus() != ConnectionStatus.SHUTDOWN;
		boolean isNewConnection = (newConnection != null) && !(newConnection.equals(currentConnectionId)); 
		
		// Ensure that connection is ok.
		IService service = RemoteConnectionsActivator.getConnectionTypeProvider().findServiceByID(HTIService.ID);
		ISelectedConnectionInfo connectionInfo;
		connectionInfo = RemoteConnectionsActivator.getConnectionsManager().ensureConnection(newConnection, service);
		IConnection connection = connectionInfo.getConnection();
		
		if (isConnected && isNewConnection) {
			// Connection has been changed when connection is started.
			restartDataGateway(connection, false);
		}
		else if(!isConnected || currentConnection == null) {
			// Connection isn't active or connections have been deleted. New connection can be set. 
			setCurrentConnection(connection, false);
		}
	}
	
	/**
	 * Call when connection changes.
	 * @param newConnection new connection
	 */
	public void changeConnection(IConnection newConnection) {
		boolean isConnected = getConnectionStatus() != ConnectionStatus.SHUTDOWN;
		boolean isNewConnection = 
			(newConnection != null) && 
			!(newConnection.equals(getCurrentConnection())); 
		
		if (isConnected && isNewConnection) {
			// Connection has been changed when connection is started.
			restartDataGateway(newConnection, false);
		}
		else if(!isConnected || currentConnection == null) {
			// Connection isn't active or connections have been deleted. New connection can be set. 
			setCurrentConnection(newConnection, false);
		}
	}
	
	/**
	 * Restarts Datagateway with settings from preference store.
	 * @param isTesting True if testing connection and not trying to establish permanent connection.
	 */
	public boolean restartDataGateway(IConnection conn, boolean isTesting) {
		stopConnection();
		setCurrentConnection(conn, false);
		return startConnection(isTesting);
	}
	
	//
	// Private classes.
	//
	
	/**
	 * Listener for listening changes in current connection status.
	 */
	private class ConnectionStatusListener implements IConnectionStatusChangedListener {
		
		public void statusChanged(IConnectionStatus status) {		
			// Stop connection when current connection device is removed.
			if (status.getEConnectionStatus().equals(EConnectionStatus.IN_USE_DISCONNECTED)) {
				stopConnection();
				setCurrentConnection(null, false);
			}
		}
	}
	
	/**
	 * Listener for listening changes in connections.
	 */
	private class ConnectionChangedListener implements IConnectionListener {

		public void connectionAdded(IConnection connection) {
			// Do nothing.
		}

		public void connectionRemoved(IConnection connection) {
			// Initializing needed variables.
			IConnection currConn = getCurrentConnection();
			if(currConn == null) {
				// Nothing to do, if no connection is selected.
				return;
			}
			
			// Checking if current connection was the removed one.
			if (currConn.getIdentifier().equals(connection.getIdentifier())) {
				//If so, remove it and stop connection.
				stopConnection();
				setCurrentConnection(null, false);
			}		
		}

		public void currentConnectionSet(IConnection connection) {
			if (HtiApiActivator.getPreferences().getConnectionID().equals(HtiApiPreferences.SELECTION_ID_CURRENT)) {
				changeConnection(connection);
			}
		}
		
	};
	
	/**
	 * Thread that tests connection.
	 * Connection needs to be tested when new connection has been started
	 * or there has been problems with current connection.
	 */
	private class ConnectionCheckerThread extends Thread {
		
		/**
		 * Time in ms that is waited between testing the connection.
		 */
		private static final long TEST_DELAY = 1000;
		
		/**
		 * Keeps track for first connection try. This is needed if there
		 * happens connection error when testing connection first time.
		 * On other times connection failure information comes from outside
		 * of this class.
		 */
		private boolean firstConnection = true;
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			Thread thisThread = Thread.currentThread();
			while(thisThread == connectionChecker) {
				
				if(getConnectionStatus() == ConnectionStatus.CONNECTING) {
					testConnection();
				}
				
				try {
					Thread.sleep(TEST_DELAY);
				} catch (InterruptedException e) {
					// Not needed to handle. Just continuing polling in this case.
				}
			}
		}
		
		/**
		 * Tests if current connection works and modifies connection status
		 * in HtiApiActivator class if needed.
		 */
		private void testConnection() {
			try {
				boolean connectionUp = RequestQueueManager.getInstance().testConnection();
				
				if(connectionUp) {
					setConnectionStatus(ConnectionStatus.CONNECTED);
				}
				
			} catch (ExecutionException e) {
				if(firstConnection && getConnectionStatus() != ConnectionStatus.SHUTDOWN) {
					// Other than first connection errors are reported from RequestQueueManager.
					// This handles only first connection problem when trying to connect to the device.
					reportConnectionError(e);
				}
			} catch (Exception e) {
				// Catching other errors that doesn't need any specific actions.
			}
			
			firstConnection = false;
		}
	}
	
	/**
	 * Updates connection status and buttons in UI.
	 */
	private class ConnectionStatusUpdater implements Runnable {

		/**
		 * Connection that needs status update.
		 */
		private final IConnection connectionToUpdate;

		/**
		 * Constructor.
		 * @param connectionToUpdate Connection that needs status update.
		 */
		public ConnectionStatusUpdater(IConnection connectionToUpdate) {
			this.connectionToUpdate = connectionToUpdate;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			
			// Connection status needs to be updated in connected service.
			if(connectionToUpdate != null) {
				Collection<IConnectedService> services = RemoteConnectionsActivator.getConnectionsManager().getConnectedServices(connectionToUpdate);
				// Collection can be null if getting services for test connection.
				if(services != null) {
					for(IConnectedService service : services) {
						if(service instanceof HTIConnectedService) {
							((HTIConnectedService)service).refreshStatus();
						}
					}
				}
			}
			
			try {
				MainView view = MainView.getViewInstance(false);
				view.updateActionButtonStates();
				
				// Updating connection description.
				String displayName = "";
				if(connectionToUpdate != null){
					displayName = connectionToUpdate.getDisplayName();
				}
				
				switch (getConnectionStatus()) {
				case CONNECTED:
					view.updateDescription(Messages.getString("HtiConnection.Connected_ToolBar_Msg") + displayName); //$NON-NLS-1$
					break;
				case CONNECTING:
					view.updateDescription(Messages.getString("HtiConnection.Connecting_ToolBar_Msg") + displayName); //$NON-NLS-1$
					break;
				case SHUTDOWN:
					view.updateDescription(Messages.getString("HtiConnection.NotConnected_ToolBar_Msg")); //$NON-NLS-1$
					break;
				case TESTING:
					view.updateDescription(Messages.getString("HtiConnection.Testing_ToolBar_Msg")); //$NON-NLS-1$
					break;
				}
			} catch (PartInitException e) {
				// No need to handle. Description doesn't need update if part is not visible.
			}
		}
	}
	
	/**
	 * Runnable that opens error message box.
	 */
	private class ErrorDialogRunnable implements Runnable {

		/**
		 * Error message.
		 */
		private final String errorMessage;

		/**
		 * Constructor.
		 * @param errorMessage Message to be shown in dialog.
		 */
		public ErrorDialogRunnable(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			
			// No need to show error message if preferences page is already created.
			if(HtiApiPreferencePage.isCreated()) {
				// Showing plain error message if preferences page is open.
				ErrorDialogWithHelp msgBox = new ErrorDialogWithHelp(errorMessage, SWT.OK);
				msgBox.open();
			} else {
				// Asking if user wants to manage preferences.
				String msgWithQuestionStr = errorMessage + Messages.getString("HtiConnection.ManageConnections_ErrMsg"); //$NON-NLS-1$
				
				ErrorDialogWithHelp msgBox = new ErrorDialogWithHelp(msgWithQuestionStr, SWT.YES | SWT.NO);
				int result = msgBox.open();
				if(result == SWT.YES && !HtiApiPreferencePage.isCreated()){
					// Opening preferences if Yes is selected.
					OpenPreferencePageAction openPreferencesAction = new OpenPreferencePageAction();
					openPreferencesAction.run();
				}
			}
		}
	}
}
