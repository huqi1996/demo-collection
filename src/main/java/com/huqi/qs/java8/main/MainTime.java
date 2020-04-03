package com.huqi.qs.java8.main;

import com.huqi.qs.javase.util.ListUtils;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
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
        List<Long> list = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        System.out.println("List: " + list);
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, 2L);
        map.put(3L, 4L);
        System.out.println("Map: " + map);
        System.out.println("\n".length());

        System.out.println("aaa/aaa".indexOf("/"));
        System.out.println("aaa".indexOf("/"));

        System.out.println(Byte.decode("0xf"));
        System.out.println(Byte.decode("3"));
        System.out.println(Byte.parseByte("3"));
        System.out.println(Byte.valueOf("3"));
        short sh = -15;
        System.out.println(Short.toUnsignedInt(sh));
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime time = LocalDateTime.of(monday, LocalTime.MIN);
        System.out.println(new Timestamp(time.toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        long lastWeekStartTime = time.toInstant(ZoneOffset.of("+8")).toEpochMilli() - 1000 * 3600 * 24 * 7;
        long lastWeekEndTime = time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("lastWeekStartTime : " + new Timestamp(lastWeekStartTime));
        System.out.println("lastWeekEndTime : " + new Timestamp(lastWeekEndTime));
        System.out.println(generateParenthesis(3));
        list = Arrays.asList(1L, 3L, 5L, 7L, 9L, 2L, 4L, 6L, 8L);
        list = list.stream().sorted((o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            if (o1 <= 5 && o2 <= 5) {
                if (o1 > o2) {
                    return 1;
                }
                return -1;
            } else if (o1 > 5 && o2 > 5) {
                if (o1 > o2) {
                    return -1;
                }
                return 1;
            } else if (o1 > 5) {
                return -1;
            }
            return 1;
        }).collect(Collectors.toList());
        System.out.println(list);
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("1", "1");
        stringMap.put("1", "11");
        stringMap.put("2", "2");
        System.out.println(stringMap);
        System.out.println(new ArrayList<>(stringMap.values()));
        String path = "88/88";
        System.out.println(path.lastIndexOf("/"));
        System.out.println(Long.parseLong(path.substring(0, path.lastIndexOf("/"))));
        /*List<Long> aaa = Collections.singletonList(1L);
        aaa.add(2L);
        System.out.println(aaa);*/
        List<Long> list1 = Arrays.asList(1L, 1L, 2L, 2L, 3L, 4L);
        List<Long> list2 = Arrays.asList(1L, 3L, 4L);
        List<Long> result = org.apache.commons.collections4.ListUtils.removeAll(list1, list2);
        System.out.println(result);
        System.out.println(ListUtils.removeAllWithoutRepeat(list1, list2));
    }

    public static List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        if (n == 0) {
            return list;
        }
        backTrack("", 0, 0, n, list);
        return list;
    }

    public static void backTrack(String combination, int open, int close, int n, List<String> list) {
        if (combination.length() == 2 * n) {
            list.add(combination);
        }
        if (open < n) {
            backTrack(combination + "(", open + 1, close, n, list);
        }
        if (close < open) {
            backTrack(combination + ")", open, close + 1, n, list);
        }
    }
}
