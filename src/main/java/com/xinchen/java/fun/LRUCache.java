package com.xinchen.java.fun;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 目前的实现有点问题
 *
 * 参考： https://www.cnblogs.com/wyq178/p/9976815.html
 *
 * Map查询时间复杂O(1)
 * 双向链表新增删除操作时间复制O(1)
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/1/14 19:19
 */
public class LRUCache<K, V> {
    /**
     * 容量
     */
    private int capacity;
    /**
     * 所有node节点的缓存
     */
    private Map<K, Node<K, V>> caches;

    /**
     * 头节点
     */
    private Node<K, V> head;
    /**
     * 尾节点
     */
    private Node<K, V> tail;

    public LRUCache() {
        // 默认10个容量
        this(10);
    }

    public LRUCache(int capacity) {
        // 容量必须大于0
        assert capacity > 0;
        // 初始化容量大小
        this.capacity = capacity;
        caches = new HashMap<>(16);
    }

    public void put(K key, V value) {
        Node<K, V> node = caches.get(key);
        // 表示新增
        if (null == node) {
            reSize(); // 进行容量检查，移除链表末操作
            node = new Node<>(key, value);
        }
        // 替换value
        node.value = value;
        // 置于链表头
        moveToHead(node);
        caches.put(key, node);
    }

    public V get(K key) {
        final Node<K, V> node = caches.get(key);
        // 移动到链表头
        moveToHead(node);
        return null == node ? null : node.value;
    }

    public Node<K, V> remove(K key) {
        final Node<K, V> node = caches.get(key);
        if (null != node) {
            // 从链表中去除
            if (null != node.next) { node.next.pre = node.pre; }
            if (null != node.pre) { node.pre.next = node.next; }
            // 判断如果是头尾节点的特殊情况
            if (node == head){ head = node.next; }
            if (node == tail){ tail = node.pre; }
        }
        return caches.remove(key);
    }

    public void clear(){
        // 清空
        head = tail = null;
        caches.clear();
    }

    private void reSize() {
        // 如果当前大小超过容量大小
        // 移除链表中的末尾
        if (caches.size() >= capacity) {
            caches.remove(tail.key);
            removeLast();
        }
    }

    private void moveToHead(Node<K, V> node) {
        // 此操作必须保证node不为空
        if (null == node) {
            return;
        }
        // 判断节点是否为head节点
        if (head == node) {
            // 已经位于头部
            return;
        }
        // 判断是否为尾节点tail，这里一定要判断，不然链表会出错
        if (node == tail) {
            tail = tail.pre;
        }
        // 如果头或者未指针为空，则认为是第一个元素插入进行初始化
        if (null == head || null == tail) {
            head = tail = node;
            return;
        }
        // 将node抽取出来，并把头尾指针指向pre和next
        if (null != node.next) {
            node.next.pre = node.pre;
        }
        if (null != node.pre) {
            node.pre.next = node.next;
        }
        node.next = head;
        head.pre = node;
        head = node;
        // 头节点pre为null,对应尾节点next为null
        node.pre = null;
    }

    private void removeLast() {
        if (null != tail) {
            tail = tail.pre;
            // 可能存在只有一个节点的情况head == tail
            if (null != tail) {
                tail.next = null;
            } else {
                head = null;
            }

        }
    }

    private static class Node<K, V> {
        K key; // key
        V value; // value

        Node<K, V> pre; // 上一个
        Node<K, V> next; // 下一个节点

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<K,V> node = head;
        while (node != null) {
            sb.append(String.format("%s:%s ", node.key, node.value));
            node = node.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>();
        for (int i = 0; i < 20; i++) {
            cache.put(i,i);
            // 模拟一直在使用
            cache.get(0);
            cache.get(1);
        }
        System.out.println(cache);
    }
}
