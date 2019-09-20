package com.huqi.qs.coding;

/**
 * @author huqi 20190919
 */
public class LeetCode {
    public static int numJewelsInStones(String J, String S) {
        return (int) S.chars().filter(c -> J.contains(String.valueOf((char) c))).count();
    }

    public static String defangIPaddr(String address) {
        return address.replace(".", "[.]");
    }

    /**
     * Definition for singly-linked list.
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
