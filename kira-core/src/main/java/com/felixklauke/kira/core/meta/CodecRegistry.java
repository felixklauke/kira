package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CodecRegistry {
  private final Map<Class<?>, Codec<?>> codecs;

  @Inject
  private CodecRegistry(
    Map<Class<?>, Codec<?>> codecs
  ) {
    this.codecs = codecs;
  }

  /**
   * Create a codec registry with a given amount of codecs.
   *
   * @param codecs Codecs.
   * @return Registry instance.
   */
  public static CodecRegistry withCodecs(
    Map<Class<?>, Codec<?>> codecs
  ) {
    Preconditions.checkNotNull(codecs);
    Map<Class<?>, Codec<?>> finalCodecs = Maps.newConcurrentMap();
    finalCodecs.putAll(codecs);
    return new CodecRegistry(finalCodecs);
  }

  /**
   * Retrieve codec for a specific type.
   *
   * @param fieldType   Field type.
   * @param <PropertyT> Generic field type.
   * @return Optional of a codec.
   */
  public <PropertyT> Optional<Codec<PropertyT>> codec(
    Class<PropertyT> fieldType
  ) {
    Preconditions.checkNotNull(fieldType);
    return Optional.ofNullable(
      (Codec<PropertyT>) codecs.get(fieldType)
    );
  }
}
