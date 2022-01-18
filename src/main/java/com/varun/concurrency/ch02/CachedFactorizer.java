package com.varun.concurrency.ch02;

import static com.varun.concurrency.Helper.encodeIntoResponse;
import static com.varun.concurrency.Helper.extractFromRequest;
import static com.varun.concurrency.Helper.factor;

import java.math.BigInteger;
import java.util.Objects;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/*
 * In CachedFactorizer we use the synchronized to only lock the code that accesses state of code.
 * This will allow multiple threads to call the service and maintain the thread-safety of the
 * program.
 *
 * We also don't need to use atomic variables anymore as we are locking the critical
 * sections of code that update or access the state.
 * */
@ThreadSafe
public class CachedFactorizer {

  @GuardedBy("this")
  private BigInteger lastNumber;

  @GuardedBy("this")
  private BigInteger[] lastFactors;

  @GuardedBy("this")
  private long hits;

  @GuardedBy("this")
  private long cacheHits;

  public synchronized long getHits() {
    return hits;
  }

  public synchronized double getCacheHitRatio() {
    return ((double) cacheHits) / hits;
  }

  public void service(String request, String response) {
    BigInteger i = extractFromRequest(request);
    BigInteger[] factors = null;
    synchronized (this) {
      ++hits;
      if (Objects.requireNonNull(i).equals(lastNumber)) {
        ++cacheHits;
        factors = lastFactors.clone();
      }
    }
    if (factors == null) {
      factors = factor(i);
      synchronized (this) {
        lastNumber = i;
        lastFactors = Objects.requireNonNull(factors).clone();
      }
    }
    encodeIntoResponse(response, factors);
  }
}
