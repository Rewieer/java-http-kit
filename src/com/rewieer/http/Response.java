package com.rewieer.http;

import java.util.Enumeration;
import java.util.Hashtable;

public class Response {
  private Hashtable<String, String> headers = new Hashtable<>();
  private String version = "1.1";
  private int statusCode = 200;
  private String statusText = "OK";
  private String body = "";

  public Response(){

  }


  public String getHeader(String key){
    return this.headers.get(key);
  }

  public Hashtable<String, String> getHeaders(){
    return this.headers;
  }

  @Override
  public String toString() {
    String data = "HTTP/" + getVersion().toUpperCase() + " " + getStatusCode() + " " + getStatusText() + "\r\n";
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusText() {
    return statusText;
  }

  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
