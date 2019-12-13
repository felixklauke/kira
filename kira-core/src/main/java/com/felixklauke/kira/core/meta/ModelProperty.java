package com.felixklauke.kira.core.meta;

import com.felixklauke.kira.core.exception.KiraDeserializationException;
import com.felixklauke.kira.core.exception.KiraException;
import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraSerializationException;
import com.google.common.base.Preconditions;
import java.lang.reflect.Field;

public final class ModelProperty<PropertyT> {
  private final Field field;
  private final String identifier;
  private final ModelPropertyCodec<PropertyT> codec;

  public ModelProperty(
    Field field, String identifier,
    ModelPropertyCodec<PropertyT> codec
  ) {
    this.field = field;
    this.identifier = identifier;
    this.codec = codec;
  }

  public static <PropertyT> ModelProperty<PropertyT> of(
    Field field,
    String identifier,
    ModelPropertyCodec<PropertyT> codec
  ) {
    Preconditions.checkNotNull(field);
    Preconditions.checkNotNull(identifier);
    Preconditions.checkNotNull(codec);
    return new ModelProperty<>(field, identifier, codec);
  }

  public String identifier() {
    return identifier;
  }

  public Object serialize(PropertyT value) throws KiraSerializationException {
    return codec.serialize(value);
  }

  public PropertyT deserialize(Object value)
    throws KiraDeserializationException {
    return codec.deserialize(value);
  }

  public <ModelT> Object extractValue(ModelT value) throws KiraModelException {
    try {
      ensureFieldAccess(value);
      return field.get(value);
    } catch (IllegalAccessException e) {
      throw KiraModelException.withMessageAndCause("Can't access field value", e);
    }
  }

  private void ensureFieldAccess(Object target) {
    if (!field.canAccess(target)) {
      field.setAccessible(true);
    }
  }
}
