/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.entitytools.exception;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nurelm
 */
public class SingleResultException extends Exception{
  private List<String> messages;

  /**
   *
   * @param messages
   */
  public SingleResultException(List<String> messages) {
    super((messages != null && messages.size() > 0 ? messages.get(0) : null));
    if (messages == null) {
      this.messages = new ArrayList<String>();
    } else {
      this.messages = messages;
    }
  }

  public SingleResultException(Throwable cause) {
    super(cause);
  }

  /**
   *
   * @return
   */
  public List<String> getMessages() {
    return messages;
  }
}
