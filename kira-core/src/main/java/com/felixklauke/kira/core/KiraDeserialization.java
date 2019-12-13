package com.felixklauke.kira.core;

import com.felixklauke.kira.core.exception.KiraDeserializationException;
import com.felixklauke.kira.core.meta.ModelMeta;
import com.felixklauke.kira.core.meta.ModelProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

  public static <ModelT> KiraDeserialization<ModelT> of(
    Map<String, Object> data,
    ModelMeta<ModelT> modelMeta
  ) {
    Preconditions.checkNotNull(data);
    Preconditions.checkNotNull(modelMeta);
    return new KiraDeserialization<>(data, modelMeta);
  }

  public ModelT execute() throws KiraDeserializationException {
    var properties = meta.properties();
    var values = processProperties(properties);
    return createModelInstanceFromValues(values);
  }

  private ModelT createModelInstanceFromValues(Collection<Object> values)
    throws KiraDeserializationException {
    List<? extends Class<?>> valueClasses = values.stream()
      .map(Object::getClass)
      .collect(Collectors.toList());

    var constructorOptional = meta.findConstructor(valueClasses);
    if (constructorOptional.isPresent()) {
      return createModelViaQualifiedConstructor(constructorOptional.get(), values);
    }

    throw KiraDeserializationException.withMessage("Couldn't create modle instance: No suitable creation");
  }

  private ModelT createModelViaQualifiedConstructor(
    Constructor<ModelT> constructor,
    Collection<Object> values) throws KiraDeserializationException {
    try {
      return constructor.newInstance(values.toArray(new Object[0]));
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw KiraDeserializationException.withMessageAndCause("Couldn't create model instance: Couldn't call constructor", e);
    }
  }

  private Collection<Object> processProperties(
    Collection<ModelProperty<?>> properties
  ) throws KiraDeserializationException {
    var processedProperties = Lists.newArrayList();
    for (ModelProperty<?> property : properties) {
      var processedProperty = processProperty(property);
      processedProperties.add(processedProperty);
    }
    return processedProperties;
  }

  private Object processProperty(ModelProperty<?> property)
    throws KiraDeserializationException {
    var identifier = property.identifier();
    var value = data.get(identifier);
    return property.deserialize(value);
  }
}
