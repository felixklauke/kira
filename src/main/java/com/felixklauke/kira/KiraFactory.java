package com.felixklauke.kira;

import com.felixklauke.kira.module.KiraModule;
import com.google.inject.Guice;

public final class KiraFactory {
  private KiraFactory() {
  }

  /**
   * Factory method to create a factory instance.
   *
   * @return Factory instance.
   */
  public static KiraFactory create() {
    return new KiraFactory();
  }

  /**
   * Create a new kira instance.
   *
   * @return Kira instance.
   */
  public Kira createKira() {
    var module = KiraModule.create();
    var injector = Guice.createInjector(module);
    return injector.getInstance(Kira.class);
  }
}
