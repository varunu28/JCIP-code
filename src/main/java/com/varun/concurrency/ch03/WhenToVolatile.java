package com.varun.concurrency.ch03;

/*
 * Volatile keyword provides visibility but not atomicity. Best use case for volatile is status
 * flag or deciding completion of a program. It should be used only when:
 *
 * 1. Writes to the variable does not depend upon its current value or only a single thread will be
 * updating the value.
 * 2. The variable is not involved with other state variables to describe state or reflect overall
 * state.
 * 3. Locking is not required for any other reason except data being accessed.
 * */
public class WhenToVolatile {

  private volatile boolean awake;

  public void service() {
    while (!awake) {
      System.out.println("I am sleeping");
    }
  }

  public void setAwake(boolean awake) {
    this.awake = awake;
  }
}
