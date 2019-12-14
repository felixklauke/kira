package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class PropertyCodecRegistry {
  private final Map<Class<?>, PropertyCodec<?>> codecs;

  @Inject
  private PropertyCodecRegistry(
    Map<Class<?>, PropertyCodec<?>> codecs
  ) {
    this.codecs = codecs;
  }

  /**
   * Create a codec registry with a given amount of codecs.
   *
   * @param codecs Codecs.
   * @return Registry instance.
   */
  public static PropertyCodecRegistry withCodecs(
    Map<Class<?>, PropertyCodec<?>> codecs
  ) {
    Preconditions.checkNotNull(codecs);
    Map<Class<?>, PropertyCodec<?>> finalCodecs = Maps.newConcurrentMap();
    finalCodecs.putAll(codecs);
    return new PropertyCodecRegistry(finalCodecs);
  }

  /**
   * Retrieve codec for a specific type.
   *
   * @param fieldType Field type.
   * @param <PropertyT> Generic field type.
   * @return Optional of a codec.
   */
  public <PropertyT> Optional<PropertyCodec<PropertyT>> codec(
    Class<PropertyT> fieldType
  ) {
    Preconditions.checkNotNull(fieldType);
    return Optional.ofNullable(
      (PropertyCodec<PropertyT>) codecs.get(fieldType)
    );
  }
}
