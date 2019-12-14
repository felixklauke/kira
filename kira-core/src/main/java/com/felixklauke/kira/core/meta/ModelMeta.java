package com.felixklauke.kira.core.meta;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Primitives;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ModelMeta<ModelT> {
  private final Class<ModelT> modelClass;
  private final Collection<Property<?>> properties;

  private ModelMeta(
    Class<ModelT> modelClass,
    Collection<Property<?>> properties
  ) {
    this.modelClass = modelClass;
    this.properties = properties;
  }

  /**
   * Factory method to create model meta by its basic components.
   *
   * @param modelClass Model type.
   * @param properties Model properties.
   * @param <ModelT> Generic model type.
   * @return Model meta.
   */
  public static <ModelT> ModelMeta<ModelT> of(
    Class<ModelT> modelClass,
    Collection<Property<?>> properties
  ) {
    Preconditions.checkNotNull(properties);
    return new ModelMeta<>(modelClass, properties);
  }

  /**
   * Obtain a collection of properties of the model.
   *
   * @return Properties.
   */
  public Collection<Property<?>> properties() {
    return Collections.unmodifiableCollection(properties);
  }

  /**
   * Find a constructor with specific parameter types.
   *
   * @param valueClasses Types of parameters.
   * @return Optional of a constructor.
   */
  public Optional<Constructor<ModelT>> findConstructor(
    List<? extends Class<?>> valueClasses
  ) {
    try {
      var classes = transformValueClasses(valueClasses);
      return Optional.of(modelClass.getConstructor(classes));
    } catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  private Class<?>[] transformValueClasses(
    List<? extends Class<?>> valueClasses
  ) {
    Class<?>[] classes = new Class[valueClasses.size()];
    for (int index = 0; index < valueClasses.size(); index++) {
      var rawClass = resolveClass(valueClasses, index);
      classes[index] = rawClass;
    }
    return classes;
  }

  private Class<?> resolveClass(List<? extends Class<?>> valueClasses,
    int index) {
    Class<?> rawClass = valueClasses.get(index);
    if (Primitives.isWrapperType(rawClass)) {
      return Primitives.unwrap(rawClass);
    }
    return rawClass;
  }
}
