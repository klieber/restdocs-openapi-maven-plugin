package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

@Value
public class HeaderDescriptor implements Descriptor {
  private String name;
  private String description;
  private SimpleType type;
  private boolean optional;
}
