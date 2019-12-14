package com.felixklauke.kira.core;

public class KiraException extends Exception {
  protected KiraException(String message, Throwable cause) {
    super(message, cause);
  }

  protected KiraException(String message) {
    super(message);
  }
}
