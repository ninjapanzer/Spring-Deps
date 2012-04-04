/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paul.smtphandler;

import com.paul.smtphandler.Config.SMTPConfigStruct;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author samurai
 */
public class SMTPFirstResponder {
  
  final SMTPConfigStruct SMTPConf = new SMTPConfigStruct(ResourceBundle.getBundle("com.paul.smtphandler.Config.SMTPConfig"));

  public SMTPFirstResponder() {
  }
  
  
  public void SendFirstResponder(String Message){
    Properties props = new Properties();
    props.put("mail.smtp.host", SMTPConf.getHost());
    props.put("mail.smtp.port", SMTPConf.getPort());
    Session session = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(SMTPConf.getFirstResponderFrom());
      message.setRecipients(MimeMessage.RecipientType.TO, SMTPConf.getFirstRespondersArray());
      message.setSubject("Support Error First Response");
      message.setText(Message);
      Transport.send(message);
    } catch (Exception ex) {
      Logger.getLogger(SMTPFirstResponder.class.getName()).log(Level.SEVERE, "Messaging First Responders Failed", ex);
    }
  }
  
  public void Send(String[] args) {
    }
}
