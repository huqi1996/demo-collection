package com.huqi.qs.java8.main;

import com.huqi.qs.java8.bean.Apple;
import com.huqi.qs.java8.bean.Country;
import com.huqi.qs.java8.bean.Person;
import com.huqi.qs.java8.function.TriFunction;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author huqi 20190621
 */
public class MainLambda {
    public static List<Apple> apples;
    public static List<Person> people;
    public static Map<Long, String> peopleMap;

    static {
        apples = Arrays.asList(
                new Apple("red", 185.2),
                new Apple("green", 152.0),
                new Apple("red", 125.3));
        people = Arrays.asList(
                new Person(1L, "1"),
                new Person(2L, "2"),
                new Person(3L, "3")
        );
        peopleMap = new HashMap<>(4);
        peopleMap.put(1L, "111");
        peopleMap.put(2L, "222");
        peopleMap.put(3L, "333");
        peopleMap.put(4L, "444");
    }

    public static void main(String[] args) {
        test190621();
        predicateTest();
        consumerTest();
        functionTest();
        biFunctionTest();
        triFunctionTest();

        List<Integer> list = new ArrayList<>();
        System.out.println(list.stream().filter(i -> i > 0).allMatch(i -> i > 1));

        while (list.size() < 10) {
            testStream();
            list.add(list.size());
        }
    }

    private static void testStream() {
        // 测试源
        List<Integer> sourceList = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            sourceList.add(i);
        }
        System.out.println("数据条数：" + sourceList.size());

        long a1 = System.currentTimeMillis();
        for (int i = 0; i < sourceList.size(); i++) {
            if (i < 2000) {
                doSome();
            }
        }
        long a2 = System.currentTimeMillis();
        System.out.println("普通for循环用时：" + (a2 - a1));

        long b1 = System.currentTimeMillis();
        for (int t : sourceList) {
            if (t < 2000) {
                doSome();
            }
        }
        long b2 = System.currentTimeMillis();
        System.out.println("增强for循环用时：" + (b2 - b1));

        long c1 = System.currentTimeMillis();
        sourceList.forEach(t -> {
            if (t < 2000) {
                doSome();
            }
        });
        long c2 = System.currentTimeMillis();
        System.out.println("forEach循环用时：" + (c2 - c1));

        long d1 = System.currentTimeMillis();
        sourceList.stream().filter(t -> t < 2000).forEach(t -> doSome());
        long d2 = System.currentTimeMillis();
        System.out.println("forEach-Stream循环用时：" + (d2 - d1));
    }

    private static void doSome() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test190621() {
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", null);
        map.put("bbb", 111);
        System.out.println(map.keySet());
        System.out.println(map.keySet().contains("aaa"));
        System.out.println(map.keySet().contains("ccc"));
        String aaa = "aaa";
        System.out.println(aaa.compareToIgnoreCase("AAA"));
        System.out.println(aaa.compareToIgnoreCase("BBB"));

        System.out.println("初始化：" + apples);
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println("weight递增 : " + apples);
        apples.sort((o1, o2) -> o2.getWeight().compareTo(o1.getWeight()));
        System.out.println("weight递减 : " + apples);
        apples.sort(comparing(Apple::getWeight));
        System.out.println("weight递增 : " + apples);
        apples.sort(comparing(Apple::getWeight).reversed());
        System.out.println("weight递减 : " + apples);
        apples.sort(comparing(Apple::getColor).thenComparing(Apple::getWeight).reversed());
        System.out.println("color递减，weight递减 : " + apples);
    }

    public static void predicateTest() {
        System.out.println("color is red : " + predicate(apples, new Apple()));
        System.out.println("weight gt 150 : " + predicate(apples, new Predicate<Apple>() {
            @Override
            public boolean test(Apple a) {
                return a.getWeight() > 150;
            }
        }));
        System.out.println("color is green : " + predicate(apples, a -> "green".equals(a.getColor())));
        Predicate<Apple> redApples = new Apple();
        System.out.println("color is green : " + predicate(apples, redApples.negate()));
        System.out.println("color is red and gt 150 : " + predicate(apples, redApples.and(a -> a.getWeight() > 150)));
        System.out.println("color is green or lt 150 : " + predicate(apples, redApples.negate().or(a -> a.getWeight() < 150)));

        System.out.println("userId gt 1 : " + predicate(people, p -> p.getUserId() > 1));
    }

    public static <T> List<T> predicate(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public static void consumerTest() {
        consumer(people, p -> {
            p.setUserId(p.getUserId() + 100L);
            System.out.println("Consumer : " + p.getUserId() + "***" + p.getUserName());
        });
    }

    public static <T> void consumer(List<T> list, Consumer<T> consumer) {
        list.forEach(consumer);
    }

    public static void functionTest() {
        System.out.println("Function : " + function(apples, t -> t.getColor() + "***" + t.getWeight()));
        System.out.println("Function : " + function(people, p -> p.getUserId() + "***" + p.getUserName()));
        Function<Integer, Integer> add = a -> a + 1;
        Function<Integer, Integer> mul = a -> a * 2;
        System.out.println("Function : " + add.andThen(mul).apply(5));
        System.out.println("Function : " + add.compose(mul).apply(5));
    }

    public static <T, R> List<R> function(List<T> list, Function<T, R> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    public static void biFunctionTest() {
        System.out.println("BiFunction : " + biFunction(peopleMap, Person::new));
    }

    public static <T, U, R> List<R> biFunction(Map<T, U> map, BiFunction<T, U, R> biFunction) {
        return map.entrySet().stream().map(entry -> biFunction.apply(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    public static void triFunctionTest() {
        Map<Long, Map<String, Byte>> people = new HashMap<>();
        Map<String, Byte> params = new HashMap<>();
        params.put("TriFunction111", Country.CHINA.getCode());
        people.put(1L, params);
        params = new HashMap<>();
        params.put("TriFunction222", Country.AMERICA.getCode());
        people.put(2L, params);
        params = new HashMap<>();
        params.put("TriFunction333", Country.JAPAN.getCode());
        people.put(3L, params);
        System.out.println("TriFunction : " + triFunction(people, Person::new));
    }

    public static <T, U, V, R> List<R> triFunction(Map<T, Map<U, V>> params, TriFunction<T, U, V, R> triFunction) {
        return params.entrySet().stream().map(param -> param.getValue().entrySet().stream()
                .map(entry -> triFunction.apply(param.getKey(), entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()).get(0)
        ).collect(Collectors.toList());
    }
}
