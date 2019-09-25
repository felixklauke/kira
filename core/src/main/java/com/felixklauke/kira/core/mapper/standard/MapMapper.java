package com.felixklauke.kira.core.mapper.standard;

import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraModelInvalidGenericsException;
import com.felixklauke.kira.core.io.KiraReader;
import com.felixklauke.kira.core.io.KiraWriter;
import com.felixklauke.kira.core.io.KiraMapReader;
import com.felixklauke.kira.core.io.KiraMapWriter;
import com.felixklauke.kira.core.mapper.Mapper;
import com.felixklauke.kira.core.mapper.MapperRegistry;
import com.felixklauke.kira.core.util.TypeUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapMapper extends AbstractMapper<Map> {

  private final MapperRegistry mapperRegistry;

  public MapMapper(MapperRegistry mapperRegistry) {
    this.mapperRegistry = mapperRegistry;
  }

  @Override
  public Class<Map> getModelClass() {
    return Map.class;
  }

  @Override
  public Map read(KiraReader reader, String propertyName, Type genericType) throws KiraModelException {

    Map<String, Object> content = super.read(reader, propertyName, genericType);

    if (content == null) {
      return null;
    }

    Map<String, Object> contentMap = new HashMap<>();
    KiraMapReader embeddedKiraReader = KiraMapReader.forData(content);

    // Get generic type
    Class<?>[] genericTypeClasses = TypeUtils.getGenericTypeClasses(genericType);
    if (genericTypeClasses.length != 2) {
      throw KiraModelInvalidGenericsException.withMessage("Couldn't infer generic type of property " + propertyName + ".");
    }

    // Construct map
    Set<Map.Entry<String, Object>> entrySet = content.entrySet();
    for (Map.Entry<String, Object> entry : entrySet) {
      Class<?> genericValueClass = genericTypeClasses[1];
      Mapper<?> valueMapper = mapperRegistry.getMapper(genericValueClass);

      String key = entry.getKey();
      Object read = valueMapper.read(embeddedKiraReader, key, null);
      contentMap.put(key, read);
    }

    return contentMap;
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, Map model) throws KiraModelException {

    Map<String, Object> contentMap = new HashMap<>();
    KiraWriter embeddedKiraWriter = KiraMapWriter.forData(contentMap);

    // Write map values
    Set<Map.Entry<String, Object>> entrySet = model.entrySet();
    for (Map.Entry<String, Object> entry : entrySet) {
      Object value = entry.getValue();
      Mapper valueMapper = mapperRegistry.getMapper(value.getClass());
      String key = entry.getKey();
      valueMapper.write(embeddedKiraWriter, key, value);
    }

    super.write(kiraWriter, propertyName, contentMap);
  }
}
