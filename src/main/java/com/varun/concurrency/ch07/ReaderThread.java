package com.varun.concurrency.ch07;

import static com.varun.concurrency.Helper.processBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
 * In order to cncel the thread running ReaderThread, interrupt is overriden to close the underlying
 * socket. It ensures that the thread stops what it is doing whether it is blocked due to reading
 * from the socket or is busy handling an interrupt.
 *
 * This deals with cancelling task that contains non-interruptible blocking.
 * */
public class ReaderThread extends Thread {
  private final Socket socket;
  private final InputStream inputStream;
  private final Integer BUFFER_SIZE = 100;

  ReaderThread(Socket socket) throws IOException {
    this.socket = socket;
    this.inputStream = socket.getInputStream();
  }

  public void interrupt() {
    try {
      socket.close();
    } catch (IOException e) {
    } finally {
      super.interrupt();
    }
  }

  public void run() {
    try {
      byte[] buffer = new byte[BUFFER_SIZE];
      while (true) {
        int count = inputStream.read(buffer);
        if (count < 0) {
          break;
        }
        if (count > 0) {
          processBuffer(buffer, count);
        }
      }
    } catch (IOException e) {

    }
  }
}
