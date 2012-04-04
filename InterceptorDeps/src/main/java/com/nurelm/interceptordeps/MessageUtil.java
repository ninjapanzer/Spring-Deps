package com.nurelm.interceptordeps;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Utility functions for dealing with sending UI messages to views from
 * controllers.
 *
 * @author Sam
 */
public class MessageUtil {

  /**
   * 
   */
  final static public String STATUS_MESSAGE_ATTRIBUTE = "statusMessage";

  /**
   * 
   * @param request
   * @param message
   */
  static public void setStatusMesssage(HttpServletRequest request, String message) {
    request.getSession().setAttribute(STATUS_MESSAGE_ATTRIBUTE, message);
  }

  /**
   * 
   * @return
   */
  static public BuildInfo getBuildInfo() {
    BuildInfo info;
    try {
      final ResourceBundle rb = ResourceBundle.getBundle("META-INF.version");
      info = new BuildInfo(rb);
    } catch (MissingResourceException ex) {
      Logger.getLogger(MessageUtil.class.getName()).log(Level.SEVERE, "META-INF/ does not contain version.properties bundle", ex);
      info = new BuildInfo();
    }
    return info;
  }
}