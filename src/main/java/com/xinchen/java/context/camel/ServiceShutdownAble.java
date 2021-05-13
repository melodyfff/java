package com.xinchen.java.context.camel;

/**
 * 具有ShutDown能力的{@link Service}
 *
 * <p/>
 *
 * 当{@link Context}处于STOPPED状态后的更细粒度的操作
 *
 * <p/>
 *
 * see {@link BaseService#shutdown()}
 *
 * <p/>
 *
 * 如：线程池的关闭
 */
public interface ServiceShutdownAble extends Service{

  /**
   * 服务状态设置为 SHUTDOWN 不能再被start
   *
   * @throws RuntimeServiceException is thrown if shutdown failed
   */
  void shutdown();
}
