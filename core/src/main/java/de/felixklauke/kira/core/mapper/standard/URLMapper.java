package com.felixklauke.kira.core.mapper.standard;

import com.felixklauke.kira.core.io.KiraReader;
import com.felixklauke.kira.core.io.KiraWriter;
import com.felixklauke.kira.core.mapper.Mapper;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class URLMapper implements Mapper<URL> {

  @Override
  public Class<URL> getModelClass() {
    return URL.class;
  }

  @Override
  public URL read(KiraReader reader, String propertyName, Type genericType) {

    String value = reader.readValue(propertyName);
    try {
      return new URL(value);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, URL model) {

    String value = model.toString();
    kiraWriter.writeValue(propertyName, value);
  }
}
