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
 
package com.nokia.s60tools.creator.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.xml.CreatorXML;

/**
 * Class for holding components in UI
 */
public class Components implements IComponentServices {
	
	private LinkedHashMap<String, Vector<AbstractComponent>> components;
	private String fileName;
	private String filePath;

	

	/**
	 * @return all components
	 */
	public Map<String, Vector<AbstractComponent>> getComponents() {
		if(components == null){
			components = new LinkedHashMap<String,  Vector<AbstractComponent>>();
		}			
		return components;
	}
	
	/**
	 * Get Components of selected type
	 * @param compoentType, component type as it is in {@link AbstractComponent} subclasses. Use
	 * <code>AbstractComponent.getType()</code> to get component id.
	 * @return a component, null if not exist
	 */
	public Vector<AbstractComponent> getComponents(String type) {
		
		return getComponents().get(type);
	}
	

	/**
	 * Add one component to components
	 * @param component
	 */
	public void addComponent(AbstractComponent component) {
		Vector<AbstractComponent> v = getComponents(component.getType());
		if(v == null){
			v = new Vector<AbstractComponent>();
		}
		if(v.contains(component)){
			v.remove(component);
			v.add(component);	
		}else{
			v.add(component);
		}
				
		getComponents().put(component.getType(), v);
	}

	/**
	 * Get keys to components added
	 * @return Keys (Types)
	 */
	public Set<String> getComponentTypes() {		
		return getComponents().keySet();
	}

	/**
	 * Get Component by type and component String
	 * @param componentType
	 * @param componentToString
	 * @return a Component or null if not found
	 */
	public AbstractComponent getComponentByTypeAndComponentString(
			String componentType, String componentToString) {
		
		AbstractComponent wantedComponent = null;
		
		Vector<AbstractComponent> comps = getComponents(componentType);
		//Looping through all components and seek wanted component 
		for (Iterator<AbstractComponent> iterator = comps.iterator(); iterator.hasNext();) {
			AbstractComponent comp = (AbstractComponent) iterator
					.next();
			if(componentToString.equals(comp.toString())){
				wantedComponent = comp;
				break;
			}
		}
		
		return wantedComponent;
	}
	
	/**
	 * Get Component by type and component String
	 * @param componentToString
	 * @return a Component or null if not found
	 */
	public AbstractComponent getComponentByComponentString(String componentToString) {

		AbstractComponent wantedComponent = null;

		Set<String> keys = getComponentTypes();
		for (Iterator<String> typesIt = keys.iterator(); typesIt.hasNext();) {
			String type = (String) typesIt.next();
			Vector<AbstractComponent> comps = getComponents(type);
			//Looping through all components and seek wanted component 
			for (Iterator<AbstractComponent> iterator = comps.iterator(); iterator.hasNext();) {
				AbstractComponent comp = (AbstractComponent) iterator
						.next();
				if(componentToString.equals(comp.toString())){
					wantedComponent = comp;
					break;
				}
			}
		}
		
		return wantedComponent;
	}	
	

	/**
	 * Removes component from list
	 * @param component
	 */
	public void remove(AbstractComponent component) {
		Vector<AbstractComponent> v = getComponents(component.getType());
		if(v == null){
			return;
		}
		v.remove(component);
		
	}

	/**
	 * Get components as XML String
	 * @return components
	 */
	public String toXMLString() {
		CreatorXML xml = new CreatorXML(this);
		return xml.toString();
	}

	/**
	 * Update existing component
	 * @param oldComp old existing component
	 * @param editedComp new edited component
	 */
	public void updateComponent(AbstractComponent oldComp,
			AbstractComponent editedComp) 
			{
		
		Vector<AbstractComponent> v = getComponents(oldComp.getType());
		if(v == null){
			v = new Vector<AbstractComponent>();
		}

		if(v.contains(oldComp)){
			v.remove(oldComp);
			v.add(editedComp);	
		}else{
			v.add(editedComp);
		}
		
				
		getComponents().put(editedComp.getType(), v);		
		
	}

	/**
	 * Has this set of components one or more Contact Sets.
	 * @return true if at least one contact set is found
	 */
	public boolean hasContactSets() {
		return getComponents(CreatorEditorSettings.TYPE_CONTACT_SET) != null 
			&& getComponents(CreatorEditorSettings.TYPE_CONTACT_SET).size() > 0 ? true : false;
	}

	/**
	 * Removes all references to component
	 * @param component
	 */
	public void removeReferencesToComponent(AbstractComponent component) {
		
		//All components
		Collection<Vector<AbstractComponent>> values = getComponents().values();
		
		//Looping through all components
		for (Iterator<Vector<AbstractComponent>> iterator = values.iterator(); iterator.hasNext();) {
			//found all component types
			Vector<AbstractComponent> componentsByType = (Vector<AbstractComponent>) iterator
					.next();
			//looping through all component types and found all components by that type
			for (Iterator<AbstractComponent> iterator2 = componentsByType.iterator(); iterator2
					.hasNext();) {
				//Found a component
				AbstractComponent comp = (AbstractComponent) iterator2
						.next();
				//IF that component has a reference to component which references wanted to remove, removing
				if(comp.hasReferenceToAnotherComponent()){
					if(comp.getReferenceToAnotherComponent().equals(component)){
						comp.setReferenceToAnotherComponent(null);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.IComponentServices#isReferencedByAnotherComponent(com.nokia.s60tools.creator.components.AbstractComponent)
	 */
	public boolean isReferencedByAnotherComponent(AbstractComponent component) {
		
		//All components
		Collection<Vector<AbstractComponent>> values = getComponents().values();
		
		//Looping through all components
		for (Iterator<Vector<AbstractComponent>> iterator = values.iterator(); iterator.hasNext();) {
			//found all component types
			Vector<AbstractComponent> componentsByType = (Vector<AbstractComponent>) iterator
					.next();
			//looping through all component types and found all components by that type
			for (Iterator<AbstractComponent> iterator2 = componentsByType.iterator(); iterator2
					.hasNext();) {
				//Found a component
				AbstractComponent comp = (AbstractComponent) iterator2
						.next();
				//IF that component has a reference to component which references wanted to remove, removing
				if(comp.hasReferenceToAnotherComponent()){
					if(comp.getReferenceToAnotherComponent().equals(component)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Set the name of the script file
	 * @param fileName
	 */	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Get the name of the script file.
	 * @return fileName
	 */
	public String getFileName(){
		return fileName;
	}

	/**
	 * Set the full path of the script file
	 * @param filePath
	 */		
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * Get the full path of the script file.
	 * @return fileName
	 */
	public String getFilePath(){
		return filePath;
	}	

}
