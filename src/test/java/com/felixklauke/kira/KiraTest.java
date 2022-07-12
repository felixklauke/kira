package com.felixklauke.kira;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KiraTest {
  @Test
  void create() {
    var kira = Kira.create();
    assertNotNull(kira);
  }
}
