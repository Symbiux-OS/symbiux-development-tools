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

package com.nokia.s60tools.imaker.internal.impmodel.tests;

import java.util.StringTokenizer;

import com.nokia.s60tools.imaker.internal.impmodel.util.BasicTokenizer;

import junit.framework.TestCase;

public class TestBasicTokenizer extends TestCase {

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testNullInput() throws Exception {
		BasicTokenizer bt = new BasicTokenizer(null);
		assertEquals(0, bt.countTokens());
	}
	
	public void testEmptyInput() throws Exception {
		BasicTokenizer bt = new BasicTokenizer("");
		assertEquals(0, bt.countTokens());
	}
	
	public void testStringTokenizerUsage() throws Exception {
		String input = "\tone \ttwo ";
		StringTokenizer st = new StringTokenizer(input);
		assertEquals(2, st.countTokens());
		assertEquals("one", st.nextToken());
		assertEquals("two", st.nextToken());
	}
	
	public void testBasicTokenizerCountTokens() throws Exception {
		String input = "one two tree";
		BasicTokenizer bt = new BasicTokenizer(input);
		assertEquals(3, bt.countTokens());
	}
	
	public void testBasicTokenizerCountTokensAdvanced() throws Exception {
		String input = "\"one two\" tree";
		BasicTokenizer pt = new BasicTokenizer(input);
		assertEquals(2, pt.countTokens());
		
		input = "one \"two tree\"";
		pt = new BasicTokenizer(input);
		assertEquals(2, pt.countTokens());

		input = "\"one two\" \"tree four\"";
		pt = new BasicTokenizer(input);
		assertEquals(2, pt.countTokens());
		assertEquals("\"one two\"", pt.nextToken());
		assertEquals("\"tree four\"", pt.nextToken());
	}

	public void testBasicTokenizerWithStringWithOneVariable() throws Exception {
		BasicTokenizer bt = new BasicTokenizer("name=value");
		assertEquals(1, bt.countTokens());
		assertEquals("name=value", bt.nextToken());
		
		bt = new BasicTokenizer("name =value");
		assertEquals(1, bt.countTokens());
		assertEquals("name=value", bt.nextToken());
		
		bt = new BasicTokenizer("name = value");
		assertEquals(1, bt.countTokens());
		assertEquals("name=value", bt.nextToken());	

		bt = new BasicTokenizer("name = \"value\"");
		assertEquals(1, bt.countTokens());
		assertEquals("name=\"value\"", bt.nextToken());	
	}

	public void testBasicTokenizerWithStringWithSubstring() throws Exception {
		BasicTokenizer bt = new BasicTokenizer("\"this is a substring\"");
		assertEquals(1, bt.countTokens());
		assertEquals("\"this is a substring\"", bt.nextToken());
	}

	public void testBasicTokenizerWithStringWithIncompleteSubstring() throws Exception {
		BasicTokenizer bt = new BasicTokenizer("\"sample substring");
		assertEquals(2, bt.countTokens());
		assertEquals("\"sample", bt.nextToken());
		assertEquals("substring", bt.nextToken());
	}

	public void testBasicTokenizerWithStringWithOneVariableWithStringValue() throws Exception {
		BasicTokenizer bt = new BasicTokenizer("name = \"long value with spaces\"");
		assertEquals(1, bt.countTokens());
		assertEquals("name=\"long value with spaces\"", bt.nextToken());		
	}
	


	public void testBasicTokenizerMultipleVariables() throws Exception {
		String input = "name1 = value1 name2= \"value 2\" name3 =value3";
		BasicTokenizer bt = new BasicTokenizer(input);
		assertEquals(3, bt.countTokens());
		assertEquals("name1=value1", bt.nextToken());
		assertEquals("name2=\"value 2\"", bt.nextToken());
		assertEquals("name3=value3", bt.nextToken());
	}
}
