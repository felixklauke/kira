package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public class KiraModelException extends KiraException {

  KiraModelException(String message) {
    super(message);
  }

  KiraModelException(String message, Throwable cause) {
    super(message, cause);
  }

  public static KiraModelException withMessageAndCause(String message, Throwable cause) {
    Preconditions.checkNotNull(message, "Message should not be null");
    Preconditions.checkNotNull(cause, "Cause should not be null");

    return new KiraModelException(message, cause);
  }

  public static KiraModelException withMessage(String message) {
    Preconditions.checkNotNull(message, "Message should not be null");

    return new KiraModelException(message);
  }
}
