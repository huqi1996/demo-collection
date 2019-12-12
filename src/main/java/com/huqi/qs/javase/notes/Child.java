package com.huqi.qs.javase.notes;

public class Child extends Parent {
    public String schoolName;

    public Child() {
        super.name = "Child";
        super.age = 8;
        this.schoolName = "ChildSchool";
    }

    @Override
    public String toString() {
        return super.name + "  " + super.age + "  " + this.schoolName;
    }
}
