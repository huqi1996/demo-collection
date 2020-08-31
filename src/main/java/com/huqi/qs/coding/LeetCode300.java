package com.huqi.qs.coding;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huqi 20200811
 */
public class LeetCode300 {

    /**
     * 给定一个数组和一个目标和，从数组中找两个数字相加等于目标和，输出这两个数字的下标。
     * 1. 双重循环，遍历所有情况，看相加是否等于目标和，如果符合直接输出，注意第二层循环的索引不要从0开始，否则会重复遍历。时间复杂度n的平方；空间复杂度1
     * 2. 改用哈希表，替代第一种方法的第二层循环，时间复杂度n，空间复杂度n
     */
    private int[] twoSum001001(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length, 1);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int sub = target - nums[i];
            if (map.containsKey(sub) && map.get(sub) != i) {
                return new int[]{i, map.get(sub)};
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * 两个链表表示的数相加，实现两个很大的数相加，链表最左边表示个位数
     */
    //private
}
