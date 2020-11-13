package com.huqi.qs.concurrency;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        final TestChild t = new TestChild();

        new Thread(new Runnable() {
            @Override
            public void run() {
                t.doSomething();
            }
        }).start();
        Thread.sleep(100);
        t.doSomethingElse();

        new Thread(new Runnable() {
            @Override
            public void run() {
                TestChild.doSomething002();
            }
        }).start();
        Thread.sleep(100);
        TestChild.doSomethingElse002();
    }

    public synchronized void doSomething() {
        System.out.println("something sleepy!");
        try {
            Thread.sleep(1000);
            System.out.println("woke up!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void doSomething002() {
        System.out.println("something sleepy!");
        try {
            Thread.sleep(1000);
            System.out.println("woke up!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class TestChild extends Test {
        @Override
        public void doSomething() {
            super.doSomething();
        }

        public synchronized void doSomethingElse() {
            System.out.println("something else");
        }

        public static void doSomething002() {
            Test.doSomething002();
        }

        public static synchronized void doSomethingElse002() {
            System.out.println("something else");
        }
    }
}
