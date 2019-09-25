package com.felixklauke.kira.core.exception;

import com.google.common.base.Preconditions;

public class KiraModelInvalidGenericsException extends KiraModelException {

  private KiraModelInvalidGenericsException(String message) {
    super(message);
  }

  public static KiraModelInvalidGenericsException withMessage(String message) {
    Preconditions.checkNotNull(message, "Message should not be null");

    return new KiraModelInvalidGenericsException(message);
  }
}
