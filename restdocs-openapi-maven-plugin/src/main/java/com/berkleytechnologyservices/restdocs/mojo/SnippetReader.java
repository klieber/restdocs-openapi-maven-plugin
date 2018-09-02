package com.berkleytechnologyservices.restdocs.mojo;

import com.berkleytechnologyservices.restdocs.resource.ResourceModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.plugin.MojoExecutionException;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class SnippetReader {

  private final ObjectMapper objectMapper;

  public SnippetReader() {
    this(new ObjectMapper());
  }

  public SnippetReader(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public List<ResourceModel> getModels(File snippetDirectory) throws MojoExecutionException {
    try {
      return Files.walk(snippetDirectory.toPath())
          .filter(Files::isRegularFile)
          .filter(SnippetReader::isResourceJson)
          .map(this::getModel)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new MojoExecutionException("Unable to read snippet files: " + snippetDirectory);
    }
  }

  private ResourceModel getModel(Path path) {
    try {
      return this.objectMapper.readValue(path.toFile(), ResourceModel.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to parse snippet file: " + path, e);
    }
  }

  private static boolean isResourceJson(Path path) {
    return path.toFile().getName().equals("resource.json");
  }
}
