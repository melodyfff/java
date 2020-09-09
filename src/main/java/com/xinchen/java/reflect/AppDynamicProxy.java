package com.xinchen.java.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * Dynamic Proxy for {@link App}
 *
 * @author xinchen
 * @version 1.0
 * @date 09/09/2020 16:08
 */
class AppDynamicProxy implements InvocationHandler {

    private Object delegate;

    private Object bind(Object delegate){
        this.delegate = delegate;

        return Proxy.newProxyInstance(
                // class loader
                Thread.currentThread().getContextClassLoader()
                // interface
                ,this.delegate.getClass().getInterfaces()
                // invocation handler
                ,this);
    }

    private Object bind(Class<?> delegateClass) throws IllegalAccessException, InstantiationException {
        this.delegate = delegateClass.newInstance();

        return Proxy.newProxyInstance(
                // class loader
                Thread.currentThread().getContextClassLoader()
                // interface
                ,delegateClass.getInterfaces()
                // invocation handler
                ,this);
    }

    public Object getDelegate() {
        return delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(delegate, args);
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        AppDynamicProxy dynamicProxy = new AppDynamicProxy();

        final TestInterface bind = (TestInterface) dynamicProxy.bind(App.class.newInstance());
        bind.call();

        final TestInterface bind1 = (TestInterface) dynamicProxy.bind(App.class);
        bind1.call();
    }
}
