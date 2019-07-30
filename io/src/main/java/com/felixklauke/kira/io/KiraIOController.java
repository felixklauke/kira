package com.felixklauke.kira.io;

import com.felixklauke.kira.core.Kira;
import com.felixklauke.kira.core.KiraFactory;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * A Kira IO Controller can write and read out of / into files, streams etc.
 */
public abstract class KiraIOController {

  /**
   * The kira instance.
   */
  private final Kira kira;

  public KiraIOController(Kira kira) {
    Objects.requireNonNull(kira, "Kira should not be null");

    this.kira = kira;
  }

  public KiraIOController() {
    this(KiraFactory.createKira());
  }

  /**
   * Read map from path.
   *
   * @param path The path.
   * @return The map.
   */
  public abstract Map<String, Object> read(Path path);

  /**
   * Write map to path.
   *
   * @param path The path.
   * @param data The data.
   */
  public abstract void write(Path path, Map<String, Object> data);
}
