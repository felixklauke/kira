package com.felixklauke.kira.core;

import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.io.KiraMapReader;
import com.felixklauke.kira.core.io.KiraMapWriter;
import com.felixklauke.kira.core.io.KiraReader;
import com.felixklauke.kira.core.io.KiraWriter;
import com.felixklauke.kira.core.mapper.Mapper;
import com.felixklauke.kira.core.mapper.MapperRegistry;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultKira implements Kira {

  /**
   * The name of the pseudo parent map.
   */
  private static final String ROOT_MAP_NAME = "root";

  /**
   * The logger to log general actions.
   */
  private final Logger logger = Logger.getLogger(DefaultKira.class.getSimpleName());

  /**
   * The mapper manager that delivers the right mappers.
   */
  private final MapperRegistry mapperRegistry;

  /**
   * Create a new kira instance by the underlying mapper manager.
   *
   * @param mapperRegistry The mapper manager.
   */
  private DefaultKira(MapperRegistry mapperRegistry) {
    this.mapperRegistry = mapperRegistry;
  }

  static DefaultKira withMapperRegistry(MapperRegistry mapperRegistry) {
    Preconditions.checkNotNull(mapperRegistry, "Mapper registry should not be null");

    return new DefaultKira(mapperRegistry);
  }

  @Override
  public <ModelType> Map<String, Object> serialize(ModelType model) {
    Preconditions.checkNotNull(model, "Model should not be null");

    // Get mapper
    Class<?> modelClass = model.getClass();
    Mapper<ModelType> mapper = (Mapper<ModelType>) mapperRegistry.getMapper(modelClass);

    // Construct data and writer
    Map<String, Object> data = new HashMap<>();
    KiraWriter writer = KiraMapWriter.forData(data);

    // Serialize
    try {
      mapper.write(writer, ROOT_MAP_NAME, model);
    } catch (KiraModelException e) {
      logger.log(Level.SEVERE, "Couldn't serialize model.", e);
      return new HashMap<>();
    }

    return (Map<String, Object>) data.get(ROOT_MAP_NAME);
  }

  @Override
  public <ModelType> ModelType deserialize(Map<String, Object> data, Class<ModelType> modelClass) {
    Preconditions.checkNotNull(data, "Data should not be null");
    Preconditions.checkNotNull(modelClass, "Model class should not be null");

    // Construct root map
    Map<String, Object> root = new HashMap<>();
    root.put("root", data);

    // Get mapper
    Mapper<ModelType> mapper = mapperRegistry.getMapper(modelClass);

    // Construct model and reader
    KiraReader reader = KiraMapReader.forData(root);

    try {
      return mapper.read(reader, ROOT_MAP_NAME, modelClass);
    } catch (KiraModelException e) {
      logger.log(Level.SEVERE, "Couldn't deserialize model.", e
      );
      return null;
    }
  }
}
