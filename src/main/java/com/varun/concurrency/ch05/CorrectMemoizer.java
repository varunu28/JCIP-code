package com.varun.concurrency.ch05;

import com.varun.concurrency.ch05.cache.Computable;

import java.util.concurrent.*;

import static com.varun.concurrency.Helper.launderThrowable;

/*
 * CorrectMemoizer solves the issue of duplicate computation that we saw in MemoizerThree by leveraging putIfAbsent()
 * API for ConcurrentMap. This encapsulates our check-then-act code into an atomic operation.
 *
 * CorrectMemoizer also removes the failed Future from cache if it gets a CancellationException. This prevents any
 * form of cache pollution.
 * */
public class CorrectMemoizer<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public CorrectMemoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        while (true) {
            Future<V> f = this.cache.get(arg);
            if (f == null) {
                Callable<V> eval = () -> c.compute(arg);
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                this.cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
    }
}
