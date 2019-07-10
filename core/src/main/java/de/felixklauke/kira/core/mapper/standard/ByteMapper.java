package de.felixklauke.kira.core.mapper.standard;

public class ByteMapper extends AbstractMapper<Byte> {

  @Override
  public Class<Byte> getModelClass() {
    return Byte.TYPE;
  }
}
