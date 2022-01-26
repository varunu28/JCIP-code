package com.varun.concurrency.ch04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.ThreadSafe;

/*
 * This is a thread-safe implementation of putIfAbsent() functionality as now we acquire the same
 * lock that is used by list which makes the both contains() and add() operation act as one atomic
 * operation rather than two separate atomic operations.
 *
 * This is also known as ClientSideLocking or ExternalLocking.
 *
 * NOTE: Be careful of using client side locking as now we are introducing locking strategy of an
 * unrelated class in our code which can be error-prone if the class changes locking strategy in the
 * future.
 * */
@ThreadSafe
public class SafeListHelperByClientSideLocking<E> {
  public List<E> list = Collections.synchronizedList(new ArrayList<>());

  public boolean putIfAbsent(E x) {
    synchronized (list) {
      boolean absent = !list.contains(x);
      if (absent) {
        list.add(x);
      }
      return absent;
    }
  }
}
