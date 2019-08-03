package com.felixklauke.kira.core.io;

import java.util.Map;

public class KiraMapWriter extends KiraIO implements KiraWriter {

  public KiraMapWriter(Map<String, Object> data) {
    super(data);
  }

  @Override
  public <T> void writeValue(String key, T value) {

    getData().put(key, value);
  }
}
