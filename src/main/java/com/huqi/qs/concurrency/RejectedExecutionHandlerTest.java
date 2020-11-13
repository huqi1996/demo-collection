package com.huqi.qs.concurrency;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedExecutionHandlerTest implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    }

    public void test(Runnable r, ThreadPoolExecutor executor) {
        ((RejectedExecutionHandler) (runnable, threadPoolExecutor) -> {
            System.out.println("hello world");
        }).rejectedExecution(r, executor);
    }

    public static void main(String[] args) {
        RejectedExecutionHandlerTest test = new RejectedExecutionHandlerTest();
        test.test(null, null);
    }
}
