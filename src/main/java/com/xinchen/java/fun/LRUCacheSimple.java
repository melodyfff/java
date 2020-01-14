package com.xinchen.java.fun;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * LRU means Least Recently Used
 *
 * 最近最少使用清除策略
 *
 * reference : https://github.com/doocs/advanced-java/blob/master/docs/high-concurrency/redis-expiration-policies-and-lru.md
 *
 * @author xinchen
 * @version 1.0
 * @date 14/01/2020 16:22
 */
public class LRUCacheSimple<K,V> extends LinkedHashMap<K,V>{


    private static final float hashLoadFactory = 0.75f;
    private final int cacheSize;

    public LRUCacheSimple(int cacheSize) {
        // accessOrder 必须设置为true
        // false： 基于插入顺序     true：  基于访问顺序
        super((int) Math.ceil(cacheSize / hashLoadFactory) + 1, hashLoadFactory, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当 map中的数据量大于指定的缓存个数的时候，就自动删除最老的数据。
        return size() > cacheSize;
    }

    public static void main(String[] args) {
        LRUCacheSimple<Integer, Integer> lru = new LRUCacheSimple<>(10);
        for (int i = 0; i < 100; i++) {
            lru.put(i,i);
            // 模拟一直有在使用
            lru.get(0);
            lru.get(1);
        }
        System.out.println(lru.toString());
    }
}
