package de.felixklauke.kira.core.io;

import java.util.Map;

public class SimpleKiraWriter extends KiraIO implements KiraWriter {

  public SimpleKiraWriter(Map<String, Object> data) {
    super(data);
  }

  @Override
  public <T> void writeValue(String key, T value) {

    getData().put(key, value);
  }
}
