/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.interceptordeps;

import java.util.ResourceBundle;

/**
 *
 * @author samurai
 */
public class BuildInfo {
  Integer build;
  String date;
  String version;

  /**
   * 
   * @param rb
   */
  public BuildInfo(final ResourceBundle rb) {
    if(rb != null){
      this.version = rb.getString("VERSION");
      this.build = Integer.parseInt(rb.getString("BUILD"));
      this.date = rb.getString("DATE");
    }else{
      this.init();
    }
  }
  
  private void init(){
    this.build = 0;
    this.date = "00-00-0000";
    this.version = "";
  }
  
  public BuildInfo(){
    this.init();
  }
  
  /**
   * 
   * @return
   */
  public Integer getBuild() {
    return build;
  }
  /**
   * 
   * @return
   */
  public String getDate() {
    return date;
  }
  /**
   * 
   * @return
   */
  public String getVersion() {
    return version;
  }
}
