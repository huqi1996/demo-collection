package com.huqi.qs.javase.main;

import com.alibaba.fastjson.JSONObject;
import com.huqi.qs.java8.bean.Apple;
import com.huqi.qs.java8.bean.Person;
import com.huqi.qs.javase.util.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * @author huqi
 */
public class MainSE {

    public static void main(String[] args) {
//        demo20190212();
//        demo20190325();
//        demo20190523();
        // demoLocalDateTime();
        StringBuilder builder = new StringBuilder();
        builder.append("a");
        builder.append("a");
        builder.append("a");
        System.out.println(builder.length());
        System.out.println("aaa123bbb".replaceAll("\\d+", ""));
        System.out.println("abc[]*/\\:?123");
        System.out.println("abc[]*/\\:?123".replaceAll("([*/:\\\\\\[\\]?])", ""));
        System.out.println(DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(new Timestamp(System.currentTimeMillis()).toLocalDateTime()));
        Map<String, String> map001 = new HashMap<>();
        map001.put("111", "111");
        map001.put("333", "333");
        map001.put("222", "222");
        System.out.println(map001);
        System.out.println(String.format("aaa%sbbb%s", "111", "222"));
        List<String> list001 = new ArrayList<>();
        list001.add("111");
        list001.add("222");
        list001.add("333");
        System.out.println(list001.remove(0));
        System.out.println(list001);

        NoteTemplate note = new NoteTemplate();
        note.setPrefix("注意事项\r\n" +
                "- 标红字段为必填项\r\n" +
                "- 导入模板中不包括图片、附件或只读字段\r\n" +
                "- 单行文本已设置校验规则的，请注意内容格式是否正确\r\n");
        note.setSuffix("- 省市区字段请使用“/”分隔，例如“广东省/深圳市/南山区”\r\n" +
                "- 楼宇房源字段请使用“/”分隔，例如“1栋/101”\r\n" +
                "- 子表单字段请在其它工作表中编辑。默认第一个字段作为子表单数据关系映射，你可以在表头中重新选择。请尽量选择可用作数据唯一标识的字段，否则校验重复时子表单数据则无法导入\r\n");
        note.setNumberRange("- %s，数字范围字段请使用英文逗号“,”隔开，例如“1,100”\r\n");
        note.setDateShort("- %s，日期字段请使用“yyyy-MM-dd”格式\r\n");
        note.setDateShortRange("- %s，日期范围字段请使用“yyyy-MM-dd”格式。日期范围请使用英文逗号“,”隔开，例如“1970-01-01, 1970-01-02”\r\n");
        note.setDateLong("- %s，日期字段请使用“yyyy-MM-dd  hh:mm”格式\r\n");
        note.setDateLongRange("- %s，日期范围字段请使用“yyyy-MM-dd hh:mm”格式。日期范围请使用英文逗号“,”隔开，例如“1970-01-01 12:00, 1970-01-02 12:00”\r\n");
        note.setTimeShort("- %s，时刻字段请使用“hh:mm”格式\r\n");
        note.setTimeLong("- %s，时刻字段请使用“hh:mm:ss”格式\r\n");
        note.setCheckbox("- %s，多选字段请使用“,”分隔，例如“选项1,选项2”\r\n");
        note.setUserSelector("- %s，选择用户字段请在输入姓名并在英文括号中输入手机号码，例如“张三(13800138000)”，多个用户使用英文逗号“,”分隔\r\n");
        System.out.println(note);
        System.out.println(JSONObject.toJSONString("aaa".split(",")));

        System.out.println(list001.stream().collect(Collectors.joining("、")));
        System.out.println(String.format("aaa%sbbb", list001.stream().collect(Collectors.joining("、"))));

        System.out.println("list001= " + list001);

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
        System.out.println(System.getProperty("line.separator").equals("/r/n"));
        String str001 = "abc";
        str001 = Optional.ofNullable(str001)
                .map(str -> str.replaceAll(".$", ""))
                .orElse(str001);
        System.out.println(str001);
        String str002 = null;
        StringBuilder builder1 = new StringBuilder();
        builder1.append(str001);
        builder1.append(str002);
        System.out.println(builder1.toString());
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
        Map<Integer, String> map002 = Collections.synchronizedMap(new HashMap<>());
        BlockingQueue queue001 = new ArrayBlockingQueue<String>(2);
        System.out.println(queue001.offer("aaa"));
        System.out.println(queue001.offer("bbb"));
        System.out.println(queue001.poll());
        System.out.println(queue001.poll());
        System.out.println(queue001.poll());
        // 不允许往队列中插入 null ，否则无法区分 poll 和 peek 方法返回的 null 是提示还是一个真正的元素。
        // System.out.println(queue001.offer(null));
        System.out.println(Integer.parseInt("111", 2));
        System.out.println(list001.subList(0, 1));
        long now = System.currentTimeMillis();
        System.out.println(new Timestamp(now));

        // 1.LocalDateTime获取毫秒数
        //获取秒数
        long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("当前时间的秒数：" + second);
        //获取毫秒数
        long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("当前时间的毫秒数：" + milliSecond);
        System.out.println(System.currentTimeMillis());
        // 2.LocalDateTime与String互转
        //时间转字符串格式化
        System.out.println(LocalDateTime.now(ZoneOffset.of("+8")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        //字符串转时间
        System.out.println(LocalDateTime.parse("1970-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(new Timestamp(-28800000));
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
        System.out.println(ListUtils.removeAllWithoutRepeat(list1, list2).size());
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
