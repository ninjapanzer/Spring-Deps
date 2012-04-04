/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Controller;

import com.nurelm.genericabstracts.Entity.AbstractEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 *
 * @author samuraipanzer
 */
public interface AbstractEntityControllerIface<T extends AbstractEntity> {
  
  public String AddEntity(T entity, BindingResult result, Model model);
  public String RemoveEntity(T entity, BindingResult result, Model model);
  public String ConfirmRemoveEntity(Integer id, Model model);
  public String EditEntity(T entity, BindingResult result, Model model);
  
}
