package com.huqi.qs.coding;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * @author huqi 20190715
 */
public class CodeWars {

    public static String highAndLow001(String numbers) {
        int max = Arrays.stream(numbers.split(" ")).mapToInt(Integer::parseInt).max().getAsInt();
        int min = Arrays.stream(numbers.split(" ")).mapToInt(Integer::parseInt).min().getAsInt();
        return String.format("%d %d", max, min);
    }

    public static String highAndLow002(String numbers) {
        IntSummaryStatistics statistics = Arrays.stream(numbers.split(" ")).mapToInt(Integer::parseInt).summaryStatistics();
        return String.format("%d %d", statistics.getMax(), statistics.getMin());
    }

    public static int maxDifferentLength(String[] a1, String[] a2) {
        return Math.max(Arrays.stream(a1).mapToInt(String::length).max().getAsInt() -
                        Arrays.stream(a2).mapToInt(String::length).min().getAsInt(),
                Arrays.stream(a2).mapToInt(String::length).max().getAsInt() -
                        Arrays.stream(a1).mapToInt(String::length).min().getAsInt());
    }


    public static boolean check(String sentence) {
        return sentence.chars().map(Character::toLowerCase).filter(Character::isLetter).distinct().count() == 26;
    }

    public static long numberDigits(int n, int d) {
        return IntStream.rangeClosed(0, n)
                .map(i -> i * i)
                .flatMap(i -> (i + "").chars())
                .map(Character::getNumericValue)
                .filter(i -> i == d)
                .count();
    }

    public static long duplicateCount(String text) {
        return text.toLowerCase().chars().boxed().collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .values().stream().filter(i -> i > 1).count();
    }

    public static int evaporator(double content, double evaporatorPerDay, double threshold) {
        return (int) Math.ceil(Math.log(threshold / 100) / Math.log(1 - evaporatorPerDay / 100));
    }

    public static String high(String s) {
        return Arrays.stream(s.split(" ")).max(Comparator.comparingInt(i -> i.chars().map(j -> j - 96).sum())).get();
    }

