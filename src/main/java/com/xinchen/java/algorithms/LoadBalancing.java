package com.xinchen.java.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * 负载均衡算法
 *
 * see <a href="https://www.cnblogs.com/xrq730/p/5154340.html">几种简单的负载均衡算法及其Java代码实现</a>
 * see <a href="https://blog.csdn.net/u011047968/article/details/99844116">Java代码实现负载均衡六种算法</a>
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/7/21 22:41
 */
public class LoadBalancing {
    /**
     * 待路由的Ip列表，Key代表Ip，Value代表该Ip的权重
     */
    static HashMap<String, Integer> serverWeightMap = new HashMap<>();
    private final Object lock = new Object();
    private Integer pos = 0;

    static {
        serverWeightMap.put("192.168.1.100", 1);
        serverWeightMap.put("192.168.1.101", 1);
        // 权重为4
        serverWeightMap.put("192.168.1.102", 4);
        serverWeightMap.put("192.168.1.103", 1);
        serverWeightMap.put("192.168.1.104", 1);
        // 权重为3
        serverWeightMap.put("192.168.1.105", 3);
        serverWeightMap.put("192.168.1.106", 1);
        // 权重为2
        serverWeightMap.put("192.168.1.107", 2);
        serverWeightMap.put("192.168.1.108", 1);
        serverWeightMap.put("192.168.1.109", 1);
        serverWeightMap.put("192.168.1.110", 1);
    }

    /**
     * 轮询
     * @return server ip
     */
    String roundRobin() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap = new HashMap<>(serverWeightMap);

        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<>(keySet);

        String server;
        synchronized (lock) {
            if (pos > keySet.size()) {
                pos = 0;
            }
            server = keyList.get(pos);
            pos++;
        }
        return server;
    }
}
