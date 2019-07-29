package de.felixklauke.kira.core.mapper.standard;

import de.felixklauke.kira.core.exception.KiraModelException;
import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;
import de.felixklauke.kira.core.io.SimpleKiraReader;
import de.felixklauke.kira.core.io.SimpleKiraWriter;
import de.felixklauke.kira.core.mapper.Mapper;
import de.felixklauke.kira.core.mapper.MapperManager;
import de.felixklauke.kira.core.util.TypeUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapMapper extends AbstractMapper<Map> {

  private final MapperManager mapperManager;

  public MapMapper(MapperManager mapperManager) {
    this.mapperManager = mapperManager;
  }

  @Override
  public Class<Map> getModelClass() {
    return Map.class;
  }

  @Override
  public Map read(KiraReader reader, String propertyName, Type genericType) {

    Map content = super.read(reader, propertyName, genericType);

    if (content == null) {
      return null;
    }

    Map contentMap = new HashMap();

    // Get generic type
    Class<?>[] genericTypeClasses = TypeUtils.getGenericTypeClasses(genericType);
    if (genericTypeClasses.length != 2) {
      throw new IllegalStateException("Invalid generic type arguments.");
    }

    // Construct map
    Set<Map.Entry> entrySet = content.entrySet();
    for (Map.Entry entry : entrySet) {
      Class<?> genericValueClass = genericTypeClasses[1];
      Mapper<?> valueMapper = mapperManager.getMapper(genericValueClass);

      try {
        Object read = valueMapper.read(new SimpleKiraReader(content), entry.getKey().toString(), null);
        contentMap.put(entry.getKey().toString(), read);
      } catch (KiraModelException e) {
        e.printStackTrace();
      }
    }

    return contentMap;
  }

  @Override
  public void write(KiraWriter kiraWriter, String propertyName, Map model) {

    Map contentMap = new HashMap();
    KiraWriter embeddedKiraWriter = new SimpleKiraWriter(contentMap);

    // Write map values
    Set<Map.Entry> entrySet = model.entrySet();
    for (Map.Entry entry : entrySet) {
      Mapper valueMapper = mapperManager.getMapper(entry.getValue().getClass());
      try {
        valueMapper.write(embeddedKiraWriter, entry.getKey().toString(), entry.getValue());
      } catch (KiraModelException e) {
        e.printStackTrace();
      }
    }

    super.write(kiraWriter, propertyName, contentMap);
  }
}
