/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.securitycontext.requestInterceptor.Config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Map;
/*
<exclusions>
  <exclusion>
    <string>goodbye</string>
    <string>dad</string>
  </exclusion>
  <exclusion>
    <string>hi</string>
    <string>mom</string>
  </exclusion>
</exclusions>
 */

/**
 *
 * @author paul Scarrone(NuRelm)
 */
@XStreamAlias("exclusions")
public class ExclusionsModel {
  
  @XStreamAlias(value="exclusion", impl=java.util.Map.class)
  public Map<String, String> exclusions;

  public Map<String, String> getExclusions() {
    return exclusions;
  }
  
}
