package com.xinchen.java.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xinchen
 * @version 1.0
 * @date 09/09/2020 11:31
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    String value() default "ok";
}
