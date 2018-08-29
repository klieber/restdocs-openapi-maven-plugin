package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

import java.util.List;

@Value
public class SecurityRequirements {
  private SecurityType type;
  private List<String> requiredScopes;
}
