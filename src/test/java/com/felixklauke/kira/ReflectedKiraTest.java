package com.felixklauke.kira;

import static org.junit.jupiter.api.Assertions.*;

import com.felixklauke.kira.exception.KiraDeserializationException;
import com.felixklauke.kira.exception.KiraSerializationException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReflectedKiraTest {

  private Kira kira;

  @BeforeEach
  void setUp() {
    kira = KiraFactory.create().createKira();
  }

  @Test
  void serialize() throws KiraSerializationException {
    var data = kira.serialize(new TestModel(
      "foo",
      1,
      1.0
    ));
    assertEquals("foo", data.get("foo"));
    assertEquals(1, data.get("bar"));
    assertEquals(1.0, data.get("baz"));
  }

  @Test
  void deserialize() throws KiraDeserializationException {
    var data = Map.<String, Object>of(
      "foo", "foo",
      "bar", 1,
      "baz", 1.0
    );
    var model = kira.deserialize(data, TestModel.class);
    assertEquals("foo", model.foo);
    assertEquals(1, model.bar);
    assertEquals(1.0, model.baz);
  }

  @Test
  void serializeNested() throws KiraSerializationException {
    var data = kira.serialize(new NestedTestModel(
      "fooBar",
      2,
      2.0,
      new TestModel(
        "barFoo",
        3,
        3.0
      )
    ));
    assertEquals("fooBar", data.get("foo"));
    assertEquals(2, data.get("bar"));
    assertEquals(2.0, data.get("baz"));
    assertEquals("barFoo", ((Map<String, Object>) data.get("testModel")).get("foo"));
    assertEquals(3, ((Map<String, Object>) data.get("testModel")).get("bar"));
    assertEquals(3.0, ((Map<String, Object>) data.get("testModel")).get("baz"));
  }

  @Test
  void deserializeNested() throws KiraDeserializationException {
    var data = Map.<String, Object>of(
      "foo", "fooBar",
      "bar", 2,
      "baz", 2.0,
      "testModel", Map.<String, Object>of(
        "foo", "barFoo",
        "bar", 3,
        "baz", 3.0
      )
    );
    var model = kira.deserialize(data, NestedTestModel.class);
    assertEquals("fooBar", model.foo);
    assertEquals(2, model.bar);
    assertEquals(2.0, model.baz);
    assertEquals("barFoo", model.testModel.foo);
    assertEquals(3, model.testModel.bar);
    assertEquals(3.0, model.testModel.baz);
  }

  public static final class TestModel {
    public String foo = "foo";
    public int bar = 1;
    public double baz = 1.0;

    public TestModel() {
    }

    public TestModel(String foo, int bar, double baz) {
      this.foo = foo;
      this.bar = bar;
      this.baz = baz;
    }
  }

  public static final class NestedTestModel {
    public String foo;
    public int bar;
    public double baz;
    public TestModel testModel;

    public NestedTestModel() {
    }

    public NestedTestModel(String foo, int bar, double baz, TestModel testModel) {
      this.foo = foo;
      this.bar = bar;
      this.baz = baz;
      this.testModel = testModel;
    }
  }
}
