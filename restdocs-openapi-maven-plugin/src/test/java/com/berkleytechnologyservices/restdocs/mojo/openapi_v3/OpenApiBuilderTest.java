package com.berkleytechnologyservices.restdocs.mojo.openapi_v3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OpenApiBuilderTest {

  private final OpenApiBuilder builder = new OpenApiBuilder();

  @Test
  public void testBuild() {
//    OpenApiParameter parameter = new OpenApiParameter();
//    parameter.setName("id");
//    parameter.setType(Integer.class);
//
//    OpenApiRequest request = new OpenApiRequest();
//    request.setHost("api.example.org");
//    request.setBasePath("/api");
//    request.setPath("/user/{id}");
//    request.setHttpMethod("get");
//    request.setPathParameters(Collections.singletonList(parameter));
//
//    OpenApiResponse response = new OpenApiResponse();
//    response.setStatus(200);
//
//    OpenApiModel model = new OpenApiModel();
//    model.setRequest(request);
//    model.setResponse(response);
//
//    OpenAPI openAPI = builder.model(model).build();
//    assertThat(openAPI.getServers()).containsExactly(new Server().url("http://api.example.org/api"));
//    assertThat(openAPI.getPaths().get("/user/{id}")).isNotNull().satisfies(pathItem -> {
//      assertThat(pathItem.getGet().getParameters())
//          .extracting(Parameter::getName, (p) -> p.getSchema().getType())
//          .containsExactly(tuple("id", "integer"));
//      assertThat(pathItem.getGet().getResponses())
//          .hasSize(1)
//          .contains(entry("200", new ApiResponse().description("success")));
//    });
  }
}