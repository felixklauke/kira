package de.felixklauke.kira.core.io;

public interface KiraWriter {

  <T> void writeValue(String key, T value);
}
