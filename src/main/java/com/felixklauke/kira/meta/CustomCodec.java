package com.felixklauke.kira.meta;

import com.felixklauke.kira.KiraDeserialization;
import com.felixklauke.kira.KiraSerialization;
import com.felixklauke.kira.exception.KiraCodecException;
import com.felixklauke.kira.exception.KiraDeserializationException;
import com.felixklauke.kira.exception.KiraSerializationException;
import com.google.common.base.Preconditions;
import java.util.Map;

public final class CustomCodec<PropertyT> implements
  Codec<PropertyT> {
  private final ModelMeta<PropertyT> meta;

  private CustomCodec(
    ModelMeta<PropertyT> meta
  ) {
    this.meta = meta;
  }

  public static <PropertyT> CustomCodec<PropertyT> withMeta(
    ModelMeta<PropertyT> meta
  ) {
    Preconditions.checkNotNull(meta);
    return new CustomCodec<>(meta);
  }

  @Override
  public Object serialize(PropertyT value) throws KiraCodecException {
    try {
      return KiraSerialization.of(value, meta).execute();
    } catch (KiraSerializationException e) {
      throw KiraCodecException
        .withMessageAndCause("Couldn't serialize model", e);
    }
  }

  @Override
  public PropertyT deserialize(Object value) throws KiraCodecException {
    try {
      var typedValue = (Map<String, Object>) value;
      return KiraDeserialization.of(typedValue, meta).execute();
    } catch (KiraDeserializationException e) {
      throw KiraCodecException
        .withMessageAndCause("Couldn't deserialize model", e);
    }
  }
}
