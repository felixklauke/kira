package de.felixklauke.kira.core.mapper;

public interface MapperManager {

  <ContentType> void addMapper(Mapper<ContentType> mapper);

  <ContentType> Mapper<ContentType> getMapper(Class<ContentType> modelClass);
}
