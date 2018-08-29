package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

import java.util.List;

@Value
public class ResponseModel {

  private int status;
  private String contentType;
  private List<HeaderDescriptor> headers;
  private List<FieldDescriptor> responseFields;
  private String example;
  private String schema;

}
