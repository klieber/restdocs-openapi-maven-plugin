package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

import java.util.List;

@Value
public class RequestModel {

  private String path;
  private HttpMethod method;
  private String contentType;
  private SecurityRequirements securityRequirements;
  private List<HeaderDescriptor> headers;
  private List<ParameterDescriptor> pathParameters;
  private List<ParameterDescriptor> requestParameters;
  private List<FieldDescriptor> requestFields;
  private String example;
  private String schema;

}
