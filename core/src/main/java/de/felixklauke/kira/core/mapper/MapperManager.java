package de.felixklauke.kira.core.mapper;

public interface MapperManager {

  <ContentType> Mapper<ContentType> getMapper(Class<ContentType> modelClass);
}
