package com.nokia.s60tools.symbianfoundationtemplates.resources;

import org.eclipse.jface.dialogs.IDialogSettings;
import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;

public class LastUsedData {

	public static final String COMPANY_NAME = "Company name";
	public static final String COMPANY_COPYRIGHT = "Company copyright";
	
	public static final String COMPANY_NAME_SECTION = "Company name section";
	public static final String COMPANY_COPYRIGHT_SECTION = "Company copyright section";
	
	private static final int MAX_SAVED_VALUES = 5;
	
	public static enum ValueTypes {NAME, COPYRIGHT};
	
	/**
	 * @param pathType specifies the type of the value being stored. This type
	 * maps to a labelled set of values under a section, in dialog_settings.xml
	 * @param value set of values to be stored.
	 */
	public void saveValues(ValueTypes pathType, String value) {
		try {
			switch (pathType) {
				case NAME: {
					savePath(value, COMPANY_NAME, getSection(COMPANY_NAME_SECTION));
					break;
				}	
				case COPYRIGHT:{
					savePath(value, COMPANY_COPYRIGHT, getSection(COMPANY_COPYRIGHT_SECTION));
					break;
				}
				default: 
					break;
			}
		} catch (Exception E) {
			E.printStackTrace();
		}
	}
	
	/**
	 * @param valueType type of the value to be retreived 
	 * @return user's previous stored values for this type.
	 */
	public String[] getPreviousValues(ValueTypes valueType) {
		try {
			String[] retVal = null;
			
			switch (valueType) {
				case NAME: {
					retVal = getPreviousPaths(COMPANY_NAME_SECTION,COMPANY_NAME);
					break;
				}
				case COPYRIGHT: {
					retVal = getPreviousPaths(COMPANY_COPYRIGHT_SECTION,COMPANY_COPYRIGHT);
					break;
				}
				default: 
					break;
			}			
			return retVal;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Saves given path to correct section in dialog_settings.xml
	 * @param path path to be saved
	 * @param listLabel name of the array which contains the values
	 * @param section section which contain the actual array 
	 */
	protected void savePath(String path, String listLabel, IDialogSettings section) {
		if (section != null) {
			String[] previousValues = section.getArray(listLabel);
			
			// No previous values exist
			if (previousValues == null) {
				previousValues = new String[1];
				previousValues[0] = path;
			// Previous values exists
			} else {
				int valuesCount = previousValues.length;
				
				boolean valueExisted = false;
				// see if passed value already exist.
				for (int i = 0; i < valuesCount; i++) {
					if (previousValues[i].compareToIgnoreCase(path) == 0) {
						valueExisted = true;
						
						// passed value exists, move it to first position
						for (int j = i; j > 0; j--) {
							previousValues[j] = previousValues[j-1];
						}
						previousValues[0] = path;
						
						break;
					}
				}
				
				// passed value did not exist, add it to first position (and move older values "down")
				if (!valueExisted) {
						if (valuesCount >= MAX_SAVED_VALUES) {
							for (int i = valuesCount-1; i > 0; i--) {
								previousValues[i] = previousValues[i-1];
							}
							previousValues[0] = path;
						}else{
							String[] values = new String[valuesCount + 1];
							values[0] = path;
							for (int i = 0; i < valuesCount; i++) {
								values[i+1] = previousValues[i];
							}
							previousValues = values;
						}
					}
				}
			section.put(listLabel, previousValues);
		}
	}
	
	/**
	 * Returns previously entered values of wanted context (i.e. wizard page).
	 * @param section section which contains array
	 * @param listLabel name of the array whose values are needed
	 * @return previously entered paths for given section
	 */
	protected String[] getPreviousPaths(String section, String listLabel) {
		String[] retVal = null;
		IDialogSettings sect = getSection(section);
		if (sect != null)
			retVal = sect.getArray(listLabel);
		
		return retVal;
	}
	
	/**
	 * @param sectionName name of the section to be retreived
	 * @return section which maps to given name, in dialog_setting.xml file.
	 */
	protected IDialogSettings getSection(String sectionName) {
		IDialogSettings retVal = null;
		if (SymbianFoundationTemplates.getDefault().getDialogSettings() != null) {
			 retVal = SymbianFoundationTemplates.getDefault().getDialogSettings().getSection(sectionName);
			if (retVal == null) {
			 retVal = SymbianFoundationTemplates.getDefault().getDialogSettings().addNewSection(sectionName);
			}
		}
		return retVal;
	}	

}
