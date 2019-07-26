package com.huqi.qs.javase.main;

import com.huqi.qs.java8.bean.Person;
import com.huqi.qs.javase.util.ListUtil;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author huqi
 */
public class Main {

    public static void main(String[] args) {
//        demo20190212();
//        demo20190325();
//        demo20190523();
        demoLocalDateTime();
    }

    public static void demo20190212() {
        int result = 11;
        while (true) {
            boolean flag = Integer.toBinaryString(result).equals(StringUtils.reverse(Integer.toBinaryString(result)))
                    && Integer.toOctalString(result).equals(StringUtils.reverse(Integer.toOctalString(result)))
                    && Integer.toHexString(result).equals(StringUtils.reverse(Integer.toHexString(result)));
            if (flag) {
                System.out.println(result);
                System.out.println(Integer.toBinaryString(result));
                System.out.println(Integer.toOctalString(result));
                System.out.println(Integer.toHexString(result));
                break;
            }
            result += 2;
        }
        String a = "[\"aaa\",\"bbb\"]";
        System.out.println(a.substring(1, a.length() - 1).split(",")[0]);
        System.out.println("[]".substring(1, 1).split(",").length);
        System.out.println("\"" + "[]".substring(1, 1).split(",")[0] + "\"");
        System.out.println("\"\"");
        System.out.println("".startsWith("\""));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    break;
                }
            }
            System.out.println(i);
        }
    }

    public static void demo20190325() {

        System.out.println("-------------------------------------------------------------");

        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("a");
        list1.add("b");
        list1.add("b");
        list1.add("c");
        List<String> list2 = new ArrayList<>();
        list2.add("a");
        list2.add("b");
        list2.add("c");
        System.out.println(ListUtil.removeAllWithoutRepeat(list1, list2).size());
        System.out.println(ListUtils.removeAll(list1, list2).size());
    }

    public static void demo20190523() {

        List<String> names = new ArrayList<>();
        names.add("aaa");
        names.add("www");
        names.add("bbb");
        System.out.println(names);

        Collections.sort(names);
        System.out.println(names);

        List<Person> people = new ArrayList<>();
        people.add(new Person(0L, "ccc"));
        people.add(new Person(1L, "Ppp"));
        people.add(new Person(2L, "Jjj"));
        System.out.println(people);

        Collections.sort(people);
        System.out.println(people);
    }

    public static void demoLocalDateTime() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println(todayStart);
        System.out.println(todayStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(System.currentTimeMillis());
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println(todayEnd);
        System.out.println(todayEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(todayEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                - todayStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(3600 * 24 * 1000);
        System.out.println(LocalDateTime.of(2100, 1, 1, 1, 1));
        System.out.println(LocalDateTime.of(2100, 1, 1, 1, 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
