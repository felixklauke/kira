package de.felixklauke.kira.core;

import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;
import de.felixklauke.kira.core.io.SimpleKiraReader;
import de.felixklauke.kira.core.io.SimpleKiraWriter;
import de.felixklauke.kira.core.mapper.Mapper;
import de.felixklauke.kira.core.mapper.MapperManager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SimpleKira implements Kira {

  private final Logger logger = Logger.getLogger(SimpleKira.class.getSimpleName());
  private final MapperManager mapperManager;

  public SimpleKira(MapperManager mapperManager) {
    this.mapperManager = mapperManager;
  }

  @Override
  public <ModelType> Map<String, Object> serialize(ModelType model) {

    // Get mapper
    Class<?> modelClass = model.getClass();
    Mapper<ModelType> mapper = (Mapper<ModelType>) mapperManager.getMapper(modelClass);

    // Construct data and writer
    Map<String, Object> data = new HashMap<>();
    KiraWriter writer = new SimpleKiraWriter(data);

    // Serialize
    mapper.write(writer, "root", model);

    return (Map<String, Object>) data.get("root");
  }

  @Override
  public <ModelType> ModelType deserialize(Map<String, Object> data, Class<ModelType> modelClass) {

    // Construct root map
    Map<String, Object> root = new HashMap<>();
    root.put("root", data);

    // Get mapper
    Mapper<ModelType> mapper = mapperManager.getMapper(modelClass);

    // Construct model and reader
    KiraReader reader = new SimpleKiraReader(root);

    return mapper.read(reader, "root");
  }
}
