/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.springexceptiongeneric;


import com.nurelm.interceptordeps.NavigationStruct;
import com.paul.smtphandler.SMTPFirstResponder;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Paul Scarrone NuRelm
 */
public class ExceptionInterceptor implements HandlerExceptionResolver {
  
  private SMTPFirstResponder smtpHandler = new SMTPFirstResponder();
  
  private String stackTracetoString(Exception ex){
    final Writer result = new StringWriter();
    final PrintWriter printWriter = new PrintWriter(result);
    ex.printStackTrace(printWriter);
    return result.toString();
  }
  
  public NavigationStruct getNavigationContext() {
    return (NavigationStruct) getServletRequest().getRequest().getSession().getAttribute("NavigationData");
  }
  
  protected ServletRequestAttributes getServletRequest() {
    return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
  }
  
  @Override
  public ModelAndView resolveException(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) {
    ModelAndView mv = new ModelAndView("universal/exception");
    mv.addObject("Back", this.getNavigationContext().getRedirectURL(this.getNavigationContext().getCurrentPage()));
    mv.addObject("Exception", excptn);
    try{
    this.smtpHandler.SendFirstResponder(excptn.getMessage() + " " + this.stackTracetoString(excptn));
    }catch(Exception ex){
      Logger.getLogger(ExceptionInterceptor.class.getName()).log(Level.SEVERE, "First Responder Failed to Send", ex);
    }
    Logger.getLogger(ExceptionInterceptor.class.getName()).log(Level.SEVERE, "Errour Occured on page", excptn);
    return mv;
  }
  
}
