/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

 
package com.nokia.s60tools.metadataeditor.xml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.transform.TransformerException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.s60tools.metadataeditor.core.MetadataEditorSettings;
import com.nokia.s60tools.metadataeditor.util.MetadataEditorConsole;
import com.nokia.s60tools.metadataeditor.util.MetadataFilenameGenerator;
import com.nokia.s60tools.util.xml.XMLIndentor;

/**
 * Metadata class representing metadata in Editor area as well as Metadata XML file in
 * hard drive. Use <code>toString()</code> to get XML String representing this metadata file.
 * <code>toFile()</code> can be used to store this XML to file. 
 * 
 * When user is updating values in UI and using <code>save</code> or <code>saveAs</code>
 * functionality, updata corresponding values and get XML data by using <code>toString()</code>.
 *
 */
public class MetadataXML {

	//public constants
	
	/**
	 * Value in version attributes when not set
	 */
	public static final String NOT_SET = "";
	
	/**
	 * Current XML data version. Only change when XML format is changing.
	 * NOTE: When XML format is changed, also backward compatibility issues
	 * should be taken care of!
	 */
	public static final String DATA_VERSION_1_0 = "1.0";

	/**
	 * Current XML data version. Only change when XML format is changing.
	 * NOTE: When XML format is changed, also backward compatibility issues
	 * should be taken care of!
	 */
	public static final String DATA_VERSION_2_0 = "2.0";
	
	
	/**
	 * Unique ID for new with wizard created metadata xml file.
	 * When file content is just this, UI will know that it's a new file. 
	 */
	public static final String NEW_API_METADATA_FILE_UID = "com.nokia.s60tools.metadataeditor.new_file";
	
	/**
	 * Characters that is not allowed to exist in XML
	 */
	public static final String[] FORBIDDEN_CHARS = 
	{"&", "<", ">" };	

	private static String [] noYesValues  = {"no" ,"yes"};


	/**
	 * default location for XML comments.
	 */
	public static final String DEFAULT_COMMENT_LOCATION = "xml";

	
	//DATA
	private String APIName;
	private String APIID;
	private String dataVersion;
	private String type;
	private String description;
	private String subsystem;
	private Vector<String> libs;
	private Map<String, Vector<String>> comments;
	private String releaseCategory;
	private String releaseSince;
	private boolean releaseDeprecated;
	private String releaseDeprecatedSince;
	private boolean htmlDocProvided;
	private boolean adaptation;
	private boolean extendedSDK;
	private String extendedSDKSince;
	private boolean extendedSDKDeprecated;
	private String extendedSDKDeprecatedSince;
	
	//FILE NAME
	private String fileName;

	/**
	 * flag will tell if this file is just created with Wizard.
	 * So the Editor view know that this can't be parsed.
	 * This will set as false when this.setName(String) is called.
	 */
	private boolean isNew;

	private String validityErrors;
	
	public MetadataXML() {
		super();
		this.fileName = NOT_SET;
		init(false);
	}	
	
	/**
	 * Use this conrstruction if metadata XML is new (not readed from file).
	 * When using this construction, remember to set APIID.
	 * @param fileName
	 * @throws NoSuchAlgorithmException
	 */
	public MetadataXML(String fileName) {
		super();
		this.fileName = fileName;
		this.APIID = NOT_SET;
		this.APIName = NOT_SET;
		init(true);
		
	}
	
	/**
	 * Use this conrstruction if metadata XML is new (not readed from file).
	 * Generating APIID from given APIName
	 * @param APIName
	 * @param fileName
	 * @throws NoSuchAlgorithmException if API ID MD5 checksum calculation fails
	 * @throws MetadataNotValidException 
	 */
	public MetadataXML(String APIName, String fileName) throws NoSuchAlgorithmException, MetadataNotValidException {
		super();
		this.APIName = removeForbiddenChars(APIName);		
		this.fileName = fileName;

		//Generate api ID by api name
		APIIDGenerator gen = new APIIDGenerator();		
		this.APIID = gen.getID(getAPIName());		
		
		init(true);
	}

