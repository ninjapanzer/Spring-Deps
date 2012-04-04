/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools.crud;

import com.nurelm.assessormapping.annotation.FormDisplay;
import com.nurelm.assessormapping.struct.AccessorStruct;
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
public class EntityPropertiesProcessor implements AccessorProcessor {

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
      GenericAccessorTools.captureAccessors(clazz);
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
  @Override
  public HashMap<String, AccessorStruct> getGetters() {
    return getters;
  }

  /**
   *
   * @return
   */
  @Override
  public ArrayList<AccessorStruct> getProcessedAccessors() {
    return processedAccessors;
  }
  
  @Override
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
  @Override
  public HashMap<String, AccessorStruct> getSetters() {
    return setters;
  }

  /**
   *
   * @return
   */
  @Override
  public ArrayList<AccessorStruct> getEntityProperties() {
    return entityProperties;
  }
  
  public Map<String, AccessorStruct> getEntityPropertiesMap(){
    return GenericAccessorTools.getEntityPropertiesMap(entityProperties);
  }

  /**
   *
   * @param clazz
   * @throws Exception
   */
  @Override
  public void extractEntityProperties(Class<? extends AbstractEntity> clazz) throws Exception {
    this.entityProperties = (ArrayList<AccessorStruct>) this.captureEntityProperties(clazz);
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

  public Set<AccessorStruct> getGetterSet() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Set<AccessorStruct> getSetterSet() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
