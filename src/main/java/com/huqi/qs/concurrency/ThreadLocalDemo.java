package com.huqi.qs.concurrency;

public class ThreadLocalDemo {
    private static ThreadLocal<String> name = new ThreadLocal<>();
    private static ThreadLocal<String> address = new ThreadLocal<>();

    public static void main(String[] args) {
        name.set("aaa");
        name.set("bbb");
        name.set("ccc");
        System.out.println(name.get());
        name.remove();
        System.out.println(name.get());

        address.set("aaa");
        System.out.println(address.get());
        address.set(null);
        System.out.println(address.get());
    }
}
