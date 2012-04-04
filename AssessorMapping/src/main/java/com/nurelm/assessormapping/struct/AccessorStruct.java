/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.assessormapping.struct;

import com.nurelm.assessormapping.annotation.FormDisplay;
import com.nurelm.assessormapping.enumerator.FormTriggerTypeEnum;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author nurelm
 */
public class AccessorStruct implements Cloneable, Serializable {

  private FormVisibilityEnum visible = FormVisibilityEnum.DISPLAYED;
  private String altName;
  private FormTriggerTypeEnum triggerType;
  private Integer order;
  private String shortName;
  private String beanName;
  private String fullName;
  private AccessorReturnTypeEnum returnType;
  private Collection<String> paramTypes = new ArrayList<String>();
  private AccessorTypeEnum type;
  private FormDisplay AnnotationProperties;
  private Method m;

  /**
   *
   * @param shortName
   * @param fullName
   * @param returnType
   * @param paramTypes
   */
  public AccessorStruct(String shortName, String fullName, AccessorReturnTypeEnum returnType, Collection<String> paramTypes) {
    this.shortName = shortName;
    this.beanName = shortName.substring(0, 1).toLowerCase().concat(shortName.substring(1, shortName.length()));
    this.fullName = fullName;
    this.returnType = returnType;
    this.paramTypes = paramTypes;
    this.type = AccessorTypeEnum.accessorType(fullName);
  }

  /**
   *
   * @param fullName
   * @param returnType
   * @param paramTypes
   */
  public AccessorStruct(String fullName, AccessorReturnTypeEnum returnType, Collection<String> paramTypes) {
    this.fullName = fullName;
    this.shortName = this.fullName.substring(3, this.fullName.length());
    this.beanName = this.shortName.substring(0, 1).toLowerCase().concat(this.shortName.substring(1, this.shortName.length()));
    this.returnType = returnType;
    this.paramTypes = paramTypes;
    this.type = AccessorTypeEnum.accessorType(fullName);
  }

  /**
   *
   * @param m
   */
  public AccessorStruct(Method m) {
    this.shortName = m.getName().substring(3, m.getName().length());
    this.beanName = this.shortName.substring(0, 1).toLowerCase().concat(this.shortName.substring(1, this.shortName.length()));
    this.fullName = m.getName();
    this.returnType = AccessorReturnTypeEnum.getReturnTypeEnum(m.getReturnType().getName());
    Class[] tempTypes = m.getParameterTypes();
    for (Class item : tempTypes) {
      if (item.getName() != null) {
	this.paramTypes.add(item.getName());
      }
    }
    this.type = AccessorTypeEnum.accessorType(this.fullName);
    this.m = m;
    try {
      if (m.getClass().getDeclaredField(this.beanName).isAnnotationPresent(FormDisplay.class)) {
	useFormAnnotation();
      }
    } catch (NoSuchFieldException ex) {
      //Logger.getLogger(AccessorStruct.class.getName()).log(Level.OFF, null, ex);
    } catch (SecurityException ex) {
      //Logger.getLogger(AccessorStruct.class.getName()).log(Level.OFF, null, ex);
    }
  }

  /**
   * 
   * @param m
   * @param itemField
   */
  public AccessorStruct(Method m, final Field itemField) {
    FormDisplay annotationItems = (FormDisplay) itemField.getAnnotation(FormDisplay.class);
    this.shortName = m.getName().substring(3, m.getName().length());
    this.beanName = this.shortName.substring(0, 1).toLowerCase().concat(this.shortName.substring(1, this.shortName.length()));
    this.fullName = m.getName();
    this.returnType = AccessorReturnTypeEnum.getReturnTypeEnum(m.getReturnType().getName());
    this.altName = annotationItems.altName();
    this.order = annotationItems.order();
    Class[] tempTypes = m.getParameterTypes();
    for (Class item : tempTypes) {
      if (item.getName() != null) {
	this.paramTypes.add(item.getName());
      }
    }
    this.type = annotationItems.type();
    this.order = annotationItems.order();
    this.m = m;
    try {
      if (m.getClass().getDeclaredField(this.beanName).isAnnotationPresent(FormDisplay.class)) {
	useFormAnnotation();
      }
    } catch (NoSuchFieldException ex) {
      //Logger.getLogger(AccessorStruct.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SecurityException ex) {
      //Logger.getLogger(AccessorStruct.class.getName()).log(Level.SEVERE, null, ex);
    }
    if(itemField.isAnnotationPresent(FormDisplay.class)) {
      this.visible = itemField.getAnnotation(FormDisplay.class).visible();
      if (itemField.isAnnotationPresent(FormDisplay.class)) {
	if (!itemField.getAnnotation(FormDisplay.class).altName().equals("")) {
	  this.setShortName(itemField.getAnnotation(FormDisplay.class).altName());
	}
      }
    }else{
      this.visible = FormVisibilityEnum.NOTDISPLAYED;
    }
  }

