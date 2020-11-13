package com.huqi.qs.javase.main;

/**
 * 执行顺序是：
 * 父类的静态初始化块
 * 子类的静态初始化块
 * 父类的初始化块
 * 父类的构造函数
 * 子类的初始化块
 * 子类的构造函数
 */
public class Foo {

    //constructor 构造函数
    public Foo() {
        System.out.println("constructor called");
    }

    //static initializer   静态初始化器
    static {
        System.out.println("static initializer called");
    }

    //instance initializer 实例变量初始化器
    {
        System.out.println("instance initializer called");
    }

    public static void main(String[] args) {
        new Foo();
        new Foo();
    }
}
