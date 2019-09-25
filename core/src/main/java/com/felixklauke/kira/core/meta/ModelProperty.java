package com.felixklauke.kira.core.meta;

import com.felixklauke.kira.core.exception.KiraModelPropertyException;
import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ModelProperty<PropertyType> {

  private final Field field;

  private ModelProperty(Field field) {
    this.field = field;
  }

  static <PropertyType> ModelProperty<PropertyType> createModelProperty(Field field) {
    Preconditions.checkNotNull(field, "Field should not be null");

    return new ModelProperty<>(field);
  }

  public Class<PropertyType> getType() {
    return (Class<PropertyType>) field.getType();
  }

  public String getName() {
    return field.getName();
  }

  public Object getValue(Object model) throws KiraModelPropertyException {

    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    try {
      return field.get(model);
    } catch (IllegalAccessException e) {
      throw KiraModelPropertyException.withMessageAndCause("Couldn't get model property.", e);
    }
  }

  public <ModelType> void set(ModelType model, Object value) throws KiraModelPropertyException {

    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    try {
      field.set(model, value);
    } catch (IllegalAccessException e) {
      throw KiraModelPropertyException.withMessageAndCause("Couldn't set model property.", e);
    }
  }

  public Type getGenericType() {

    return field.getGenericType();
  }
}
