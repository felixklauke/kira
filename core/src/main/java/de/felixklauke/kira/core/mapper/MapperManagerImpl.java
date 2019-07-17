package de.felixklauke.kira.core.mapper;

import de.felixklauke.kira.core.mapper.standard.*;
import de.felixklauke.kira.core.meta.ModelMetaManager;

import java.net.URI;
import java.net.URL;
import java.util.*;

public class MapperManagerImpl implements MapperManager {

  private final ModelMetaManager metaManager;
  private final Map<Class<?>, Mapper<?>> mappers;

  public MapperManagerImpl(ModelMetaManager metaManager, Map<Class<?>, Mapper<?>> mappers) {
    this.metaManager = metaManager;
    this.mappers = mappers;

    // Standard java types
    mappers.put(String.class, new StringMapper());
    mappers.put(UUID.class, new UUIDMapper());
    mappers.put(Date.class, new DateMapper());
    mappers.put(URL.class, new URLMapper());
    mappers.put(URI.class, new URIMapper());
    mappers.put(List.class, new ListMapper());
    mappers.put(Map.class, new MapMapper());
    mappers.put(HashMap.class, new MapMapper());
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

  public MapperManagerImpl(ModelMetaManager metaManager) {
    this(metaManager, new HashMap<>());
  }

  @Override
  public <ContentType> void addMapper(Mapper<ContentType> mapper) {

    mappers.put(mapper.getModelClass(), mapper);
  }

  @Override
  public <ContentType> Mapper<ContentType> getMapper(Class<ContentType> modelClass) {

    Mapper<?> mapper = mappers.get(modelClass);

    if (mapper == null) {
      mapper = new CustomMapper<>(modelClass, metaManager, this);
      mappers.put(modelClass, mapper);
    }

    return (Mapper<ContentType>) mapper;
  }
}
