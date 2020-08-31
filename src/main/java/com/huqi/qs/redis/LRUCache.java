package com.huqi.qs.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * noeviction(默认策略)：对于写请求不再提供服务，直接返回错误（DEL请求和部分特殊请求除外）
 * allkeys-lru：从所有key中使用LRU算法进行淘汰
 * volatile-lru：从设置了过期时间的key中使用LRU算法进行淘汰
 * allkeys-random：从所有key中随机淘汰数据
 * volatile-random：从设置了过期时间的key中随机淘汰
 * volatile-ttl：在设置了过期时间的key中，根据key的过期时间进行淘汰，越早过期的越优先被淘汰
 * ttl：time to live
 * 当使用volatile-lru、volatile-random、volatile-ttl这三种策略时，如果没有key可以被淘汰，则和noeviction一样返回错误。
 *
 * LRU(Least Recently Used)，即最近最少使用，是一种缓存置换算法。
 * Redis使用的是近似LRU算法，它跟常规的LRU算法还不太一样。近似LRU算法通过随机采样法淘汰数据，每次随机出5（默认）个key，从里面淘汰掉最近最少使用的key。
 * 可以通过maxmemory-samples参数修改采样数量： 例：maxmemory-samples 10
 * maxmenory-samples配置的越大，淘汰的结果越接近于严格的LRU算法
 *
 * LFU（Least Frequently Used），Redis4.0新加的淘汰策略。根据key的最近被访问的频率进行淘汰，很少被访问的优先被淘汰，被访问的多的则被留下来。
 * LFU一共有两种策略：
 * volatile-lfu：在设置了过期时间的key中使用LFU算法淘汰key
 * allkeys-lfu：在所有的key中使用LFU算法淘汰数据
 *
 * LRU(Least Recently Used)，即最近最少使用，是一种缓存置换算法。
 */
public class LRUCache<k, v> {
    //容量
    private int capacity;
    //当前有多少节点的统计
    private int count;
    //缓存节点
    private Map<k, Node<k, v>> nodeMap;
    private Node<k, v> head;
    private Node<k, v> tail;

    public LRUCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(String.valueOf(capacity));
        }
        this.capacity = capacity;
        this.nodeMap = new HashMap<>();
        //初始化头节点和尾节点，利用哨兵模式减少判断头结点和尾节点为空的代码
        Node headNode = new Node(null, null);
        Node tailNode = new Node(null, null);
        headNode.next = tailNode;
        tailNode.pre = headNode;
        this.head = headNode;
        this.tail = tailNode;
    }

    public void put(k key, v value) {
        Node<k, v> node = nodeMap.get(key);
        if (node == null) {
            if (count >= capacity) {
                //先移除一个节点
                removeNode();
            }
            node = new Node<>(key, value);
            //添加节点
            addNode(node);
        } else {
            //移动节点到头节点
            moveNodeToHead(node);
        }
    }

    public Node<k, v> get(k key) {
        Node<k, v> node = nodeMap.get(key);
        if (node != null) {
            moveNodeToHead(node);
        }
        return node;
    }

    private void removeNode() {
        Node node = tail.pre;
        //从链表里面移除
        removeFromList(node);
        nodeMap.remove(node.key);
        count--;
    }

    private void removeFromList(Node<k, v> node) {
        Node pre = node.pre;
        Node next = node.next;

        pre.next = next;
        next.pre = pre;

        node.next = null;
        node.pre = null;
    }

    private void addNode(Node<k, v> node) {
        //添加节点到头部
        addToHead(node);
        nodeMap.put(node.key, node);
        count++;
    }

    private void addToHead(Node<k, v> node) {
        Node next = head.next;
        next.pre = node;
        node.next = next;
        node.pre = head;
        head.next = node;
    }

    public void moveNodeToHead(Node<k, v> node) {
        //从链表里面移除
        removeFromList(node);
        //添加节点到头部
        addToHead(node);
    }

    class Node<k, v> {
        k key;
        v value;
        Node pre;
        Node next;

        public Node(k key, v value) {
            this.key = key;
            this.value = value;
        }
    }
}
