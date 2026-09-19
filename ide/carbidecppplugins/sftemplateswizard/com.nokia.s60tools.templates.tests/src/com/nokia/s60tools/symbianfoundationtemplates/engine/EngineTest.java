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
package com.nokia.s60tools.symbianfoundationtemplates.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.nokia.s60tools.symbianfoundationtemplates.engine.s60.S60Engine;
import com.nokia.s60tools.symbianfoundationtemplates.engine.s60.S60TransformKeys;

/**
 * Test cases for the template wizard.
 */
public class EngineTest extends TestCase {
	private static final String PLUGIN_DIRECTORY = System.getProperty("TESTDATALOCATION") + "/templates";
	private static final String TEST_DIRECTORY = System.getProperty("TESTDATALOCATION") + "/correct";

	private static final String C_CLASS_HEADER_TEMPLATE = "/c_class_header_template.h";
	private static final String M_CLASS_HEADER_TEMPLATE = "/m_class_header_template.h";
	private static final String R_CLASS_HEADER_TEMPLATE = "/r_class_header_template.h";
	private static final String T_CLASS_HEADER_TEMPLATE = "/t_class_header_template.h";
	
	private static final String C_EMPTY_CLASS_HEADER_TEMPLATE = "/empty_c_class_header_template.h";
	private static final String M_EMPTY_CLASS_HEADER_TEMPLATE = "/empty_m_class_header_template.h";
	private static final String R_EMPTY_CLASS_HEADER_TEMPLATE = "/empty_r_class_header_template.h";
	private static final String T_EMPTY_CLASS_HEADER_TEMPLATE = "/empty_t_class_header_template.h";
	
	private static final String SOURCE_TEMPLATE 		= "/source_template.cpp";
	private static final String C_EMPTY_CLASS_SOURCE_TEMPLATE = "/empty_source_template.cpp";
		
	private static final String RESOURCE_HRH_TEMPLATE	= "/resource_template.hrh";
	private static final String RESOURCE_RH_TEMPLATE	= "/resource_template.rh";
	private static final String RESOURCE_LOC_TEMPLATE	= "/resource_template.loc";
	private static final String RESOURCE_RSS_TEMPLATE	= "/resource_template.rss";
	
	private static final String EMPTY_RESOURCE_HRH_TEMPLATE	= "/empty_resource_template.hrh";
	private static final String EMPTY_RESOURCE_RH_TEMPLATE	= "/empty_resource_template.rh";
	private static final String EMPTY_RESOURCE_LOC_TEMPLATE	= "/empty_resource_template.loc";
	private static final String EMPTY_RESOURCE_RSS_TEMPLATE	= "/empty_resource_template.rss";

	private static final String ICONS_TEMPLATE			= "/icons.mk";
	private static final String ICONS_BITMAP_TEMPLATE	= "/icons_aif_bitmaps.mk";
	private static final String ICONS_SCALABLE_TEMPLATE	= "/icons_aif_scalable.mk";
	
	private static final String EMPTY_ICONS_TEMPLATE			= "/empty_icons.mk";
	private static final String EMPTY_ICONS_BITMAP_TEMPLATE	= "/empty_icons_aif_bitmaps.mk";
	private static final String EMPTY_ICONS_SCALABLE_TEMPLATE	= "/empty_icons_aif_scalable.mk";
	
	private static final String MMP_TEMPLATE			= "/project_specification_template.mmp";
	private static final String BLDINF_TEMPLATE			= "/build_info_template.inf";
	
	private static final String EMPTY_MMP_TEMPLATE			= "/empty_project_specification_template.mmp";
	private static final String EMPTY_BLDINF_TEMPLATE			= "/empty_build_info_template.inf";
	
	private static final String INLINE_TEMPLATE			= "/inline_template.inl";
	
	private Map<String, String> transformRules;
	
