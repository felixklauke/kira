package com.felixklauke.kira.core;

import com.google.common.base.Preconditions;

public final class KiraCodecException extends KiraException {
  private KiraCodecException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Create an exception with a message and a root cause.
   *
   * @param message Message.
   * @param cause   Root cause.
   * @return Exception instance.
   */
  public static KiraCodecException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraCodecException(message, cause);
  }
}
