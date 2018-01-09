package annotation.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/1/9 23:17
 */
public class AnnotationParsing {

        public static void main (String[] args){
        try {
            for (Method method : AnnotationParsing.class
                    .getClassLoader()
                    .loadClass(("annotation.example.AnnotationExample"))
                    .getMethods()) {
                // checks if MethodInfo annotation is present for the method
                if (method.isAnnotationPresent(MethodInfo.class)) {
                    try {
                        // iterates all the annotations available in the method
                        for (Annotation anno : method.getDeclaredAnnotations()) {
                            System.out.println("Annotation in Method " + method +  " : " + anno);
                        }
                        MethodInfo methodAnno = method.getAnnotation(MethodInfo.class);
                        if (methodAnno.revision() == 1) {
                            System.out.println("Method with revision no 1 = " + method);
                        }

                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    }