package de.felixklauke.kira.core.io;

import java.util.Map;

public class SimpleKiraReader implements KiraReader {

  private final Map<String, Object> data;

  public SimpleKiraReader(Map<String, Object> data) {
    this.data = data;
  }

  @Override
  public <T> T readValue(String key) {

    return (T) data.get(key);
  }
}
