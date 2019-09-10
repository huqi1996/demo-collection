package com.huqi.qs.codewars;

import java.util.Arrays;

import static com.huqi.qs.codewars.CodeWars.*;

/**
 * @author huqi
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(highAndLow001("8 3 -5 42 -1 0 0 -9 4 7 4 -4"));
        System.out.println(highAndLow002("8 3 -5 42 -1 0 0 -9 4 7 4 -4"));
        String[] s1 = new String[]{"hoqq", "bbllkw", "oox", "ejjuyyy", "plmiis", "xxxzgpsssa", "xxwwkktt", "znnnnfqknaz", "qqquuhii", "dvvvwz"};
        String[] s2 = new String[]{"cccooommaaqqoxii", "gggqaffhhh", "tttoowwwmmww"};
        System.out.println(maxDifferentLength(s1, s2));
        String pangram1 = "The quick brown fox jumps over the lazy dog.";
        String pangram2 = "You shall not pass!";
        System.out.println(check(pangram1) + "   " + check(pangram2));
        System.out.println(numberDigits(5750, 0));
        System.out.println(duplicateCount("abcdAB123412"));
        System.out.println(evaporator(10, 10, 10));
        System.out.println(high("what time are we climbing up to the volcano"));
        System.out.println(makeReadable(86399));
        System.out.println(zeros001(100));
        System.out.println(zeros002(100));
        System.out.println(reverseWords001("The greatest victory is that which requires no battle"));
        System.out.println(reverseWords002("The greatest victory is that which requires no battle"));
        System.out.println(reverseWords("ehT kciuq nworb xof spmuj revo eht yzal .god"));
        System.out.println(accum001("ABcdEf"));
        System.out.println(accum002("ABcdEf"));
        System.out.println(getCount001("aeiouaiouebbb"));
        System.out.println(getCount002("aeiouaiouebbb"));
        System.out.println(getCount003("aeiouaiouebbb"));
        System.out.println(findIt001(new int[]{5, 4, 3, 2, 1, 5, 4, 3, 2, 10, 10}));
        System.out.println(findIt002(new int[]{5, 4, 3, 2, 1, 5, 4, 3, 2, 10, 10}));
        System.out.println(findShort001("Lets all go on holiday somewhere very cold"));
        System.out.println(findShort002("Lets all go on holiday somewhere very cold"));
        System.out.println(sortDesc(123789456));
        System.out.println(sortDesc002(123789456));
        System.out.println(getXO("xxxXooOozzppp"));
        System.out.println(getXO002("xxxXooOozzppp"));
        System.out.println(squareDigits(9119));
        System.out.println(squareDigits002(9119));
        System.out.println(find(new int[]{-1, -3, -5, 9, 0}));
        System.out.println(find002(new int[]{-1, -3, -5, 9, 0}));
        System.out.println(digitalRoot(456));
        System.out.println(digitalRoot002(456));
        System.out.println(digPow(46288, 3));
        System.out.println(persistence(999));
        System.out.println(findEvenIndex(new int[]{20, 10, 30, 10, 10, 15, 35}));
        System.out.println(Arrays.toString(tribonacci(new double[]{1, 1, 1}, 10)));
        System.out.println(Arrays.toString(tribonacci002(new double[]{1, 1, 1}, 10)));
        System.out.println(spinWords("Hey wollef sroirraw"));
        System.out.println(order("4of Fo1r pe6ople g3ood th5e the2"));
        System.out.println(order002("4of Fo1r pe6ople g3ood th5e the2"));
        System.out.println(SongDecoder("WUBWEWUBAREWUBWUBTHEWUBCHAMPIONSWUBMYWUBFRIENDWUB"));
        System.out.println(SongDecoder002("WUBWEWUBAREWUBWUBTHEWUBCHAMPIONSWUBMYWUBFRIENDWUB"));
        System.out.println(isValid(new char[]{'n', 's', 'n', 's', 'n', 's', 'n', 's', 'n', 's'}));
        System.out.println(encode("Success"));
        System.out.println(encode002("Success"));
        System.out.println(longest("acef", "bdef"));
    }
}
