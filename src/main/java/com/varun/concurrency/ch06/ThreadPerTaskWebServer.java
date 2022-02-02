package com.varun.concurrency.ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.varun.concurrency.Helper.handleRequest;

/*
 * ThreadPerTaskWebServer moves the responsibility of processing request to a separate thread. This frees the main
 * thread to receive more requests and hence doesn't block the remaining requests.
 *
 * As a new thread is created to handle each request:
 *  - Main thread can accept new connections
 *  - Task can be processed in parallel so multiple requests can be handled simultaneously
 *  - Task handling code should be either stateless or thread-safe as multiple threads are invoking it concurrently
 *
 * But creating a new thread for each task comes with its overheads:
 *  - Task creation and shutdown are not free and consume computation resources. This can result in degrading
 * overall performance of application as the CPU is doing more work for thread maintenance
 *  - Threads consume memory. When number of threads outnumber number of available processor then the threads sit idle
 * . This results in memory being blocked by threads that are doing nothing.
 *  - Number of threads are limited based on the platform on which application is deployed. Once this limit is
 * reached, we will see an OutOfMemoryError which is difficult to handle.
 * */
public class ThreadPerTaskWebServer {
  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(80);
    while (true) {
      final Socket connection = socket.accept();
      Runnable task = () -> handleRequest(connection);
      new Thread(task).start();
    }
  }
}
