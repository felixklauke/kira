package de.felixklauke.kira.core.exception;

public class KiraException extends Exception {

  public KiraException(String message) {
    super(message);
  }

  public KiraException(String message, Throwable cause) {
    super(message, cause);
  }
}
