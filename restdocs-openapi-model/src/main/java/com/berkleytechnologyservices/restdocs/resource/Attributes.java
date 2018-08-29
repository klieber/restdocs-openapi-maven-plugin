package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

import java.util.List;

@Value
public class Attributes {
  List<Constraint> validationConstraints;
}
