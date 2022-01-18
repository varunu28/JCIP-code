package com.varun.concurrency.ch03;

import java.util.HashSet;
import java.util.Set;
import net.jcip.annotations.Immutable;

/*
 * Set data structure that stores the state of class is immutable and there is no way to modify
 * either the set or values that it contains after the instance of ImmutableThreeStooges is created.
 * Also set is not exposed through the instance and only the behavior is exposed through a public
 * method.
 *
 * A good practice is to make all fields final unless there is a necessity for them to be mutated.
 * */
@Immutable
public final class ImmutableThreeStooges {

  private final Set<String> stooges = new HashSet<>();

  public ImmutableThreeStooges() {
    stooges.add("John");
    stooges.add("Jim");
    stooges.add("Jerry");
  }

  public boolean isStooge(String name) {
    return this.stooges.contains(name);
  }
}
