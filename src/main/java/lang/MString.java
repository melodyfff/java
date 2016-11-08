package lang;

/**
 * Description：
 * String 是final对象，不会被修改，每次使用 + 进行拼接都会创建新的对象，而不是改变原来的对象；
 * StringBuffer 可变字符串，主要用于字符串的拼接，属于线程安全的；
 * StringBuilder 可变字符串，主要用于字符串的拼接，属于线程不安全的；
 * Created by ChenXin on 2016/11/8.
 */
public class MString {
    public static void main(String[] args) {
        getStringBuilderTime();
        getStringBufferTime();
        getStringTime();
    }

    /**
     * StringBuilder拼接，线程不安全，速度最快（10000条记录大概0毫秒）
     */
    public static void getStringBuilderTime(){
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < 1000000;i++){
            sb.append(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * StringBuffer拼接，线程安全，速度略快（10000条记录大概16毫秒）
     */
    public static void getStringBufferTime(){
        long start = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < 1000000;i++){
            sb.append(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * String拼接，线程安全，速度最慢（10000条记录大概265毫秒）
     */
    public static void getStringTime(){
        long start = System.currentTimeMillis();
        String sb = new String();
        for(int i = 0;i < 10000;i++){
            sb += i;
        }
        //每次拼接产生新对象
//        String s = "sss";
//        System.out.println(s.hashCode());
//        s= s + "ds";
//        System.out.println(s.hashCode());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
