package com.varun.concurrency.ch03;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/*
 * Both set & get functions are synchronized that ensures that the thread will always see the most
 * updated value for both the operations.
 * */
@ThreadSafe
public class SynchronizedInteger {

    @GuardedBy("this")
    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }
}
