package com.varun.concurrency.ch04;

import java.util.HashSet;
import java.util.Set;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/*
 * HashSet used in the class is not thread-safe on its own, but it is kept private and is not
 * published outside the scope of the class. The only 2 APIs that allow updating this collection
 * acquire a lock before updating it making the class ThreadSafe.
 * */
@ThreadSafe
public class PersonSet {
  @GuardedBy("this")
  private final Set<Person> personSet = new HashSet<>();

  public synchronized void addPerson(Person p) {
    this.personSet.add(p);
  }

  public synchronized boolean containsPerson(Person p) {
    return this.personSet.contains(p);
  }
}
