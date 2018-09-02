package com.berkleytechnologyservices.restdocs.mojo;

import com.berkleytechnologyservices.restdocs.resource.ResourceModel;

import java.util.List;

public interface SpecificationGenerator {
  Specification getSpecification();
  String generate(ApiDetails details, List<ResourceModel> models) throws SpecificationGeneratorException;
}
