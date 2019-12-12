package com.huqi.qs.java8.main;

import org.apache.xmlbeans.impl.validator.ValidatorUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huqi 20190920
 */
public class MainTime {
    public static void main(String[] args) {
        System.out.println(Instant.now().toEpochMilli());

        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Timestamp timestamp = Timestamp.valueOf(start);
        System.out.println(timestamp.toString());
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        timestamp = Timestamp.valueOf(end);
        System.out.println(timestamp.toString());
        System.out.println(Integer.MAX_VALUE + 2L);
        System.out.println(Integer.MAX_VALUE + 2);
        System.out.println(Integer.BYTES + " - " + Integer.SIZE + " - " + Integer.TYPE);
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - startTime);
        List<Long> list = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        System.out.println("List: " + list);
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, 2L);
        map.put(3L, 4L);
        System.out.println("Map: " + map);
        System.out.println("\n".length());
    }
}
