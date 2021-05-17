package com.xinchen.java.context.camel.support;

import com.xinchen.java.context.camel.Context;
import com.xinchen.java.context.camel.RuntimeServiceException;
import com.xinchen.java.context.camel.ServiceStatus;
import com.xinchen.java.context.camel.ServiceSuspendable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个有用的基类，它确保服务仅被初始化一次，并提供一些帮助程序来查询其状态。
 * <p/>
 * 实现可以扩展此基类并实现{@link ServiceSuspendable} （如果它们支持暂停/恢复）。
 * <p/>
 * <b>重要提示：</b> 您应该覆盖以do开头的生命周期方法，例如 {@link #doStart()} ， {@link #doStop()}等，在其中实现您的逻辑。
 * 该方法{@link #start()} ， {@link #stop()} <b>不能被重写</b> 因为它们是内部使用，用于跟踪该服务的状态，并适当调用以安全的方式操作应该
 */
public abstract class BaseService {
  //------------------LifeCycle Status----------------

  protected static final byte NEW = 0;
  protected static final byte BUILT = 1;
  protected static final byte INITIALIZING = 2;
  protected static final byte INITIALIZED = 3;
  protected static final byte STARTING = 4;
  protected static final byte STARTED = 5;
  protected static final byte SUSPENDING = 6;
  protected static final byte SUSPENDED = 7;
  protected static final byte STOPPING = 8;
  protected static final byte STOPPED = 9;
  protected static final byte SHUTTING_DOWN = 10;
  protected static final byte SHUTDOWN = 11;
  protected static final byte FAILED = 12;

  private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

  /** 默认状态为 NEW */
  protected volatile byte status = NEW;
  protected final Object lock = new Object();

  public void build() {
    // 只有NEW状态才能build
    if (status == NEW) {
      synchronized (lock) {
        if (status == NEW) {
          LOG.trace("Building service: {}", this);
          // 生命周期管理
          try (AutoCloseable ignored = doLifecycleChange()) {
            doBuild();
          } catch (Exception e) {
            doFail(e);
          }
          // 改变状态为BUILD
          status = BUILT;
          LOG.trace("Built service: {}", this);
        }
      }
    }
  }

  public void init() {
    // 允许初始化状态范围，可以重复初始化
    if (status <= BUILT || status >= STOPPED) {
      synchronized (lock) {
        if (status == NEW) {
          if (status <= BUILT || status >= STOPPED) {
            build();
            LOG.trace("Initializing service: {}", this);
            try (AutoCloseable ignore = doLifecycleChange()) {
              status = INITIALIZING;
              doInit();
              status = INITIALIZED;
              LOG.trace("Initialized service: {}", this);
            } catch (Exception e) {
              LOG.trace("Error while initializing service: {}", this, e);
              // 处理异常，并且将状态设置为FAILED
              fail(e);
            }
          }
        }
      }
    }
  }

  //------------------LifeCycle Method----------------

  /**
   * <b>重要提示：</b> 您应该覆盖以do开头的生命周期方法，例如 {@link #doStart()} ， {@link #doStop()}等，在其中实现您的逻辑。
   * 该方法{@link #start()} ， {@link #stop()} <b>不能被重写</b> 因为它们是内部使用，用于跟踪该服务的状态，并适当调用以安全的方式操作应该
   */
  public void start() {
    synchronized (lock) {
      // 状态检查
      if (status == STARTED) {
        LOG.trace("Service: {} already started", this);
        return;
      }
      if (status == STARTING) {
        LOG.trace("Service: {} already starting", this);
        return;
      }
      // 确保进行过初始化
      init();
      try (final AutoCloseable ignore = doLifecycleChange()) {
        status = STARTING;
        LOG.trace("Starting service: {}", this);
        doStart();
        status = STARTED;
        LOG.trace("Started service: {}", this);
      } catch (Exception e) {
        // 尝试停止一些已经启动了的服务
        try {
          stop();
        } catch (Exception e2) {
          // ignore
          LOG.trace(
              "Error while stopping service after it failed to start: {}. This exception is ignored",
              this, e);
        }
        LOG.trace("Error while starting service: {}", this, e);
        fail(e);
      }
    }
  }

  /**
   * <b>重要提示：</b> 您应该覆盖以do开头的生命周期方法，例如 {@link #doStart()} ， {@link #doStop()}等，在其中实现您的逻辑。
   * 该方法{@link #start()} ， {@link #stop()} <b>不能被重写</b>
   */
  public void stop() {
    synchronized (lock) {
      // 状态检查
      if (status == STOPPING) {
        LOG.trace("Service: {} already stopping", this);
        return;
      }
      // STOPPED <=status <= SHUTDOWN
      if (status == STOPPED || status == SHUTTING_DOWN || status == SHUTDOWN) {
        LOG.trace("Service: {} already stopped", this);
        return;
      }
      if (status == FAILED) {
        LOG.trace("Service: {} failed and regarded as already stopped", this);
        return;
      }
      status = STOPPING;
      LOG.trace("Stopping service: {}", this);
      try (AutoCloseable ignore = doLifecycleChange()) {
        doStop();
        status = STOPPED;
        LOG.trace("Stopped: {} service", this);
      } catch (Exception e) {
        LOG.trace("Error while stopping service: {}", this, e);
        fail(e);
      }
    }
  }

  /**
   * <b>重要提示：</b> 您应该覆盖以do开头的生命周期方法，例如 {@link #doStart()} ， {@link #doStop()}等，在其中实现您的逻辑。
   * 该方法{@link #start()} ， {@link #stop()} <b>不能被重写</b>
   */
  public void suspend() {
    synchronized (lock) {
      if (status == SUSPENDING) {
        LOG.trace("Service: {} already suspending", this);
        return;
      }
      if (status == SUSPENDED) {
        LOG.trace("Service: {} already suspended", this);
        return;
      }
      status = SUSPENDING;
      LOG.trace("Suspending service: {}", this);
      try (AutoCloseable ignore = doLifecycleChange()) {
        doSuspend();
        status = SUSPENDED;
        LOG.trace("Suspended service: {}", this);
      } catch (Exception e) {
        LOG.trace("Error while suspending service: {}", this, e);
        fail(e);
      }
    }
  }

  /**
   * <b>重要提示：</b> 您应该覆盖以do开头的生命周期方法，例如 {@link #doStart()} ， {@link #doStop()}等，在其中实现您的逻辑。
   * 该方法{@link #start()} ， {@link #stop()} <b>不能被重写</b>
   */
  public void resume() {
    synchronized (lock) {
      if (status != SUSPENDED) {
        LOG.trace("Service is not suspended: {}", this);
        return;
      }
      status = STARTING;
      LOG.trace("Resuming service: {}", this);
      try (AutoCloseable ignore = doLifecycleChange()) {
        doResume();
        status = STARTED;
        LOG.trace("Resumed service: {}", this);
      } catch (Exception e) {
        LOG.trace("Error while resuming service: {}", this, e);
        fail(e);
      }
    }
  }

  /**
   * <b>重要提示：</b> 您应该覆盖以do开头的生命周期方法，例如 {@link #doStart()} ， {@link #doStop()}等，在其中实现您的逻辑。
   * 该方法{@link #start()} ， {@link #stop()} <b>不能被重写</b>
   */
  public void shutdown() {
    synchronized (lock) {
      if (status == SHUTDOWN) {
        LOG.trace("Service: {} already shutdown", this);
        return;
      }
      if (status == SHUTTING_DOWN) {
        LOG.trace("Service: {} already shutting down", this);
        return;
      }
      stop();
      status = SHUTDOWN;
      LOG.trace("Shutting down service: {}", this);
      // 与stop的区别，更细粒度的处理停止后的操作
      try (AutoCloseable ignore = doLifecycleChange()) {
        doShutdown();
        LOG.trace("Shutdown service: {}", this);
        status = SHUTDOWN;
      } catch (Exception e) {
        LOG.trace("Error shutting down service: {}", this, e);
        fail(e);
      }
    }
  }


  //------------------LifeCycle Status Method----------------


  public ServiceStatus getStatus() {
    // 默认SOPPED
    switch (status){
      case INITIALIZING:
        return ServiceStatus.Initializing;
      case INITIALIZED:
        return ServiceStatus.Initialized;
      case STARTING:
        return ServiceStatus.Starting;
      case STARTED:
        return ServiceStatus.Started;
      case SUSPENDING:
        return ServiceStatus.Suspending;
      case SUSPENDED:
        return ServiceStatus.Suspended;
      case STOPPING:
        return ServiceStatus.Stopping;
      default:
        return ServiceStatus.Stopped;
    }
  }

  public boolean isNew() {
    return status == NEW;
  }

  public boolean isBuild() {
    return status == BUILT;
  }

  public boolean isInit() {
    return status == INITIALIZED;
  }

  public boolean isStarted() {
    return status == STARTED;
  }

  public boolean isStarting() {
    return status == STARTING;
  }

  public boolean isStopping() {
    return status == STOPPING;
  }

  public boolean isStopped() {
    return status < STARTING || status >= STOPPED;
  }

  public boolean isSuspending() {
    return status == SUSPENDING;
  }

  public boolean isSuspended() {
    return status == SUSPENDED;
  }

  public boolean isRunAllowed() {
    return status >= STARTING && status <= SUSPENDED;
  }

  public boolean isShutdown() {
    return status == SHUTDOWN;
  }

  /**
   * Is the service in progress of being stopped or already stopped
   */
  public boolean isStoppingOrStopped() {
    return status < STARTING || status > SUSPENDED;
  }

  /**
   * Is the service in progress of being suspended or already suspended
   */
  public boolean isSuspendingOrSuspended() {
    return status == SUSPENDING || status == SUSPENDED;
  }

  /**
   * Is the service in progress of being suspended or already suspended
   */
  public boolean isStartingOrStarted() {
    return status == STARTING || status == STARTED;
  }

  protected void fail(Exception e) {
    try {
      doFail(e);
    } finally {
      status = FAILED;
    }
  }


  //------------------LifeCycle Override Method----------------


  protected void doBuild() {
  }

  /**
   * 初始化服务。 该方法在启动之前将仅被调用一次。
   */
  protected void doInit() {
  }

  /**
   * 实现会覆盖此方法，以支持自定义的开始/停止。
   *<p/>
   *
   * <b>重要：</b>有关更多详细信息，请参见{@link #doStop()} 。
   *
   * <p/>
   * @see #doStop()
   */
  protected void doStart() {
  }

  protected void doSuspend() {
  }

  protected void doResume() {
  }

  /**
   * 实现会覆盖此方法，以支持自定义的开始/停止。
   *<p/>
   *
   * <b>重要提示：</b>
   * 服务停止时，将调用此{@link #doStop()}方法。
   *
   * <p/>
   *
   * 如果服务仍处于未初始化状态（例如尚未启动），则也会调用此方法。 <b>始终</b>调用该方法停止服务，再{@link Context}中
   *
   * <p/>
   * @see #doStop()
   */
  protected void doStop() {
  }

  protected void doShutdown() {
  }

  protected void doFail(Exception e) {
    throw RuntimeServiceException.wrapRuntimeException(e);
  }

  protected AutoCloseable doLifecycleChange() {
    return null;
  }
}
