package de.felixklauke.kira.core.mapper.standard;

import java.util.Map;

public class MapMapper extends AbstractMapper<Map> {

  @Override
  public Class<Map> getModelClass() {
    return Map.class;
  }
}
