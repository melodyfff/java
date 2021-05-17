package com.xinchen.java.context.camel;

/**
 *
 */
public abstract class AbstractContext implements Context{

  @Override
  public String getName() {
    // 这里的名字可以使用策略加载
    return "AbstractContext";
  }
}
