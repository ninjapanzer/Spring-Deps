/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nurelm.interceptordeps;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Paul Scarrone(NuRelm)
 */
public class NavigationStruct {
  String currentPage;
  String lastPage;
  String currentRequestHeaders;
  String jumpBack;
  HttpServletRequest RequestContext;

  /**
   *
   * @param request
   */
  public NavigationStruct(HttpServletRequest request) {
    this.RequestContext = request;
  }

  /**
   *
   * @return
   */
  public String getCurrentPage() {
    return currentPage;
  }

  /**
   * Setting the Current page automatically masses the old current page if there as one to the lastpage field
   * @param currentPage
   */
  public void setCurrentPage(String currentPage) {
    if(this.currentPage != null){
      this.lastPage = this.currentPage;
    }
    this.currentPage = currentPage;
  }

  /**
   * 
   */
  public void clearPages(){
    this.lastPage = null;
    this.currentPage = null;
  }
  
  /**
   *
   * @return
   */
  public String getCurrentRequestHeaders() {
    return currentRequestHeaders;
  }

  /**
   *
   * @param currentRequestHeaders
   */
  public void setCurrentRequestHeaders(String currentRequestHeaders) {
    this.currentRequestHeaders = currentRequestHeaders;
  }

  /**
   * if Object is being maintained when the last page is retrieved the current page is reset
   * @return
   */
  @Deprecated
  public String getLastPage() {
    this.currentPage = null;
    return lastPage;
  }
  
  public String getLastPage(Boolean clear){
    if(clear){
      this.currentPage = null;
    }
    return lastPage;
  }

  /**
   *
   * @return
   */
  public String getJumpBack() {
    return jumpBack;
  }

  /**
   *
   * @param jumpBack
   */
  public void setJumpBack(String jumpBack) {
    this.jumpBack = jumpBack;
  }

  /**
   *
   * @param RequestContext
   */
  public void setRequestContext(HttpServletRequest RequestContext) {
    this.RequestContext = RequestContext;
  }

  /**
   *
   * @param URL
   * @return
   */
  public String getRedirectURL(String URL){
    String URLTemplate = this.RequestContext.getRequestURL().toString().replace(this.RequestContext.getRequestURI(), "");
    if(URL == null){
      URL = this.getLastPage(Boolean.FALSE);
    }
    String intermediateURL = URL.replace(URLTemplate, "").replace(this.RequestContext.getContextPath(), "");
    return intermediateURL.substring(1+"app/".length(),intermediateURL.length());
  }

}
