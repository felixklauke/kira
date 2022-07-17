package com.felixklauke.kira.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CodecRegistry {
  private final Map<Class<?>, Codec<?>> codecs;
  private final CodecFactory codecFactory;

  @Inject
  private CodecRegistry(
    Map<Class<?>, Codec<?>> codecs,
    CodecFactory codecFactory
  ) {
    this.codecs = codecs;
    this.codecFactory = codecFactory;
  }

  /**
   * Create a codec registry with a given amount of codecs.
   *
   * @param codecs Codecs.
   * @return Registry instance.
   */
  public static CodecRegistry withCodecs(
    Map<Class<?>, Codec<?>> codecs,
    CodecFactory codecFactory
  ) {
    Preconditions.checkNotNull(codecs);
    Map<Class<?>, Codec<?>> finalCodecs = Maps.newConcurrentMap();
    finalCodecs.putAll(codecs);
    return new CodecRegistry(finalCodecs, codecFactory);
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

  public <PropertyT> Codec<PropertyT> findCodecOrCreate(
    Class<PropertyT> fieldType
  ) {
    Preconditions.checkNotNull(fieldType);
    return (Codec<PropertyT>) codecs.computeIfAbsent(
      fieldType,
      key -> codecFactory.createCustomCodec(fieldType)
    );
  }
}
