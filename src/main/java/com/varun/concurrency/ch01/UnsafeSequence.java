package com.varun.concurrency.ch01;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence implements Sequence {

    private int value;

    public int getNext() {
        return value++;
    }
}
