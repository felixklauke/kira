package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public class KiraModelInstantiationException extends KiraModelException {

  private KiraModelInstantiationException(String message, Throwable cause) {
    super(message, cause);
  }

  public static KiraModelInstantiationException withMessageAndCause(String message, Throwable cause) {
    Preconditions.checkNotNull(message, "Message should not be null");
    Preconditions.checkNotNull(cause, "Cause should not be null");

    return new KiraModelInstantiationException(message, cause);
  }
}
