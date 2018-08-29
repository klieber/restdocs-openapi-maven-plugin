package com.berkleytechnologyservices.restdocs.resource;

import lombok.Value;

@Value
public class ResourceModel {

  private String operationId;
  private String summary;
  private String description;
  private boolean privateResource;
  private boolean deprecated;
  private RequestModel request;
  private ResponseModel response;

}
