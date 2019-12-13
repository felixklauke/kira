package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public final class KiraSerializationException extends KiraException {
  private KiraSerializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public static KiraSerializationException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraSerializationException(message, cause);
  }
}
