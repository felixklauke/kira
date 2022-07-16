package com.felixklauke.kira;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.felixklauke.kira.exception.KiraDeserializationException;
import com.felixklauke.kira.exception.KiraSerializationException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimitiveKiraTest {
  private Kira kira;

  @BeforeEach
  void setUp() {
    kira = KiraFactory.create().createKira();
  }

  @Test
  void testPrimitiveSerialization() throws KiraSerializationException {
    PrimitiveContainer container = new PrimitiveContainer(
      1,
      2.3,
      (short) 2,
      2,
      true,
      'c',
      (byte) 1,
      1.0f
    );
    var data = kira.serialize(container);
    assertEquals(1, data.get("foo"));
    assertEquals(2.3, data.get("bar"));
    assertEquals((short) 2.5, data.get("baz"));
    assertEquals((long) 2, data.get("qux"));
    assertEquals(true, data.get("quux"));
    assertEquals('c', data.get("corge"));
    assertEquals((byte) 1, data.get("bit"));
    assertEquals(1.0f, data.get("floating"));
  }

  @Test
  void testPrimitiveDeserialization() throws KiraDeserializationException {
    Map<String, Object> data = Map.of(
      "foo", 1,
      "bar", 2.3,
      "baz",  2,
      "qux", 2,
      "quux", true,
      "corge", 'c',
      "bit", (byte) 1,
      "floating", 1.0f
    );
    var container = kira.deserialize(data, PrimitiveContainer.class);
    assertEquals(1, container.foo);
    assertEquals(2.3, container.bar);
    assertEquals((short) 2.5, container.baz);
    assertEquals(2, container.qux);
    assertEquals(true, container.quux);
    assertEquals('c', container.corge);
    assertEquals((byte) 1, container.bit);
    assertEquals(1.0f, container.floating);
  }

  public static final class PrimitiveContainer {
    private final int foo;
    private final double bar;
    private final short baz;
    private final long qux;
    private final boolean quux;
    private final char corge;
    private final byte bit;
    private final float floating;

    public PrimitiveContainer(int foo, double bar, short baz, long qux,
      boolean quux, char corge, byte bit, float floating) {
      this.foo = foo;
      this.bar = bar;
      this.baz = baz;
      this.qux = qux;
      this.quux = quux;
      this.corge = corge;
      this.bit = bit;
      this.floating = floating;
    }
  }
}
