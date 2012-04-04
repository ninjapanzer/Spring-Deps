/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Entity.FormModel;

import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.util.ArrayList;
import java.util.List;

/*
 *
 * Document   : ListModel
 * Created on : Nov 2, 2011, 12:55:15 AM
 * Author     : samuraipanzer
 */
/**
 * ListModel
 * @author samuraipanzer
 */
public class ListModel<Entity extends AbstractEntity> {
  List<Entity> collection;

  public ListModel() {
    this.collection = new ArrayList<Entity>();
  }

  public ListModel(List<Entity> collection) {
    this.collection = collection;
  }

  public List<Entity> getCollection() {
    return collection;
  }

  public void setCollection(List<Entity> collection) {
    this.collection = collection;
  }

  
}
