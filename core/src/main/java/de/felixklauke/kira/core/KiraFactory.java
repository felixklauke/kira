package de.felixklauke.kira.core;

import de.felixklauke.kira.core.mapper.MapperManager;
import de.felixklauke.kira.core.mapper.MapperManagerImpl;
import de.felixklauke.kira.core.meta.ModelMetaManager;
import de.felixklauke.kira.core.meta.ModelMetaManagerImpl;

public class KiraFactory {

  /**
   * Create a new kira instance.
   *
   * @return The kira instance.
   */
  public static Kira createKira() {

    // Create dependencies
    ModelMetaManager modelMetaManager = new ModelMetaManagerImpl();
    MapperManager mapperManager = new MapperManagerImpl(modelMetaManager);

    // Construct kira instance
    return new SimpleKira(mapperManager);
  }
}
