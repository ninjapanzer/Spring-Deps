/**
 * AbstractGenericController
 * Generic Version of Deprecated AbstractController
 * @author Paul Scarrone (NuRelm)
 */
package com.nurelm.genericabstracts.Controller;

import com.nurelm.interceptordeps.AuthData;
import com.nurelm.interceptordeps.AuthStruct;
import javax.servlet.ServletContext;
import org.springframework.ui.Model;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Super Controller for non entity based child controllers
 * @author Paul Scarrone (NuRelm)
 */
public class AbstractGenericContoller implements ServletContextAware, AbstractControllerIface {

  /**
   *
   */
  protected String basePath;
  /**
   *
   */
  protected ServletContext servletContext;
  
  protected final String AUTHDATANAME = "AuthData";

  /**
   *
   * @param basepath
   */
  public AbstractGenericContoller(String basepath) {
    this.basePath = basepath;
  }

  /**
   *
   * @param model
   */
  public void persistBasePath(Model model) {
    model.addAttribute("basepath", this.basePath);
  }

  /**
   *
   * @return
   */
  protected ServletRequestAttributes getServletRequest() {
    return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
  }

  /**
   *
   * @param sc
   */
  @Override
  public void setServletContext(ServletContext sc) {
    this.servletContext = sc;
  }

  @Override
  public AuthStruct getAuth() {
    return this.getAuthData().getAuth();
  }

  @Override
  public AuthData getAuthData() {
    return (AuthData) this.getServletRequest().getRequest().getSession().getAttribute(AUTHDATANAME);
  }

  @Override
  public void setAuthData(AuthData<? extends AuthStruct> data) {
    this.getServletRequest().getRequest().getSession().setAttribute(AUTHDATANAME, data);
  }

  @Override
  public void clearAuthData(){
    this.getServletRequest().getRequest().getSession().setAttribute(AUTHDATANAME, null);
  }
  
  @Override
  public String getBasePath() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  private String normalizePathStrings(String str) {
    if (str.length() > 0) {
      if (str.length() > 0 && str.charAt(0) != '/') {
	str = Character.toString('/') + str;
      }
      if (str.length() > 0 && str.charAt(str.length() - 1) == '/') {
	str = str.substring(0, str.length() - 1);
      }
      return str;
    }
    return "";
  }

  private String normalizeBasePath() {
    if (this.basePath.length() > 0) {
      String localbasepath = this.basePath;
      if (localbasepath.length() > 0 && localbasepath.charAt(0) == '/') {
	localbasepath = localbasepath.substring(1, localbasepath.length());
      }
      if (localbasepath.length() > 0 && localbasepath.charAt(localbasepath.length() - 1) == '/') {
	localbasepath = localbasepath.substring(0, localbasepath.length() - 1);
      }
      return localbasepath;
    }
    return "";
  }

  public String getConstructedPath(String str) {
    return this.normalizeBasePath() + this.normalizePathStrings(str);
  }

  public String getConstructedPath() {
    return this.normalizeBasePath();
  }

  public String getRedirectPath() {
    return "redirect:/" + this.getConstructedPath();
  }

  public String getRedirectPath(String str) {
    return "redirect:/" + this.getConstructedPath(str);
  }
}
