package de.felixklauke.kira.core.meta;

import java.lang.reflect.Field;

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

  public Object getValue(Object model) {

    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    try {
      return field.get(model);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return null;
  }

  public <ModelType> void set(ModelType model, Object value) {

    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    try {
      field.set(model, value);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
