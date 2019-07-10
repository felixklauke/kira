package de.felixklauke.kira.core.meta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<ModelProperty> modelProperties = new ArrayList<>();

    Field[] declaredFields = modelClass.getDeclaredFields();
    for (Field declaredField : declaredFields) {
      ModelProperty property = new ModelProperty(declaredField);
      modelProperties.add(property);
    }

    return new ModelMeta<>(modelProperties);
  }
}
