package com.huqi.qs.javase.main;

public class TestC implements Test {
    @Override
    public void printA(){
        System.out.println("test A");
        /*var var1 = "111";
        System.out.println(var1.getClass());
        System.out.println(Integer.parseInt(var1) - 100);
        System.out.println("".isBlank());*/
    }

    public static void main(String[] args) {
        new TestC().printB();
    }
}
