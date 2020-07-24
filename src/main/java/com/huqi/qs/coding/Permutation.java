package com.huqi.qs.coding;

import java.util.ArrayList;
import java.util.List;

/**
 * 康托展开
 */
public class Permutation {
    public static String getPermutation(int n, int k) {
        StringBuilder sb = new StringBuilder();
        // 候选数字
        List<Integer> candidates = new ArrayList<>();
        // 分母的阶乘数
        int[] factorials = new int[n + 1];
        factorials[0] = 1;
        int fact = 1;
        for (int i = 1; i <= n; ++i) {
            candidates.add(i);
            fact *= i;
            factorials[i] = fact;
        }
        //预处理
        k -= 1;
        for (int i = n - 1; i >= 0; --i) {
            // 计算候选数字的index
            int index = k / factorials[i];
            sb.append(candidates.remove(index));
            k -= index * factorials[i];
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getPermutation(4, 24));
    }
}
