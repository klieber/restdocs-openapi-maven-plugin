package com.berkleytechnologyservices.restdocs.resource;

public interface Descriptor {
  String getName();
  String getDescription();
  String getType();
  boolean isOptional();
}
