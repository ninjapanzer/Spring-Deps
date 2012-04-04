package com.nurelm.interceptordeps;

import java.util.UUID;

/**
 * Manages 3 UUID sequences for Request Signing
 * @author Paul Scarrone(NuRelm)
 */
public class RequestSigning {
  private UUID currentSigning;
  private UUID lastSigning;
  private UUID nextSigning;
  
  private void incrementSigning(){
    if(currentSigning == null){
      currentSigning = UUID.randomUUID();
      nextSigning = UUID.randomUUID();
    }else{
      lastSigning = UUID.fromString(currentSigning.toString());
      currentSigning = UUID.fromString(nextSigning.toString());
      nextSigning = UUID.randomUUID();
    }
  }

  /**
   * 
   * @return
   */
  public UUID getCurrentSigning() {
    return currentSigning;
  }

  /**
   * 
   * @return
   */
  public UUID getLastSigning() {
    return lastSigning;
  }

  /**
   * 
   * @return
   */
  public UUID getNextSigning() {
    return nextSigning;
  }
  
  /**
   * 
   * @return
   */
  public UUID getNextCurrentSigning(){
    incrementSigning();
    return currentSigning;
  }
}
