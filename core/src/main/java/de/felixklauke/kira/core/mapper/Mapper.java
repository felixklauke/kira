package de.felixklauke.kira.core.mapper;

import de.felixklauke.kira.core.exception.KiraModelException;
import de.felixklauke.kira.core.io.KiraReader;
import de.felixklauke.kira.core.io.KiraWriter;

import java.lang.reflect.Type;

/**
 * A mapper transforms a data type to its map representation and vice versa.
 *
 * @param <ContentType> The generic type of the data.
 * @author Felix Klauke (info@felix-klauke.de)
 */
public interface Mapper<ContentType> {

  /**
   * Get the class this mapper is mapping.
   *
   * @return The class being mapped.
   */
  Class<ContentType> getModelClass();

  ContentType read(KiraReader reader, String propertyName, Type genericType) throws KiraModelException;

  /**
   * Write the model.
   *
   * @param kiraWriter   The writer.
   * @param propertyName
   */
  void write(KiraWriter kiraWriter, String propertyName, ContentType model) throws KiraModelException;
}
