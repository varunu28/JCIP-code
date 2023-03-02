package com.varun.concurrency.ch05;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DesktopSearchDemo {

    public static final Integer BOUND = 10;
    public static final Integer N_CONSUMERS = 10;

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingDeque<>(BOUND);
        FileFilter fileFilter = pathname -> true;
        for (File root : roots) {
            new Thread(new FileCrawler(queue, fileFilter, root)).start();
        }
        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }
}
