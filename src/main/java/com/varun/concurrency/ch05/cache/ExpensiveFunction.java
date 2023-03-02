package com.varun.concurrency.ch05.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        // Big computation
        return new BigInteger(arg);
    }
}
