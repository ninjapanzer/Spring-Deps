/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.interceptordeps;

import java.util.Collection;

/**
 *
 * @author nurelm
 */
public interface AuthStruct {

  String userName = null;
  String fullName = null;
  String id = null;
  Object data = null;
  //private AuthStruct proxyData;

  public Collection getAuthRole();
  
}
