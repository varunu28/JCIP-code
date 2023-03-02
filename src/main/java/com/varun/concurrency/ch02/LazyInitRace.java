package com.varun.concurrency.ch02;

import net.jcip.annotations.NotThreadSafe;

/*
 * Creating a new instance of ExpensiveObject follows a Check-Then-Act pattern that is not
 * thread-safe.
 * */
@NotThreadSafe
public class LazyInitRace {

    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }
}

class ExpensiveObject {

    public ExpensiveObject() {
        System.out.println("Building ExpensiveObject");
    }
}
