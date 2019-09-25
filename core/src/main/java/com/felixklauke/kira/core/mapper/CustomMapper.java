package com.felixklauke.kira.core.mapper;

import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraModelInstantiationException;
import com.felixklauke.kira.core.io.KiraMapReader;
import com.felixklauke.kira.core.io.KiraMapWriter;
import com.felixklauke.kira.core.io.KiraReader;
import com.felixklauke.kira.core.io.KiraWriter;
import com.felixklauke.kira.core.meta.ModelMeta;
import com.felixklauke.kira.core.meta.ModelMetaRegistry;
import com.felixklauke.kira.core.meta.ModelProperty;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
  private final ModelMetaRegistry metaRepository;

  /**
   * The manager of all available mappers.
   */
  private final MapperRegistry mapperRegistry;

  public CustomMapper(Class<ModelType> modelClass, ModelMetaRegistry metaRepository, MapperRegistry mapperRegistry) {
    this.modelClass = modelClass;
    this.metaRepository = metaRepository;
    this.mapperRegistry = mapperRegistry;
  }

  @Override
  public Class<ModelType> getModelClass() {

    return modelClass;
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, ModelType model) throws KiraModelException {

    // Get meta
    Class<ModelType> modelClass = getModelClass();

    Optional<ModelMeta<ModelType>> metaOptional = metaRepository.getMeta(modelClass);
    ModelMeta<ModelType> modelMeta = metaOptional.orElseGet(() -> {
      ModelMeta<ModelType> meta = ModelMeta.fromClass(modelClass);
      metaRepository.saveMeta(modelClass, meta);
      return meta;
    });

    Map<String, Object> data = new HashMap<>();

    Collection<ModelProperty> properties = modelMeta.getProperties();

    // Loop through properties
    for (ModelProperty<?> property : properties) {

      // Read the meta values
      Class<?> propertyType = property.getType();
      String localPropertyName = property.getName();
      Object propertyValue = property.getValue(model);

      if (propertyValue == null) {
        continue;
      }

      KiraWriter propertyWriter = KiraMapWriter.forData(data);

      // Obtain corresponding mapper
      Mapper mapper = mapperRegistry.getMapper(propertyType);
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

    Optional<ModelMeta<ModelType>> metaOptional = metaRepository.getMeta(modelClass);
    ModelMeta<ModelType> modelMeta = metaOptional.orElseGet(() -> {
      ModelMeta<ModelType> meta = ModelMeta.fromClass(modelClass);
      metaRepository.saveMeta(modelClass, meta);
      return meta;
    });

    // Create model
    ModelType model;

    try {
      model = modelClass.newInstance();
    } catch (InstantiationException e) {
      throw KiraModelInstantiationException.withMessageAndCause("Couldn't create model instance. Make sure there is a non-argument constructor available.", e);
    } catch (IllegalAccessException e) {
      throw KiraModelException.withMessageAndCause("Couldn't create model instance.", e);
    }

    // Read properties
    Collection<ModelProperty> properties = modelMeta.getProperties();

    for (ModelProperty property : properties) {

      KiraReader propertyReader = KiraMapReader.forData(data);

      Class propertyType = property.getType();
      Type localGenericType = property.getGenericType();
      String localPropertyName = property.getName();

      Mapper mapper = mapperRegistry.getMapper(propertyType);
      Object read = mapper.read(propertyReader, localPropertyName, localGenericType);

      property.set(model, read);
    }

    return model;
  }
}
