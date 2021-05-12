package com.xinchen.java.context.camel;


/**
 *
 */
interface Context extends ContextLifecycle{

  /**
   * 返回当前Context的名字
   * @return name string
   */
  String getName();

}
