/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Entity;

import com.nurelm.genericabstracts.DAO.AbstractDAO;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author samuraipanzer
 */
public interface AbstractEntityIface<DAO extends AbstractDAO<Entity>, Entity extends AbstractEntity> extends Cloneable, Serializable {

  /**
   *
   * @param object
   * @return
   */
  boolean equals(Object object);

  /**
   *
   * @return
   */
  ArrayList getChildren();

  /**
   *
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  DAO getDAO() throws InstantiationException, IllegalAccessException;

  /**
   *
   * @return
   */
  Integer getId();

  Integer getAltId();

  /**
   *
   * @return
   */
  String getName();

  /**
   *
   * @return
   */
  Integer getOrder();

  /**
   *
   * @return
   */
  Object getParent();

  /**
   *
   * @return
   */
  Class getParentClass();

  /**
   *
   * @return
   */
  int hashCode();

  /**
   *
   * @param id
   */
  void setId(Integer id);

}
