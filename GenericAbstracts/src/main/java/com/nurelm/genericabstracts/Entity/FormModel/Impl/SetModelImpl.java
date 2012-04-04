/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Entity.FormModel.Impl;

import com.nurelm.genericabstracts.Entity.AbstractEntity;
import com.nurelm.genericabstracts.Entity.FormModel.SetModel;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 *
 * Document   : SetModel
 * Created on : Nov 2, 2011, 1:03:21 AM
 * Author     : samuraipanzer
 */
/**
 * SetModel
 * @author Paul Scarrone (NuRelm)
 */
public class SetModelImpl<Entity extends AbstractEntity> implements SetModel<Entity> {

  private Set<Entity> collection;

  public SetModelImpl() {
    this.collection = new LinkedHashSet<Entity>();
  }

  public SetModelImpl(Set<Entity> collection) {
    this.collection = collection;
  }

  @Override
  public Set<Entity> getCollection() {
    return collection;
  }

  @Override
  public void setCollection(Set<Entity> collection) {
    this.collection = collection;
  }

}
