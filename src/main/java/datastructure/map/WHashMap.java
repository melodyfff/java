package datastructure.map;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap研究
 *
 * @author Xin Chen
 * @date 2017/12/11 14:32
 */
public class WHashMap {

    /**
     * hash函数确定Entry插入位置
     * @param key 对象
     * @return 插入位置
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static void main(String[] args) {
        String temp = "ojbk";
        Map map = new HashMap(16);
        System.out.println(hash(temp));
        System.out.println(3411812^52);
    }
}
