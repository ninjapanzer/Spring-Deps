/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.smtphandler.Enumerator;

import com.nurelm.genericabstracts.Enumerator.EnumIface;

/**
 *
 * @author nurelm
 */
public enum ConfigTypeEnum implements EnumIface<ConfigTypeEnum> {

  LOCAL {

    public int getCode() {
      return 1;
    }

    public String getRealName() {
      return this.name().toLowerCase();
    }
    
  },
  REMOTE {
    
    public int getCode() {
      return 2;
    }

    public String getRealName() {
      return this.name().toLowerCase();
    }
    
    
  },
  NULL{
    
    @Override
    public int getCode() {
      return -1;
    }

    public String getRealName() {
      return this.name().toLowerCase();
    }
    
  };

  @Override
  public ConfigTypeEnum getEnumByName(String name) {
    for (ConfigTypeEnum enumerator : values()) {
      if (enumerator.name().toLowerCase().equals(name.toLowerCase())) {
        return enumerator;
      }
    }
    return NULL;
  }

  @Override
  public ConfigTypeEnum getStatusEnum(Integer status) {
    if (status == null) {
      return NULL;
    }
    for (ConfigTypeEnum enumerator : values()) {
      if (enumerator.getCode() == status) {
        return enumerator;
      }
    }
    return NULL;
  }
}
