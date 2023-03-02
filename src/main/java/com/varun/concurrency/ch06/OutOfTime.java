package com.varun.concurrency.ch06;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.SECONDS;

/*
 * Here an unchecked exception from ThrowTask cancels the entire timer which in turn cancels all the upcoming executions.
 * ScheduledThreadPoolExecutor handles with ill-behaved tasks and doesn't allow the errors from one task to creep into
 *  the following scheduled tasks.
 *
 * Prefer using ScheduledThreadPoolExecutor over a Timer for scheduled tasks.
 * */
public class OutOfTime {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
