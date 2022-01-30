package com.varun.concurrency.ch05.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import net.jcip.annotations.GuardedBy;

/*
 * HashMap used by MemoizerOne is not thread-safe and in order to ensure thread safety, MemoizerOne adds a
 * synchronized keyword. This does make the code thread-safe but impacts the scalability of the code. Now only one
 * thread can call to compute() method at a time. This will result in large number of threads being blocked leading to
 *  impact on scalability.
 * */
public class MemoizerOne<A, V> implements Computable<A, V> {
  @GuardedBy("this")
  private final Map<A, V> cache = new HashMap<>();
  private final Computable<A, V> c;

  public MemoizerOne(Computable<A, V> c) {
    this.c = c;
  }

  @Override
  public synchronized V compute(A arg) throws InterruptedException, ExecutionException {
    V result = this.cache.get(arg);
    if (result == null) {
      result = this.c.compute(arg);
      this.cache.put(arg, result);
    }
    return result;
  }
}
