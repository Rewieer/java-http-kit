package com.rewieer.http;

import java.util.Enumeration;
import java.util.Hashtable;

public class Request {
  Hashtable<String, String> headers = new Hashtable<>();
  String url = "";
  String method = "";
  String version = "1.1";
  String body = "";

  public Request(){

  }

  public Request(String url, String method){
    this.url = url;
    this.method = method;
  }



  public void set(String name, String value){
    headers.put(name, value);
  }

  public void setHost(String host){
    this.set("Host", host);
  }

  public void setMethod(String method){
    this.method = method;
  }


  public String getMethod() {
    return method;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getHeader(String key){
    return this.headers.get(key);
  }

  public Hashtable<String, String> getHeaders(){
    return this.headers;
  }

  @Override
  public String toString() {
    String data = getMethod().toUpperCase() + " / HTTP/" + getVersion() + "\r\n";
    Enumeration e = getHeaders().keys();
    while(e.hasMoreElements()){
      String key = (String) e.nextElement();
      String value = getHeader(key);
      data += key + ": " + value + "\r\n";
    }

    data += "\r\n";
    data += getBody();
    return data;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
