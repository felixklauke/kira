package de.felixklauke.kira.core.mapper.standard;

import java.util.LinkedHashMap;

public class LinkedHashMapMapper extends AbstractMapper {
  @Override
  public Class getModelClass() {
    return LinkedHashMap.class;
  }
}
