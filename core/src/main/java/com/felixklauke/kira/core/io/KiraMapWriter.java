package com.felixklauke.kira.core.io;

import com.google.common.base.Preconditions;

import java.util.Map;

public class KiraMapWriter extends KiraIO implements KiraWriter {

  private KiraMapWriter(Map<String, Object> data) {
    super(data);
  }

  public static KiraMapWriter forData(Map<String, Object> data) {
    Preconditions.checkNotNull(data, "Data should not be null");

    return new KiraMapWriter(data);
  }

  @Override
  public <T> void writeValue(String key, T value) {

    getData().put(key, value);
  }
}
