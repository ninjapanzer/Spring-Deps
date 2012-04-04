package com.nurelm.genericabstracts.Entity;

import com.nurelm.genericabstracts.DAO.AbstractDAO;
import com.nurelm.genericabstracts.Enumerator.SubmitModeEnum;

/**
 *
 * @author nurelm
 * @param <DAO>
 * @param <Entity>
 */
public abstract class AbstractEntity<DAO extends AbstractDAO<Entity>, Entity extends AbstractEntity> implements AbstractEntityIface<DAO, Entity>  {
  private Class<Entity> entityClass;
  private Class<DAO> entityDAO;
  private Class parentClass;
  private SubmitModeEnum submitMode = SubmitModeEnum.CANCEL;


  /**
   *
   * @param daoClass
   * @param idClass
   * @param parentClass
   */
  public AbstractEntity(Class<DAO> daoClass, Class<Entity> idClass, Class parentClass) {
    this.entityClass = idClass;
    this.entityDAO = daoClass;
    this.parentClass = parentClass;
  }

  /**
   *
   * @param daoClass
   * @param idClass
   */
  public AbstractEntity(Class<DAO> daoClass, Class<Entity> idClass) {
    this.entityClass = idClass;
    this.entityDAO = daoClass;
    this.parentClass = idClass;
  }

  //Copy Constructor
  /**
   *
   * @param entity
   */
  public AbstractEntity(AbstractEntity<DAO, Entity> entity){
    this.entityClass = entity.entityClass;
    this.entityDAO = entity.entityDAO;
    this.parentClass = entity.parentClass;
  }

  /**
   *
   * @param obj
   */
  protected abstract void deepCopy(Entity obj);

  /**
   *
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  @Override
  public DAO getDAO() throws InstantiationException, IllegalAccessException{
    return this.entityDAO.newInstance();
  }

  /**
   *
   * @return
   */
  @Override
  public Class getParentClass(){
    return parentClass;
  };
  

  /**
   *
   * @return
   */
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (getId() != null ? getId().hashCode() : 0);
    return hash;
  }

  /**
   *
   * @param object
   * @return
   */
  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof AbstractEntity)) {
      return false;
    }
    AbstractEntity other = (AbstractEntity) object;
    if ((getId() == null && other.getId() != null) ||
        (getId() != null && !getId().equals(other.getId()))) {
      return false;
    }
    return true;
  }

  /**
   *
   * @return
   */
  @Override
  public Integer getOrder(){
    throw new UnsupportedOperationException("To Use Override");
  }
}