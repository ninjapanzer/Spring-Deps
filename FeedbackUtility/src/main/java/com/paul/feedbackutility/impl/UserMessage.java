/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.feedbackutility.impl;

import com.paul.feedbackutility.Exception.SessionMessageException;
import com.paul.feedbackutility.struct.MessageObject;
import javax.servlet.http.HttpServletRequest;

/**
 * Static Implementation of a Message Feedback system that can supersede request conversations
 * Display Mechanism is not specified this is just a session controller designed to be called from a spring Controller
 * or any object which has access to the HttpServletRequest object
 * @author Paul Scarrone (NuRelm)
 */
public class UserMessage {

  private static final String MESSAGE_SESSION_NAME = "UserMessage";
  private static MessageObject message = null;

  /**
   * Accessor in case you want to retrieve the name coded into the application to manually manipulate the message object
   * @return
   */
  public static String getMESSAGE_SESSION_NAME() {
    return MESSAGE_SESSION_NAME;
  }

  /**
   * Retrieve the internal Message Object
   * @return
   */
  public static MessageObject getMessage() {
    return message;
  }

  /**
   * Uses internal Constructino of the message object from its component parts
   * @param request
   * @param inner
   * @param title
   */
  public static void setMessage(HttpServletRequest request, String inner, String title) {
    UserMessage.message = new MessageObject(true, inner, title);
    request.getSession().setAttribute(MESSAGE_SESSION_NAME, UserMessage.message);
  }

  /**
   * Allows the message to be set from an external message object as opposed to using internal construction
   * To allow for easier AJAX message object setting from the client side
   * @param request
   * @param message
   */
  public static void setMessage(HttpServletRequest request, MessageObject message) {
    UserMessage.message = message;
    request.getSession().setAttribute(MESSAGE_SESSION_NAME, request);
  }

  /**
   * Does not delete the object but turns off the current active message
   * @param request
   */
  public static void resetMessage(HttpServletRequest request) throws SessionMessageException {
    if (UserMessage.message != null) {
      UserMessage.message.setTest(false);
    }
    MessageObject local = (MessageObject) request.getSession().getAttribute(MESSAGE_SESSION_NAME);
    if (local != null) {
      local.setTest(false);
      request.getSession().setAttribute(MESSAGE_SESSION_NAME, local);
    } else {
      throw new SessionMessageException(MESSAGE_SESSION_NAME + " Was not set in order to be reset");
    }
  }
}
