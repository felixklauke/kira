package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReflectionBasedModelMetaRegistry implements ModelMetaRegistry {

  private final Map<Class<?>, ModelMeta<?>> meta;

  private ReflectionBasedModelMetaRegistry(Map<Class<?>, ModelMeta<?>> meta) {
    this.meta = meta;
  }

  public ReflectionBasedModelMetaRegistry() {
    this(new HashMap<>());
  }

  @Override
  public <ModelType> void saveMeta(Class<ModelType> modelClass, ModelMeta<ModelType> modelMeta) {
    Preconditions.checkNotNull(modelClass, "Model class should not be null");
    Preconditions.checkNotNull(modelClass, "Model meta should not be null");

    meta.put(modelClass, modelMeta);
  }

  @Override
  public <ModelType> Optional<ModelMeta<ModelType>> getMeta(Class<ModelType> modelClass) {
    Preconditions.checkNotNull(modelClass, "Model class should not be null");

    ModelMeta<ModelType> modelMeta = (ModelMeta<ModelType>) meta.get(modelClass);
    return Optional.ofNullable(modelMeta);
  }
}
