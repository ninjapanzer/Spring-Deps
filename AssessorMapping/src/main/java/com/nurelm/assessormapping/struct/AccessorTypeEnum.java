/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.assessormapping.struct;

/**
 *
 * @author nurelm
 */
public enum AccessorTypeEnum {

  /**
   *
   */
  IS {
    @Override
    public String info() {
      return "is";
    }
  },
  /**
   *
   */
  GET {
    @Override
    public String info() {
      return "get";
    }
  },
  /**
   *
   */
  SET {
    @Override
    public String info() {
      return "set";
    }
  },
  /**
   *
   */
  OBJ,
  /**
   *
   */
  COLLECTION,
  /**
   *
   */
  BAD {
    @Override
    public String info(){
      return "bad";
    }
  },
  /**
   *
   */
  NONE;

  /**
   *
   * @return
   */
  public String info(){
    return "";
  }

  /**
   *
   * @param accessorName
   * @return
   */
  public static AccessorTypeEnum accessorType(String accessorName){
    String twoLetterTest = accessorName.substring(0, 2);
    String threeLetterTest = accessorName.substring(0, 3);
    if(twoLetterTest.equals("is")){
      return IS;
    }
    if(threeLetterTest.equals("get")){
      return GET;
    }
    if(threeLetterTest.equals("set")){
      return SET;
    }
    return BAD;
  }
}
