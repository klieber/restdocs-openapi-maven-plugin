package com.berkleytechnologyservices.restdocs.mojo.openapi_v3;

import com.berkleytechnologyservices.restdocs.resource.ParameterDescriptor;
import com.berkleytechnologyservices.restdocs.resource.RequestModel;
import com.berkleytechnologyservices.restdocs.resource.ResourceModel;
import com.berkleytechnologyservices.restdocs.resource.ResponseModel;
import com.berkleytechnologyservices.restdocs.resource.SimpleType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OpenApiBuilder {

  private static final Map<Class<?>, Schema> TYPE_MAP = ImmutableMap.<Class<?>, Schema>builder()
      .put(String.class, new Schema().type("string"))
      .put(Boolean.class, new Schema().type("boolean"))
      .put(Integer.class, new Schema().type("integer").format("int32"))
      .put(Long.class, new Schema().type("integer").format("int64"))
      .put(Float.class, new Schema().type("number").format("float"))
      .put(Double.class, new Schema().type("number").format("double"))
      .put(Byte.class, new Schema().type("string").format("byte"))
      .put(Date.class, new Schema().type("string").format("date-time"))
      .put(Calendar.class, new Schema().type("string").format("date-time"))
      .build();

  private static final Map<SimpleType, Schema> TYPE_SCHEMA_MAP = ImmutableMap.<SimpleType, Schema>builder()
      .put(SimpleType.STRING, new Schema().type("string"))
      .put(SimpleType.BOOLEAN, new Schema().type("boolean"))
      .put(SimpleType.INTEGER, new Schema().type("integer"))
      .put(SimpleType.NUMBER, new Schema().type("number"))
      .build();

  private final Set<String> serverUrls;
  private final Map<String, PathItem> pathItems;

  private String name;
  private String version;

  public OpenApiBuilder() {
    this.serverUrls = new HashSet<>();
    this.pathItems = new HashMap<>();
  }

  public OpenApiBuilder name(String name) {
    this.name = name;
    return this;
  }

  public OpenApiBuilder version(String version) {
    this.version = version;
    return this;
  }

  public OpenApiBuilder serverUrl(String url) {
    this.serverUrls.add(url);
    return this;
  }

  public OpenApiBuilder serverUrl(String schema, String host, String basePath) {
    return this.serverUrl(schema + "://" + host + (basePath.startsWith("/") ? basePath : "/" + basePath));
  }

  public OpenApiBuilder pathItem(String path, PathItem pathItem) {
    this.pathItems.put(path, pathItem);
    return this;
  }

  public OpenApiBuilder operation(String path, String method, Operation operation) {
    PathItem pathItem = pathItems.get(path);

    if (pathItem == null) {
      pathItem = new PathItem();
      pathItems.put(path, pathItem);
    }

    if ("get".equalsIgnoreCase(method)) {
      pathItem.get(operation);
    } else if ("post".equalsIgnoreCase(method)) {
      pathItem.post(operation);
    } else if ("put".equalsIgnoreCase(method)) {
      pathItem.put(operation);
    } else if ("delete".equalsIgnoreCase(method)) {
      pathItem.delete(operation);
    } else if ("patch".equalsIgnoreCase(method)) {
      pathItem.patch(operation);
    }
    return this;
  }

  public OpenApiBuilder model(ResourceModel model) {
    Operation operation = new Operation()
        .parameters(createParameters(model.getRequest()))
        .responses(createResponses(model.getResponse()));

    return this.model(model.getRequest(), model.getResponse());
  }

  private OpenApiBuilder model(RequestModel request, ResponseModel response) {
    Operation operation = new Operation()
        .parameters(createParameters(request))
        .tags(Lists.newArrayList(convertPathToTag(request)))
        .responses(createResponses(response));
    return this
        //.serverUrl("http", request.getHost(), request.getBasePath())
        .operation(request.getPath(), request.getMethod().name().toLowerCase(), operation);
  }

  /**
   * Turn /endpoint/{somePathParam}/somethingElse into "endpoint"
   */
  private String convertPathToTag(RequestModel request) {
    int secondSlashIndex = request.getPath().indexOf("/", 1);
    return request.getPath().substring(1, secondSlashIndex >= 0 ? secondSlashIndex : request.getPath().length());
  }

  public OpenAPI build() {
    OpenAPI openAPI = new OpenAPI()
        .info(createInfo())
        .openapi("3.0.0")
        .servers(createServerList(this.serverUrls));

    pathItems.forEach(openAPI::path);

    return openAPI;
  }

  private List<Server> createServerList(Set<String> serverPaths) {
    return serverPaths.stream().map(this::createServer).collect(Collectors.toList());
  }

  public Server createServer(String serverPath) {
    return new Server().url(serverPath);
  }

  private List<Parameter> createParameters(RequestModel request) {
    List<Parameter> parameters = new ArrayList<>();
    parameters.addAll(createPathParameters(request.getPathParameters()));
    parameters.addAll(createQueryParameters(request.getRequestParameters()));
    return parameters;
  }

  private ApiResponses createResponses(ResponseModel response) {
    return new ApiResponses().addApiResponse(Integer.toString(response.getStatus()), new ApiResponse().description("success"));
  }

  private List<Parameter> createPathParameters(List<ParameterDescriptor> descriptors) {
    return descriptors != null ? descriptors.stream().map(this::createPathParameter).collect(Collectors.toList()) : Collections.emptyList();
  }

  private Parameter createPathParameter(ParameterDescriptor descriptor) {
    return createParameter(descriptor, "path");
  }

  private List<Parameter> createQueryParameters(List<ParameterDescriptor> descriptors) {
    return descriptors != null ? descriptors.stream().map(this::createQueryParameter).collect(Collectors.toList()) : Collections.emptyList();
  }

  private Parameter createQueryParameter(ParameterDescriptor descriptor) {
    return createParameter(descriptor, "query");
  }

  private Parameter createParameter(ParameterDescriptor descriptor, String in) {
    return new Parameter()
        .name(descriptor.getName())
        .description(descriptor.getDescription())
        .in(in)
        .schema(createSchema(descriptor.getType()));
  }

  private Schema createSchema(SimpleType type) {
    return TYPE_SCHEMA_MAP.getOrDefault(type, new Schema().type("object"));
  }

  private Info createInfo() {
    return new Info()
        .title(this.name != null ? this.name.trim().replaceAll("\\\\s", "-") : "api")
        .version(this.version != null ? this.version : "1.0.0");
  }
}
