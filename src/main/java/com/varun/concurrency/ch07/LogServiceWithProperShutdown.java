package com.varun.concurrency.ch07;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import net.jcip.annotations.GuardedBy;

/*
 * LogServiceWithProperShutdown improves LogWriter by doing a proper shutdown of both producer and
 * consumer.
 *
 * We have a isShutdown flag that is thread-safe and reservations to track number of logs that are
 * remaining in queue to be processed. The reservations are also updated in a thread-safe manner.
 * */
public class LogServiceWithProperShutdown {
  private final BlockingQueue<String> queue;
  private final LoggerThread loggerThread;
  private final Integer CAPACITY = 3;

  @GuardedBy("this")
  private boolean isShutdown;

  @GuardedBy("this")
  private int reservations;

  public LogServiceWithProperShutdown(PrintWriter writer) {
    this.queue = new LinkedBlockingDeque<>(CAPACITY);
    this.loggerThread = new LoggerThread(writer);
  }

  public void start() {
    loggerThread.start();
  }

  public void stop() {
    synchronized (this) {
      isShutdown = true;
    }
    loggerThread.interrupt();
  }

  private class LoggerThread extends Thread {
    private final PrintWriter writer;

    LoggerThread(PrintWriter writer) {
      this.writer = writer;
    }

    public void run() {
      try {
        while (true) {
          synchronized (this) {
            if (isShutdown && reservations == 0) {
              break;
            }
          }
          String message = queue.take();
          synchronized (this) {
            --reservations;
          }
          writer.println(message);
        }
      } catch (InterruptedException e) {

      } finally {
        writer.close();
      }
    }
  }
}
