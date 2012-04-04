/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.securitycontext.util;

import com.nurelm.interceptordeps.AccessLevel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;

/**
 *
 * @author Paul Scarrone (NuRelm)
 */
public abstract class SecurityContextUtilImpl<E extends AccessLevel> implements SecurityContextUtil<E> {

  private final String AuthLevelName = "AccessLevels";

  public SecurityContextUtilImpl() {
  }
  
  /**
   * Legacy Context generation tool which will create the context EnumMap for E
   * from a anonymously defined Collection of objects.
   * @param model Current Action Model
   * @param Levels Collection of E enum
   */
  @Override
  public void setupSecurityContext(Model model, Collection<E> Levels) {
  }

  /**
   * By providing E one by one the security context of an action can be
   * controlled without the messy creation of a collection
   * @param model Current Action Model
   * @param level A single E enum
   */
  @Override
  public void setupSecurityContext(Model model, final E level) {
    Map<E, Object> levels;
    if (model.containsAttribute(AuthLevelName)) {
      Map<String, Object> modelMap = model.asMap();
      levels = (HashMap<E, Object>) modelMap.get(AuthLevelName);
      levels.put(level, null);
      modelMap.put(AuthLevelName, levels);
      model.mergeAttributes(modelMap);
    } else {
      levels = new HashMap<E, Object>();
      levels.put(level, null);
      model.addAttribute(AuthLevelName, levels);
    }
  }
  
}
