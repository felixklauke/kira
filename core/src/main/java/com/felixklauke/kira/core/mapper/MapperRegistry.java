package com.felixklauke.kira.core.mapper;

public interface MapperRegistry {

  <ContentType> void addMapper(Mapper<ContentType> mapper);

  <ContentType> Mapper<ContentType> getMapper(Class<ContentType> modelClass);
}