	/**
	 * 
	 * @param APIName
	 * @param APIID
	 * @param dataVersion
	 * @param type
	 * @param description
	 * @param subsystem
	 * @param libs
	 * @param releaseCategory
	 * @param releaseSince
	 * @param releaseDeprecated
	 * @param releaseDeprecatedSince
	 * @param htmlDocProvided
	 * @param adaptation
	 * @param extendedSDK
	 * @param extendedSDKSince
	 * @param extendedSDKDeprecatedSince
	 * @param fileName
	 * @throws MetadataNotValidException 
	 */
	public MetadataXML(String APIName, String APIID, String dataVersion, String type, 
			String description, String subsystem, Vector<String> libs, String releaseCategory, 
			String releaseSince, boolean releaseDeprecated, String releaseDeprecatedSince, 
			boolean htmlDocProvided, boolean adaptation, boolean extendedSDK, String extendedSDKSince, 
			String extendedSDKDeprecatedSince, String fileName, Map<String, Vector<String>> comments) 
	throws MetadataNotValidException {
		super();
		this.APIName = removeForbiddenChars(APIName);
		
		this.APIID = APIID;
		this.dataVersion = dataVersion;
		this.type = type;
		this.comments = comments;
		this.description = removeForbiddenChars(description);
		this.subsystem = removeForbiddenChars(subsystem);
		this.libs = libs;
		this.releaseCategory = releaseCategory;
		this.releaseSince = releaseSince;
		this.releaseDeprecated = releaseDeprecated;
		this.releaseDeprecatedSince = releaseDeprecatedSince;
		this.htmlDocProvided = htmlDocProvided;
		this.adaptation = adaptation;
		this.extendedSDK = extendedSDK;
		this.extendedSDKSince = extendedSDKSince;
		this.extendedSDKDeprecatedSince = extendedSDKDeprecatedSince;
		this.fileName = fileName;
		this.isNew = false;
	}
		
	private void init(boolean isNew){
		this.dataVersion = DATA_VERSION_2_0;
		
		if(isNew){
			this.type = MetadataEditorSettings.getInstance().getDefaultType();
		}else{
			this.type = NOT_SET;
		}
		
		this.description = NOT_SET;
		this.subsystem = NOT_SET;
		this.releaseCategory = NOT_SET;
		this.releaseSince = NOT_SET;
		
		this.libs = new Vector<String>();
		this.releaseDeprecated = false;
		this.releaseDeprecatedSince = NOT_SET;
		this.extendedSDKDeprecated = false;
		this.extendedSDKDeprecatedSince = NOT_SET;
		this.extendedSDK = false;
		this.extendedSDKSince = NOT_SET;		
		
		this.validityErrors = NOT_SET;
		
		//When creating new file, setting flag
		this.isNew = isNew;
		comments = new HashMap<String, Vector<String>>();
		
	}

