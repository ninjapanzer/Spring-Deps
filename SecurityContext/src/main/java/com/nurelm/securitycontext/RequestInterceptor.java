/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.securitycontext;

import com.nurelm.interceptordeps.*;
import com.nurelm.securitycontext.requestInterceptor.Config.ExclusionBuilderDefault;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Paul Scarrone
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {

  private Map<String, String> exclu = (Map<String, String>) new ExclusionBuilderDefault().getExclusions().getExclusions();

  /**
   *
   * @param request
   * @param response
   * @param handler
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    /**
     * Manages the Navigation Data which collects the current and previous URLS
     */
    this.prepareNavigation(request);
    /**
     * Manages BuildInfo Object in Session
     */
    this.prepareBuildInfo(request);
    /**
     * Manages the AuthData Object at each request
     */
    String contextPath = request.getContextPath();
    boolean matchExclusion = false;
    String matchedExlusion = "";
    String shortURI = request.getRequestURI().replace(contextPath, "");
    ArrayList<String> URIdisect = new ArrayList<String>(Arrays.asList(request.getRequestURI().split("/")));
    for (String item : URIdisect) {
      if (this.exclu.containsKey(item)) {
        matchExclusion = true;
        matchedExlusion = item;
      }
    }
    if (request.getRequestURI().equals(contextPath + "/app/login")) {
      if (request.getMethod().toLowerCase().equals("get")) {
        request.getSession().setAttribute("AuthData", null);
        request.getSession().setAttribute("UserMonitor", null);
      }
      return true;
    } else {
      if (matchExclusion) {
        //response.sendRedirect(contextPath + this.exclu.get(matchedExlusion));
        Logger.getLogger(RequestInterceptor.class.getName()).log(Level.INFO, matchExclusion + " :Logic Matched for Exclusion");
        return true;
      }
      AuthData AuthData = ((AuthData) request.getSession().getAttribute("AuthData"));
      if (AuthData != null) {
        return true;
      }
      //@TODO make suck less
      //work on later
      //UserMessage.setMessage(request, "Where Are You Going?", "Login First");
      response.sendRedirect(contextPath + "/login");
    }
    return false;
  }

  /**
   * 
   * @param request
   * @param response
   * @param handler
   * @param modelAndView
   * @throws Exception
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    //preparePageContext(modelAndView);
    UserMonitorImpl userMon = (UserMonitorImpl) request.getSession().getAttribute("UserMonitor");
    if (userMon != null) {
      userMon.setNavData((NavigationStruct) request.getSession().getAttribute("NavigationData"));
      userMon.setAs(((AuthData) request.getSession().getAttribute("AuthData")).getAuth());
      request.getSession().setAttribute("UserMonitor", userMon);
    }
    super.postHandle(request, response, handler, modelAndView);
  }

  /*private void preparePageContext(ModelAndView modelAndView) {
  ModelMap modelmap = (ModelMap) modelAndView.getModelMap();
  if (modelmap.containsAttribute(PageContextUtil.getPageContextName())) {
  EnumMap<PageContextOptionEnum, Boolean> map = (EnumMap<PageContextOptionEnum, Boolean>) modelmap.get(PageContextUtil.getPageContextName());
  ArrayList<PageContextOptionEnum> ContextOptions = new ArrayList<PageContextOptionEnum>(Arrays.asList(map.keySet().toArray(new PageContextOptionEnum[map.size()])));
  for (PageContextOptionEnum item : ContextOptions) {
  modelAndView.addObject(item.getName(), item.getTrigger());
  }
  }
  }*/
  private void prepareNavigation(HttpServletRequest request) {
    NavigationStruct NavData = (NavigationStruct) request.getSession().getAttribute("NavigationData");
    if (NavData == null) {
      NavData = new NavigationStruct(request);
    }
    NavData.setRequestContext(request);
    String queryString = request.getQueryString();
    if (queryString == null) {
      queryString = "";
    }
    NavData.setCurrentPage(request.getRequestURL() + "?" + queryString);
    request.getSession().setAttribute("NavigationData", NavData);
  }

  private void prepareBuildInfo(HttpServletRequest request) {
    if (request.getSession().getAttribute("BuildInfo") == null) {
      request.getSession().setAttribute("BuildInfo", (BuildInfo) MessageUtil.getBuildInfo());
    }
  }
}
