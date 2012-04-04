/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Enumerator;

/**
 *
 * @author nurelm
 */
public interface EnumIface<E> {
  /**
   *
   * @return
   */
  int getCode();
  
  /**
   *
   * @return
   */
  String getRealName();

  public E getEnumByName(String name);
/**
 * public ConfigTypeEnum getEnumByName(String name) {
    for (ConfigTypeEnum enumerator : values()) {
      if (enumerator.name().toLowerCase().equals(name.toLowerCase())) {
        return enumerator;
      }
    }
    return NULL;
  }
 */
  /**
   *
   * @param status
   * @return
   */
  public E getStatusEnum(Integer status);
  /**
   * public ConfigTypeEnum getStatusEnum(Integer status) {
    if (status == null) {
      return NULL;
    }
    for (ConfigTypeEnum enumerator : values()) {
      if (enumerator.getCode() == status) {
        return enumerator;
      }
    }
    return NULL;
  }
   */
}
