package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public class KiraModelPropertyException extends KiraModelException {

  private KiraModelPropertyException(String message, Throwable cause) {
    super(message, cause);
  }

  public static KiraModelPropertyException withMessageAndCause(String message, Throwable cause) {
    Preconditions.checkNotNull(message, "Message should not be null");
    Preconditions.checkNotNull(cause, "Cause should not be null");

    return new KiraModelPropertyException(message, cause);
  }
}
