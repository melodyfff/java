package com.xinchen.java.context.camel;

import static org.junit.jupiter.api.Assertions.*;

import com.xinchen.java.context.camel.support.BaseService;
import org.junit.jupiter.api.Test;

/**
 *
 */
class BaseServiceTest {

  @Test
  void testService(){
    TestService testService = new TestService();

    testService.start();
    assertEquals(ServiceStatus.Started,testService.getStatus());

    testService.suspend();
    assertEquals(ServiceStatus.Suspended,testService.getStatus());

    testService.resume();
    assertEquals(ServiceStatus.Started,testService.getStatus());

    testService.shutdown();
    assertEquals(ServiceStatus.Stopped,testService.getStatus());

    testService.stop();
    assertEquals(ServiceStatus.Stopped,testService.getStatus());
  }

  static class TestService extends BaseService {}
}