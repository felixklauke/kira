package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import javax.inject.Inject;
import javax.inject.Provider;

public final class ModelPropertyFactory {
  private final ModelPropertyCodecRegistry codecRegistry;
  private final Provider<CustomModelPropertyCodecFactory> customModelPropertyCodecFactory;

  @Inject
  private ModelPropertyFactory(
    ModelPropertyCodecRegistry codecRegistry,
    Provider<CustomModelPropertyCodecFactory> customModelPropertyCodecFactory
  ) {
    this.codecRegistry = codecRegistry;
    this.customModelPropertyCodecFactory = customModelPropertyCodecFactory;
  }

  public <PropertyT> ModelProperty<PropertyT> createProperty(
    Field field
  ) {
    Preconditions.checkNotNull(field);
    var identifier = findFieldIdentifier(field);
    ModelPropertyCodec<PropertyT> codec = findFieldCodec(field);
    return ModelProperty.of(field, identifier, codec);
  }

  private String findFieldIdentifier(Field field) {
    return field.getName();
  }

  private <PropertyT> ModelPropertyCodec<PropertyT> findFieldCodec(
    Field field
  ) {
    Class<PropertyT> fieldType = (Class<PropertyT>) field.getType();
    var codecOptional = codecRegistry.codec(fieldType);
    return codecOptional.orElseGet(() -> createCustomCodec(fieldType));
  }

  private <PropertyT> ModelPropertyCodec<PropertyT> createCustomCodec(
    Class<PropertyT> fieldType
  ) {
    return customModelPropertyCodecFactory.get().createCustomCodec(fieldType);
  }
}
