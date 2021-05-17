package com.xinchen.java.context.camel;

/**
 * 支持{@link Service}能提供suspend与resume的功能
 */
public interface ServiceSuspendable extends Service{

  /**
   * Suspends the service.
   *
   * @throws RuntimeServiceException is thrown if suspending failed
   */
  void suspend();

  /**
   * Resumes the service.
   *
   * @throws RuntimeServiceException is thrown if resuming failed
   */
  void resume();

  /**
   * Tests whether the service is suspended or not.
   *
   * @return <tt>true</tt> if suspended
   */
  boolean isSuspended();
}
