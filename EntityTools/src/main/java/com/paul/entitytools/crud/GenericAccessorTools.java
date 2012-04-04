/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools.crud;

import com.nurelm.assessormapping.struct.AccessorStruct;
import com.nurelm.assessormapping.struct.AccessorTypeEnum;
import com.nurelm.genericabstracts.Entity.AbstractEntity;
import com.nurelm.genericabstracts.Entity.AbstractEntityIface;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author samuraipanzer
 */
public class GenericAccessorTools {

  //Sets up the items I wanna test against
  private static Map<Class<? extends Annotation>, Object> JPAAnnotations;

  public static Collection<AccessorStruct> captureAccessors(Class EntityClass) throws InstantiationException, IllegalAccessException {
    ArrayList<AccessorStruct> processedAccessors = new ArrayList<AccessorStruct>();
    Object item = (Object) EntityClass.newInstance();
    Class itemClass = item.getClass();
    Method[] methods = itemClass.getDeclaredMethods();
    for (Method m : methods) {
      AccessorStruct tempProp = new AccessorStruct(m);
      try {
	processedAccessors.add(tempProp.clone());
      } catch (CloneNotSupportedException ex) {
	Logger.getLogger(GenericAccessorTools.class.getName()).log(Level.OFF, null, ex);
      }
    }
    return processedAccessors;
  }

  private static Map<Class<? extends Annotation>, Object> setupJPAAnnotations() {
    Map<Class<? extends Annotation>, Object> Annotations = new HashMap<Class<? extends Annotation>, Object>();
    Annotations.put(Column.class, null);
    Annotations.put(OneToMany.class, null);
    Annotations.put(OneToOne.class, null);
    Annotations.put(JoinColumn.class, null);
    return Annotations;
  }

  public static Map<String, AccessorStruct> getEntityPropertiesMap(Collection<AccessorStruct> items) {
    Map<String, AccessorStruct> propertyMap = new HashMap<String, AccessorStruct>();
    for (AccessorStruct item : items) {
      propertyMap.put(item.getBeanName(), item);
    }
    return propertyMap;
  }

  public static Map<String, AccessorStruct> getGetters(Collection<AccessorStruct> items) {
    Map<String, AccessorStruct> getters = new HashMap<String, AccessorStruct>();
    for (AccessorStruct item : items) {
      if (item.getType() != null) {
	if (item.getType().equals(AccessorTypeEnum.GET)) {
	  try {
	    getters.put(item.getBeanName(), item.clone());
	  } catch (CloneNotSupportedException ex) {
	    Logger.getLogger(GenericAccessorTools.class.getName()).log(Level.WARNING, null, ex);
	  }
	}
      }
    }
    return getters;
  }

  public static Map<String, AccessorStruct> getSetters(Collection<AccessorStruct> items) {
    Map<String, AccessorStruct> setters = new HashMap<String, AccessorStruct>();
    for (AccessorStruct item : items) {
      if (item.getType() != null) {
	if (item.getType().equals(AccessorTypeEnum.SET)) {
	  try {
	    setters.put(item.getBeanName(), item.clone());
	  } catch (CloneNotSupportedException ex) {
	    Logger.getLogger(GenericAccessorTools.class.getName()).log(Level.WARNING, null, ex);
	  }
	}
      }
    }
    return setters;
  }

  /**
   * Do not use. Designed to allow for abstraction of annotation processing at
   * execution
   *
   * @param EntityClass
   * @param annotations
   * @return
   */
  public static Collection<AccessorStruct> captureFieldAnnotationProperties(Class<? extends AbstractEntity> EntityClass, Collection<? extends Annotation> annotations) {
    ArrayList<AccessorStruct> items = new ArrayList<AccessorStruct>();
    for (Annotation item : annotations) {
      items.addAll(GenericAccessorTools.captureAnnotationEntityProps(EntityClass, item));
    }
    throw new UnsupportedOperationException("Still Working on this one");
    //return items;
  }

  /**
   * Do not use. Designed to allow for abstraction of annotation processing at
   * execution
   *
   * @param EntityClass
   * @param annotation
   * @return
   */
  public static Collection<AccessorStruct> captureAnnotationEntityProps(Class<? extends AbstractEntity> EntityClass, Annotation annotation) {
    ArrayList<AccessorStruct> items = new ArrayList<AccessorStruct>();
    throw new UnsupportedOperationException("Still Working on this one");
    //return items;
  }

  public static Collection<AccessorStruct> captureJPAEntityProperties(Class<? extends AbstractEntityIface> EntityClass) throws InstantiationException, IllegalAccessException {
    Map<Class<? extends Annotation>, Object> annotationsMap = GenericAccessorTools.setupJPAAnnotations();
    ArrayList<AccessorStruct> entityProperties = new ArrayList<AccessorStruct>();
    Map<AccessorStruct, Object> filteredAccessors = new HashMap<AccessorStruct, Object>();
    Object item = (Object) EntityClass.newInstance();
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
	    List<Annotation> annotations = Arrays.asList(itemField.getAnnotations());
	    for (Annotation type : annotations) {
	      if (annotationsMap.containsKey(type.annotationType())) {
		filteredAccessors.put(tempProp, null);
	      }
	    }
	  } catch (NoSuchFieldException ex) {
	    Logger.getLogger(GenericAccessorTools.class.getName()).log(Level.WARNING, itemClass.getName(), ex);
	  } catch (SecurityException ex) {
	    Logger.getLogger(GenericAccessorTools.class.getName()).log(Level.WARNING, itemClass.getName(), ex);
	  }
	}
      }
    }
    entityProperties = new ArrayList<AccessorStruct>(filteredAccessors.keySet());
    return entityProperties;
  }

  public static Collection<AccessorStruct> captureModelProperties(Class modelClass) throws InstantiationException, IllegalAccessException {
    Map<Class<? extends Annotation>, Object> annotationsMap = GenericAccessorTools.setupJPAAnnotations();
    ArrayList<AccessorStruct> entityProperties = new ArrayList<AccessorStruct>();
    Map<AccessorStruct, Object> filteredAccessors = new HashMap<AccessorStruct, Object>();
    Object item = (Object) modelClass.newInstance();
    Class itemClass = item.getClass();
    Method[] methods = item.getClass().getDeclaredMethods();
    for (Method m : methods) {
      String rtype = m.getReturnType().getName();
      String accessorCheck = m.getName();
      //if (!rtype.equals("void")) {
	AccessorStruct tempProp = new AccessorStruct(m);
	tempProp.setBeanName(tempProp.getShortName());
	if (tempProp.getType().info().equals("get") | tempProp.getType().info().equals("set")) {
	  try {
	    Field itemField = itemClass.getDeclaredField(tempProp.getBeanName());
	    List<Annotation> annotations = Arrays.asList(itemField.getAnnotations());
	    if (!annotations.isEmpty()) {
	      for (Annotation type : annotations) {
		if (annotationsMap.containsKey(type.annotationType())) {
		  filteredAccessors.put(tempProp, null);
		}
	      }
	    } else{
	      filteredAccessors.put(tempProp, null);
	    }
	  } catch (NoSuchFieldException ex) {
	    Logger.getLogger(GenericAccessorTools.class.getName()).log(Level.WARNING, itemClass.getName(), ex);
	  } catch (SecurityException ex) {
	    Logger.getLogger(GenericAccessorTools.class.getName()).log(Level.WARNING, itemClass.getName(), ex);
	  }
	}
      //}
    }
    entityProperties = new ArrayList<AccessorStruct>(filteredAccessors.keySet());
    return entityProperties;
  }
}
