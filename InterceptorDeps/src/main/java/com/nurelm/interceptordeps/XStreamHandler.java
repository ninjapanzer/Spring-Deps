/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.interceptordeps;

import com.thoughtworks.xstream.XStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class acts as a abstract loader for XStream Processes
 * @param <XMLModel> Class of a defined XML Model
 * @author Paul Scarrone <NuRelm>
 */
public class XStreamHandler<XMLModel> {

  private Class<XMLModel> entityModelClass;
  private XStream xml;

  /**
   * 
   * @param xml
   */
  public void setXml(XStream xml) {
    this.xml = xml;
  }

  /**
   * 
   * @return
   */
  public XStream getXml() {
    return xml;
  }

  /**
   * Constructor for XStream which passes in the class of the Handler
   * @param modelClass 
   */
  public XStreamHandler(Class<XMLModel> modelClass) {
    this.entityModelClass = modelClass;
    this.xml = new XStream();
  }

  /**
   * Takes in the XML String and returns an instance of the object which contains
   * @param Alias The root alias of the XML
   * @param XMLString Raw XML String
   * @return
   */
  public Collection<XMLModel> getXMLObject(String Alias, String XMLString) {
    XStream item = new XStream();
    item.alias(Alias, this.entityModelClass);
    ArrayList<XMLModel> thing = (ArrayList<XMLModel>) item.fromXML(XMLString);
    return thing;
  }
  /**
   * 
   * @param Alias
   * @param XMLString
   * @return
   */
  public XMLModel getXMLSingleObject(String Alias, String XMLString) {
    XStream item = new XStream();
    item.processAnnotations(this.entityModelClass);
    //item.alias(Alias, this.entityModelClass);
    return (XMLModel) item.fromXML(XMLString);
  }

  /**
   * 
   * @param XMLString
   * @return
   */
  public XMLModel getComplexObject(String XMLString){
    if(this.xml == null){
      return null;
    }
    else{
      this.xml.autodetectAnnotations(true);
      this.xml.processAnnotations(this.entityModelClass);
      return (XMLModel) this.xml.fromXML(XMLString);
    }
  }

}
