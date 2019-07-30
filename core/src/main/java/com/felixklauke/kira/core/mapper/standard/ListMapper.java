package com.felixklauke.kira.core.mapper.standard;

import java.util.List;

public class ListMapper extends AbstractMapper<List> {

  @Override
  public Class<List> getModelClass() {
    return List.class;
  }
}
