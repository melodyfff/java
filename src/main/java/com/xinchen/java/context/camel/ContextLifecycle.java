package com.xinchen.java.context.camel;

import com.xinchen.java.context.camel.support.BaseService;

/**
 * Lifecycle API for {@link Context}.
 *
 *
 * <br/>
 * 以下周期其实和{@link Context}的生命周期对应
 *
 * @see Service
 * @see ServiceStateful
 * @see BaseService
 */
public interface ContextLifecycle extends AutoCloseable{
  /**
   * Builds the Context.
   */
  void build();

  /**
   * Initializes the Context.
   */
  void init();

  void start();

  /**
   * Suspends the Context.
   */
  void suspend();

  /**
   * Resumes the Context.
   */
  void resume();

  void stop();

  /**
   * Shutdown the CamelContext, which means it cannot be started again.
   * <p/>
   * See more details at the class-level javadoc at {@link Context}.
   */
  void shutdown();

  /**
   * Closes (Shutdown) the Context, which means it cannot be started again.
   *
   * @throws Exception is thrown if shutdown failed
   */
  void close() throws Exception;

  /**
   * Get the status of this Context
   *
   * @return the status
   */
  ServiceStatus getStatus();

  /**
   * Whether the Context is started
   *
   * @return true if this Context has been started
   */
  boolean isStarted();

  /**
   * Whether the Context is starting
   *
   * @return true if this Context is being started
   */
  boolean isStarting();

  /**
   * Whether the Context is stopping
   *
   * @return true if this Context is in the process of stopping
   */
  boolean isStopping();

  /**
   * Whether the Context is stopped
   *
   * @return true if this Context is stopped
   */
  boolean isStopped();

  /**
   * Whether the Context is suspending
   *
   * @return true if this Context is in the process of suspending
   */
  boolean isSuspending();

  /**
   * Whether the Context is suspended
   *
   * @return true if this Context is suspended
   */
  boolean isSuspended();

  /**
   * Helper methods so the Context knows if it should keep running. Returns <tt>false</tt> if the Context is
   * being stopped or is stopped.
   *
   * @return <tt>true</tt> if the Context should continue to run.
   */
  boolean isRunAllowed();
}
