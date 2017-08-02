package com.rewieer.http.server;

import com.rewieer.http.Request;
import com.rewieer.http.RequestFactory;
import com.rewieer.http.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class WebServer {
  class WebServerThread extends Thread {
    private Request extractRequest(InputStream stream){
      BufferedInputStream input = new BufferedInputStream(stream);
      byte bytes[] = new byte[8];
      int readed = 0;

      StringBuilder stringBuilder = new StringBuilder();

      try {
        while (input.available() > 0) {
          readed = input.read(bytes);
          for (int i = 0; i < readed; i++) {
            stringBuilder.append((char) bytes[i]);
          }
        }
      } catch (IOException e){
        e.printStackTrace();
      }

      return RequestFactory.buildFromString(stringBuilder.toString());
    }

    private void putResponse(Response response, OutputStream stream){
      BufferedOutputStream output = new BufferedOutputStream(stream);
      try {
        output.write(response.toString().getBytes());
        output.flush();
      } catch (IOException e){
        e.printStackTrace();
      }
    }

    private String getFileContent(String path) throws IOException {
      return Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8).stream().map(Object::toString).collect(Collectors.joining(""));
    }

    @Override
    public void run() {
      try {
        while(true){
          Socket socket = server.accept();
          Request request = extractRequest(socket.getInputStream());
          String url = request.getUrl();
          if(request.getUrl().equals("/")){
           url = "index.html";
          }

          Response response = new Response();
          String fileContent = getFileContent("web/" + url);
          response.setBody(fileContent);

          putResponse(response, socket.getOutputStream());
          socket.close();
        }
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  private WebServerThread thread = new WebServerThread();
  private ServerSocket server;
  private int port;

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

}
