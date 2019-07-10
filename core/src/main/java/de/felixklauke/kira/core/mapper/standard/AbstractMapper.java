package de.felixklauke.kira.core.mapper.standard;

import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;
import de.felixklauke.kira.core.mapper.Mapper;

import java.util.Map;

public abstract class AbstractMapper<ContentType> implements Mapper<ContentType> {

  @Override
  public ContentType read(KiraReader reader, String propertyName) {

    return reader.readValue(propertyName);
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, ContentType model) {

    kiraWriter.writeValue(propertyName, model);
  }
}
