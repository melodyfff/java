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
     * 与(&)、非(~)、或(|)、异或(^)
     * hash函数确定Entry插入位置
     * key的hashCode 与 key的hashCode无符号右位移16 取 异或^
     *
     * @param key 对象
     * @return 插入位置
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static void main(String[] args) {
        String temp = "ojbk";
        int newCap, newThr = 0;
        Map map = new HashMap(16);
        map.put(1, 1);
        map.put(2, 2);

        System.out.println(temp.hashCode() >>> 16);
        System.out.println(hash(temp));
        System.out.println(3411812 ^ 52);
        System.out.println(1 << 4);
        System.out.println(16 * 0.75f);
    }
}
