package com.huqi.qs.java8.main;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    }
}
