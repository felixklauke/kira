package com.felixklauke.kira.exception;

import com.google.common.base.Preconditions;

public final class KiraPropertyException extends KiraException {
  private KiraPropertyException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Create an exception with a message and a root cause.
   *
   * @param message Message.
   * @param cause   Root cause.
   * @return Exception instance.
   */
  public static KiraPropertyException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraPropertyException(message, cause);
  }
}
