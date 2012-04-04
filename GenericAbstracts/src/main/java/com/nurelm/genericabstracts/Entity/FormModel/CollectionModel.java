/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Entity.FormModel;

import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.util.ArrayList;
import java.util.Collection;

/*
 *
 * Document   : CollectionModel
 * Created on : Nov 2, 2011, 12:48:57 AM
 * Author     : samuraipanzer
 */
/**
 * CollectionModel
 * @author samuraipanzer
 */
public class CollectionModel<Entity extends AbstractEntity> {
  Collection<Entity> collection;

  public CollectionModel() {
    this.collection = new ArrayList<Entity>();
  }

  public CollectionModel(Collection<Entity> collection) {
    this.collection = collection;
  }

  public Collection<Entity> getCollection() {
    return collection;
  }

  public void setCollection(Collection<Entity> collection) {
    this.collection = collection;
  }

}
