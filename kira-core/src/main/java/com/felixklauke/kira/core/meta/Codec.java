package com.felixklauke.kira.core.meta;

import com.felixklauke.kira.core.exception.KiraCodecException;

public interface Codec<PropertyT> {
  Object serialize(PropertyT value) throws KiraCodecException;

  PropertyT deserialize(Object value) throws KiraCodecException;
}
