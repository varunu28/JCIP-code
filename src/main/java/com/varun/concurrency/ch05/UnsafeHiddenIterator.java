package com.varun.concurrency.ch05;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;

/*
 * Call to UnsafeHiddenIterator() can result in ConcurrentModificationException as the print statement invokes the
 * toString() method which internally iterates the collection. If another thread adds or removes any element from the
 * set, the iteration will throw an error. This is a side effect of having a hidden iterator.
 *
 * One solution to this can be to acquire the lock before invoking the print statement. But that will make the code
 * difficult to read. A better solution will be to use a synchronized collection that declares the thread-safety
 * as part of initialization.
 * */
@NotThreadSafe
public class UnsafeHiddenIterator {
  @GuardedBy("this")
  private final Set<Integer> set = new HashSet<>();

  public void addTenThings() {
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      add(random.nextInt());
    }
    System.out.println("Added ten elements to " + this.set);
  }

  public synchronized void add(Integer i) {
    set.add(i);
  }

  public synchronized void remove(Integer i) {
    set.remove(i);
  }
}
