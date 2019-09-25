package com.felixklauke.kira.core;

import com.felixklauke.kira.core.mapper.DefaultMapperRegistry;
import com.felixklauke.kira.core.mapper.Mapper;
import com.felixklauke.kira.core.mapper.MapperRegistry;
import com.felixklauke.kira.core.meta.ModelMetaRegistry;
import com.felixklauke.kira.core.meta.ReflectionBasedModelMetaRegistry;
import com.google.common.base.Preconditions;

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
    Preconditions.checkNotNull(extraMappers, "Extra mappers should not be null");

    // Create dependencies
    ModelMetaRegistry modelMetaRegistry = new ReflectionBasedModelMetaRegistry();
    MapperRegistry mapperRegistry = new DefaultMapperRegistry(modelMetaRegistry);

    // Register mappers
    for (Mapper<?> extraMapper : extraMappers) {
      mapperRegistry.addMapper(extraMapper);
    }

    // Construct kira instance
    return DefaultKira.withMapperRegistry(mapperRegistry);
  }
}
