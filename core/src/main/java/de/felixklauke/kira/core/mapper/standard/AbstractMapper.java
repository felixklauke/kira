package de.felixklauke.kira.core.mapper.standard;

import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;
import de.felixklauke.kira.core.mapper.Mapper;

import java.lang.reflect.Type;

public abstract class AbstractMapper<ContentType> implements Mapper<ContentType> {

  @Override
  public ContentType read(KiraReader reader, String propertyName, Type genericType) {

    return reader.readValue(propertyName);
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, ContentType model) {

    kiraWriter.writeValue(propertyName, model);
  }
}
