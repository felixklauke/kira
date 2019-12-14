package com.felixklauke.kira.core.meta;

import com.felixklauke.kira.core.codec.Codec;
import com.felixklauke.kira.core.exception.KiraCodecException;
import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraPropertyException;
import com.google.common.base.Preconditions;
import java.lang.reflect.Field;

public final class Property<PropertyT> {
  private final Field field;
  private final String identifier;
  private final Codec<PropertyT> codec;

  private Property(
    Field field, String identifier,
    Codec<PropertyT> codec
  ) {
    this.field = field;
    this.identifier = identifier;
    this.codec = codec;
  }

  /**
   * Factory method to create a property of its basic components.
   *
   * @param field       Reflective field.
   * @param identifier  Property identifier.
   * @param codec       Property codec.
   * @param <PropertyT> Generic property type.
   * @return Property.
   */
  public static <PropertyT> Property<PropertyT> of(
    Field field,
    String identifier,
    Codec<PropertyT> codec
  ) {
    Preconditions.checkNotNull(field);
    Preconditions.checkNotNull(identifier);
    Preconditions.checkNotNull(codec);
    return new Property<>(field, identifier, codec);
  }

  /**
   * The serialization identifier of the property.
   *
   * @return Identifier.
   */
  public String identifier() {
    return identifier;
  }

  /**
   * Serialize the given property instance.
   *
   * @param value Property instance.
   * @return Serialized data.
   * @throws KiraPropertyException If the serialization fails.
   */
  public Object serialize(PropertyT value) throws KiraPropertyException {
    try {
      return codec.serialize(value);
    } catch (KiraCodecException e) {
      throw KiraPropertyException
        .withMessageAndCause("Couldn't serialized property", e);
    }
  }

  /**
   * Deserialize the given data.
   *
   * @param value Serialized data.
   * @return Property instance.
   * @throws KiraPropertyException If the deserialization fails.
   */
  public PropertyT deserialize(
    Object value
  ) throws KiraPropertyException {
    try {
      return codec.deserialize(value);
    } catch (KiraCodecException e) {
      throw KiraPropertyException
        .withMessageAndCause("Couldn't deserialize property", e);
    }
  }

  /**
   * Extract the property value out of the given model instance.
   *
   * @param value    Model instance.
   * @param <ModelT> Generic model type.
   * @return Property value.
   * @throws KiraModelException If the model can't access the value.
   */
  public <ModelT> PropertyT extractValue(
    ModelT value
  ) throws KiraModelException {
    try {
      ensureFieldAccess(value);
      return (PropertyT) field.get(value);
    } catch (IllegalAccessException e) {
      throw KiraModelException
        .withMessageAndCause("Can't access field value", e);
    }
  }

  private void ensureFieldAccess(Object target) {
    if (!field.canAccess(target)) {
      field.setAccessible(true);
    }
  }
}
