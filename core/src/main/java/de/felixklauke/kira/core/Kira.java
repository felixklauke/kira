package de.felixklauke.kira.core;

import java.util.Map;

/**
 * Central kira API class.
 * <p>
 * You can serialize objects and deserialize map data from here:
 * <p>
 * {@link #serialize(Object)}
 * {@link #deserialize(Map, Class)}
 *
 * @author Felix Klauke (info@felix-klauke.de)
 */
public interface Kira {

  /**
   * Serialize the given model into a map.
   *
   * @param model       The model.
   * @param <ModelType> The generic type of the model.
   * @return The model as a map.
   */
  <ModelType> Map<String, Object> serialize(ModelType model);

  /**
   * Deserialize the given data into a model.
   *
   * @param data        The data.
   * @param modelClass  The model class.
   * @param <ModelType> The generic type of the model.
   * @return The model.
   */
  <ModelType> ModelType deserialize(Map<String, Object> data, Class<ModelType> modelClass);
}
