package com.felixklauke.kira.core.meta;

/**
 * @author Felix Klauke (info@felix-klauke.de)
 */
public interface ModelMetaManager {

  <ModelType> ModelMeta<ModelType> getMeta(Class<ModelType> modelClass);
}
