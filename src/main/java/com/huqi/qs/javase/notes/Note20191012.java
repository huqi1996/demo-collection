package com.huqi.qs.javase.notes;

import com.huqi.qs.excel.bean.Record;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huqi
 */
public class Note20191012 {
    public static void main(String[] args) {
        char ch = 'c';
        System.out.println(ch);
        ch = '"';
        System.out.println(ch);
        ch = '\"';
        System.out.println(ch);
        ch = '\u9999';
        System.out.println(ch);
        ch = '疯';
        System.out.println(ch);
        int i = ch;
        System.out.println(i);
        char c = 30127;
        System.out.println(c);

        System.out.println(1.0 / 0 == Double.POSITIVE_INFINITY);
        System.out.println(-1.0 / 0 == Double.NEGATIVE_INFINITY);
        System.out.println(0.0 / 0.0 == Double.NaN);
        System.out.println(0.0 / 0.0);

        String str = true + "";
        System.out.println(str);

        System.out.println('a' + 100 + "aaa");

        Record record = new Record();
        for (Method method : record.getClass().getDeclaredMethods()) {
            String methodName = method.getName();
            if ("get".equals(methodName.substring(0, 3))) {
                Field field;
                try {
                    field = record.getClass().getDeclaredField(methodName.substring(3, 4).toLowerCase() + methodName.substring(4));
                    field.setAccessible(true);
                    System.out.println(method.getName().substring(3) + "  " + field.getType().getSimpleName() + "  " + field.get(record));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(new Child().toString());

        for (int index = 0; index < 5; index++) {
            new Note20191012();
            // 下面两行代码的作用相同，强制系统进行垃圾回收
            System.gc();
            Runtime.getRuntime().gc();
        }

        System.out.println(StatusType.INVALID.getCode() + " - " + StatusType.INVALID.getType() + " - "
                + StatusType.INVALID.first() + " - " + StatusType.INVALID.second() + " - " + StatusType.INVALID.third());
        System.out.println(StatusType.PROCESSING.getCode() + " - " + StatusType.PROCESSING.getType() + " - "
                + StatusType.PROCESSING.first() + " - " + StatusType.PROCESSING.second() + " - " + StatusType.PROCESSING.third());
        System.out.println(StatusType.FINISH.getCode() + " - " + StatusType.FINISH.getType() + " - "
                + StatusType.FINISH.first() + " - " + StatusType.FINISH.second() + " - " + StatusType.FINISH.third());

        System.out.println("floor : " + Math.floor(3.4) + " - " + Math.floor(3.5));
        System.out.println("ceil : " + Math.ceil(3.4) + " - " + Math.ceil(3.5));
        System.out.println("round : " + Math.round(3.4) + " - " + Math.round(3.5));
        System.out.println("平方根 : " + Math.sqrt(2.3));
        System.out.println("立方根 : " + Math.cbrt(9));
        System.out.println("乘方 : " + Math.pow(2, 3));
        System.out.println("自然对数 : " + Math.log(12));
        System.out.println("绝对值 : " + Math.abs(-4.5));
        System.out.println("最大值 : " + Math.max(2.3, 4.5));
        System.out.println("最小值 : " + Math.min(2.3, 4.5));
        System.out.println("下一个浮点数 : " + Math.nextUp(-1.2) + " - " + Math.nextUp(1.2));
        System.out.println("伪随机数 [0.0<= x <= 1.0] : " + Math.random());

        // 当两个Random对象的种子相同时，以相同的顺序调用，会产生相同的数字序列
        Random random1 = new Random(100L);
        Random random2 = new Random(100L);
        System.out.println(random1.nextBoolean() + " - " + random2.nextBoolean());
        System.out.println(random1.nextInt() + " - " + random2.nextInt());
        System.out.println(random1.nextDouble() + " - " + random2.nextDouble());
        System.out.println(random1.nextDouble() + " - " + random2.nextDouble());
        System.out.println(random1.nextLong() + " - " + random2.nextLong());
        System.out.println(random1.nextLong() + " - " + random2.nextLong());

        ThreadLocalRandom random = ThreadLocalRandom.current();
        System.out.println(random.nextInt(4, 20));
        System.out.println(random.nextDouble(1.0, 10));

        System.out.println("0.05 + 0.01 = " + (0.05 + 0.01));
        System.out.println("1.0 - 0.43 = " + (1.0 - 0.43));
        System.out.println("4.017 * 100 = " + (4.017 * 100));
        System.out.println("137.3 / 100 = " + (137.3 / 100));
        BigDecimal bigDecimal1 = new BigDecimal("0.05");
        BigDecimal bigDecimal2 = BigDecimal.valueOf(0.01);
        BigDecimal bigDecimal3 = new BigDecimal(0.05);
        System.out.println("使用String作为BigDecimal的构造参数：");
        System.out.println("0.05 + 0.01 = " + bigDecimal1.add(bigDecimal2));
        System.out.println("0.05 - 0.01 = " + bigDecimal1.subtract(bigDecimal2));
        System.out.println("0.05 * 0.01 = " + bigDecimal1.multiply(bigDecimal2));
        System.out.println("0.05 / 0.01 = " + bigDecimal1.divide(bigDecimal2));
        System.out.println("使用Double作为BigDecimal的构造参数：");
        System.out.println("0.05 + 0.01 = " + bigDecimal3.add(bigDecimal2));
        System.out.println("0.05 - 0.01 = " + bigDecimal3.subtract(bigDecimal2));
        System.out.println("0.05 * 0.01 = " + bigDecimal3.multiply(bigDecimal2));
        System.out.println("0.05 / 0.01 = " + bigDecimal3.divide(bigDecimal2));

    }

    @Override
    public void finalize() {
        System.out.println("系统正在进行垃圾回收");
    }
}
