package com.varun.concurrency.ch02;

import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;

import static com.varun.concurrency.Helper.*;

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
