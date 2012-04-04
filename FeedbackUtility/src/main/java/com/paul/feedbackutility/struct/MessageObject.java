/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.feedbackutility.struct;

/**
 *
 * @author samurai
 */
public class MessageObject {
  private boolean test = false;
  private String inner = "";
  private String title = "";

  public MessageObject(boolean test, String inner, String title) {
    this.test = test;
    this.inner = inner;
    this.title = title;
  }
  
  public String getInner() {
    return inner;
  }

  public void setInner(String inner) {
    this.inner = inner;
  }

  public boolean isTest() {
    return test;
  }

  public void setTest(boolean test) {
    this.test = test;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  
}
