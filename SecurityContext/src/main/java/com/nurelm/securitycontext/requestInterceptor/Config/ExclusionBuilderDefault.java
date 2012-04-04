/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.securitycontext.requestInterceptor.Config;

import com.nurelm.interceptordeps.XStreamHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nurelm
 */
public class ExclusionBuilderDefault {

  private XStreamHandler<ExclusionsModel> xStream = new XStreamHandler<ExclusionsModel>(ExclusionsModel.class);
  private ExclusionsModel exclusions = new ExclusionsModel();

  public ExclusionBuilderDefault(){
    try {
      InputStream is = this.getClass().getClassLoader().getResourceAsStream("/META-INF/requestExclusion.xml");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
          String line = "";
          String output = "";
          while ((line = br.readLine()) != null) {
            output = output + line;
          }
        br.close();
        //xStream.getXml().alias("exclusion", java.util.Map.class);
        this.exclusions = xStream.getComplexObject(output);
    } catch (IOException ex) {
      Logger.getLogger(ExclusionBuilderDefault.class.getName()).log(Level.SEVERE, "Read Failed", ex);
    }
  }
  
  public ExclusionsModel getExclusions(){
    return this.exclusions;
  }
}
