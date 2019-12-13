package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import javax.inject.Inject;

public final class CustomModelPropertyCodecFactory {
  private final ModelMetaRegistry metaRegistry;

  @Inject
  private CustomModelPropertyCodecFactory(
    ModelMetaRegistry metaRegistry
  ) {
    this.metaRegistry = metaRegistry;
  }

  public <PropertyT> CustomModelPropertyCodec<PropertyT> createCustomCodec(
    Class<PropertyT> propertyType
  ) {
    Preconditions.checkNotNull(propertyType);
    var meta = metaRegistry.metaForClass(propertyType);
    return CustomModelPropertyCodec.withMeta(meta);
  }
}
