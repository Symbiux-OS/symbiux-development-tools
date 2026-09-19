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

package com.nokia.s60tools.remotecontrol.keyboard;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.IKeyEventService;
import com.nokia.s60tools.remotecontrol.keyboard.BufKeyEvent.EventType;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * This class is mediator between keyboard requests from UI 
 * and services that send key requests further to key services.
 * This class is created during plug-in start-up therefore should 
 * be accessible during plug-in.
 * 
 * The mediator instance should be always queried via 
 * method <code>RemoteControlActivator..getKeyboardMediator()</code>
 * life time.
 * 
 * Mediator instance buffers pure textual input and sends buffer contents
 * further in pre-defined intervals which enables some kind of feedback
 * for user about the written text. Without buffering no data is shown to the
 * writer before all the text is written and the writer waits for some time
 * for screen refresh.
 * 
 * @see com.nokia.s60tools.remotecontrol.RemoteControlActivator#getKeyboardMediator
 */
public class KeyboardMediator implements IKeyboardMediator {
		
	//
	// Constants and members for keyboard mediator class
	//
	
	/**
	 * Error message shown whenever trying to send events from unregistered client object.
	 */
	private static final String UNREGISTERED_INSTANCE_ERR_MSG = Messages.getString("KeyboardMediator.TriedToReferUnregisteredClientObject_ErrMsg"); //$NON-NLS-1$
	
	/**
	 * Maximum amount of characters that are sent to device in single send text request.
	 * There needs to be maximum amount so that timeout can be prevented with fixed timeout.
	 * It was decided against using dynamic timeout to prevent requests that would take very
	 * long time to complete.
	 */
	private static final int MAX_CHARS_IN_MESSAGE = 30;

	/**
	 * Character key events are buffered amount of milliseconds set in here, before sending them further.
	 * The value can be overridden by calling appropriate constructor.
	 */
	private int bufferFlushTimeOutMillisec = 1000;
	
	/**
	 * Map storing key press sequence data per client.
	 */
	private Map<Object, KeyPressSequenceData> clientMap;
	
	/**
	 * Console to report warnings, errors, and info message.
	 */
	private IConsolePrintUtility console;

	//
	// Private nested classes
	//
	
	/**
	 * Stores information about currently ongoing key press sequences
	 * and does buffering for textual character sequences.
	 */
	private class KeyPressSequenceData{
		
		/**
		 * Time-out used for sending key events.
		 */
		private static final long DEFAULT_KEY_EVENT_TIME_OUT = 3000;
		/**
		 * Key event service instance.
		 */
		private IKeyEventService service;
		/**
		 * Queue that holds key events.
		 */
		private ConcurrentLinkedQueue<BufKeyEvent> keyEventQueue;
		/**
		 * Flusher thread for
		 */
		private Thread bufFlusher;
		
		/**
		 * Synchronization object used to wait/signal flusher thread.
		 */
		private Object flusherWaitEvent = new Object();
		/**
		 * Synchronization object used to wait/signal flusher thread when buffering characters.
		 */
		private Object flusherWaitCharsEvent = new Object();
		/**
		 * Flusher flag telling for thread to shutdown.
		 */
		private boolean isShutdown = false;
		
		/**
		 * Scan code count used to track scan code amount in queue.
		 * This is important for deciding if flusher should wait for more text
		 * or flush next text.
		 * Flusher can wait for more text, if there are no scan codes in queue.
		 */
		private Integer scanCodeCount = 0;
		
		/**
		 * Information about if thread is being idle and doing nothing.
		 * True if there are no events to be handled. False otherwise.
		 */
		private boolean isIdle;
		
		//
		// Private methods
		//

		/**
		 * Checks if there are scan codes in queue.
		 * @return True if there are scan codes in queue. Else false
		 */
		private synchronized boolean hasScanCodes() {
			synchronized(scanCodeCount){
				return scanCodeCount.intValue() > 0;
			}
		}

		/**
		 * Increases scan code amount by one.
		 */
		private synchronized void incScanCodeCount() {
			synchronized(scanCodeCount){
				scanCodeCount++;
			}
		}

		/**
		 * Decreases scan code amount by one.
		 */
		private synchronized void decrScanCodeCount() {
			synchronized(scanCodeCount){
				scanCodeCount--;
			}
		}

