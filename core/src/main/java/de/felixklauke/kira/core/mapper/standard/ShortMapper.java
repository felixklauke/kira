package de.felixklauke.kira.core.mapper.standard;

public class ShortMapper extends AbstractMapper<Short> {
  @Override
  public Class<Short> getModelClass() {
    return Short.TYPE;
  }
}
