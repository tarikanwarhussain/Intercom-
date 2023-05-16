package com.trantor.enums;

public enum SchemaObjectEnum {
  CONVERSATIONS("conversations"), CONTACT("contact"), TAGS(
      "tags");

  private String streamObjectName;

  SchemaObjectEnum(String streamObjName) {
    this.streamObjectName = streamObjName;
  }

  public String getStreamObjectName() {
    return streamObjectName;
  }
}
