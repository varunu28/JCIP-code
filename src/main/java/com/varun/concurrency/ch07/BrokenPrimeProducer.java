package com.varun.concurrency.ch07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/*
 * BrokenPrimeProducer is an example of why a flag based cancellation won't be a good idea for all cases. In
 * BrokenPrimeProducer a blocking queue is used. So when we set cancelled to true, it doesn't guarantee the
 * cancellation as BrokenPrimeProducer can be blocked by queue.put() if queue is full and won't have any idea about
 * the cancelled flag being set to true.
 *
 * For such cases thread interruption can be used to cancel tasks by leveraging isInterrupted() method of thread class.
 * */
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
        }
    }

    public void cancel() {
        cancelled = true;
    }
}

class BrokenPrimeProducerDemo {

    void consumePrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new LinkedBlockingDeque<>();
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.start();
        try {
            while (true) {
                System.out.println(primes.take());
            }
        } finally {
            producer.cancel();
        }
    }
}
