/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nurelm.assessormapping.annotation;

import com.nurelm.assessormapping.enumerator.FormTriggerTypeEnum;
import com.nurelm.assessormapping.struct.AccessorTypeEnum;
import com.nurelm.assessormapping.struct.FormVisibilityEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author nurelm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormDisplay {

  /**
   *
   * @return
   */
  public FormVisibilityEnum visible() default FormVisibilityEnum.DISPLAYED;
  /**
   *
   * @return
   */
  public String altName() default "";
  /**
   *
   * @return
   */
  public int order() default -1;
  /**
   *
   * @return
   */
  public AccessorTypeEnum type() default AccessorTypeEnum.NONE;

  /**
   * 
   * @return
   */
  public FormTriggerTypeEnum triggerType() default FormTriggerTypeEnum.NONE;

}
