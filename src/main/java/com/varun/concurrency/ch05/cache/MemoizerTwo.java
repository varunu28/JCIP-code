package com.varun.concurrency.ch05.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/*
 * MemoizerTwo improves on scalability when compared to MemoizerOne by using a ConcurrentHashMap which is thread-safe
 * data structure. Now it doesn't need to synchronize to compute() method and multiple threads can access it at one
 * time.
 *
 * Although not having a synchronized compute() method poses a problem when multiple thread are unable to find the value
 * associated to a key and all the threads end up doing the expensive computation to calculate the result. This
 * degrades the performance of code and defeats the purpose of having a cache.
 *
 * Optimal scenario in case of a cache miss would be to allow only one thread to compute the result by doing expensive
 *  computation while other threads wait for the computation to finish.
 * */
public class MemoizerTwo<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public MemoizerTwo(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        V result = this.cache.get(arg);
        if (result == null) {
            result = this.c.compute(arg);
            this.cache.put(arg, result);
        }
        return result;
    }
}
