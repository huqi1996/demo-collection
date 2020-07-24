package com.huqi.qs.concurrency;

import java.util.LinkedList;

public class MyBlockingQueueForWaitNotify {

    private int maxSize;
    private LinkedList<Object> storage;

    public MyBlockingQueueForWaitNotify(int size) {
        this.maxSize = size;
        storage = new LinkedList<>();
    }

    public synchronized void put() throws InterruptedException {
        while (storage.size() == maxSize) {
            this.wait();
        }
        storage.add(new Object());
        this.notifyAll();
    }

    public synchronized void take() throws InterruptedException {
        while (storage.size() == 0) {
            this.wait();
        }
        System.out.println(storage.remove());
        this.notifyAll();
    }
}
