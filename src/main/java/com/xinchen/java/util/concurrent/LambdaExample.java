package com.xinchen.java.util.concurrent;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author xinchen
 * @version 1.0
 * @date 19/12/2019 10:14
 */
public class LambdaExample {
    public static void main(String[] args) {
        // Supplier 生产者     T get()
        System.out.println(supplierDemo().get());

        // Consumer 消费者     void accept(T t)
        consumerDemo()
                // 按照顺序执行
                .andThen((t) -> System.out.println(t * t))
                .andThen((t) -> System.out.println(t * 2))
                .andThen((t) -> System.out.println(t * 3))
//                .andThen((q)->{ throw new IllegalArgumentException();})
                .accept(supplierDemo().get());

        // Predicate 断言    boolean test(T t)
        boolean re = predicateDemo()
                .and((o) -> o > 0)
                .or(Objects::nonNull)
                .test(supplierDemo().get());
        System.out.println(re);


        // Function 对参数执行并返回  R apply(T t)
        //                >>> 1
        //                >>> 2
        //                >>> 3
        //                >>> 4
        //                3200000
        int finalRe = functionDemo()
                // 以下为执行的顺序
                .compose((o)->{
                    System.out.println(">>> 2");
                    return (int) o * 20;
                })
                .compose((o)->{
                    System.out.println(">>> 1");
                    return (int) o * 20;
                })
                .andThen((o)->{
                    System.out.println(">>> 3");
                    return (int) o * 20;
                })
                .andThen((o)->{
                    System.out.println(">>> 4");
                    return (int) o * 20;
                })
                .apply(supplierDemo().get());
        System.out.println(finalRe);


        int tran = transDemo()
                // 以下为执行的顺序 1 ,2
                .before((o) -> {
                    System.out.println(">>>>>> 2");
                    return (int) o * 10;
                })
                .before((o) -> {
                    System.out.println(">>>>>> 1");
                    return (int) o * 20;
                })
                .apply(supplierDemo().get());

    }



    static Supplier<Integer> supplierDemo() {
        return () -> 1;
    }

    static Consumer<Integer> consumerDemo() {
        return (o) -> System.out.println("consumer " + o);
    }

    static Predicate<Integer> predicateDemo() {
        return (o) -> o != 1;
    }

    static Function<Integer,Integer> functionDemo(){
        return (o) -> o;
    }

    static Trans<Integer,Integer> transDemo(){
        return (o) -> o;
    }


    /**
     * 按照函数式接口编程标准编写
     * @param <T>
     * @param <R>
     */
    interface Trans<T,R> {
        R apply(T t);

        default <V> Trans<V,R> before(Trans<?super V,?extends T> before){
            return (V v) -> apply(before.apply(v));
        }
    }

}
