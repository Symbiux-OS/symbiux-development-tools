/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.util.sourcecode.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Pattern;
import com.nokia.s60tools.util.cmdline.CmdLineExeption;
import com.nokia.s60tools.util.cmdline.UnsupportedOSException;
import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.internal.Messages;
import com.nokia.s60tools.util.resource.FileUtils;
import com.nokia.s60tools.util.sourcecode.CannotFoundFileException;
import com.nokia.s60tools.util.sourcecode.ISourceFinder;
import com.nokia.s60tools.util.sourcecode.ISourcesFinder;
import com.nokia.s60tools.util.sourcecode.SourceFileLocation;

/**
 * Class for founding source file location from .map files.
 */
public class MapSourceFinder implements ISourceFinder, ISourcesFinder {

	//
	// Private constants used in map file seeking and parsing
	//
	private static final String CLASS_SEPARATOR = "::";//$NON-NLS-1$
	private static final String SEMI_COLON = ";";//$NON-NLS-1$
	private static final String COMMENT_START = "/*";//$NON-NLS-1$
	private static final String COMMENT_END = "*/";//$NON-NLS-1$
	private static final String CODE_COMMENT_PREFIX = "//";//$NON-NLS-1$
	private static final String DLL_SUFFIX= ".dll";//$NON-NLS-1$
	private static final String BACKSLASH = "\\"; //$NON-NLS-1$
	private static final String S60_PATH_PREFIX = BACKSLASH + "s60" + BACKSLASH; //$NON-NLS-1$
	private static final String SRC_PATH_PREFIX = BACKSLASH + "src" + BACKSLASH; //$NON-NLS-1$
	private static final String S60_PATH_PREFIX_DOUBLE_SLASHS = BACKSLASH + BACKSLASH + "s60" + BACKSLASH + BACKSLASH; //$NON-NLS-1$
	private static final String SRC_PATH_PREFIX_DOUBLE_SLASHS = BACKSLASH + BACKSLASH + "src" + BACKSLASH + BACKSLASH; //$NON-NLS-1$
	private static final String C_SUFFIX = ".c"; //$NON-NLS-1$
	private static final String CPP_SUFFIX = ".cpp"; //$NON-NLS-1$
	private static final String METHOD_CLOSING_CHAR = ")"; //$NON-NLS-1$
	private static final String METHOD_OPENING_CHAR = "("; //$NON-NLS-1$
	private static final String METHOD_DEFINITION_CLOSING_CHAR = "}";
	private static final String MAP_FILE_SUFFIX = ".map"; //$NON-NLS-1$
	private static final String NULL_ADDRESS = "0x00000000"; //$NON-NLS-1$
	private static final String ABSOLUTE  = "ABSOLUTE"; //$NON-NLS-1$
	private static final String EPOC32_BUILD  = "EPOC32\\BUILD"; //$NON-NLS-1$
	private static final String EPOC32_BUILD_DOUBLE_SLASHES  = "EPOC32\\\\BUILD"; //$NON-NLS-1$
	private static final String EXPORT ="EXPORT_C"; //$NON-NLS-1$
	private static final String IMPORT = "IMPORT_C"; //$NON-NLS-1$

	
	/**
	 * Print utility used to report error, warnings, and info messages.
	 */
	private IConsolePrintUtility printUtility = null;

	/**
	 * Constructor.
	 * @param printUtility Print utility used to report error, warnings, and info messages.
	 */
	public MapSourceFinder(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.ISourceFinder#findSourceFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public SourceFileLocation findSourceFile(String ordinal, String methodName, String compoentName,
			String variant, String build, String epocRootPath) throws CannotFoundFileException {
		SourceFileLocation sourceLocation = null;
		try {
			String address = getAddress(ordinal, compoentName, variant, build,
					epocRootPath);
			String mapFilePath = getMapFilePath(compoentName, variant, build, epocRootPath);
			File mapFile = getMapFile(mapFilePath);

			String lineWhereAddressIs = getAddressLineFromFile(address, mapFile, methodName);

			if(methodName == null){
				methodName = getMethodNameFromMapFileLine(lineWhereAddressIs);				
			}
			
			String objectFileName = getObjectFileName(lineWhereAddressIs, address);
			sourceLocation = getSourceFileLocation(objectFileName, mapFile, methodName, epocRootPath, isDll(compoentName));			
			
			sourceLocation.setDllName(compoentName);
			sourceLocation.setMethodName(methodName);
			sourceLocation.setObjectName(objectFileName);
			sourceLocation.setOrdinal(ordinal);
			sourceLocation.setMethodAddress(address);
			
		} catch (Exception e) {			
			e.printStackTrace(); 
			// Mapping all possible exceptions into single exception type. 
			throw new CannotFoundFileException(e.getMessage(), e);
		}		
		return sourceLocation;
	}
	
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.ISourceFinder#findSourceFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Collection)
	 */
	public SourceFileLocation findSourceFile(String ordinal, String methodName, String componentName, String epocRootPath, Collection<String> pathsToSeekMapFile) throws CannotFoundFileException {
		SourceFileLocation sourceLocation = null;
		try {
	
			String mapFileName = componentName + MAP_FILE_SUFFIX;
			
			File mapFile = getMapFile(pathsToSeekMapFile, componentName, mapFileName);
			
			if(mapFile == null){
				String msg = "'" + mapFileName  //$NON-NLS-1$
					+"' " //$NON-NLS-1$
					+Messages.getString("MapSourceFinder.MapFileNotFoundForMethod_ErrMsg_Part1")   //$NON-NLS-1$
					+methodName 
					+"' "  //$NON-NLS-1$
					+Messages.getString("MapSourceFinder.MapFileNotFoundForMethod_ErrMsg_Part2");  //$NON-NLS-1$
				String consoleMsg = msg + " "  //$NON-NLS-1$
					+Messages.getString("MapSourceFinder.MapFileNotFoundForMethod_ErrMsg_Part3");  //$NON-NLS-1$

				String pathSeparator= ", "; //$NON-NLS-1$
				for (Iterator<String> iterator = pathsToSeekMapFile.iterator(); iterator
						.hasNext();) {
					String path = (String) iterator.next();
					consoleMsg += path + pathSeparator;
				}
				consoleMsg = consoleMsg.substring(0, consoleMsg.length() - pathSeparator.length());
				printUtility.println(consoleMsg, IConsolePrintUtility.MSG_ERROR);
				throw new CannotFoundFileException(msg);
			}
			File parentDir = mapFile.getParentFile();
			
			String componentPath = parentDir.getAbsolutePath() + File.separatorChar + componentName;
			
			String address = getAddress(ordinal, componentPath, epocRootPath);
			String lineWhereAddressIs = getAddressLineFromFile(address, mapFile, methodName);

			if(methodName == null){
				methodName = getMethodNameFromMapFileLine(lineWhereAddressIs);				
			}
			
			String objectFileName = getObjectFileName(lineWhereAddressIs, address);
			sourceLocation = getSourceFileLocation(objectFileName, mapFile, methodName, epocRootPath, isDll(componentName));			
			
			sourceLocation.setDllName(componentName);
			sourceLocation.setMethodName(methodName);
			sourceLocation.setObjectName(objectFileName);
			sourceLocation.setOrdinal(ordinal);
			sourceLocation.setMethodAddress(address);
			
		} catch (Exception e) {			
			e.printStackTrace(); 
			// Mapping all possible exceptions into single exception type. 
			throw new CannotFoundFileException(e.getMessage(), e);
		}		
		return sourceLocation;
	}
	
