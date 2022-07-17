package com.felixklauke.kira.meta.collection;

import com.felixklauke.kira.exception.KiraCodecException;
import com.felixklauke.kira.meta.Codec;
import com.felixklauke.kira.meta.CodecRegistry;
import com.felixklauke.kira.meta.Property;
import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;

public final class ListCodec implements Codec<List<?>> {
  private final CodecRegistry codecRegistry;

  @Inject
  ListCodec(CodecRegistry codecRegistry) {
    this.codecRegistry = codecRegistry;
  }

  @Override
  public Object serialize(List<?> value) throws KiraCodecException {
    var root = Lists.newArrayList();
    for (Object item : value) {
      Codec codec = codecRegistry.findCodecOrCreate(item.getClass());
      root.add(codec.serialize(item));
    }
    return root;
  }

  @Override
  public List<?> deserialize(Object value) throws KiraCodecException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<?> deserialize(Object value, Property<List<?>> property) throws KiraCodecException {
    List<Object> list = Lists.newArrayList();
    for (Object item : (List<?>) value) {
      Codec codec = codecRegistry.findCodecOrCreate(item.getClass());
      var deserialized = codec.deserialize(item);
      list.add(deserialized);
    }
    return list;
  }
}
