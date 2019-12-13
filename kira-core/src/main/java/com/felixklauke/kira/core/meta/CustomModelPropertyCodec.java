package com.felixklauke.kira.core.meta;

import com.felixklauke.kira.core.KiraDeserialization;
import com.felixklauke.kira.core.KiraSerialization;
import com.felixklauke.kira.core.exception.KiraDeserializationException;
import com.felixklauke.kira.core.exception.KiraSerializationException;
import com.google.common.base.Preconditions;
import java.util.Map;

public final class CustomModelPropertyCodec<PropertyT> implements ModelPropertyCodec<PropertyT> {
  private final ModelMeta<PropertyT> meta;

  private CustomModelPropertyCodec(
    ModelMeta<PropertyT> meta
  ) {
    this.meta = meta;
  }

  public static <PropertyT> CustomModelPropertyCodec<PropertyT> withMeta(
    ModelMeta<PropertyT> meta
  ) {
    Preconditions.checkNotNull(meta);
    return new CustomModelPropertyCodec<>(meta);
  }

  @Override
  public Object serialize(PropertyT value) throws KiraSerializationException {
    return KiraSerialization.of(value, meta).execute();
  }

  @Override
  public PropertyT deserialize(Object value)
    throws KiraDeserializationException {
    return KiraDeserialization.of((Map<String, Object>) value, meta).execute();
  }
}
