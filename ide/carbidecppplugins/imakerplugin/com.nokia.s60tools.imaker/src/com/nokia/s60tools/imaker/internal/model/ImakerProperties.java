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
package com.nokia.s60tools.imaker.internal.model;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.nokia.s60tools.imaker.IMakerKeyConstants;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.UIVariable;
import com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry;
import com.nokia.s60tools.imaker.internal.impmodel.FileListEntry;
import com.nokia.s60tools.imaker.internal.impmodel.ImpConstants;
import com.nokia.s60tools.imaker.internal.impmodel.ImpDocument;
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelFactory;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;
import com.nokia.s60tools.imaker.internal.impmodel.util.BasicTokenizer;
import com.nokia.s60tools.imaker.internal.impmodel.util.ImpResourceImpl;
import com.nokia.s60tools.imaker.internal.managers.ProjectManager;
import com.nokia.s60tools.imaker.internal.model.iContent.ACTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory;
import com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapperPreferences;
import com.nokia.s60tools.imaker.internal.wrapper.PlatsimManager;

public class ImakerProperties extends Properties {
	public static final String SEPARATOR			  = ":;";
	public static final String IBYENTRY_FIELDS_SEPARATOR	  = ";";
	public static final String GENERATED_FILES_FOLDER = "imakerplugin";
	private static final long serialVersionUID = 1L;
	private List<UIVariable> variables=null;
	private String activeFile = null;
	private boolean used = false;

	/**
	 * Create imaker properties from the given file
	 * @param file
	 * @return
	 */
	public static ImakerProperties createFromFile(IFile file) {
		if(file!=null) {
			ImpDocument model = getModel(file);
			return convertModelToProperties(model);
		}
		return null;
	}

	private static ImakerProperties convertModelToProperties(ImpDocument model) {
		ImakerProperties properties = new ImakerProperties();
		if(model!=null) {
			//main tab basic
			addProperty(properties, IMakerKeyConstants.PRODUCT, model
					.getVariable(ImpConstants.TARGET_PRODUCT), model);
			addProperty(properties, IMakerKeyConstants.TARGET_LIST, model
					.getVariable(ImpConstants.DEFAULT_GOALS), model);
			addProperty(properties, IMakerKeyConstants.TYPE, model
					.getVariable(ImpConstants.TYPE), model);
			addProperty(properties, IMakerKeyConstants.SYMBOLFILES, model
					.getVariable(ImpConstants.USE_SYMGEN), model);
			addProperty(properties, IMakerKeyConstants.VERBOSE, model
					.getVariable(ImpConstants.VERBOSE), model);
			
			//platsim variables
			addProperty(properties, IMakerKeyConstants.PLATSIM_INSTANCE, model
					.getVariable(ImpConstants.PLATSIM_INSTANCE), model);
			addProperty(properties, IMakerKeyConstants.PLATSIM_RUN, model
					.getVariable(ImpConstants.PLATSIM_RUN), model);
			
			//additional variables
			EList<Variable> vars = model.getVariables();
			StringBuffer sb = new StringBuffer();
			Variable variable;
			for (int i = 0; i < vars.size(); i++) {
				variable = vars.get(i);
				sb.append(variable.getName()+"="+variable.getValue());
				if(i<vars.size()-1) {
					sb.append(" ");
				}
			}
			if(!"".equals(sb.toString())) {
				properties.put(IMakerKeyConstants.ADDITIONAL_PARAMETERS, sb.toString());				
			}
			
			//files
			EList<OverrideFiles> files = model.getOrideFiles();
			List<IbyEntry> ibyEntries = new ArrayList<IbyEntry>();
			if (!files.isEmpty()) {
				OverrideFiles of = files.get(0);
				EList<FileListEntry> entries = of.getEntries();
				for (int i = 0; i < entries.size(); i++) {
					FileListEntry fEntry = entries.get(i);
					EList<ConfigEntry> actions = fEntry.getActions();
					for (ConfigEntry action : actions) {
						IbyEntry iby = IContentFactory.eINSTANCE.createIbyEntry();
						iby.setEnabled(true);
						iby.setFile(fEntry.getSource());
						iby.setTarget(fEntry.getTarget());
						if(action.getLocation().equals(IMAGESECTION.ANY.getLiteral())) {
							iby.setLocation(IMAGESECTION.ANY);							
						} else {
							iby.setLocation(IMAGESECTION.get(action.getLocation()));														
						}
						iby.setAction(ACTION.get(action.getAction()));
						ibyEntries.add(iby);
					}
				}
			}
			if(!ibyEntries.isEmpty()) {
				properties.put(IMakerKeyConstants.DEBUGFILES, ibyEntries);
			}
		}
		return properties;
	}

