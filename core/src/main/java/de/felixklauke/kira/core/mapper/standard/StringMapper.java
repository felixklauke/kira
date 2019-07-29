package com.felixklauke.kira.core.mapper.standard;

import com.felixklauke.kira.core.mapper.Mapper;

public class StringMapper extends AbstractMapper<String> implements Mapper<String> {

  private static final Class<String> STRING_CLASS = String.class;

  @Override
  public Class<String> getModelClass() {

    return STRING_CLASS;
  }
}
