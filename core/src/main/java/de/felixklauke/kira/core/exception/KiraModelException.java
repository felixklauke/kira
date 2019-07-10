package de.felixklauke.kira.core.exception;

public class KiraModelException extends KiraException {

  public KiraModelException(String message) {
    super(message);
  }

  public KiraModelException(String message, Throwable cause) {
    super(message, cause);
  }
}
