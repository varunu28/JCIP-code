package com.varun.concurrency.ch04;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/*
 * In Java any object can serve as a monitor which makes it easier to ensure that only one thread
 * accesses object attributes and each object in Java comes with a monitor which a thread can use to
 * lock or unlock object when it is accessing or updating shared variables.
 * */
@ThreadSafe
public final class SynchronizedCounterUsingJavaMonitorPattern {
    @GuardedBy("this")
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("Counter overflow");
        }
        return ++value;
    }
}
