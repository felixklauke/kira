package com.felixklauke.kira.core;

import com.felixklauke.kira.core.exception.KiraModelException;
import com.felixklauke.kira.core.exception.KiraModelInstantiationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleKiraTest {

  private static final String TEST_MODEL_NAME = "peter";
  private static final SimpleTestModel TEST_MODEL = new SimpleTestModel(TEST_MODEL_NAME);
  private static final Map<String, Object> TEST_MODEL_MAP = new HashMap<>() {{ put("name", TEST_MODEL_NAME); }};

  private Kira kira;

  @BeforeEach
  void setUp() {
    kira = KiraFactory.createKira();
  }

  @Test
  void testSerializeSimple() {

    Map<String, Object> serialize = kira.serialize(TEST_MODEL);
    assertEquals(TEST_MODEL_MAP, serialize, "Maps should match.");
  }

  @Test
  void testDeserializeSimple() {

    SimpleTestModel deserialize = kira.deserialize(TEST_MODEL_MAP, SimpleTestModel.class);
    assertEquals(TEST_MODEL, deserialize, "Java objects should equal.");
  }

  public static final class SimpleTestModel {

    private String name;

    public SimpleTestModel() {
    }

    public SimpleTestModel(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", SimpleTestModel.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .toString();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof SimpleTestModel)) return false;

      SimpleTestModel that = (SimpleTestModel) o;

      return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
      return name != null ? name.hashCode() : 0;
    }
  }
}
