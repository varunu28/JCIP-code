package com.varun.concurrency.ch03;

import static com.varun.concurrency.Helper.encodeIntoResponse;
import static com.varun.concurrency.Helper.extractFromRequest;
import static com.varun.concurrency.Helper.factor;

import java.math.BigInteger;
import net.jcip.annotations.ThreadSafe;

/*
 * As the cache is marked as volatile, whenever a thread updates the cache field it gets visible to
 * all the other threads due to volatile keyword. OneValueCache is immutable and hence cannot be
 * altered by any of the threads.
 * */
@ThreadSafe
public class VolatileCachedFactorizer {

  private volatile OneValueCache cache = new OneValueCache(null, null);

  public void service(String request, String response) {
    BigInteger i = extractFromRequest(request);
    BigInteger[] factors = cache.getFactors(i);
    if (factors == null) {
      factors = factor(i);
      cache = new OneValueCache(i, factors);
    }
    encodeIntoResponse(response, factors);
  }
}
