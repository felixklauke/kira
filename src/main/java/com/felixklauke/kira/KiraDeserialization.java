package com.felixklauke.kira;

import com.felixklauke.kira.exception.KiraDeserializationException;
import com.felixklauke.kira.exception.KiraPropertyException;
import com.felixklauke.kira.meta.ModelMeta;
import com.felixklauke.kira.meta.Property;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class KiraDeserialization<ModelT> {
  private final Map<String, Object> data;
  private final ModelMeta<ModelT> meta;

  private KiraDeserialization(
    Map<String, Object> data,
    ModelMeta<ModelT> meta
  ) {
    this.data = data;
    this.meta = meta;
  }

  /**
   * Factory method to create a deserialization of its basic components.
   *
   * @param data      Serialized data.
   * @param modelMeta Model meta.
   * @param <ModelT>  Generic model type.
   * @return Model Deserialization.
   */
  public static <ModelT> KiraDeserialization<ModelT> of(
    Map<String, Object> data,
    ModelMeta<ModelT> modelMeta
  ) {
    Preconditions.checkNotNull(data);
    Preconditions.checkNotNull(modelMeta);
    return new KiraDeserialization<>(data, modelMeta);
  }

  /**
   * Execute the deserialization.
   *
   * @return Model instance.
   * @throws KiraDeserializationException When the deserialization fails.
   */
  public ModelT execute() throws KiraDeserializationException {
    var properties = meta.properties();
    var values = processProperties(properties);
    return createModelInstanceFromValues(values);
  }

  private ModelT createModelInstanceFromValues(
    Collection<Object> values
  ) throws KiraDeserializationException {
    List<? extends Class<?>> valueClasses = extractParameterTypes(values);
    return tryConstructorCreation(values, valueClasses);
  }

  private ModelT tryConstructorCreation(
    Collection<Object> values,
    List<? extends Class<?>> valueClasses
  ) throws KiraDeserializationException {
    var constructorOptional = meta.findConstructor(valueClasses);
    var constructor = constructorOptional
      .orElseThrow(() -> KiraDeserializationException
        .withMessage("Couldn't create model instance: No suitable creation"));
    return createModelViaQualifiedConstructor(constructor,
      values);
  }

  private List<? extends Class<?>> extractParameterTypes(
    Collection<Object> values
  ) {
    return values.stream()
      .map(Object::getClass)
      .collect(Collectors.toList());
  }

  private ModelT createModelViaQualifiedConstructor(
    Constructor<ModelT> constructor,
    Collection<Object> values
  ) throws KiraDeserializationException {
    try {
      return constructor.newInstance(values.toArray(new Object[0]));
    } catch (ReflectiveOperationException e) {
      throw KiraDeserializationException.withMessageAndCause(
        "Couldn't create model instance: Couldn't call constructor", e);
    }
  }

  private Collection<Object> processProperties(
    Collection<Property<?>> properties
  ) throws KiraDeserializationException {
    var processedProperties = Lists.newArrayList();
    for (Property<?> property : properties) {
      var processedProperty = processProperty(property);
      processedProperties.add(processedProperty);
    }
    return processedProperties;
  }

  private Object processProperty(
    Property<?> property
  ) throws KiraDeserializationException {
    var identifier = property.identifier();
    var value = data.get(identifier);
    return tryPropertyDeserialization(property, value);
  }

  private Object tryPropertyDeserialization(
    Property<?> property,
    Object value
  ) throws KiraDeserializationException {
    try {
      return property.deserialize(value);
    } catch (KiraPropertyException e) {
      throw KiraDeserializationException
        .withMessageAndCause("Couldn't deserialize property", e);
    }
  }
}
