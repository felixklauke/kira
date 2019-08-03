package com.felixklauke.kira.core.meta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReflectionBasedModelMetaRepository implements ModelMetaRepository {

  private final Map<Class<?>, ModelMeta<?>> meta;

  public ReflectionBasedModelMetaRepository(Map<Class<?>, ModelMeta<?>> meta) {
    this.meta = meta;
  }

  public ReflectionBasedModelMetaRepository() {
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
      .map((Function<Field, ModelProperty>) ModelProperty::createModelProperty)
      .collect(Collectors.toList());

    return ModelMeta.createModelMeta(modelProperties);
  }
}
