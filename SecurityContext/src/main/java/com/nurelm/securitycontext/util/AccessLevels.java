/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.securitycontext.util;

import com.nurelm.interceptordeps.AccessLevel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Paul Scarrone (NuRelm)
 */
public enum AccessLevels implements AccessLevel {

  
  OPADMIN{

    @Override
    public int getCode() {
      return 0;
    }

    @Override
    public String getRealName() {
      return "Operation Admin";
    }

    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>(){{
        add(ORGADMIN);
        add(OPADMIN);
        add(ORGREPORTADMIN);
        add(ORGUSER);
      }};
    }
    
    
  },
  /**
   * 
   */
  ORGADMIN {

    @Override
    public int getCode() {
      return 1;
    }

    @Override
    public String getRealName() {
      return "Corporate Security Admin";
    }
    
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>(){{
        add(ORGADMIN);
        add(FACILITYADMIN);
        add(FACILITYREPORTADMIN);
        add(ORGUSER);
      }};
    }
  },
  /**
   * 
   */
  FACILITYADMIN {

    @Override
    public int getCode() {
      return 2;
    }

    @Override
    public String getRealName() {
      return "Facility Security Admin";
    }
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>(){{
        add(FACILITYADMIN);
        add(FACILITYREPORTADMIN);
        add(ACCESSOR);
        add(USER);
      }};
    }
  },
  /**
   * 
   */
  USER {

    @Override
    public int getCode() {
      return 3;
    }

    @Override
    public String getRealName() {
      return "User";
    }
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>();
    }
  },
  /**
   * 
   */
  ORGREPORTADMIN{

    @Override
    public int getCode() {
      return 4;
    }

    @Override
    public String getRealName() {
      return "Corporate Report Admin";
    }
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>();
    }
  },
  /**
   * 
   */
  ORGUSER{

    @Override
    public int getCode() {
      return 5;
    }

    @Override
    public String getRealName() {
      return "Corporate Clinical User";
    }
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>();
    }
  },
  /**
   * 
   */
  FACILITYREPORTADMIN{

    @Override
    public int getCode() {
      return 6;
    }

    @Override
    public String getRealName() {
      return "Facility Report Admin";
    }
    
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>();
    }
  },
  /**
   * 
   */
  ACCESSOR{

    @Override
    public int getCode() {
      return 7;
    }

    @Override
    public String getRealName() {
      return "Accessor";
    }
    
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>();
    }
  },
  /**
   * 
   */
  NULL {

    @Override
    public int getCode() {
      return -1;
    }

    @Override
    public String getRealName() {
      return null;
    }
    
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>();
    }
  },
  /**
   * 
   */
  DEVELOPER {

    @Override
    public int getCode() {
      return -2;
    }

    @Override
    public String getRealName() {
      return "Unlimited Developer";
    }
    @Override
    public ArrayList<AccessLevels> getAssignables() {
      return new ArrayList<AccessLevels>(){{addAll(getAllStatusCollection());
      }};
    }
  };

  /**
   * 
   * @param status
   * @return
   */
  @Override
  public AccessLevels getStatusEnum(Integer status) {
    if (status == null) {
      return NULL;
    }
    for (AccessLevels enumerator : values()) {
      if (enumerator.getCode() == status) {
        return enumerator;
      }
    }
    return NULL;
  }

  @Override
  public AccessLevels getEnumByName(String name){
    for (AccessLevels enumerator : values()) {
      if (enumerator.toString() == name) {
        return enumerator;
      }
    }
    return NULL;
  }
  
  @Override
  public Map<Integer, Object>getAllStatusMap(){
    Map<Integer, Object> map = new HashMap<Integer, Object>();
    for (AccessLevels enumerator : values()) {
      map.put(enumerator.getCode(), enumerator);
    }
    return map;
  }
  
  @Override
  public Collection<AccessLevels> getAllStatusCollection(){
    return new ArrayList<AccessLevels>(Arrays.asList(values()));
  }
  
  @Override
  public Collection<AccessLevels> getAdminStatusCollection(){
    return new ArrayList<AccessLevels>(){{
      add(FACILITYADMIN);
      add(FACILITYREPORTADMIN);
      add(OPADMIN);
      add(ORGADMIN);
      add(ORGREPORTADMIN);
    }};
  }
}
