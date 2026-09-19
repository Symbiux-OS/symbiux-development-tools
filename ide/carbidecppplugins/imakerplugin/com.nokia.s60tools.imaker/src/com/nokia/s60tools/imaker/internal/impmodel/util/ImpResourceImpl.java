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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import com.nokia.s60tools.imaker.internal.impmodel.Comment;
import com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry;
import com.nokia.s60tools.imaker.internal.impmodel.FileListEntry;
import com.nokia.s60tools.imaker.internal.impmodel.ImpConstants;
import com.nokia.s60tools.imaker.internal.impmodel.ImpDocument;
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelFactory;
import com.nokia.s60tools.imaker.internal.impmodel.LineNumberContainer;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;

public class ImpResourceImpl extends ResourceImpl {
	private ImpmodelFactory factory = ImpmodelFactory.eINSTANCE;

	public ImpResourceImpl(URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		ImpDocument document = factory.createImpDocument();
		//load the contents of the resource here
		InputStreamReader isr = new InputStreamReader(inputStream, ImpConstants.FILE_ENCODING);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		int lineNumber = 0;
		boolean breaked = false;
		StringBuffer breakedBuilder = new StringBuffer();
		while ((line = br.readLine())!=null) {
			++lineNumber;
			if(line.endsWith(ImpConstants.LINE_SPLITTER)) {
				breaked = true;
				breakedBuilder.append(line.substring(0, line.length()-1));
				continue;
			}
			if(breaked) {
				breakedBuilder.append(line);
				line = breakedBuilder.toString();
				breaked = false;
			}
			if(isComment(line)) {
				addComment(document,line.substring(1)).setLineNumber(lineNumber);
			} else if(isVariable(line)) {
				addVariable(document,line).setLineNumber(lineNumber);
			} else if(isOrideFilesStart(line)) {
				int startLine = lineNumber;
				List<String> entries = new ArrayList<String>();
				while((line = br.readLine())!=null) {
					lineNumber++;
					if (line.equals(ImpConstants.DEFINE_END)) {
						break;
					}
					entries.add(line);
				}
				addOrideFiles(document,entries).setLineNumber(startLine);
			} else if(isOrideConfsStart(line)) {
				int startLine = lineNumber;
				List<String> entries = new ArrayList<String>();
				while((line = br.readLine())!=null) {
					lineNumber++;
					if (line.equals(ImpConstants.DEFINE_END)) {
						break;
					}
					entries.add(line);
				}
				addOrideConf(document,entries).setLineNumber(startLine);
			} else {}
		}
		connectFilesAndConfigs(document);
		getContents().add(document);
	}

	private void connectFilesAndConfigs(ImpDocument document) {
		EList<OverrideFiles> files = document.getOrideFiles();
		EList<OverrideConfiguration> confs = document.getOrideConfs();
		if(!files.isEmpty()&&!confs.isEmpty()) {
			OverrideFiles of = files.get(0);
			OverrideConfiguration oc = confs.get(0);
			EList<FileListEntry> entries = of.getEntries();
			for (FileListEntry fEntry : entries) {
				connect(fEntry,oc);
			}
		}
	}

	private void connect(FileListEntry fEntry, OverrideConfiguration oc) {
		EList<ConfigEntry> entries = oc.getEntries();
		for (ConfigEntry cEntry : entries) {
			if(fEntry.getTarget().endsWith(cEntry.getTarget())) {
				fEntry.getActions().add(cEntry);
			}
		}
	}

	private boolean isOrideConfsStart(String line) {
		if(line.length()>1&&line.startsWith(ImpConstants.ORIDECONF_START)) {
			return true;
		}
		return false;
	}
	
	private LineNumberContainer addOrideConf(ImpDocument document, List<String> entries) {
		OverrideConfiguration oc = factory.createOverrideConfiguration();
		for (String entry : entries) {
			ConfigEntry ce = factory.createConfigEntry();
			String[] parts = entry.split(ImpConstants.ENTRY_SEPARATOR);
			ce.setTarget(parts[0].trim());
			ce.setAction(parts[1].trim());
			ce.setLocation(parts[2].trim());
			oc.getEntries().add(ce);
		}
		document.getOrideConfs().add(oc);
		return oc;
	}
	
	private boolean isOrideFilesStart(String line) {
		if(line.length()>1&&line.startsWith(ImpConstants.ORIDEFILES_START)) {
			return true;
		}
		return false;
	}

	private LineNumberContainer addOrideFiles(ImpDocument document, List<String> entries) {
		OverrideFiles overrideFiles = factory.createOverrideFiles();		
		for (String entry : entries) {
			FileListEntry fle = factory.createFileListEntry();
			BasicTokenizer pt = new BasicTokenizer(entry);
			if(pt.countTokens()==2) {
				fle.setSource(pt.nextToken());
				fle.setTarget(pt.nextToken());				
				overrideFiles.getEntries().add(fle);
			}
		}
		document.getOrideFiles().add(overrideFiles);
		return overrideFiles;
	}


	private boolean isVariable(String line) {
		if(line.length()>1&&line.contains(ImpConstants.VARIABLE_SEPARATOR)) {
			return true;
		}
		return false;
	}

	private LineNumberContainer addVariable(ImpDocument document, String line) {
		String[] parts = line.split(ImpConstants.VARIABLE_SEPARATOR);
		Variable var = factory.createVariable();
		if(parts.length==2) {
			var.setName(parts[0].trim());
			var.setValue(parts[1].trim());
			document.getVariables().add(var);
		}
		return var;
	}

	private LineNumberContainer addComment(ImpDocument document, String content) {
		Comment comment = factory.createComment();
		comment.setComment(content);
		document.getComments().add(comment);
		return comment;
	}

	private boolean isComment(String line) {
		if(line.length()>1&&line.startsWith(ImpConstants.COMMENT_START)) {
			return true;
		}
		return false;
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		if(!getContents().isEmpty()) {
			String separator = System.getProperty("line.separator");
			OutputStreamWriter ow = new OutputStreamWriter(outputStream, ImpConstants.FILE_ENCODING);
			BufferedWriter bw = new BufferedWriter(ow);
			ImpDocument doc = (ImpDocument) getContents().get(0);
			List<LineNumberContainer> content = new ArrayList<LineNumberContainer>();
			//save comments
			for (Comment comment : doc.getComments()) {
				content.add(comment);
			}
			//save variables
			for (Variable var : doc.getVariables()) {
				content.add(var);
			}

			//save override files
			for (OverrideFiles orid : doc.getOrideFiles()) {
				content.add(orid);
			}
			
			//save override configurations
			for (OverrideConfiguration orid : doc.getOrideConfs()) {
				content.add(orid);
			}

			int max = 0;
			for (LineNumberContainer item : content) {
				if(item.getLineNumber()>max) {
					max = item.getLineNumber();
				}
			}
			List<LineNumberContainer> temp = new ArrayList<LineNumberContainer>();
			for (int i = 0; i < max; i++) {
				temp.add(null);
			}
			for (LineNumberContainer item : content) {
				temp.add(item.getLineNumber(), item);
			}

			for (int i = 0; i < temp.size(); i++) {
				Object item = temp.get(i);
				if(item!=null) {
					bw.write(item.toString());
				}
				bw.write(separator);					
			}
			bw.flush();
			bw.close();
		}
	}

	public void save(OutputStream out) {
		try {
			doSave(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
