package com.varun.concurrency.ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import static com.varun.concurrency.Helper.handleRequest;

/*
 * When we need to stop an executor, at any given point new requests will be coming in and few requests will be in
 * middle of processing. Hence, for a graceful shutdown we need to stop accepting new requests and wait for pending
 * requests to finish processing. This can be done by checking isShutdown() on executor after a shutdown() is called
 * on it.
 *
 * ExecutorService has three states:
 *  - Running
 *  - Shutting down
 *  - Terminated
 *
 * Once a shutdown() is called, the executor service goes into shutting down state after which it is terminated.
 * */
public class LifecycleWebServer {
    private static final int NTHREADS = 100;
    private static final ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!executor.isShutdown()) {
            try {
                final Socket connection = socket.accept();
                executor.execute(() -> handleRequest(connection));
            } catch (RejectedExecutionException e) {
                if (!executor.isShutdown()) {
                    System.out.println("Task submission rejected " + e);
                }
            }
        }
    }

    public void stop() {
        executor.shutdown();
    }
}
