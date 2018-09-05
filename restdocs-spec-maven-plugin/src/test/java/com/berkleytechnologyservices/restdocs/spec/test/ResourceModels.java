package com.berkleytechnologyservices.restdocs.spec.test;

import com.epages.restdocs.openapi.generator.Attributes;
import com.epages.restdocs.openapi.generator.FieldDescriptor;
import com.epages.restdocs.openapi.generator.HTTPMethod;
import com.epages.restdocs.openapi.generator.HeaderDescriptor;
import com.epages.restdocs.openapi.generator.ParameterDescriptor;
import com.epages.restdocs.openapi.generator.RequestModel;
import com.epages.restdocs.openapi.generator.ResourceModel;
import com.epages.restdocs.openapi.generator.ResponseModel;
import com.epages.restdocs.openapi.generator.SecurityRequirements;

import java.util.List;

import static org.assertj.core.util.Lists.emptyList;

public final class ResourceModels {
  
  public static ResourceModel resource(String operationId, String description, RequestModel request, ResponseModel response) {
    return resource(
        operationId,
        description,
        description,
        request,
        response
    );
  }

  public static ResourceModel resource(String operationId, String summary, String description, RequestModel request, ResponseModel response) {
    return resource(
        operationId,
        summary,
        description,
        false,
        false,
        request,
        response
    );
  }

  public static ResourceModel resource(String operationId, String summary, String description, boolean privateResource, boolean deprecated, RequestModel request, ResponseModel response) {
    return new ResourceModel(
        operationId,
        summary,
        description,
        privateResource,
        deprecated,
        request,
        response
    );
  }

  public static RequestModel request(String path,
                                      HTTPMethod method,
                                      List<ParameterDescriptor> pathParameters,
                                      List<ParameterDescriptor> requestParameters) {
    return request(
        path,
        method,
        null,
        null,
        emptyList(),
        pathParameters,
        requestParameters,
        emptyList(),
        null,
        null
    );
  }

  public static RequestModel request(String path,
                                      HTTPMethod method,
                                      String contentType,
                                      SecurityRequirements securityRequirements,
                                      List<HeaderDescriptor> headers,
                                      List<ParameterDescriptor> pathParameters,
                                      List<ParameterDescriptor> requestParameters,
                                      List<? extends FieldDescriptor> requestFields, String example, String schema) {
    return new RequestModel(
        path,
        method,
        contentType,
        securityRequirements,
        headers,
        pathParameters,
        requestParameters,
        requestFields,
        example,
        schema
    );
  }

  public static ResponseModel response(int status, String contentType, List<FieldDescriptor> fields) {
    return response(status, contentType, emptyList(), fields);
  }

  public static ResponseModel response(int status, String contentType, List<HeaderDescriptor> headers, List<FieldDescriptor> fields) {
    return response(status, contentType, headers, fields, null, null);
  }

  public static ResponseModel response(int status,
                                        String contentType,
                                        List<HeaderDescriptor> headers,
                                        List<FieldDescriptor> fields,
                                        String example,
                                        String schema) {
    return new ResponseModel(status, contentType, headers, fields, example, schema);
  }

  public static FieldDescriptor field(String path, String description, String type) {
    return new FieldDescriptor(path, description, type, false, false, new Attributes());
  }

  public static ParameterDescriptor requiredParam(String name, String description, String type) {
    return param(name, description, type, false);
  }

  public static ParameterDescriptor param(String name, String description, String type) {
    return param(name, description, type, true);
  }

  public static ParameterDescriptor param(String name, String description, String type, boolean optional) {
    return new ParameterDescriptor(name, description, type, optional, false);
  }
}
