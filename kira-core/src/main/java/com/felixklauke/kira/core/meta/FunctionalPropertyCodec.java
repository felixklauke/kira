package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import java.util.function.Function;

public final class FunctionalPropertyCodec<PropertyT> implements ModelPropertyCodec<PropertyT> {
  private final Function<PropertyT, Object> serializationFunction;
  private final Function<Object, PropertyT> deserializationFunction;

  private FunctionalPropertyCodec(
    Function<PropertyT, Object> serializationFunction,
    Function<Object, PropertyT> deserializationFunction
  ) {
    this.serializationFunction = serializationFunction;
    this.deserializationFunction = deserializationFunction;
  }

  @Override
  public Object serialize(PropertyT value) {
    return serializationFunction.apply(value);
  }

  @Override
  public PropertyT deserialize(Object value) {
    return deserializationFunction.apply(value);
  }

  public static <PropertyT> ModelPropertyCodec<PropertyT> of(
    Function<PropertyT, Object> serializationFunction,
    Function<Object, PropertyT> deserializationFunction
  ) {
    Preconditions.checkNotNull(serializationFunction);
    Preconditions.checkNotNull(deserializationFunction);
    return new FunctionalPropertyCodec<>(serializationFunction, deserializationFunction);
  }
}