  private AccessorStruct(AccessorStruct aThis) {
    this.altName = aThis.altName;
    this.beanName = aThis.beanName;
    this.fullName = aThis.fullName;
    this.m = aThis.m;
    this.order = aThis.order;
    this.paramTypes = aThis.paramTypes;
    this.returnType = aThis.returnType;
    this.shortName = aThis.shortName;
    this.type = aThis.type;
    this.visible = aThis.visible;
    this.AnnotationProperties = aThis.AnnotationProperties;
    this.triggerType = aThis.triggerType;
  }

  private void useFormAnnotation() throws NoSuchFieldException {
    Class accessorClass = this.m.getClass();
    Field accessorField = accessorClass.getDeclaredField(this.beanName);
    FormDisplay accessorAnnotation = accessorField.getAnnotation(FormDisplay.class);
    this.altName = accessorAnnotation.altName();
    this.visible = accessorAnnotation.visible();
    this.order = accessorAnnotation.order();
    this.type = accessorAnnotation.type();
    this.triggerType = accessorAnnotation.triggerType();
    this.AnnotationProperties = accessorAnnotation;
  }

  /**
   *
   * @return
   */
  public Method getM() {
    return m;
  }

  /**
   *
   * @param m
   */
  public void setM(Method m) {
    this.m = m;
  }

  /**
   * 
   * @return
   */
  public String getAltName() {
    return altName;
  }

  /**
   *
   * @param altName
   */
  public void setAltName(String altName) {
    this.altName = altName;
  }

  /**
   *
   * @return
   */
  public Integer getOrder() {
    return order;
  }

  /**
   *
   * @param order
   */
  public void setOrder(Integer order) {
    this.order = order;
  }

  /**
   *
   * @return
   */
  public FormVisibilityEnum getVisible() {
    return visible;
  }

  /**
   * 
   * @return
   */
  public boolean getIsDisplayed() {
    return visible.equals(FormVisibilityEnum.DISPLAYED);
  }

  /**
   * 
   * @return
   */
  public boolean getIsHidden() {
    return visible.equals(FormVisibilityEnum.HIDDEN);
  }

  /**
   *
   * @param visible
   */
  public void setVisible(FormVisibilityEnum visible) {
    this.visible = visible;
  }

  /**
   *
   * @return
   */
  public String getBeanName() {
    return beanName;
  }

  /**
   *
   * @param beanName
   */
  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  /**
   *
   * @return
   */
  public AccessorTypeEnum getType() {
    return type;
  }

  /**
   *
   * @param type
   */
  public void setType(AccessorTypeEnum type) {
    this.type = type;
  }

  /**
   *
   * @return
   */
  public String getFullName() {
    return fullName;
  }

  /**
   *
   * @param fullName
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   *
   * @return
   */
  public Collection<String> getParamTypes() {
    return paramTypes;
  }

  /**
   *
   * @param paramTypes
   */
  public void setParamTypes(Collection<String> paramTypes) {
    this.paramTypes = paramTypes;
  }

  /**
   *
   * @return
   */
  public AccessorReturnTypeEnum getReturnType() {
    return returnType;
  }

  /**
   *
   * @param returnType
   */
  public void setReturnType(AccessorReturnTypeEnum returnType) {
    this.returnType = returnType;
  }

  /**
   *
   * @return
   */
  public String getShortName() {
    return shortName;
  }

  /**
   *
   * @param shortName
   */
  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  /**
   * 
   * @return
   */
  public FormTriggerTypeEnum getTriggerType() {
    return triggerType;
  }

  /**
   * 
   * @param triggerType
   */
  public void setTriggerType(FormTriggerTypeEnum triggerType) {
    this.triggerType = triggerType;
  }

  /**
   * 
   * @return
   */
  public FormDisplay getAnnotationProperties() {
    return AnnotationProperties;
  }

  /**
   *
   * @return
   * @throws CloneNotSupportedException
   */
  @Override
  public AccessorStruct clone() throws CloneNotSupportedException {
    return new AccessorStruct(this);
  }

  public Object invokeGetter(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    return this.m.invoke(obj);
  }

  public Object invokeSetter(Object obj, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    return this.m.invoke(obj, value);
  }
}
