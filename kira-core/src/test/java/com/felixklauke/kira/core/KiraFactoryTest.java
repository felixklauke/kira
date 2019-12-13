package com.felixklauke.kira.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KiraFactoryTest {
  private KiraFactory kiraFactory;

  @BeforeEach
  void setUp() {
    kiraFactory = KiraFactory.create();
  }

  @Test
  void testCreateKira() {
    var kira = kiraFactory.createKira();
    assertNotNull(kira);
  }
}