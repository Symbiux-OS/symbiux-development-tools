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


package com.nokia.s60tools.remotecontrol.screen.ui.view;

import java.io.ByteArrayInputStream;

import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.services.HTIScreenMode;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.IScreenCaptureService;
import com.nokia.s60tools.hticonnection.services.ScreenCaptureData;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.actions.OpenPreferencePageAction;
import com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferencePage;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.screen.ui.actions.OpenKeyboardAction;
import com.nokia.s60tools.remotecontrol.screen.ui.actions.SaveMultiScreenshotsAction;
import com.nokia.s60tools.remotecontrol.screen.ui.actions.SaveSingleScreenshotAction;
import com.nokia.s60tools.remotecontrol.screen.ui.actions.ZoomAction;
import com.nokia.s60tools.remotecontrol.screen.ui.actions.ZoomAction.ZoomFactor;
import com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite;
import com.nokia.s60tools.remotecontrol.ui.views.main.MainView;
import com.nokia.s60tools.remotecontrol.util.ImageHelper;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * This class comprises the screen view of the Remote Control
 * application.
 */ 
public class ScreenView extends AbstractUiFractionComposite implements PaintListener {
	 
	/**
	 * We can get view ID at runtime once the view is instantiated, but we
	 * also need static access to ID in order to be able to invoke the view.
	 */
	public static final String ID = "com.nokia.s60tools.remotecontrol.screen.ui.view.ScreenView"; //$NON-NLS-1$
		
	//
	// Actions
	//	
	
	/**
	 * Open preferences action
	 */
	private static Action preferencesAction;
	
	/**
	 * Save single screenshot action
	 */
	private static Action saveSingleScreenshotAction;
	
	/**
	 * Save multiple sequential screenshots action
	 */
	private static SaveMultiScreenshotsAction saveMultiScreenshotsAction;
	
	/**
	 * Show keyboard view action
	 */
	private static Action keyboardAction;
	
	//
	// Members.
	//
	
	/**
	 * Screen canvas
	 */ 
	private Canvas screenCanvas;
	
	/**
	 * Default scroll bars for screen canvas.
	 */
	private ScrolledComposite screenScrollBarDefault;
	
	/**
	 * Scroll bars for screen canvas.
	 * Used with ZOOM_TO_WIDTH zoom factor.
	 */
	private ScrolledComposite screenScrollBarVertical;

	/**
	 * Scroll bars for screen canvas. 
	 * Used with ZOOM_TO_HEIGHT zoom factor.
	 */
	private ScrolledComposite screenScrollBarHorizontal;

	/**
	 * Layout used to change between different scroll bars.
	 */
	private StackLayout scrollBarLayout;
		
	/**
	 * Thread for capturing screen
	 */
	private volatile Thread screenCaptureThread;

	/**
	 * Thread for saving screenshots.
	 */
	private ScreenSaverThread screenSaverThread;

	/**
	 * Main view
	 */
	private final MainView mainView;
	
	/**
	 * Property change listener
	 */
	private IPropertyChangeListener listener = null;
	
	/**
	 * Listener for resize events.
	 */
	private ControlListener resizeListener = null;
	
	/**
	 * Touch screen handler
	 */
	private TouchScreenHandler touchScreenHandler = null;
	
	/**
	 * Keyboard mediator to delegate events to.
	 */
	private IKeyboardMediator keyboardMediator;
	
	/**
	 * Instance of this class. Used for sending key events
	 * to keyboard mediator.
	 */
	private ScreenView instance;
	
	/**
	 * Previously captured image data from the device screen.
	 */
	byte[] previousImageData;
	
	//
	// Constants
	//
	
	/**
	 * Screen is captured in png format.
	 */
	private static final String IMAGE_TYPE = "image/png"; //$NON-NLS-1$

	/**
	 * Amount that scroll bars are incremented by default.
	 */
	private static final int DEFAULT_SCROLL_INCREMENT = 100;

	/**
	 * Settings for current screen.
	 */
	private ScreenSettings screenSettings;
	
