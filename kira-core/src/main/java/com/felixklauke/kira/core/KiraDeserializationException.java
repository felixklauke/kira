package com.felixklauke.kira.core;

import com.google.common.base.Preconditions;

public final class KiraDeserializationException extends KiraException {
  private KiraDeserializationException(String message, Throwable cause) {
    super(message, cause);
  }

  private KiraDeserializationException(String message) {
    super(message);
  }

  /**
   * Create an exception with a message and a root cause.
   *
   * @param message Message.
   * @param cause   Root cause.
   * @return Exception instance.
   */
  public static KiraDeserializationException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraDeserializationException(message, cause);
  }

  /**
   * Create an exception with a message.
   *
   * @param message Message.
   * @return Exception instance.
   */
  public static KiraDeserializationException withMessage(String message) {
    Preconditions.checkNotNull(message);
    return new KiraDeserializationException(message);
  }
}
