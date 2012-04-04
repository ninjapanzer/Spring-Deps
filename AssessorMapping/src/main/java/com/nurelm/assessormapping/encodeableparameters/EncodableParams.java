/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nurelm.assessormapping.encodeableparameters;

import com.nurelm.assessormapping.struct.AccessorStruct;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @param <Struct> 
 * @author samurai
 */
public abstract class EncodableParams<Struct> {

  String queryString;
  String queryStringDecoded;
  Class<Struct> structClass;
  Struct structEntity;
  ArrayList<InternalParamStruct> Params;
  HashMap<String, String> ParamsMap;

  /**
   * 
   * @param structEntity
   */
  public void setStructEntity(Struct structEntity) {
    this.structEntity = structEntity;
    this.structClass = (Class<Struct>) this.structEntity.getClass();
  }

  /**
   * 
   */
  protected class InternalParamStruct {

    private String name;
    private String value;

    /**
     *
     * @param name
     * @param value
     */
    public InternalParamStruct(String name, String value) {
      this.name = name;
      this.value = value;
    }

    /**
     *
     * @return
     */
    public String getName() {
      return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
      this.name = name;
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
  }

  /**
   * 
   */
  protected EncodableParams() {
  }

  /**
   * 
   * @param entity
   * @param queryString
   * @param encoded
   */
  public EncodableParams(Class entity, String queryString, boolean encoded) {
    try {
      structClass = entity;
      structEntity = structClass.newInstance();
    } catch (InstantiationException ex) {
      Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (encoded) {
      this.queryString = queryString;
      this.decodeQueryString();
    } else {
      this.queryStringDecoded = queryString;
    }
    this.prepareParameters();
  }

  /**
   * 
   * @param obj
   * @return
   * @throws UnsupportedEncodingException
   */
  protected String getEncodedString(Struct obj) throws UnsupportedEncodingException {
    this.encodeObject(obj);
    return Base64.encodeBase64URLSafeString(this.queryStringDecoded.getBytes());
  }

  /**
   * 
   */
  protected final void decodeQueryString() {
    queryStringDecoded = new String(Base64.decodeBase64(queryString));
  }

  /**
   * 
   */
  protected final void prepareParameters() {
    this.Params = new ArrayList<InternalParamStruct>();
    this.ParamsMap = new HashMap<String, String>();
    ArrayList<String> paramPairs = new ArrayList<String>(Arrays.asList(this.queryStringDecoded.split("&")));
    for (String item : paramPairs) {
      if (!item.equals("")) {
        final String[] pair = item.split("=");
        try {
          if (pair.length > 1) {
            this.Params.add(new InternalParamStruct(pair[0], URLDecoder.decode(pair[1], "UTF-8")));
            this.ParamsMap.put(pair[0], URLDecoder.decode(pair[1], "UTF-8"));
          }
        } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  private void encodeObject(Struct obj) {
    this.queryStringDecoded = "";
    ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(structClass.getDeclaredFields()));
    for (Field item : fields) {
      try {
        Object value = item.get(obj);
        this.queryStringDecoded += item.getName() + "=" + value + "&";
      } catch (IllegalArgumentException ex) {
        Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
        Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    if (!this.queryStringDecoded.equals("")) {
      this.queryStringDecoded = this.queryStringDecoded.substring(0, this.queryStringDecoded.length() - 1);
    }
  }

  /**
   * 
   * @param obj
   * @return
   */
  protected Struct processParmeters(Struct obj) {
    ArrayList<Method> methods = new ArrayList<Method>(Arrays.asList(structClass.getDeclaredMethods()));
    for (Method item : methods) {
      AccessorStruct tempProp = new AccessorStruct(item);
      Field local;
      try {
        local = structClass.getDeclaredField(tempProp.getBeanName());
        try {
          String type = local.getType().getName();
          if (type.equals("java.lang.Integer")) {
            local.set(obj, Integer.parseInt((String) this.ParamsMap.get(tempProp.getBeanName())));
          } else if (type.equals("java.util.Date")) {
            local.set(obj, new Date(queryString));
          } else {
            local.set(obj, this.ParamsMap.get(tempProp.getBeanName()));
          }
        } catch (IllegalArgumentException ex) {
          Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
          Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
        }
      } catch (NoSuchFieldException ex) {
        Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SecurityException ex) {
        Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return obj;
  }

  /**
   * 
   */
  public void reset() {
    try {
      structEntity = structClass.newInstance();
    } catch (InstantiationException ex) {
      Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(EncodableParams.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
