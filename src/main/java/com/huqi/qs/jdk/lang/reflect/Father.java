package com.huqi.qs.jdk.lang.reflect;

public class Father implements Person {
    private String name;

    public Father() {
        this.name = "default";
    }

    public Father(String name) {
        this.name = name;
    }

    @Override
    public String eat(String food) {
        System.out.println(this.getClass().getName() + "运行结果：" + name + " eat " + food);
        return name + " eat done";
    }
}
