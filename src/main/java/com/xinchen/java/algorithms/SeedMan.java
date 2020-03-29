package com.xinchen.java.algorithms;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * 生成随机数据
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/3/29 10:12
 */
public final class SeedMan {

    public static int[] getSeedInt(int size){
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(size);

        }
        return arr;
    }

    public static int[] getSeedIntByStream(int size,int low,int high){
        return ThreadLocalRandom.current().ints(size, low, high).toArray();
    }
}
