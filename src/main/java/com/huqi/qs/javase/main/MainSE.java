package com.huqi.qs.javase.main;

import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.huqi.qs.java8.bean.Apple;
import com.huqi.qs.java8.bean.Person;
import com.huqi.qs.javase.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.validator.ValidatorUtil;
import org.elasticsearch.common.netty.util.internal.ExecutorUtil;
import org.springframework.transaction.TransactionStatus;

import javax.management.RuntimeErrorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author huqi
 */
public class MainSE {

    private static Pattern p = Pattern.compile("java");

    public static void main(String[] args) {
        demo20190212();
        demo20190325();
        demo20190523();
        demoLocalDateTime();
        StringBuilder builder = new StringBuilder();
        builder.append("a");
        builder.append("a");
        builder.append("a");
        System.out.println(builder.length());
        System.out.println("aaa123bbb".replaceAll("\\d+", ""));
        System.out.println("abc[]*/\\:?123");
        System.out.println("([*/:\\[]?])");
        System.out.println("abc[]*/\\:?123".replaceAll("([*/:\\\\\\[\\]?])", ""));
        System.out.println(DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(new Timestamp(System.currentTimeMillis()).toLocalDateTime()));
        Map<String, String> map001 = new HashMap<>();
        map001.put("111", "111");
        map001.put("333", "333");
        map001.put("222", "222");
        map001.put("222", "222222");
        map001.put("111", "111111111");
        System.out.println("map001 is " + map001);
        System.out.println(String.format("aaa%sbbb%s", "111", "222"));
        List<String> list001 = new ArrayList<>();
        list001.add("111");
        list001.add("222");
        list001.add("333");
        System.out.println(list001.remove(0));
        System.out.println(list001);
        System.out.println(JSONObject.toJSONString("aaa".split(",")));
        System.out.println("list001= " + list001);
        System.out.println(list001.stream().collect(Collectors.joining("、")));
        System.out.println("a".startsWith("bb"));
        String date001 = "2020-06-10";
        System.out.println(date001.matches("\\d{4}-\\d{1,2}-\\d{1,2}"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date001 + " 00:00:00", formatter);
        Date date002 = Date.from(dateTime.toInstant(OffsetDateTime.now().getOffset()));
        System.out.println(date002);
        Date date003 = Date.from((new Timestamp(1591718400000L).toInstant()));
        System.out.println(date003);
        System.out.println(date002.equals(date003));

        list001.add("1111111");
        list001.add("123456");
        System.out.println(list001);
        // toMap时，key冲突，是填充前值还是后值
        System.out.println(list001.stream().collect(Collectors.toMap(s -> s.charAt(0), s -> s, (s1, s2) -> s1)));
        System.out.println(list001.stream().collect(Collectors.toMap(s -> s.charAt(0), s -> s, (s1, s2) -> s2)));

        Apple apple001 = new Apple("red", 1.1);
        System.out.println(apple001);
        updateApple(apple001);
        System.out.println(apple001);
        System.out.println("\r\n".equals(System.getProperty("line.separator")));
        String str001 = "abc";
        str001 = Optional.ofNullable(str001)
                .map(str -> str.replaceAll(".$", ""))
                .orElse(str001);
        System.out.println(str001);
        System.out.println(str001 + null);
        Timestamp timestamp001 = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp001);
        System.out.println(timestamp001.toString());
        System.out.println(count001(10));
        System.out.println(new MainSE().count002(10));
        int int001 = 0b1111_1111;
        // 255
        System.out.println(int001);
        // 15
        int001 = int001 >> 4;
        System.out.println(int001);
        System.out.println(15 * 256);
        System.out.println(int001 << 8);
        int001 = 0xff;
        System.out.println(int001);
        System.out.println("map001 is: " + map001);
        Map<String, String> map002 = Collections.synchronizedMap(map001);
        System.out.println("map002 is: " + map002);
        // 修改map001，map002也会跟着修改
        map001.put("555", "555");
        System.out.println("map001 is: " + map001);
        System.out.println("map002 is: " + map002);
        // 修改map002，map001也会跟着修改
        map002.put("666", "666");
        System.out.println("map001 is: " + map001);
        System.out.println("map002 is: " + map002);
        BlockingQueue queue001 = new ArrayBlockingQueue<String>(2);
        System.out.println(queue001.offer("aaa"));
        System.out.println(queue001.offer("bbb"));
        System.out.println(queue001.poll());
        System.out.println(queue001.poll());
        System.out.println(queue001.poll());
        // 不允许往队列中插入 null ，否则无法区分 poll 和 peek 方法返回的 null 是提示还是一个真正的元素。
        // System.out.println(queue001.offer(null)); // NullPointerException
        System.out.println(Integer.parseInt("111", 2));
        System.out.println(list001);
        System.out.println(list001.subList(0, 1));

        // 1.LocalDateTime获取毫秒数
        //获取秒数
        long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("当前时间的秒数：" + second);
        //获取毫秒数
        long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("当前时间的毫秒数：" + milliSecond);
        System.out.println("当前时间的毫秒数：" + System.currentTimeMillis());
        // 2.LocalDateTime与String互转
        //时间转字符串格式化
        System.out.println(LocalDateTime.now(ZoneOffset.of("+8")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        //字符串转时间
        LocalDateTime dateTime1 = LocalDateTime.parse("1970-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateTime1);
        System.out.println(dateTime1.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(new Timestamp(-28800000));

        // System.out.println只能直接打印char[]，其它元素类型的数组都被当成object打印，而object的打印实现是String.valueOf(object)
        char[] ch = new char[]{'x', 'y', '中'};
        System.out.println(ch);
        System.out.println("ch=" + ch);
        int[] intArray = {1, 2, 3, 4};
        System.out.println(intArray);
        System.out.println(Integer.toHexString(ch[0] & 0xFFFFFF));
        System.out.println(Integer.toHexString(ch[2] & 0xFFFFFF));

        boolean isOpen = false;
        // 打印是否开启了断言，如果为false，则没有启用断言
        // 如果开启了断言，会将isOpen的值改为true
        // JVM默认关闭断言指令，即遇到assert语句就自动忽略了，不执行。
        // 要执行assert语句，必须给Java虚拟机传递-enableassertions（可简写为-ea）参数启用断言
        assert isOpen = true;
        System.out.println(isOpen);

        // 泛型 + 可变长参数
        String[] arr = asArray("one", "two", "three");
        System.out.println(Arrays.toString(arr));
        // ClassCastException:
        /*String[] firstTwo = pickTwo("one", "two", "three");
        System.out.println(Arrays.toString(firstTwo));*/

        List<String> list002 = Arrays.asList(arr);
        // list002只读
        // list002.add("111");
        System.out.println(list002);

        Matcher m = p.matcher("The java book is java program book c");
        StringBuffer sb = new StringBuffer();
        if (m.find()) {
            m.appendReplacement(sb, "python");
        }
        System.out.println(sb);
        if (m.find()) {
            m.appendReplacement(sb, "python");
        }
        System.out.println(sb);
        if (m.find()) {
            m.appendReplacement(sb, "python");
        }
        System.out.println(sb);
        System.out.println(m.replaceAll("c++"));

        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("java -version");
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            System.out.println("OUTPUT");
            while ((line = stdoutReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("ERROR");
            while ((line = stderrReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = process.waitFor();
            System.out.println("process exit value is " + exitVal);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Process process = Runtime.getRuntime().exec(new String[]{"java", "-version"});
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("RuntimeProcess Printed: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }

        StringBuilder builder1 = new StringBuilder();
        System.out.println(builder1.toString() + "aaa");

        String str002 = "/printFormValueByPrintTemplate?printUrl=";
        System.out.println(str002.substring(0, str002.indexOf("?") + 1));
        System.out.println(str002.substring(str002.indexOf("?") + 1));

        List<String> list003 = new ArrayList<>();
        for (String s : list003) {
            System.out.println(s);
        }
        list003.forEach(s -> System.out.println(s));
        testSwitch(2);

        System.out.println("aaaa".lastIndexOf("bbb"));
        String str003 = "aaa&formPrintName=111222";
        System.out.println(str003.lastIndexOf("formPrintName"));
        System.out.println(str003.substring(str003.lastIndexOf("formPrintName") + "formPrintName".length() + 1));

        StringBuilder builder001 = new StringBuilder();
        System.out.println(builder001.toString().equals(""));
        System.out.println(builder001.toString().length());

        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().maxMemory() / (1024 * 1024 * 1024.0));

        Byte byte001 = 1;
        Map<String, Object> map003 = new HashMap<>();
        map003.put("a", byte001);
        Byte byte002 = 1;
        System.out.println(byte002 == map003.get("a"));
        System.out.println(map003.remove("a"));
        System.out.println(map003.remove("bb"));

        String str004 = "/localFlowListener/officeauto";
        System.out.println(str004.substring(str004.lastIndexOf("/")));

        str004 = null;
        System.out.println(str004 instanceof Object);

        // 最小2进制，最大36进制
        System.out.println(Long.parseLong("11", Character.MAX_RADIX));

        map003.put("a", 1);
        for (Map.Entry<String, Object> entry : map003.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        map003.forEach((key, value) -> System.out.println(key + " - " + value));

        Thread thread001 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, "001");
        thread001.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread002 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            try {
                thread001.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, "002");
        thread002.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // interrupt会结束join等待
        //thread001.interrupt();

        Lock lock001 = new ReentrantLock(true);
        Thread thread003 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            lock001.lock();
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock001.unlock();
            System.out.println(Thread.currentThread().getName() + " end");
        }, "003");
        thread003.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread004 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            lock001.lock();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock001.unlock();
            System.out.println(Thread.currentThread().getName() + " end");
        }, "004");
        thread004.start();

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("red", 1.1));
        apples.add(new Apple("green", null));
        apples.add(new Apple("yellew", 1.2));
        Map<String, Double> colorToWeight = apples.stream().filter(apple -> apple.getWeight() != null).collect(Collectors.toMap(Apple::getColor, Apple::getWeight));
        System.out.println(colorToWeight);

        str001 = "aaa-bbb-ccc";
        System.out.println(str001.contains("-"));
        System.out.println(str001.substring(0, str001.indexOf("-")));

        Apple apple002 = new Apple(null, 1.1);
        System.out.println(apple002.toString());
        System.out.println(apple002.getColor() == null);

        String[] arr001 = new String[] {"a", "b","c"};
        List<String> list004 = Arrays.asList(arr001);
        System.out.println(list004);
        // list004不能add元素
        /*list004.add("d");
        System.out.println(list004);*/

        System.out.println(File.separator);
    }

    public static void testSwitch(int value) {
        switch (value) {
            case 1:
                int result = value * 2;
                System.out.println("testSwitch: " + value + " - " + result);
                break;
            case 2:
                result = value * 3;
                System.out.println("testSwitch: " + value + " - " + result);
                break;
            default:
                result = value;
                System.out.println("testSwitch: " + value + " - " + result);
        }
    }

    static <K> K[] pickTwo(K k1, K k2, K k3) {
        System.out.println(k1.getClass());
        K[] arr = asArray(k1, k2);
        System.out.println(arr.getClass());
        return arr;
    }

    static <T> T[] asArray(T... objs) {
        System.out.println(objs.getClass());
        return objs;
    }

    public static void processException() throws Exception {
        try {
            throwException();
        } finally {
            // finally不能省略
            System.out.println("end");
        }
    }

    public static void throwException() throws IOException {
        throw new IOException();
    }

    //3.Date与LocalDateTime互转
    //将java.util.Date 转换为java8 的java.time.LocalDateTime,默认时区为东8区
    public static LocalDateTime dateConvertToLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }

    //将java8 的 java.time.LocalDateTime 转换为 java.util.Date，默认时区为东8区
    public static Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

    public static synchronized int count001(int num) {
        if (num <= 1) {
            return 1;
        }
        return count001(--num) + 1;
    }

    public synchronized int count002(int num) {
        if (num <= 1) {
            return 1;
        }
        return count001(--num) + 1;
    }

    public static void updateApple(Apple apple) {
        apple = new Apple("black", 2.2);
    }

    public static void demo20190212() {
        // 1,3,5,7,9这些数字太简单，得跳过
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
        System.out.println("[]".substring(1, 1).split(",")[0]);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    // break只终止内层for循环，不会终止外层for循环
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
        // 不会重复剔除相同元素
        System.out.println(ListUtils.removeAllWithoutRepeat(list1, list2).size());
        // 重复剔除相同元素
        System.out.println(org.apache.commons.collections4.ListUtils.removeAll(list1, list2).size());
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

        // 按userName排序
        Collections.sort(people);
        System.out.println(people);
    }

    public static void demoLocalDateTime() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("今天凌晨0点：" + todayStart);
        System.out.println("今天凌晨0点：" + todayStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println("当前时间：" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println("当前时间：" + System.currentTimeMillis());
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println("今天晚上24点：" + todayEnd);
        System.out.println("今天晚上24点：" + todayEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println("一天的时长：" + (todayEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                - todayStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        System.out.println("一天的时长：" + 3600 * 24 * 1000);
        System.out.println("2100年1月1日1时1分：" + LocalDateTime.of(2100, 1, 1, 1, 1));
        System.out.println("2100年1月1日1时1分：" + LocalDateTime.of(2100, 1, 1, 1, 1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
