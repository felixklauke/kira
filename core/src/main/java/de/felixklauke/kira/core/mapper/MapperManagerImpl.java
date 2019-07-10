package de.felixklauke.kira.core.mapper;

import de.felixklauke.kira.core.mapper.standard.DateMapper;
import de.felixklauke.kira.core.mapper.standard.IntegerMapper;
import de.felixklauke.kira.core.mapper.standard.StringMapper;
import de.felixklauke.kira.core.mapper.standard.UUIDMapper;
import de.felixklauke.kira.core.meta.ModelMetaManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapperManagerImpl implements MapperManager {

  private final ModelMetaManager metaManager;
  private final Map<Class<?>, Mapper<?>> mappers;

  public MapperManagerImpl(ModelMetaManager metaManager, Map<Class<?>, Mapper<?>> mappers) {
    this.metaManager = metaManager;
    this.mappers = mappers;

    mappers.put(String.class, new StringMapper());
    mappers.put(int.class, new IntegerMapper());
    mappers.put(Integer.class, new IntegerMapper());
    mappers.put(UUID.class, new UUIDMapper());
    mappers.put(Date.class, new DateMapper());
  }

  public MapperManagerImpl(ModelMetaManager metaManager) {
    this(metaManager, new HashMap<>());
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
