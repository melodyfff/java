package com.xinchen.java.reflect;

/**
 *
 * Static Proxy for {@link App}
 *
 * @author xinchen
 * @version 1.0
 * @date 09/09/2020 15:57
 */
class AppStaticProxy implements TestInterface{
    private final TestInterface instance;

    private AppStaticProxy(TestInterface instance) {
        this.instance = instance;
    }

    @Override
    public void call() {
        // do some before
        instance.call();
        // do some after
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        // App can be provide by factory or SPI load.
        TestInterface instanceProxy = new AppStaticProxy(App.class.newInstance());
        instanceProxy.call();
    }
}
