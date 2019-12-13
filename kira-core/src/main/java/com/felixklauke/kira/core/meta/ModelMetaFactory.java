package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

public final class ModelMetaFactory {
  private final ModelPropertyFactory modelPropertyFactory;

  @Inject
  private ModelMetaFactory(ModelPropertyFactory modelPropertyFactory) {
    this.modelPropertyFactory = modelPropertyFactory;
  }

  public <ModelT> ModelMeta<?> createMeta(Class<ModelT> modelClass) {
    Preconditions.checkNotNull(modelClass);
    var declaredFields = modelClass.getDeclaredFields();
    return createMeta(modelClass, declaredFields);
  }

  private <ModelT> ModelMeta<ModelT> createMeta(
    Class<ModelT> modelClass,
    Field[] declaredFields
  ) {
    List<ModelProperty<?>> properties = Arrays.stream(declaredFields)
      .map(this::decodeProperty)
      .collect(Collectors.toList());
    return ModelMeta.of(modelClass, properties);
  }

  private <PropertyT> ModelProperty<PropertyT> decodeProperty(
    Field declaredField
  ) {
    return modelPropertyFactory.createProperty(declaredField);
  }
}
