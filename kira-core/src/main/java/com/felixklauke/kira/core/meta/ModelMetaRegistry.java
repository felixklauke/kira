package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ModelMetaRegistry {
  private final Map<Class<?>, ModelMeta<?>> meta = Maps.newConcurrentMap();
  private final ModelMetaFactory metaFactory;

  @Inject
  private ModelMetaRegistry(
    ModelMetaFactory metaFactory
  ) {
    this.metaFactory = metaFactory;
  }

  public <ModelT> ModelMeta<ModelT> metaForClass(Class<ModelT> modelClass) {
    Preconditions.checkNotNull(modelClass);
    ModelMeta<ModelT> modelMeta = (ModelMeta<ModelT>) meta.get(modelClass);
    if (modelMeta == null) {
      modelMeta = createModelMeta(modelClass);
      meta.put(modelClass, modelMeta);
    }
    return modelMeta;
  }

  private <ModelT> ModelMeta<ModelT> createModelMeta(Class<ModelT> modelClass) {
    return (ModelMeta<ModelT>) metaFactory.createMeta(modelClass);
  }
}
