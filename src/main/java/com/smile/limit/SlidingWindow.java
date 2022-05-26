package com.smile.limit;

/**
 * @author smile
 */
public class SlidingWindow {
    private final Node[] arr;
    private final int windowTime;
    private final int slotNum;
    private final int slotTime;
    private final int limit;

    public SlidingWindow(int windowTime, int slotNum, int limit) {
        this.windowTime = windowTime;
        this.slotTime = windowTime / slotNum;
        this.slotNum = slotNum;
        this.limit = limit;
        this.arr = new Node[slotNum];
    }

    public synchronized boolean pass() {
        long now = System.currentTimeMillis();
        if (count(now) >= limit) {
            return false;
        }

        add(now);
        return true;
    }

    public synchronized int count(long now) {
        int count = 0;
        long windowStartTime = (now + (slotTime - 1) - windowTime) / slotTime * slotTime;
        for (Node node : arr) {
            if (node != null && node.startTime >= windowStartTime) {
                count += node.value;
            }
        }

        return count;
    }

    public synchronized void add(long now) {
        long startTime = now / slotTime * slotTime;
        int slot = (int) ((now / slotTime) % slotNum);
        Node node = arr[slot];
        if (node == null) {
            node = new Node(startTime);
            arr[slot] = node;
        } else if (node.startTime != startTime) {
            node.startTime = startTime;
            node.value = 0;
        }
        node.value++;
    }


    public static class Node {
        private long startTime;
        private long value;

        public Node(long startTime) {
            this.startTime = startTime;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SlidingWindow limit = new SlidingWindow(3000, 3, 2);
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
        SlidingWindow limit;

        public MyThread(String name, SlidingWindow limit) {
            this.limit = limit;
            this.setName(name);
        }

        @Override
        public void run() {
            System.out.println(getName() + " " + limit.pass());
        }
    }
}
