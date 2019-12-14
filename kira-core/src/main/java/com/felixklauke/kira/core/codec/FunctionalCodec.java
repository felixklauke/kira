package com.felixklauke.kira.core.codec;

import com.felixklauke.kira.core.exception.KiraCodecException;
import com.google.common.base.Preconditions;
import java.util.function.Function;

public final class FunctionalCodec<ModelT> implements
  Codec<ModelT> {
  private final Function<ModelT, Object> serializationFunction;
  private final Function<Object, ModelT> deserializationFunction;

  private FunctionalCodec(
    Function<ModelT, Object> serializationFunction,
    Function<Object, ModelT> deserializationFunction
  ) {
    this.serializationFunction = serializationFunction;
    this.deserializationFunction = deserializationFunction;
  }

  /**
   * Create a codec by two functions.
   *
   * @param serializationFunction   Function for serialization.
   * @param deserializationFunction Function for deserialization.
   * @param <ModelT>                Generic model type.
   * @return Codec.
   */
  public static <ModelT> Codec<ModelT> of(
    Function<ModelT, Object> serializationFunction,
    Function<Object, ModelT> deserializationFunction
  ) {
    Preconditions.checkNotNull(serializationFunction);
    Preconditions.checkNotNull(deserializationFunction);
    return new FunctionalCodec<>(serializationFunction,
      deserializationFunction);
  }

  @Override
  public Object serialize(ModelT value) throws KiraCodecException {
    return serializationFunction.apply(value);
  }

  @Override
  public ModelT deserialize(Object value) throws KiraCodecException {
    return deserializationFunction.apply(value);
  }
}
