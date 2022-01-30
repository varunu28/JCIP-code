package com.varun.concurrency.ch05.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static com.varun.concurrency.Helper.launderThrowable;

/*
 * MemoizerThree improves upon MemoizerTwo by replacing value V with a Future<V>. This reduces the chances of multiple
 *  threads triggering expensive computations in case of a cache miss. Though it does not totally remove its
 * possibility.
 *
 * We use a check-then-act operation using the if block which in worse case can end up allowing more than one thread
 * to trigger the expensive computation.
 *
 * Another issue with this implementation is that if a future gets cancelled due to a failure, we will have the
 * failed future as result stored against the key. All the upcoming threads will get the same failure if the failed
 * future is not removed from cache. This is known as cache pollution.
 * */
public class MemoizerThree<A, V> implements Computable<A, V> {
  private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
  private final Computable<A, V> c;

  public MemoizerThree(Computable<A, V> c) {this.c = c;}

  @Override
  public V compute(A arg) throws InterruptedException, ExecutionException {
    Future<V> future = this.cache.get(arg);
    if (future == null) {
      Callable<V> eval = () -> c.compute(arg);
      FutureTask<V> futureTask = new FutureTask<>(eval);
      future = futureTask;
      this.cache.put(arg, futureTask);
      futureTask.run();
    }
    try {
      return future.get();
    } catch (ExecutionException e) {
      throw launderThrowable(e.getCause());
    }
  }
}
