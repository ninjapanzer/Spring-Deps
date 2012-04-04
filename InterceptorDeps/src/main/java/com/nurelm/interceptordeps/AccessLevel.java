/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.interceptordeps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author nurelm
 */
public interface AccessLevel<E> {

  Collection<E> getAdminStatusCollection();

  Collection<E> getAllStatusCollection();

  Map<Integer, Object> getAllStatusMap();

  ArrayList<E> getAssignables();

  /**
   *
   * @return
   */
  int getCode();

  E getEnumByName(String name);

  /**
   *
   * @return
   */
  String getRealName();

  /**
   *
   * @param status
   * @return
   */
  E getStatusEnum(Integer status);
  
}