	public void setUp() {
		transformRules = new HashMap<String, String>();
		
		transformRules.put(S60TransformKeys.getString("key_filename"), "TestFileName");
		transformRules.put(S60TransformKeys.getString("key_subsystem"), "TestSubSystem");
		transformRules.put(S60TransformKeys.getString("key_module"), "TestModule");
		transformRules.put(S60TransformKeys.getString("key_description"), "TestDescription\n\ncontinues\n\n---\nstill\n");
		transformRules.put(S60TransformKeys.getString("key_copyright"), "2000-2001");
		transformRules.put(S60TransformKeys.getString("key_classname"), "TestClassName");
		transformRules.put(S60TransformKeys.getString("key_appname"), "TestAppName");
		transformRules.put(S60TransformKeys.getString("key_cclassname"), "C_TESTCLASSNAME_H");
		transformRules.put(S60TransformKeys.getString("key_mclassname"), "M_TESTCLASSNAME_H");
		transformRules.put(S60TransformKeys.getString("key_rclassname"), "R_TESTCLASSNAME_H");
		transformRules.put(S60TransformKeys.getString("key_tclassname"), "T_TESTCLASSNAME_H");
		transformRules.put(S60TransformKeys.getString("key_hrhmodulename"), "TESTFILENAME_HRH");
		transformRules.put(S60TransformKeys.getString("key_rhmodulename"), "TESTFILENAME_RH");
		transformRules.put(S60TransformKeys.getString("key_license"), "Symbian Foundation License v1.0");
		transformRules.put(S60TransformKeys.getString("key_companyname"), "Nokia Corporation");
		transformRules.put(S60TransformKeys.getString("key_licenseurl"), "http://www.symbianfoundation.org/legal/sfl-v10.html");
	}
	
