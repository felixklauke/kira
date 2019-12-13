package com.felixklauke.kira.core.meta;

import com.felixklauke.kira.core.exception.KiraDeserializationException;
import com.felixklauke.kira.core.exception.KiraSerializationException;
import java.util.function.Function;

public interface ModelPropertyCodec<PropertyT> {
  Object serialize(PropertyT value) throws KiraSerializationException;
  PropertyT deserialize(Object value) throws KiraDeserializationException;
}
