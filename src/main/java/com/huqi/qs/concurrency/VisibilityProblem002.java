package com.huqi.qs.concurrency;

public class VisibilityProblem002 {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() { //读线程
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) { //主线程
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