		/**
		 * Called by flusher itself to start waiting for wake-up event.
		 * @throws InterruptedException
		 */
		private void flusherWaitInfinite() throws InterruptedException{
			synchronized(flusherWaitEvent){
				isIdle = true;
				flusherWaitEvent.wait();
			}
		}
		
		/**
		 * Called by flusher itself to start waiting for more characters until timeout
		 * expires or for wake-up event.
		 * @param timeout Timeout
		 * @throws InterruptedException
		 */
		private void flusherWaitForChars(long timeout) throws InterruptedException{
			synchronized(flusherWaitCharsEvent){
				flusherWaitCharsEvent.wait(timeout);
			}
		}
		
		/**
		 * Called by client to wake-up flusher from wait.
		 */
		private void wakeUpFlusher(){
			synchronized(flusherWaitEvent){
				isIdle = false;
				flusherWaitEvent.notifyAll();
			}
		}
		
		/**
		 * Flushes any previous textual data stored in buffer.
		 * @throws InterruptedException 
		 */
		private void forceFlush() {
			wakeUpFlusher();
			synchronized(flusherWaitCharsEvent){
				flusherWaitCharsEvent.notifyAll();
			}
		}
		
		/**
		 * Gets next event from queue and sends request to the device.
		 * Buffers events of same type together if possible to improve event handling.
		 */
		private void flushBuffer() {
			
			if(keyEventQueue.peek() == null) {
				// Queue is empty, nothing to flush.
				return;
			}
			
			BufKeyEvent event = keyEventQueue.poll();

			try {
				// Sending next data from queue. It can be either next X amount of characters or next scan code.
				
				if(event.getEventType() == EventType.charKeyPressed) {
					StringBuffer eventBuf = new StringBuffer();
					eventBuf.append(event.getCharKey());
					
					// Buffering all characters to one buffer, so that they can be sent at the same time.
					for(int i = 0;i < MAX_CHARS_IN_MESSAGE;i++) {
						if(keyEventQueue.isEmpty() || 
								keyEventQueue.peek().getEventType() != EventType.charKeyPressed) {
							// Breaking because next event is not character event.
							break;
						}
						
						eventBuf.append(keyEventQueue.poll().getCharKey());
					}
					service.typeText(eventBuf.toString(), DEFAULT_KEY_EVENT_TIME_OUT);
				}
				else if(event.getEventType() == EventType.scanCodeKeyPressed) {
					service.pressKey(event.getScanCode(), DEFAULT_KEY_EVENT_TIME_OUT);
					decrScanCodeCount();
				}
				else if(event.getEventType() == EventType.scanCodeKeyPressAndHold) {
					// Adding hold time to default timeout to prevent service timeout.
					service.pressKeyLong(event.getScanCode(), event.getHoldTime(),
							event.getHoldTime() + DEFAULT_KEY_EVENT_TIME_OUT);
					decrScanCodeCount();
				}
				else if(event.getEventType() == EventType.scanCodeKeyHold) {
					service.holdKey(event.getScanCode(), DEFAULT_KEY_EVENT_TIME_OUT);
					decrScanCodeCount();
				}
				else if(event.getEventType() == EventType.scanCodeKeyReleased) {
					service.releaseKey(event.getScanCode(), DEFAULT_KEY_EVENT_TIME_OUT);
					decrScanCodeCount();
				}
			} catch (ServiceShutdownException e) {
				// Services have been purposely shut down. No need to report error.
			} catch (Exception e) {
				e.printStackTrace();
				String detailedErrMsg = Messages.getString("KeyboardMediator.FailedToForwardKeyEvent_ErrMsg"); //$NON-NLS-1$
				reportErrorToConsole(e, detailedErrMsg);
			}
		}
		
		/**
		 * Creates and starts flusher thread.
		 */
		private void createAndStartFlusherThread() {
			bufFlusher = new Thread(){
				public void run(){
					while(!isShutdown){
						
						try {
							if(keyEventQueue.isEmpty()) {
								// Waiting for signal informing that key event has been added to queue. 
								flusherWaitInfinite();
							}
							
							if(!hasScanCodes() && !keyEventQueue.isEmpty()) {
								// Waiting pre-defined time before buffer flush, or notify that scan code has been added to queue.
								// This wait is done so that text can be buffered and sent as one long text, instead of
								// sending text char by char.
								// Scan code in line forces flush, because they are sent one by one and there is no need to wait
								// for more characters when there are scan codes in queue.
								flusherWaitForChars(bufferFlushTimeOutMillisec);
							}
							
							flushBuffer();

						} catch (Exception e) {
							e.printStackTrace();
							String detailedErrMsg = Messages.getString("KeyboardMediator.FailedToFlushBuffer_ErrMsg");  //$NON-NLS-1$
							reportErrorToConsole(e, detailedErrMsg);
						}
						
					} // while
				} // run

			};
			bufFlusher.start();					
		}

