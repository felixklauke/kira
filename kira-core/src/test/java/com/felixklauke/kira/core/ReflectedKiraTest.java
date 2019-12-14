package com.felixklauke.kira.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.base.Objects;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReflectedKiraTest {
  private static KiraFactory kiraFactory;
  private Kira kira;

  @BeforeAll
  static void beforeAll() {
    kiraFactory = KiraFactory.create();
  }

  @BeforeEach
  void setUp() {
    kira = kiraFactory.createKira();
  }

  private static final SimpleModel SIMPLE_MODEL = new SimpleModel("Hans", 10);
  private static final Map<String, Object> SIMPLE_MODEL_MAP = Map.of("name", "Hans", "age", 10);

  @Test
  void testSerializeSimple() throws KiraSerializationException {
    var serialize = kira.serialize(SIMPLE_MODEL);
    assertEquals(SIMPLE_MODEL_MAP, serialize);
  }

  @Test
  void testDeserializeSimple() throws KiraDeserializationException {
    SimpleModel deserialize = kira
      .deserialize(SIMPLE_MODEL_MAP, SIMPLE_MODEL.getClass());
    assertEquals(SIMPLE_MODEL, deserialize);
  }

  private static final ComplexModel COMPLEX_MODEL = new ComplexModel("Hans", new SimpleModel("Peter", 10));
  private static final Map<String, Object> COMPLEX_MODEL_MAP = Map.of("name", "Hans", "model", Map.of("name", "Peter", "age", 10));

  @Test
  void testSerializeComplex() throws KiraSerializationException {
    var serialize = kira.serialize(COMPLEX_MODEL);
    assertEquals(COMPLEX_MODEL_MAP, serialize);
  }

  @Test
  void testDeserializeComplex() throws KiraDeserializationException {
    ComplexModel deserialize = kira
      .deserialize(COMPLEX_MODEL_MAP, COMPLEX_MODEL.getClass());
    assertEquals(COMPLEX_MODEL, deserialize);
  }

  public static final class SimpleModel {
    private String name;
    private int age;

    public SimpleModel(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String name() {
      return name;
    }

    public int age() {
      return age;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      SimpleModel that = (SimpleModel) o;
      return age == that.age &&
        Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name, age);
    }
  }

  public static final class ComplexModel {
    private final String name;
    private final SimpleModel model;

    public ComplexModel(String name, SimpleModel model) {
      this.name = name;
      this.model = model;
    }

    public String name() {
      return name;
    }

    public SimpleModel model() {
      return model;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ComplexModel that = (ComplexModel) o;
      return Objects.equal(name, that.name) &&
        Objects.equal(model, that.model);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name, model);
    }
  }

  public static final class NestedModel {
    private final String name;
    private final NestedModel parent;

    public NestedModel(String name, NestedModel parent) {
      this.name = name;
      this.parent = parent;
    }

    public String name() {
      return name;
    }

    public NestedModel parent() {
      return parent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      NestedModel that = (NestedModel) o;
      return Objects.equal(name, that.name) &&
        Objects.equal(parent, that.parent);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name, parent);
    }
  }

  public static final class CollectionModel {
    private final String name;
    private final List<String> hobbies;

    public CollectionModel(String name, List<String> hobbies) {
      this.name = name;
      this.hobbies = hobbies;
    }

    public String name() {
      return name;
    }

    public List<String> hobbies() {
      return hobbies;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      CollectionModel that = (CollectionModel) o;
      return Objects.equal(name, that.name) &&
        Objects.equal(hobbies, that.hobbies);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name, hobbies);
    }
  }

  public static final class CollectionNestedModel {
    private final String name;
    private final Map<String, SimpleModel> brothers;

    public CollectionNestedModel(String name, Map<String, SimpleModel> brothers) {
      this.name = name;
      this.brothers = brothers;
    }

    public String name() {
      return name;
    }

    public Map<String, SimpleModel> brothers() {
      return brothers;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      CollectionNestedModel that = (CollectionNestedModel) o;
      return Objects.equal(name, that.name) &&
        Objects.equal(brothers, that.brothers);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name, brothers);
    }
  }
}