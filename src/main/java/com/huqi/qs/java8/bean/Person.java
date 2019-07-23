package com.huqi.qs.java8.bean;

/**
 * @author huqi
 */
public class Person implements Comparable<Person> {
    private Long userId;
    private String userName;
    private Byte country;
    private Long salary;
    private Boolean married;

    public Person() {
    }

    public Person(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Person(Long userId, String userName, Byte country) {
        this.userId = userId;
        this.userName = userName;
        this.country = country;
    }

    public Person(Long userId, String userName, Byte country, Long salary, Boolean married) {
        this.userId = userId;
        this.userName = userName;
        this.country = country;
        this.salary = salary;
        this.married = married;
    }

    @Override
    public int compareTo(Person o) {
        return this.userName.compareTo(o.userName);
    }

    @Override
    public String toString() {
        if (Country.fromCode(country) == null) {
            return "{" + userId + ", " + userName + ", " + null + ", " + salary + ", " + married + "}";
        }
        return "{" + userId + ", " + userName + ", " + Country.fromCode(country).getName() + ", " + salary + ", " + married + "}";
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Byte getCountry() {
        return country;
    }

    public void setCountry(Byte country) {
        this.country = country;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }
}
