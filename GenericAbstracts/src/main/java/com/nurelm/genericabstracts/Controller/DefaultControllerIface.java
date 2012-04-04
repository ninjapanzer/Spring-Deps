/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author samurai
 */
public interface DefaultControllerIface {

  @RequestMapping(method = RequestMethod.GET)
  String getFront(Model model);
  
}
