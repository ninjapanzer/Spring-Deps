/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Entity.FormModel;

import com.nurelm.genericabstracts.Entity.AbstractEntity;
import java.util.Set;

/**
 *
 * @author nurelm
 */
public interface SetModel<Entity extends AbstractEntity> {

  Set<Entity> getCollection();

  void setCollection(Set<Entity> collection);
  
}
