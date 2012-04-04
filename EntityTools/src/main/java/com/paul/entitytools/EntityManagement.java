/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools;

import com.nurelm.assessormapping.struct.AccessorStruct;
import com.nurelm.assessormapping.struct.AccessorTypeEnum;
import com.nurelm.genericabstracts.DAO.AbstractDAO;
import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @param <Entity> 
 * @param <DAO> 
 * @author nurelm
 */
public class EntityManagement<Entity extends AbstractEntity, DAO extends AbstractDAO<Entity>> {

  /**
   * 
   */
  protected EntityPropertiesProcessor entityProperties;
  private Entity entityClass;
  private DAO daoClass;
  private DAO currentDAO;
  private ArrayList<AccessorStruct> processedAssessors;

  /**
   * 
   * @param dao
   */
  public EntityManagement(DAO dao) {
    try {
      this.entityClass = dao.getEntityClass().newInstance();
      entityProperties = new EntityPropertiesProcessor(entityClass.getClass());
      this.currentDAO = dao;
    } catch (InstantiationException ex) {
      Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
    }
    processedAssessors = entityProperties.getProcessedAccessors();
  }

  /**
   * 
   * @param Update
   * @param commit
   * @return
   */
  public Entity SmartEntityMerge(Entity Update, Boolean commit) {
    Entity local = Update;
    Entity persisted = (Entity) this.currentDAO.find(Update.getId());
    ArrayList<AccessorStruct> accessors = this.processedAssessors;
    for (AccessorStruct item : accessors) {
      try {
	if (item.getType().equals(AccessorTypeEnum.GET)) {
	  Object localValue = item.getM().invoke(local);
	  Object persistedValue = item.getM().invoke(persisted);
	  if (localValue != null) {
	    if (!localValue.equals(persistedValue)) {
	      AccessorStruct tempItem = this.entityProperties.getSetters().get(item.getBeanName());
	      if (tempItem != null) {
		tempItem.getM().invoke(persisted, localValue);
	      }
	    }
	  }
	}
      } catch (IllegalAccessException ex) {
	Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalArgumentException ex) {
	Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (InvocationTargetException ex) {
	Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
      }
      if (commit) {
	this.currentDAO.edit(persisted);
      }
    }
    return (Entity) persisted;
  }
  
  public Entity SmartEntityMergeNoDAO(Entity Update, Entity Existing) {
    Entity local = Update;
    Entity persisted = Existing;
    ArrayList<AccessorStruct> accessors = this.processedAssessors;
    for (AccessorStruct item : accessors) {
      try {
	if (item.getType().equals(AccessorTypeEnum.GET)) {
	  Object localValue = item.getM().invoke(local);
	  Object persistedValue = item.getM().invoke(persisted);
	  if (localValue != null) {
	    if (!localValue.equals(persistedValue)) {
	      AccessorStruct tempItem = this.entityProperties.getSetters().get(item.getBeanName());
	      if (tempItem != null) {
		tempItem.getM().invoke(persisted, localValue);
	      }
	    }
	  }
	}
      } catch (IllegalAccessException ex) {
	Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalArgumentException ex) {
	Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (InvocationTargetException ex) {
	Logger.getLogger(EntityManagement.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return (Entity) persisted;
  }

  public Boolean isChildDuplicate(Entity parentEntity, AbstractEntity newEntity) {
    List<? extends AbstractEntity> children = (List<? extends AbstractEntity>) parentEntity.getChildren();
    if (children.isEmpty()) {
      return false;
    }
    for (AbstractEntity item : children) {
      if (item.getName().equals(newEntity.getName())) {
	return true;
      }
    }

    return false;
  }
}
