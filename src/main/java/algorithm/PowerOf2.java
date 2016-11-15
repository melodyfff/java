package algorithm;

/**
 * Description：
 * Created by ChenXin on 2016/11/15.
 */
public class PowerOf2 {

    /**
     * 时间复杂度O(logN)
     */
    public static boolean isPowerOf2(int number) {
        long begin = System.currentTimeMillis();
        int temp = 1;
        while (temp <= number) {
            if (temp == number) {
                return true;
            }
            //temp = temp * 2;
            //乘以2操作该做左位移1位
            temp = temp << 1;
        }
        long end = System.currentTimeMillis();
        System.out.println("isPowerOf2()耗时：" + (end - begin));
        return false;

    }

    /**
     * 十进制  二进制    N-1     N&N-1   是否2的乘方
     * 8      1000B    111B     0       是
     * 16     10000B   1111B    0       是
     * 32     100000B  11111B   0       是
     * 64     1000000B 111111B  0       是
     * 100    1100100B 1100011B 1100000B否
     *
     * 规律凡是2的乘方和它本身减一相与，即N&N-1，结果必然为0
     *
     * 时间复杂度O(1)
     */
    public static boolean isPowerOf2Fast(int number) {
        return (number & number - 1) == 0;
    }

    /**获取而精准补码中的1的个数*/
    public static int bitCount(int i) {
        // HD, Figure 5-2
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;
    }

    public static void main(String[] args) {
        System.out.println(isPowerOf2(1024));
        System.out.println(isPowerOf2Fast(1024));
        System.out.println(8 >> 3);
        System.out.println(Integer.toBinaryString(100));
        System.out.println(bitCount(100));
    }
}
