package com.felixklauke.kira;

import java.util.Map;

public final class Kira {
  private Kira() {

  }

  public static Kira create() {
    return new Kira();
  }

  public <ModelType> Map<String, Object> encode(ModelType model) {
    return null;
  }

  public <ModelType> ModelType decode(
    Map<String, Object> model,
    Class<ModelType> modelType
  ) {
    return null;
  }
}
