package com.felixklauke.kira.core.mapper.standard;

import java.util.Set;

public class SetMapper extends AbstractMapper<Set> {
  @Override
  public Class<Set> getModelClass() {
    return Set.class;
  }
}
