package com.felixklauke.kira.core;

import com.felixklauke.kira.core.mapper.Mapper;
import com.felixklauke.kira.core.mapper.MapperManager;
import com.felixklauke.kira.core.mapper.MapperManagerImpl;
import com.felixklauke.kira.core.meta.ModelMetaRepository;
import com.felixklauke.kira.core.meta.ReflectionBasedModelMetaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    Objects.requireNonNull(extraMappers, "Extra mappers list cannot be null.");

    // Create dependencies
    ModelMetaRepository modelMetaRepository = new ReflectionBasedModelMetaRepository();
    MapperManager mapperManager = new MapperManagerImpl(modelMetaRepository);

    // Register mappers
    for (Mapper<?> extraMapper : extraMappers) {
      mapperManager.addMapper(extraMapper);
    }

    // Construct kira instance
    return new SimpleKira(mapperManager);
  }
}
