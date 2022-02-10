package com.varun.concurrency.ch07.desktopsearch;

import static com.varun.concurrency.ch07.desktopsearch.Constants.POISON_MESSAGE;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class IndexerThread extends Thread {
  private final BlockingQueue<File> queue;

  IndexerThread(BlockingQueue<File> queue) {
    this.queue = queue;
  }

  public void run() {
    try {
      while (true) {
        File file = queue.take();
        if (file.getName().equals(POISON_MESSAGE)) {
          break;
        } else {
          indexFile(file);
        }
      }
    } catch (InterruptedException e) {

    }
  }

  private void indexFile(File file) {
    System.out.println("Indexing");
  }
}
