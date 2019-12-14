package com.felixklauke.kira.core.meta;

import com.felixklauke.kira.core.exception.KiraDeserializationException;
import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraSerializationException;
import com.google.common.base.Preconditions;
import java.lang.reflect.Field;

public final class Property<PropertyT> {
  private final Field field;
  private final String identifier;
  private final PropertyCodec<PropertyT> codec;

  private Property(
    Field field, String identifier,
    PropertyCodec<PropertyT> codec
  ) {
    this.field = field;
    this.identifier = identifier;
    this.codec = codec;
  }

  /**
   * Factory method to create a property of its basic components.
   *
   * @param field Reflective field.
   * @param identifier Property identifier.
   * @param codec Property codec.
   * @param <PropertyT> Generic property type.
   * @return Property.
   */
  public static <PropertyT> Property<PropertyT> of(
    Field field,
    String identifier,
    PropertyCodec<PropertyT> codec
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
   * @throws KiraSerializationException If the serialization fails.
   */
  public Object serialize(PropertyT value) throws KiraSerializationException {
    return codec.serialize(value);
  }

  /**
   * Deserialize the given data.
   *
   * @param value Serialized data.
   * @return Property instance.
   * @throws KiraDeserializationException If the deserialization fails.
   */
  public PropertyT deserialize(
    Object value
  ) throws KiraDeserializationException {
    return codec.deserialize(value);
  }

  /**
   * Extract the property value out of the given model instance.
   *
   * @param value Model instance.
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
