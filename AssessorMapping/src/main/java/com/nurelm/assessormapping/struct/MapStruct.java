/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nurelm.assessormapping.struct;

import java.util.Collection;

/**
 *
 * @param <T> 
 * @param <U>
 * @author samuraipanzer
 */
public class MapStruct<T, U> {
  T key;
  Collection<U> values;

  /**
   *
   * @return
   */
  public T getKey() {
    return key;
  }

  /**
   *
   * @param key
   */
  public void setKey(T key) {
    this.key = key;
  }

  /**
   *
   * @return
   */
  public Collection<U> getValues() {
    return values;
  }

  /**
   *
   * @param values
   */
  public void setValues(Collection<U> values) {
    this.values = values;
  }
  
}
