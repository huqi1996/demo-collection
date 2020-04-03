package com.huqi.qs.javase.main;

import org.apache.lucene.util.StringHelper;
import org.apache.xmlbeans.impl.validator.ValidatorUtil;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huqi
 */
public class MyFairLock extends Thread {

    private ReentrantLock lock = new ReentrantLock(true);

    public void fairLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在持有锁");
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了锁");
        }
    }

    public static void main(String[] args) {
        MyFairLock myFairLock = new MyFairLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            myFairLock.fairLock();
        };
        Thread[] thread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }
}