	/**
	 * Check if component is DLL or not.
	 * @param componentName Component name.
	 * @return <code>true</code> if component is DLL, otherwise <code>false</code>.
	 */
	private boolean isDll(String componentName) {		
		return componentName.trim().endsWith(DLL_SUFFIX) ? true : false;
	}
	/**
	 * Gets map file object based on path name if file exists.
	 * @param mapFilePath Path to map file.
	 * @return File object for map file, if file exists, otherwise throwing exception.
	 * @throws FileNotFoundException
	 */
	private File getMapFile(String mapFilePath) throws CannotFoundFileException {
		File mapFile = new File(mapFilePath);
		if(!mapFile.exists()){
			throw new CannotFoundFileException(
					Messages.getString("MapSourceFinder.MapDoesntExist_ErrMsg_Part1") //$NON-NLS-1$
					+mapFilePath
					+Messages.getString("MapSourceFinder.MapDoesntExist_ErrMsg_Part2")); //$NON-NLS-1$
		}
		return mapFile;
	}


	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.ISourceFinder#findSourceFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public SourceFileLocation findSourceFile(String ordinal, String componentName,
			String variant, String build, String epocRootPath)  throws CannotFoundFileException{

			return findSourceFile(ordinal, null, componentName, variant, build, epocRootPath);
			
	}
	
	/**
	 * Tries to find a variant map file for given component. E.g. if component is called MyComponent.exe,
	 * it might not have a map file called MyComponent.exe.map. It might contain a variant map file e.g.
	 * MyComponent.11001011.exe.map. This method tries to find this variant map file MyComponent.11001011.exe.map.
	 * @param epocRootPath EPOCROOT path
	 * @param variant Build variant
	 * @param build Build type (udeb/urel)
	 * @param component Component name.
	 * @return variant map file, or <code>null</code> if not found.
	 */
	private File getVariantMapFile(String epocRootPath, String variant, String build, String component) {
		File variantMapFile = null;
		
		String mapPath = 
			epocRootPath + File.separatorChar 
			+SourceFileLocation.EPOC32_FOLDER_NAME + File.separatorChar
			+SourceFileLocation.RELEASE_FOLDER_NAME + File.separatorChar
			+variant + File.separatorChar
			+build + File.separatorChar;
		mapPath = makeWindowsPath(mapPath);
		
		File path = new File(mapPath);
		if (path.isDirectory() && path.exists()) {
			
			final class VariantFilter implements FilenameFilter {
				String component;
				public VariantFilter(String componentName) {
					component = componentName;
				}
		        public boolean accept(File dir, String name) {
		            if (name.toLowerCase().endsWith(".map")) {
		            	int index = component.lastIndexOf(".");
		            	if (name.toLowerCase().startsWith(component.substring(0, index+1).toLowerCase()))
		            		return true;
		            }
	            	return false;
		        }
			}
		    String[] files = path.list(new VariantFilter(component));
		    if (files != null && files.length == 1)
		    	variantMapFile = new File(mapPath + files[0]);
		}
		
		return variantMapFile;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.ISourceFinder#findSourceFileByMethodName(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public SourceFileLocation findSourceFileByMethodName(String methodName,
			String componentName, String variant, String build, String epocRootPath) throws CannotFoundFileException{
		
		String mapFilePath = getMapFilePath(componentName, variant, build, epocRootPath);
		
		File mapFile = null;
		try {
			mapFile = getMapFile(mapFilePath);

		// we could not find map file, try to find variant map file
		} catch (CannotFoundFileException e) {
			mapFile = getVariantMapFile(epocRootPath, variant, build, componentName);
			if (mapFile == null)
				throw e;
		}
		
		return findSourceFile(methodName, componentName, epocRootPath, mapFile);		
	
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.ISourceFinder#findSourceFileByMethodName(java.lang.String, java.lang.String, java.lang.String, java.util.Collection)
	 */
	public SourceFileLocation findSourceFileByMethodName(String methodName,
			String componentName, String epocRootPath, Collection<String> pathsToSeekMapFile) throws CannotFoundFileException{

		String mapFileName = componentName + MAP_FILE_SUFFIX;
		File mapFile = getMapFile(pathsToSeekMapFile, componentName, mapFileName);
		
		//If we did not found map file, we cannot proceed to src file seek
		if(mapFile == null){
			String msg = "'" + mapFileName  //$NON-NLS-1$
				+"' " //$NON-NLS-1$
				+Messages.getString("MapSourceFinder.MapFileNotFoundForMethod_ErrMsg_Part1")   //$NON-NLS-1$
				+methodName 
				+"' "  //$NON-NLS-1$
				+Messages.getString("MapSourceFinder.MapFileNotFoundForMethod_ErrMsg_Part2");  //$NON-NLS-1$
			String consoleMsg = msg + " "  //$NON-NLS-1$
				+Messages.getString("MapSourceFinder.MapFileNotFoundForMethod_ErrMsg_Part3");  //$NON-NLS-1$

			String pathSeparator= ", "; //$NON-NLS-1$
			for (Iterator<String> iterator = pathsToSeekMapFile.iterator(); iterator
					.hasNext();) {
				String path = (String) iterator.next();
				consoleMsg += path + pathSeparator;
			}
			consoleMsg = consoleMsg.substring(0, consoleMsg.length() - pathSeparator.length());
			printUtility.println(consoleMsg, IConsolePrintUtility.MSG_ERROR);
			throw new CannotFoundFileException(msg);
		}
		
		return findSourceFile(methodName, componentName, epocRootPath, mapFile);		
		
	}
	
	
	/**
	 * Gets a map file by component name and list of folder where to seek it
	 * @param pathsToSeekMapFile Path list to seek map files from.
	 * @param componentName Component name.
	 * @return file File object pointing to map file and <code>null</code> if not found.
	 */
	private File getMapFile(Collection<String> pathsToSeekMapFile,
			String componentName, String mapFileName) throws CannotFoundFileException {
		
		MapFilenameFilter mapFilenameFilter = new MapFilenameFilter(mapFileName);
		DirFileFilter dirFilter = new DirFileFilter();
		
		for (Iterator<String> iterator = pathsToSeekMapFile.iterator(); iterator
				.hasNext();) {
			String path = (String) iterator.next();
			File dir = new File(path);
			if(!dir.exists() || !dir.isDirectory()){
				continue;
			}
			File mapFile = getFileFromDir(dir, mapFileName, mapFilenameFilter, dirFilter);
			if(mapFile != null){
				return mapFile;
			}
			
		}
		
		return null;
	}
	
	/**
	 * Get map file from dir and dirs under given dir. Calls recursively it self.
	 * @param dir Directory to search for.
	 * @param mapFileName Map file name to search for.
	 * @param mapFilenameFilter Map file name filter.
	 * @param dirFilter Directory filter.
	 * @return mapFile with given name, or null if cannot be found
	 */
	private File getFileFromDir(File dir, String mapFileName, MapFilenameFilter mapFilenameFilter, DirFileFilter dirFilter){

		File [] files = dir.listFiles(mapFilenameFilter);
		if(files != null && files.length > 0){
			return files[0];
		}

		File [] dirs = dir.listFiles(dirFilter);
		for (int i = 0; i < dirs.length; i++) {
			File mapFile = getFileFromDir(dirs[i], mapFileName, mapFilenameFilter, dirFilter);
			if(mapFile != null){
				return mapFile;
			}
		}
		return null;
		
	}
	
	/**
	 * Filter accepts only directories, not files
	 */
	private class DirFileFilter implements FileFilter {

		public boolean accept(File file) {
			return file.isDirectory();
		}		
	}
	
	/**
	 * Filter accepts only files named by given file (case in sensitive)
	 */
	private class MapFilenameFilter implements FilenameFilter{
		
		private String mapFileName = null;

		private MapFilenameFilter(){
		}
		public MapFilenameFilter(String mapFileName){
			this();
			this.mapFileName = mapFileName;			
		}
		
		public boolean accept(File dir, String name) {
			return name.equalsIgnoreCase(mapFileName) ? true : false;
		}	
	}
	
	/**
	 * Finds source file based on the method name.
	 * @param methodName Method name.
	 * @param componentName Component name.
	 * @param epocRootPath EPOCROOT path.
	 * @param mapFile File object pointing to MMP file.
	 * @return source file location, or <code>null</code> if not found.
	 * @throws CannotFoundFileException
	 */
	private SourceFileLocation findSourceFile(String methodName,
			String componentName, String epocRootPath, File mapFile)
			throws CannotFoundFileException {
		try {			
			// Example method name line
			//20	CRepository::Create(unsigned long, TDesC16 const&)

			String lineWhereAddressIs = getAddressLineFromFile(mapFile, methodName);
			String address = getAddresFromLine(lineWhereAddressIs, methodName);
			
			String objectFileName = getObjectFileName(lineWhereAddressIs, address);
			String shortMethodNameWithoutParams = getMethodNameWithoutParams(methodName);
			SourceFileLocation sourceLocation = getSourceFileLocation(objectFileName, mapFile, shortMethodNameWithoutParams, epocRootPath, isDll(componentName));			
			
			sourceLocation.setDllName(componentName);
			sourceLocation.setMethodAddress(address);
			sourceLocation.setMethodName(methodName);
			sourceLocation.setObjectName(objectFileName);
			sourceLocation.setMethodAddress(address);
			return sourceLocation;
			
		} catch (Exception e) {
			e.printStackTrace();
			// Mapping all possible exceptions into single exception type. 
			throw new CannotFoundFileException(e.getMessage() , e);
		}
	}

	/**
	 * Get address from map file line based on method name
	 * @param lineWhereAddressIs Line containing address.
	 * @param methodName Method name.
	 * @return Method address.
	 */
	private String getAddresFromLine(String lineWhereAddressIs,
			String methodName) {
		//e.g. : CRepository::Create(unsigned long, const TDesC16&) 0x000080b9   Thumb Code     8  centralrepository.in(.text)
		String addString = lineWhereAddressIs.substring(lineWhereAddressIs.indexOf(methodName) + methodName.length()).trim();
		//e.g. : '0x000080b9   Thumb Code     8  centralrepository.in(.text)'		
		addString = addString.substring(0, addString.indexOf(" ")).trim(); //$NON-NLS-1$
		return addString;
	}
	
	
	/**
	 * Get Address using petran command-line runner.
	 * @param ordinal Method ordinal.
	 * @param componentName Component name.
	 * @param variant Build variant.
	 * @param build Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @return Address for the given method ordinal.
	 * @throws UnsupportedOSException
	 * @throws InterruptedException
	 * @throws CmdLineExeption
	 */
	private String getAddress(String ordinal, String componentName, String variant,
			String build, String epocRootPath) throws UnsupportedOSException,
			InterruptedException, CmdLineExeption {
		
		PetranDumpCMDLineRunner runner = new PetranDumpCMDLineRunner(printUtility);		
		String address =  runner.getAddress(ordinal, componentName, variant, build, epocRootPath);
		return address;
	}
	
	/**
	 * Get Address using petran command-line runner.
	 * @param ordinal Method ordinal
	 * @param componentPath path of the component
	 * @param epocRootPath EPOCROOT path
	 * @return Address for the given method ordinal.
	 * @throws UnsupportedOSException
	 * @throws InterruptedException
	 * @throws CmdLineExeption
	 */
	private String getAddress(String ordinal, String componentPath, String epocRootPath) throws UnsupportedOSException,
			InterruptedException, CmdLineExeption {
		
		PetranDumpCMDLineRunner runner = new PetranDumpCMDLineRunner(printUtility);		
		String address =  runner.getAddress(ordinal, componentPath, epocRootPath);
		return address;
	}
	
	/**
	 * Get sourceFile location
	 * @param objectFileName Object file name.
	 * @param mapFile File object pointing to map file.
	 * @param methodName Method name.
	 * @param epocRootPath EPOCROOT path.
	 * @param isDll <code>true</code> if component is DLL, otherwise <code>false</code>.
	 * @return path in Windows format, separators will be "\":s even when they were "/":s originally.
	 * @throws IOException
	 * @throws CannotFoundFileException 
	 */
	private SourceFileLocation getSourceFileLocation(String objectFileName, File mapFile, String methodName, String epocRootPath, boolean isDll) throws IOException, CannotFoundFileException {
		FileInputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		ArrayList<String> lines = new ArrayList<String>();
		SourceFileLocation sourceLocation = null;

		try {
			in = new FileInputStream(mapFile);
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);

			String line = null;
			//seek "0x00000000" and "ABSOLUTE" and object file name from file line by line
			while ((line = br.readLine()) != null) {
				if(line.contains(objectFileName) && 
						line.contains(NULL_ADDRESS) &&
						line.contains(ABSOLUTE) ){

					//if we found wanted text, checking that it does not contain 
					//\EPOC32\BUILD or \\EPOC32\\BUILD, because then its not a source, but build file
					//Returning path in windows format
					String epocBuildLine = makeWindowsPath(line);
					//Making sure that souce location does not point to BUILD folder
					if(epocBuildLine.indexOf(EPOC32_BUILD) == SourceFileLocation.OFFSET_NOT_FOUND){						
						epocBuildLine = epocBuildLine.substring(0, epocBuildLine.indexOf(NULL_ADDRESS)).trim();
						lines.add(epocBuildLine);			
					}
					
				}
			}
			
			//If there was only one line
			if(lines.size() == 1){
				String filePath = buildAbsoluteFilePath(lines.get(0), epocRootPath);
				if(sourceFileExist(filePath)){
					sourceLocation = new SourceFileLocation();
					sourceLocation.setSourceFileLocation(filePath);
					String methodNameWithoutParams = getMethodNameWithoutParams(methodName);
					int offset = getMethodNameLineOfsetFromSourceFile(methodNameWithoutParams, new File(filePath), isDll);
					if(offset != SourceFileLocation.OFFSET_NOT_FOUND){
						sourceLocation.setMethodOffset(offset);						
					}
				}else{
					String msg = Messages.getString("MapSourceFinder.ObjectFileDoesntExist_ErrMsg_Part1") +objectFileName //$NON-NLS-1$ //$NON-NLS-2$ 
							+Messages.getString("MapSourceFinder.ObjectFileDoesntExist_ErrMsg_Part2") + mapFile.getAbsolutePath() //$NON-NLS-1$ //$NON-NLS-2$ 
							+Messages.getString("MapSourceFinder.ObjectFileDoesntExist_ErrMsg_Part3") + filePath//$NON-NLS-1$ //$NON-NLS-2$ 					
							+Messages.getString("MapSourceFinder.ObjectFileDoesntExist_ErrMsg_Part4");//$NON-NLS-1$ 
					printUtility.println(msg); 
					throwCannotFoundFileException(mapFile, methodName, msg);
				}
			}
			//If there was many occurrences, opening files and seek method name, to get only one result
			else if(lines.size() > 0){
				sourceLocation = foundSourceFileWhereMethodIsImplemented(lines, methodName, epocRootPath, isDll);
				
				if(sourceLocation == null){
					String msg = Messages.getString("MapSourceFinder.SourceFileNotFoundForMethod_ErrMsg_Part1") //$NON-NLS-1$
							+methodName  + Messages.getString("MapSourceFinder.SourceFileNotFoundForMethod_ErrMsg_Part2") +mapFile.getAbsolutePath() +"'."; //$NON-NLS-1$ //$NON-NLS-2$
					printUtility.println(msg);

					throwCannotFoundFileException(mapFile, methodName, msg);
				}

			}//No results found
			else{
				String msg = Messages.getString("MapSourceFinder.ObjectFileNotFound_ErrMsg_Part1") +objectFileName +Messages.getString("MapSourceFinder.ObjectFileNotFound_ErrMsg_Part2") + mapFile.getAbsolutePath() +"'."; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				printUtility.println(msg);
				throwCannotFoundFileException(mapFile, methodName, msg);
				
			}
						
		} finally{
			if(in != null){
				in.close();
			}
			if(isr != null){
				isr.close();
			}
			if(br != null){
				br.close();
			}
		}

		return sourceLocation;		
	}

	/**
	 * Throw an exception with message about map file and method name, that a source file cannot be found from those details
	 * @param mapFile File object pointing to map file.
	 * @param methodName Method name.
	 * @throws CannotFoundFileException
	 */
	private void throwCannotFoundFileException(File mapFile, String methodName, String msg) throws CannotFoundFileException{
		throw new CannotFoundFileException(msg);
	}

	
	/**
	 * Converts path into Windows format by 
	 * replacing extra "\\":s and replace "/" with "\".
	 * @param path Path name string to convert.
	 * @return path name string in windows format
	 */
	private String makeWindowsPath(String path) {
		//fixing slashes so we can find epoc32 build folder in any cases.
		//First replacing "\"'s with "/"'s so regular expression will work
		String windowsPath = path.replace(BACKSLASH, "/" ); //$NON-NLS-1$ //$NON-NLS-2$
		//removing multiple backslashes: " +" means more than one white spaces, e.g. "a    b c" -> "a b c"
		windowsPath = windowsPath.replaceAll("/+", "/"); //$NON-NLS-1$ //$NON-NLS-2$
		windowsPath = windowsPath.replace("/", BACKSLASH); //$NON-NLS-1$ //$NON-NLS-2$
		return windowsPath;
	}		
	
	/**
	 * Checks if file exists.
	 * @param filePath File path name.
	 * @return <code>true</code> if exist, otherwise <code>false</code>.
	 */
	private boolean sourceFileExist(String filePath) {		
		File file = new File(filePath);		
		return file.exists();
	}
	/**
	 * Builds absolute file path name from source file path and EPOCROOT path
	 * @param srcPath File source path
	 * @param epocRootPath EPOCROOT path
	 * @return Absolute file path name.
	 */
	private String buildAbsoluteFilePath(String srcPath, String epocRootPath) {
		String filePath;
		
		srcPath = FileUtils.removeDriveLetterPortionFromPathIfExists(srcPath);
		
		if(epocRootPath.endsWith(File.separator) && srcPath.startsWith(File.separator)){
			filePath = epocRootPath + srcPath;
		}
		else if(epocRootPath.endsWith(File.separator) || srcPath.startsWith(File.separator) ){
			filePath = epocRootPath + srcPath;
		}		
		else{
			filePath = epocRootPath + File.separatorChar + srcPath;
		}
		
		//Removing possible double slashes
		filePath = makeWindowsPath(filePath);
		
		return filePath;
	}
	
	/**
	 * Check if source files contains method name, if contains, returning that file location. 
	 * Skips parameters, only seeking method name or ClassName::MethodName if given.
	 * @param lines containing paths to source files found in .map file
	 * @param methodName Method name.
	 * @param epocRootPath EPOCROOT path.
	 * @param isDll <code>true</code> if component is DLL, otherwise <code>false</code>.
	 * @return Source file path if method name without parameters is found from that file, or <code>null</code> if not found
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws CannotFoundFileException 
	 */
	private SourceFileLocation foundSourceFileWhereMethodIsImplemented(ArrayList<String> lines,
			String methodName, String epocRootPath, boolean isDll) throws FileNotFoundException, IOException, CannotFoundFileException {			

		File srcFile;
		//Seeking lines where is source file paths one by one
		for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
			String path = (String) iterator.next();
			srcFile = buildSourceFileObject(path, epocRootPath, methodName);
			
			if(srcFile.exists()){
				String methodNameWithOutParams = getMethodNameWithoutParams(methodName);
				
				int methodLineOfset = getMethodNameLineOfsetFromSourceFile(methodNameWithOutParams, srcFile, isDll);
				//If we found a file where is classname::methodname, stop searching and return that one.
				if(methodLineOfset != SourceFileLocation.OFFSET_NOT_FOUND){
					SourceFileLocation sourceLocation = new SourceFileLocation();
					sourceLocation.setSourceFileLocation(srcFile.getAbsolutePath());
					sourceLocation.setMethodOffset(methodLineOfset);
					return sourceLocation;
				}
			}else{
				//Caller will throw an exception if null returned
				printUtility.println(Messages.getString("MapSourceFinder.FileDoesntExist_ErrMsg_Part1") +srcFile.getAbsolutePath()  //$NON-NLS-1$
						+ Messages.getString("MapSourceFinder.FileDoesntExist_ErrMsg_Part2") +methodName +Messages.getString("MapSourceFinder.FileDoesntExist_ErrMsg_Part3")); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return null;
	}
	
	/**
	 * Builds alternate source path from the parameters in case the default behavior has failed.
	 * @param path Source file path found from *.map-file.
	 * @param epocRootPath EPOCROOT defined in SDK properties.
	 * @param methodName Name of the method we were about to search.
	 * @return Alternate file path, if found.
	 */
	private File buildSourceFileObject(String path, String epocRootPath, String methodName) {
		
		// Default behavior is to add 'source path' to EPOCROOT. (format used in platform built map-files)	
		String srcFileUri = buildAbsoluteFilePath(path, epocRootPath);
		File srcFile = new File(srcFileUri);
		
		// Checking if the default behavior resulted correct file name
		if(!srcFile.exists()){
			// Printing info to console about the operation
			printUtility.println(Messages.getString("MapSourceFinder.FileDoesntExist_ErrMsg_Part1") +srcFile.getAbsolutePath()  //$NON-NLS-1$
					+ Messages.getString("MapSourceFinder.FileDoesntExist_ErrMsg_Part2") +methodName +Messages.getString("MapSourceFinder.FileDoesntExist_ErrMsg_Part3")); //$NON-NLS-1$ //$NON-NLS-2$
			// Checking if the source path already contained EPOCROOT, and stripping it.
			srcFile = stripEpocRootFromPath(path, epocRootPath, srcFile);
		}
		
		return srcFile;
	}
	/**
	 * Checks if given path already contains EPOCROOT without logical drive letter.
	 * If contains, then stripping it away and returning file object containing correct path.
	 * @param path Original source path in map-file
	 * @param epocRootPath Currently used EPOCROOT
	 * @param srcFile Currently used source file absolute path (possibly containing already EPOCROOT).
	 * @return File object containing correct path without duplicate EPOCROOT, or current file object
	 *         as result if there was no overlapping.
	 */
	private File stripEpocRootFromPath(String path, String epocRootPath, File srcFile) {
		// For components that user has build in Carbide has different kind of source path used.
		// The source path in map-file may already include EPOCROOT e.g. in the following example:
		//
		// EPOCROOT=C:\foo\bar\MySDK\
		// Source file path found from *.map-file=\foo\bar\MySDK\MyExamples\MyExample\ENGINE\src\enginedll.cpp
		//
		String epocRootWithoutDrive = epocRootPath.substring(2); // Skipping drive letter and colon: "C:" 

		if(path.startsWith(epocRootWithoutDrive)){
			// Stripping EPOCROOT
			String altSrcFileUri = path.substring(epocRootWithoutDrive.length());
			srcFile = new File(epocRootPath + altSrcFileUri);
			// Printing info to console about the operation
			printUtility.println(Messages.getString("MapSourceFinder.StrippingEpocrootFromPath_InfoMsg")); //$NON-NLS-1$
		}
		return srcFile;
	}
	
	/**
	 * Parses out params from method name
	 * @param methodName Name of the method to parse.
	 * @return Method name without parameters.
	 */
	private String getMethodNameWithoutParams(String methodName) {
		
		//ContentAccess::CData::NewL(RFile&, TDesC16 const&, ContentAccess::TIntent)
		String methodNameWithClassButWithOutParams = methodName.split(Pattern.quote(METHOD_OPENING_CHAR))[0];
		String methodNameWithOutParams;
		
		String classSeparator = CLASS_SEPARATOR;
		if(methodNameWithClassButWithOutParams.indexOf(classSeparator) != -1){
			String [] parts = methodNameWithClassButWithOutParams.split(Pattern.quote(classSeparator));
			methodNameWithOutParams = parts[parts.length - 2] +classSeparator + parts[parts.length -1];
		}else{
			methodNameWithOutParams = methodNameWithClassButWithOutParams;
		}
		
		//If that file contains line, where is methodname, e.g. MyClass:MyMethod then that file is what we want
		//Only Class name and method name is searched, skipping parameters because of possible new lines and so on.
		return methodNameWithOutParams;
	}
	
	/**
	 * Get object filename from line where method address was found
	 * @param lineWhereAddressIs Line containing the address and object file name.
	 * @param address Method address
	 * @return object file name
	 */
	private String getObjectFileName(String lineWhereAddressIs, String address) {

		//First line should be e.g.:
		//CRepository::Create(unsigned long, const TDesC16&) 0x000080b9   Thumb Code     8  centralrepository.in(.text)
		//or/
		//User::LeaveIfError(int)                  0x00009078   ARM Code       0  euser{000a0000}-593.o(StubCode)
		
		//Cut string after address
		String objFileName = lineWhereAddressIs.substring(lineWhereAddressIs.indexOf(address) +address.length()).trim();
		//No there should be: "Thumb Code     8  centralrepository.in(.text)"
		//or: "ARM Code       0  euser{000a0000}-593.o(StubCode)"
		
		//Cut string before "("
		objFileName = objFileName.substring(0, objFileName.lastIndexOf(METHOD_OPENING_CHAR)).trim();
		//No there should be: "Thumb Code     8  centralrepository.in"
		//or: "ARM Code       0  euser{000a0000}-593.o"
		
		//Cut string from last " "
		objFileName = objFileName.substring(objFileName.lastIndexOf(" ")).trim(); //$NON-NLS-1$

		//No there should be: "centralrepository.in"
		//or: "euser{000a0000}-593.o"

		return objFileName;
	}

	/**
	 * Get method name from line where method address was found.
	 * @param lineWhereAddressIs Line containing the address and object file name.
	 * @return method name
	 */
	private String getMethodNameFromMapFileLine(String lineWhereAddressIs) {
		//CRepository::Create(unsigned long, const TDesC16&) 0x000080b9   Thumb Code     8  centralrepository.in(.text)
		String method = lineWhereAddressIs.substring(0, lineWhereAddressIs.indexOf(METHOD_CLOSING_CHAR));
		return method != null ? method.trim() : null;
	}		
	
	/**
	 * Get those lines from file, where is the wanted string.
	 * @param wantedString wanted string, or <code>null</code> if all lines is wanted
	 * @param file File object to search for the string.
	 * @param addAllLinesIfWantedStringExist If set to <code>true</code> and wantedString 
	 *                                       occurs at least once, all lines from file will be returned, 
	 *                                       otherwise an empty list will be returned, 
	 *                                       If set to <code>false</code>, only those lines where 
	 *                                       wanted string exist will be returned. 
	 * @return Lines based wanted strings and boolean parameter setting.
	 * @throws IOException
	 */
	private ArrayList<String> getLinesFromFile(String wantedString, File file, boolean addAllLinesIfWantedStringExist) throws IOException{
		ArrayList<String> lines = new ArrayList<String>();
		FileInputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		boolean fileContainsWantedString = false;
				
		try {
			in = new FileInputStream(file);
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);

			String line = null;
			
			//seek wanted text from file line by line
			while ((line = br.readLine()) != null) {
				//if we found wanted text, stopping search
				if(addAllLinesIfWantedStringExist){
					lines.add(line);
					//Marking that we found a required line from that file when all lines is gathered
					if(line.contains(wantedString)){
						fileContainsWantedString = true;
					}
				}
				else if(!addAllLinesIfWantedStringExist && line.contains(wantedString)){
					lines.add(line);
				}//Else line is not wanted and will not be added to lines
			}		
		}finally{
				if(in != null){
					in.close();
				}
				if(isr != null){
					isr.close();
				}
				if(br != null){
					br.close();
				}
		}		
		
		//If we want all lines from file and that file does not contain wanted String
		//we collect already all lines but there was no wanted line, so returning new empty list
		if(addAllLinesIfWantedStringExist && !fileContainsWantedString){
			return new ArrayList<String>();
		}else{		
			return lines;
		}
	}
	
	
	/**
	 * Seek address line where is method address and braces ().
	 * @param address Method address.
	 * @param mapFile File object pointing to map file.
	 * @param methodName Method name.
	 * @return Address line.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CannotFoundFileException 
	 */
	private String getAddressLineFromFile(String address, File mapFile, String methodName)
			throws FileNotFoundException, IOException, CannotFoundFileException {
		
		ArrayList<String> lines = getLinesFromFile(address, mapFile, false);
		String wantedLine = null;
			
			//If there was only one occurrence
			if(lines.size() == 1){
				wantedLine = lines.get(0);
				
			}
			//If there was many occurences, selecting wanted one  
			else if(lines.size() > 1){
				
				//Seeking all foundings and select wanted line
				for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
					String aLine = (String) iterator.next();
					
					//If there is many lines, we want that one where method name is, 
					//and in method name lines, there will be braces "()" before address e.g. in XnResource.dll.map
					//StubCode                                 0x000084b8   Section        8  apmime{000a0000}-57.o(StubCode)
					//TDataType::InternalizeL(RReadStream&)    0x000084b8   ARM Code       0  apmime{000a0000}-57.o(StubCode)
					
					//cut string before address
					aLine = aLine.substring(aLine.indexOf(address));
					if(aLine.contains(METHOD_CLOSING_CHAR) && aLine.contains(METHOD_OPENING_CHAR)){
						
							wantedLine = aLine;
							break;							
					}
									
				}
				
			}//else wantedLine will be null and exception will thrown
			
		if(wantedLine == null){
			throw new CannotFoundFileException(
					Messages.getString("MapSourceFinder.CannotFoundAddress_ErrMsg_Part1") +methodName +Messages.getString("MapSourceFinder.CannotFoundAddress_ErrMsg_Part2") +mapFile.getName() +"'."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		
		return wantedLine;
	}
	
	
	/**
	 * Seek address line where is method name and braces ().
	 * @param mapFile File object pointing to map file.
	 * @param methodName Method name.
	 * @return Address line.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CannotFoundFileException 
	 */
	private String getAddressLineFromFile(File mapFile, String methodName)
			throws FileNotFoundException, IOException, CannotFoundFileException {

		//Line is e.g:
		//CTerminalControlSession::GetDeviceLockParameterL(TBuf8<(int)21>&, int) 0x000090e1   Thumb Code   188  TerminalControl.in(.text)
		//When method is: CTerminalControlSession::GetDeviceLockParameterL(TBuf8<(int)21>&, int)
		
		ArrayList<String> lines = getLinesFromFile(methodName, mapFile, false);
		String wantedLine = null;
			
			//If there was only one occurrence
			if(lines.size() == 1){
				wantedLine = lines.get(0);
				
			}
			//If there was many occurrences, selecting wanted one  
			else if(lines.size() > 1){
				
				//Seeking all findings and select wanted line
				for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
					String aLine = (String) iterator.next();
					//What to do if many lines found? Currently just printing a debug message. 
					//Really it should not be happening, but if does, logic must be improved if error situation occurs. 
					 printUtility.println(
							 Messages.getString("MapSourceFinder.ManyAddressLines_Msg_Part_1")  //$NON-NLS-1$
							 +methodName  
							 +Messages.getString("MapSourceFinder.ManyAddressLines_Msg_Part_2")	//$NON-NLS-1$							
							+mapFile.getName()
							+Messages.getString("MapSourceFinder.ManyAddressLines_Msg_Part_3")//$NON-NLS-1$
							+aLine
							+"'.");	//$NON-NLS-1$					
					
				}
				//Even if there is many lines, just selecting the first one
				wantedLine = lines.get(0);
				
			}//else wantedLine will be null and exception will thrown
			
		if(wantedLine == null){
			throw new CannotFoundFileException(
					Messages.getString("MapSourceFinder.CannotFoundAddress_ErrMsg_Part1") +methodName +Messages.getString("MapSourceFinder.CannotFoundAddress_ErrMsg_Part2") +mapFile.getName() +"'."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		
		return wantedLine;
	}	
	
	
	/**
	 * Seek offset from method implementation from source file.
	 * @param methodName Method name
	 * @param file File to gen method name line offset.
	 * @param isDll  When set to <code>true</code> tells that implementation must 
	 *               have prefix IMPORT_C or EXPORT_C and if set to <code>false</code> 
	 *               we suppose that it is exe file in question and those are not required.
	 * @return Offset or -1 if not found
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private int getMethodNameLineOfsetFromSourceFile(String methodName, File file, boolean isDll)
			throws FileNotFoundException, IOException {
		//getting all lines of that file if it contains methodName at least some where
		ArrayList<String> lines = getLinesFromFile(methodName, file, true);
		String wantedLine = null;
		int offset = SourceFileLocation.OFFSET_NOT_FOUND;//-1 will return if offset cannot be found from file
			// If there was at least one occurrence of methodName
			if (lines.size() > 0) {

				//For finding offset of method implementation, we need to collect file contents
				StringBuffer fileBuffer = new StringBuffer();
				StringBuffer tmpBuffer = null;
				boolean isInsideCommentSegment = false;	
				boolean searchForMethodDefinition = false;
				String methodStartingLine = null;
				
				// Seeking all findings and select wanted line
				for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
					String aLine = (String) iterator.next();//Not to be modified
					fileBuffer.append(aLine);//for founding out offset collecting file to buffer
					fileBuffer.append("\r\n"); //$NON-NLS-1$
					String lineWithoutComments = aLine;
									
					//If we are inside of comment segment, and this line does not contain comment segment closing
					//we can just skip the line because its a comment line. 
					if(isInsideCommentSegment && lineWithoutComments.indexOf(COMMENT_END) == SourceFileLocation.OFFSET_NOT_FOUND){
						continue;
					}
					//if we are inside of comment segment and this line contains comment closing segment
					//checking out if there is something after that comment segment line
					else if(isInsideCommentSegment && lineWithoutComments.indexOf(COMMENT_END) != SourceFileLocation.OFFSET_NOT_FOUND){
						
						lineWithoutComments = getLineWithOutComments(lineWithoutComments, isInsideCommentSegment);
						
						//mark that comment segment is closed if so
						isInsideCommentSegment = isLineClosingCommentSegment(lineWithoutComments);
					}										
					
					//if that line contains opening sequence to comment segment
					else if(lineWithoutComments.indexOf(COMMENT_START) != SourceFileLocation.OFFSET_NOT_FOUND ){

						//case 1 / ** <comments>
						//case 2 ..Text / ** <comments> * /  Text..
						//case 3 / ** <comments> * / Text..
						
						isInsideCommentSegment = isLineOpeningCommentSegment(lineWithoutComments);
						lineWithoutComments = getLineWithOutComments(lineWithoutComments, isInsideCommentSegment);
					}
					//Else in this line there is no comment segment, and status nor line needs no modifications

					//if this line does not contain methodName, just continue
					if(!searchForMethodDefinition && (isInsideCommentSegment || !lineWithoutComments.contains(methodName)) /*|| isCommentLine*/){
						continue;
					}
					boolean isCommentLine = isCommentLine(lineWithoutComments, methodName);
					//if this line does not contain methodName, just continue
					if(!searchForMethodDefinition && (isInsideCommentSegment || !lineWithoutComments.contains(methodName) || isCommentLine)){
						continue;
					}
					
					//Cut the line, because there can be e.g. MyClass::MyMethod() //;;; When ";" would cause line to drop out
					if(lineWithoutComments.indexOf(CODE_COMMENT_PREFIX) != SourceFileLocation.OFFSET_NOT_FOUND){
						lineWithoutComments = lineWithoutComments.split(Pattern.quote(CODE_COMMENT_PREFIX))[0];
					}
					
					/**
					 * If this (searchForMethodDefinition) is true, the method assumes that
					 * it has encountered a line with the method name but not sure whether
					 * its a definition point or invocation point. So, it scans for the text
					 * after the method name until it encounters certain delimiters, to conclude
					 * whether its point of definition or point of invocation.
					 */
					if(searchForMethodDefinition)
					{
						String neededText = fetchContentBeforeDefinition(lineWithoutComments);
						tmpBuffer.append(neededText);
						
						if(hasDelimiter(tmpBuffer.toString()))
						{
							tmpBuffer = tmpBuffer.deleteCharAt(tmpBuffer.length()-1);
							String contentToBeParsed = tmpBuffer.toString();
							boolean isMethodDefined = parseForMethodDefinition(contentToBeParsed);
						
							if(isMethodDefined)
							{
								searchForMethodDefinition = false;
								wantedLine = methodStartingLine;
								tmpBuffer = null;
								break;
							}
							else{
								searchForMethodDefinition = false;
								tmpBuffer = null;
								continue;
							}
						}
					}
					//
					//Now we know that this line where we are, is not inside of "/**/" -comments,
					//This line is not a comment line with "//":s
					//And this line is containing method name
					//					

					//If component is DLL it exports functions, and EXPORT_C (or IMPORT_C just in case) must occur in line
					//This is not a perfect way to do it, but it works, because EXPORT_C definition is
					//most likely written to same line as method definition.
					if (isDll && 
							( lineWithoutComments.toUpperCase().indexOf(EXPORT) != SourceFileLocation.OFFSET_NOT_FOUND
							|| lineWithoutComments.toUpperCase().indexOf(IMPORT) != SourceFileLocation.OFFSET_NOT_FOUND)
							&& lineWithoutComments.indexOf(SEMI_COLON) == SourceFileLocation.OFFSET_NOT_FOUND) {
						wantedLine = aLine;						
						break;
					}
					//If the line contains function name but does not contain EXPORT_C or IMPORT_C, we will 
					//scan the text after method name to check if method definition has started.
					else if(isDll && lineWithoutComments.contains(methodName)) {
						tmpBuffer = new StringBuffer();
						tmpBuffer.append(lineWithoutComments.substring(lineWithoutComments.indexOf(methodName) + methodName.length()));
					
						methodStartingLine = aLine;
						searchForMethodDefinition = true;
					}
					//If method name is not [ClassName]::[MethodName] but e.g. __my_macro, excluding EXPORT_C check
					//But then there must be "("
					else if(methodName.indexOf(CLASS_SEPARATOR) == SourceFileLocation.OFFSET_NOT_FOUND
							&& lineWithoutComments.indexOf(SEMI_COLON) == SourceFileLocation.OFFSET_NOT_FOUND
							&& lineWithoutComments.indexOf(METHOD_OPENING_CHAR) != SourceFileLocation.OFFSET_NOT_FOUND){ 
						wantedLine = aLine;
						break;
					}
					
					//If its not dll but exe, it does not export functions, then it might be implementation 
					else if(!isDll && lineWithoutComments.indexOf(SEMI_COLON) == SourceFileLocation.OFFSET_NOT_FOUND){
						wantedLine = aLine;
						break;
					}
					//Else we are not interested on this line and keep seeking					
					else{
						 printUtility.println(
								 Messages.getString("MapSourceFinder.OffsetSeek_Msg_Part_1")  //$NON-NLS-1$
								 +methodName  
								 +Messages.getString("MapSourceFinder.OffsetSeek_Msg_Part_2")	//$NON-NLS-1$							
								+file.getAbsolutePath()
								+Messages.getString("MapSourceFinder.OffsetSeek_Msg_Part_3")//$NON-NLS-1$
								+lineWithoutComments
								+Messages.getString("MapSourceFinder.OffsetSeek_Msg_Part_4"));	//$NON-NLS-1$					
					}

				}//for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
				if(wantedLine != null){
					offset = fileBuffer.lastIndexOf(wantedLine); //getOfsetForLine(lines, wantedLine);
				}
				
			}//if (lines.size() > 0) {

			//Now wantedLine should be line we want, we can found out offset for that line			

		return offset;
	}		

	/**
	 * Get content of this line with out comments.
	 *  e.g. 'MyClass::Foo(TInt bar /*My comments * /)'
	 *  returns 'MyClass::Foo(TInt bar )'
	 *  NOTE: does not handle '//' comments at all
	 * @param line Line to remove comments from
	 * @param isInsideCommentSegment  set to <code>true</code>, if we are already are inside of comment segment 
	 * @return a line without comments
	 */
	private String getLineWithOutComments(String line, boolean isInsideCommentSegment) {
		
		// /** aa */ BB /** cc */ DD  --> Should return " BB  DD"
		// aa */ BB /** cc */ DD  --> Should return " BB  DD" if isInsideComment and "aa  BB  DD" if no
		// AA */ should return "" if inside comments, and "AA */" if not
		
		//if we are inside comments, and comment segment is not closing, 
		//there is no other than comments, so return empty string
		if(isInsideCommentSegment && line.indexOf(COMMENT_END) == SourceFileLocation.OFFSET_NOT_FOUND){
			return ""; //$NON-NLS-1$
		}
		//if we are not inside comments and not even start a comment just return the line
		else if(!isInsideCommentSegment && line.indexOf(COMMENT_START) == SourceFileLocation.OFFSET_NOT_FOUND){
			return line;
		}		
		//else we parse out everything else but comments

		StringBuffer b = new StringBuffer();		
		String [] parts = line.split(Pattern.quote(COMMENT_START));
				
		//Splitted string goes like this:
		// "/** aa */ BB /** cc */ DD" -->
		// " aa */ BB "
		// " cc */ DD"
		for (int i = 0; i < parts.length; i++) {
			//Skip empty parts
			if(parts[i].equals("")){ //$NON-NLS-1$
				continue;
			}
			//If we are not inside comments, and line was not started with comments, the first part belongs to text
			else if(i==0 && !isInsideCommentSegment && parts[i].indexOf(COMMENT_END) == SourceFileLocation.OFFSET_NOT_FOUND){
				b.append(parts[i]);
			}
			//if comment is opened, but not closed, this part belongs to comment and will be skipped			
			else if(parts[i].indexOf(COMMENT_END) == SourceFileLocation.OFFSET_NOT_FOUND){
				continue;
			}else{
				b.append(parts[i].substring( parts[i].indexOf(COMMENT_END)+COMMENT_END.length() ) );
			}
		}			
	
		return b.toString();
	}
	/**
	 * Check if this line opens a comment segment, and is not closing it
	 * @param line Line to be checked.
	 * @return <code>true</code> if start a comment block without closing it, otherwise <code>false</code>.
	 */
	private boolean isLineOpeningCommentSegment(String line) {
		
		int commentStartIndex = line.lastIndexOf(COMMENT_START);
		int commentEndIndex = line.lastIndexOf(COMMENT_END);			
		
		if(commentStartIndex > commentEndIndex){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if this line closes a comment segment
	 * @param line Line to be checked.
	 * @return <code>true</code> if line closes comment segment, otherwise <code>false</code>.
	 */
	private boolean isLineClosingCommentSegment(String line) {
		
		int commentStartIndex = line.lastIndexOf(COMMENT_START);
		int commentEndIndex = line.lastIndexOf(COMMENT_END);			
		
		if(commentEndIndex > commentStartIndex){
			return true;
		}
		
		return false;
	}	
	
	
	/**
	 * Check if this line is comment line starting with //
	 * @param line Line to be checked.
	 * @return <code>true</code> if line starts with //, otherwise <code>false</code>.
	 */
	private boolean isCommentLine(String line, String methodName) {
		if(line.trim().startsWith(CODE_COMMENT_PREFIX)){
			return true;
		}
		else if(line.indexOf(CODE_COMMENT_PREFIX) != SourceFileLocation.OFFSET_NOT_FOUND){
			
			int commentIndex = line.indexOf(CODE_COMMENT_PREFIX);
			int methodNameIndex = line.indexOf(methodName);
			if(commentIndex > methodNameIndex){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * Gets *.map file path.
	 * @param componentName Component name.
	 * @param variant Build variant.
	 * @param build Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @return path to .dll file +MAP_FILE_SUFFIX
	 */
	private String getMapFilePath(String componentName, String variant, String build,
			String epocRootPath) {
		String mapPath = 
			epocRootPath + File.separatorChar 
			+SourceFileLocation.EPOC32_FOLDER_NAME + File.separatorChar
			+SourceFileLocation.RELEASE_FOLDER_NAME + File.separatorChar
			+variant + File.separatorChar
			+build + File.separatorChar
			+componentName
			+MAP_FILE_SUFFIX;
		mapPath = makeWindowsPath(mapPath);
		return mapPath;
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.ISourcesFinder#findSourceFiles(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String[] findSourceFiles(String componentName,
			String variant, String build, String epocRootPath
			) throws CannotFoundFileException {
		
		try {
			//Projects map file
			String mapFilePath = getMapFilePath(componentName, variant, build, epocRootPath);
			File mapFile = getMapFile(mapFilePath);
		
			String objectFileName = componentName.split(Pattern.quote("."))[0]; //$NON-NLS-1$

			//Get all source file paths from map file
			String sourcePaths [] = getAllSourceFilePaths(mapFile, epocRootPath, objectFileName);

			return sourcePaths;
			
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new CannotFoundFileException(e.getMessage() , e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CannotFoundFileException(e.getMessage() , e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CannotFoundFileException(e.getMessage() , e);
		}			
		
	}	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.sourcecode.ISourcesFinder#findSourceFiles(java.lang.String, java.lang.String, java.util.Collection)
	 */
	public String[] findSourceFiles(String componentName,
			 String epocRootPath, Collection<String> pathsToSeekMapFile
			) throws CannotFoundFileException {
		
		try {
			//Projects map file
			String mapFileName = componentName + MAP_FILE_SUFFIX;
			
			File mapFile = getMapFile(pathsToSeekMapFile, componentName, mapFileName);
		
			//If we did not found map file, we cannot proceed to src file seek
			if(mapFile == null){
				String msg = "'" + mapFileName  //$NON-NLS-1$
					+"' " //$NON-NLS-1$
					+Messages.getString("MapSourceFinder.MapFileNotFoundForSource_ErrMsg_Part1") //$NON-NLS-1$
					+componentName; 
				String consoleMsg = msg + " "  //$NON-NLS-1$
					+Messages.getString("MapSourceFinder.MapFileNotFoundForSource_ErrMsg_Part2"); //$NON-NLS-1$

				String pathSeparator= ", "; //$NON-NLS-1$
				for (Iterator<String> iterator = pathsToSeekMapFile.iterator(); iterator
						.hasNext();) {
					String path = (String) iterator.next();
					consoleMsg += path + pathSeparator;
				}
				consoleMsg = consoleMsg.substring(0, consoleMsg.length() - pathSeparator.length());
				printUtility.println(consoleMsg, IConsolePrintUtility.MSG_ERROR);
				throw new CannotFoundFileException(msg);
			}
			String objectFileName = componentName.split(Pattern.quote("."))[0]; //$NON-NLS-1$

			//Get all source file paths from map file
			String sourcePaths [] = getAllSourceFilePaths(mapFile, epocRootPath, objectFileName);

			return sourcePaths;
			
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new CannotFoundFileException(e.getMessage() , e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CannotFoundFileException(e.getMessage() , e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CannotFoundFileException(e.getMessage() , e);
		}			
		
	}
	/**
	 * Get all source file lines from given file.
	 * @param file File object to be checked for.
	 * @param epocRootPath EPOCROOT path.
	 * @param objectFileName Object file name used to filter results.
	 * @return all source file lines from given file.
	 * @throws IOException
	 */
	private String[] getAllSourceFilePaths(File file, String epocRootPath, String objectFileName) throws IOException {

	FileInputStream in = null;
	InputStreamReader isr = null;
	BufferedReader br = null;
	//HashSet does not allow duplicates
	HashSet<String> sourceFilePaths = new HashSet<String>();

	try {
		in = new FileInputStream(file);
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		String line = null;

		// seek wanted text from file line by line
		while ((line = br.readLine()) != null) {
			// if we found wanted text, stopping search
			String srcLine = isSourceFilePathLine(line, epocRootPath, objectFileName); 
			if (srcLine != null) {
				sourceFilePaths.add(srcLine);
			}
		}
 
	} finally {
		if (in != null) {
			in.close();
		}
		if (isr != null) {
			isr.close();
		}
		if (br != null) {
			br.close();
		}
	}

		return (String []) sourceFilePaths.toArray(new String [0]);
	}
	
	/**
	 * Check if line from file is a source file line or not.
	 * @param line Line to be checked.
	 * @param epocRootPath EPOCROOT path.
	 * @param objectFileName Object file name used to filter results.
	 * @return Path to src file or <code>null</code> if it was not src line.
	 */
	private String isSourceFilePathLine(String line, String epocRootPath, String objectFileName) {
		
//		 * Source file lines are e.g. lines:
//	 *  \s60\mw\web\WebEngine\OssWebengine\WebCore\kwq\kwqvariant.cpp 0x00000000   Number         0  KWQVariant.o ABSOLUTE
//	 *  \src\cedar\generic\base\e32\EUSER\EPOC\ up_dll_file.cpp 0x00000000   Number         0  up_dll_file.o ABSOLUTE
//	 *  \\src\\cedar\\generic\\BASE\\E32\\compsupp\\RVCT2_2\\ucppinit_aeabi.cpp 0x00000000   Number         0  ucppinit_aeabi.o ABSOLUTE

//	 * but not lines:
//	 *  \EPOC32\BUILD\src\COMMON\GENERIC\comms-infras\esock\group\ESOCK\ARMV5\VtblExports.s 0x00000000   Number         0  VtblExports.o ABSOLUTE
//	 *  \EPOC32\BUILD\src\cedar\generic\base\e32\EDLL\ARMV5\ urel\ uc_dll_.cpp 0x00000000   Number         0  uc_dll_.o ABSOLUTE
//	 *  \\EPOC32\\BUILD\\src\\cedar\\generic\\base\\e32\\EDLL\\ARMV5\\urel\\uc_dll_.cpp 0x00000000   Number         0  uc_dll_.o ABSOLUTE

		//   Also source codes can be from users own source projects, e.g.
//		\Projects\Trombi\Carbide\creator\src\creatormailboxelement.cpp 0x00000000   Number         0  creator.in ABSOLUTE
		
		if(line == null || line.trim().length() < 1){
			return null;
		}
		
		if( (line.contains(CPP_SUFFIX) || line.contains(C_SUFFIX) )&& 
				line.contains(NULL_ADDRESS) &&
				line.contains(ABSOLUTE) &&
				line.toLowerCase().contains(objectFileName.toLowerCase())){
			
			//if we found wanted text, checking that it does not contain 
			//\EPOC32\BUILD or \\EPOC32\\BUILD, because then its not a source, but build file
			//fixing slashes so we can find epoc32 build folder in any cases.
			String srcPath = line.replace("/", BACKSLASH).trim(); //$NON-NLS-1$ //$NON-NLS-2$
			//removing multiple spaces: " +" means more than one white spaces, e.g. "a    b c" -> "a b c"

			//Making sure that souce location does not point to BUILD folder
			if(srcPath.indexOf(EPOC32_BUILD) == SourceFileLocation.OFFSET_NOT_FOUND 
					&& srcPath.indexOf(EPOC32_BUILD_DOUBLE_SLASHES) == SourceFileLocation.OFFSET_NOT_FOUND){
				
				if(!srcPath.startsWith(BACKSLASH)){
					srcPath = BACKSLASH + srcPath;
				}
				
				//if path starts with src\... or s60\... its a symbian or S60 src path
				if(srcPath.startsWith( SRC_PATH_PREFIX) || srcPath.startsWith( S60_PATH_PREFIX) 
						|| srcPath.startsWith( SRC_PATH_PREFIX_DOUBLE_SLASHS) || srcPath.startsWith( S60_PATH_PREFIX_DOUBLE_SLASHS)
						)
				{
					
					String srcPathOrig = srcPath.substring(0, srcPath.indexOf(NULL_ADDRESS)).trim();
					srcPath = epocRootPath + srcPathOrig;
					srcPath = makeWindowsPath(srcPath);
					// Making sure that path does not have duplicate EPOCROOT.
					File file = stripEpocRootFromPath(srcPathOrig, epocRootPath, new File(srcPath));
					return file.getAbsolutePath();
				}
				//otherwise it can be users own source codes from own projects, and path can be anything
				//then cheking that found source file path is actually real path to existing file
				else{
					String srcPathOrig = srcPath.substring(0, srcPath.indexOf(NULL_ADDRESS)).trim();
					srcPath = epocRootPath + srcPathOrig;
					srcPath = makeWindowsPath(srcPath);
					// Making sure that path does not have duplicate EPOCROOT.
					File file = stripEpocRootFromPath(srcPathOrig, epocRootPath, new File(srcPath));
					if(file.exists()){
						return file.getAbsolutePath();		
					}
				}
			}
			
		}
				
		return null;
	}
	
	/**
	 * The method parses the given content for certain characters in a specific order
	 * to determine whether method definition has started or not.
	 * @param lineContent content to be parsed.
	 * @return true if the method definition is started else false.
	 */
	private boolean parseForMethodDefinition(String lineContent)
	{
		Stack<Character> chars_stack = new Stack<Character>();
		char [] line_chars = lineContent.toCharArray();
		
		final char METHOD_OPENING_CHAR = '(';
		final char METHOD_CLOSING_CHAR = ')';
		final char METHOD_DEFINITION_OPENING_CHAR = '{';
		
		for(char c: line_chars)
		{
			switch(c){
				case METHOD_OPENING_CHAR:
				case METHOD_CLOSING_CHAR:
				case METHOD_DEFINITION_OPENING_CHAR:
					chars_stack.push(c);
					break;
			}
		}
		
		while(!chars_stack.isEmpty())
		{
			Character lastChar = chars_stack.pop();
			
			if(lastChar == METHOD_DEFINITION_OPENING_CHAR)
			{
				char prevChar_1 = chars_stack.pop();
				char prevChar_2 = chars_stack.pop();
				
				if((prevChar_1 == METHOD_CLOSING_CHAR) && (prevChar_2 == METHOD_OPENING_CHAR))
					return true;
			}
		}
		return false;
		
	}
	
	/**
	 * The method checks for some predefined delimiter characters and 
	 * returns the content before those characters.
	 * @param input 
	 * @return
	 */
	private String fetchContentBeforeDefinition(String input)
	{
		if(input.contains(SEMI_COLON))
		{
			if(input.contains(METHOD_DEFINITION_CLOSING_CHAR)
					&& input.indexOf(METHOD_DEFINITION_CLOSING_CHAR) < input.indexOf(SEMI_COLON))
				return input.substring(0, input.indexOf(METHOD_DEFINITION_CLOSING_CHAR) +1);
			else	
				return input.substring(0, input.indexOf(SEMI_COLON) +1);
		}
		else if(input.contains(METHOD_DEFINITION_CLOSING_CHAR)){
			return input.substring(0, input.indexOf(METHOD_DEFINITION_CLOSING_CHAR) +1);
		}
		else{
			return input.toString();
		}
	}
	
	private boolean hasDelimiter(String line)
	{
		if(line.contains(METHOD_DEFINITION_CLOSING_CHAR) ||
				line.contains(SEMI_COLON))
			return true;
		else
			return false;
	}
	
}
