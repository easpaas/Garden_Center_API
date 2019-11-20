package io.catalyte.training.exceptions;

import org.springframework.beans.factory.config.YamlProcessor;

public class ResourceNotFound extends RuntimeException {

  public ResourceNotFound(YamlProcessor.MatchStatus notFound) {}

  public ResourceNotFound(String message) {
    super(message);
  }
}
