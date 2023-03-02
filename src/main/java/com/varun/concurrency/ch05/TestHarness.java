package com.varun.concurrency.ch05;

import java.util.concurrent.CountDownLatch;

/*
 * TestHarness is a program to measure amount of time a task takes when it is run n times concurrently. For this purpose
 * we use two latches namely startGate & endGate. startGate is assigned with a counter of 1 and endGate is assigned with
 * a counter of nThreads.
 *
 * We await each thread until the startThread counts down to zero. This ensures that all threads are started at the same
 * time concurrently.
 *
 * When a thread finishes the task it counts down on the endGate latch. Once all threads have finished, endGate latch
 * will end up with a value of zero and main thread will know this as await() will be satisfied.
 *
 * So using the two latches we ensure that we are able to start all threads at the same time and wait till the last
 * thread finishes the task.
 *
 * A latch is simply a flag(In our case a counter) which prevents any thread from entering while it is closed(Greater
 * than zero in our case). Once it is opened(Counter reaches 0), it allows all threads to enter. Latch once opened
 * cannot be closed again.
 *
 * Another concept known as BARRIER is similar to latch. In a barrier, all threads must come together at the barrier
 * point. So instead of waiting for an event like latch, a barrier waits for all threads.
 *
 * Cyclic barrier are used to follow the above process repeatedly.
 * */
public class TestHarness {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new TestHarness().timeTasks(4, () -> {
            double count = 0;
            for (int i = 0; i < 100; i++) {
                count += Math.sqrt(i);
            }
            System.out.println(count);
        }));
    }

    public long timeTasks(int nThreads, Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        long startTime = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
