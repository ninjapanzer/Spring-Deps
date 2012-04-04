/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Entity.FormModel;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author nurelm
 */
public class SetStringModel {
  private Set<Integer> collection;

  public SetStringModel() {
    this.collection = new LinkedHashSet<Integer>();
  }

  public SetStringModel(Set<Integer> collection) {
    this.collection = collection;
  }

  public Set<Integer> getCollection() {
    return collection;
  }

  public void setCollection(Set<Integer> collection) {
    this.collection = collection;
  }
  
}
