package com.varun.concurrency.ch07.desktopsearch;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/*
 * In a producer-consumer pattern, another way of termination is POISON_PILL. Poison pill is a
 * message which producer will send when it wants to terminate the process. Consumer will process
 * all the messages before poison pill and then will shut down the process. This ensures a graceful
 * shutdown where all the messages are processed before stopping the application.
 *
 * This pattern can be extended to multiple producer-consumers.
 * */
public class IndexingService {
  private final BlockingQueue<File> queue;
  private final IndexerThread consumer;
  private final CrawlerThread producer;

  public IndexingService() {
    this.queue = new LinkedBlockingDeque<>(10);
    this.consumer = new IndexerThread(queue);
    this.producer = new CrawlerThread(new File("root"), queue);
  }

  public void start() {
    producer.start();
    consumer.start();
  }

  public void stop() {
    producer.interrupt();
  }

  public void awaitTermination() throws InterruptedException {
    consumer.join();
  }
}
