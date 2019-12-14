package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public final class KiraModelException extends KiraException {
  private KiraModelException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Create an exception with a message and a root cause.
   *
   * @param message Message.
   * @param cause   Root cause.
   * @return Exception instance.
   */
  public static KiraModelException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraModelException(message, cause);
  }
}
