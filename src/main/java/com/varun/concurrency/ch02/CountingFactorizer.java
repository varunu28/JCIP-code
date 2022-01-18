package com.varun.concurrency.ch02;

import static com.varun.concurrency.Helper.encodeIntoResponse;
import static com.varun.concurrency.Helper.extractFromRequest;
import static com.varun.concurrency.Helper.factor;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;

/*
 * The count variable is atomic that means reading, updating and writing back the value is considered
 * as a single operation instead of three separate operations.
 * */
@ThreadSafe
public class CountingFactorizer {

  private final AtomicLong count = new AtomicLong(0);

  public AtomicLong getCount() {
    return count;
  }

  public void service(String request, String response) {
    BigInteger i = extractFromRequest(request);
    BigInteger[] factors = factor(i);
    count.incrementAndGet();
    encodeIntoResponse(response, factors);
  }
}