	/**
	 * The constructor.
	 * @param parentComposite Parent
	 * @param style Style bits
	 * @param mainView MainView
	 */
	public ScreenView(Composite parentComposite, int style, MainView mainView) {
		super(parentComposite, style);
		this.mainView = mainView;
		this.instance = this;
		screenSettings = new ScreenSettings();
		screenSettings.getPreferences();
		createPropertyChangeListener();
		// Dummy screen mode, so that valid value is returned before first screen capture.
		screenSettings.setScreenMode(new HTIScreenMode(0, 0, 0, 0, 0, 0));
		
		// Starting thread to capture screens from the device.
		screenSaverThread = new ScreenSaverThread(this);
		screenSaverThread.start();
		screenCaptureThread = new screenCaptureThread();
		screenCaptureThread.start();
		
		// Action button states needs to be updated after all resources have been created.
		updateActionButtonStates();
		createResizeListener();
		
		// Register to keyboard mediator
		keyboardMediator = RemoteControlActivator.getKeyboardMediator();
		keyboardMediator.registerKeyboardMediatorClient(this);
	}
	
	/**
	 * Create listener to listen screen resize messages. 
	 */
	private void createResizeListener() {
		
		resizeListener = new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				// ScreenCanvas needs to be redrawn to keep screen zoomed.
				screenCanvas.redraw();
			}
		};
		
		addControlListener(resizeListener);
	}
	
	/**
	 * Create change listener to prefstore preferences 
	 */
	private void createPropertyChangeListener() {
		
		// Call getPreferences() when prefstore values are changed
		listener =
			   new IPropertyChangeListener() {
			      public void propertyChange(PropertyChangeEvent event) {
			    	  screenSettings.getPreferences();
			      }
			   };
			   
			   RCPreferences.addPropertyChangeListener(listener);
	}
	
	/**
	 * Create main menu actions in here. The same actions
	 * can be also used to populate context menus, if needed.
	 */
	private void createMainMenuActions() {
		preferencesAction = new OpenPreferencePageAction(Messages.getString("ScreenView.Open_Preferences_Action"),  //$NON-NLS-1$
				Messages.getString("ScreenView.Open_Preferences_Tooltip"), IAction.AS_PUSH_BUTTON,  //$NON-NLS-1$
				ImageKeys.IMG_PREFERENCES, RCPreferencePage.Tabs.SCREENCAPTURE);
		saveMultiScreenshotsAction = new SaveMultiScreenshotsAction(this);
		saveSingleScreenshotAction = new SaveSingleScreenshotAction(Messages.getString("ScreenView.Save_Screenshot_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Save_Screenshot_Action_Tooltip"), this);  //$NON-NLS-1$
		keyboardAction = new OpenKeyboardAction(Messages.getString("ScreenView.Open_Keyboard_Action"), Messages.getString("ScreenView.Open_Keyboard_Action_Tooltip"));  //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * This method fills action bars (no need for further modifications).
	 */
	protected void fillViewActionBars() {
		IActionBars bars = mainView.getActionBars();
		fillViewMainMenu(bars.getMenuManager());
		fillViewToolBar(bars.getToolBarManager());
	}

	/**
	 * View's main menu is populated in here.
	 * @param manager Menu manager instance.
	 */
	private void fillViewMainMenu(IMenuManager manager) {
		manager.removeAll();
		manager.add(saveMultiScreenshotsAction);
		manager.add(saveSingleScreenshotAction);
		manager.add(keyboardAction);
		manager.add(preferencesAction);		
		manager.update(true);
	}

	/**
	 * View's tool bar is populated in here.
	 * @param manager Tool bar instance.
	 */
	private void fillViewToolBar(IToolBarManager manager) {
		manager.removeAll();
		manager.add(saveMultiScreenshotsAction);
		manager.add(saveSingleScreenshotAction);
		manager.add(preferencesAction);
		manager.update(true);
	}

	/**
	 * Hooks context menu to the current view.
	 */
	protected void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ScreenView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(screenCanvas);
		screenCanvas.setMenu(menu);
		screenScrollBarDefault.setMenu(menu);
		screenScrollBarHorizontal.setMenu(menu);
		screenScrollBarVertical.setMenu(menu);
		mainView.registerContextMenu(menuMgr, 
				new ISelectionProvider() {
						public void addSelectionChangedListener(ISelectionChangedListener listener) {
						}
						public ISelection getSelection() {
							return new StructuredSelection(screenCanvas);
						}
						public void removeSelectionChangedListener(ISelectionChangedListener listener) {
						}
						public void setSelection(ISelection selection) {
						}
					}
				);
	}

	/**
	 * Fills context menu.
	 * @param manager used to fill menu.
	 */
	private void fillContextMenu(IMenuManager manager) {
		manager.add(new ZoomAction(this, Messages.getString("ScreenView.Zoom_To_Fit_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Zoom_To_Fit_Tooltip"), ZoomFactor.ZOOM_TO_FIT)); //$NON-NLS-1$
		manager.add(new ZoomAction(this, Messages.getString("ScreenView.Zoom_To_Height_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Zoom_To_Height_Tooltip"), ZoomFactor.ZOOM_TO_HEIGHT)); //$NON-NLS-1$
		manager.add(new ZoomAction(this, Messages.getString("ScreenView.Zoom_To_Width_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Zoom_To_Width_Tooltip"), ZoomFactor.ZOOM_TO_WIDTH)); //$NON-NLS-1$
		manager.add(new ZoomAction(this, Messages.getString("ScreenView.Zoom_50%_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Zoom_50%_Tooltip"), ZoomFactor.ZOOM_TO_50_PERCENT)); //$NON-NLS-1$
		manager.add(new ZoomAction(this, Messages.getString("ScreenView.Zoom_100%_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Zoom_100%_Tooltip"), ZoomFactor.ZOOM_TO_100_PERCENT)); //$NON-NLS-1$
		manager.add(new ZoomAction(this, Messages.getString("ScreenView.Zoom_200%_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Zoom_200%_Tooltip"), ZoomFactor.ZOOM_TO_200_PERCENT)); //$NON-NLS-1$
		manager.add(new ZoomAction(this, Messages.getString("ScreenView.Zoom_400%_Action"), //$NON-NLS-1$
				Messages.getString("ScreenView.Zoom_400%_Tooltip"), ZoomFactor.ZOOM_TO_400_PERCENT)); //$NON-NLS-1$
		// Other plug-ins can contribute actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	/**
	 * Allows other classes to update content description.
	 * @param newContentDescription New description.
	 */
	public void updateDescription(String newContentDescription){
		mainView.updateDescription(newContentDescription);
	}
	
	/**
	 * Allows other classes to set default content description.
	 */
	public void setDefaultDescription() {
		mainView.setDefaultDescription();
	}

	/**
	 * The view should refresh all its UI components in this method.
	 */
	public void refresh(){
		// Currently there is no need for refresh, because no data model views.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {		
		super.dispose();

		// Stopping screensaver thread
		screenSaverThread.stopThread();
		
		// Ending capture thread.
		screenCaptureThread = null;

		// Images need to be disposed.
		if(ScreenSettings.getScreenImage() != null){
			ScreenSettings.setScreenImage(null, null);
		}
		// Remove property listener
		if(listener != null){
			RCPreferences.removePropertyChangeListener(listener);
		}
		
		// Remove touch screen handler.
		if(touchScreenHandler != null) {
			screenCanvas.removeMouseListener(touchScreenHandler);
			touchScreenHandler.dispose();
		}
		
		// Unregister from keyboard mediator
		keyboardMediator.unregisterKeyboardMediatorClient(this);
	}

	/**
	 * Makes necessary changes to context menu and action bars.
	 */
	public void setAsActiveView(){
		hookContextMenu();
		fillViewActionBars();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
	 */
	public void paintControl(PaintEvent e) {
		if(ScreenSettings.getScreenImage() != null){
			
			// Getting necessary variables.
			Color backgroundColor = e.gc.getBackground();
			Rectangle clientArea = screenCanvas.getClientArea();
			Image image = new Image(Display.getCurrent(), clientArea.width, clientArea.height);
			GC gc = new GC(image);
			
			// Drawing background with original color for image.
			// Background and image is drawn to different GC to prevent flickering.
			gc.setBackground(backgroundColor);
			gc.fillRectangle(clientArea);
			drawScreenImage(gc);
			
			// Drawing image with its correct size to screen.
			e.gc.drawImage(image, 0, 0);
			
			// Disposing created objects.
			gc.dispose();
			image.dispose();
		}
		else{
			// Filling screen with background color.
			Rectangle clientArea = screenCanvas.getClientArea();
			e.gc.fillRectangle(clientArea);

			drawFirstScreenHelp(e);
		}
	}

	/**
	 * Draws current screen shot to given GC object.
	 * @param gc Graphics object where current screen image is drawn.
	 */
	private void drawScreenImage(GC gc) {
		
		Image screenShot = ScreenSettings.getScreenShotCopy();
		Rectangle screenSize = screenShot.getBounds();
		Rectangle clientArea = ((ScrolledComposite)scrollBarLayout.topControl).getClientArea();

		// Calculating destination width and height based on zoom factor. 
		
		double zoomPercent = getZoomPercent(screenSize, clientArea);
		screenSettings.setZoomPercent(zoomPercent);
		
		int destWidth = (int) (screenSize.width * zoomPercent);
		int destHeight = (int) (screenSize.height * zoomPercent);
		
		// Drawing the image.
		gc.drawImage(screenShot, 0, 0, screenSize.width, screenSize.height, 0, 0, destWidth, destHeight);
		screenShot.dispose();
		
		// Setting screenCanvas size to make scroll bars show correctly.
		screenCanvas.setSize(destWidth, destHeight);
	}

	/**
	 * Calculates percentage that screen image needs to be zoomed based on
	 * currently selected zoom factor.
	 * @param screenSize Size of the screen image.
	 * @param clientArea Size of the are where image is drawn.
	 * @return Percentage of how much image needs to be zoomed.
	 */
	private double getZoomPercent(Rectangle screenSize, Rectangle clientArea) {
		
		// Calculating how much screen is zoomed. 
		
		double zoomPercent;
		
		switch(screenSettings.getZoomFactor()){
		case ZOOM_TO_FIT:
			double maxWidthFactor = (double)clientArea.width / screenSize.width;
			double maxHeightFactor = (double)clientArea.height / screenSize.height;
			if(maxHeightFactor < maxWidthFactor) {
				// Height factor is smaller, so height limits zooming.
				zoomPercent = maxHeightFactor;
			} else {
				// Width factor is smaller, so width limits zooming.
				zoomPercent = maxWidthFactor;
			}
			break;
		case ZOOM_TO_HEIGHT:
			zoomPercent = (double)clientArea.height / screenSize.height;
			break;
		case ZOOM_TO_WIDTH:
			zoomPercent = (double)clientArea.width / screenSize.width;
			break;
		case ZOOM_TO_50_PERCENT:
			zoomPercent = 0.5;
			break;
		case ZOOM_TO_100_PERCENT:
			zoomPercent = 1.0;
			break;
		case ZOOM_TO_200_PERCENT:
			zoomPercent = 2.0;
			break;
		case ZOOM_TO_400_PERCENT:
			zoomPercent = 4.0;
			break;
		default:
			// Using normal size as default.
			zoomPercent = 1.0;
			break;
		}
		
		return zoomPercent;
	}
	
	/**
	 * Draws start-up information text for user.
	 * @param event Event that contains control for painting to screen.
	 */
	private void drawFirstScreenHelp(PaintEvent event) {
		event.gc.drawString(Messages.getString("ScreenView.FirstTimeUse_Help_Text1"), 10, 10); //$NON-NLS-1$
		event.gc.drawString(Messages.getString("ScreenView.FirstTimeUse_Help_Text2"), 10,50); //$NON-NLS-1$
		event.gc.drawString(Messages.getString("ScreenView.FirstTimeUse_Help_Text3"), 10, 70); //$NON-NLS-1$
		
		// Setting size to screen canvas, so that scroll bars are shown if necessary.
		final int helpTextLength = 300;
		final int helpTextHeight = 100;
		screenCanvas.setSize(helpTextLength, helpTextHeight);
	}
	
	/**
	 * Captures image from phone and saves it to screenImage.
	 */
	private boolean captureImage(){
		IScreenCaptureService service = HTIServiceFactory.createScreenCaptureService(RemoteControlConsole.getInstance());
		
		try {
			// Getting the current screen mode.
			HTIScreenMode screenMode = service.getScreenMode(screenSettings.getTimeoutTime());
			// If screen mode has changed, take full screen capture.
			if (!screenMode.equals(screenSettings.getScreenMode())) {
				previousImageData = null;
			}		
			screenSettings.setScreenMode(screenMode);
			
			// Capturing the image.
			// If image not captured before, take a full screen capture.
			byte[] imagedata = null;
			if (previousImageData == null) {
				service.resetScreenDelta(screenSettings.getTimeoutTime());
				imagedata = service.captureFullScreenDelta(IMAGE_TYPE, screenSettings.getColorChoice(), screenSettings.getTimeoutTime()).getImageData();
				previousImageData = imagedata;
			} else { // If image captured earlier...
				// ...capture only changed part of the image.
				ScreenCaptureData captureData = service.captureFullScreenDelta(IMAGE_TYPE, screenSettings.getColorChoice(), screenSettings.getTimeoutTime());
				
				// If image not empty (changes have been made since last screen delta).
				if (captureData.getImageData().length > 0) {
					// Add captured delta image on top of previous screen image.
					imagedata = ImageHelper.overlayImage(previousImageData, captureData.getImageData(), 
							captureData.getTopLeftX(), captureData.getTopLeftY(), IMAGE_TYPE);
				} else {
					// No need to change the screen image.
					imagedata = previousImageData;
				}
		        
				// Replace previous image with changed image combination.
		        previousImageData = imagedata;
			}
			// Converting bytes to image.
			ByteArrayInputStream is = new ByteArrayInputStream(imagedata);
			Image image = new Image(Display.getCurrent(), is);
			
			byte[] previousImageData = ScreenSettings.getImageData();
			
			// Setting new image in use.
			ScreenSettings.setScreenImage(image, imagedata);
			
			if(screenSettings.isSavingAllScreenshots()) {
				if(screenSettings.getImagesSaved() == 0 || !Arrays.equals(imagedata, previousImageData)) {
					// Only first and new images are saved.
					saveCurrentScreenDefaultValues();
				}
			}
			
			updateActionButtonStates();
						
			// Screen captured successfully.
			return true;
			
		} catch (ServiceShutdownException e) {
			// Services have been purposely shut down. No need to report error.
			return false;
		} catch (Exception e) {
			// Problem is reported already via RemoteControlConsole given as parameter to service.
			return false;
		}
	}
	
	/**
	 * Thread that takes care of updating screen.
	 */
	private class screenCaptureThread extends Thread {
		
		public void run() {
			Thread thisThread = Thread.currentThread();
			boolean isScreenCaptured;
			
			while(thisThread == screenCaptureThread){
				try {
					isScreenCaptured = captureImage();

					// Updating image only if one was successfully captured.
					if(isScreenCaptured){
						Runnable updateScreenImage = new Runnable(){
							public void run() {
								// Testing that screenImage is still there and it is possible to redraw.
								if(ScreenSettings.getScreenImage() != null){
									try{
										screenCanvas.redraw();
									} catch (SWTException e) {
										// Catching exceptions that arise when plug-in is being
										// disposed and thread is not yet stopped.
									}
								}
							}							
						};
						// UI updates from background threads has to be queued
						// into UI thread in order not to cause invalid thread access
						Display.getDefault().asyncExec(updateScreenImage);
					}
					
					Thread.sleep(screenSettings.getRefreshTime());

				} catch (InterruptedException e) {
					e.printStackTrace();
					RemoteControlConsole.getInstance().println("screenCaptureThread.run() InterruptedException: " + e.getMessage()  //$NON-NLS-1$
							, RemoteControlConsole.MSG_ERROR);
				} catch (Exception e) {
					e.printStackTrace();
					RemoteControlConsole.getInstance().println("screenCaptureThread.run() Error: " + e.getMessage()  //$NON-NLS-1$
							, RemoteControlConsole.MSG_ERROR);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent e) {
		// Not needed.
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createControls()
	 */
	@Override
	protected void createControls() {
		try {
			//
			// Actions invoked by content providers may set enable/disable
			// states for the actions, therefore all the action has to be
			// created before creating the controls. This makes sure that
			// it is safe to refer to all the actions after this point.
			//
			createMainMenuActions();

			//
			// Creating controls
			//
			
			// Creating layout data
			GridData gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = SWT.FILL;
			gridData.verticalAlignment = SWT.FILL;

			scrollBarLayout = new StackLayout();
			this.setLayout(scrollBarLayout);
			
			// Creating scroll bars for different zoom types.
			screenScrollBarDefault = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
			screenScrollBarDefault.setSize(screenScrollBarDefault.computeSize(getClientArea().width, getClientArea().height));
			screenScrollBarVertical = new ScrolledComposite(this, SWT.V_SCROLL);
			screenScrollBarHorizontal = new ScrolledComposite(this, SWT.H_SCROLL);
			
			// NO_BACKGROUND is used to reduce flickering.
			// Because of this, it is needed to draw whole image before drawing it to screen.
			screenCanvas = new Canvas(screenScrollBarDefault, SWT.NO_BACKGROUND);
			screenCanvas.setSize(screenScrollBarDefault.getSize());
			touchScreenHandler = new TouchScreenHandler(this);
			screenCanvas.addMouseListener(touchScreenHandler);
			screenCanvas.addPaintListener(this);
			screenCanvas.setLayoutData(gridData);
			
			// Scroll bars need to be configured after screenCanvas has been created.
			configureScrollBars();

			//
			// Doing other initializations that may refer to the component
			// that has been created above.
			//		
			
			// Listening key events coming from keyboard
			KeyListener pcKeyboardListener = new KeyListener() {
				public void keyPressed(KeyEvent e) {		
					// Non character (e.g. Alt, Ctrl) key events are not forwarded
					if (e.character != '\0') {
						if (keyboardMediator != null) {
							keyboardMediator.characterKeyPressed(instance, e.character);
						}
					}
				}
				
				public void keyReleased(KeyEvent e) {
					// Not used
				}
			};
			screenCanvas.addKeyListener(pcKeyboardListener);
				
		} catch (Exception e) {
			e.printStackTrace();
			RemoteControlConsole.getInstance().println(Messages.getString("ScreenView.Failed_Create_Screen_View_ConsoleMsg") + e.getMessage()  //$NON-NLS-1$
					, RemoteControlConsole.MSG_ERROR);
		}
	}
	
	/**
	 * Configures scroll bars used around screenCanvas.
	 */
	private void configureScrollBars() {
		scrollBarLayout.topControl = screenScrollBarDefault;
		
		screenScrollBarVertical.setContent(screenCanvas);
		screenScrollBarVertical.setAlwaysShowScrollBars(true);
		screenScrollBarVertical.getVerticalBar().setPageIncrement(DEFAULT_SCROLL_INCREMENT);
		screenScrollBarVertical.getVerticalBar().setIncrement(DEFAULT_SCROLL_INCREMENT);
		
		screenScrollBarHorizontal.setContent(screenCanvas);
		screenScrollBarHorizontal.setAlwaysShowScrollBars(true);
		screenScrollBarHorizontal.getHorizontalBar().setPageIncrement(DEFAULT_SCROLL_INCREMENT);
		screenScrollBarHorizontal.getHorizontalBar().setIncrement(DEFAULT_SCROLL_INCREMENT);
		
		screenScrollBarDefault.setContent(screenCanvas);
		screenScrollBarDefault.getVerticalBar().setPageIncrement(DEFAULT_SCROLL_INCREMENT);
		screenScrollBarDefault.getVerticalBar().setIncrement(DEFAULT_SCROLL_INCREMENT);
		screenScrollBarDefault.getHorizontalBar().setPageIncrement(DEFAULT_SCROLL_INCREMENT);
		screenScrollBarDefault.getHorizontalBar().setIncrement(DEFAULT_SCROLL_INCREMENT);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createLayout()
	 */
	protected Layout createLayout() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		return gridLayout;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.ui.AbstractUiFractionComposite#createLayoutData()
	 */
	protected Object createLayoutData() {
		// No default layout data.
		return null;
	}
	
	/**
	 * Sets enabled/disabled states for actions commands
	 * on this view, based on the current application state.
	 * This method should be called whenever an operation is
	 * started or stopped that might have effect on action 
	 * button states.
	 */
	public void updateActionButtonStates(){
		if(!screenSettings.isSavingAllScreenshots()) {
			saveMultiScreenshotsAction.setSaving(false);
		}
		
		// Setting save single screenshot action.
		if(ScreenSettings.getScreenImage() == null || screenSettings.isSavingAllScreenshots()) {
			saveSingleScreenshotAction.setEnabled(false);
		} else {
			saveSingleScreenshotAction.setEnabled(true);
		}
		
		// Setting save multi screenshot action.
		saveMultiScreenshotsAction.setEnabled(ScreenSettings.getScreenImage() != null);
	}
	
	/**
	 * Updates zooming for current screen.
	 */
	public void updateZoom() {
		
		// Setting correct scroll bars for new zoom factor.
		
		switch(screenSettings.getZoomFactor()){
		case ZOOM_TO_HEIGHT:
			if(!screenCanvas.setParent(screenScrollBarHorizontal)) {
				// Set parent not supported. Using default scroll bars.
				break;
			}
			scrollBarLayout.topControl = screenScrollBarHorizontal;
			this.layout();
			break;
		case ZOOM_TO_WIDTH:
			if(!screenCanvas.setParent(screenScrollBarVertical)) {
				// Set parent not supported. Using default scroll bars. 
				break;
			}
			scrollBarLayout.topControl = screenScrollBarVertical;
			this.layout();
			break;
		default:
			if(!screenCanvas.setParent(screenScrollBarDefault)) {
				// Set parent not supported. Using default scroll bars. 
				break;
			}
			scrollBarLayout.topControl = screenScrollBarDefault;
			this.layout();
			break;
		}
		
		// Updating screen image
		
		if(ScreenSettings.getScreenImage() != null) {
			Runnable updateScreenImage = new Runnable(){
				public void run() {
					// Testing that screenImage is still there and it is possible to redraw.
					if(ScreenSettings.getScreenImage() != null){
						try{
							screenCanvas.redraw();
						} catch (SWTException e) {
							// Catching exceptions that arise when plug-in is being
							// disposed and thread is not yet stopped.
						}
					}
				}
			};
			// UI updates from background threads has to be queued
			// into UI thread in order not to cause invalid thread access
			Display.getDefault().asyncExec(updateScreenImage);
		}
	}
	
	/**
	 * Saves current screen with values defined preferences.
	 */
	public void saveCurrentScreenDefaultValues() {
		screenSaverThread.addImageToQueue(ScreenSettings.getImageData());
	}

	/**
	 * Returns current settings for showing the screen.
	 * @return Current settings for the screen.
	 */
	public ScreenSettings getScreenSettings() {
		return screenSettings;
	}
}
