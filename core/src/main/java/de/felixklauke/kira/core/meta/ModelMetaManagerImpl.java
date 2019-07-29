package com.felixklauke.kira.core.meta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModelMetaManagerImpl implements ModelMetaManager {

  private final Map<Class<?>, ModelMeta<?>> meta;

  public ModelMetaManagerImpl(Map<Class<?>, ModelMeta<?>> meta) {
    this.meta = meta;
  }

  public ModelMetaManagerImpl() {
    this(new HashMap<>());
  }

  @Override
  public <ModelType> ModelMeta<ModelType> getMeta(Class<ModelType> modelClass) {
    ModelMeta<ModelType> modelMeta = (ModelMeta<ModelType>) meta.get(modelClass);

    if (modelMeta == null) {
      modelMeta = readModelMeta(modelClass);
      meta.put(modelClass, modelMeta);
    }

    return modelMeta;
  }

  private <ModelType> ModelMeta<ModelType> readModelMeta(Class<ModelType> modelClass) {

    List<ModelProperty> modelProperties;

    Field[] declaredFields = modelClass.getDeclaredFields();
    modelProperties = Arrays.stream(declaredFields)
      .map((Function<Field, ModelProperty>) ModelProperty::new)
      .collect(Collectors.toList());

    return new ModelMeta<>(modelProperties);
  }
}
