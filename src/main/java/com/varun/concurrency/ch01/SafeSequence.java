package com.varun.concurrency.ch01;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SafeSequence implements Sequence {

  @GuardedBy("this")
  private int nextValue;

  public static void main(String[] args) {
    SequenceDemo.testSequence(new SafeSequence());
  }

  public synchronized int getNext() {
    return nextValue++;
  }
}
