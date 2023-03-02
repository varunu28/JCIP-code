package com.varun.concurrency.ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.varun.concurrency.Helper.handleRequest;

/*
 * Executor is a framework for asynchronous task execution. It decouples task submission from task execution. It is
 * based on producer-consumer pattern. Using an executor we can ensure that we don't face the thread overloading issue
 *  like we were facing in ThreadPerTaskWebServer.
 * */
public class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor executor = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> handleRequest(connection);
            executor.execute(task);
        }
    }
}