    public static String makeReadable(int seconds) {
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds / 60) % 60, seconds % 60);
    }

    public static int zeros001(int n) {
        if (n / 5 == 0) {
            return 0;
        }
        return n / 5 + zeros001(n / 5);
    }

    public static int zeros002(int n) {
        int result = 0;
        for (int i = 5; i < n; i *= 5) {
            result += n / i;
        }
        return result;
    }

    public static String reverseWords001(String str) {
        List<String> list = Arrays.asList(str.split(" "));
        Collections.reverse(list);
        return String.join(" ", list);
    }

    public static String reverseWords002(String str) {
        return Arrays.stream(str.split(" ")).reduce((x, y) -> y + " " + x).get();
    }

    public static String reverseWords(final String original) {
        return Arrays.stream(original.split(" ")).map(str -> new StringBuilder(str).reverse().toString()).collect(Collectors.joining(" "));
    }

    /**
     * @author huqi
     */
    public static String accum001(String s) {
        return IntStream.rangeClosed(1, s.length())
                .mapToObj(i -> IntStream.rangeClosed(1, i).mapToObj(j -> s.charAt(i - 1) + "").collect(Collectors.joining()))
                .map(i -> i.toUpperCase().substring(0, 1) + i.toLowerCase().substring(1))
                .collect(Collectors.joining("-"));
    }

    public static String accum002(String s) {
        return IntStream.range(0, s.length())
                .mapToObj(i -> IntStream.range(0, i + 1)
                        .mapToObj(j -> j == 0 ? String.valueOf(s.charAt(i)).toUpperCase() : (s.charAt(i) + "").toLowerCase()).collect(Collectors.joining())
                ).collect(Collectors.joining("-"));
    }

    /**
     * @author huqi
     */
    public static long getCount001(String str) {
        return str.chars().mapToObj(i -> (char) i).filter(i -> "aeiou".contains(i + "")).count();
    }

    public static long getCount002(String str) {
        return str.chars().filter(i -> "aeiou".indexOf(i) > -1).count();
    }

    public static long getCount003(String str) {
        return str.replaceAll("(?i)[^aeiou]", "").length();
    }

    /**
     * @author huqi
     */
    public static int findIt001(int[] a) {
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result ^= a[i];
        }
        return result;
    }

    public static int findIt002(int[] a) {
        return Arrays.stream(a).reduce(0, (x, y) -> x ^ y);
    }

    public static int findShort001(String s) {
        return Arrays.stream(s.split(" ")).map(String::length).min(Integer::compareTo).orElse(-1);
    }

    public static int findShort002(String s) {
        return Arrays.stream(s.split(" ")).mapToInt(String::length).min().getAsInt();
    }

    /**
     * @author huqi
     */
    public static int sortDesc(final int num) {
        return Integer.parseInt(Arrays.stream((num + "").split("")).map(Integer::parseInt)
                .sorted(comparing(Integer::intValue).reversed()).map(i -> i + "").collect(Collectors.joining()));
    }

    public static int sortDesc002(final int num) {
        return Integer.parseInt(String.valueOf(num).chars().mapToObj(i -> String.valueOf(Character.getNumericValue(i)))
                .sorted(Comparator.reverseOrder()).collect(Collectors.joining()));
    }

    /**
     * @author huqi
     */
    public static boolean getXO(String str) {
        return str.toLowerCase().chars().map(i -> {
            if (i == 'x') {
                return 1;
            } else if (i == 'o') {
                return -1;
            } else {
                return 0;
            }
        }).sum() == 0;
    }

    public static boolean getXO002(String str) {
        str = str.toLowerCase();
        return str.replace("o", "").length() == str.replace("x", "").length();
    }

    public static int squareDigits(int n) {
        return Integer.parseInt(String.valueOf(n).chars()
                .map(Character::getNumericValue)
                .mapToObj(i -> (i * i) + "")
                .collect(Collectors.joining()));
    }

    public static int squareDigits002(int n) {
        return Integer.parseInt(String.valueOf(n).chars()
                .map(i -> Integer.parseInt(String.valueOf((char) i)))
                .map(i -> i * i)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining()));
    }

    public static int find(int[] integers) {
        int flag = 0;
        for (int i = 0; i < 3; i++) {
            if (integers[i] % 2 == 0) {
                flag++;
            } else {
                flag--;
            }
        }
        flag = flag > 0 ? 1 : 0;
        for (int i : integers) {
            if (Math.abs(i) % 2 == flag) {
                return i;
            }
        }
        return 0;
    }

    public static int find002(int[] integers) {
        int flag = Arrays.stream(integers).limit(3L).map(i -> i % 2 == 0 ? 1 : -1).sum();
        int finalFlag = flag > 0 ? 1 : 0;
        return Arrays.stream(integers).filter(i -> Math.abs(i) % 2 == finalFlag).findFirst().getAsInt();
    }

    public static int digitalRoot(int n) {
        if ((n + "").length() == 1) {
            return n;
        }
        n = (n + "").chars().map(Character::getNumericValue).sum();
        return digitalRoot(n);
    }

    public static int digitalRoot002(int n) {
        return n > 9 ? n % 9 : n;
    }

    public static long digPow(int n, int p) {
        char[] chars = (n + "").toCharArray();
        int sum = 0;
        for (char aChar : chars) {
            sum += Math.pow(Character.getNumericValue(aChar), p++);
        }
        return sum % n == 0 ? sum / n : -1;
    }

    public static int persistence(long n) {
        if (n < 10) {
            return 0;
        }
        n = (n + "").chars().map(Character::getNumericValue).reduce(1, (x, y) -> x * y);
        return persistence(n) + 1;
    }

    public static int findEvenIndex(int[] arr) {
        int sumRight = Arrays.stream(arr).sum() - arr[0];
        int sumLeft = 0;
        if (sumLeft == sumRight) {
            return 0;
        }
        for (int i = 1; i < arr.length; i++) {
            sumLeft += arr[i - 1];
            sumRight -= arr[i];
            if (sumLeft == sumRight) {
                return i;
            }
        }
        return -1;
    }

    public static double[] tribonacci(double[] s, int n) {
        double[] result = Arrays.copyOf(s, n);
        for (int i = s.length; i < n; i++) {
            result[i] = result[i - 1] + result[i - 2] + result[i - 3];
        }
        return result;
    }

    public static double[] tribonacci002(double[] s, int n) {
        return Stream.iterate(s, p -> new double[]{p[1], p[2], p[0] + p[1] + p[2]}).limit(n).mapToDouble(p -> p[0]).toArray();
    }

    public static String spinWords(String sentence) {
        return Arrays.stream(sentence.split(" ")).map(i -> i.length() < 5 ? i : new StringBuilder(i).reverse().toString()).collect(Collectors.joining(" "));
    }

    public static String order(String words) {
        return Arrays.stream(words.split(" "))
                .sorted(comparing(i -> Integer.parseInt(Pattern.compile("[^0-9]").matcher(i).replaceAll(""))))
                .collect(Collectors.joining(" "));
    }

    public static String order002(String words) {
        return Arrays.stream(words.split(" "))
                .sorted(comparing(i -> Integer.valueOf(i.replaceAll("\\D", ""))))
                .collect(Collectors.joining(" "));
    }

    public static String songDecoder(String song) {
        return Arrays.stream(song.replace("WUB", " ").split(" ")).filter(i -> i.length() > 0).collect(Collectors.joining(" "));
    }

    public static String songDecoder002(String song) {
        return song.replaceAll("(WUB)+", " ").trim();
    }

    public static boolean isValid(char[] walk) {
        return walk.length == 10
                && String.valueOf(walk).replace("n", "").length()
                == String.valueOf(walk).replace("s", "").length()
                && String.valueOf(walk).replace("w", "").length()
                == String.valueOf(walk).replace("e", "").length();
    }

    public static String encode(String word) {
        return word.toLowerCase().chars()
                .mapToObj(i -> (word.length() - word.toLowerCase().replace(String.valueOf((char) i), "").length()) == 1 ? "(" : ")")
                .collect(Collectors.joining());
    }

    public static String encode002(String word) {
        return word.toLowerCase().chars()
                .mapToObj(i -> String.valueOf((char) i))
                .map(i -> word.toLowerCase().indexOf(i) == word.toLowerCase().lastIndexOf(i) ? "(" : ")")
                .collect(Collectors.joining());
    }

    public static String longest(String s1, String s2) {
        return (s1 + s2).chars().distinct().sorted().mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
    }

    public static int[] monkeyCount(final int n) {
        int[] arr = new int[n];
        for (int i = 1; i <= n; i++) {
            arr[i - 1] = i;
        }
        return arr;
    }

    public static int[] monkeyCount002(final int n) {
        return IntStream.range(1, n).toArray();
    }

    public static List<String> sort(List<String> textbooks) {
        return textbooks.stream().sorted(String::compareToIgnoreCase).collect(Collectors.toList());
    }

    public static List<String> sort002(List<String> textbooks) {
        textbooks.sort(Comparator.naturalOrder());
        return textbooks;
    }

    public static List<String> sort003(List<String> textbooks) {
        //use sort() from Collections with the static field of String class to ensure case insensitivity
        textbooks.sort(String.CASE_INSENSITIVE_ORDER);
        return textbooks;
    }

    public static List<String> sort004(List<String> textbooks) {
        return textbooks.stream().sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
    }
}
