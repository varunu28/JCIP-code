package com.varun.concurrency.ch07;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import static java.util.concurrent.TimeUnit.SECONDS;

/*
 * Every task that wants to be cancellable needs to expose some form of cancellation policy. For example
 * PrimeGenerator exposes a cancelled flag that an exterior thread can set to true and trigger the cancellation of
 * prime generation process.
 * */
@ThreadSafe
public class PrimeGenerator implements Runnable {
  @GuardedBy("this")
  private final List<BigInteger> primes = new ArrayList<>();
  private volatile boolean cancelled;

  @Override
  public void run() {
    BigInteger p = BigInteger.ONE;
    while (!cancelled) {
      p = p.nextProbablePrime();
      synchronized (this) {
        primes.add(p);
      }
    }
  }

  public void cancel() {
    cancelled = true;
  }

  public synchronized List<BigInteger> get() {
    return new ArrayList<>(primes);
  }
}

class PrimeGeneratorDemo {

  List<BigInteger> aSecondOfPrimes() throws InterruptedException {
    PrimeGenerator generator = new PrimeGenerator();
    new Thread(generator).start();
    try {
      SECONDS.sleep(1);
    } finally {
      generator.cancel();
    }
    return generator.get();
  }
}
