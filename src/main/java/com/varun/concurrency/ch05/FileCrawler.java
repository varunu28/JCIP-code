package com.varun.concurrency.ch05;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

public class FileCrawler implements Runnable {
  private final BlockingQueue<File> fileQueue;
  private final FileFilter fileFilter;
  private final File root;

  public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
    this.root = root;
    this.fileQueue = fileQueue;
    this.fileFilter = fileFilter;
  }

  @Override
  public void run() {
    try {
      this.crawl(this.root);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private void crawl(File root) throws InterruptedException {
    File[] files = this.root.listFiles(this.fileFilter);
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          crawl(file);
        } else if (!alreadyIndexed(file)) {
          this.fileQueue.put(file);
        }
      }
    }
  }

  private boolean alreadyIndexed(File file) {
    return false;
  }
}
