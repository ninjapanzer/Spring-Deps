/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.securitycontext.util;

import com.nurelm.interceptordeps.AccessLevel;
import java.util.Collection;
import java.util.Map;
import org.springframework.ui.Model;

/**
 *
 * @author nurelm
 */
public interface SecurityContextUtil<E extends AccessLevel> {

  /**
   * Special Use Function
   * @param items The EnumMap of E generally from the AuthRole Session Attribute
   * @return E Enum as a String
   */
  String getAccessLevelString(Map<E, Object> items);

  /**
   * Legacy Context generation tool which will create the context EnumMap for E
   * from a anonymously defined Collection of objects.
   * @param model Current Action Model
   * @param Levels Collection of E enum
   */
  void setupSecurityContext(Model model, Collection<E> Levels);

  /**
   * By providing E one by one the security context of an action can be
   * controlled without the messy creation of a collection
   * @param model Current Action Model
   * @param level A single E enum
   */
  void setupSecurityContext(Model model, final E level);

  public void setupUtilitySecurityContext(Model model);
  
}
