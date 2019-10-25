package com.huqi.qs.javase.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MathGame {
    private static Random random = new Random();
    public int illegalArgumentCount = 0;

    public static void main(String[] args) throws InterruptedException {
        MathGame game = new MathGame();
        for (; ; ) {
            game.run();
            TimeUnit.SECONDS.sleep(1L);
        }
    }

    public void run() {
        try {
            int number = random.nextInt() / 10000;
            List<Integer> primeFactors = primeFactors(number);
            print(number, primeFactors);
        } catch (Exception e) {
            System.out.println(String.format("illegalArgumentCount:%3d, ", new Object[]{Integer.valueOf(this.illegalArgumentCount)}) + e.getMessage());
        }
    }

    public static void print(int number, List<Integer> primeFactors) {
        StringBuffer sb = new StringBuffer(number + "=");
        for (Iterator localIterator = primeFactors.iterator(); localIterator.hasNext(); ) {
            int factor = ((Integer) localIterator.next()).intValue();
            sb.append(factor).append('*');
        }
        if (sb.charAt(sb.length() - 1) == '*') {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb);
    }

    public List<Integer> primeFactors(int number) {
        if (number < 2) {
            this.illegalArgumentCount += 1;
            throw new IllegalArgumentException("number is: " + number + ", need >= 2");
        }
        List<Integer> result = new ArrayList();
        int i = 2;
        while (i <= number) {
            if (number % i == 0) {
                result.add(Integer.valueOf(i));
                number /= i;
                i = 2;
            } else {
                i++;
            }
        }
        return result;
    }
}
