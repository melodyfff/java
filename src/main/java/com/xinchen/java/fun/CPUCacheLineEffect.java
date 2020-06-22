package com.xinchen.java.fun;

/**
 *
 * 缓存行cache line
 *
 * 一个Java的long类型变量是8字节，因此在一个缓存行中可以存8个long类型的变量
 *
 * CPU每次从主存中拉取数据时，会把相邻的数据也存入同一个cache line。
 *
 * 在访问一个long数组的时候，如果数组中的一个值被加载到缓存中，它会自动加载另外7个。
 *
 * 下面的例子是测试利用cache line的特性和不利用cache line的特性的效果对比。
 *
 * https://tech.meituan.com/2016/11/18/disruptor.html
 *
 *
 * @author xinchen
 * @version 1.0
 * @date 22/06/2020 11:02
 */
public class CPUCacheLineEffect {
    //考虑一般缓存行大小是64字节，一个 long 类型占8字节
    static  long[][] arr;

    public static void main(String[] args) {
        arr = new long[1024 * 1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }
        long sum = 0L;
        long marked = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i+=1) {
            for(int j =0; j< 8;j++){
                sum = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < 8; i+=1) {
            for(int j =0; j< 1024 * 1024;j++){
                sum = arr[j][i];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}