package algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 冒泡排序
 * @author Xin Chen
 * @date 2018/5/8 14:13
 */
public class BubbleSort2 {
    /**
     * 日志记录
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(BubbleSort2.class);


    /**
     * 创建数组
     * @param size 初始化数组大小
     * @return int[]
     */
    private static int[] createArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = ((int) (Math.random()*1000));
        }
        return arr;
    }

    /**
     * 排序算法
     * @param arrays 需要排序的数组
     */
    private static void sort(int[] arrays) {
        int temp;
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays.length - i - 1; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    temp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int[] arr = createArray(10000);
        sort(arr);

        StringBuffer sb = new StringBuffer();
        for (int i : arr) {
            sb.append(i + ",");
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("sort size:{}",arr.length);
        LOGGER.info("result:{}",sb.toString());
        LOGGER.info("cost time: {}ms",endTime-startTime);

    }
}
