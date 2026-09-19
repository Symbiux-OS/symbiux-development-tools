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

package com.nokia.s60tools.imaker.internal.impmodel.util;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@SuppressWarnings("unchecked")
public class BasicTokenizer implements Enumeration {
	private String inputString;
	private List<String> tokens=null;
	private int currentToken = 0;
	
	public BasicTokenizer(String str) {
		if(str==null) {
			str = "";
		}
		inputString = str.trim();
		inputString = inputString.replaceAll("\\s+", " ");
		inputString = inputString.replaceAll("\\s*=\\s*", "=");
		tokens = new ArrayList<String>();
		Tokenize();
	}

	private void Tokenize() {
		int start = 0;
		int end = 0;
		while ((start = getStartIndex(start))<inputString.length()) {
			if(inputString.charAt(start)=='"') {
				end = getEndIndex(start+1,true);
				tokens.add(inputString.substring(start,end+1).trim());
				start = end + 1; // skip end quote
			} else {
				end = getEndIndex(start,false);
				tokens.add(inputString.substring(start,end).trim());
				start = end+1;
			}
		}
	}

	private int getStartIndex(int start) {
		while ((start < inputString.length())
				&& (inputString.charAt(start) == ' ')) {
			start++;
		}
		return start;
	}

	private int getEndIndex(int start, boolean inQuotes) {
		int end = start;
		if (inQuotes) {
			end = inputString.indexOf('"', start);
			if(end == -1) {
				end = inputString.indexOf(' ', start);				
			}
		} else {
			while (end < inputString.length()) {
				if (inputString.charAt(end) == '"') {
					int endQ = inputString.indexOf('"',end+1);
					if (endQ==-1) {
						end++;
						continue;
					} else {
						end = endQ+1;
						break;
					}
				} else if (inputString.charAt(end) == ' ') {
					break;
				} else {
					end++;
				}
			}
		}
		if(end==-1) {
			end = inputString.length();
		}
		return end;
	}

	/**
	 *	Returns the next token from the input string.
	 *
	 *	@return			String		the current token from the input string.
	 */
	public String nextToken() {
		if(currentToken<tokens.size()) {
			String token = tokens.get(currentToken);
			currentToken++;
			return token;
		}
		return null;
	}

	/**
	 *	Checks whether any token is left in the input string
	 *
	 *	@return			boolean		true, if any token is left
	 */
	public boolean hasMoreTokens() {
		return currentToken < tokens.size();
	}

	/**
	 *	Checks whether any token is left in the input string
	 *
	 *	@return			boolean		true, if any token is left
	 */
	public boolean hasMoreElements()
	{
		return hasMoreTokens();
	}

	/**
	 *	Returns the next token from the input string.
	 *
	 *	@return			Object		the current token from the input string.
	 */
	public Object nextElement()
	{
		return nextToken();
	}

	/**
	 *	Total number of tokens present in the input string
	 *
	 *	@return			int		total number of tokens
	 */
	public int countTokens() {
		return tokens.size();
	}
}