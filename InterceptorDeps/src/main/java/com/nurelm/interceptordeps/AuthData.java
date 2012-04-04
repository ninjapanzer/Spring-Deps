/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.interceptordeps;



/**
 *
 * @author samuraipanzer
 */
public class AuthData<T extends AuthStruct> {
  T AuthData;
  T ProxyData;
  Boolean Proxying = false;
  
  public AuthStruct getAuth() {
    if(this.Proxying){
      return nullFilter(ProxyData);
    }else{
      return nullFilter(AuthData);
    }
  }

  private AuthStruct nullFilter(AuthStruct item){
    if(item == null){
      return null;
    }else{
      return item;
    }
  }
  
  public AuthStruct getAuthData() {
    return AuthData;
  }

  public void setAuthData(T AuthData) {
    this.AuthData = AuthData;
  }

  public AuthStruct getProxyData() {
    return ProxyData;
  }

  public void setProxyData(T ProxyData) {
    this.ProxyData = ProxyData;
  }

  public Boolean getProxying() {
    return Proxying;
  }

  public void setProxying(Boolean Proxying) {
    this.Proxying = Proxying;
  }
  
  
}
