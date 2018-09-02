package com.berkleytechnologyservices.restdocs.resource;

public interface Descriptor {
  String getName();
  String getDescription();
  SimpleType getType();
  boolean isOptional();
}
