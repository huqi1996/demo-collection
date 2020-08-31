package com.huqi.qs.redis;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author huqi
 */
public class MainRedis {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.10.129");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());

        // string
        jedis.set("string1", "www.runoob.com");
        System.out.println("redis 存储的字符串为: " + jedis.get("string1"));

        // hash
        Map<String, String> user1 = new HashMap<>(3, 1);
        user1.put("name", "huqi");
        user1.put("age", "24");
        user1.put("country", "China");
        jedis.hmset("hash1", user1);
        jedis.hmset("hash2", user1);
        jedis.hmset("hash:1", user1);
        jedis.hmset("hash:2", user1);
        System.out.println("hash1 is : " + jedis.hgetAll("hash1"));
        System.out.println("hash:2 is : " + jedis.hgetAll("hash:2"));

        // list
        System.out.println("list1删除结果：" + jedis.del("list1"));
        //存储数据到列表中
        // 插到头部
        jedis.lpush("list1", "value111");
        jedis.lpush("list1", "value222");
        jedis.lpush("list1", "value333");
        // 插到尾部
        jedis.rpush("list1", "value444");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("list1", 0, 3);
        System.out.println("list1查询数据量：" + list.size());
        for (String i : list) {
            System.out.println("列表项为: " + i);
        }

        // set
        System.out.println("set成功添加了" +
                jedis.sadd("set1", "value11", "value22", "value33", "value33")
                + "个元素");
        System.out.println(jedis.smembers("set1"));
        System.out.println(jedis.smembers("111"));

        // zset(sorted set：有序集合)
        jedis.zadd("zset1", 66, "value1");
        jedis.zadd("zset1", 78, "value2");
        jedis.zadd("zset1", 66, "value3");
        jedis.zadd("zset1", 83.5, "value4");
        jedis.zadd("zset1", 90.5, "value2");
        System.out.println(jedis.zrange("zset1", 0, 3));
        System.out.println(jedis.zrangeByScore("zset1", 66, 90.5));

        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }
}
