package com.varun.concurrency.ch07.desktopsearch;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import static com.varun.concurrency.ch07.desktopsearch.Constants.POISON_MESSAGE;

public class CrawlerThread extends Thread {
    public final File root;
    public final BlockingQueue<String> queue;

    CrawlerThread(File root, BlockingQueue queue) {
        this.root = root;
        this.queue = queue;
    }

    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {

        } finally {
            while (true) {
                try {
                    queue.put(POISON_MESSAGE);
                    break;
                } catch (InterruptedException e1) {

                }
            }
        }
    }

    private void crawl(File root) throws InterruptedException {
        System.out.println("Crawling");
    }
}
