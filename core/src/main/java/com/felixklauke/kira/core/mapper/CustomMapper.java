package com.felixklauke.kira.core.mapper;

import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraModelInstantiationException;
import com.felixklauke.kira.core.io.KiraReader;
import com.felixklauke.kira.core.io.KiraWriter;
import com.felixklauke.kira.core.io.SimpleKiraReader;
import com.felixklauke.kira.core.io.SimpleKiraWriter;
import com.felixklauke.kira.core.meta.ModelMeta;
import com.felixklauke.kira.core.meta.ModelMetaRepository;
import com.felixklauke.kira.core.meta.ModelProperty;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom mapper that can map arbitrary objects by working through their properties.
 *
 * @param <ModelType> The generic type of the mode.
 */
public class CustomMapper<ModelType> implements Mapper<ModelType> {

  /**
   * The class of the model.
   */
  private final Class<ModelType> modelClass;

  /**
   * The manager of the model meta information.
   */
  private final ModelMetaRepository metaManager;

  /**
   * The manager of all available mappers.
   */
  private final MapperManager mapperManager;

  public CustomMapper(Class<ModelType> modelClass, ModelMetaRepository metaManager, MapperManager mapperManager) {
    this.modelClass = modelClass;
    this.metaManager = metaManager;
    this.mapperManager = mapperManager;
  }

  @Override
  public Class<ModelType> getModelClass() {

    return modelClass;
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, ModelType model) throws KiraModelException {

    // Get meta
    Class<ModelType> modelClass = getModelClass();
    ModelMeta meta = metaManager.getMeta(modelClass);

    Map<String, Object> data = new HashMap<>();

    List<ModelProperty> properties = meta.getProperties();

    // Loop through properties
    for (ModelProperty<?> property : properties) {

      // Read the meta values
      Class<?> propertyType = property.getType();
      String localPropertyName = property.getName();
      Object propertyValue = property.getValue(model);

      if (propertyValue == null) {
        continue;
      }

      KiraWriter propertyWriter = new SimpleKiraWriter(data);

      // Obtain corresponding mapper
      Mapper mapper = mapperManager.getMapper(propertyType);
      mapper.write(propertyWriter, localPropertyName, propertyValue);
    }

    kiraWriter.writeValue(propertyName, data);
  }

  @Override
  public ModelType read(KiraReader reader, String propertyName, Type genericType) throws KiraModelException {

    // Read data
    Map<String, Object> data = reader.readValue(propertyName);

    if (data == null) {
      return null;
    }

    // Get model meta
    Class<ModelType> modelClass = getModelClass();
    ModelMeta meta = metaManager.getMeta(modelClass);

    // Create model
    ModelType model;

    try {
      model = modelClass.newInstance();
    } catch (InstantiationException e) {
      throw new KiraModelInstantiationException("Couldn't create model instance. Make sure there is a non-argument constructor available.", e);
    } catch (IllegalAccessException e) {
      throw new KiraModelException("Couldn't create model instance.", e);
    }

    // Read properties
    List<ModelProperty> properties = meta.getProperties();

    for (ModelProperty property : properties) {

      KiraReader propertyReader = new SimpleKiraReader(data);

      Class propertyType = property.getType();
      Type localGenericType = property.getGenericType();
      String localPropertyName = property.getName();

      Mapper mapper = mapperManager.getMapper(propertyType);
      Object read = mapper.read(propertyReader, localPropertyName, localGenericType);

      property.set(model, read);
    }

    return model;
  }
}
