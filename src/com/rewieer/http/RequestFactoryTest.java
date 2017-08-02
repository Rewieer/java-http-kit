package com.rewieer.http;

import static org.junit.Assert.*;

public class RequestFactoryTest {
  @org.junit.Test
  public void buildFromString() throws Exception {
    String raw =
        "GET /index HTTP/1.1\r\n" +
            "Host: www.site.com\r\n" +
            "Content-Type: text/html\r\n"
        ;

    Request request = RequestFactory.buildFromString(raw);

    assertEquals("GET", request.getMethod());
    assertEquals("/index", request.getUrl());
    assertEquals("1.1", request.getVersion());
    assertEquals("www.site.com", request.getHeader("Host"));
    assertEquals("text/html", request.getHeader("Content-Type"));
  }

  @org.junit.Test
  public void buildFromStringPost() throws Exception {
    String raw =
        "POST / HTTP/1.1\r\n" +
            "Host: www.site.com\r\n" +
            "Content-Type: application/json\r\n\r\n" +
            "{\"foo\":\"bar\"}"
        ;

    Request request = RequestFactory.buildFromString(raw);

    assertEquals("POST", request.getMethod());
    assertEquals("/", request.getUrl());
    assertEquals("1.1", request.getVersion());
    assertEquals("www.site.com", request.getHeader("Host"));
    assertEquals("application/json", request.getHeader("Content-Type"));
    assertEquals("{\"foo\":\"bar\"}", request.getBody());
  }

}