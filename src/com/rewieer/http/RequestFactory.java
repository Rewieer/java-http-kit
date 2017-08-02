package com.rewieer.http;

public class RequestFactory {
  private static void fromRequestLine(String line, Request request){
    String[] split = line.split(" ");
    String version = split[2].split("/")[1];

    request.setMethod(split[0]);
    request.setUrl(split[1]);
    request.setVersion(version);
  }

  private static void fromHeaderLine(String line, Request request){
    String[] split = line.split(":");
    String value = split[1].trim();
    String key = split[0];

    request.set(key, value);
  }

  public static Request buildFromString(String raw){
    Request request = new Request();
    String[] lines = raw.split("\r\n");
    String body = "";

    int lineNumber = 0;
    boolean hasReachedBody = false;
    for(String line : lines){
      lineNumber++;
      if(lineNumber == 1){
        fromRequestLine(line, request);
      } else {
        if(line.equals("")){
          hasReachedBody = true;
        } else {
          if(hasReachedBody){
            body += line;
          } else {
            fromHeaderLine(line, request);
          }
        }
      }
    }

    request.setBody(body);
    return request;
  }
}
