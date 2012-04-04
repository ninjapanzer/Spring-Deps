/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nurelm.genericabstracts.Enumerator;

/**
 *
 * @author samuraipanzer
 */
public enum SubmitModeEnum {
  /**
   * 
   */
  SUBMITANDCONTINUE{

    @Override
    public String toString() {
      return super.toString();
    }
  },
  /**
   * 
   */
  SUBMIT{

    @Override
    public String toString() {
      return super.toString();
    }
  },
  /**
   * 
   */
  CANCEL{

    @Override
    public String toString() {
      return super.toString();
    }
  },
  /**
   * 
   */
  EDIT{

    @Override
    public String toString() {
      return super.toString();
    }
  };

  /**
   * 
   * @param submitMode
   * @return
   */
  public static SubmitModeEnum getEnum(String submitMode) {
    for(SubmitModeEnum item : SubmitModeEnum.values()){
      if(item.toString().toLowerCase().equals(submitMode.toLowerCase())){
      return item;
      }
    }
    return CANCEL;
  }
}
