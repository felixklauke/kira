package de.felixklauke.kira.core.mapper.standard;

import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;
import de.felixklauke.kira.core.mapper.Mapper;

import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDMapper implements Mapper<UUID> {

  @Override
  public Class<UUID> getModelClass() {
    return UUID.class;
  }

  @Override
  public UUID read(KiraReader reader, String propertyName, Type genericType) {

    String value = reader.readValue(propertyName);
    return UUID.fromString(value);
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, UUID model) {
    kiraWriter.writeValue(propertyName, model.toString());
  }
}
