/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.genericabstracts.Controller;

import com.nurelm.interceptordeps.AuthData;
import com.nurelm.interceptordeps.AuthStruct;
import org.springframework.ui.Model;

/**
 *
 * @author samuraipanzer
 */
public interface AbstractControllerIface {
  public String getBasePath();
  public AuthData getAuthData();
  public void setAuthData(AuthData<? extends AuthStruct> data);
  public AuthStruct getAuth();
  public void persistBasePath(Model model);
  public void clearAuthData();
}
