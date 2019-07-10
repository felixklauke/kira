package de.felixklauke.kira.core.io;

public interface KiraReader {

  <T> T readValue(String key);
}
