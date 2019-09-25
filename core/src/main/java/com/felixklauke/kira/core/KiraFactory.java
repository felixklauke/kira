package com.felixklauke.kira.core;

import com.felixklauke.kira.core.mapper.Mapper;
import com.felixklauke.kira.core.mapper.MapperRegistry;
import com.felixklauke.kira.core.mapper.DefaultMapperRegistry;
import com.felixklauke.kira.core.meta.ModelMetaRegistry;
import com.felixklauke.kira.core.meta.ReflectionBasedModelMetaRegistry;

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
    ModelMetaRegistry modelMetaRegistry = new ReflectionBasedModelMetaRegistry();
    MapperRegistry mapperRegistry = new DefaultMapperRegistry(modelMetaRegistry);

    // Register mappers
    for (Mapper<?> extraMapper : extraMappers) {
      mapperRegistry.addMapper(extraMapper);
    }

    // Construct kira instance
    return new SimpleKira(mapperRegistry);
  }
}
