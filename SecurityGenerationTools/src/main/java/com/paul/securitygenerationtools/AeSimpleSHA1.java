package com.paul.securitygenerationtools;

import com.paul.securitygenerationtools.Exception.InvalidHashException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author nurelm
 */
public class AeSimpleSHA1 {

  private static String convertToHex(byte[] data) {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      int halfbyte = (data[i] >>> 4) & 0x0F;
      int two_halfs = 0;
      do {
	if ((0 <= halfbyte) && (halfbyte <= 9)) {
	  buf.append((char) ('0' + halfbyte));
	} else {
	  buf.append((char) ('a' + (halfbyte - 10)));
	}
	halfbyte = data[i] & 0x0F;
      } while (two_halfs++ < 1);
    }
    return buf.toString();
  }

  /**
   *
   * @param text
   * @return
   * @throws NoSuchAlgorithmException
   * @throws UnsupportedEncodingException
   */
  public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md;
    md = MessageDigest.getInstance("SHA-1");
    byte[] sha1hash = new byte[40];
    md.update(text.getBytes("iso-8859-1"), 0, text.length());
    sha1hash = md.digest();
    return convertToHex(sha1hash);
  }

  public static String CreateLicenseKey(String seedValue, int length) throws InvalidHashException{
    try {
      String hash = AeSimpleSHA1.SHA1(seedValue);
      return hash.substring(0, length);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(AeSimpleSHA1.class.getName()).log(Level.SEVERE, "SHA-1 Not Viable", ex);
      throw new InvalidHashException(ex);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(AeSimpleSHA1.class.getName()).log(Level.SEVERE, "SHA-1 Not Viable", ex);
      throw new InvalidHashException(ex);
    }
  }
}