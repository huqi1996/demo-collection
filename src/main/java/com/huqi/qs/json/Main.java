package com.huqi.qs.json;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author huqi 20190227
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(null));
        System.out.println(JSON.toJSONString(null).length() + "   " + "".length());
        System.out.println(Main.class.getSimpleName());

        try {
            System.out.println(Double.parseDouble(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Double.parseDouble(""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = "1 45 0 9";
        String[] arr = str.split(" ");
        int min = Integer.parseInt(arr[0]);
        int max = Integer.parseInt(arr[0]);
        for (String i : arr) {
            int j = Integer.parseInt(i);
            min = min < j ? min : j;
            max = max > j ? max : j;
        }
        System.out.println(max + " " + min);
        System.out.println(isBlank("   "));
        System.out.println(isBlank(null));
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() + 1000 * 3600 * 24 * 5);
        System.out.println('a' + 0);
        System.out.println('z' + 0);
        System.out.println('1' + 0);
        List<String> a = new ArrayList<>();
        a.add("bb");
        a.add("cc");
        Collections.reverse(a);
        System.out.println(a);
        System.out.println(zeros(1000000));

        new B(6);

    }

    public static int zeros(int n) {
        int counter = 0;
        int a = 5;
        while (a < n) {
            counter = counter + n / a;
            a *= 5;
        }
        return counter;
    }

    public static class A {
        private int f1 = 7;

        public A(int f1) {
            this.f1 = f1;
            initialize();
        }

        protected void initialize() {
            System.out.println(f1);
        }
    }

    public static class B extends A {
        protected int f1 = 3;

        public B(int f1) {
            super(f1);
            this.f1 += f1;
            initialize();
        }

        @Override
        protected void initialize() {
            System.out.println(f1);
        }
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}
