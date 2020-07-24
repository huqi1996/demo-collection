package com.huqi.qs.javase.main;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogAnalyzer {

    // 匹配日志
    public static Pattern PATTERN = Pattern.compile("\\S+\\s*\\S+\\s*\\[\\S+]\\s+(DEBUG|INFO|WARN|ERROR)*\\s+\\[\\S+].+");

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            String line;
            Map<String, Long> logMemoryCounter = new HashMap<>();
            Map<String, Long> logRecordCounter = new HashMap<>();
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Desktop\\fsdownload\\core-server.2020-07-03.0.log"));
            while ((line = reader.readLine()) != null) {
                if (!PATTERN.matcher(line).matches()) {
                    continue;
                }
                List<String> list = Arrays.stream(line.split(" ")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
                // 根据日志级别和类名进行统计
                String logKey = list.get(3) + " - " + list.get(4);
                logMemoryCounter.putIfAbsent(logKey, 0L);
                // 统计占用存储空间
                logMemoryCounter.put(logKey, logMemoryCounter.get(logKey) + line.getBytes().length);
                logRecordCounter.putIfAbsent(logKey, 0L);
                // 统计打印次数
                logRecordCounter.put(logKey, logRecordCounter.get(logKey) + 1);
            }
            List<String> logMemoryResult = sortLogAnalyzerResult(logMemoryCounter);
            System.out.println(logMemoryResult);
            List<String> logRecordResult = sortLogAnalyzerResult(logRecordCounter);
            System.out.println(logRecordResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("LogAnalyzer cost : " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static List<String> sortLogAnalyzerResult(Map<String, Long> notSortResult) {
        // 日志统计结果降序
        return notSortResult.entrySet().stream().sorted((o1, o2) -> {
            if (o1.getValue() > o2.getValue()) {
                return -1;
            }
            if (o1.getValue() < o2.getValue()) {
                return 1;
            }
            return 0;
        }).limit(10).map(v -> v.getKey() + " - " + v.getValue()).collect(Collectors.toList());
    }
}
