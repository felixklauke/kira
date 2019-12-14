package com.felixklauke.kira.core.codec;

import com.felixklauke.kira.core.meta.ModelMetaRegistry;
import com.google.common.base.Preconditions;
import javax.inject.Inject;

public final class CodecFactory {
  private final ModelMetaRegistry metaRegistry;

  @Inject
  private CodecFactory(
    ModelMetaRegistry metaRegistry
  ) {
    this.metaRegistry = metaRegistry;
  }

  /**
   * Create a custom codec.
   *
   * @param propertyType Type of the property.
   * @param <PropertyT>  Generic type of the property.
   * @return Custom property codec.
   */
  public <PropertyT> CustomCodec<PropertyT> createCustomCodec(
    Class<PropertyT> propertyType
  ) {
    Preconditions.checkNotNull(propertyType);
    var meta = metaRegistry.forType(propertyType);
    return CustomCodec.withMeta(meta);
  }
}
