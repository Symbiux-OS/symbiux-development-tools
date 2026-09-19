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


package com.nokia.s60tools.imaker.internal.wrapper;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreAlreadyRunningException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreCancelledException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.IMaker;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Interface;
import com.nokia.s60tools.imaker.internal.iqrf.Result;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;
import com.nokia.s60tools.imaker.internal.iqrf.wrapper.IQRFWrapper;

/**
 * This class implements the IIMakerWrapper interface which is used
 * to interface with iMaker core.
 * 
 * @version 1.0
 */
public class IMakerWrapper implements IIMakerWrapper {
	/** Shared singleton instance */
//	private static IIMakerWrapper instance;

	/** Public instance variables */
	public PipedOutputStream pos = null;
	public PipedInputStream pis = null;

	/** Public instance variables */
	private Boolean builderRunning = false;
	private List<String> params = null;
	private IProgressMonitor monitor = null;
	private int exitValue = 0;
	private boolean runVerdict;
	private String dProduct = null;
//	private String dTarget = null;
	private Pattern blockBegin = Pattern.compile("-{30,}");

	private List<String> tool;
	private IMakerCoreExecutionException lastImakerException;

	/**
	 * Default constructor
	 */
	public IMakerWrapper(List<String> tool) {
		params = new ArrayList<String>();
		this.tool = tool;
		this.lastImakerException = new IMakerCoreExecutionException("No configurations loaded!");
	}

