package com.varun.concurrency.ch03;

import net.jcip.annotations.NotThreadSafe;

/*
 * In this class there is no correct order in which the code will behave. There are multiple possible
 * outcomes:
 *
 * 1. ReaderThread sees both the updates to number & ready in the order they were made and prints 42.
 * 2. ReaderThread sees the update to ready but not the number and prints 0 i.e. default int value.
 * 3. ReaderThread doesn't see update to ready and continues in the infinite loop.
 * */
@NotThreadSafe
public class NoVisibility {

    private static boolean ready;
    private static int number;

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }
}
