/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.feedbackutility.Controller;

import com.nurelm.securitycontext.util.SecurityContextUtil;
import com.paul.feedbackutility.Exception.SessionMessageException;
import com.paul.feedbackutility.impl.UserMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Generic implementation of the feedback mechanism from cat-qis
 * Format is pre annotated Spring Controller. To implement this controller
 * Add com.paul to the basepath scan for context config
 * <context:component-scan base-package="com.paul" />
 * Otherwise I am sure the bean can be added individually
 * Request Mapping is statically set to /feedback/AJAX
 * @author Paul Scarrone (NuRelm)
 */
@Controller
@RequestMapping(value="/feedback/AJAX")
public class FeedbackAJAXController {
  
  @Autowired(required=false) SecurityContextUtil securityContextUtil;
  
  /**
   * Spring IoC injectable will only function if this class is in the Controller context of you applcation
   * @return ServletRequestAttributes
   */
  protected ServletRequestAttributes getServletRequest() {
    return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
  }
  
  /**
   * Via GET or POST this will reset the Session object which contains the message trigger
   * @param model
   * @return
   */
  @RequestMapping(value="reset")
  public String resetFeedback(Model model){
    securityContextUtil.setupUtilitySecurityContext(model);
    try {
      UserMessage.resetMessage(this.getServletRequest().getRequest());
    } catch (SessionMessageException ex) {
      Logger.getLogger(FeedbackAJAXController.class.getName()).log(Level.INFO, ex.getMessage(), ex);
    }
    return "redirect:/";
  }
  
  /**
   * 
   * @param model
   * @return
   */
  @RequestMapping(value="set", method= RequestMethod.POST)
  public String setFeedback(Model model){
    securityContextUtil.setupUtilitySecurityContext(model);
    return "redirect:/";
  }
}
