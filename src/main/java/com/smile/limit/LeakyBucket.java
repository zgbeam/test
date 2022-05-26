package com.smile.limit;

/**
 * @author smile
 */
public class LeakyBucket {
    private final long interval;
    private final long maxSleepNum;
    private long sleepNum;
    private long lastTime;


    public LeakyBucket(long interval, long maxSleepNum) {
        this.interval = interval;
        this.maxSleepNum = maxSleepNum;
    }

    public synchronized long sleep() {
        long now = System.currentTimeMillis();
        if (now - lastTime >= interval && sleepNum <= 0) {
            lastTime = now;
            return 0;
        }

        if (sleepNum >= maxSleepNum) {
            throw new RuntimeException("rate limit error");
        }

        sleepNum++;
        return interval * sleepNum;
    }

    public synchronized void wake() {
        lastTime = System.currentTimeMillis();
        sleepNum--;
    }

    public static void main(String[] args) throws InterruptedException {
        LeakyBucket limit = new LeakyBucket(1000, 3);
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
        thread5.start();
        thread6.start();
        thread7.start();

        Thread.sleep(10000);
        System.out.println(limit.lastTime + " " + limit.sleepNum);
    }

    public static class MyThread extends Thread {
        LeakyBucket limit;

        public MyThread(String name, LeakyBucket limit) {
            this.limit = limit;
            this.setName(name);
        }

        @Override
        public void run() {
            long time = limit.sleep();
            if (time > 0) {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                limit.wake();
            }
            System.out.println(getName());
        }
    }
}
