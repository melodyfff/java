package com.xinchen.java.context.camel;

/**
 * A {@link Service} which has all the lifecycle events and offers details about its current state.
 */
public interface ServiceStateful extends ServiceShutdownAble{

  /**
   * Returns the current status
   *
   * @return the current status
   */
  ServiceStatus getStatus();

  /**
   * Whether the service is started
   *
   * @return true if this service has been started
   */
  boolean isStarted();

  /**
   * Whether the service is starting
   *
   * @return true if this service is being started
   */
  boolean isStarting();

  /**
   * Whether the service is stopping
   *
   * @return true if this service is in the process of stopping
   */
  boolean isStopping();

  /**
   * Whether the service is stopped
   *
   * @return true if this service is stopped
   */
  boolean isStopped();

  /**
   * Whether the service is suspending
   *
   * @return true if this service is in the process of suspending
   */
  boolean isSuspending();

  /**
   * Helper methods so the service knows if it should keep running. Returns <tt>false</tt> if the service is being
   * stopped or is stopped.
   *
   * @return <tt>true</tt> if the service should continue to run.
   */
  boolean isRunAllowed();
}
