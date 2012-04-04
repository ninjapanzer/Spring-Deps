/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.smtphandler;

import com.paul.smtphandler.Config.SMTPConfigStruct;
import com.paul.smtphandler.Enumerator.ConfigTypeEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 *
 * Document : SMTPHandler Created on : Jan 29, 2012, 9:14:58 PM Author :
 * samuraipanzer
 */
/**
 * SMTPHandler
 *
 * @author samuraipanzer
 */
public class SMTPHandler {

  private SMTPConfigStruct SMTPConf = null;

  public SMTPHandler() {
  }

  public SMTPHandler(String type, String fromAddress) {
    this.SMTPConf = new SMTPConfigStruct();
    this.SMTPConf.setFirstResponderFromAddress(fromAddress);
    this.SMTPConf.setConfigType(ConfigTypeEnum.LOCAL);
  }

  public SMTPHandler(String type, String host, String port, String fromAddress) {
    if (!type.equals("local")) {
      this.SMTPConf = new SMTPConfigStruct(host, port, fromAddress);
      this.SMTPConf.setConfigType(ConfigTypeEnum.REMOTE);
    } else {
      this.SMTPConf = new SMTPConfigStruct();
      this.SMTPConf.setFirstResponderFromAddress(fromAddress);
      this.SMTPConf.setConfigType(ConfigTypeEnum.LOCAL);
    }
  }

  public SMTPConfigStruct getSMTPConf() {
    return SMTPConf;
  }

  public void setSMTPConf(SMTPConfigStruct SMTPConf) {
    this.SMTPConf = SMTPConf;
  }

  public void SendFirstResponder(String Message, String subject, ArrayList<InternetAddress> recipients) {
    if (SMTPConf != null) {
      Properties props = new Properties();
      if (this.SMTPConf.getConfigType().equals(ConfigTypeEnum.REMOTE)) {
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTPConf.getHost());
        props.put("mail.smtp.port", SMTPConf.getPort());
      }
      Session session = Session.getDefaultInstance(props, null);
      MimeMessage message = new MimeMessage(session);
      InternetAddress[] recps = recipients.toArray(new InternetAddress[recipients.size()]);
      this.Transmit(message, Message, subject, recps);
    }
  }

  public void SendFirstResponder(String Message, String subject, String recipients) {
    if (SMTPConf != null) {
      Properties props = new Properties();
      if (this.SMTPConf.getConfigType().equals(ConfigTypeEnum.REMOTE)) {
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTPConf.getHost());
        props.put("mail.smtp.port", SMTPConf.getPort());
      }
      Session session = Session.getDefaultInstance(props, null);
      MimeMessage message = new MimeMessage(session);
      if (recipients != null) {
        ArrayList<InternetAddress> iRecps = new ArrayList<InternetAddress>();
        if (recipients.contains(",")) {
          ArrayList<String> al = new ArrayList<String>();
          al.addAll(Arrays.asList(recipients.split(",")));
          for (String item : al) {
            try {
              iRecps.add(new InternetAddress(item.trim()));
            } catch (AddressException ex) {
              Logger.getLogger(SMTPHandler.class.getName()).log(Level.SEVERE, "address " + item + " is not an internet address", ex);
            }
          }
        } else if (!recipients.equals("")) {
          try {
            iRecps.add(new InternetAddress(recipients.trim()));
          } catch (AddressException ex) {
            Logger.getLogger(SMTPHandler.class.getName()).log(Level.SEVERE, "address " + recipients.trim() + " is not an internet address", ex);
          }
        }
        if (iRecps.size() > 0) {
          InternetAddress[] recps = iRecps.toArray(new InternetAddress[iRecps.size()]);
          this.Transmit(message, Message, subject, recps);
        }
      }
    }
  }

  private void Transmit(MimeMessage message, String Message, String subject, InternetAddress[] recipients) {
    try {
      message.setFrom(SMTPConf.getFirstResponderFrom());
      message.setRecipients(MimeMessage.RecipientType.TO, recipients);
      message.setSubject(subject);
      message.setText(Message);
      Transport.send(message);
    } catch (Exception ex) {
      Logger.getLogger(SMTPFirstResponder.class.getName()).log(Level.SEVERE, "Messaging First Responders Failed", ex);
    }
  }

  public void Send(String[] args) {
  }
}