		//
		// Public methods
		//
		
		/**
		 * Constructor.
		 */
		public KeyPressSequenceData(){			
			service = HTIServiceFactory.createKeyEventService(console);		
			keyEventQueue = new ConcurrentLinkedQueue<BufKeyEvent>();
			createAndStartFlusherThread();
		}

		/**
		 * Stops flusher thread.
		 */
		public void stopFlusherThread() {
			isShutdown = true;			
			forceFlush();
		}
		
		/**
		 * Character key pressed event.
		 * @param charKey Character key.
		 */
		public void characterKeyPressed(char charKey) {
			keyEventQueue.add(new BufKeyEvent(charKey));
			wakeUpFlusher();
		}

		/**
		 * Scan code key pressed event.
		 * @param scanCode Scan code.
		 */
		public void scanCodeKeyPressed(int scanCode) {
			keyEventQueue.add(new BufKeyEvent(scanCode, EventType.scanCodeKeyPressed));
			incScanCodeCount();
			forceFlush();
		}
		
		/**
		 * Scan code key pressed and hold down event.
		 * @param scanCode Scan code.
		 * @param holdTime Time that key is hold down.
		 */
		public void scanCodeKeyPressedAndHold(int scanCode, int holdTime) {
			keyEventQueue.add(new BufKeyEvent(scanCode, holdTime));
			incScanCodeCount();
			forceFlush();
		}

		/**
		 * Scan code key hold event.
		 * @param scanCode Scan code.
		 */
		public void scanCodeKeyHold(int scanCode) {
			keyEventQueue.add(new BufKeyEvent(scanCode, EventType.scanCodeKeyHold));
			incScanCodeCount();
			forceFlush();
		}

		/**
		 * Scan code key released event.
		 * @param scanCode Scan code.
		 */
		public void scanCodeKeyReleased(int scanCode) {
			keyEventQueue.add(new BufKeyEvent(scanCode, EventType.scanCodeKeyReleased));
			incScanCodeCount();
			forceFlush();
		}

		/**
		 * Returns information about if flusher thread is waiting for new events.
		 * @return True if event thread is doing nothing, or false if there are events to be handled. 
		 */
		public boolean isIdle() {
			return isIdle;
		}
		
	} // end of KeyPressSequenceData class
	
	//
	// Methods for keyboard mediator class
	//
	
	/**
	 * Constructor. 
	 * @param console Reference to console to direct error, warning, and info message to.
	 */
	public KeyboardMediator(IConsolePrintUtility console){
		this.console = console;
		clientMap = Collections.synchronizedMap(new HashMap<Object, KeyPressSequenceData>());
	}
	
	/**
	 * Constructor. 
	 * @param console Reference to console to direct error, warning, and info message to.
	 * @param bufferFlushTimeOutMillisec Keyboard mediator buffers the key events before sending them further.
	 *                           By using this constructor one is able to change the default buffering time.
	 *                           This is especially useful for testing when the input data is entered instantly.
	 */
	public KeyboardMediator(IConsolePrintUtility console, int bufferFlushTimeOutMillisec){
		this.console = console;
		this.bufferFlushTimeOutMillisec = bufferFlushTimeOutMillisec;
		clientMap = Collections.synchronizedMap(new HashMap<Object, KeyPressSequenceData>());
	}
	
