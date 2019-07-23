package com.huqi.qs;

import org.junit.*;

/**
 * @author huqi 20190530
 */
public class Main {
    public int add(int i, int j) {
        return i + j;
    }

    @BeforeClass
    public static void initClass() {
        System.out.println("init class");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("after class");
    }

    @Before
    public void initMethod() {
        System.out.println("init method");
    }

    @After
    public void afterMethod() {
        System.out.println("after method");
    }

    @Test
    public void testAdd() {
        Assert.assertEquals(2, add(1, 1));
    }
}
