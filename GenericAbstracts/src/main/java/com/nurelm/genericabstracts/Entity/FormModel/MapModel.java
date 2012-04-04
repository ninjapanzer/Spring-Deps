/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Entity.FormModel;

import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.util.HashMap;
import java.util.Map;

/*
 *
 * Document   : MapModel
 * Created on : Nov 2, 2011, 1:12:56 AM
 * Author     : samuraipanzer
 */
/**
 * MapModel
 * @author Paul Scarrone (NuRelm)
 */
public class MapModel<Entity extends AbstractEntity> {
  Map<Entity, Object> collection;

  public MapModel() {
    this.collection = new HashMap<Entity, Object>();
  }

  public MapModel(Map<Entity, Object> collection) {
    this.collection = collection;
  }
  
  public Map<Entity, Object> getCollection() {
    return collection;
  }

  public void setCollection(Map<Entity, Object> collection) {
    this.collection = collection;
  }


}
