package com.xander.notnullgson;

import com.google.gson.internal.bind.NotNullAdapterFactory;
import com.google.gson.internal.bind.NotNullArrayAdapterFactory;
import com.google.gson.internal.bind.NotNullCollectionTypeAdapterFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;

public class NotNullGsonBuilder {

  public static GsonBuilder builder() {
    return builder(false);
  }

  public static GsonBuilder builder(boolean applyNotNull) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    if (!applyNotNull) {
      return gsonBuilder;
    }
    Field fieldInstanceCreators;
    try {
      fieldInstanceCreators = GsonBuilder.class.getDeclaredField("instanceCreators");
      fieldInstanceCreators.setAccessible(true);
      Map<Type, InstanceCreator<?>> map = (Map<Type, InstanceCreator<?>>) fieldInstanceCreators.get(gsonBuilder);
      ConstructorConstructor constructorConstructor = new ConstructorConstructor(map);
      gsonBuilder
          .registerTypeAdapterFactory(new NotNullCollectionTypeAdapterFactory(constructorConstructor))
          .registerTypeAdapterFactory(new NotNullArrayAdapterFactory())
          .registerTypeAdapterFactory(new NotNullAdapterFactory());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return gsonBuilder;
  }
}