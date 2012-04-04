/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools;

import com.nurelm.assessormapping.annotation.FormDisplay;
import com.nurelm.assessormapping.struct.AccessorStruct;
import com.nurelm.assessormapping.struct.AccessorTypeEnum;
import com.nurelm.assessormapping.struct.FormVisibilityEnum;
import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nurelm
 */
public class EntityPropertiesProcessor {

  private ArrayList<AccessorStruct> entityProperties;
  private ArrayList<AccessorStruct> processedAccessors;
  private HashMap<String, AccessorStruct> getters;
  private HashMap<String, AccessorStruct> setters;
  private ArrayList<AccessorStruct> SortedDisplayOnly;

  /**
   *
   */
  public EntityPropertiesProcessor() {
  }

  /**
   *
   * @param clazz
   */
  public EntityPropertiesProcessor(Class<? extends AbstractEntity> clazz) {
    try {
      this.entityProperties = (ArrayList<AccessorStruct>) this.captureEntityProperties(clazz);
      this.captureAccessors(clazz);
    } catch (InstantiationException ex) {
      Logger.getLogger(EntityPropertiesProcessor.class.getName()).log(Level.OFF, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(EntityPropertiesProcessor.class.getName()).log(Level.OFF, null, ex);
    }

  }

  public EntityPropertiesProcessor(Object model){
    Class clazz = model.getClass();
    try {
      this.entityProperties = (ArrayList<AccessorStruct>) this.captureEntityProperties(clazz);
      this.captureAccessors(clazz);
    } catch (InstantiationException ex) {
      Logger.getLogger(EntityPropertiesProcessor.class.getName()).log(Level.OFF, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(EntityPropertiesProcessor.class.getName()).log(Level.OFF, null, ex);
    }
  }

  /**
   *
   * @return
   */
  public HashMap<String, AccessorStruct> getGetters() {
    return getters;
  }

  /**
   *
   * @return
   */
  public ArrayList<AccessorStruct> getProcessedAccessors() {
    return processedAccessors;
  }
  
  public Map<String, AccessorStruct> getProcessedAccessorsMap(){
    Map<String, AccessorStruct> propertyMap = new HashMap<String, AccessorStruct>();
    for(AccessorStruct item : processedAccessors){
      propertyMap.put(item.getBeanName(), item);
    }
    return propertyMap;
  }

  /**
   *
   * @return
   */
  public HashMap<String, AccessorStruct> getSetters() {
    return setters;
  }

  /**
   *
   * @return
   */
  public ArrayList<AccessorStruct> getEntityProperties() {
    return entityProperties;
  }
  
  public Map<String, AccessorStruct> getEntityPropertiesMap(){
    Map<String, AccessorStruct> propertyMap = new HashMap<String, AccessorStruct>();
    for(AccessorStruct item : entityProperties){
      propertyMap.put(item.getBeanName(), item);
    }
    return propertyMap;
  }

  /**
   *
   * @param clazz
   * @throws Exception
   */
  public void extractEntityProperties(Class<? extends AbstractEntity> clazz) throws Exception {
    this.entityProperties = (ArrayList<AccessorStruct>) this.captureEntityProperties(clazz);
  }

  private void captureAccessors(Class<? extends AbstractEntity> EntityClass) throws InstantiationException, IllegalAccessException {
    this.processedAccessors = new ArrayList<AccessorStruct>();
    AccessorStruct[] SortedProperties = null;
    this.getters = new HashMap<String, AccessorStruct>();
    this.setters = new HashMap<String, AccessorStruct>();
    //ArrayList<Integer> SortOrder = new ArrayList<Integer>();
    Object item = (Object) EntityClass.newInstance();
    Class itemClass = item.getClass();
    Method[] methods = itemClass.getDeclaredMethods();
    for (Method m : methods) {
      AccessorStruct tempProp = new AccessorStruct(m);
      try {
        this.processedAccessors.add(tempProp.clone());
        if (tempProp.getType() != null) {
          if (tempProp.getType().equals(AccessorTypeEnum.GET)) {
            getters.put(tempProp.getBeanName(), tempProp.clone());
          } else if (tempProp.getType().equals(AccessorTypeEnum.SET)) {
            this.setters.put(tempProp.getBeanName(), tempProp.clone());
          }
        }
      } catch (CloneNotSupportedException ex) {
        Logger.getLogger(EntityPropertiesProcessor.class.getName()).log(Level.OFF, null, ex);
      }
    }
  }

  private Collection<AccessorStruct> captureEntityProperties(Class<? extends AbstractEntity> E) throws InstantiationException, IllegalAccessException {
    List<AccessorStruct> Properties = new ArrayList<AccessorStruct>();
    AccessorStruct[] SortedProperties = null;
    ArrayList<Integer> SortOrder = new ArrayList<Integer>();
    Object item = (Object) E.newInstance();
    Class itemClass = item.getClass();
    Method[] methods = item.getClass().getDeclaredMethods();
    for (Method m : methods) {
      String rtype = m.getReturnType().getName();
      String accessorCheck = m.getName();
      if (!rtype.equals("void")) {
        AccessorStruct tempProp = new AccessorStruct(m);
        if (tempProp.getType().info().equals("get")) {
          try {
            Field itemField = itemClass.getDeclaredField(tempProp.getBeanName());
            if (itemField.isAnnotationPresent(FormDisplay.class)) {
              FormVisibilityEnum visible = itemField.getAnnotation(FormDisplay.class).visible();
              if (visible.equals(FormVisibilityEnum.DISPLAYED) ||
                  visible.equals(FormVisibilityEnum.HIDDEN)) {
                final Field tempItemField = itemField;
		final FormDisplay annotationItems = (FormDisplay) itemField.getAnnotation(FormDisplay.class);
                if (tempItemField != null) {
                  Properties.add(new AccessorStruct(m, itemField) {
                    /*{
                      if (!tempItemField.getAnnotation(FormDisplay.class).altName().equals("")) {
                        this.setShortName(tempItemField.getAnnotation(FormDisplay.class).altName());
                      }
                      this.setVisible(visible);
                    }*/
                  });
                } else {
                  //Properties.add(new AccessorStruct(m){{setType(annotationItems.type());}});
                }
                if (annotationItems.order() != -1) {
                  SortOrder.add(annotationItems.order());
                }
              }
            } else {
              //Properties.add(new AccessorStruct(m));
            }
          } catch (Exception ex) {
            //Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, "Method Does not have Field", ex);
          }
        }
      }
    }
    SortedProperties = new AccessorStruct[SortOrder.size()];
    ArrayList<AccessorStruct> FinalSortedProperties = new ArrayList<AccessorStruct>();
    int Index = 0;
    for (Integer PropertyItem : SortOrder) {
      SortedProperties[(int) PropertyItem] = Properties.get(Index++);
    }
    for (AccessorStruct accessorItem : SortedProperties) {
      if (accessorItem != null) {
        FinalSortedProperties.add(accessorItem);
      }
    }
    for (AccessorStruct SortedProperty : FinalSortedProperties) {
      Properties.remove(SortedProperty);
    }

    FinalSortedProperties.addAll(Properties);
    this.SortedDisplayOnly = FinalSortedProperties;
    return FinalSortedProperties;
  }
}
