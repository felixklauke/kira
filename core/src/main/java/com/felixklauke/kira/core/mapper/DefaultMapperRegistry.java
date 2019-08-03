package com.felixklauke.kira.core.mapper;

import com.felixklauke.kira.core.mapper.standard.*;
import com.felixklauke.kira.core.meta.ModelMetaRepository;

import java.net.URI;
import java.net.URL;
import java.util.*;

public class DefaultMapperRegistry implements MapperRegistry {

  private final ModelMetaRepository metaRepository;
  private final Map<Class<?>, Mapper<?>> mappers;

  public DefaultMapperRegistry(ModelMetaRepository metaRepository, Map<Class<?>, Mapper<?>> mappers) {
    this.metaRepository = metaRepository;
    this.mappers = mappers;

    // Standard java types
    mappers.put(String.class, new StringMapper());
    mappers.put(UUID.class, new UUIDMapper());
    mappers.put(Date.class, new DateMapper());
    mappers.put(URL.class, new URLMapper());
    mappers.put(URI.class, new URIMapper());
    mappers.put(List.class, new ListMapper());
    mappers.put(Map.class, new MapMapper(this));
    mappers.put(HashMap.class, new MapMapper(this));
    mappers.put(LinkedHashMap.class, new MapMapper(this));
    mappers.put(Set.class, new SetMapper());
    mappers.put(HashSet.class, new SetMapper());

    // Primitives and Wrapper
    mappers.put(int.class, new IntegerMapper());
    mappers.put(Integer.class, new IntegerMapper());
    mappers.put(boolean.class, new BooleanMapper());
    mappers.put(Boolean.class, new BooleanMapper());
    mappers.put(long.class, new LongMapper());
    mappers.put(Long.class, new LongMapper());
    mappers.put(short.class, new ShortMapper());
    mappers.put(Short.class, new ShortMapper());
    mappers.put(byte.class, new ByteMapper());
    mappers.put(Byte.class, new ByteMapper());
    mappers.put(char.class, new CharMapper());
    mappers.put(Character.class, new CharMapper());
    mappers.put(double.class, new DoubleMapper());
    mappers.put(Double.class, new DoubleMapper());
  }

  public DefaultMapperRegistry(ModelMetaRepository metaRepository) {
    this(metaRepository, new HashMap<>());
  }

  @Override
  public <ContentType> void addMapper(Mapper<ContentType> mapper) {

    Objects.requireNonNull(mapper, "Mapper cannot be null.");

    mappers.put(mapper.getModelClass(), mapper);
  }

  @Override
  public <ContentType> Mapper<ContentType> getMapper(Class<ContentType> modelClass) {

    Objects.requireNonNull(modelClass, "Model class cannot be null.");

    Mapper<?> mapper = mappers.get(modelClass);

    if (mapper == null) {
      mapper = new CustomMapper<>(modelClass, metaRepository, this);
      mappers.put(modelClass, mapper);
    }

    return (Mapper<ContentType>) mapper;
  }
}
