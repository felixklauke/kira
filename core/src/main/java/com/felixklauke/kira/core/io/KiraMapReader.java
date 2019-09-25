package com.felixklauke.kira.core.io;

import com.google.common.base.Preconditions;

import java.util.Map;

public class KiraMapReader extends KiraIO implements KiraReader {

  private KiraMapReader(Map<String, Object> data) {
    super(data);
  }

  public static KiraMapReader forData(Map<String, Object> data) {
    Preconditions.checkNotNull(data, "Data should not be null");

    return new KiraMapReader(data);
  }

  @Override
  public <T> T readValue(String key) {

    return (T) getData().get(key);
  }
}
