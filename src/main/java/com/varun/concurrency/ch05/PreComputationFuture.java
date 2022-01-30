package com.varun.concurrency.ch05;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.varun.concurrency.Helper.launderThrowable;

/*
 * FutureTask also acts as a latch. It contains computation implemented by Callable which is a result-bearing
 * variation of Runnable. It can have 3 states:
 *  - Waiting to run
 *  - Running
 *  - Completed
 *
 * FutureTask can be used to start pre-computing long-running computation. It provides a method get() which will either
 * yield result if the computation has finished or will block the calling thread until it finishes computation.
 * */
public class PreComputationFuture {
  private final FutureTask<Integer> futureTask = new FutureTask<>(() -> {
    Integer result = 0;
    for (int i = 0; i < 1000; i++) {
      result += (int) Math.sqrt(i);
    }
    return result;
  });
  private final Thread thread = new Thread(futureTask);

  public void start() {
    thread.start();
  }

  public Integer get() throws InterruptedException {
    try {
      return futureTask.get();
    } catch (ExecutionException e) {
      Throwable cause = e.getCause();
      throw launderThrowable(cause);
    }
  }
}
