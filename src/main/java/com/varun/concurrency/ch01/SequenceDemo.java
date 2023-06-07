package com.varun.concurrency.ch01;

public class SequenceDemo {

    private static void testSequence(Sequence sequence) throws InterruptedException {
        Thread thread1 = new Thread(() -> performFunc(sequence));
        Thread thread2 = new Thread(() -> performFunc(sequence));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    private static void performFunc(Sequence sequence) {
        for (int i = 0; i < 5; i++) {
            System.out.println(sequence.getNext());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        testSequence(new UnsafeSequence());
        testSequence(new SafeSequence());
    }
}
