package com.felixklauke.kira.core.mapper.standard;

import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.io.KiraReader;
import com.felixklauke.kira.core.io.KiraWriter;
import com.felixklauke.kira.core.mapper.Mapper;

import java.lang.reflect.Type;

public abstract class AbstractMapper<ContentType> implements Mapper<ContentType> {

  @Override
  public ContentType read(KiraReader reader, String propertyName, Type genericType) throws KiraModelException {

    return reader.readValue(propertyName);
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, ContentType model) throws KiraModelException {

    kiraWriter.writeValue(propertyName, model);
  }
}
