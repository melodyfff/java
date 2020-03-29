package com.xinchen.java.algorithms;

import java.util.Arrays;

/**
 *
 * 常见排序算法
 *
 * 参考： https://www.cnblogs.com/guoyaohua/p/8600214.html
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/3/29 10:39
 */
public class Sort {
    /**
     * 冒泡排序
     *
     * @param arr     int[]
     * @param display 是否显示结果
     */
    public static void bubbleSort(int[] arr, boolean display) {
        System.out.println("Start Bubble Sort.");
        long start = System.currentTimeMillis();
        int tmp;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        System.out.println("Cost : " + (System.currentTimeMillis() - start) + " ms");
        if (display) {
            display(arr);
        }
    }

    /**
     * 选择排序
     *
     * @param arr     int[]
     * @param display 是否显示结果
     */
    public static void selectionSort(int[] arr, boolean display) {
        System.out.println("Start Selection Sort.");
        long start = System.currentTimeMillis();
        int tmp;
        for (int i = 0; i < arr.length; i++) {
            // 保存最小值索引
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换最小值
            tmp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = tmp;
        }
        System.out.println("Cost : " + (System.currentTimeMillis() - start) + " ms");
        if (display) {
            display(arr);
        }
    }

    /**
     * 插入排序
     *
     * @param arr     int[]
     * @param display 是否显示结果
     */
    public static void insertionSort(int[] arr, boolean display) {
        System.out.println("Start Insertion Sort.");
        long start = System.currentTimeMillis();
        // 存储当前值
        int current;
        // 存储前一个值索引
        int preIndex;
        for (int i = 0; i < arr.length - 1; i++) {
            // 从后一个数开始，初始值假设已经排序
            current = arr[i + 1];
            preIndex = i;
            while (preIndex >= 0 && current < arr[preIndex]) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        System.out.println("Cost : " + (System.currentTimeMillis() - start) + " ms");
        if (display) {
            display(arr);
        }
    }

    /**
     * 希尔排序(缩小增量排序)
     *
     * @param arr     int[]
     * @param display 是否显示结果
     */
    public static void shellSort(int[] arr, boolean display) {
        System.out.println("Start Insertion Sort.");
        long start = System.currentTimeMillis();

        int len = arr.length;
        int current, gap = len / 2;

        // 根据选择增量gap
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                // 当前值
                current = arr[i];
                // 同一序列内的前一个数
                int preIndex = i - gap;
                while (preIndex >= 0 && arr[preIndex] > current) {
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex + gap] = current;
            }
            // 直到gap=1,整个序列被分为一组
            gap /= 2;
        }

        System.out.println("Cost : " + (System.currentTimeMillis() - start) + " ms");
        if (display) {
            display(arr);
        }
    }

    /**
     * 归并排序（分治）
     *
     * @param arr     int[]
     * @param display 是否显示结果
     */
    public static void mergeSort(int[] arr, boolean display) {
        System.out.println("Start Merge Sort.");
        long start = System.currentTimeMillis();

        arr = mergeOne(arr);

        System.out.println("Cost : " + (System.currentTimeMillis() - start) + " ms");
        if (display) {
            display(arr);
        }
    }

    private static int[] mergeOne(int[] arr) {
        // 问题拆分至最小值1
        if (arr.length < 2) {
            return arr;
        }
        // 获取中间索引
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return merge(mergeOne(left), mergeOne(right));
    }

    /**
     * 合并结果
     * @param left int[]
     * @param right int[]
     * @return int[]
     */
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            // 如果左数组已经排序完毕，则直接移动右数组
            if (i>=left.length){
                result[index] = right[j++];
                // 如果右数组已经排序完毕，则直接移动左数组
            } else if (j>=right.length){
                result[index] = left[i++];
            } else if (left[i]>right[j]){
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }
        return result;
    }


    public static void display(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static int[] copy(int[] arr) {
        int[] c = new int[arr.length];
        System.arraycopy(arr, 0, c, 0, arr.length);
        return c;
    }

    public static void main(String[] args) {

        final int[] seed = SeedMan.getSeedInt(10_000);

        bubbleSort(copy(seed), false);

        selectionSort(copy(seed), false);

        insertionSort(copy(seed), false);

        shellSort(copy(seed), false);

        mergeSort(copy(seed), false);
    }
}
