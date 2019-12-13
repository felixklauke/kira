package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ModelPropertyCodecRegistry {
  private final Map<Class<?>, ModelPropertyCodec<?>> codecs;

  @Inject
  private ModelPropertyCodecRegistry(
    Map<Class<?>, ModelPropertyCodec<?>> codecs
  ) {
    this.codecs = codecs;
  }

  public static ModelPropertyCodecRegistry withCodecs(
    Map<Class<?>, ModelPropertyCodec<?>> codecs
  ) {
    Preconditions.checkNotNull(codecs);
    Map<Class<?>, ModelPropertyCodec<?>> finalCodecs = Maps.newConcurrentMap();
    finalCodecs.putAll(codecs);
    return new ModelPropertyCodecRegistry(finalCodecs);
  }

  public <PropertyT> Optional<ModelPropertyCodec<PropertyT>> codec(
    Class<PropertyT> fieldType
  ) {
    Preconditions.checkNotNull(fieldType);
    return Optional.ofNullable(
      (ModelPropertyCodec<PropertyT>) codecs.get(fieldType)
    );
  }
}
