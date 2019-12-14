package com.felixklauke.kira.core.module;

import com.felixklauke.kira.core.Kira;
import com.felixklauke.kira.core.ReflectedKira;
import com.felixklauke.kira.core.meta.FunctionalCodec;
import com.felixklauke.kira.core.meta.PropertyCodec;
import com.felixklauke.kira.core.meta.PropertyCodecRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.Map;

public final class KiraModule extends AbstractModule {

  private static final Map<Class<?>, PropertyCodec<?>> DEFAULT_CODECS =
    Map.of(
      String.class,
      FunctionalCodec.of(String::toString, String::valueOf),
      int.class, FunctionalCodec
        .of(Integer::intValue, value -> Integer.parseInt(value.toString())),
      float.class, FunctionalCodec
        .of(Float::floatValue, value -> Float.parseFloat(value.toString())),
      Double.class, FunctionalCodec
        .of(Double::doubleValue, value -> Double.parseDouble(value.toString()))
    );

  private KiraModule() {
  }

  public static KiraModule create() {
    return new KiraModule();
  }

  @Override
  protected void configure() {
    bind(Kira.class).to(ReflectedKira.class).asEagerSingleton();
  }

  @Provides
  public PropertyCodecRegistry provideCodecRegistry() {
    return PropertyCodecRegistry.withCodecs(DEFAULT_CODECS);
  }
}
