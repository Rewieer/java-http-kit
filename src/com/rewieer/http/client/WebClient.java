package com.rewieer.http.client;

import com.rewieer.http.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WebClient {

  public void send(Request request){
    try {
      Socket socket = new Socket(request.getUrl(), 80);

      DataOutputStream output;
      DataInputStream input;

      output = new DataOutputStream(socket.getOutputStream());
      input = new DataInputStream(socket.getInputStream());

      output.write(request.toString().getBytes());

      String responseLine;
      while((responseLine = input.readLine()) != null){
        System.out.println(responseLine);
      }

      socket.close();
    } catch (IOException e){
      e.printStackTrace();
    }
  }

}
