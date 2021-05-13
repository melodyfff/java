package com.xinchen.java.context.camel;

import java.io.Serializable;

/**
 * Represents the status of a {@link Service} instance
 */
public enum ServiceStatus implements Serializable {
  Initializing,
  Initialized,
  Starting,
  Started,
  Stopping,
  Stopped,
  Suspending,
  Suspended;

  public boolean isStartable() {
    return this == Initialized || this == Stopped || this == Suspended;
  }

  public boolean isStoppable() {
    return this == Started || this == Suspended;
  }

  public boolean isSuspendable() {
    return this == Started;
  }

  public boolean isInitializing() {
    return this == Initializing;
  }

  public boolean isInitialized() {
    return this == Initialized;
  }

  public boolean isStarting() {
    return this == Starting;
  }

  public boolean isStarted() {
    return this == Started;
  }

  public boolean isStopping() {
    return this == Stopping;
  }

  public boolean isStopped() {
    return this == Stopped;
  }

  public boolean isSuspending() {
    return this == Suspending;
  }

  public boolean isSuspended() {
    return this == Suspended;
  }
}
