package com.felixklauke.kira.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeUtils {

  /**
   * Extract the generic type arguments as classes.
   *
   * @param genericType The generic type.
   * @return The classes.
   */
  public static Class<?>[] getGenericTypeClasses(Type genericType) {

    if (!(genericType instanceof ParameterizedType)) {
      throw new IllegalStateException("Can only deserialitze map with valid generic types.");
    }

    // Infer generic type argument
    ParameterizedType parameterizedType = (ParameterizedType) genericType;
    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

    // Convert types to classes.
    Class<?>[] classes = new Class[actualTypeArguments.length];

    for (int i = 0; i < actualTypeArguments.length; i++) {

      Type actualTypeArgument = actualTypeArguments[i];

      if (!(actualTypeArgument instanceof Class)) {
        throw new IllegalStateException("Actual class wasn't a class.");
      }

      classes[i] = (Class<?>) actualTypeArgument;
    }

    return classes;
  }
}
