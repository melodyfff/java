package com.xinchen.java.context.camel;


/**
 *
 */
public interface Context extends ContextLifecycle{

  /**
   * 返回当前Context的名字
   * @return name string
   */
  String getName();

}
