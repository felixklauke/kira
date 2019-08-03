package com.felixklauke.kira.core.io;

import java.util.Map;

public class KiraMapReader extends KiraIO implements KiraReader {

  public KiraMapReader(Map<String, Object> data) {
    super(data);
  }

  @Override
  public <T> T readValue(String key) {

    return (T) getData().get(key);
  }
}
