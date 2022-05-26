package com.smile.limit;

/**
 * @author smile
 */
public class TokenBucket {
    private final long interval;
    private final long maxNum;
    private long num;
    private long lastTime;

    public TokenBucket(long interval, long maxNum) {
        this.interval = interval;
        this.maxNum = maxNum;
    }

    public synchronized boolean pass() {
        long now = System.currentTimeMillis();
        long add = (now - lastTime) / interval;
        if (add > 0) {
            num = Math.min(maxNum, num + add);
            lastTime = now;
        }

        if (num >= 1) {
            num--;
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket limit = new TokenBucket(1000, 2);
        MyThread thread1 = new MyThread("thread1", limit);
        MyThread thread2 = new MyThread("thread2", limit);
        MyThread thread3 = new MyThread("thread3", limit);
        MyThread thread4 = new MyThread("thread4", limit);
        MyThread thread5 = new MyThread("thread5", limit);
        MyThread thread6 = new MyThread("thread6", limit);
        MyThread thread7 = new MyThread("thread7", limit);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        Thread.sleep(3000);

        thread5.start();
        thread6.start();
        thread7.start();
    }

    public static class MyThread extends Thread {
        TokenBucket limit;

        public MyThread(String name, TokenBucket limit) {
            this.limit = limit;
            this.setName(name);
        }

        @Override
        public void run() {
            System.out.println(getName() + " " + limit.pass());
        }
    }
}