	public String getAdaptation(){
		return getYesNo(isAdaptation());
	}	
	public boolean isAdaptation() {
		return adaptation;
	}
	public void setAdaptation(boolean adaptation) {
		this.adaptation = adaptation;
	}
	public String getAPIID() {
		return APIID;
	}
	public void setAPIID(String apiid) {
		APIID = apiid;
	}
	public String getAPIName() {
		return APIName;
	}
	public void setAPIName(String name){
		APIName = removeForbiddenChars(name);
		this.isNew = false; 
		
	}
	public String getDataVersion() {
		return dataVersion;
	}	
	public void setDataVersion(String dataVersion) throws MetadataNotValidException {
		if(isVersionNumberValid(dataVersion)){
			this.dataVersion = dataVersion;	
		}
		else {
			throw new MetadataNotValidException("Data version was not valid, value was: " +dataVersion);
		}					
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = removeForbiddenChars(description);
	}
	public boolean isExtendedSDK() {
		return extendedSDK;
	}
	public void setExtendedSDK(boolean extendedSDK) {
		this.extendedSDK = extendedSDK;
	}
	public String getExtendedSDKDeprecatedSince() {
		return extendedSDKDeprecatedSince;
	}
	/**
	 * @param extendedSDKDeprecatedSince
	 * @throws MetadataNotValidException
	 */
	public void setExtendedSDKDeprecatedSince(String extendedSDKDeprecatedSince) throws MetadataNotValidException {

		if(extendedSDKDeprecatedSince.equals(NOT_SET)){
			this.setExtendedSDKDeprecated(false);
			this.extendedSDKDeprecatedSince = NOT_SET;
		}
		else if(isVersionNumberValid(extendedSDKDeprecatedSince)){			
			this.extendedSDKDeprecatedSince = extendedSDKDeprecatedSince;
			this.setExtendedSDKDeprecated(true);
		}
		else {
			throw new MetadataNotValidException("Extended SDK Deprecated since was not valid, value was: " +dataVersion);
		}					
	}
	public String getExtendedSDKSince() {
		return extendedSDKSince;
	}
	public void setExtendedSDKSince(String extendedSDKSince) throws MetadataNotValidException {
		if(isVersionNumberValid(extendedSDKSince)){			
			this.extendedSDKSince = extendedSDKSince;
			this.setExtendedSDK(true);
		}
		else {
			this.extendedSDKSince = NOT_SET;
			this.setExtendedSDK(true);
			throw new MetadataNotValidException("Extended SDK since was not valid, value was: " +dataVersion);
		}					
	}
	
	public String getHtmlDocProvided(){
		return getYesNo(isHtmlDocProvided());
	}
	public boolean isHtmlDocProvided() {
		return htmlDocProvided;
	}
	public void setHtmlDocProvided(boolean htmlDocProvided) {
		this.htmlDocProvided = htmlDocProvided;
	}
	public Vector<String> getLibs() {
		return libs;
	}
	public void setLibs(Vector<String> libs) {
		this.libs = libs;
	}
	public void addLib(String lib) {
		this.libs.add(lib);
	}

