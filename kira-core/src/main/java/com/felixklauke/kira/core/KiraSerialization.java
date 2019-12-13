package com.felixklauke.kira.core;

import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraSerializationException;
import com.felixklauke.kira.core.meta.ModelMeta;
import com.felixklauke.kira.core.meta.ModelProperty;
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

  public static <ModelT> KiraSerialization<ModelT> of(
    ModelT model,
    ModelMeta<ModelT> meta
  ) {
    Preconditions.checkNotNull(model);
    Preconditions.checkNotNull(meta);
    return new KiraSerialization<>(model, meta);
  }

  public Map<String, Object> execute() throws KiraSerializationException {
    Collection<ModelProperty<?>> properties = meta.properties();
    Map<String, Object> root = new HashMap<>();
    try {
      processProperties(root, properties);
      return root;
    } catch (KiraModelException e) {
      throw KiraSerializationException.withMessageAndCause("Couldn't create model", e);
    }
  }

  private void processProperties(
    Map<String, Object> root,
    Collection<ModelProperty<?>> properties
  ) throws KiraSerializationException, KiraModelException {
    for (ModelProperty<?> property : properties) {
      processProperty(root, property);
    }
  }

  private void processProperty(
    Map<String, Object> root,
    ModelProperty property
  ) throws KiraModelException, KiraSerializationException {
    Object propertyValue = property.extractValue(value);
    Object serializedPropertyValue = property.serialize(propertyValue);
    root.put(property.identifier(), serializedPropertyValue);
  }
}
