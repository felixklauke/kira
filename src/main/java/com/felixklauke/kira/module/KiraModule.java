package com.felixklauke.kira.module;

import com.felixklauke.kira.Kira;
import com.felixklauke.kira.ReflectedKira;
import com.felixklauke.kira.meta.Codec;
import com.felixklauke.kira.meta.CodecRegistry;
import com.felixklauke.kira.meta.FunctionalCodec;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.Map;

public final class KiraModule extends AbstractModule {

  private static final Map<Class<?>, Codec<?>> DEFAULT_CODECS =
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
  public CodecRegistry provideCodecRegistry() {
    return CodecRegistry.withCodecs(DEFAULT_CODECS);
  }
}
