package com.felixklauke.kira.meta;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import javax.inject.Inject;
import javax.inject.Provider;

public final class PropertyFactory {
  private final CodecRegistry codecRegistry;
  private final Provider<CodecFactory> customCodecFactory;

  @Inject
  private PropertyFactory(
    CodecRegistry codecRegistry,
    Provider<CodecFactory> customCodecFactory
  ) {
    this.codecRegistry = codecRegistry;
    this.customCodecFactory = customCodecFactory;
  }

  /**
   * Create a property from a reflective java field.
   *
   * @param field       Field.
   * @param <PropertyT> Generic field type.
   * @return ModelProperty.
   */
  public <PropertyT> Property<PropertyT> createProperty(
    Field field
  ) {
    Preconditions.checkNotNull(field);
    var identifier = findFieldIdentifier(field);
    Codec<PropertyT> codec = findFieldCodec(field);
    return Property.of(field, identifier, codec);
  }

  private String findFieldIdentifier(Field field) {
    return field.getName();
  }

  private <PropertyT> Codec<PropertyT> findFieldCodec(
    Field field
  ) {
    Class<PropertyT> fieldType = (Class<PropertyT>) field.getType();
    var codecOptional = codecRegistry.codec(fieldType);
    return codecOptional.orElseGet(() -> createCustomCodec(fieldType));
  }

  private <PropertyT> Codec<PropertyT> createCustomCodec(
    Class<PropertyT> fieldType
  ) {
    return customCodecFactory.get().createCustomCodec(fieldType);
  }
}
