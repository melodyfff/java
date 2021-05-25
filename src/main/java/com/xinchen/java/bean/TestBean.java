package com.xinchen.java.bean;

import java.util.List;
import java.util.Map;

/**
 * 测试Bean（VO/DTO...）
 */
class TestBean {
  Long id;
  String name;
  List<String> testLists;
  Map<String, String> testMap;

  // 注意这里是private，表示不能被
  private Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTestLists() {
    return testLists;
  }

  public void setTestLists(List<String> testLists) {
    this.testLists = testLists;
  }

  public Map<String, String> getTestMap() {
    return testMap;
  }

  public void setTestMap(Map<String, String> testMap) {
    this.testMap = testMap;
  }

  @Override
  public String toString() {
    return "TestBean{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", testLists=" + testLists +
        ", testMap=" + testMap +
        '}';
  }
}
