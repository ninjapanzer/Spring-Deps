/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.assessormapping.struct;

/**
 *
 * @author nurelm
 */
public enum AccessorReturnTypeEnum {

  /**
   *
   */
  INTEGER,
  /**
   *
   */
  STRING,
  /**
   *
   */
  COLLECTION,
  /**
   *
   */
  OBJECT,
  /**
   *
   */
  BOOLEAN,
  /**
   *
   */
  VOID,
  /**
   *
   */
  DATE;

  /**
   *
   * @param type
   * @return
   */
  public static AccessorReturnTypeEnum getReturnTypeEnum(String type) {
    if (type.toLowerCase().contains("boolean")) {
      return BOOLEAN;
    } else if (type.toLowerCase().contains("boolean") || type.toLowerCase().equals("int")) {
      return INTEGER;
    } else if (type.toLowerCase().contains("object")) {
      return OBJECT;
    } else if (type.toLowerCase().contains("date")) {
      return DATE;
    } else if (type.toLowerCase().contains("collection") || type.toLowerCase().contains("list") || type.toLowerCase().contains("vector")) {
      return COLLECTION;
    } else if (type.toLowerCase().contains("string")) {
      return STRING;
    } else if (type.toLowerCase().contains("void")) {
      return VOID;
    } else {
      return OBJECT;
    }
  }
}
