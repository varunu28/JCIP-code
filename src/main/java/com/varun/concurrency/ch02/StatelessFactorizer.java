package com.varun.concurrency.ch02;

import static com.varun.concurrency.ch02.Helper.encodeIntoResponse;
import static com.varun.concurrency.ch02.Helper.extractFromRequest;
import static com.varun.concurrency.ch02.Helper.factor;

import java.math.BigInteger;
import net.jcip.annotations.ThreadSafe;

/*
 * This class is thread-safe as the method doesn't access any class specific state
 * */
@ThreadSafe
public class StatelessFactorizer {

  public void service(String request, String response) {
    BigInteger i = extractFromRequest(request);
    BigInteger[] factors = factor(i);
    encodeIntoResponse(response, factors);
  }
}
