package com.varun.concurrency.ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.varun.concurrency.Helper.handleRequest;

/*
 * Although SingleThreadedWebServer is a correct implementation of web server, it will perform poorly as it can handle
 *  only one request at a time. While the server is handlingRequest() other requests need to wait until the processing
 *  has finished.
 * */
public class SingleThreadedWebServer {
  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(80);
    while (true) {
      Socket connection = socket.accept();
      handleRequest(connection);
    }
  }
}