	public String getReleaseCategory() {
		return releaseCategory;
	}
	public void setReleaseCategory(String releaseCategory) {
		this.releaseCategory = releaseCategory;
	}
	public boolean isReleaseDeprecated() {
		return releaseDeprecated;
	}
	/**
	 * Setting this.releaseDeprecatedSince to "" as well
	 * @param releaseDeprecated
	 */
	public void setReleaseDeprecated(boolean releaseDeprecated) {
		if(!releaseDeprecated){
			this.releaseDeprecatedSince = NOT_SET;
		}
		this.releaseDeprecated = releaseDeprecated;
	}
	public String getReleaseDeprecatedSince() {
		return releaseDeprecatedSince;
	}
	/**
	 * @param releaseDeprecatedSince
	 * @throws MetadataNotValidException
	 */
	public void setReleaseDeprecatedSince(String releaseDeprecatedSince) throws MetadataNotValidException {

		if(isVersionNumberValid(releaseDeprecatedSince)){			
			this.releaseDeprecatedSince = releaseDeprecatedSince;
			this.setReleaseDeprecated(true);
		}
		else {
			//Must set to NOT_SET so isValid() will tell that it's not valid
			this.releaseDeprecatedSince = NOT_SET;
			this.setReleaseDeprecated(true);
			throw new MetadataNotValidException("Release deprecated since was not valid, value was: " +dataVersion);
		}					
	}
	public String getReleaseSince() {
		return releaseSince;
	}
	/**
	 * Sets the Release since version. If release category is "domain" 
	 * value can be this.NOT_SET or valid version number, if release category
	 * is "sdk" since must be valid release number. In any case release since
	 * can be set as this.NOT_SET when this.isValid() will tell that if xml
	 * is valid or not.
	 * 
	 * @param releaseSince
	 * @throws MetadataNotValidException
	 */
	public void setReleaseSince(String releaseSince) throws MetadataNotValidException {
		if(isVersionNumberValid(releaseSince) || releaseSince.equals(NOT_SET)){
			this.releaseSince = releaseSince;	
		}				
		else {
			throw new MetadataNotValidException("Release since was not valid, value was: " +releaseSince);
		}		
	}
	public String getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(String subsystem){
		this.subsystem = removeForbiddenChars(subsystem);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isExtendedSDKDeprecated() {
		return extendedSDKDeprecated;
	}

	/**
	 * Setting this.extendedSDKDeprecatedSince to "" as well
	 * @param extendedSDKDeprecated
	 */
	public void setExtendedSDKDeprecated(boolean extendedSDKDeprecated) {
		if(!extendedSDKDeprecated){
			this.extendedSDKDeprecatedSince = NOT_SET;
		}
		this.extendedSDKDeprecated = extendedSDKDeprecated;
	}
	
	public String getYesNo(boolean yes){
		return yes ? "yes" : "no" ;
	}
	public boolean isYes(String yesString){
		return yesString.equalsIgnoreCase("yes") ? true : false ;
	}
	
	public String toString(){
		return toXMLString();
	}
	
	/** 
	 * Get this object as XML String.
	 * @return a XML String representing this Metadata file
	 */
	private String toXMLString(){
		
		StringBuffer b = new StringBuffer();
		
		b.append("<?xml version=\"1.0\" ?>");
				
		String api = "api";
		
		if(hasCommentFor(api)){
			addCommentToBuffer(b, getComment(api) );
		}
		
		b.append("<" + api + " id=\"");
		b.append(this.getAPIID());
		b.append("\" dataversion=\"");
		b.append(this.getDataVersion());
		b.append("\">");
		String name = "name";
		if(hasCommentFor(name)){
			addCommentToBuffer(b, getComment(name) );
		}
		
		b.append("<" + name + ">");
		b.append(this.getAPIName());
		b.append("</" +name +">");
		
		
		String description = "description";
		if(hasCommentFor(description)){
			addCommentToBuffer(b, getComment(description) );
		}		
		b.append("<" + description + ">");
		b.append(this.getDescription());
		b.append("</" +description+">");

		String type = "type";
		if(hasCommentFor(type)){
			addCommentToBuffer(b, getComment(type) );
		}		
		b.append("<" + type + ">");
		b.append(this.getType());
		b.append("</" +type +">");

		//subsystem is subsystem version 1.0 and collection on 2.0 onwards. 
		String subsystem = getSubsystemString().toLowerCase();
		if(hasCommentFor(subsystem)){
			addCommentToBuffer(b, getComment(subsystem) );
		}		
		b.append("<");
		b.append(subsystem);
		b.append(">");
		b.append(this.getSubsystem());
		b.append("</");
		b.append(subsystem);
		b.append(">");

		String libs = "libs";
		if(hasCommentFor(libs)){
			addCommentToBuffer(b, getComment(libs) );
		}		
		b.append("<" + libs + ">");
		
		String lib = "lib";
		//Comments for "lib" cannot be added in loop, because same comments will then be for all "lib" elements
		//Now all comments for "lib"s will be above first "lib" element
		if(hasCommentFor(lib)){
			addCommentToBuffer(b, getComment(lib) );
		}					
		for(int i = 0; i< this.getLibs().size(); i++){
			b.append("<" + lib + " name=\"");
			b.append(this.getLibs().get(i));
			b.append("\"/>");
		}
		b.append("</" +libs +">");
		
		String release = "release";
		if(hasCommentFor(release)){
			addCommentToBuffer(b, getComment(release) );
		}		
		b.append("<" + release + " category=\"");
		b.append(this.getReleaseCategory());
		b.append("\" sinceversion=\"");
		b.append(this.getReleaseSince());
		b.append("\"");
		if(isReleaseDeprecated()){
			b.append(" deprecatedsince=\"");
			b.append(this.getReleaseDeprecatedSince());
			b.append("\"");
		}
		b.append("/>");
		
		String attributes = "attributes";
		if(hasCommentFor(attributes)){
			addCommentToBuffer(b, getComment(attributes) );
		}		
		b.append("<" + attributes + ">");		
		String htmlDoc = "htmldocprovided";
		if(hasCommentFor(htmlDoc)){
			addCommentToBuffer(b, getComment(htmlDoc) );
		}			
		b.append("<" + htmlDoc + ">");
		b.append(this.getYesNo(this.isHtmlDocProvided()));
		b.append("</" +htmlDoc +">");
		String adaptation = "adaptation";
		if(hasCommentFor(adaptation)){
			addCommentToBuffer(b, getComment(adaptation) );
		}			
		b.append("<" + adaptation + ">");
		b.append(this.getYesNo(this.isAdaptation()));
		b.append("</" + adaptation + ">");
		
		//extended SDK occurs only in version 1.0
		if(getDataVersion().equals(MetadataXML.DATA_VERSION_1_0)){
		
			if(isExtendedSDK()){
				String extendedSDK = "extendedsdk";
				if(hasCommentFor(extendedSDK)){
					addCommentToBuffer(b, getComment(extendedSDK) );
				}				
				b.append("<" + extendedSDK + " sinceversion=\"");
				b.append(this.getExtendedSDKSince());
				b.append("\"");
				if(isExtendedSDKDeprecated()){
					b.append(" deprecatedsince=\"");
					b.append(this.getExtendedSDKDeprecatedSince());
					b.append("\"");				
				}
				b.append("/>");	
			}
		
		}
		
		b.append("</" +attributes +">");
		
		b.append("</" +api +">");
	
		String xmlWithOutIndents = b.toString();
		
		String xmlWithIndents;
		try {
			xmlWithIndents = XMLIndentor.indentXML(xmlWithOutIndents);
			return xmlWithIndents;			
			// If indent fails, dont throw error, just return XML with no indents
		} catch (UnsupportedEncodingException e) {
			printIndentErrorMsg(e);
		} catch (TransformerException e) {
			printIndentErrorMsg(e);
		} catch (Exception e) {
			printIndentErrorMsg(e);
		}		
		return xmlWithOutIndents;
	}

	/**
	 * Adds comment to buffer
	 * @param b
	 * @param comments
	 */
	private void addCommentToBuffer(StringBuffer b, Vector<String> comments) {
		for (String comment : comments) {
			b.append("<!--");
			b.append(comment);			
			b.append("-->");
		}
		
	}

	/**
	 * Print indent error message
	 * @param e
	 */
	private void printIndentErrorMsg(Exception e) {
		e.printStackTrace();
		MetadataEditorConsole.getInstance().println(
				"Unable to indent Metadata file: '" +getFileName() + "'. Error was: " +e, 
				MetadataEditorConsole.MSG_ERROR);
	}
	
	
	
	
	/**
	 * Save this.toString() to file.
	 * @param fileName
	 * @throws IOException
	 */
	public void toFile( String fileName ) throws IOException {		
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					fileName));
			out.write(toXMLString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			
		}
	}		
	
	public static boolean isVersionNumberValid(String versionNumber){
		try {
			if( !isSet(versionNumber) || 					
					versionNumber.indexOf('.') == -1 ||
					versionNumber.length() != 3 ){
				return false;
			}
				Integer.parseInt(versionNumber.substring(0,1));
				Integer.parseInt(versionNumber.substring(2,3));
		} catch (Exception e) {
			//NumberFormatException can occur
			return false;
		}
		
		return true;
	}
	
	private static boolean isSet(String value){
		boolean isSet =  true;
		if(value == null || value.trim().length() == 0 || value.equals(NOT_SET)){
			isSet = false;
		}
		
		return isSet;
	}
	
	/**
	 * @return true if this MetadataXML is valid false otherwise
	 */
	public boolean isValid(){
		
		boolean valid = true;
		StringBuffer errors = new StringBuffer();
		
		if (!isSet(this.getAPIName())) {
			errors.append("Name is required.\n");
			valid = false;
		}
		if (!isSet(this.getAPIID())) {
			errors.append("API ID is required.\n");
			valid = false;
		}
		//Can occur if readed from file and not set
		if (!isSet(this.getDataVersion())) {
			errors.append("Data version is required.\n");
			valid = false;
		}
		if (!isSet(this.getDescription())) {
			errors.append("Description is required.\n");
			valid = false;
		}
		if (!isSet(this.getType())) {
			errors.append("Type is required.\n");
			valid = false;
		}
		if (!isSet(this.getSubsystem())) {
			errors.append(getSubsystemString()  +" is required.\n");
			valid = false;
		}
		if (!isSet(this.getReleaseCategory())) {
			errors.append("Category is required.\n");
			valid = false;
		}

		
		//version release since is mandatory if category is "sdk" 
		//optional if category is "domain"
		if((	this.getReleaseCategory().equalsIgnoreCase(getSDK_or_PublicString()) ) 
				&& (!isSet(this.getReleaseSince()) 
				|| !isVersionNumberValid(this.getReleaseSince())))
		{
			errors.append("Release since is required when category is " +getSDK_or_PublicString() +".\n");
			valid = false;
		}
		if(this.isReleaseDeprecated()){
			if(!isSet(this.getReleaseDeprecatedSince()) 
					 || !isVersionNumberValid(this.getReleaseDeprecatedSince()))
			{
				errors.append("Deprecated since is required if API is deprecated.\n");
				valid = false;
			}
			else if(isSDKDeprecatedBeforeCreated(this.getReleaseSince(), this.getReleaseDeprecatedSince())){
				errors.append("Release cannot be deprecated before it is released.\n");
				valid = false;
			}			
		}
		if(this.isExtendedSDK()){
			if(!isSet(this.getExtendedSDKSince()) 
					|| !isVersionNumberValid(this.getExtendedSDKSince()) )
			{
				errors.append("Extended SDK since version is required if API is part of extended SDK.\n");
				valid = false;
			}			
			
			if(this.isExtendedSDKDeprecated()){
				if(!isSet(this.getExtendedSDKDeprecatedSince())
						|| !isVersionNumberValid(this.getExtendedSDKDeprecatedSince()))
				{
					errors.append("Deprecated since is required.\n");
					valid = false;
				}
				if(isSDKDeprecatedBeforeCreated(this.getExtendedSDKSince(), this.getExtendedSDKDeprecatedSince())){
					errors.append("Extended SDK cannot be deprecated before it is released.\n");
					valid = false;
				}
			}
		}
		if(!valid){
			validityErrors = errors.toString();
		}else{
			validityErrors = NOT_SET;
		}
		
		return valid;		
	}

	public String getSubsystemString() {
		if(DATA_VERSION_1_0.equals(getDataVersion())){
			return "Subsystem";
		}else{
			return "Collection";
		}

	}

	public String getSDK_or_PublicString() {
		if(DATA_VERSION_1_0.equals(getDataVersion())){
			return "sdk";
		}else{
			return "public";
		}
	}
	
	private boolean isSDKDeprecatedBeforeCreated(String sdkVersion, String deprVersion){
		
		boolean deprBeforeCreated = true;
		try {
			Double sdk = new Double(sdkVersion);
			Double depr = new Double(deprVersion);
			int compare = sdk.compareTo(depr);
			deprBeforeCreated = compare <= 0 ? false : true ;
		} catch (Exception e) {
			//NumberFormatException may occur
			e.printStackTrace();
			MetadataEditorConsole.getInstance().println(
					"Exception on deprecation check: " +e.toString() 
					+". Values was: '" +sdkVersion +"', '" +deprVersion +"'.");
		}
		return deprBeforeCreated;
		
	}

	public boolean isNew() {
		return isNew;
	}

	public String[] getNoYesValues() {
		return noYesValues;
	}

	public String[] getCategoryValues(String SDKVersion) {
		return MetadataEditorSettings.getInstance().getCategoryValues(SDKVersion);
	}

	public String[] getSDKVersionValues() {
		return MetadataEditorSettings.getInstance().getSDKVersionValues();
	}

	public String[] getTypeValues() {
		return MetadataEditorSettings.getInstance().getTypeValues();
	}

	public String getValidityErrors() {
		//Make sure that errors are set propeply
		this.isValid();
		return validityErrors;
	}

	/**
	 * Checks that there are no forbidden characters and replaces
	 * with valid ones if needed.
	 * @param in String to be checked.
	 * @return Returns checked and corrected string.
	 */
	public static String removeForbiddenChars(String in){
		
		String out = new String(in); 
		for(int i=0; i<FORBIDDEN_CHARS.length; i++){
			out = out.replace(FORBIDDEN_CHARS[i], "");
		}
		
		return out;		
	}	
	
	/**
	 * Checks that there are no forbidden characters and replaces
	 * with valid ones if needed.
	 * @param in String to be checked.
	 * @return Returns checked and corrected string.
	 */
	public static boolean containForbiddenChars(String in){
		
		for(int i=0; i<FORBIDDEN_CHARS.length; i++){
			if(in.indexOf(FORBIDDEN_CHARS[i]) != -1){
				return true;
			}
		}
		
		return false;

	}		
	
	public boolean isAPINameChanged(){
		IPath file = new Path (fileName);		
		String fileN = file.lastSegment();
		String genFileN = MetadataFilenameGenerator.createMetadataFilename(APIName);
		return genFileN.equals(fileN) ? false : true;
	}
	
	/**
	 * Updates data version to 2.0 and sets {@link MetadataXML#setReleaseCategory(String)} to new version values.
	 */
	public void updateToVersion2_0(){
		this.dataVersion = DATA_VERSION_2_0;
		String category = getReleaseCategory();
		if(!NOT_SET.equals(category)){
			String [] catVal1 = MetadataEditorSettings.getInstance().getCategoryValues(DATA_VERSION_1_0);
			String [] catVal2 = MetadataEditorSettings.getInstance().getCategoryValues(DATA_VERSION_2_0);
			if(category.equals(catVal1[0])){
				setReleaseCategory(catVal2[0]);
			}else{
				setReleaseCategory(catVal2[1]);
			}
		}
	}

	/**
	 * Add comment to XML
	 * @param string
	 */
	public void addComment(String element, String comment) {
		if(comments.containsKey(element)){
			Vector<String> existingComments = comments.get(element);
			existingComments.add(comment);
			comments.put(element, existingComments);
		}else{
			Vector<String> newComments = new Vector<String>();
			newComments.add(comment);
			comments.put(element, newComments);
		}
	}
	
	/**
	 * Add comment to XML
	 * @param string
	 */
	public void addComments(String element, Vector<String> commentsForOneElement) {
		if(comments.containsKey(element)){
			Vector<String> existingComments = comments.get(element);
			existingComments.addAll(commentsForOneElement);
			comments.put(element, existingComments);
		}else{
			comments.put(element, commentsForOneElement);
		}		
	}	
	
	/**
	 * get comments, return empty vector if dont have any
	 * @return comments
	 */	 
	public Collection<Vector<String>> getComments(){
		return comments.values();
	}
	
	/**
	 * Check if this XML has comments or not
	 * @return <code>true</code> if has comments, <code>false</code> otherwise.
	 */
	public boolean hasComments(){
		return !comments.isEmpty();
	}
	
	/**
	 * Check if this XML has comments or not
	 * @return <code>true</code> if has comments, <code>false</code> otherwise.
	 */
	public boolean hasCommentFor(String elementName){
		return comments.containsKey(elementName);
	}	
	
	/**
	 * Check if this XML has comments or not
	 * @return <code>true</code> if has comments, <code>false</code> otherwise.
	 */
	public Vector<String> getComment(String elementName){
		return comments.get(elementName);
	}		
	
}
