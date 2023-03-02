package com.varun.concurrency.ch04;

import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * UnsafeListHelper is not thread-safe for putIfAbsent operation. The synchronized keyword gives an
 * illusion of thread safety, but it doesn't guarantee atomicity of putIfAbsent().
 *
 * The contains() and add() method of Collections.synchronizedList use their own lock for performing
 * operations. Whereas synchronized acquires a lock from UnsafeListHelper class which is not the
 * same lock that synchronizedList uses. So another thread can actually perform add operation just
 * after we call contains() and initialize the absent flag. This can lead to a scenario where x is
 * inserted twice.
 *
 * Check SafeListHelperByClientSideLocking for correct implementation.
 * */
@NotThreadSafe
public class UnsafeListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }
        return absent;
    }
}
