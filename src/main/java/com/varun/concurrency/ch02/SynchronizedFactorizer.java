package com.varun.concurrency.ch02;

import static com.varun.concurrency.ch02.Helper.encodeIntoResponse;
import static com.varun.concurrency.ch02.Helper.extractFromRequest;
import static com.varun.concurrency.ch02.Helper.factor;

import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.ThreadSafe;

/*
 * Use of synchronized keyword on the service method acquires a lock that can be held by at-most one
 * thread at a time. So now the update on lastNumber and lastFactors happens as a single operation.
 *
 * Although now the service method allows only one thread to run the code under it which will impact
 * the performance even though it is a thread-safe class.
 * */
@ThreadSafe
public class SynchronizedFactorizer {

  private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
  private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

  public synchronized void service(String request, String response) {
    BigInteger i = extractFromRequest(request);
    if (Objects.requireNonNull(i).equals(lastNumber.get())) {
      encodeIntoResponse(response, lastFactors.get());
    } else {
      BigInteger[] factors = factor(i);
      lastNumber.set(i);
      lastFactors.set(factors);
      encodeIntoResponse(response, factors);
    }
  }
}
