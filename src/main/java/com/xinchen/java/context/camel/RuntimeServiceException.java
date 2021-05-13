package com.xinchen.java.context.camel;

/**
 * unchecked exceptions.
 */
public class RuntimeServiceException extends RuntimeException{

  public RuntimeServiceException() {
  }

  public RuntimeServiceException(String message) {
    super(message);
  }

  public RuntimeServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public RuntimeServiceException(Throwable cause) {
    super(cause);
  }

  public static RuntimeServiceException wrapRuntimeServiceException(Throwable e) {
    if (e instanceof RuntimeServiceException) {
      // don't double wrap
      return (RuntimeServiceException) e;
    } else {
      return new RuntimeServiceException(e);
    }
  }

  public static RuntimeException wrapRuntimeException(Throwable e) {
    if (e instanceof RuntimeException){
      // 直接抛出
      throw (RuntimeException) e;
    } else {
      return new RuntimeServiceException(e);
    }
  }
}