	public boolean isRunning() {
		return builderRunning;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imakerplugin.wrapper.IIMakerWrapper#getIMakerCoreVersion()
	 */
	public String getIMakerCoreVersion() throws IMakerCoreNotFoundException, IMakerCoreExecutionException {
		verifyExists();
		params.clear();
		params.add(IMakerWrapperPreferences.CMD_FETCH_VERSION);
		List<String> version = executeCommand(params,null);
		String vers = "";
		for (String line : version) {
			if(line.startsWith("iMaker")) {
				vers = line;
				break;
			}
		}
		return vers;
	}

	private void verifyExists() throws IMakerCoreNotFoundException {
		if(!IMakerUtils.iMakerCoreExists(tool.get(0))) {
			IMakerCoreNotFoundException e = new IMakerCoreNotFoundException();
			String newMessage = e.getMessage() +" "+ Messages.getString("IMakerWrapper.27").replace("xxx", tool.get(0));
			throw new IMakerCoreNotFoundException(newMessage);
		}
	}

	private void verifyCompatibility() throws IMakerCoreNotFoundException, IMakerCoreExecutionException {
		//verify exists
		String minVersion = Messages.getString("PreferencesTab.22"); //$NON-NLS-1$
		String curVersion = IMakerUtils.parseIMakerVersion(getIMakerCoreVersion());
		if(curVersion==null) throw new IMakerCoreExecutionException("Unable to query version information!");
		if(minVersion.compareTo(curVersion)>0) {
			String msg = Messages.getString("Error.3");
			msg = msg.replace("xxx", minVersion);
			msg = msg.replace("yyy", curVersion);			
			throw new IMakerCoreExecutionException(msg);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imakerplugin.wrapper.IIMakerWrapper#getConfigurations(java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<UIConfiguration> getConfigurations(IProgressMonitor monitor, String makefile) 
	throws IMakerCoreExecutionException, IMakerCoreNotFoundException {
		this.monitor = monitor;
		verifyCompatibility();
		List<UIConfiguration> configurations = getConfigurations(makefile);
		return configurations;
	}

	private List<UIConfiguration> getConfigurations(String makefile) throws IMakerCoreExecutionException {
		List<UIConfiguration> uiConfigs = new ArrayList<UIConfiguration>();
		List<String> makeFiles=null;
		loadDefaultData();
		if(makefile==null||makefile.equals("")) {
			if (monitor != null) {
				monitor.beginTask(Messages.getString("IMakerWrapper.3"), 1);
			}
			makeFiles = queryMakefiles();
			
			for (String mk : makeFiles) {
				Configuration item = createConfiguration(mk);
				uiConfigs.add(new UIConfiguration(item,this));
			}
			
			if(dProduct!=null) {
				for(UIConfiguration config: uiConfigs) {
					String pname = config.getConfigurationName();
					if(pname != null && pname.equals(dProduct)) {
						config.setDefaultConfig(true);
						break;
					}
				}			
			}
			
			if (monitor != null) {
				monitor.done();
			}
		} else {
			makeFiles = new ArrayList<String>();
			makeFiles.add(makefile);
			
			if (monitor != null) {
				monitor.beginTask(Messages.getString("IMakerWrapper.3"), (makeFiles.size()*2));
			}
			
			Result result = parseResult(makeFiles);
			
			// Get configurations out of the result object
			Iterator<Configuration> configIter = result.getConfigurations().iterator();
			while (configIter.hasNext()) {
				Configuration item = configIter.next();
				if(item.getTargetrefs().size()==0) continue;
				uiConfigs.add(new UIConfiguration(item,this));
			}
			if(uiConfigs.isEmpty()) {
				throw lastImakerException;
			}			
			if (monitor != null) {
				monitor.done();
			}
		}
		
		return uiConfigs;
	}

	/**
	 * fetches default configuration and target if given
	 */
	private void loadDefaultData() {
		params.clear();
		params.add(IMakerWrapperPreferences.DEFAULT_DATA);// + configPath
		Pattern product = Pattern.compile("\\s*IMAKER_CONFMK.*image_conf_(.*)\\.mk.\\s*");
		try {
			List<String> lines = executeCommand(params,null);
			for (String line : lines) {
				Matcher matcher = product.matcher(line);
				if(matcher.matches()) {
					String pr = matcher.group(1);
					if(!pr.equals("")) {
						dProduct = pr;
						break;
					}
				}
			}
		} catch (IMakerCoreExecutionException e) {
			e.printStackTrace();
		}
	}

	private List<String> queryMakefiles() throws IMakerCoreExecutionException {
		List<String> makeFiles;
		params.clear();
		params.add(IMakerWrapperPreferences.CMD_FETCH_CONFIGURATIONS);// + configPath
		makeFiles = executeCommand(params,null);
		
		// Remove redundant lines of information from makeFile list
		if ((makeFiles != null) && (makeFiles.size() > 0)) {
			List<String> tmpMakeFiles = new ArrayList<String>();
			
			Iterator<String> iter = makeFiles.iterator();
			while (iter.hasNext()) {
				String makefile = iter.next().toLowerCase();
				if (makefile.matches(
						IMakerWrapperPreferences.MAKEFILE_LIST_PATTERN)) {
					tmpMakeFiles.add(makefile);					
				}
			}
			makeFiles = tmpMakeFiles;
		}
		return makeFiles;
	}

	public UIConfiguration getConfiguration(List<String> parameters,
			IProgressMonitor mon) throws IMakerCoreExecutionException,
			IMakerCoreNotFoundException {
		this.monitor = mon;
		verifyCompatibility();
		if(parameters==null||parameters.size()<2) {
			return null;
		}
		if (monitor != null) {
			monitor.beginTask(Messages.getString("IMakerWrapper.4"), 2); //$NON-NLS-1$
		}
		
		params.clear();
		params.add(parameters.remove(0));
		params.add(IMakerWrapperPreferences.ARG_FETCH_CONFIGURATION);
		String makefile = parameters.remove(0);
		params.add(makefile);
		params.add(IMakerWrapperPreferences.CMD_FETCH_CONFIGURATION);
		params.addAll(parameters);
		
		builderRunning=true;
		List<String> settingsList = executeCommand(params,null);
		builderRunning=false;
		
		ArrayList<ConfigurationElement> elements = new ArrayList<ConfigurationElement>();
		ArrayList<Setting> settings = new ArrayList<Setting>();
		
		parseConfigurations(settingsList, elements, settings);
		
		Configuration config = createConfiguration(makefile);
		setUpConfiguration(config, settings);
		
		if (monitor != null) {
			monitor.done();
		}
		return new UIConfiguration(config,this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imakerplugin.wrapper.IIMakerWrapper#isBuilderRunning()
	 */
	public Boolean isBuilderRunning() {
		return builderRunning;
	}


	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imakerplugin.wrapper.IIMakerWrapper#flashImage(java.util.List)
	 */
	public boolean flashImage(List<String> cmdParams) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.imakerplugin.wrapper.IIMakerWrapper#buildImage(java.util.List)
	 */
	public boolean buildImage(List<String> cmdParams, OutputStream out) throws IMakerCoreExecutionException, 
	IMakerCoreAlreadyRunningException, IMakerCoreNotFoundException {
		verifyCompatibility();
		builderRunning = true;
		List<String> ret = executeCommand(cmdParams,out);
		builderRunning = false;
		return getReturnStatus(ret);
	}

	private boolean getReturnStatus(List<String> ret) {
		if(exitValue==0) {
			//old imaker check output
			try {
				String vers = ret.get(0);
				String verNumber = IMakerUtils.parseIMakerVersion(vers);
				String versionNumber = verNumber;
				String[] parts = versionNumber.split("\\.");
				if(Integer.parseInt(parts[0])<=8&&Integer.parseInt(parts[1])<35&&Integer.parseInt(parts[2])<3) {
					runVerdict = validateBuildResult(ret);
				} else {
					runVerdict = true;
				}
			} catch (Exception e) {
				return runVerdict = true;
			}
			return runVerdict;
		} else {
			return (runVerdict=false);
		}
	}

	/**
	 * Method returns configuration information as an XMI document.
	 * 
	 * @return An XMI document describing configurations.
	 * @throws IMakerCoreExecutionException 
	 */
	public String getConfigurationsAsXMI() throws IMakerCoreExecutionException {
		// First create a result object and then use it to query
		// the EMF for xmi document.

		List<String> makeFiles = queryMakefiles();
		Result result=null;
		result = parseResult(makeFiles);

		IMaker imaker = IQRFFactory.eINSTANCE.createIMaker();
		imaker.setResult(result);
		imaker.setQuery(IMakerWrapperPreferences.CMD_FETCH_CONFIGURATIONS);
		IQRFWrapper wrapper = new IQRFWrapper();
		String xmi = wrapper.getXMLDocument(imaker);
		return xmi;
	}

	/**
	 * Calls the iMaker on a command line and reads the output.
	 * @param out 
	 * 
	 * @param  cmd additional arguments to be passed to the iMaker core.
	 * @return Output of iMaker as list of Strings.
	 */
	private List<String> executeCommand(List<String> params, OutputStream out) throws IMakerCoreExecutionException {
		List<String> cmd = prepareCommand(params);

		// Try to start a process to execute iMaker
		try {
			// Finally build a new ProcessBuilder with cmds.
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Map<String, String> environment = builder.environment();
			environment.put(IMakerWrapperPreferences.IMAKER_EXITSHELL, "1");
			// In windows environment, the working directory of
			// ProcessBuilder must point to the drive/path where
			// the imaker is run. Usually x:\
			String com = cmd.get(0);
			if (com.charAt(1) == IMakerWrapperPreferences.MAKEFILE_DRIVE_DESIGNATOR) {
				String dir = com.substring(0, 2);
				builder.directory(new File(dir));
			}

			builder.redirectErrorStream(true);
			Process process = builder.start();

			// Start reading the output
			StreamGobbler gobbler = new StreamGobbler(process.getInputStream(), out);
			gobbler.start();
			
			ArrayList<String> output;
			try {
				exitValue = process.waitFor();
				gobbler.join();
				output = gobbler.getOutput();
				exitValue  = process.exitValue();
				if(exitValue!=0) {
					StringBuilder error = new StringBuilder();
//					for (String str : output) {
//						error.append(str+"\n");
//					}
					
					error.append(Messages.getString("IMakerWrapper.28") + " ");
					for (String str : cmd) {
						error.append(str+" ");
					}
					throw new IMakerCoreExecutionException(error.toString());
				}
				return output;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException ioe) {
			throw new IMakerCoreExecutionException(ioe.getMessage());
		}
		throw new IMakerCoreExecutionException();
	}

	private List<String> prepareCommand(List<String> prm) {
		List<String> cmd = new ArrayList<String>();
		cmd.addAll(tool);
		cmd.addAll(prm);
		return cmd;
	}

	/**
	 * Validates the output from iMaker to determine if the
	 * operation was successful.
	 * 
	 * @param  out  output from the iMaker.
	 * @return true if the iMaker exited successfully based
	 *              on the output, otherwise false.
	 */
	private boolean validateBuildResult(List<String> out) {
		if(out.size()>1) {
			String last = out.get(out.size()-2) + out.get(out.size()-1);

			if(last.indexOf(Messages.getString("IMakerWrapper.22"))>-1) {
				return (runVerdict=true);
			}
			if (last.indexOf(Messages.getString("IMakerWrapper.7")) > -1) { //$NON-NLS-1$
				return (runVerdict=false);
			}
			if (last.indexOf(Messages.getString("IMakerWrapper.8")) > -1) { //$NON-NLS-1$
				return (runVerdict=false);
			}
		}
		if(out.size()==1) {
			String entry = out.get(0);
			return (runVerdict=entry.startsWith(Messages.getString("IMakerWrapper.23")));
		}
		return (runVerdict=false);
	}

	public boolean getRunVerdict() {
		return runVerdict;
	}

	/**
	 * Parses ConfigurationElement and Setting objects from the output
	 * of iMaker.
	 * 
	 * @param confData Lines of data to be parsed.
	 * @param elements ConfigurationElement list where to store results
	 * @param settings Setting list where to store results
	 */
	private void parseConfigurations(List<String> confData,
			ArrayList<ConfigurationElement> elements,
			ArrayList<Setting> settings) {

		confData = clearResultList(confData);

		String line                        = null;
		Iterator<String> lineIter          = confData.iterator();
		Pattern varValuePt                 = Pattern.compile(Messages.getString("IMakerWrapper.9")); //$NON-NLS-1$
		Pattern varBodyPt                  = Pattern.compile(Messages.getString("IMakerWrapper.10")); //$NON-NLS-1$
		Setting setting                    = null;
		ConfigurationElement configElement = null;

		String name = null;
		String desc = null;
		String values = null;
		while (lineIter.hasNext()) {
			line = lineIter.next();

			if(blockBegin.matcher(line).matches()) {
				setting       = IQRFFactory.eINSTANCE.createSetting();
				configElement = IQRFFactory.eINSTANCE.createConfigurationElement();
				name = lineIter.next();
				lineIter.next(); // ignore type line
				desc = lineIter.next();
				values = lineIter.next();
				Matcher matcher = varValuePt.matcher(name);
				if(matcher.matches()) {
					setting.setName(matcher.group(1));
					configElement.setName(setting.getName());
					setting.setValue(matcher.group(2));
				}
				matcher = varBodyPt.matcher(desc);
				if(matcher.matches()) {
					configElement.setDescription(matcher.group(2));
				}
				matcher = varBodyPt.matcher(values);
				if(matcher.matches()) {
					values = matcher.group(2);
					values = values.replaceAll(Messages.getString("IMakerWrapper.12"), "");
					values = values.replaceAll(Messages.getString("IMakerWrapper.14"), ""); //$NON-NLS-1$ //$NON-NLS-2$
					configElement.setValues(values);
				}
				setting.setConfigurationElement(configElement);
				settings.add(setting);
				elements.add(configElement);				
			}	
		}
	}

	/**
	 * Parses an interface object from a string.
	 * 
	 * @return An interface object.
	 */
	private Interface parseInterface() {
		Interface intf = IQRFFactory.eINSTANCE.createInterface();
		intf.setName(IMakerWrapperPreferences.INTERFACE_DEFAULT_NAME);
		return intf;
	}


	/**
	 * Parses targets from the output of iMaker.
	 * 
	 * @param targetStr Lines of data to be parsed.
	 * @return List of parsed Target objects.
	 */
	private ArrayList<Target> parseTargets(List<String> targetStr) {

		targetStr = clearResultList(targetStr);

		String line               = null;
		Iterator<String> lineIter = targetStr.iterator();
		ArrayList<Target> targets = new ArrayList<Target>();
		Pattern tgBodyPt          = Pattern.compile(Messages.getString("IMakerWrapper.16")); //$NON-NLS-1$
		Matcher tgValueMt         = null;
		Target target             = null;
		
		while ((lineIter != null) && lineIter.hasNext()) {
			line = lineIter.next();
			//new target
			if(blockBegin.matcher(line).matches()) {
				String name = lineIter.next();
				target = IQRFFactory.eINSTANCE.createTarget();
				target.setName(name.trim());				
				//ignore type
				lineIter.next();
				
				String desc = lineIter.next();
				tgValueMt = tgBodyPt.matcher(desc);
				if (tgValueMt.matches()) {
					if (tgValueMt.group(1).toLowerCase().equals(
							IMakerWrapperPreferences.TARGET_FIELD_DESCRIPTION)) {
						target.setDescription(tgValueMt.group(2));
					}
				}
				targets.add(target);
			}
		}
		return targets;
	}

	/**
	 * @param dirtyList
	 * @return
	 */
	private ArrayList<String> clearResultList(List<String> dirtyList) {
		ArrayList<String> cleanList = new ArrayList<String>();
		Iterator<String> lineIter = dirtyList.iterator();
		while(lineIter.hasNext()) {
			String line = lineIter.next();
			if(!line.startsWith(Messages.getString("IMakerWrapper.17"))  //$NON-NLS-1$
					&& !line.startsWith(Messages.getString("IMakerWrapper.18"))  //$NON-NLS-1$
					&& line.length() > 0 ) {
				cleanList.add(line);
			}
		}
		return cleanList;
	}

	/**
	 * Finds a target in the list of targets given as a parameter.
	 * 
	 * @param targets A List of targets to search.
	 * @param target  The target to search for.
	 * @return The target found in the list or null if not found. 
	 */
	private Target findTarget(List<Target> targets, Target target) {
		Target retTarget = null;
		Iterator<Target> targetIter = targets.iterator();

		while ((targetIter != null) && targetIter.hasNext()) {
			Target tmp = targetIter.next();
			if (tmp.getName().equals(target.getName())) {
				retTarget = tmp;
				break;
			}
		}
		return retTarget;
	}

	/**
	 * Parses a result object from a string.
	 * 
	 * @param configElements A list of configuration elements as strings.
	 * @param makeFiles      A list of makefiles as strings.
	 * @return A result object.
	 * @throws IMakerCoreExecutionException 
	 * @throws InterruptedException if the operation gets cancelled
	 */
	private Result parseResult(List<String> makeFiles) throws IMakerCoreExecutionException {
		ArrayList<Target> targets = null;
		Result result = IQRFFactory.eINSTANCE.createResult();

		Interface intf = parseInterface();

		// Process configurations/settings, targets and hwids
		if ((makeFiles != null) && (makeFiles.size() > 0)) {
			Iterator<String> iterConfigs = makeFiles.iterator();
			while ((iterConfigs != null) && iterConfigs.hasNext()) {
				// Get settings and targets for each configuration/makefile
				// and first create a Configuration object.
				String makeFile = iterConfigs.next();
				Configuration config = createConfiguration(makeFile);
				if (monitor != null) {
					monitor.subTask(Messages.getString("IMakerWrapper.19")+": "+makeFile); //$NON-NLS-1$
				}
				params.clear();
				params.add(IMakerWrapperPreferences.CMD_FETCH_TARGETS);
				params.add(IMakerWrapperPreferences.ARG_FETCH_CONFIGURATION);
				params.add(makeFile);
				List<String> prodTargets; 
				try {
					prodTargets = executeCommand(params,null);
				} catch (IMakerCoreExecutionException e) {
					this.lastImakerException = e;
					if(monitor != null) {
						monitor.worked(1);
					}
					String message = "Error while fetching configurations. " +
					e.getMessage();
					IStatus status = new Status(IStatus.ERROR,
							IMakerPlugin.getDefault().getBundle().getSymbolicName(),
							IStatus.ERROR, message, e);
					IMakerPlugin.getDefault().getLog().log(status);
					continue;
				}

				ArrayList<Target> allTargets = new ArrayList<Target>();
				targets = parseTargets(prodTargets);
				Iterator<Target> targetIter = targets.iterator();
				
				while ((targetIter != null) && targetIter.hasNext()) {
					Target target = targetIter.next();
					Target found = findTarget(allTargets, target);
					if (found == null) {
						allTargets.add(target);
						result.addTarget(target);
						config.addTargetRef(target);
					} else {
						config.addTargetRef(found);
					}
				}
				if (monitor != null) {
					monitor.worked(1);
					monitor.subTask(Messages.getString("IMakerWrapper.20")+": "+makeFile); //$NON-NLS-1$
				}
				params.set(0, IMakerWrapperPreferences.CMD_FETCH_CONFIGURATION);
				List<String> settingsList;
				try {
					settingsList = executeCommand(params,null);
				} catch (IMakerCoreExecutionException e) {
					this.lastImakerException = e;
					if(monitor != null) {
						monitor.worked(1);
					}
					String message = "Error while fetching configurations. " +
					e.getMessage();
					IStatus status = new Status(IStatus.ERROR,
							IMakerPlugin.getDefault().getBundle().getSymbolicName(),
							IStatus.ERROR, message, e);
					IMakerPlugin.getDefault().getLog().log(status);
					continue;
				}
				
				ArrayList<ConfigurationElement> elements = new ArrayList<ConfigurationElement>();
				ArrayList<Setting> settings = new ArrayList<Setting>();

				parseConfigurations(settingsList, elements, settings);

				Iterator<ConfigurationElement> iterElem = elements.iterator();
				while ((iterElem != null) && (iterElem.hasNext())) {
					intf.addConfigurationElement(iterElem.next());
				}
				setUpConfiguration(config, settings);
				result.addInterface(intf);
				result.addConfiguration(config);
				
				if(monitor != null) {
					monitor.worked(1);
					if(monitor.isCanceled()) {
						throw new IMakerCoreCancelledException("User cancellation!");
					}
				}
			}
		}
		return result;
	}

	private void setUpConfiguration(Configuration config,
			ArrayList<Setting> settings) {
		// Go through settings and find hwid data
		String[] hwids = null;
		Iterator<Setting> iterSetting = settings.iterator();
		while ((iterSetting != null) && iterSetting.hasNext()) {
			Setting setting = iterSetting.next();
			String name = setting.getName();
			if(name!=null&&name.equals(Messages.getString("IMakerWrapper.21"))) { //$NON-NLS-1$
				String str = setting.getValue();
				hwids =	str.split(IMakerWrapperPreferences.HWID_DELIMITER);
			}
		}

		iterSetting = settings.iterator();
		while ((iterSetting != null) && iterSetting.hasNext()) {
			Setting setting = iterSetting.next();
			// Check to see if it's a hwid
			String name = setting.getName();
			if (name!=null&&name.toLowerCase().equals(IMakerWrapperPreferences.HWID)) {
				// Process hwids
				String defHwid = setting.getValue();
				String hwidList = ""; //$NON-NLS-1$

				for (int i=0,j=hwids.length; i<j; i++) {
					if (hwids[i].equals(defHwid)) {
						hwids[i] = hwids[i]+IMakerWrapperPreferences.HWID_DEFAULT_MARKER;
					}
					hwidList += hwids[i];
					if (i < j-1) {
						hwidList += " "; //$NON-NLS-1$
					}
				}
				setting.setValue(hwidList);
			}
			config.addSetting(setting);
		}
	}

	private Configuration createConfiguration(String makeFile) {
		Configuration config = IQRFFactory.eINSTANCE.createConfiguration();
//		File file = new File(makeFile);
		config.setFilePath(makeFile);
//		config.setName(file.getName());
		return config;
	}

	public void cancel() {
//		if(process!=null&&builderRunning) {
//			try {
//				process.getInputStream().close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			process.destroy();
//			builderRunning=false;
//		}
	}

	public String getWorkdir(String makefile) throws IMakerCoreNotFoundException, IMakerCoreAlreadyRunningException, IMakerCoreExecutionException {
		String target = "help-variable-WORKDIR*-value";
		String retValue = null;
		verifyCompatibility();
		List<String> parameters = new ArrayList<String>();
		parameters.add("-f");
		parameters.add(makefile);
		parameters.add(target);
		
		List<String> ret = executeCommand(parameters,null);
		String output = ret.get(0);
		for (String str : ret) {
			if(str.startsWith("WORK")) {
				output = str;
				break;
			}
		}
		int start = output.indexOf('/');
		output = output.substring(start+1,output.length()-1);
		retValue = output;
		return retValue;
	}

//	@Override
	public String getBuildCommand(List<String> params) {
		StringBuffer sb = new StringBuffer();
		sb.append(tool.get(0) + " ");
		String[] parameters = null;
		String prms = params.toString();
		if(prms.length()>2) {
			prms = prms.substring(1, prms.length()-1);
			parameters=prms.split(Messages.getString("IMakerWrapper.5")); //$NON-NLS-1$
		}
		for(String str: parameters) {
			sb.append(str);
		}
		return sb.toString();
	}

	public List<String> getTool() {
		return this.tool;
	}

	public String getTargetSteps(String target, String makefile, IProgressMonitor monitor) throws IMakerCoreExecutionException {
		if (monitor != null) {
			monitor.beginTask(Messages.getString("IMakerWrapper.29"),10);
		}
		String ret = getTargetSteps2(target, makefile, monitor,false); 
		if(monitor!=null) {
			monitor.done();
		}
		return ret;
	}

	private String getTargetSteps2(String target, String makefile,
			IProgressMonitor monitor, boolean isRestart) throws IMakerCoreExecutionException {
		if(monitor!=null) {
			monitor.subTask(Messages.getString("IMakerWrapper.30")+ target);
		}
		Pattern pattern = Pattern.compile(".*\\s*=\\s*.(.*).");
		List<String> cmd = new ArrayList<String>();
		cmd.add(IMakerWrapperPreferences.ARG_FETCH_CONFIGURATION);
		cmd.add(makefile);
		cmd.add(target);
		cmd.add(IMakerWrapperPreferences.TARGET_STEPS);
		if(isRestart) {
			cmd.add(IMakerWrapperPreferences.TARGET_STEPS_RESTARTS);
		}
		StringBuilder sb = new StringBuilder();
		List<String> ret = executeCommand(cmd,null);
		for (String str : ret) {
			Matcher matcher = pattern.matcher(str);
			if(matcher.find()) {
				int end = matcher.end(1);
				int start = matcher.start(1);
				String retValue = str.substring(start,end);
				if(retValue.contains(" RESTART")) {
					String subRet = getTargetSteps2(target,makefile,monitor,true);
					if(subRet!=null) sb.append(subRet);
				} else {
					String[] parts = retValue.split(" ");
					
					for (int i = 0; i < parts.length; i++) {
						String p = parts[i];
						if(isTarget(p)) {
							String subRet = getTargetSteps2(p,makefile,monitor, false); 
							if(subRet!=null) sb.append(subRet);
						} else {
							sb.append(p);
							sb.append(" ");							
						}
					}
				}
				break;
			}
		}
		String str = sb.toString();
		if(monitor!=null) {
			monitor.worked(1);
		}
		if(str.equals("")) {
			return null;
		} else {
			return str;
		}
	}

	private boolean isTarget(String p) {
		if(p!=null) {
			String pl = p.toLowerCase();
			return pl.equals(p);
		}
		return false;
	}

	public boolean buildImage(File impFile, OutputStream out)
			throws IMakerCoreNotFoundException, IMakerCoreExecutionException,
			IMakerCoreAlreadyRunningException {
		if(impFile==null||!impFile.exists()) {
			throw new IMakerCoreExecutionException("Invalid imp file given!");
		}
		verifyCompatibility();
		
		params.clear();
		params.add("-f");
		params.add(getFilePath(impFile));
		builderRunning = true;
		List<String> ret = executeCommand(params,out);
		builderRunning = false;
		return getReturnStatus(ret);
	}

	private String getFilePath(File impFile) {
		String path = impFile.getAbsolutePath();
		if (path.contains(" ")) {
			return "\""+path+"\"";			
		} else {
			return path;
		}
	}

	public String getBuildCommand(File impFile) {
		StringBuffer sb = new StringBuffer();
		sb.append(tool.get(0) + " -f " + getFilePath(impFile));
		return sb.toString();
	}	
}
