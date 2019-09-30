package com.huqi.qs.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huqi.qs.json.bean.Test001;

import java.util.*;

/**
 * @author huqi
 */
public class MapToObject {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("key001", "value001");
        map.put("key002", null);
        map.put("key003", "value003");
        System.out.println(map);
        System.out.println(JSON.toJSON(map));
        System.out.println(map.get(null));

        Test001 test001 = new Test001();
        test001.setKey001("value001");
        test001.setKey002(null);
        test001.setKey003("value003");
        System.out.println(test001);
        test001 = JSON.parseObject(JSON.toJSONString(map), Test001.class);
        System.out.println(test001);
        System.out.println((Object) null);
        System.out.println("null".equals(String.valueOf((Object) null)));
        List<Integer> list = new ArrayList<>(100);
        System.out.println(list.size());
        String doubles = "[1,1]";
        Double[] arr = JSONObject.parseObject(doubles, Double[].class);
        System.out.println(arr[0] + "  " + arr[1]);
        doubles = "[null,1]";
        arr = JSONObject.parseObject(doubles, Double[].class);
        System.out.println(arr[0] + "  " + arr[1]);
        System.out.println(JSONObject.toJSONString(new ArrayList<>()));

        test001 = JSON.parseObject("{\"text\":\"\"}", Test001.class);
        System.out.println("text : " + test001.getText());
        System.out.println(test001.getText().length());
        System.out.println(test001.getText().equals(""));

        System.out.println(1 ^ 2);
        int[] result = fraction(new int[]{3, 2, 0, 2});
        System.out.println(result[0] + "   " + result[1]);

        System.out.println(robot("URR", new int[][]{{10, 10}}, 3, 2));
    }

    public static int[] fraction(int[] cont) {
        if (cont.length == 1) {
            return new int[]{cont[0], 1};
        }
        int[] temp = new int[cont.length - 1];
        for (int i = 1; i < cont.length; i++) {
            temp[i - 1] = cont[i];
        }
        int[] result = fraction(temp);
        return new int[]{cont[0] * result[0] + result[1], result[0]};
    }

    public static boolean robot(String command, int[][] obstacles, int x, int y) {
        char[] cmd = command.toCharArray();
        int[] step = new int[]{0, 0};
        while (true) {
            for (char ch : cmd) {
                if (ch == 'U') {
                    step[1] = step[1] + 1;
                } else if (ch == 'R') {
                    step[0] = step[0] + 1;
                }
                for (int[] i : obstacles) {
                    if ((i[0] ^ step[0]) + (i[1] ^ step[1]) == 0) {
                        return false;
                    }
                }
                if ((x ^ step[0]) + (y ^ step[1]) == 0) {
                    return true;
                }
            }
        }
    }
}
