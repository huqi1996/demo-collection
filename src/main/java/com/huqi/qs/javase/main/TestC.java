package com.huqi.qs.javase.main;

public class TestC implements Test {
    @Override
    public void printA(){
        System.out.println("test A");
    }

    public static void main(String[] args) {
        new TestC().printB();
    }
}
