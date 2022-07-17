package com.felixklauke.kira;

import com.felixklauke.kira.exception.KiraSerializationException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectionKiraTest {
  private Kira kira;

  @BeforeEach
  void setUp() {
    kira = KiraFactory.create().createKira();
  }

  @Test
  void testCollectionSerialization() throws KiraSerializationException {
    var model = new CollectionModel(List.of(1, 2, 3));
    var data = kira.serialize(model);
    System.out.println(data);
  }

  @Test
  void testCollectionDeserialization() {
  }

  public static final class CollectionModel {
    public final List<Integer> list;

    public CollectionModel(List<Integer> list) {
      this.list = list;
    }
  }
}
