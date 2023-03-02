package com.varun.concurrency.ch04;

import net.jcip.annotations.ThreadSafe;

import java.util.Vector;

/*
 * Cases where a thread-safe class fulfils our majority of the requirement, we can extend the class
 * and add the missing functionality to fulfil our requirements.
 *
 * Things to note while choosing an option to extend thread-safe class
 *  - Thread safe class should be open for extension
 *  - Thread safe class should have fixed synchronization mechanism else our implementation will
 *    suffer the side effects if synchronization mechanism changes in the thread-safe class.
 *    Vector has a fixed synchronization policy.
 * */
@ThreadSafe
public class BetterVectorByExtention<E> extends Vector<E> {
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent) {
            add(x);
        }
        return absent;
    }
}
