package com.varun.concurrency.ch02;

import static com.varun.concurrency.Helper.encodeIntoResponse;
import static com.varun.concurrency.Helper.extractFromRequest;
import static com.varun.concurrency.Helper.factor;

import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.NotThreadSafe;

/*
 * This class is not thread-safe as though lastNumber & lastFactors are individually thread-safe, but
 * they are related to each other, and they are not updated together as a single operation.
 *
 * Consider T1 & T2 are two threads. T1 calls the service for factors of a number(Say N). After
 * calculating the factors it will set lastNumber to N. After completion of this operation T2 calls
 * the service for factor of N. Note that T1 has not updated value of lastFactors but now the value
 * of lastNumber is N. So the if condition for equality will become true and the service will return
 * lastFactors as a result. The value of lastFactors can be null or value of factors of previous
 * lastNumber which in turn results in computing incorrect result for T2 thread.
 *
 * So to preserve state the related variables should be updated as a single operation.
 * */
@NotThreadSafe
public class UnsafeCachingFactorizer {

  private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
  private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

  public void service(String request, String response) {
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
