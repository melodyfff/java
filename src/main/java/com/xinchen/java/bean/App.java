package com.xinchen.java.bean;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
class App {

  public static void main(String[] args) throws IntrospectionException {
    TestBean testBean = new TestBean();

    Map<String, Object> beanDefine = new HashMap<>();
    beanDefine.put("id", "1L");
    beanDefine.put("name", "test");
    beanDefine.put("testLists", new ArrayList<>());
    beanDefine.put("testMap", new HashMap<>());


    BeanInfo beanInfo = Introspector.getBeanInfo(TestBean.class);

    // bean info
    BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
    // property info
    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    // method info
    MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();

    System.out.println(beanDescriptor);
    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
      System.out.println(propertyDescriptor);
    }
    System.out.println("\n");
    for (MethodDescriptor methodDescriptor : methodDescriptors) {
      System.out.println(methodDescriptor);
    }


    beanDefine.forEach((property,value)->{
      try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(property, TestBean.class);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        writeMethod.invoke(testBean, value);
      } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    });

    System.out.println(testBean);
  }
}
