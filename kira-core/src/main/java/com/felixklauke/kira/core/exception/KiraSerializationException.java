package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public final class KiraSerializationException extends KiraException {
  private KiraSerializationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Create an exception with a message and a root cause.
   *
   * @param message Message.
   * @param cause Root cause.
   * @return Exception instance.
   */
  public static KiraSerializationException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraSerializationException(message, cause);
  }
}
