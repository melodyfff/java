package com.xinchen.java.context.camel;

import java.io.IOException;

/**
 * Represents the core lifecycle API for services which can be initialized, started and stopped
 */
public interface Service extends AutoCloseable{

  /**
   * Optional build phase which is executed by frameworks that supports pre-building projects (pre-compile) which
   * allows special optimizations.
   *
   * @throws RuntimeServiceException is thrown if build failed
   */
  default void build(){

  }

  /**
   * Initialize the service
   *
   * @throws RuntimeServiceException is thrown if initialization failed
   */
  default void init(){

  }

  /**
   * Starts the service
   *
   * @throws RuntimeServiceException is thrown if starting failed
   */
  void start();

  /**
   * Stops the service
   *
   * @throws RuntimeServiceException is thrown if stopping failed
   */
  void stop();

  /**
   *  Delegates to {@link Service#stop()} so it can be used in try-with-resources expression.
   *
   * @throws IOException per contract of {@link AutoCloseable} if {@link Service#stop()} fails
   */
  @Override
  default void close() throws IOException {
    try {
      stop();
    } catch (RuntimeException e){
      throw e;
    } catch (Exception e){
      // trans to IOException
      throw new IOException(e);
    }
  }
}
