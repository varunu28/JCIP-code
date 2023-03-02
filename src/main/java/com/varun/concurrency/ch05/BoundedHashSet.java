package com.varun.concurrency.ch05;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/*
 * BoundedHashSet is an implementation of Set using a Semaphore. Semaphore keep track of number of activities that can
 * be access a resource or perform a certain action.
 *
 * A semaphore with a bound of one will act as binary semaphore in which either a thread can access or it will wait until
 * another thread releases the semaphore.
 * */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.semaphore = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        this.semaphore.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = this.set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                this.semaphore.release();
            }
        }
    }

    public boolean remove(T o) {
        boolean wasRemoved = this.set.remove(o);
        if (!wasRemoved) {
            this.semaphore.release();
        }
        return wasRemoved;
    }
}
