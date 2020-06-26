package com.xinchen.java.spi;

import java.util.ServiceLoader;

/**
 *
 * Java中的spi = 基于接口的编程＋策略模式＋配置文件 的动态加载机制
 *
 * 当服务的提供者，提供了服务接口的一种实现之后，在jar包的META-INF/services/目录里同时创建一个以服务接口命名的文件。
 * 该文件里就是实现该服务接口的具体实现类。
 *
 * 根据SPI的规范我们的服务实现类必须有一个无参构造方法。否则会报错：{@link NoSuchMethodException}
 *
 * https://melodyfff.github.io/2019/05/12/%E3%80%90Java%E3%80%91%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3Java%E4%B8%AD%E7%9A%84spi%E6%9C%BA%E5%88%B6/
 *
 *
 * <pre>
 * 过程：
 * {@link ServiceLoader}默认扫描前缀： META-INF/services/
 *
 * {@link ServiceLoader#load(Class)} 会使用当前线程的类加载器创建一个新的service loader
 *      public static <S> ServiceLoader<S> load(Class<S> service,ClassLoader loader)
 *
 *
 * 提供：
 * LinkedHashMap<String,S> providers 缓存已经实例化的接口对象
 * LazyIterator lookupIterator       懒加载迭代器，传入接口类型和类加载器，当call next()时，真正的核心方法nextService()处理具体细节
 *
 *
 * 实例化和缓存：
 * lookupIterator.next()的时候，会去调用nextService(),步骤如下：
 * 1. hasNextService() 判断是否有服务存在，先加载META-INF/services/ + service.getName()路径下的接口资源文件，通过ClassLoader.getSystemResources(fullName)，主要是获取具体接口服务的具体实现类的类名
 * 2. newInstance()初始,并放入providers缓存中
 *
 * </pre>
 *
 *
 *
 *
 *
 * @see ServiceLoader
 * @author xinchen
 * @version 1.0
 * @date 13/05/2020 15:49
 */
public class Client {
    public static void main(String[] args) {
        ServiceLoader<IService> services = ServiceLoader.load(IService.class);

        services.forEach(service-> service.say("Hello World"));
        // 结果
        // class com.xinchen.java.spi.ServiceImplA say: Hello World
        // class com.xinchen.java.spi.ServiceImplB say: Hello World
    }
}
