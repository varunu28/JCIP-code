package com.varun.concurrency.ch04;

import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * In NumberRange even though we have delegated the thread safety to AtomicInteger, NumberRange class
 * is still not thread-safe as the two integers are constrained by each other.
 *
 * In both the setter methods we follow check-then-act for confirming the constraint. This pattern
 * is not thread-safe as even though we will get correct state on invoking the get() function,
 * another thread can update that variable after the check.
 *
 * Hence, in this case we cannot ensure that our class is thread-safe due to the fact that both
 * state variables are constrained by each other and can be updated individually. Hence, it does
 * not preserve the constraint.
 *
 * NOTE: If a class is composed of multiple thread safe variables and performs compound actions
 * like setLower() then that class cannot be considered as thread-safe and additional measures like
 * Java monitor need to be added to make it thread-safe.
 * */
@NotThreadSafe
public class NumberRange {

    // IMPORTANT lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        if (i > upper.get()) {
            throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
        }
        lower.set(i);
    }

    public void setUpper(int i) {
        if (i < lower.get()) {
            throw new IllegalArgumentException("Can't set upper to " + i + " < lower");
        }
        upper.set(i);
    }

    public boolean isInRange(int i) {
        return i >= lower.get() && i <= upper.get();
    }
}
