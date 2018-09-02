package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

@Value
public class FieldDescriptor implements Descriptor {
  private String name;
  private String path;
  private String description;
  private SimpleType type;
  private boolean optional;
  private boolean ignored;
  private Attributes attributes;
}
