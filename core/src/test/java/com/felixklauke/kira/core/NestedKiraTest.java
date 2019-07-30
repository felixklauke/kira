package com.felixklauke.kira.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class NestedKiraTest {

  private static final String TEST_MODEL_NAME_PETER = "peter";
  private static final String TEST_MODEL_NAME_PAUL = "paul";
  private static final String TEST_MODEL_NAME_LISA = "lisa";

  private static final NestedTestModel TEST_MODEL_LISA = new NestedTestModel(TEST_MODEL_NAME_LISA, null);
  private static final NestedTestModel TEST_MODEL_PAUL = new NestedTestModel(TEST_MODEL_NAME_PAUL, TEST_MODEL_LISA);
  private static final NestedTestModel TEST_MODEL_PETER = new NestedTestModel(TEST_MODEL_NAME_PETER, TEST_MODEL_PAUL);

  private static final Map<String, Object> TEST_MODEL_PETER_MAP = new HashMap<>() {{
    put("name", TEST_MODEL_NAME_PETER);
    put("mother", new HashMap<>() {{
      put("name", TEST_MODEL_NAME_PAUL);
      put("mother", new HashMap<>() {{
        put("name", TEST_MODEL_NAME_LISA);
      }});
    }});
  }};


  private Kira kira;

  @BeforeEach
  void setUp() {
    kira = KiraFactory.createKira();
  }

  @Test
  void testSerializeNested() {

    Map<String, Object> serialize = kira.serialize(TEST_MODEL_PETER);
    Assertions.assertEquals(TEST_MODEL_PETER_MAP, serialize);
  }

  @Test
  void testDeserializeNested() {

    NestedTestModel deserialize = kira.deserialize(TEST_MODEL_PETER_MAP, NestedTestModel.class);
    Assertions.assertEquals(TEST_MODEL_PETER, deserialize);
  }

  public static class NestedTestModel {

    private String name;
    private NestedTestModel mother;

    public NestedTestModel() {
    }

    public NestedTestModel(String name, NestedTestModel mother) {
      this.name = name;
      this.mother = mother;
    }

    public String getName() {
      return name;
    }

    public NestedTestModel getMother() {
      return mother;
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", NestedTestModel.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("mother=" + mother)
        .toString();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      NestedTestModel that = (NestedTestModel) o;

      if (!name.equals(that.name)) return false;
      return mother != null ? mother.equals(that.mother) : that.mother == null;
    }

    @Override
    public int hashCode() {
      int result = name.hashCode();
      result = 31 * result + (mother != null ? mother.hashCode() : 0);
      return result;
    }
  }
}
