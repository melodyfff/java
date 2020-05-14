package com.xinchen.java.spi;

/**
 *
 *
 * @author xinchen
 * @version 1.0
 * @date 13/05/2020 15:46
 */
public class ServiceImplB implements IService{
    @Override
    public void say(String word) {
        System.out.println(this.getClass().toString() + " say: " + word);
    }
}
