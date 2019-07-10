package de.felixklauke.kira.core.mapper.standard;

import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;
import de.felixklauke.kira.core.mapper.Mapper;

import java.util.List;

public class ListMapper extends AbstractMapper<List> {

  @Override
  public Class<List> getModelClass() {
    return List.class;
  }
}
