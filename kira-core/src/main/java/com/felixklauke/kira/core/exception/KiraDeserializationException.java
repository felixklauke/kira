package com.felixklauke.kira.core.exception;

import com.felixklauke.kira.core.Kira;
import com.google.common.base.Preconditions;

public final class KiraDeserializationException extends KiraException {
  private KiraDeserializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public KiraDeserializationException(String message) {
    super(message);
  }

  public static KiraDeserializationException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraDeserializationException(message, cause);
  }

  public static KiraDeserializationException withMessage(String message) {
    Preconditions.checkNotNull(message);
    return new KiraDeserializationException(message);
  }
}
