package de.felixklauke.kira.core.mapper.standard;

import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;

import java.util.Map;

public class IntegerMapper extends AbstractMapper<Integer> {

  @Override
  public Class<Integer> getModelClass() {

    return Integer.class;
  }
}
