package com.varun.concurrency.ch04;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/*
 * This is a thread-safe implementation of Point class. We don't mark the state variables as final
 * but rather use the monitor pattern to acquire lock for any access or modification.
 * */
@ThreadSafe
public class ThreadSafePoint {
  @GuardedBy("this")
  private int x;

  @GuardedBy("this")
  private int y;

  public ThreadSafePoint(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public ThreadSafePoint(int[] a) {
    this(a[0], a[1]);
  }

  public ThreadSafePoint(ThreadSafePoint p) {
    this(p.get());
  }

  public synchronized int[] get() {
    return new int[] {this.x, this.y};
  }

  public synchronized void set(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
