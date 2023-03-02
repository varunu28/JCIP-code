package com.varun.concurrency.ch01;

public class SequenceDemo {

    public static void testSequence(Sequence sequence) {
        Thread thread1 = new Thread(() -> performFunc(sequence));
        Thread thread2 = new Thread(() -> performFunc(sequence));
        thread1.start();
        thread2.start();
    }

    public static void performFunc(Sequence sequence) {
        for (int i = 0; i < 5; i++) {
            System.out.println(sequence.getNext());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
