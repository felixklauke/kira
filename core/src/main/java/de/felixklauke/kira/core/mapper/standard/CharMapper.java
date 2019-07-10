package de.felixklauke.kira.core.mapper.standard;

public class CharMapper extends AbstractMapper<Character> {
  @Override
  public Class<Character> getModelClass() {
    return Character.TYPE;
  }
}
