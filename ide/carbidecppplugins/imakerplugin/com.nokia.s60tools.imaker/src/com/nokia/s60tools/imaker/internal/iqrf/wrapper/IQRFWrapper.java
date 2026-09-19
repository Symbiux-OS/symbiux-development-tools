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
package com.nokia.s60tools.imaker.internal.iqrf.wrapper;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.nokia.s60tools.imaker.internal.iqrf.IMaker;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage;
import com.nokia.s60tools.imaker.internal.iqrf.impl.IMakerImpl;

public class IQRFWrapper {
	/**
	 * The default constructor.
	 */
	public IQRFWrapper() {
	}

	/**
	 * The method converts an XML document into an object of the IMaker class.
	 * 
	 * @param xml XML document content as String.
	 * @return IMaker object containing the data.
	 */
	@SuppressWarnings("unchecked")
	public IMaker getModel(String xml) {
		IMaker imaker = null;

		// Register the package -- only needed for stand-alone!
		@SuppressWarnings("unused")
		IQRFPackage libraryPackage = IQRFPackage.eINSTANCE;

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		// Create a resource to handle XMI content.
		XMIResourceImpl resource = new XMIResourceImpl();

		// Create an input stream to read the String contents into it
		// and then pass it to resources' load method to load the XMI.
		InputStream in = new ByteArrayInputStream(xml.getBytes());

		try
		{
			resource.load(in, null);
			Iterator iter = resource.getContents().iterator();

			//Result result = IQRFFactory.eINSTANCE.createResult();
			//Result result;
			Object obj;

			while(iter.hasNext())
			{
				obj = iter.next();
				if (obj instanceof IMakerImpl)
				{
					imaker = (IMaker)obj;
					break;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return imaker;
	}

	/**
	 * This class converts a IMaker object into an XML document.
	 * 
	 * @param result instance of the IMaker class.
	 * @return string containing the data as XML.
	 */
	@SuppressWarnings("unchecked")
	public String getXMLDocument(IMaker imaker) {

		// Register the default resource factory -- only needed for stand-alone!
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		// Create a resource for handling XMI.
		XMIResourceImpl resource = new XMIResourceImpl();

		// Add the imaker object to the contents.
		resource.getContents().add(imaker);

		// Create a writer to store the generated XMI.
//		OutputStream out = new ByteArrayOutputStream();

		// Save the contents of the resource to output stream.
		try {
			StringWriter writer = new StringWriter (  ) ; 
			PrintWriter pwriter = new PrintWriter ( writer ) ; 
			resource.save(pwriter, Collections.EMPTY_MAP);
			pwriter.close();
			return writer.toString();

		}
		catch (IOException e) {}
		return null;
	}

}
