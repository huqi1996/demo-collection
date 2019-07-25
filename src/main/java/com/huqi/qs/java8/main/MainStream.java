package com.huqi.qs.java8.main;

import com.huqi.qs.java8.bean.Country;
import com.huqi.qs.java8.bean.Person;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * @author huqi
 */
public class MainStream {
    public static List<Person> people;

    static {
        people = Arrays.asList(
                new Person(1L, "A", Country.CHINA.getCode(), 13000L, false),
                new Person(2L, "B", Country.JAPAN.getCode(), 9000L, true),
                new Person(3L, "C", Country.JAPAN.getCode(), 7000L, true),
                new Person(4L, "D", Country.AMERICA.getCode(), 11000L, false),
                new Person(5L, "E", Country.AMERICA.getCode(), 8000L, true)
        );
    }

    public static void main(String[] args) {
        // 流操作：无状态和有状态
        // 排序->过滤->映射->限制数量，过滤和映射 循环合并
        Stream<Person> personStream = people.stream();
        List<String> userNames = personStream.sorted(comparing(Person::getSalary).reversed())
                .filter(p -> {
                    System.out.println("filter : " + p.getUserName());
                    return p.getSalary() > 8000;
                })
                .map(p -> {
                    System.out.println("map : " + p.getUserName());
                    return p.getUserName();
                })
                .limit(2).collect(Collectors.toList());
        System.out.println(userNames);
        // 过滤->排序->映射->限制数量，过滤所有元素
        personStream = people.stream();
        userNames = personStream.filter(p -> {
            System.out.println("filter : " + p.getUserName());
            return p.getSalary() > 8000;
        }).sorted(comparing(Person::getSalary).reversed())
                .map(p -> {
                    System.out.println("map : " + p.getUserName());
                    return p.getUserName();
                }).limit(2).collect(Collectors.toList());
        System.out.println(userNames);
        System.out.println(Stream.of(1, 2, 3, 4, 3, 4, 5, 6)
                .filter(i -> i % 2 == 0).distinct().collect(Collectors.toList()));
        // 截至前4个元素
        System.out.println(Stream.of(1, 2, 3, 4, 3, 4, 5, 6)
                .limit(4).filter(i -> i % 2 == 0).distinct().collect(Collectors.toList()));
        // 跳过前4个元素
        System.out.println(Stream.of(1, 2, 3, 4, 3, 4, 5, 6)
                .skip(4).filter(i -> i % 2 == 0).distinct().collect(Collectors.toList()));
        Stream<String> stringStream = Stream.of("Hello", "World");
        List<String> stringList = stringStream.collect(Collectors.toList());
        // Stream<String[]>
        stringList.stream().map(str -> str.split("")).distinct()
                .forEach(str -> System.out.println(str.getClass().getSimpleName() + " : " + str.length));
        // Stream<Stream<String>>
        stringList.stream().map(str -> str.split("")).map(Arrays::stream).distinct()
                .forEach(stream -> System.out.println(stream.collect(Collectors.toList())));
        // Stream<String>
        System.out.println(stringList.stream().map(str -> str.split("")).flatMap(Arrays::stream).distinct()
                .collect(Collectors.toList()));
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        List<Integer> integerList2 = Arrays.asList(1, 2, 3);
        // 流的扁平化
        System.out.println(integerList.stream().flatMap(i -> integerList2.stream().map(j -> i + "-" + j))
                .collect(Collectors.toList()));
        System.out.println(integerList.stream().flatMap(i -> integerList2.stream().filter(j -> (i + j) % 2 == 0).map(j -> i + "-" + j))
                .collect(Collectors.toList()));
        System.out.println(people.stream().anyMatch(p -> p.getSalary() > 10000));
        System.out.println(people.stream().allMatch(p -> p.getSalary() > 10000));
        System.out.println(people.stream().noneMatch(p -> p.getSalary() > 10000));
        System.out.println(people.stream().filter(Person::getMarried).findFirst());
        System.out.println(people.stream().filter(p -> p.getSalary() > 100000).findFirst());
        // findFirst与findAny的区别：并行
        System.out.println(people.stream().filter(Person::getMarried).findAny());
        System.out.println(people.stream().filter(p -> p.getSalary() > 100000).findAny());
        // 规约
        System.out.println(people.stream().map(Person::getSalary).reduce(0L, (i, j) -> i + j));
        System.out.println(people.stream().map(Person::getSalary).reduce(0L, Long::sum));
        System.out.println(people.stream().map(Person::getSalary).reduce(Long::sum));
        System.out.println(people.stream().map(Person::getSalary).reduce(Long::max));
        System.out.println(people.stream().map(p -> 1).reduce(Integer::sum));
        System.out.println(people.stream().count());
        System.out.println(people.size());
        System.out.println(people.stream().filter(p -> p.getSalary() > 8000)
                .sorted(comparing(Person::getSalary)).collect(Collectors.toList()));
        System.out.println(people.stream().map(p -> Country.fromCode(p.getCountry())).distinct().collect(Collectors.toList()));
        System.out.println(people.stream().map(p -> Country.fromCode(p.getCountry())).collect(Collectors.toSet()));
        System.out.println(people.stream().map(Person::getUserName).distinct().sorted().reduce((i, j) -> i + " " + j));
        System.out.println(people.stream().map(Person::getUserName).distinct().sorted().collect(Collectors.joining(" ")));
        // 数值流：原始类型流特化
        // LongStream
        System.out.println(people.stream().mapToLong(Person::getSalary).sum());
        System.out.println(people.stream().filter(p -> p.getSalary() > 100000).mapToLong(Person::getSalary).sum());
        // boxed : LongStream -> Stream<Long>
        System.out.println(people.stream().mapToLong(Person::getSalary).boxed().collect(Collectors.toList()));
        // OptionalLong
        System.out.println(people.stream().mapToLong(Person::getSalary).max());
        System.out.println(people.stream().filter(p -> p.getSalary() > 100000).mapToLong(Person::getSalary).max());
        System.out.println(people.stream().filter(p -> p.getSalary() > 100000).mapToLong(Person::getSalary).max().orElse(0));
        // IntStream
        System.out.println(IntStream.range(1, 100).filter(i -> i % 2 == 0).count());
        System.out.println(IntStream.rangeClosed(1, 100).filter(i -> i % 2 == 0).count());
        IntStream.rangeClosed(1, 100).boxed().flatMap(
                a -> IntStream.rangeClosed(a, 100).boxed()
                        .map(b -> new double[]{a, b, Math.sqrt(a * a + b * b)}).filter(t -> t[2] % 1 == 0)
        ).limit(5).forEach(t -> System.out.println(t[0] + "   " + t[1] + "   " + t[2]));
        System.out.println(IntStream.rangeClosed(1, 100).sum());
        int[] intArray = {1, 2, 3, 4, 5, 6};
        System.out.println(Arrays.stream(intArray).sum());
        // iterate  Stream<T>
        System.out.println(Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}).limit(10)
                .map(t -> t[0] + "").collect(Collectors.joining(" ")));
        // generate  IntStream
        System.out.println(IntStream.generate(new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = previous;
                int nextCurrent = previous + current;
                previous = current;
                current = nextCurrent;
                return oldPrevious;
            }
        }).limit(10).mapToObj(t -> t + "").collect(Collectors.joining(" ")));
        // generate  Stream<T>
        System.out.println(Stream.generate(new Supplier<Integer>() {
            private int previous = 0;
            private int current = 1;

            @Override
            public Integer get() {
                int oldPrevious = previous;
                int nextCurrent = previous + current;
                previous = current;
                current = nextCurrent;
                return oldPrevious;
            }
        }).limit(10).map(t -> t + "").collect(Collectors.joining(" ")));
        System.out.println(people.stream().collect(Collectors.maxBy(Comparator.comparingLong(Person::getSalary))));
        System.out.println(people.stream().max(Comparator.comparingLong(Person::getSalary)));
        System.out.println(people.stream().collect(Collectors.summingLong(Person::getSalary)));
        System.out.println(people.stream().mapToLong(Person::getSalary).sum());
        System.out.println(people.stream().collect(Collectors.averagingLong(Person::getSalary)));
        LongSummaryStatistics peopleStatistics = people.stream().collect(Collectors.summarizingLong(Person::getSalary));
        System.out.println(peopleStatistics);
        System.out.println(people.stream().filter(t -> t.getSalary() > 100000).map(Person::getSalary).reduce(Long::sum));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()))));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()),
                Collectors.groupingBy(t -> t.getSalary() > 10000))));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()),
                Collectors.counting())));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()),
                Collectors.maxBy(Comparator.comparingLong(Person::getSalary)))));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()),
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingLong(Person::getSalary)), Optional::get))));
        System.out.println(people.stream().collect(Collectors.toMap(t -> Country.fromCode(t.getCountry()),
                Function.identity(), BinaryOperator.maxBy(Comparator.comparingLong(Person::getSalary)))));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()),
                Collectors.summingLong(Person::getSalary))));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()),
                Collectors.mapping(t -> t.getSalary() > 10000, Collectors.toSet()))));
        System.out.println(people.stream().collect(Collectors.groupingBy(t -> Country.fromCode(t.getCountry()),
                Collectors.mapping(t -> t.getSalary() > 10000, Collectors.toCollection(HashSet::new)))));
        System.out.println(people.stream().collect(Collectors.partitioningBy(t -> t.getSalary() > 10000)));
        System.out.println(people.stream().collect(Collectors.partitioningBy(t -> t.getSalary() > 10000,
                Collectors.groupingBy(t -> Country.fromCode(t.getCountry())))));
        System.out.println(people.stream().collect(Collectors.partitioningBy(t -> t.getSalary() > 10000,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingLong(Person::getSalary)), Optional::get))));
        System.out.println(IntStream.rangeClosed(2, 20).boxed().collect(Collectors.partitioningBy(candidate ->
                IntStream.rangeClosed(2, (int) Math.sqrt(candidate)).anyMatch(t -> candidate % t == 0)
        )));
    }
}
