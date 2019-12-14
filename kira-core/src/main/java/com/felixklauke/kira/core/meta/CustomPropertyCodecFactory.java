package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import javax.inject.Inject;

public final class CustomPropertyCodecFactory {
  private final ModelMetaRegistry metaRegistry;

  @Inject
  private CustomPropertyCodecFactory(
    ModelMetaRegistry metaRegistry
  ) {
    this.metaRegistry = metaRegistry;
  }

  /**
   * Create a custom codec.
   *
   * @param propertyType Type of the property.
   * @param <PropertyT> Generic type of the property.
   * @return Custom property codec.
   */
  public <PropertyT> CustomPropertyCodec<PropertyT> createCustomCodec(
    Class<PropertyT> propertyType
  ) {
    Preconditions.checkNotNull(propertyType);
    var meta = metaRegistry.forType(propertyType);
    return CustomPropertyCodec.withMeta(meta);
  }
}
