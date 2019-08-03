package com.felixklauke.kira.core.meta;

import java.util.List;

/**
 * @author Felix Klauke (info@felix-klauke.de)
 */
public class ModelMeta<ModelType> {

  private final List<ModelProperty> properties;

  ModelMeta(List<ModelProperty> properties) {
    this.properties = properties;
  }

  public List<ModelProperty> getProperties() {

    return List.copyOf(properties);
  }
}
