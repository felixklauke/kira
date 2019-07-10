package de.felixklauke.kira.core.mapper.standard;

public class LongMapper extends AbstractMapper<Long> {
  @Override
  public Class<Long> getModelClass() {
    return Long.TYPE;
  }
}
