/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.smtphandler.Config;

import com.paul.smtphandler.Enumerator.ConfigTypeEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author samurai
 */
public class SMTPConfigStruct {
  private ConfigTypeEnum configType;
  private String host;
  private String port;
  private String firstResponderFromAddress;
  private String adminAddress;
  private ArrayList<String> firstRespondersTo;

  public SMTPConfigStruct(ResourceBundle rb) {
    this.host = rb.getString("mail.smtp.host");
    this.port = rb.getString("mail.smtp.port");
    this.firstResponderFromAddress = rb.getString("supportFrom");
    this.adminAddress = rb.getString("endUserFrom");
    this.firstRespondersTo = new ArrayList<String>(Arrays.asList(rb.getString("firstResponder").replace(" ", "").split(",")));
  }

  public SMTPConfigStruct() {
  }

  public SMTPConfigStruct(String host, String port, String fromAddress) {
    this.host = host;
    this.port = port;
    this.firstResponderFromAddress = fromAddress;
  }

  public void setConfigType(ConfigTypeEnum configType) {
    this.configType = configType;
  }

  public void setFirstResponderFromAddress(String firstResponderFromAddress) {
    this.firstResponderFromAddress = firstResponderFromAddress;
  }

  public ConfigTypeEnum getConfigType() {
    return configType;
  }

  public String getAdminAddress() {
    return adminAddress;
  }

  public String getFirstResponderFromAddress() {
    return firstResponderFromAddress;
  }
  
  public InternetAddress getFirstResponderFrom() throws AddressException{
    return new InternetAddress(this.firstResponderFromAddress);
  }

  public ArrayList<String> getFirstRespondersTo() {
    return firstRespondersTo;
  }
  
  public ArrayList<InternetAddress> getFirstResponders() throws AddressException{
    ArrayList<InternetAddress> addArray = new ArrayList<InternetAddress>();
    for(String item : this.firstRespondersTo){
      addArray.add(new InternetAddress(item));
    }
    return addArray;
  }
  
  public InternetAddress[] getFirstRespondersArray() throws AddressException{
    InternetAddress[] iaddressarray = new InternetAddress[this.firstRespondersTo.size()];
    int i = 0;
    for(String item : this.firstRespondersTo){
      iaddressarray[i++] = new InternetAddress(item);
    }
    return iaddressarray;
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }
  
}