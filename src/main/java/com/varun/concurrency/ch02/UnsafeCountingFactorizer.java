package com.varun.concurrency.ch02;

import static com.varun.concurrency.ch02.Helper.encodeIntoResponse;
import static com.varun.concurrency.ch02.Helper.extractFromRequest;
import static com.varun.concurrency.ch02.Helper.factor;

import java.math.BigInteger;
import net.jcip.annotations.NotThreadSafe;

/*
 * This method has count variable that stores the state and incrementing count includes a
 * Read-Modify-Write which is not thread-safe
 * */
@NotThreadSafe
public class UnsafeCountingFactorizer {

  private long count;

  public UnsafeCountingFactorizer() {
    this.count = 0;
  }

  public void service(String request, String response) {
    BigInteger i = extractFromRequest(request);
    BigInteger[] factors = factor(i);
    ++count;
    encodeIntoResponse(response, factors);
  }

  public long getCount() {
    return count;
  }
}
