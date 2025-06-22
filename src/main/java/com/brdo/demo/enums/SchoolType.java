package com.brdo.demo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum SchoolType {
  GYMNASIUM("ГІМНАЗІЯ"),
  LYCEUM("ЛІЦЕЙ"),
  ZZSO("ЗЗСО");

  private final String ukrainianName;

  SchoolType(String ukrainianName) {
    this.ukrainianName = ukrainianName;
  }

  @JsonValue
  public String getUkrainianName() {
    return ukrainianName;
  }

  @JsonCreator
  public static SchoolType fromUkrainianName(String value) {
    return Arrays.stream(SchoolType.values())
        .filter(type -> type.ukrainianName.equalsIgnoreCase(value)
            || type.name().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid school type: " + value));
  }
}
