/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools.Conversion;

import org.springframework.core.convert.converter.Converter;

/*
 *
 * Document   : EntityIDWrappertoString
 * Created on : Nov 15, 2011, 4:21:30 PM
 * Author     : samuraipanzer
 */
/**
 * EntityIDWrappertoString
 * @author samuraipanzer
 */
public class EntityIDWrappertoString implements Converter<EntityIDWrapper, String> {

  public String convert(EntityIDWrapper source) {
    return source.toString();
  }

}
