package de.felixklauke.kira.core.io;

import java.util.Map;

public class SimpleKiraReader extends KiraIO implements KiraReader {

  public SimpleKiraReader(Map<String, Object> data) {
    super(data);
  }

  @Override
  public <T> T readValue(String key) {

    return (T) getData().get(key);
  }
}
