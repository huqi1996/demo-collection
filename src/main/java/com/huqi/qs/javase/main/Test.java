package com.huqi.qs.javase.main;

public interface Test {
    default void printA() {
        System.out.println("a");
    }

    default void printB() {
        System.out.println("bbb");
        printA();
    }
}
