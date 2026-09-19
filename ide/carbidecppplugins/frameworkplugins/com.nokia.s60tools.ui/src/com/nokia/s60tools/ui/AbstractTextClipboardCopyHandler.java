/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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
 
 
package com.nokia.s60tools.ui;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

/**
 * This class defines common interface for all 
 * clipboard copy handlers supporting plain text.
 * This abstract class uses Template method design
 * pattern.
 * 
 * This class may be subclasses in the following way:
 * 
 * <code>
 * <pre>
 * 
 * public class MylipboardCopyHandler extends AbstractTextClipboardCopyHandler 
 *                                                  implements ICopyActionHandler {
 * 	
 *  MyDataHolderClass[] dataArr = null;
 * 
 *  protected String buildTextString() {
 *      StringBuffer strBuf = new StringBuffer();
 * 		
 *      for (int i = 0; i < dataArr.length; i++) {
 *          MyDataHolderClass dataItem = dataArr[i];
 * 			
 *          // Adding data fields to buffer as text
 *          strBuf.append(dataItem.getSomeData() + "\t");
 *          strBuf.append(dataItem.getSomeOtherData() + "\t");
 *          // Other possible fields, and e.g. new line to the end
 *          // The format might be specific for each data type.
 *          strBuf.append("\n");
 * 			
 *      }
 *      return strBuf.toString();
 *  }
 * 
 *  public boolean acceptAndCopy(List objectsToCopy) {
 * 
 *          // Checking that there are objects to copy and 
 *          // this is a correct copy handler for the passed objects 
 *          if(objectsToCopy.size() > 0){
 *              if(!(objectsToCopy.get(0) instanceof MyDataHolderClass)){
 *              return false;
 *              }
 *          }
 *          else{
 *              // No objects to copy
 *              return false;
 *          }
 * 
 *          dataArr = (MyDataHolderClass[]) objectsToCopy.toArray(new MyDataHolderClass[0]);
 *          // Class cast succeeded, and we can perform copy
 *          performCopy();
 *          // Copy was performed succesfully
 *          return true;
 *  }
 * 
 * }
 *
 * </pre>
 * </code>
 * 
 */
public abstract class AbstractTextClipboardCopyHandler implements ICopyActionHandler{
		
	/**
	 * Constructor.
	 */
	public AbstractTextClipboardCopyHandler(){		
	}
	
	/**
	 * Performs the actual copy operation that
	 * calls <code>buildTextString</code> template method.
	 * This method binds the clipboard into
	 * currently used display.
	 */
	protected void performCopy(){
		Display disp = Display.getCurrent();
		Clipboard clipBrd  = new Clipboard(disp);
		String strToCopy = buildTextString();
		TextTransfer txtTransfInstance = TextTransfer.getInstance();
		clipBrd.setContents(new String[]{ strToCopy },
							new Transfer[] {txtTransfInstance});
		clipBrd.dispose();
	}

	/**
	 * Abstract template method that each sub class must implement.
	 * @return Returns text string to be copied into the clipboard.
	 */
	abstract protected String buildTextString();
}
