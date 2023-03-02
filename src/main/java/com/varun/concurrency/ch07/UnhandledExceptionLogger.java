package com.varun.concurrency.ch07;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Thread API provides handler to that reports uncaught exceptions UncaughtExceptionHandler. It lets
 * us detect if thread is cancelled due to an uncaught exception such as RuntimeException. We can
 * implement this handler to log the exception message for further debugging.
 *
 * This is recommended approach to deal with uncaught exceptions in order to have clear visibility
 * of failures instead of tasks failing silently.
 * */
public class UnhandledExceptionLogger implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
    }
}
