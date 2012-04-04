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
public class UserMonitorImpl implements UserMonitorIface {
  private AuthStruct as;
  private RequestSigning ra;
  private RequestSigning ajaxra;
  private NavigationStruct NavData;


  @Override
  public NavigationStruct getNavData() {
    return NavData;
  }


  @Override
  public void setNavData(NavigationStruct NavData) {
    this.NavData = NavData;
  }

  @Override
  public RequestSigning getAjaxra() {
    return ajaxra;
  }

  @Override
  public void setAjaxra(RequestSigning ajaxra) {
    this.ajaxra = ajaxra;
  }

  @Override
  public AuthStruct getAs() {
    return as;
  }

  @Override
  public void setAs(AuthStruct as) {
    this.as = as;
  }

  @Override
  public RequestSigning getRa() {
    return ra;
  }

  @Override
  public void setRa(RequestSigning ra) {
    this.ra = ra;
  }
  
}
