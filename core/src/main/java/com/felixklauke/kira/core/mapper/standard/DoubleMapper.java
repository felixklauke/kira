package com.felixklauke.kira.core.mapper.standard;

public class DoubleMapper extends AbstractMapper<Double> {
  @Override
  public Class<Double> getModelClass() {
    return Double.TYPE;
  }
}
