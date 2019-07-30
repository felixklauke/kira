package com.felixklauke.kira.core.mapper.standard;

import com.felixklauke.kira.core.io.KiraReader;
import com.felixklauke.kira.core.io.KiraWriter;
import com.felixklauke.kira.core.mapper.Mapper;

import java.lang.reflect.Type;
import java.net.URI;

public class URIMapper implements Mapper<URI> {

  @Override
  public Class<URI> getModelClass() {
    return URI.class;
  }

  @Override
  public URI read(KiraReader reader, String propertyName, Type genericType) {

    String value = reader.readValue(propertyName);
    return URI.create(value);
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, URI model) {
    String value = model.toString();
    kiraWriter.writeValue(propertyName, value);
  }
}
