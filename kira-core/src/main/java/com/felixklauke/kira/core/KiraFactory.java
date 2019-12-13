package com.felixklauke.kira.core;

import com.felixklauke.kira.core.module.KiraModule;
import com.google.inject.Guice;

public final class KiraFactory {
  private KiraFactory() {
  }

  public static KiraFactory create() {
    return new KiraFactory();
  }

  public Kira createKira() {
    var module = KiraModule.create();
    var injector = Guice.createInjector(module);
    return injector.getInstance(Kira.class);
  }
}
