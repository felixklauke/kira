package com.felixklauke.kira.core.meta;

import java.util.Optional;

/**
 * @author Felix Klauke (info@felix-klauke.de)
 */
public interface ModelMetaRegistry {

  <ModelType> void saveMeta(Class<ModelType> modelClass, ModelMeta<ModelType> modelMeta);

  <ModelType> Optional<ModelMeta<ModelType>> getMeta(Class<ModelType> modelClass);
}
