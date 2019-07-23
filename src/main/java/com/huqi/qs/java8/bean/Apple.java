package com.huqi.qs.java8.bean;

import java.util.function.Predicate;

/**
 * @author huqi
 */
public class Apple implements Comparable<Apple>, Predicate<Apple> {
    private String color;
    private Double weight;

    public Apple() {
    }

    public Apple(String color, Double weight) {
        this.color = color;
        this.weight = weight;
    }

    @Override
    public int compareTo(Apple o) {
        return this.weight.compareTo(o.weight);
    }

    @Override
    public boolean test(Apple o) {
        return "red".equals(o.getColor());
    }

    @Override
    public String toString() {
        return "{" + color + ", " + weight + "}";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
