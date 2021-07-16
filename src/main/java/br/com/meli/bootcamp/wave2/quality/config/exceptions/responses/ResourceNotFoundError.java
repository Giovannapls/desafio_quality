package br.com.meli.bootcamp.wave2.quality.config.exceptions.responses;

import br.com.meli.bootcamp.wave2.quality.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResourceNotFoundError extends ApiError {

  @Schema(description = "Resource type that wasn't present on the server", example = "User")
  private String resource;

  @Schema(type = "integer", description = "ID used to search for the resource", example = "1")
  private Object resourceId;

  public ResourceNotFoundError(ResourceNotFoundException exception,  LocalDateTime timeError) {
    super(exception,  timeError);
    this.resource = exception.getResource();
    this.resourceId = exception.getResourceId();
  }
}
