package com.huqi.qs.concurrency;

/**
 * 描述：演示可见性带来的问题
 */
public class VisibilityProblem {

    private volatile int a = 10;
    private int b = 20;

    private void change() {
        a = 30;
        b = a;
    }

    private void print() {
        System.out.println("b=" + b + ";a=" + a);
    }

    public static void main(String[] args) {
        while (true) {
            VisibilityProblem problem = new VisibilityProblem();
            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                problem.change();
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                problem.print();
            }).start();
        }
    }
}
