package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

@Value
public class ParameterDescriptor implements Descriptor {
  private String name;
  private String path;
  private String description;
  private String type;
  private boolean optional;
  private boolean ignored;
}
