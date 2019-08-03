package com.felixklauke.kira.core.meta;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Felix Klauke (info@felix-klauke.de)
 */
public class ModelMeta<ModelType> {

  private final Collection<ModelProperty> properties;

  private ModelMeta(Collection<ModelProperty> properties) {

    this.properties = properties;
  }

  static <ModelType> ModelMeta<ModelType> createModelMeta(Collection<ModelProperty> properties) {

    Objects.requireNonNull(properties, "Properties may not be null.");

    return new ModelMeta<>(properties);
  }

  public Collection<ModelProperty> getProperties() {

    return Collections.unmodifiableCollection(properties);
  }
}
