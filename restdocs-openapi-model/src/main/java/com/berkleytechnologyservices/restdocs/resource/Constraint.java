package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

import java.util.Map;

@Value
public class Constraint {
  private String name;
  private Map<String, Object> configuration;
}
