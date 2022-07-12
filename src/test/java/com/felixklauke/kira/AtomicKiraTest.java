package com.felixklauke.kira;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AtomicKiraTest {
  private Kira kira;

  @BeforeEach
  void setUp() {
    kira = Kira.create();
  }

  @Test
  public void testEmptyKiraModelEncoding() {
    var result = kira.encode(new EmptyKiraModel());
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testEmptyKiraModelDecoding() {
    var result = kira.decode(Map.of(), EmptyKiraModel.class);
    assertNotNull(result);
  }

  public static final class EmptyKiraModel {
  }
}
