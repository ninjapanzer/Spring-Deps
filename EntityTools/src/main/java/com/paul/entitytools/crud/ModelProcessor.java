/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools.crud;

import com.nurelm.assessormapping.struct.AccessorStruct;
import com.nurelm.genericabstracts.Entity.AbstractEntity;
import com.paul.entitytools.crud.AccessorProcessor;
import com.paul.entitytools.crud.GenericAccessorTools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *
 * Document : ModelProcessor Created on : Mar 6, 2012, 6:43:19 PM Author :
 * samuraipanzer
 */
/**
 * ModelProcessor
 *
 * @author samuraipanzer
 */
public class ModelProcessor implements AccessorProcessor {

  private ArrayList<AccessorStruct> entityProperties;
  private ArrayList<AccessorStruct> processedAccessors;
  private HashMap<String, AccessorStruct> getters;
  private HashMap<String, AccessorStruct> setters;
  private ArrayList<AccessorStruct> SortedDisplayOnly;
  private Set<AccessorStruct> getterSet;
  private Set<AccessorStruct> setterSet;

  public ModelProcessor(Class clazz) {
    try {
      this.entityProperties = (ArrayList<AccessorStruct>) GenericAccessorTools.captureModelProperties(clazz);
      this.processedAccessors = (ArrayList<AccessorStruct>) GenericAccessorTools.captureAccessors(clazz);
      this.getters = (HashMap<String, AccessorStruct>) GenericAccessorTools.getGetters(this.entityProperties);
      this.setters = (HashMap<String, AccessorStruct>) GenericAccessorTools.getSetters(this.entityProperties);
      this.getterSet = new LinkedHashSet<AccessorStruct>(this.getters.values());
      this.setterSet = new LinkedHashSet<AccessorStruct>(this.setters.values());
    } catch (InstantiationException ex) {
      Logger.getLogger(ModelProcessor.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(ModelProcessor.class.getName()).log(Level.SEVERE, null, ex);
    }

  }



  public void extractEntityProperties(Class<? extends AbstractEntity> clazz) throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public ArrayList<AccessorStruct> getEntityProperties() {
    return this.entityProperties;
  }

  public Map<String, AccessorStruct> getEntityPropertiesMap() {
    return GenericAccessorTools.getEntityPropertiesMap(this.entityProperties);
  }

  public HashMap<String, AccessorStruct> getGetters() {
    return this.getters;
  }

  public ArrayList<AccessorStruct> getProcessedAccessors() {
    return this.processedAccessors;
  }

  public Map<String, AccessorStruct> getProcessedAccessorsMap() {
    return GenericAccessorTools.getEntityPropertiesMap(this.processedAccessors);
  }

  public HashMap<String, AccessorStruct> getSetters() {
    return this.setters;
  }

  public Set<AccessorStruct> getGetterSet() {
    return getterSet;
  }

  public Set<AccessorStruct> getSetterSet() {
    return setterSet;
  }


}
