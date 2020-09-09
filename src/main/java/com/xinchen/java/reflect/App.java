package com.xinchen.java.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xinchen
 * @version 1.0
 * @date 08/09/2020 15:57
 */
class App implements TestInterface {

    public App() {
        System.out.println("App init");
    }

    @TestAnnotation
    public App(Object o){
        System.out.println("App init with args");
    }

    @Override
    public void call(){
        System.out.println("App call()");
    }

    private void call(Object... objects){
        System.out.println("App call(Object... objects)");
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        // Constructor - 无参数构造函数初始化 - 可能报错NoSuchMethodException
        final App instance = App.class.newInstance();
        final App app = (App) Class.forName("com.xinchen.java.reflect.App").newInstance();

        // Constructor - 有参构造函数初始化 - 可能报错InvocationTargetException
        final Constructor<App> constructor = App.class.getConstructor(Object.class);
        constructor.newInstance(new Object());

        // 判断接口兼容性
        // 1). isAssignableFrom()方法是从类继承的角度去判断，instanceof()方法是从实例继承的角度去判断
        // 2). isAssignableFrom()方法是判断是否为某个类的父类，instanceof()方法是判断是否某个类的子类
        // true
        System.out.println(App.class.isInstance(instance));
        //  true
        System.out.println(instance instanceof TestInterface);
        //  true
        System.out.println(TestInterface.class.isAssignableFrom(App.class));

        // Method
        final Method call = app.getClass().getDeclaredMethod("call", Object[].class);
        // 如果这里不进行强制转换会报错： wrong number of arguments
        // 如果在其他类中调用需要设置权限 setAccessible(true)
        call.invoke(app,(Object)null);
    }

}
