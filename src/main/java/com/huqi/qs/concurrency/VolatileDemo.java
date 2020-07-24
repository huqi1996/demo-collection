package com.huqi.qs.concurrency;

public class VolatileDemo {
    private static boolean flag = false;

    public static void main(String[] args) {
        Runnable runnable001 = () -> {
            flag = true;
        };

        Runnable runnable002 = () -> {
            if (flag) {
                System.out.println("flag = true");
            }
        };

        for (int i = 0; i < 100; i++) {
            new Thread(runnable002).start();
        }
        new Thread(runnable001).start();
        for (int i = 0; i < 10; i++) {
            new Thread(runnable002).start();
        }

    }
}
