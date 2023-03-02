package com.varun.concurrency.ch07;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/*
 * LogWriter is a typical producer-consumer system. Stopping just the logger thread doesn't ensure
 * a graceful shutdown. Such a shutdown can result in discarding the messages in queue. Also, if
 * thread is blocked because queue is full then it will not become unblocked.
 *
 * Cancelling a producer-consumer activity requires cancelling both the producer & consumer.
 * Cancelling the logger thread cancels the consumer but producer doesn't have a dedicated thread,
 * cancelling them becomes complicated.
 * */
public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private final Integer CAPACITY = 3;

    public LogWriter(PrintWriter writer) {
        this.queue = new LinkedBlockingDeque<>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String message) throws InterruptedException {
        queue.put(message);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        public void run() {
            try {
                while (true) {
                    writer.println(queue.take());
                }
            } catch (InterruptedException e) {

            } finally {
                writer.close();
            }
        }
    }
}
