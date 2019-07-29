package de.felixklauke.kira.core.meta;

import de.felixklauke.kira.core.exception.KiraModelPropertyException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ModelProperty<PropertyType> {

  private final Field field;

  public ModelProperty(Field field) {
    this.field = field;
  }

  public Class<PropertyType> getType() {
    return (Class<PropertyType>) field.getType();
  }

  public String getName() {
    return field.getName();
  }

  public Field getField() {
    return field;
  }

  public Object getValue(Object model) throws KiraModelPropertyException {

    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    try {
      return field.get(model);
    } catch (IllegalAccessException e) {
      throw new KiraModelPropertyException("Couldn't get model property.", e);
    }
  }

  public <ModelType> void set(ModelType model, Object value) throws KiraModelPropertyException {

    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    try {
      field.set(model, value);
    } catch (IllegalAccessException e) {
      throw new KiraModelPropertyException("Couldn't set model property.", e);
    }
  }

  public Type getGenericType() {

    return field.getGenericType();
  }
}
