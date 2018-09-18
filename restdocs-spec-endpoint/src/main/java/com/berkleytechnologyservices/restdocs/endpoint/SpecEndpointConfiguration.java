package com.berkleytechnologyservices.restdocs.endpoint;

import com.berkleytechnologyservices.restdocs.spec.generator.SpecificationGeneratorFactory;
import com.berkleytechnologyservices.restdocs.spec.generator.openapi_v2.OpenApi20SpecificationGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SpecEndpointConfiguration {

  @Bean
  public SpecificationGeneratorFactory specificationGeneratorFactory() {
    return new SpecificationGeneratorFactory(Collections.singletonList(new OpenApi20SpecificationGenerator()));
  }

}
