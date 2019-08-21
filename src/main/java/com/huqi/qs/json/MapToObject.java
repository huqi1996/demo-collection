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
    }
}