	/**
	 * Reports errors to console.
	 * @param e Exception encountered.
	 * @param detailedErrMsg Error message describing the error context.
	 */
	private void reportErrorToConsole(Exception e, String detailedErrMsg) {
		String errMsg = detailedErrMsg
						+ " ("  //$NON-NLS-1$
						+ Messages.getString("KeyboardMediator.Exception_Str") //$NON-NLS-1$
						+ "="  //$NON-NLS-1$
						+ e.getClass().getSimpleName() 
						+ ")." //$NON-NLS-1$
						+ Messages.getString("KeyboardMediator.ErrorDetails_Msg") //$NON-NLS-1$
						+ e.getMessage();
		console.println(errMsg , IConsolePrintUtility.MSG_ERROR);
	}	
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#registerKeyboardMediatorClient(java.lang.Object)
	 */
	public void registerKeyboardMediatorClient(Object mediatorClient) {
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData != null){
		    	String errMsg = Messages.getString("KeyboardMediator.TriedToRegisterAlreadyRegisteredClientObject_ErrMsg"); //$NON-NLS-1$
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
		    	throw new RuntimeException(errMsg);
		    }
		    clientMap.put(mediatorClient, new KeyPressSequenceData());
		} // synchronized
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#unregisterKeyboardMediatorClient(java.lang.Object)
	 */
	public void unregisterKeyboardMediatorClient(Object mediatorClient) {
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData != null){
		    	keyPressSequenceData.stopFlusherThread();
			    clientMap.remove(mediatorClient);
		    }
		    else{
		    	String errMsg = Messages.getString("KeyboardMediator.TriedToUnregisterNonExistingClientObject_ErrMsg"); //$NON-NLS-1$
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
				throw new RuntimeException(errMsg);		    	
		    }
		} // synchronized
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#keyPressed(java.lang.Object, org.eclipse.swt.events.KeyEvent)
	 */
	public void characterKeyPressed(Object mediatorClient, char charKey) {		
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData == null){
		    	String errMsg = UNREGISTERED_INSTANCE_ERR_MSG;
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
		    	throw new RuntimeException(errMsg);
		    }
		    // Forwarding key press
		    keyPressSequenceData.characterKeyPressed(charKey);
		} // synchronized				
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#scanCodeKeyPressed(java.lang.Object, int)
	 */
	public void scanCodeKeyPressed(Object mediatorClient, int scanCode) {		
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData == null){
		    	String errMsg = UNREGISTERED_INSTANCE_ERR_MSG;
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
		    	throw new RuntimeException(errMsg);
		    }
			// Sending scan code further
		    keyPressSequenceData.scanCodeKeyPressed(scanCode);
		} // synchronized
	}
	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#scanCodeKeyPressedAndHold(java.lang.Object, int, int)
	 */
	public void scanCodeKeyPressedAndHold(Object mediatorClient, int scanCode, int holdTime) {
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData == null){
		    	String errMsg = UNREGISTERED_INSTANCE_ERR_MSG;
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
		    	throw new RuntimeException(errMsg);
		    }
			// Sending scan code further
		    keyPressSequenceData.scanCodeKeyPressedAndHold(scanCode, holdTime);
		} // synchronized
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#scanCodeKeyHold(java.lang.Object, int)
	 */
	public void scanCodeKeyHold(Object mediatorClient, int scanCode) {				
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData == null){
		    	String errMsg = UNREGISTERED_INSTANCE_ERR_MSG;
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
		    	throw new RuntimeException(errMsg);
		    }
			// Sending scan code further
		    keyPressSequenceData.scanCodeKeyHold(scanCode);
		} // synchronized				
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#keyReleased(java.lang.Object, org.eclipse.swt.events.KeyEvent)
	 */
	public void scanCodeKeyReleased(Object mediatorClient, int scanCode) {
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData == null){
		    	String errMsg = UNREGISTERED_INSTANCE_ERR_MSG;
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
		    	throw new RuntimeException(errMsg);
		    }
			// Sending scan code further
		    keyPressSequenceData.scanCodeKeyReleased(scanCode);
		} // synchronized				
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator#isMediatorIdle(java.lang.Object)
	 */
	public boolean isMediatorIdle(Object mediatorClient) {
		//We needs to synchronize the map of already found components during check 
		// and add operation for enabling safe multithread access into the map.
		synchronized(clientMap){
		    KeyPressSequenceData keyPressSequenceData = clientMap.get(mediatorClient);
		    if(keyPressSequenceData == null){
		    	String errMsg = UNREGISTERED_INSTANCE_ERR_MSG;
		    	console.println(errMsg, IConsolePrintUtility.MSG_ERROR);
		    	throw new RuntimeException(errMsg);
		    }
			// Sending size of the queue.
		    return keyPressSequenceData.isIdle();
		} // synchronized	
	}
}
