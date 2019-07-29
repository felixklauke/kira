package com.felixklauke.kira.core.mapper.standard;

public class BooleanMapper extends AbstractMapper<Boolean> {
  @Override
  public Class<Boolean> getModelClass() {
    return Boolean.TYPE;
  }
}
