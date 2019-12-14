package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

public final class ModelMetaFactory {
  private final PropertyFactory propertyFactory;

  @Inject
  private ModelMetaFactory(PropertyFactory propertyFactory) {
    this.propertyFactory = propertyFactory;
  }

  /**
   * Create model meta from the type of a model.
   *
   * @param modelClass Model type.
   * @param <ModelT>   Generic model type.
   * @return Model meta.
   */
  public <ModelT> ModelMeta<?> createMeta(Class<ModelT> modelClass) {
    Preconditions.checkNotNull(modelClass);
    var declaredFields = modelClass.getDeclaredFields();
    return createMeta(modelClass, declaredFields);
  }

  private <ModelT> ModelMeta<ModelT> createMeta(
    Class<ModelT> modelClass,
    Field[] declaredFields
  ) {
    List<Property<?>> properties = Arrays.stream(declaredFields)
      .map(this::decodeProperty)
      .collect(Collectors.toList());
    return ModelMeta.of(modelClass, properties);
  }

  private <PropertyT> Property<PropertyT> decodeProperty(
    Field declaredField
  ) {
    return propertyFactory.createProperty(declaredField);
  }
}
