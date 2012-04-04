/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.assessormapping.struct;

import com.nurelm.assessormapping.encodeableparameters.EncodableParams;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samurai
 */
public class AssessmentParamsStruct extends EncodableParams<AssessmentParamsStruct> {

  /**
   * 
   */
  protected Integer questionId;
  /**
   * 
   */
  protected Integer instanceId;
  /**
   * 
   */
  protected Integer choiceId;
  /**
   * 
   */
  protected String value;

  /**
   * 
   */
  public AssessmentParamsStruct() {
    super();
    super.setStructEntity(this);
  }

  /**
   * 
   * @param queryString
   * @param encoded
   */
  public AssessmentParamsStruct(String queryString, boolean encoded) {
    super(AssessmentParamsStruct.class, queryString, encoded);
    this.processParmeters(this);
  }

  /**
   * 
   * @return
   */
  public Integer getChoiceId() {
    return choiceId;
  }

  /**
   * 
   * @param choiceId
   */
  public void setChoiceId(Integer choiceId) {
    this.choiceId = choiceId;
  }

  /**
   * 
   * @return
   */
  public Integer getInstanceId() {
    return instanceId;
  }

  /**
   * 
   * @param instanceId
   */
  public void setInstanceId(Integer instanceId) {
    this.instanceId = instanceId;
  }

  /**
   * 
   * @return
   */
  public Integer getQuestionId() {
    return questionId;
  }

  /**
   * 
   * @param questionId
   */
  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
  }

  /**
   * 
   * @return
   */
  public String getValue() {
    return value;
  }

  /**
   * 
   * @param value
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * 
   * @return
   */
  public String getEncoded() {
    try {
      return super.getEncodedString(this);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(AssessmentParamsStruct.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "";
  }
}
