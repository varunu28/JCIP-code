package com.varun.concurrency.ch07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/*
 * BlockingPrimeProducerWithInterrupt solves the problem of BrokenPrimeProducer by replacing cancelled flag with a
 * thread interruption. This is better than a cancelled flag as thread will always check for interruptions.
 * */
public class BlockingPrimeProducerWithInterrupt extends Thread {
  private final BlockingQueue<BigInteger> queue;

  BlockingPrimeProducerWithInterrupt(BlockingQueue<BigInteger> queue) {
    this.queue = queue;
  }

  public void run() {
    try {
      BigInteger p = BigInteger.ONE;
      while (!Thread.currentThread().isInterrupted()) {
        queue.put(p = p.nextProbablePrime());
      }
    } catch (InterruptedException e) {

    }
  }

  public void cancel() {
    interrupt();
  }
}
