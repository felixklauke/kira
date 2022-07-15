package com.felixklauke.kira;

import com.felixklauke.kira.exception.KiraModelException;
import com.felixklauke.kira.exception.KiraPropertyException;
import com.felixklauke.kira.exception.KiraSerializationException;
import com.felixklauke.kira.meta.ModelMeta;
import com.felixklauke.kira.meta.Property;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class KiraSerialization<ModelT> {
  private final ModelT value;
  private final ModelMeta<ModelT> meta;

  private KiraSerialization(ModelT value, ModelMeta<ModelT> meta) {
    this.value = value;
    this.meta = meta;
  }

  /**
   * Factory method to create a serialization of its basic components.
   *
   * @param model    Model.
   * @param meta     Model meta.
   * @param <ModelT> Generic model type.
   * @return Model serialization.
   */
  public static <ModelT> KiraSerialization<ModelT> of(
    ModelT model,
    ModelMeta<ModelT> meta
  ) {
    Preconditions.checkNotNull(model);
    Preconditions.checkNotNull(meta);
    return new KiraSerialization<>(model, meta);
  }

  /**
   * Execute the serialization.
   *
   * @return The serialized data.
   * @throws KiraSerializationException If the serialization fails.
   */
  public Map<String, Object> execute() throws KiraSerializationException {
    Collection<Property<?>> properties = meta.properties();
    Map<String, Object> root = new HashMap<>();
    return trySerialization(root, properties);
  }

  private Map<String, Object> trySerialization(
    Map<String, Object> root,
    Collection<Property<?>> properties
  ) throws KiraSerializationException {
    try {
      processProperties(root, properties);
      return root;
    } catch (KiraModelException e) {
      throw KiraSerializationException
        .withMessageAndCause("Couldn't create model", e);
    }
  }

  private void processProperties(
    Map<String, Object> root,
    Collection<Property<?>> properties
  ) throws KiraModelException {
    for (Property<?> property : properties) {
      processProperty(root, property);
    }
  }

  private void processProperty(
    Map<String, Object> root,
    Property property
  ) throws KiraModelException {
    var propertyValue = property.extractValue(value);
    var serializedPropertyValue = tryPropertySerialization(property,
      propertyValue);
    root.put(property.identifier(), serializedPropertyValue);
  }

  private Object tryPropertySerialization(
    Property property,
    Object propertyValue
  ) throws KiraModelException {
    try {
      return property.serialize(propertyValue);
    } catch (KiraPropertyException e) {
      throw KiraModelException
        .withMessageAndCause("Couldn't deserialize property", e);
    }
  }
}
