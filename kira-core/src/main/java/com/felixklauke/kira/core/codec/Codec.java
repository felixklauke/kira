package com.felixklauke.kira.core.codec;

import com.felixklauke.kira.core.KiraCodecException;

public interface Codec<PropertyT> {
  Object serialize(PropertyT value) throws KiraCodecException;

  PropertyT deserialize(Object value) throws KiraCodecException;
}
