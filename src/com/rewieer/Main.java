package com.rewieer;

import com.rewieer.http.Request;
import com.rewieer.http.client.WebClient;
import com.rewieer.http.server.WebServer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
  public static void testClient(){
    WebClient webClient = new WebClient();
    Request request = new Request("linux-france.org", "GET");
    request.set("Host", "www.linux-france.org");
    webClient.send(request);
  }

  public static void testServer(){
    WebServer server = new WebServer(3232);
    server.start();

    System.out.println("Server started");
  }
  public static void main(String[] args) {
    testServer();
  }
}
