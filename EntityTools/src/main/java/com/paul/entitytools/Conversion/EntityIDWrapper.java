/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools.Conversion;

import com.nurelm.genericabstracts.Entity.AbstractEntity;

/*
 *
 * Document   : EntityIDWrapper
 * Created on : Nov 15, 2011, 1:45:16 PM
 * Author     : samuraipanzer
 */
/**
 * EntityIDWrapper
 * @author samuraipanzer
 */
public class EntityIDWrapper {
  private AbstractEntity entity;
  private Integer id;
  private String name;

  public EntityIDWrapper(AbstractEntity entity) {
    this.entity = entity;
    this.id = entity.getAltId();
    this.name = entity.getName();
  }

  public AbstractEntity getEntity() {
    return entity;
  }

  public void setEntity(AbstractEntity entity) {
    this.entity = entity;
  }

  public Integer getId(){
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "com.nurelm.echoinsight.util.Conversion.EntityIDWrapper[ id=" + id + " ]";
  }

}
