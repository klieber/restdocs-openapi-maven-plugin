package com.berkleytechnologyservices.restdocs.endpoint;

import com.berkleytechnologyservices.restdocs.spec.ApiDetails;
import com.berkleytechnologyservices.restdocs.spec.Specification;
import com.berkleytechnologyservices.restdocs.spec.SpecificationFormat;
import com.berkleytechnologyservices.restdocs.spec.generator.SpecificationGenerator;
import com.berkleytechnologyservices.restdocs.spec.generator.SpecificationGeneratorException;
import com.berkleytechnologyservices.restdocs.spec.generator.SpecificationGeneratorFactory;
import com.epages.restdocs.openapi.model.ResourceModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecRestController {

  private final SpecificationGeneratorFactory generatorFactory;

  public SpecRestController(SpecificationGeneratorFactory generatorFactory) {
    this.generatorFactory = generatorFactory;
  }

  @GetMapping
  public ResponseEntity<String> getSpec(HttpServletRequest request,
                                        @RequestParam(value = "specification", defaultValue = "OPENAPI_V2") Specification specification,
                                        @RequestParam(value = "format", required = false) SpecificationFormat format) throws SpecificationGeneratorException, IOException {
    ApiDetails apiDetails = new ApiDetails()
        .host(request.getServerName() + ":" + request.getServerPort())
//      .basePath(request.getContextPath())
        .schemes(Collections.singletonList(request.getScheme()))
        .format(format);

    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new KotlinModule());

    List<ResourceModel> resources = new ArrayList<>();

    for (Resource resource : resolver.getResources("restdocs-spec/**/resource.json")) {
      resources.add(objectMapper.readValue(resource.getFile(), ResourceModel.class));
    }

    SpecificationGenerator generator = generatorFactory.createGenerator(specification);

    String rawDocument = generator.generate(apiDetails, resources);

    return ResponseEntity.ok()
        .contentType(MediaType.valueOf("text/yaml"))
        .body(rawDocument);
  }

}
