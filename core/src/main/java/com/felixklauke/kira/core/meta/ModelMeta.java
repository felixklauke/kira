package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Felix Klauke (info@felix-klauke.de)
 */
public class ModelMeta<ModelType> {

  private final Collection<ModelProperty> properties;

  private ModelMeta(Collection<ModelProperty> properties) {
    this.properties = properties;
  }

  public static <ModelType> ModelMeta<ModelType> fromClass(Class<ModelType> modelClass) {
    Preconditions.checkNotNull(modelClass, "Model class should not be null");

    List<ModelProperty> modelProperties;

    Field[] declaredFields = modelClass.getDeclaredFields();
    modelProperties = Arrays.stream(declaredFields)
      .map((Function<Field, ModelProperty>) ModelProperty::createModelProperty)
      .collect(Collectors.toList());

    return ModelMeta.createModelMeta(modelProperties);
  }

  static <ModelType> ModelMeta<ModelType> createModelMeta(Collection<ModelProperty> properties) {
    Preconditions.checkNotNull(properties, "Properties should not be null");
    return new ModelMeta<>(properties);
  }

  public Collection<ModelProperty> getProperties() {
    return Collections.unmodifiableCollection(properties);
  }
}
