package com.felixklauke.kira.meta;

import com.felixklauke.kira.exception.KiraCodecException;

public interface Codec<PropertyT> {
  Object serialize(PropertyT value) throws KiraCodecException;

  PropertyT deserialize(Object value) throws KiraCodecException;
}
