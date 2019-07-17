package de.felixklauke.kira.core;

import de.felixklauke.kira.core.mapper.Mapper;
import de.felixklauke.kira.core.mapper.MapperManager;
import de.felixklauke.kira.core.mapper.MapperManagerImpl;
import de.felixklauke.kira.core.meta.ModelMetaManager;
import de.felixklauke.kira.core.meta.ModelMetaManagerImpl;

import java.util.Collections;
import java.util.List;

public class KiraFactory {

  /**
   * Create a new kira instance.
   *
   * @return The kira instance.
   */
  public static Kira createKira() {

    return createKira(Collections.emptyList());
  }

  /**
   * Create a new kira instance with additional mappers.
   *
   * @param extraMappers The mappers.
   * @return The kira instance.
   */
  public static Kira createKira(List<Mapper<?>> extraMappers) {

    // Create dependencies
    ModelMetaManager modelMetaManager = new ModelMetaManagerImpl();
    MapperManager mapperManager = new MapperManagerImpl(modelMetaManager);

    // Register mappers
    for (Mapper<?> extraMapper : extraMappers) {
      mapperManager.addMapper(extraMapper);
    }

    // Construct kira instance
    return new SimpleKira(mapperManager);
  }
}
