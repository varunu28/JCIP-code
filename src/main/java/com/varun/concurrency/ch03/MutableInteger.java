package com.varun.concurrency.ch03;

import net.jcip.annotations.NotThreadSafe;

/*
 * MutableInteger is not thread-safe as it can very well be the case that thread T1 calls setValue()
 * and updates the value. Now thread T2 calling getValue() might see the stale value due to the program
 * being not synchronized.
 * */
@NotThreadSafe
public class MutableInteger {

  private int value;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
