package annotation.example;

import java.lang.annotation.*;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/1/9 23:11
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo{
    String author() default "xinchen";
    String date();
    int revision() default 1;
    String comments();
}
