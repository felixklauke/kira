package com.felixklauke.kira.core.module;

import com.felixklauke.kira.core.Kira;
import com.felixklauke.kira.core.ReflectedKira;
import com.felixklauke.kira.core.meta.FunctionalPropertyCodec;
import com.felixklauke.kira.core.meta.ModelPropertyCodec;
import com.felixklauke.kira.core.meta.ModelPropertyCodecRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.List;
import java.util.Map;

public final class KiraModule extends AbstractModule {

  public static KiraModule create() {
    return new KiraModule();
  }

  @Override
  protected void configure() {
    bind(Kira.class).to(ReflectedKira.class).asEagerSingleton();
  }

  private static final Map<Class<?>, ModelPropertyCodec<?>> DEFAULT_CODECS = Map.of(
    String.class, FunctionalPropertyCodec.of(String::toString, String::valueOf),
    int.class, FunctionalPropertyCodec.of(Integer::intValue, value -> Integer.parseInt(value.toString())),
    float.class, FunctionalPropertyCodec.of(Float::floatValue, value -> Float.parseFloat(value.toString())),
    Double.class, FunctionalPropertyCodec.of(Double::doubleValue, value -> Double.parseDouble(value.toString()))
  );

  @Provides
  public ModelPropertyCodecRegistry provideCodecRegistry() {
    return ModelPropertyCodecRegistry.withCodecs(DEFAULT_CODECS);
  }
}
