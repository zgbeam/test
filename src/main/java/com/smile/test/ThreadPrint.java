package com.smile.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author smile
 */

public class ThreadPrint {
    static ReentrantLock lock = new ReentrantLock();
    static Condition allowA = lock.newCondition();
    static Condition allowB = lock.newCondition();
    static Condition allowC = lock.newCondition();

    public static void main(String[] args) {
        Thread t1 = new MyThread(allowA, allowB, "A", 0);
        Thread t2 = new MyThread(allowB, allowC, "B", 1);
        Thread t3 = new MyThread(allowC, allowA, "C", 2);

        t3.start();
        t2.start();
        t1.start();
    }

    public static class MyThread extends Thread {
        private static final int cnt = 300;
        private static volatile int i = 0;
        private final Condition curr;
        private final Condition next;
        private final String str;
        private final int idx;

        public MyThread(Condition curr, Condition next, String str, int idx) {
            this.curr = curr;
            this.next = next;
            this.str = str;
            this.idx = idx;
        }

        @Override
        public void run() {
            while (i < cnt) {
                lock.lock();
                try {
                    while (i % 3 != idx) {
                        curr.await();
                    }
                    i++;
                    System.out.println(str);
                    next.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}


