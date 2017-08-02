package com.rewieer.http.server;

import com.rewieer.http.Request;
import com.rewieer.http.RequestFactory;
import com.rewieer.http.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

  class WebServerThread extends Thread {
    @Override
    public void run() {
      try {
        while(true){
          Socket socket = server.accept();
          BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
          BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());
          byte bytes[] = new byte[8];

          String rawRequest = "";

          int readed = 0;
          while(input.available() > 0){
            readed = input.read(bytes);
            for(int i = 0; i < readed; i++){
              rawRequest += (char) bytes[i];
            }
          }

          Request request = RequestFactory.buildFromString(rawRequest);
          String url = request.getUrl();
          if(request.getUrl().equals("/")){
           url = "index";
          }

          Response response = new Response();
          try {
            BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(new File("web/" + url + ".html")));
            String rawBody = "";
            while(fileInput.available() > 0){
              readed = fileInput.read(bytes);
              for(int i = 0; i < readed; i++){
                rawBody += (char) bytes[i];
              }
            }

            response.setBody(rawBody);
          } catch (IOException e){
            e.printStackTrace();
          }

          String rawResponse = response.toString();
          output.write(rawResponse.getBytes());
          output.flush();
          socket.close();
        }
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  WebServerThread thread = new WebServerThread();
  ServerSocket server;
  int port;

  public WebServer(int port){
    this.port = port;
  }

  public void start(){
    try {
      server = new ServerSocket(port);
      thread.start();
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public void stop(){

  }
}
