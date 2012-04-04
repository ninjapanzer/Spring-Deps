/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.DAO;

import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author samuraipanzer
 */
public interface AbstractDAOIface<T extends AbstractEntity> extends Serializable{

  /**
   *
   */
  void clear();

  /**
   *
   * @return
   */
  int count();

  /**
   *
   * @param entity
   */
  void create(T entity);

  /**
   *
   * @param entity
   */
  void edit(T entity);

  /**
   *
   * @param id
   * @return
   */
  T find(Object id);

  /**
   *
   * @return
   */
  Collection<Object> findAll();

  /**
   *
   * @param range
   * @return
   */
  List<T> findRange(int[] range);

  /**
   *
   */
  void flush();

  /**
   *
   * @param entity
   * @return
   */
  T getCreate(T entity);

  /**
   *
   * @return
   */
  Class<T> getEntityClass();

  /**
   *
   * @return
   */
  EntityManager getEntityManager();

  /**
   *
   * @return
   */
  Object getParentDAO();

  /**
   *
   * @param entity
   */
  void remove(T entity);

  /**
   *
   * @param em
   */
  void setEntityManager(EntityManager em);
  
}
