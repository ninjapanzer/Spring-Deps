/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paul.securitygenerationtools;

import com.paul.gpwpasswords.GPWPasswordGen;

/*
 *
 * Document   : GPWUtil
 * Created on : Oct 29, 2011, 11:09:21 PM
 * Author     : samuraipanzer
 */
/**
 * GPWUtil
 * @author samuraipanzer
 */
public class GPWUtil {

  private static GPWPasswordGen GPW = new GPWPasswordGen();

  public static String genPronounceable(int hashLength, int pronouncableLength){
    return GPW.generate(hashLength, pronouncableLength);
  }

}
