package com.varun.concurrency.ch03;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.varun.concurrency.Helper.getBirthDateFromDb;

/*
 * ThreadLocal is required to build instances per thread for memory efficiency and thread-safety.
 * Issues that are required to be solved:
 * 1. In below scenario, if we provide individual instances of SimpleDateFormat to each invocation
 * of birthDate() method then it is not memory efficient.
 * 2. If we create a global instance that every thread uses then it can lead to data corruption when
 * one of the thread tries to update the instance's value.
 * 3. Adding a lock will slow things down as now only one thread can access the instance in-turn
 * blocking other threads.
 *
 * Hence, a solution is to provide individual instances for each thread that can be used multiple
 * times by each thread as part of thread-pool. This can be done by leveraging ThreadLocal class.
 * */
public class UserServiceWithThreadLocal {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            int id = i;
            threadPool.submit(
                    () -> {
                        String birthDate = new UserServiceWithThreadLocal().birthDate(id);
                        System.out.println(birthDate);
                    });
        }

        Thread.sleep(1000);
    }

    public String birthDate(int userId) {
        Date birthDate = getBirthDateFromDb(userId);
        final SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatter.get();
        return simpleDateFormat.format(birthDate);
    }
}

class ThreadSafeFormatter {

    public static ThreadLocal<SimpleDateFormat> dateFormatter =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    /* First call for each thread to DataFormatter goes to initialValue function where instance is
     * created. Subsequent calls go to get() method where the same instance is reused. This complete
     * code is transformed to single line in Java8+ versions
     * */
    public static ThreadLocal<SimpleDateFormat> dateFormatterPreJava8 =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }

                @Override
                public SimpleDateFormat get() {
                    return super.get();
                }
            };
}
