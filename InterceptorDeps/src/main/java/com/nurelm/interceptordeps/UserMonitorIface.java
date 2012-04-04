/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.interceptordeps;

import com.nurelm.interceptordeps.AuthStruct;
import com.nurelm.interceptordeps.NavigationStruct;
import com.nurelm.interceptordeps.RequestSigning;

/**
 *
 * @author nurelm
 */
public interface UserMonitorIface {

  RequestSigning getAjaxra();

  AuthStruct getAs();

  NavigationStruct getNavData();

  RequestSigning getRa();

  void setAjaxra(RequestSigning ajaxra);

  void setAs(AuthStruct as);

  void setNavData(NavigationStruct NavData);

  void setRa(RequestSigning ra);
  
}
