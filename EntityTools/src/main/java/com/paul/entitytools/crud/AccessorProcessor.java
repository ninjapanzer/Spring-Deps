/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools.crud;

import com.nurelm.assessormapping.struct.AccessorStruct;
import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author samuraipanzer
 */
public interface AccessorProcessor {

  /**
   *
   * @param clazz
   * @throws Exception
   */
  void extractEntityProperties(Class<? extends AbstractEntity> clazz) throws Exception;

  /**
   *
   * @return
   */
  ArrayList<AccessorStruct> getEntityProperties();

  /**
   *
   * @return
   */
  HashMap<String, AccessorStruct> getGetters();

  /**
   *
   * @return
   */
  ArrayList<AccessorStruct> getProcessedAccessors();

  Map<String, AccessorStruct> getProcessedAccessorsMap();

  /**
   *
   * @return
   */
  HashMap<String, AccessorStruct> getSetters();

  Set<AccessorStruct> getGetterSet();

  Set<AccessorStruct> getSetterSet();
}
