/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.securitygenerationtools.Exception;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * Document   : InvalidHashException
 * Created on : Oct 29, 2011, 11:33:15 PM
 * Author     : samuraipanzer
 */
/**
 * InvalidHashException
 * @author Paul Scarrone (NuRelm)
 */
public class InvalidHashException extends Exception {

  private List<String> messages;

  /**
   *
   * @param messages
   */
  public InvalidHashException(List<String> messages) {
    super((messages != null && messages.size() > 0 ? messages.get(0) : null));
    if (messages == null) {
      this.messages = new ArrayList<String>();
    } else {
      this.messages = messages;
    }
  }

  public InvalidHashException(Throwable cause) {
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