	public void testCClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + C_CLASS_HEADER_TEMPLATE);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		engine.setTransformRules(transformRules);
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + C_CLASS_HEADER_TEMPLATE), true);
	}
	
	public void testMClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + M_CLASS_HEADER_TEMPLATE);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		engine.setTransformRules(transformRules);
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + M_CLASS_HEADER_TEMPLATE), true);
	}

	public void testRClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + R_CLASS_HEADER_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + R_CLASS_HEADER_TEMPLATE), true);
	}

	public void testTClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + T_CLASS_HEADER_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + T_CLASS_HEADER_TEMPLATE), true);
	}
	
	public void testEmptyCClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + C_EMPTY_CLASS_HEADER_TEMPLATE);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		engine.setTransformRules(transformRules);
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + C_EMPTY_CLASS_HEADER_TEMPLATE), true);
	}
	
	public void testEmptyMClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + M_EMPTY_CLASS_HEADER_TEMPLATE);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		engine.setTransformRules(transformRules);
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + M_EMPTY_CLASS_HEADER_TEMPLATE), true);
	}
	
	public void testEmptyRClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + R_EMPTY_CLASS_HEADER_TEMPLATE);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		engine.setTransformRules(transformRules);
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + R_EMPTY_CLASS_HEADER_TEMPLATE), true);
	}
	
	public void testEmptyTClassTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + T_EMPTY_CLASS_HEADER_TEMPLATE);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		engine.setTransformRules(transformRules);
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + T_EMPTY_CLASS_HEADER_TEMPLATE), true);
	}
	
	public void testSourceTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + SOURCE_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + SOURCE_TEMPLATE), true);
	}
	
	public void testEmptySourceTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + C_EMPTY_CLASS_SOURCE_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + C_EMPTY_CLASS_SOURCE_TEMPLATE), true);
	}
	
	public void testResourceHRHTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + RESOURCE_HRH_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + RESOURCE_HRH_TEMPLATE), true);
	}
	
	public void testEmptyResourceHRHTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_RESOURCE_HRH_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_RESOURCE_HRH_TEMPLATE), true);
	}
	
	public void testResourceRHTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + RESOURCE_RH_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + RESOURCE_RH_TEMPLATE), true);
	}
	
	public void testEmptyResourceRHTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_RESOURCE_RH_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_RESOURCE_RH_TEMPLATE), true);
	}
	
	public void testResourceLOCTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + RESOURCE_LOC_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + RESOURCE_LOC_TEMPLATE), true);
	}
	
	public void testEmptyResourceLOCTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_RESOURCE_LOC_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_RESOURCE_LOC_TEMPLATE), true);
	}
	
	public void testResourceRSSTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + RESOURCE_RSS_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + RESOURCE_RSS_TEMPLATE), true);
	}
	
	public void testEmptyResourceRSSTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_RESOURCE_RSS_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_RESOURCE_RSS_TEMPLATE), true);
	}
	
	public void testIconsTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + ICONS_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + ICONS_TEMPLATE), true);
	}
	
	public void testEmptyIconsTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_ICONS_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_ICONS_TEMPLATE), true);
	}
	
	public void testIconsBitmapTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + ICONS_BITMAP_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + ICONS_BITMAP_TEMPLATE), true);
	}
	
	public void testEmptyIconsBitmapTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_ICONS_BITMAP_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_ICONS_BITMAP_TEMPLATE), true);
	}
	
	public void testIconsScalableTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + ICONS_SCALABLE_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + ICONS_SCALABLE_TEMPLATE), true);
	}
	
	public void testEmptyIconsScalableTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_ICONS_SCALABLE_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_ICONS_SCALABLE_TEMPLATE), true);
	}
	
	public void testMMPTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + MMP_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + MMP_TEMPLATE), true);
	}
	
	public void testEmptyMMPTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_MMP_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_MMP_TEMPLATE), true);
	}
	
	public void testBuildInfoTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + BLDINF_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + BLDINF_TEMPLATE), true);
	}
	
	public void testEmptyBuildInfoTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + EMPTY_BLDINF_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + EMPTY_BLDINF_TEMPLATE), true);
	}
	
	public void testInlineTransform() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + INLINE_TEMPLATE);
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
		
		assertEquals(stringEqualsFile(engine.getTransformedString(), TEST_DIRECTORY + INLINE_TEMPLATE), true);
	}
	
	public void testInvalidFile() {
		S60Engine engine = null;
		
		try {
			engine = new S60Engine(PLUGIN_DIRECTORY + "invalidfile.test");
		
			engine.setTransformRules(transformRules);
		} catch(IOException ioe) {
			return;
		}
		
		fail("Exception not thrown!");
	}
	
	/**
	 * Helper method for testing that a transformed string equals the contents of
	 * a "correctly" transformed file.
	 * 
	 * @param string the transformed string
	 * @param file the transformed file
	 * @return true if equal, false otherwise
	 */
	private boolean stringEqualsFile(String string, String file) {
		BufferedReader stringIn = null;
		BufferedReader fileIn = null;
		
		StringReader stringReader = null;
		FileReader fileReader = null;
		
		boolean equal = true;
		
		int lineNum = 0;
		
		try {
			stringReader = new StringReader(string);
			fileReader = new FileReader(file);
			
			stringIn = new BufferedReader(stringReader);
			fileIn = new BufferedReader(fileReader);
		
			String f1Line;
			
			while((f1Line = stringIn.readLine()) != null) {
				lineNum++;
				
				String f2Line = fileIn.readLine();
				
				if(!f1Line.equals(f2Line)) {
					System.out.println("NOT EQUAL " + lineNum + ":\n" + f1Line + "\n" + f2Line + "\n");
					equal = false;
				}
			}
			
			if(fileIn.readLine() != null) {
				System.out.println("NOT EQUAL:\n" + "Too many newlines in the end of the file\n");
				equal = false;
			}
		} catch(FileNotFoundException fnfe) {
			System.out.println(fnfe);
		} catch(IOException ioe) {
			System.out.println(ioe);
		} finally {
			try {
				if(stringReader != null)
					stringReader.close();
			
				if(fileReader != null)
					fileReader.close();
			
				if(stringIn != null)
					stringIn.close();
				
				if(fileIn != null)
					fileIn.close();
			} catch(IOException ioe) {
				System.out.println(ioe);
			}
		}
		
		return equal;
	}
}
