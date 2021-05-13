package com.xinchen.java.context.camel;

/**
 * Lifecycle API for {@link Context}.
 */
public interface ContextLifecycle {
  void start();
  void stop();
}
