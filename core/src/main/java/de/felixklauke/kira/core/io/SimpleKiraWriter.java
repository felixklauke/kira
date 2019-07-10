package de.felixklauke.kira.core.io;

import java.util.HashMap;
import java.util.Map;

public class SimpleKiraWriter implements KiraWriter {

  private final Map<String, Object> data;

  public SimpleKiraWriter(Map<String, Object> data) {
    this.data = data;
  }

  @Override
  public <T> void writeValue(String key, T value) {

    data.put(key, value);
  }
}