	private static void addProperty(ImakerProperties properties,
			String key, Variable var, ImpDocument model) {
		if(var!=null) {
			model.getVariables().remove(var);
			properties.put(key, var.getValue());
		}
	}

	private static ImpDocument getModel(IFile file) {
		ResourceSetImpl rs = new ResourceSetImpl();
		
		URI uri = URI.createFileURI(file.getLocation().toFile().getAbsolutePath());
		Resource resource = rs.createResource(uri);
		try {
			resource.load(null);
			EList<EObject> contents = resource.getContents();
			return (ImpDocument) contents.get(0);	
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create imaker properties from file
	 * @param file
	 * @return
	 */
	public void saveToFile(File file) {
		if(file!=null) {
			ImpDocument model = getModel();
			Resource resource = getResource(file);
			resource.getContents().add(model);
			try {
				resource.save(null);
			} catch (IOException e) {
				// TODO log?
				e.printStackTrace();
			}	
		}
	}
	
	private Resource getResource(File file) {
		ResourceSetImpl rs = new ResourceSetImpl();
		URI uri = URI.createFileURI(file.getAbsolutePath());
		Resource resource = rs.createResource(uri);
		return resource;	
	}

	/**
	 * Constract Impmodel from these properties
	 * @return
	 */
	private ImpDocument getModel() {
		Properties clone = (Properties) clone();
		ImpmodelFactory factory = ImpmodelFactory.eINSTANCE;
		ImpDocument doc = factory.createImpDocument();

		//save variables
		addBasicVariables(clone, doc,factory);
		addAdditionalVariables(clone, doc,factory);
		
		//save platsim variables
		addPlatsimVariables(clone, doc,factory);
		
		//save override files
		Object oEntries = clone.remove(IMakerKeyConstants.DEBUGFILES);
		if(oEntries!=null) {
			List<IbyEntry> entries = (List<IbyEntry>) oEntries;
			OverrideFiles files = factory.createOverrideFiles();
			OverrideConfiguration confs = factory.createOverrideConfiguration();
			
			for (IbyEntry ibyEntry : entries) {
				if(!ibyEntry.isEnabled()) {
					continue; //Notice skipping disabled entries
				}
				FileListEntry fileEntry = factory.createFileListEntry();
				ConfigEntry configEntry = factory.createConfigEntry();
				fileEntry.setSource(ibyEntry.getFile());
				fileEntry.setTarget(ibyEntry.getTarget());
				if(!containsComponentEntry(files,fileEntry)) {
					files.getEntries().add(fileEntry);					
				}
				
				//
				configEntry.setTarget(ibyEntry.getTarget());
				configEntry.setAction(ibyEntry.getAction().getLiteral());
				configEntry.setLocation(ibyEntry.getLocation().getLiteral());
				confs.getEntries().add(configEntry);
			}
			
			if(!files.getEntries().isEmpty()) {
				doc.getOrideConfs().add(confs);
				doc.getOrideFiles().add(files);
			}
		}
		
		//save override confs
		return doc;
	}

	private boolean containsComponentEntry(OverrideFiles files,
			FileListEntry fileEntry) {
		boolean found = false;
		for (FileListEntry entry : files.getEntries()) {
			if (entry.getSource().equals(fileEntry.getSource())
					&& entry.getTarget().equals(fileEntry.getTarget())) {
				found = true;
			}
		}
		return found;
	}

	private void addPlatsimVariables(Properties clone, ImpDocument doc,
			ImpmodelFactory factory) {
		addVariable(doc, factory, ImpConstants.PLATSIM_INSTANCE, (String) clone
				.remove(IMakerKeyConstants.PLATSIM_INSTANCE));
		addVariable(doc, factory, ImpConstants.PLATSIM_RUN, (String) clone
				.remove(IMakerKeyConstants.PLATSIM_RUN));
	}

	private void addAdditionalVariables(Properties clone, ImpDocument doc,
			ImpmodelFactory factory) {
		String adds = (String) clone.remove(IMakerKeyConstants.ADDITIONAL_PARAMETERS);
		if(adds==null) return;
		BasicTokenizer bt = new BasicTokenizer(adds);
		while (bt.hasMoreTokens()) {
			String token = bt.nextToken();
			String[] comps = token.split("=");
			if(comps.length==2) {
				int start = 0;
				int end = comps[1].length();
				if(comps[1].charAt(0)=='"') start++;
				if(comps[1].charAt(comps[1].length()-1)=='"') end--;
				addVariable(doc, factory, comps[0], comps[1].substring(start,end));
			}
		}
	}

	private void addBasicVariables(Properties clone, ImpDocument doc, ImpmodelFactory factory) {
		addVariable(doc, factory, ImpConstants.TARGET_PRODUCT, (String) clone
				.remove(IMakerKeyConstants.PRODUCT));
		addVariable(doc, factory, ImpConstants.DEFAULT_GOALS, (String) clone
				.remove(IMakerKeyConstants.TARGET_LIST));
		addVariable(doc, factory, ImpConstants.TYPE, (String) clone
				.remove(IMakerKeyConstants.TYPE));
		addVariable(doc, factory, ImpConstants.USE_SYMGEN, (String) clone
				.remove(IMakerKeyConstants.SYMBOLFILES));
		addVariable(doc, factory, ImpConstants.VERBOSE, (String) clone
				.remove(IMakerKeyConstants.VERBOSE));
//		addVariable(doc, factory, ImpConstants.VERBOSE, (String) clone.remove(IMakerKeyConstants.VERBOSE));
//		addVariable(doc, factory, ImpConstants.VERBOSE, (String) clone.remove(IMakerKeyConstants.VERBOSE));
	}

	private void addVariable(ImpDocument doc, ImpmodelFactory factory, String name, String value) {
		Variable var;
		if (value!=null) {
			var = factory.createVariable();
			var.setName(name);
			var.setValue(value);
			doc.getVariables().add(var);
		}
	}

	public ImakerProperties() {}

	
	/**
	 * Parse the contents of this properties file and create imaker command that is executable
	 * @param selectedProject
	 * @param workdir Place where the temporary iby/mk files will be placed
	 * @return
	 */
	public List<String> parseImakerCommand(IProject selectedProject, IPath workdir) {
		String drive = IMakerUtils.getProjectRootLocation(selectedProject);
		ArrayList<String> command = new ArrayList<String>();

		String makefile = (String) get(IMakerKeyConstants.MAKEFILE);
		if(makefile!=null) {
			appendCommand(command,IMakerWrapperPreferences.ARG_FETCH_CONFIGURATION);
			appendCommand(command,makefile);
		}
		addAdditionalParameters(command,(String) get(IMakerKeyConstants.TARGET_LIST)," ");
		String platsim = (String) get(IMakerKeyConstants.PLATSIM_INSTANCE);
		if(platsim!=null) {
			command.add(IMakerKeyConstants.PLATSIM_INSTANCE +"="+parse(platsim,drive));
			String run = (String)get(IMakerKeyConstants.PLATSIM_RUN);
			if(run!=null&&run.equals("true")) {
				command.add(IMakerKeyConstants.PLATSIM_RUN +"=1");		
			}
		}
		addAdditionalParameters(command,(String) get(IMakerKeyConstants.ADDITIONAL_PARAMETERS),SEPARATOR);
		addAdditionalParameters(command, (String) get(IMakerKeyConstants.MODIFIED_SETTINGS),SEPARATOR);
		String type = (String) get(IMakerKeyConstants.TYPE);
		command.add(IMakerWrapperPreferences.TYPE+"="+type);
		String verb = (String) get(IMakerKeyConstants.VERBOSE);
		if(verb!=null) {
			command.add(Messages.getString("Flashmenu.9"));
		}
		
		// parse iby files and makefile
		String str = (String)get(IMakerKeyConstants.DEBUGFILES);
		if(str!=null&&!str.equals("")) {
			List<IbyEntry> entries = new ArrayList<IbyEntry>();
			String[] entriesStr = str.split(ImakerProperties.SEPARATOR);
			for (int i = 0; i < entriesStr.length; i++) {
				String entryStr = entriesStr[i];
				if(entryStr!=null&&!entryStr.equals("")) {
					IbyEntry entry = IContentFactory.eINSTANCE.createEntryFromString(entryStr);
					if(entry.isEnabled()) {
						entries.add(entry);						
					}
				}
			}
			ProjectManager pm = new ProjectManager(selectedProject);
			IPath mkfile = IMakerUtils.createIbyFiles(entries, pm, workdir);
			if(mkfile!=null) {
				appendCommand(command,IMakerWrapperPreferences.ARG_FETCH_CONFIGURATION);
				appendCommand(command,"\""+mkfile.toOSString()+"\"");
			}
		}

		return command;
	}

	private String parse(String instance, String env) {
		if(instance==null) {
			instance="1";
		}
		if(instance.equals(Messages.getString("PlatsimTab.0"))) {
			PlatsimManager pm = new PlatsimManager(env);
			int max = 1;
			List<String> instances = pm.getInstances();
			for (String ins : instances) {
				int intIns = Integer.parseInt(ins);
				if(intIns>max) max = intIns;
			}
			return String.valueOf(max+1);
		}
		return instance;
	}

	private void addAdditionalParameters(ArrayList<String> command,
			String adds, String sep) {
		if(adds==null||adds.equals("")) { //$NON-NLS-1$
			return;
		}
		String[] parts = adds.split(sep); //$NON-NLS-1$
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			if(!part.equals("")) { //$NON-NLS-1$
				appendCommand(command, part);
			}
		}
	}

	private void appendCommand(ArrayList<String> command, String value) {
		if((value!=null)&&!(value.equals(""))) { //$NON-NLS-1$
			command.add(value.trim());
		}
	}
	
	private void updateVariables() {
		if(variables==null) {
			return;
		}
		List<UIVariable> includeVariables = IMakerUtils.getCommandlineIncludeVariables(variables);
		List<String> variablesToStrings = IMakerUtils.convertVariablesToStrings(includeVariables);

		StringBuilder settings = new StringBuilder();

		for (Iterator<String> iterator = variablesToStrings.iterator(); iterator
		.hasNext();) {
			String setting = iterator.next();
			settings.append(setting+SEPARATOR); //$NON-NLS-1$
		}

		String string = settings.toString();
		if(!string.equals("")) { //$NON-NLS-1$
			put(IMakerKeyConstants.MODIFIED_SETTINGS, string);
		}
	}

	public void setVariables(List<UIVariable> variables) {
		this.variables = variables;
		updateVariables();
	}

	public void setActiveFile(String path) {
		this.activeFile = path;
	}

	public String getActiveFile() {
		return activeFile;
	}

	public void setUsed(boolean b) {
		this.used  = b;
	}
	
	public boolean isUsed() {
		return this.used;
	}

	@Override
	public synchronized void store(OutputStream out, String comments)
			throws IOException {
		ImpDocument model = getModel();
		URI uri = URI.createFileURI("sample.imp");
		ImpResourceImpl res = new ImpResourceImpl(uri);

		res.getContents().add(model);

		res.save(out);
	}

	
}
