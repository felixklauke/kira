package com.felixklauke.kira.core.io;

import java.util.Map;

public class KiraIO {

  private final Map<String, Object> data;

  public KiraIO(Map<String, Object> data) {
    this.data = data;
  }

  protected Map<String, Object> getData() {
    return data;
  }
}
