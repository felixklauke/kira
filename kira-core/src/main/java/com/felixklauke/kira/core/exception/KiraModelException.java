package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public final class KiraModelException extends KiraException {
  private KiraModelException(String message, Throwable cause) {
    super(message, cause);
  }

  public static KiraModelException withMessageAndCause(
    String message,
    Throwable cause
  ) {
    Preconditions.checkNotNull(message);
    Preconditions.checkNotNull(cause);
    return new KiraModelException(message, cause);
  }
}
